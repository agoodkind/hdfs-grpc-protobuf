package ds.hdfs.test;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import ds.hdfs.test.TestRequest;
import ds.hdfs.test.TestReply;

import java.util.logging.Logger;

public class TestClient {
    private static final Logger logger = Logger.getLogger(TestClient.class.getName());
    private final TestService.Stub blockingStub;

    /** Construct client for accessing HelloWorld server using the existing channel. */
    public TestClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = TestService.newBlockingStub(channel);
    }


    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        TestRequest request = TestRequest.newBuilder().setTestRequestField(name).build();
        TestReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
        try {
            response = blockingStub.sayHelloAgain(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }
}
