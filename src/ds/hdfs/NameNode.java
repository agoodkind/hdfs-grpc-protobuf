package ds.hdfs;

import ds.hdfs.generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NameNode extends NameNodeGrpc.NameNodeImplBase {

    Config config;
    private ConcurrentHashMap<String, BlockLocationMapping> blockMappings;
    private ConcurrentHashMap<DataNodeInfo, Long> dataNodeTimestamps;

    public NameNode() {
        this.config = new Config();
        blockMappings = new ConcurrentHashMap<>();
        dataNodeTimestamps = new ConcurrentHashMap<>();
    }

    public NameNode(int portNum, int blockSize, int replicationFactor, int heartbeatInvtervalMS, String persistFile) {
        this();
        Config config = new Config();
        config.NAME_NODE_PORT = portNum;
        config.BLOCK_SIZE_BYTES = blockSize;
        config.REPLICATION_FACTOR = replicationFactor;
        config.HEARTBEAT_INTERVAL_MS = heartbeatInvtervalMS;
        config.NAME_NODE_METADATA_PERSIST_FILE = persistFile;
    }

    public NameNode(Config config) {
        this();
        this.config = config;
    }

    /**
     * @return all the files current being tracked by NN
     */
    private List<FileMetadata> getCurrentFiles() {
        return blockMappings.keySet().stream().map(s -> blockMappings.get(s).getFileInfo())
                .collect(Collectors.toList());
    }

    /**
     * getter for the NN's blockLocationMappings
     *
     * @param fileName
     * @return blockLocationMapping
     */
    private BlockLocationMapping getBlockLocationMapping(String fileName) {
        return blockMappings.get(fileName);
    }

    /**
     * given a file, retrieve its metadata then return the number of blocks
     * if fileSize % BLOCK_SIZE == 0: then every block will be filled exactly to BLOCK_SIZE
     * else: there will 1 extra block that is not completely filled
     * aka: 0 < len(Nth block) <= BLOCK_SIZE
     *
     * @param fileMetadata
     * @return numberOfBlocks
     */
    private int calculateNumBlocks(FileMetadata fileMetadata) {
        long fileSize = fileMetadata.getSize();
        return (int) Math.ceil((double) fileSize / config.BLOCK_SIZE_BYTES);
    }

    /**
     * takes in a file's metadata then uses a modified version of the apache cassandra token ring
     * hashing algorithm to assign each block to a config.REPLICATION_FACTOR # of nodes
     * then returns them in a blockLocationMapping messsage
     * @param fileMetadata
     * @return blockLocationMapping
     */
    private BlockLocationMapping generateBlockMappings(FileMetadata fileMetadata) {

        int numOfBlocks = calculateNumBlocks(fileMetadata);
        int numOfDNs = dataNodeTimestamps.size();
        List<BlockLocation> blockLocations = Collections.synchronizedList(new ArrayList<>());
        List<DataNodeInfo> currentDataNodes
                = Collections.synchronizedList(new ArrayList<>(dataNodeTimestamps.keySet()));

        // follows a modified version of the apache cassandra token ring hashing algorithm
        // http://cassandra.apache.org/doc/latest/architecture/dynamo.html#consistent-hashing-using-a-token-ring
        // i in loop from 0 to numOfBlocks
        // file  #: range of nodes its on
        // store 0: [0, 1] -> 0 <=  2,  1 <=  2  -> [0, 1]
        // store 1: [1, 2] -> 1 <=  2,  2 <=  2  -> [1, 2]
        // store 2: [2, 3] -> 2 <=  2,  3 \<= 2  -> [2, 1] {2, 3 - 2}, {2 > 1} -> [1, 2]
        // store 3: [3, 4] -> 3 \<= 2,  4 \<= 2  -> [0, 1] {3 - 3, 4 - 3}
        // store 4: [4, 5] -> 4 \<= 2,  5 \<= 2  -> [1, 2] {4 - 3, 5 - 3}
        // store 5: [5, 6] -> 5 \<= 2,  6 \<= 2  -> [2, 1] {5 - 3, 6 - 3

        long bytesRemaining = fileMetadata.getSize();

        int j = 0;
        for (int i = 0; i < numOfBlocks; i++) {
            int k = 0;
            while (k < config.REPLICATION_FACTOR) {

                if (j >= numOfDNs) {
                    j -= numOfDNs;
                }

                // correctly set the last block size
                int thisBlocksSize = config.BLOCK_SIZE_BYTES;

                if (bytesRemaining < config.BLOCK_SIZE_BYTES) {
                    thisBlocksSize = (int) bytesRemaining;
                } else {
                    bytesRemaining -= config.BLOCK_SIZE_BYTES;
                }

                // create the individual 1:1 block mappings
                // store them in a temporary array
                blockLocations.add(BlockLocation
                        .newBuilder()
                        .setDataNodeInfo(currentDataNodes.get(j))
                        .setBlockInfo(BlockMetadata
                                .newBuilder()
                                .setBlockSize(thisBlocksSize)
                                .setIndex(i)
                                .setFileName(fileMetadata.getName())
                                .build())
                        .build());

                k++;
                j++;

            }

        }

        // create a the protobuf BlockLocationMapping from the temp array
        return BlockLocationMapping
                .newBuilder()
                .addAllMapping(blockLocations)
                .setFileInfo(fileMetadata)
                .build();
    }

    /**
     * remove a given data node
     * @param dataNodeInfo
     */
    private void removeDataNode(DataNodeInfo dataNodeInfo) {
        dataNodeTimestamps.remove(dataNodeInfo);
    }

//    private DataNodeInfo getDataNodeInfo(String address) {
//        for (DataNodeInfo info : dataNodeTimestamps.keySet()) {
//            if (info.getIp().equals(address)) {
//                return info;
//            }
//        }
//
//        return null;
//    }

    // no delete or edit operation required yet

    /**
     * write out a file containing all the blocks that the NN is tracking
     * file: the output is a protobuf compiled bin
     */

    private void persistFileInfo() {
        try {
            new File(new File(config.NAME_NODE_METADATA_PERSIST_FILE).getParent()).mkdirs();

            FileOutputStream outputStream = new FileOutputStream(config.NAME_NODE_METADATA_PERSIST_FILE);
            FileList.newBuilder()
                    .addAllFiles(getCurrentFiles())
                    .build()
                    .writeTo(outputStream);

            outputStream.flush();
            outputStream.close();
        } catch(IOException e) {
            logger.log(Level.SEVERE, "unable to persist the list files");
        }
    }

    /**
     * loops through all the block mappings and returns a set with all the block metadata
     * since there will be multiple DNs with the same node, we skip those
     * @param fileName
     * @return blockSize
     */
    private Set<BlockMetadata> getBlocksFromMappings(String fileName) {
        Set<BlockMetadata> seenBlocks = new HashSet<>();

        if (!blockMappings.isEmpty()) {
            for (BlockLocation blockLocation : blockMappings.get(fileName).getMappingList()) {
                seenBlocks.add(blockLocation.getBlockInfo());
            }
        }

        return seenBlocks;
    }

    private int getFileSize(String fileName) {
        int fileSize = 0;
        for (BlockMetadata blockMetadata : getBlocksFromMappings(fileName)) {
            fileSize += blockMetadata.getBlockSize();
        }
        return fileSize;
    }

    /**
     * @param dataNodeInfo
     * @return the set of blocks that we currently know the given DN has
     */
    private Set<BlockMetadata> getBlocksForDN(DataNodeInfo dataNodeInfo) {

        Set<BlockMetadata> out = Collections.synchronizedSet(new HashSet<>());
        if (!blockMappings.isEmpty()) {
            for (BlockLocationMapping blockLocationMapping : blockMappings.values()) {
                for (BlockLocation blockLocation : blockLocationMapping.getMappingList()) {
                    if (blockLocation.getDataNodeInfo().equals(dataNodeInfo)) {
                        out.add(blockLocation.getBlockInfo());
                    }
                }
            }
        }

        return out;
    }

//    /**
//     * @param blockInfo
//     * @return a set of DNs that we currently know contain the given block
//     */
//    private Set<DataNodeInfo> getDNsForBlock(BlockMetadata blockInfo) {
//        Set<DataNodeInfo> out = Collections.synchronizedSet(new HashSet<>());
//        for (BlockLocation blockLocation : blockMappings.get(blockInfo.getFileName()).getMappingList()) {
//            if (blockLocation.getBlockInfo().equals(blockInfo)) {
//                out.add(blockLocation.getDataNodeInfo());
//            }
//        }
//        return out;
//    }

    /**
     * remove all data nodes that haven't responded within HEARTBEAT_INTERVAL_MS
     * this ensures that blocks won't be assigned to these DN's until they have come back online
     */
    private void pruneDataNodes() {
        for (Map.Entry<DataNodeInfo, Long> dataNodeEntry : dataNodeTimestamps.entrySet()) {
            if (System.currentTimeMillis() - dataNodeEntry.getValue() > config.HEARTBEAT_INTERVAL_MS) {
                removeDataNode(dataNodeEntry.getKey());

                logger.log(Level.WARNING, "DataNode at "
                        + dataNodeEntry.getKey().getIp()
                        + ":"
                        + dataNodeEntry.getKey().getPort()
                        + " has not sent a heartbeat within " + config.HEARTBEAT_INTERVAL_MS + "ms"
                        + " and has been marked as offline.");
            }
        }
    }

    //************* NameNode gRPC implementations *******************//

    /**
     * Called by Client to get a list of the current files
     *
     * @param request
     * @param responseObserverWithFileList
     */
    @Override
    public synchronized void listFiles(ListFilesParam request,
                                       StreamObserver<FileList> responseObserverWithFileList) {
        responseObserverWithFileList.onNext(FileList
                .newBuilder()
                .addAllFiles(getCurrentFiles())
                .build());
        responseObserverWithFileList.onCompleted();
    }

    /**
     * Called by DataNode's to report what blocks they have
     * updates our in-memory mapping of blocks & dns
     * @param requestWithBlockReportFromDN
     * @param responseObserverWithStatus
     */
    @Override
    public synchronized void heartBeat(BlockReport requestWithBlockReportFromDN,
                                       StreamObserver<Status> responseObserverWithStatus) {

        List<BlockMetadata> blockReportList = requestWithBlockReportFromDN.getBlocksList();
        Set<BlockMetadata> knownBlocksForDN = getBlocksForDN(requestWithBlockReportFromDN.getDataNodeInfo());

        logger.log(Level.INFO, "got a heartbeat from: "
                + requestWithBlockReportFromDN.getDataNodeInfo().getIp()
                + ":"
                + requestWithBlockReportFromDN.getDataNodeInfo().getPort());

        for (BlockMetadata blockReport : blockReportList) {
            if (!knownBlocksForDN.contains(blockReport)) {
                // we don't have information on this block
                // so add a new block location mapping
                BlockLocation location = BlockLocation
                        .newBuilder()
                        .setBlockInfo(blockReport)
                        .setDataNodeInfo(requestWithBlockReportFromDN.getDataNodeInfo())
                        .build();

                FileMetadata currentFile = FileMetadata.newBuilder()
                        .setName(blockReport.getFileName())
                        .setSize(blockReport.getBlockSize() + getFileSize(blockReport.getFileName()))
                        .build();

                // if we already have a record for the corresponding file, then just update it
                blockMappings.put(blockReport.getFileName(),
                        blockMappings.getOrDefault(blockReport.getFileName(),
                                BlockLocationMapping.getDefaultInstance())
                                .toBuilder()
                                .addMapping(location)
                                .setFileInfo(currentFile)
                                .build());
            }

        }

        responseObserverWithStatus.onNext(Status.newBuilder().setSuccess(true).build());
        responseObserverWithStatus.onCompleted();

        // update the last time heard timestamp for  this DN
        dataNodeTimestamps.put(requestWithBlockReportFromDN.getDataNodeInfo(), System.currentTimeMillis());
        // prune old nodes
        pruneDataNodes();
    }

    /**
     * Called by client to retrieve current known block location mappings
     *
     * @param requestWithFileMetadata
     * @param responseObserverWithBlockLocationMapping
     */
    @Override
    public synchronized void getBlockLocations(FileMetadata requestWithFileMetadata,
                                               StreamObserver<BlockLocationMapping> responseObserverWithBlockLocationMapping) {
        // TODO: print warning when DN's have responded > HEART_BEAT_INTERVAL
        responseObserverWithBlockLocationMapping
                .onNext(getBlockLocationMapping(requestWithFileMetadata.getName()));

        responseObserverWithBlockLocationMapping.onCompleted();
    }

    /**
     * Called by client to assign blocks to data nodes
     */
    @Override
    public synchronized void assignBlocks(FileMetadata requestWithFileMetadata,
                                          StreamObserver<BlockLocationMapping> responseObserverWithBlockLocationMapping) {
        // prune stale data nodes before assigning
        pruneDataNodes();
        // generate the assignment for the file
        BlockLocationMapping blockLocationMapping =
                generateBlockMappings(requestWithFileMetadata);
        // store the assignment metadata
        blockMappings.put(requestWithFileMetadata.getName(), blockLocationMapping);
        // respond with the mapping assignments
        responseObserverWithBlockLocationMapping.onNext(blockLocationMapping);
        responseObserverWithBlockLocationMapping.onCompleted();
        // write out the updated file list: persistFiles()
        persistFileInfo();
    }

    /**
     * Server stuff
     */
    private static final Logger logger = Logger.getLogger(NameNode.class.getName());

    private Server server;

    private void startServer() throws IOException {

        if (server != null) {
            throw new IllegalStateException("Already started");
        }

        server = ServerBuilder.forPort(config.NAME_NODE_PORT)
                .addService(this)
                .build()
                .start();

        logger.info("Server started, listening on " + config.NAME_NODE_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.log(Level.INFO, "*** shutting down gRPC server since JVM is shutting down");
                try {
                    NameNode.this.stopServer();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                logger.log(Level.INFO, "*** server shut down");
            }
        });
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final NameNode nameNodeServer;
        if (args.length == 1) {
            nameNodeServer = new NameNode(Config.readConfig(args[0]));
        } else {
            nameNodeServer = new NameNode();
        }


        nameNodeServer.startServer();
        nameNodeServer.blockUntilShutdown();
    }
}
