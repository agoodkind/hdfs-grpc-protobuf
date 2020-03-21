package ds.hdfs;

import ds.hdfs.generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NameNode extends NameNodeGrpc.NameNodeImplBase {

    private static int BLOCK_SIZE;
    private static int PORT_NUM;
    private static int REPLICATION_FACTOR = 2;
    private ConcurrentHashMap<String, FileMetadata> fileNameFileMetadataMap;
    private ConcurrentHashMap<String, BlockLocationMapping> fileNameBlockLocationMappingMap;
    private List<DataNodeInfo> dataNodeList;

    public NameNode(int portNum, int blockSize) {
        BLOCK_SIZE = blockSize;
        PORT_NUM = portNum;
        fileNameFileMetadataMap = new ConcurrentHashMap<>();
        fileNameBlockLocationMappingMap = new ConcurrentHashMap<>();
        dataNodeList = Collections.synchronizedList(new ArrayList<>());

        // TODO: remove debug data
        dataNodeList.add(DataNodeInfo
                .newBuilder()
                .setIp("69.69.69.69")
                .setPort(69420)
                .build());
    }

    /**
     * for a given filename return its a metadata
     * @param fileName
     * @return fileMetadata
     */
    private FileMetadata getFileMetadata(String fileName) {
        return fileNameFileMetadataMap.get(fileName);
    }

    /**
     * @return the list of files that the NN server currently is tracking
     */
    private ArrayList<String> getListOfFiles() {
        return new ArrayList<>(fileNameFileMetadataMap.keySet());
    }

    /**
     * getter for the NN's blockLocationMappings
     * @param fileName
     * @return blockLocationMapping
     */
    private BlockLocationMapping getBlockLocationMapping(String fileName) {
        return fileNameBlockLocationMappingMap.get(fileName);
    }

    /**
     * given a file, retrieve its metadata then return the number of blocks
     * if fileSize % BLOCK_SIZE == 0: then every block will be filled exactly to BLOCK_SIZE
     * else: there will 1 extra block that is not completely filled
     * @param fileName
     * @return numberOfBlocks
     */
    private int calculateNumberOfBlocksForFile(String fileName) {
        int fileSize = getFileMetadata(fileName).getSize();
        return (int) Math.ceil((double) fileSize / BLOCK_SIZE);
    }

    private BlockLocationMapping generateBlockLocationMappingAssignmentsForFile(String fileName) {
        FileMetadata fileMetadata = getFileMetadata(fileName);
        int numOfBlocks = calculateNumberOfBlocksForFile(fileName);
        int numOfDNs = dataNodeList.size();
        List<BlockLocation> blockLocations = Collections.synchronizedList(new ArrayList<>());

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
        int j = 0;
        for (int i = 0; i < numOfBlocks; i++) {
            int k = 0;
            while (k < REPLICATION_FACTOR) {

                if (j >= numOfDNs) {
                    j -= numOfDNs;
                }

                // create the individual 1:1 block mappings
                // store them in a temporary array
                blockLocations.add(BlockLocation
                        .newBuilder()
                        .setDataNodeInfo(dataNodeList.get(j))
                        .setBlockInfo(BlockMetadata
                                .newBuilder()
                                .setBlockSize(BLOCK_SIZE)
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
                .build();
    }

    private void addDataNode(DataNodeInfo dataNodeInfo) {
        dataNodeList.add(dataNodeInfo);
    }

    private DataNodeInfo getDataNodeInfo(String address) {
        for (DataNodeInfo info : dataNodeList) {
            if (info.getIp().equals(address)) {
                return info;
            }
        }

        return null;
    }

    // no delete or edit operation required yet

    /**
     * TODO: write out all the files and their info to a persisted place, this method should be called in some interval
     */
    private void persistFileInfo() {

    }


    /**
     * NameNode gRPC implementations
     */

    /**
     * TODO: block reports impl
     */
    @Override
    public synchronized void heartBeat(BlockReport requestWithBlockReportFromDN,
                                       StreamObserver<Status> responseObserverWithStatus) {

    }


    @Override
    public synchronized void getBlockLocations(FileMetadata requestWithFileMetadata,
                                               StreamObserver<BlockLocationMapping> responseObserverWithBlockLocationMapping) {

        responseObserverWithBlockLocationMapping
                .onNext(getBlockLocationMapping(requestWithFileMetadata.getName()));

        responseObserverWithBlockLocationMapping.onCompleted();
    }


    /**
     * logic to assign blocks to data nodes
     */
    @Override
    public synchronized void assignBlocks(FileMetadata requestWithFileMetadata,
                                          StreamObserver<BlockLocationMapping> responseObserverWithBlockLocationMapping) {

        String fileName = requestWithFileMetadata.getName();
        // store the file metadata
        fileNameFileMetadataMap.put(fileName, requestWithFileMetadata);
        // generate the assignment for the file
        BlockLocationMapping blockLocationMapping =
                generateBlockLocationMappingAssignmentsForFile(fileName);
        // store the assignment
        fileNameBlockLocationMappingMap.put(fileName, blockLocationMapping);
        // send the assignment mapping to the client
        responseObserverWithBlockLocationMapping.onNext(blockLocationMapping);
        //
        responseObserverWithBlockLocationMapping.onCompleted();
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

        server = ServerBuilder.forPort(PORT_NUM)
                .addService(new NameNode(PORT_NUM, BLOCK_SIZE))
                .build()
                .start();

        logger.info("Server started, listening on " + PORT_NUM);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    NameNode.this.stopServer();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
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
        // read in the two config files
        // read in persisted file info
        // we need port
        // and block size
//        String config = args[0 or 1 i cant remember];
        int blockSize = 64; // bytes
        int portNum = 50051;

        final NameNode nameNodeServer = new NameNode(portNum, blockSize);
        // TEST:



        nameNodeServer.startServer();

        nameNodeServer.blockUntilShutdown();
    }
}
