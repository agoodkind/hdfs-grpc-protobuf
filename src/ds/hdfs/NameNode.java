package ds.hdfs;

import ds.hdfs.generated.BlockLocationMapping;
import ds.hdfs.generated.FileMetadata;
import ds.hdfs.generated.NameNodeGrpc;
import ds.hdfs.test.TestServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NameNode extends NameNodeGrpc.NameNodeImplBase {
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

    private void startServer(int port) throws IOException {

        if (server != null) {
            throw new IllegalStateException("Already started");
        }

        server = ServerBuilder.forPort(port)
                .addService(new NameNode())
                .build()
                .start();

        logger.info("Server started, listening on " + port);
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
        // read in config file
        // we need port
//        String config = args[0 or 1 i cant remember];
        int port = 50051;
        final NameNode nameNodeServer = new NameNode();
        nameNodeServer.startServer(port);
    }
}
