package ds.hdfs;

import com.google.protobuf.ByteString;
import ds.hdfs.generated.*;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private Config config;
    private final NameNodeGrpc.NameNodeBlockingStub nameNodeBlockingStub;
    private Map<DataNodeInfo, DataNodeConnection> openDataNodes = new ConcurrentHashMap<>();

    public Client(Config config, Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.
        this.config = config;

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        nameNodeBlockingStub = NameNodeGrpc.newBlockingStub(channel);
    }

    // helper class for manipulating files
    class ClientFile {

        private RandomAccessFile randomAccessFile;

        ClientFile(String fileName) throws IOException {
            randomAccessFile = new RandomAccessFile(fileName, "rw");
        }

        public void writeBlock(Block block) throws IOException, RuntimeException {

            int offset = block.getBlockInfo().getIndex() * config.BLOCK_SIZE_BYTES;

            randomAccessFile.write(block.getContent().toByteArray(), offset, block.getBlockInfo().getBlockSize());

        }

        public Block readBlock(BlockMetadata blockMetadata) throws IOException {

            int offset = blockMetadata.getIndex() * config.BLOCK_SIZE_BYTES;

            byte[] tempDataBuffer = new byte[blockMetadata.getBlockSize()];
            randomAccessFile.seek(offset);
            randomAccessFile.read(tempDataBuffer);

            return Block.newBuilder()
                    .setBlockInfo(blockMetadata)
                    .setContent(ByteString.copyFrom(tempDataBuffer))
                    .build();

        }

        public long length() throws IOException {
            return this.randomAccessFile.length();
        }

        public void close() throws IOException {
            randomAccessFile.close();
        }
    }

    // helper class that makes connecting to DN's quick
    class DataNodeConnection {

        private DataNodeGrpc.DataNodeBlockingStub dataNodeBlockingStub;
        private ManagedChannel managedChannel;
        private DataNodeInfo dataNodeInfo;

        DataNodeConnection(DataNodeInfo dataNodeInfo) {
            this.dataNodeInfo = dataNodeInfo;
            managedChannel = ManagedChannelBuilder
                    .forAddress(dataNodeInfo.getIp(),
                            dataNodeInfo.getPort())
                    .usePlaintext()
                    .build();

            dataNodeBlockingStub = DataNodeGrpc
                    .newBlockingStub(managedChannel);

            openDataNodes.putIfAbsent(dataNodeInfo, this);

        }

        public DataNodeInfo getDataNodeInfo() {
            return this.dataNodeInfo;
        }

        public ds.hdfs.generated.Status writeBlock(Block block) {
            return dataNodeBlockingStub.withDeadlineAfter(config.CLIENT_DN_DEADLINE_MS,
                    TimeUnit.MILLISECONDS).writeBlock(block);
        }

        public Block readBlock(BlockMetadata blockMetadata) {
            return dataNodeBlockingStub.readBlock(blockMetadata);
        }

        public void shutdown() throws InterruptedException {
            managedChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            if (managedChannel.isShutdown()) {
                openDataNodes.remove(this.dataNodeInfo);
            }
        }
    }

    // helper function to close a DN connection
    private void closeDNConnections() throws InterruptedException {
        for (HashMap.Entry<DataNodeInfo, DataNodeConnection> openDataNode : openDataNodes.entrySet()) {
            openDataNode.getValue().shutdown();
        }
    }

    /**
     * given a remoteFile
     * request the NN to respond with all the locations of known blocks
     * then retrieve each block from each DN
     * write the assembled set of blocks to localFile
     * @param remoteFile
     * @param localFile
     * @throws IOException
     */
    private void get(String remoteFile, String localFile) throws IOException {
        HashMap<DataNodeInfo, Long> bytesRetrievedSuccessfully = new HashMap<>();
        HashSet<BlockMetadata> blocksSeen = new HashSet<>();

        try {
            BlockLocationMapping responseWithBlockLocationMapping = nameNodeBlockingStub
                    .getBlockLocations(FileMetadata.newBuilder()
                            .setName(remoteFile)
                            .build());

            if (responseWithBlockLocationMapping.getFileInfo().getSize() > 0) {

                ClientFile clientFile = new ClientFile(localFile);

                for (BlockLocation blockLocation : responseWithBlockLocationMapping.getMappingList()) {
                    DataNodeConnection currentDataNodeConnection;

                    // we only need to open a connection to a data node once, this saves resources
                    if (openDataNodes.containsKey(blockLocation.getDataNodeInfo())) {
                        currentDataNodeConnection = openDataNodes.get(blockLocation.getDataNodeInfo());
                    } else {
                        // datanodeconnection class adds to the list of open datanodes already
                        currentDataNodeConnection = new DataNodeConnection(blockLocation.getDataNodeInfo());
                    }

                    if (!blocksSeen.contains(blockLocation.getBlockInfo())) {
                        try {
                            Block retrievedBlock = currentDataNodeConnection.readBlock(blockLocation.getBlockInfo());

                            if (retrievedBlock.isInitialized()) {
                                clientFile.writeBlock(retrievedBlock);

                                bytesRetrievedSuccessfully.put(blockLocation.getDataNodeInfo(),
                                        bytesRetrievedSuccessfully.getOrDefault(
                                                blockLocation.getDataNodeInfo(), 0L)
                                                + blockLocation.getBlockInfo().getBlockSize());

                                blocksSeen.add(blockLocation.getBlockInfo());
                            } else {
                                throw new RuntimeException("Error retrieving block from DataNode");
                            }
                        } catch (RuntimeException e) {
                            logger.log(Level.WARNING, "Could not retrieve block from DataNode "
                                    + blockLocation.getDataNodeInfo().getIp() + ":"
                                    + blockLocation.getDataNodeInfo().getPort()
                                    + " because: " + e.getMessage()
                                    + " continue to try other DNs...", e);
                        }
                    }
                }

                if (Collections.max(bytesRetrievedSuccessfully.values()) <
                        responseWithBlockLocationMapping.getFileInfo().getSize()) {
                    throw new RuntimeException("Not all blocks were successfully retrieved.");
                }

                clientFile.close();
            }
            else {
                logger.log(Level.WARNING, "Remote File: " + remoteFile + " was not found, nothing done.");
            }

        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Was unable to successfully get file. ", e);
        }


    }

    /**
     * given a localFile:
     * - send the file metadata to the NN but use the name of remoteFile
     * with the NN response:
     * - send the assignment blocks to each specified DN
     * @param localFile
     * @param remoteFile
     * @throws IOException
     */
    private void put(String localFile, String remoteFile) throws IOException {

        File checkFile = new File(localFile);

        if (checkFile.exists() && checkFile.isFile()) {
            ClientFile clientFile = new ClientFile(localFile);

            FileMetadata fileMetadata = FileMetadata.newBuilder()
                    .setSize(clientFile.length())
                    .setName(remoteFile)
                    .build();

            HashMap<DataNodeInfo, Long> bytesSentSuccessfully = new HashMap<>();

            try {
                BlockLocationMapping responseWithBlockLocationMapping = nameNodeBlockingStub.assignBlocks(fileMetadata);

                // we only need to do anything if there was actually stuff in the file
                // else we just return
                if (responseWithBlockLocationMapping.getMappingList().size() > 0) {

                    for (BlockLocation blockLocation : responseWithBlockLocationMapping.getMappingList()) {

                        DataNodeConnection currentDataNodeConnection;
                        // we only need to open a connection to a data node once, this saves resources
                        if (openDataNodes.containsKey(blockLocation.getDataNodeInfo())) {
                            currentDataNodeConnection = openDataNodes.get(blockLocation.getDataNodeInfo());
                        } else {
                            // datanodeconnection class adds to the list of open datanodes already
                            currentDataNodeConnection = new DataNodeConnection(blockLocation.getDataNodeInfo());
                        }

                        Block blockToWrite = clientFile.readBlock(blockLocation.getBlockInfo());

                        try {
                            Status responseWithSuccess = currentDataNodeConnection.writeBlock(blockToWrite);
                            if (!responseWithSuccess.getSuccess()) {
                                throw new RuntimeException("Error writing block to DataNode");
                            } else {
                                bytesSentSuccessfully.put(blockLocation.getDataNodeInfo(),
                                        bytesSentSuccessfully.getOrDefault(
                                                blockLocation.getDataNodeInfo(), 0L)
                                                + blockLocation.getBlockInfo().getBlockSize());
                            }
                        } catch (RuntimeException e) {
                            logger.log(Level.WARNING, "Could not write block to DataNode "
                                    + blockLocation.getDataNodeInfo().getIp() + ":"
                                    + blockLocation.getDataNodeInfo().getPort()
                                    + " because: " + e.getMessage()
                                    + " continue to try other DNs...", e);
                        }
                    }

                    if (Collections.max(bytesSentSuccessfully.values()) < fileMetadata.getSize()) {
                        throw new RuntimeException("Not all blocks were successfully written.");
                    }
                }

            } catch (RuntimeException e) {
                logger.log(Level.SEVERE, "Was unable to successfully put file. ", e);
            }

            clientFile.close();
        } else {
            logger.log(Level.WARNING, "Local File: " + localFile + " does not exist, nothing done.");
        }
    }

    /**
     * sends a request to the DN asking for current list of files it is tracking
     */
    public void list() {
        ListFilesParam request = ListFilesParam.getDefaultInstance();

        try {
            FileList responseWithFileList = nameNodeBlockingStub.listFiles(request);

            System.out.println("\tGot " + responseWithFileList.getFilesCount() + " file(s):");
            for (FileMetadata fileMetadata : responseWithFileList.getFilesList()) {
                System.out.println("\t\t" + fileMetadata.getName());
            }
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "unable to get a list of files.", e);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Config config;
        // Allow passing in the user and target strings as command line arguments
        if (args.length == 0 || args[0].equals("help")) {
            System.err.println("Usage: <command> <file> <file> [configFile]");
            System.err.println("");
            System.err.println("\tget hdfs_file local_file\tWrites a file to the local filesystem from HDFS");
            System.err.println("\tput local_file hdfs_file\tWrites a new file to HDFS from local file system");
            System.err.println("\tlist                    \tDisplays all files present in HDFS");
            System.err.println("");
            System.err.println("\thelp                    \tDisplay this message.");
            System.exit(1);
        } else {

            if (args.length > 3) {
                config = Config.readConfig(args[3]);
            } else if (args.length == 2 && args[0].equals("list")) {
                config = Config.readConfig(args[1]);
            } else {
                config = Config.readConfig("config/default_config.json");
            }

            // Create a communication channel to the server, known as a Channel. Channels are thread-safe
            // and reusable. It is common to create channels at the beginning of your application and reuse
            // them until the application shuts down.
            ManagedChannel channel = ManagedChannelBuilder.forAddress(config.NAME_NODE_IP, config.NAME_NODE_PORT)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();

            Client client = new Client(config, channel);

            try {
                switch (args[0]) {
                    case "get":
                        client.get(args[1], args[2]);
                        break;
                    case "put":
                        client.put(args[1], args[2]);
                        break;
                    case "list":
                        client.list();
                        break;
                }

            } finally {
                // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
                // resources the channel should be shut down when it will no longer be used. If it may be used
                // again leave it running.

                client.closeDNConnections();
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            }
        }
    }
}
