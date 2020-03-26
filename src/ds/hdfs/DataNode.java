package ds.hdfs;

import ds.hdfs.generated.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class DataNode extends DataNodeGrpc.DataNodeImplBase {
    private static Config config;
    private static BlockStore blockStore;

    private final NameNodeGrpc.NameNodeBlockingStub nameNodeBlockingStub;

    private static final Logger logger = Logger.getLogger(DataNode.class.getName());
    private Server server;

    public DataNode(Channel channel) {
        nameNodeBlockingStub = NameNodeGrpc.newBlockingStub(channel);
        blockStore = new BlockStore("persist/block_store/");
    }

    @java.lang.Override
    public void readBlock(BlockMetadata request, io.grpc.stub.StreamObserver<Block> responseObserver) {
        try {
            responseObserver.onNext(blockStore.getBlock(request));
        } catch (Exception e) {
            System.out.println(e);
        }
        responseObserver.onCompleted();
    }

    @java.lang.Override
    public void writeBlock(Block request, io.grpc.stub.StreamObserver<Status> responseObserver) {
        try {
            blockStore.persistBlock(request);
            responseObserver.onNext(Status.newBuilder().setSuccess(true).build());
        } catch (Exception e) {
            System.out.println(e);
            responseObserver.onNext(Status.newBuilder().setSuccess(false).build());
        }
        responseObserver.onCompleted();
    }

    private void startServer(int port, Channel channel) throws IOException {

        if (server != null) {
            throw new IllegalStateException("Already started");
        }

        server = ServerBuilder.forPort(port)
                .addService(new DataNode(channel))
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

    private void sendHeartBeat() {
        BlockReport report = BlockReport.newBuilder().addAllBlocks(blockStore.getMetaDataList()).build();

        nameNodeBlockingStub.heartBeat(report);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length == 1) {
            config = Config.readConfig(args[0]);
        } else {
            config = new Config();
        }

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(config.NAME_NODE_IP, config.NAME_NODE_PORT)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();

        int port = 9000;
        DataNode dataNodeServer = new DataNode(channel);

        dataNodeServer.startServer(port, channel);
        dataNodeServer.blockUntilShutdown();
    }
}
