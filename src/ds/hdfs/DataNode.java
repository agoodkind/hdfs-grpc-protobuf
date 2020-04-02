package ds.hdfs;

import ds.hdfs.generated.Status;
import ds.hdfs.generated.*;
import io.grpc.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataNode extends DataNodeGrpc.DataNodeImplBase {

    private Config config;
    private int PORT = 9000;

    private BlockStore blockStore;

    private final NameNodeGrpc.NameNodeBlockingStub nameNodeBlockingStub;

    private static final Logger logger = Logger.getLogger(DataNode.class.getName());
    private Server server;

    public DataNode(Channel channel, int port, Config config) {
        this.config  = config;
        this.PORT = port;
        nameNodeBlockingStub = NameNodeGrpc.newBlockingStub(channel);
        blockStore = new BlockStore(config.DATA_NODE_BLOCK_STORE_PATH);
    }

    @java.lang.Override
    public void readBlock(BlockMetadata request, io.grpc.stub.StreamObserver<Block> responseObserver) {
        logger.log(Level.INFO, "Client requested block: "
                    + request.getFileName() + "_"
                    + request.getIndex());
        Block block = blockStore.getBlock(request);

        try {
            responseObserver.onNext(blockStore.getBlock(request));
            logger.log(Level.INFO, "Sent client block: "
                    + request.getFileName() + "_"
                    + request.getIndex());
        } catch (NoSuchElementException e) {
            logger.log(Level.WARNING, "Unable to retrieve block from block store", e);
        }
        responseObserver.onCompleted();
    }

    @java.lang.Override
    public void writeBlock(Block request, io.grpc.stub.StreamObserver<Status> responseObserver) {
        logger.log(Level.INFO, "Received block from client: "
                + request.getBlockInfo().getFileName() + "_"
                + request.getBlockInfo().getIndex());

        try {
            blockStore.persistBlock(request);
            responseObserver.onNext(Status.newBuilder().setSuccess(true).build());
            logger.log(Level.INFO, "Successfully persisted block: "
                    + request.getBlockInfo().getFileName() + "_"
                    + request.getBlockInfo().getIndex());
        } catch (IOException e) {
            responseObserver.onNext(Status.newBuilder().setSuccess(false).build());
            logger.log(Level.INFO, "Failed to persisted block: "
                    + request.getBlockInfo().getFileName() + "_"
                    + request.getBlockInfo().getIndex(), e);
        }
        responseObserver.onCompleted();
    }

    private void startServer() throws IOException {

        if (server != null) {
            throw new IllegalStateException("Already started");
        }

        server = ServerBuilder.forPort(this.PORT)
                .addService(this)
                .build()
                .start();

        // reset port just in case 0
        if (this.PORT == 0) {
            this.PORT = server.getPort();
        }

        logger.info("Server started, listening on "
                + InetAddress.getLocalHost().getHostAddress()
                + ":"
                + this.PORT);

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
            DataNodeInfo info = DataNodeInfo.newBuilder()
                    .setIp(ip)
                    .setPort(this.server.getPort()).build();

            BlockReport report = BlockReport.newBuilder()
                    .addAllBlocks(blockStore.getMetaDataList())
                    .setDataNodeInfo(info).build();
            nameNodeBlockingStub.heartBeat(report);
            logger.info("sent heartbeat");
        } catch (UnknownHostException e) {
            // TODO handle error
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 0;
        final Config config;

        if (args.length > 0) {
            if (args[0].equals("help")) {
                System.err.println("Usage: [configFile]");
                System.err.println("");
                System.err.println("\thelp                    \tDisplay this message.");
                System.exit(1);
            }

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

        DataNode dataNodeServer = new DataNode(channel, port, config);
        dataNodeServer.startServer();

        Thread heartBeatThread = new Thread(() -> {
            class SendHeartBeat extends TimerTask {
                public void run() {
                    try {
                        dataNodeServer.beat();
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Cannot connect to NameNode, unable to send heartbeat");
                    }
                }
            }

            Timer timer = new Timer();
            timer.schedule(new SendHeartBeat(), 0, config.HEARTBEAT_INTERVAL_MS);
        });
        heartBeatThread.start();

        dataNodeServer.blockUntilShutdown();
    }
}
