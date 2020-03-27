package ds.hdfs;

import ds.hdfs.generated.Status;
import ds.hdfs.generated.*;
import io.grpc.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DataNode extends DataNodeGrpc.DataNodeImplBase {

    private static Config config;
    private static int PORT = 9000;

    private BlockStore blockStore;

    private final NameNodeGrpc.NameNodeBlockingStub nameNodeBlockingStub;

    private static final Logger logger = Logger.getLogger(DataNode.class.getName());
    private Server server;

    public DataNode(Channel channel) {
        nameNodeBlockingStub = NameNodeGrpc.newBlockingStub(channel);
        blockStore = new BlockStore(config.DATA_NODE_BLOCK_STORE_PATH);
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
        } catch (IOException e) {
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
                .addService(this)
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

    private void beat() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress().toString();
            DataNodeInfo info = DataNodeInfo.newBuilder().setIp(ip).setPort(PORT).build();
            BlockReport report = BlockReport.newBuilder().addAllBlocks(blockStore.getMetaDataList()).setDataNodeInfo(info).build();
            nameNodeBlockingStub.heartBeat(report);
            System.out.println("sent heart beat");
        } catch (UnknownHostException e) {
            // TODO handle error
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: ./run.sh datanode <port> [configFile]");
            System.exit(1);
        } else {
            if(args.length == 2) {
                config = Config.readConfig(args[1]);
            } else {
                config = new Config();
            }
            PORT = Integer.parseInt(args[0]);

            // Create a communication channel to the server, known as a Channel. Channels are thread-safe
            // and reusable. It is common to create channels at the beginning of your application and reuse
            // them until the application shuts down.
            ManagedChannel channel = ManagedChannelBuilder.forAddress(config.NAME_NODE_IP, config.NAME_NODE_PORT)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();

            DataNode dataNodeServer = new DataNode(channel);

            Thread heartBeatThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    class SendHeartBeat extends TimerTask {
                        public void run() {
                            try {
                                dataNodeServer.beat();
                            } catch (Exception e) {
                                System.out.println("Cannot connect to NameNode");
                                System.out.println("unable to send beat");
                            }
                        }
                    }

                    Timer timer = new Timer();
                    timer.schedule(new SendHeartBeat(), 0, 5000);
                }
            });

            heartBeatThread.start();

            System.out.println("debug: ip: " + InetAddress.getLocalHost().getHostAddress().toString());

            dataNodeServer.startServer(PORT, channel);
            dataNodeServer.blockUntilShutdown();
        }
    }
}
