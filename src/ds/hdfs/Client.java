package ds.hdfs;

import example.test.TestClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status.Code;
import ds.hdfs.generated.BlockLocationMapping;
import ds.hdfs.generated.FileMetadata;
import ds.hdfs.generated.NameNodeGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private final NameNodeGrpc.NameNodeBlockingStub blockingStub;

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public Client(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = NameNodeGrpc.newBlockingStub(channel);
    }

    private void get(String fileName) {

    }

    private void put(String fileName) {
        FileMetadata request = FileMetadata.newBuilder()
                .setSize(128)
                .setName(fileName)
                .build();

        try {
            BlockLocationMapping response = blockingStub.assignBlocks(request);
            System.out.println(response.getMappingList());
            if (response.getMappingList().size() < 1) {
                throw new RuntimeException("Invalid response");
            }
        } catch (StatusRuntimeException e) {
            System.out.println("err");
        }
    }

    public static void main(String[] args) throws InterruptedException {
// Access a service running on the local machine on port 50051
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
        try {
            Client client = new Client(channel);
            client.put("testtttttkjasldjalksjd");
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
