package ds.hdfs.test;

import io.grpc.stub.StreamObserver;
import ds.hdfs.test.TestRequest;
import ds.hdfs.test.TestReply;

public class TestServer {
    public void testServerMethedOne(TestRequest req, StreamObserver<TestReply> responseObserver) {
        TestReply reply = TestReply.newBuilder().setTestReplyField("Hello " + req.getTestRequestField()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
