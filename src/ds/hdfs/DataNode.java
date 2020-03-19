package ds.hdfs;

import ds.hdfs.generated.Block;
import ds.hdfs.generated.BlockMetadata;
import ds.hdfs.generated.DataNodeGrpc;
import ds.hdfs.generated.Status;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.io.IOException;

public class DataNode extends DataNodeGrpc.DataNodeImplBase {

    /**
     * DataNode implementation
     */

    @java.lang.Override
    public void readBlock(BlockMetadata request, io.grpc.stub.StreamObserver<Status> responseObserver) {

    }

    @java.lang.Override
    public void writeBlock(Block request, io.grpc.stub.StreamObserver<Status> responseObserver) {

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
                .addService(new DataNode())
                .build()
                .start();

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    DataNode.this.stopServer();
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
        int port = 9000;
        final DataNode dnServer = new DataNode();
        dnServer.startServer(port);
    }
}
