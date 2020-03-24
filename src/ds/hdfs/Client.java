package ds.hdfs;

import com.google.protobuf.ByteString;
import ds.hdfs.generated.*;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private static int BLOCK_SIZE;
    private final NameNodeGrpc.NameNodeBlockingStub nameNodeBlockingStub;
    private Map<DataNodeInfo, DataNodeConnection> openDataNodes = new ConcurrentHashMap<>();

    public Client(Channel channel, int blockSize) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        nameNodeBlockingStub = NameNodeGrpc.newBlockingStub(channel);
        BLOCK_SIZE = blockSize;
    }

    class ClientFile {

        private RandomAccessFile randomAccessFile;

        ClientFile(String fileName) throws IOException {
            randomAccessFile = new RandomAccessFile(fileName, "rw");
        }

        ClientFile(FileMetadata fileMetadata) throws IOException {
            this(fileMetadata.getName());
        }

        public void writeBlock(Block block) throws IOException, RuntimeException {

            int offset = block.getBlockInfo().getIndex() * BLOCK_SIZE;

            randomAccessFile.write(block.getContent().toByteArray(), offset, block.getBlockInfo().getBlockSize());

        }

        public Block readBlock(BlockMetadata blockMetadata) throws IOException {

            int offset = blockMetadata.getIndex() * BLOCK_SIZE;

            byte[] tempDataBuffer = new byte[blockMetadata.getBlockSize()];
            randomAccessFile.seek(offset);
            randomAccessFile.read(tempDataBuffer);

            return Block.newBuilder()
                    .setBlockInfo(blockMetadata)
                    .setContent(ByteString.copyFrom(tempDataBuffer))
                    .build();

        }

        public void close() throws IOException {
            randomAccessFile.close();
        }
    }

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
            return dataNodeBlockingStub.withDeadlineAfter(2, TimeUnit.SECONDS).writeBlock(block);
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

    private void shutDownAllDataNodes() throws InterruptedException {
        for (HashMap.Entry<DataNodeInfo, DataNodeConnection> openDataNode : openDataNodes.entrySet()) {
            openDataNode.getValue().shutdown();
        }
    }

    private void get(String fileName) throws IOException {

        // TODO: remove debug data
        FileMetadata request = FileMetadata.newBuilder()
                .setName(fileName)
                .build();

        // TODO: assemble metadata and get blocks from nodes
        // TODO: write out file

        ClientFile clientFile = new ClientFile(request);
//        Set<BlockMetadata> successfullySentBlocks = new LinkedHashSet<>();
//        if (!successfullySentBlocks.contains(blockLocation.getBlockInfo())) {

        try {
            BlockLocationMapping response = nameNodeBlockingStub.getBlockLocations(request);

//            for (BlockLocation blockLocation : response.getMappingList()) {
//                DataNodeConnection currentDataNodeConnection;
//
//
//
//
//
//                clientFile.writeBlock(currentDataNodeConnection
//                        .readBlock(blockLocation.getBlockInfo()));
//
//                throw new RuntimeException("Error reading block from DataNode" + currentDataNodeConnection.getDataNodeInfo());
//
//            }
        } catch (StatusRuntimeException e) {
            System.err.println("err");
        }
    }

    private void put(String fileName) throws IOException {
        ClientFile clientFile = new ClientFile(fileName);
        FileMetadata fileMetadata = FileMetadata.newBuilder()
                .setSize(clientFile.randomAccessFile.length())
                .setName(fileName)
                .build();
        HashMap<DataNodeInfo, Long> bytesWrittenSuccessfully = new HashMap<>();

        try {
            BlockLocationMapping responseWithBlockLocationMapping = nameNodeBlockingStub.assignBlocks(fileMetadata);

            for (BlockLocation blockLocation : responseWithBlockLocationMapping.getMappingList()) {

                DataNodeConnection currentDataNodeConnection;
                // we only need to open a connection to a data node once, this saves resources
                if (openDataNodes.containsKey(blockLocation.getDataNodeInfo())) {
                    currentDataNodeConnection = openDataNodes.get(blockLocation.getDataNodeInfo());
                } else {
                    // datanodeconnection class adds to the list of open datanodes already
                    currentDataNodeConnection = new DataNodeConnection(blockLocation.getDataNodeInfo());
                }

                bytesWrittenSuccessfully.put(blockLocation.getDataNodeInfo(),
                        bytesWrittenSuccessfully.getOrDefault(
                                blockLocation.getDataNodeInfo(), 0L)
                                + blockLocation.getBlockInfo().getBlockSize());

                Block blockToWrite = clientFile.readBlock(blockLocation.getBlockInfo());

                try {
                    Status responseWithSuccess = currentDataNodeConnection.writeBlock(blockToWrite);
                    if (!responseWithSuccess.getSuccess()) {
                        throw new RuntimeException("Error writing block to DataNode"
                                + currentDataNodeConnection.getDataNodeInfo());
                    }

                } catch (StatusRuntimeException e) {
                    if (e.getStatus().getCode() == io.grpc.Status.Code.DEADLINE_EXCEEDED) {
                        logger.log(Level.SEVERE, "Could not contact DataNode "
                                + blockLocation.getDataNodeInfo() + ", continuing to try others", e);
                    }
                }
            }

            if (Collections.max(bytesWrittenSuccessfully.values()) < fileMetadata.getSize()) {
                throw new RuntimeException("Error: unable completely send file to any Data Node");
            }

        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Was not able to successfully put file because: " + e.getMessage(), e);
        }

        clientFile.close();
    }

    public void list() {
        // TODO: remove debug data
        ListFilesParam request = ListFilesParam.getDefaultInstance();

        try {
            FileList response = nameNodeBlockingStub.listFiles(request);
            System.out.println("got files: " + response.getFilesList());
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "err");
        }
    }

    private void testDNHeartbeat() {

        // TODO: remove debug data
        BlockReport request = BlockReport
                .newBuilder()
                .addBlocks(BlockMetadata.newBuilder()
                        .setFileName("test.txt")
                        .setIndex(0)
                        .setBlockSize(64)
                        .build())
                .addBlocks(BlockMetadata.newBuilder()
                        .setFileName("test.txt")
                        .setIndex(1)
                        .setBlockSize(64)
                        .build())
                .setDataNodeInfo(DataNodeInfo.newBuilder()
                        .setIp("69.69.69.70")
                        .setPort(6942)
                        .build())
                .build();

        try {
            Status response = nameNodeBlockingStub.heartBeat(request);
            System.out.println("sent heartbeat: " + response.getSuccess());
            if (!response.getSuccess()) {
                throw new RuntimeException("Error occurred");
            }
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "dn heartbeat test failed");
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
// Access a service running on the local machine on port 50051
        int BLOCK_SIZE = 64;

        String target = "localhost:50051";
        // Allow passing in the user and target strings as command line arguments
//        if (args.length > 0) {
//            if ("--help".equals(args[0])) {
//                System.err.println("Usage: [name [target]]");
//                System.err.println("");
//                System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
//                System.err.println("  target  The server to connect to. Defaults to " + target);
//                System.exit(1);
//            }
//            user = args[0];
//        }
//        if (args.length > 1) {
//            target = args[1];
//        }

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();

        Client client = new Client(channel, BLOCK_SIZE);


        try {
            // TODO: read in config data
            // TODO: read in NN config
            // TODO: remove debug data
            // TODO: implemennt help & commands
            // TODO: look at file size vs block metadatas returned from NN



            client.testDNHeartbeat();
            client.list();
            client.get("test.txt");
//            client.put("test.txt");
            client.get("test.txt");

        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.

            client.shutDownAllDataNodes();
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

        }
    }
}
