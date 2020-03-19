package ds.hdfs;

import ds.hdfs.generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class NameNode extends NameNodeGrpc.NameNodeImplBase {

    private static int BLOCK_SIZE;
    private static int PORT_NUM;

    public NameNode(int portNum, int blockSize) {
        this.BLOCK_SIZE = blockSize;
        this.PORT_NUM = portNum;
    }

    class SingleFile {
        /**
         * a mapping of mappings.....just think about it
         * maps metadata about a file to the list of mappings for its blocks
         */
        private FileMetadata fileMetadata;
        private BlockMetadata[] blockList;
        private int numBlocks;


        public SingleFile(FileMetadata fileMetadata) {
            this.fileMetadata = fileMetadata;
            this.numBlocks =  (int) Math.ceil((double) fileMetadata.getSize() / BLOCK_SIZE);
            this.blockList = new BlockMetadata[numBlocks];
        }

        public void addBlock(BlockMetadata blockMetadata) {
            this.blockList[blockMetadata.getIndex()] = blockMetadata;
        }



    }


    /**
     * NameNode implementation
     */
    @Override
    public synchronized void assignBlocks(FileMetadata requestWithFileMetadata,
                                          StreamObserver<BlockLocationMapping> responseObserverWithBlockLocationMapping) {

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

    private void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws IOException {
        // read in the two config files
        // we need port
        // and block size
//        String config = args[0 or 1 i cant remember];
        int blockSize = 64; // bytes
        int portNum = 50051;

        final NameNode nameNodeServer = new NameNode(portNum, blockSize);
        nameNodeServer.startServer();
    }
}
