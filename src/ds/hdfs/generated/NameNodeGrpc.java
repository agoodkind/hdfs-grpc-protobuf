package ds.hdfs.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.0)",
    comments = "Source: hdfsformat.proto")
public final class NameNodeGrpc {

  private NameNodeGrpc() {}

  public static final String SERVICE_NAME = "ds.hdfs.NameNode";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ds.hdfs.generated.BlockReport,
      ds.hdfs.generated.Status> getHeartBeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "heartBeat",
      requestType = ds.hdfs.generated.BlockReport.class,
      responseType = ds.hdfs.generated.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.hdfs.generated.BlockReport,
      ds.hdfs.generated.Status> getHeartBeatMethod() {
    io.grpc.MethodDescriptor<ds.hdfs.generated.BlockReport, ds.hdfs.generated.Status> getHeartBeatMethod;
    if ((getHeartBeatMethod = NameNodeGrpc.getHeartBeatMethod) == null) {
      synchronized (NameNodeGrpc.class) {
        if ((getHeartBeatMethod = NameNodeGrpc.getHeartBeatMethod) == null) {
          NameNodeGrpc.getHeartBeatMethod = getHeartBeatMethod =
              io.grpc.MethodDescriptor.<ds.hdfs.generated.BlockReport, ds.hdfs.generated.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "heartBeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.BlockReport.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.Status.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeMethodDescriptorSupplier("heartBeat"))
              .build();
        }
      }
    }
    return getHeartBeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata,
      ds.hdfs.generated.BlockLocationMapping> getAssignBlocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "assignBlocks",
      requestType = ds.hdfs.generated.FileMetadata.class,
      responseType = ds.hdfs.generated.BlockLocationMapping.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata,
      ds.hdfs.generated.BlockLocationMapping> getAssignBlocksMethod() {
    io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata, ds.hdfs.generated.BlockLocationMapping> getAssignBlocksMethod;
    if ((getAssignBlocksMethod = NameNodeGrpc.getAssignBlocksMethod) == null) {
      synchronized (NameNodeGrpc.class) {
        if ((getAssignBlocksMethod = NameNodeGrpc.getAssignBlocksMethod) == null) {
          NameNodeGrpc.getAssignBlocksMethod = getAssignBlocksMethod =
              io.grpc.MethodDescriptor.<ds.hdfs.generated.FileMetadata, ds.hdfs.generated.BlockLocationMapping>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "assignBlocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.FileMetadata.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.BlockLocationMapping.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeMethodDescriptorSupplier("assignBlocks"))
              .build();
        }
      }
    }
    return getAssignBlocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata,
      ds.hdfs.generated.BlockLocationMapping> getGetBlockLocationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBlockLocations",
      requestType = ds.hdfs.generated.FileMetadata.class,
      responseType = ds.hdfs.generated.BlockLocationMapping.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata,
      ds.hdfs.generated.BlockLocationMapping> getGetBlockLocationsMethod() {
    io.grpc.MethodDescriptor<ds.hdfs.generated.FileMetadata, ds.hdfs.generated.BlockLocationMapping> getGetBlockLocationsMethod;
    if ((getGetBlockLocationsMethod = NameNodeGrpc.getGetBlockLocationsMethod) == null) {
      synchronized (NameNodeGrpc.class) {
        if ((getGetBlockLocationsMethod = NameNodeGrpc.getGetBlockLocationsMethod) == null) {
          NameNodeGrpc.getGetBlockLocationsMethod = getGetBlockLocationsMethod =
              io.grpc.MethodDescriptor.<ds.hdfs.generated.FileMetadata, ds.hdfs.generated.BlockLocationMapping>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getBlockLocations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.FileMetadata.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.BlockLocationMapping.getDefaultInstance()))
              .setSchemaDescriptor(new NameNodeMethodDescriptorSupplier("getBlockLocations"))
              .build();
        }
      }
    }
    return getGetBlockLocationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NameNodeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeStub>() {
        @java.lang.Override
        public NameNodeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeStub(channel, callOptions);
        }
      };
    return NameNodeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NameNodeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeBlockingStub>() {
        @java.lang.Override
        public NameNodeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeBlockingStub(channel, callOptions);
        }
      };
    return NameNodeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NameNodeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NameNodeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NameNodeFutureStub>() {
        @java.lang.Override
        public NameNodeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NameNodeFutureStub(channel, callOptions);
        }
      };
    return NameNodeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class NameNodeImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * used by the DataNodes
     * </pre>
     */
    public void heartBeat(ds.hdfs.generated.BlockReport request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getHeartBeatMethod(), responseObserver);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void assignBlocks(ds.hdfs.generated.FileMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping> responseObserver) {
      asyncUnimplementedUnaryCall(getAssignBlocksMethod(), responseObserver);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void getBlockLocations(ds.hdfs.generated.FileMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockLocationsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHeartBeatMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.hdfs.generated.BlockReport,
                ds.hdfs.generated.Status>(
                  this, METHODID_HEART_BEAT)))
          .addMethod(
            getAssignBlocksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.hdfs.generated.FileMetadata,
                ds.hdfs.generated.BlockLocationMapping>(
                  this, METHODID_ASSIGN_BLOCKS)))
          .addMethod(
            getGetBlockLocationsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.hdfs.generated.FileMetadata,
                ds.hdfs.generated.BlockLocationMapping>(
                  this, METHODID_GET_BLOCK_LOCATIONS)))
          .build();
    }
  }

  /**
   */
  public static final class NameNodeStub extends io.grpc.stub.AbstractAsyncStub<NameNodeStub> {
    private NameNodeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the DataNodes
     * </pre>
     */
    public void heartBeat(ds.hdfs.generated.BlockReport request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHeartBeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void assignBlocks(ds.hdfs.generated.FileMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssignBlocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void getBlockLocations(ds.hdfs.generated.FileMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockLocationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NameNodeBlockingStub extends io.grpc.stub.AbstractBlockingStub<NameNodeBlockingStub> {
    private NameNodeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the DataNodes
     * </pre>
     */
    public ds.hdfs.generated.Status heartBeat(ds.hdfs.generated.BlockReport request) {
      return blockingUnaryCall(
          getChannel(), getHeartBeatMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public ds.hdfs.generated.BlockLocationMapping assignBlocks(ds.hdfs.generated.FileMetadata request) {
      return blockingUnaryCall(
          getChannel(), getAssignBlocksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public ds.hdfs.generated.BlockLocationMapping getBlockLocations(ds.hdfs.generated.FileMetadata request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockLocationsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NameNodeFutureStub extends io.grpc.stub.AbstractFutureStub<NameNodeFutureStub> {
    private NameNodeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NameNodeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NameNodeFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the DataNodes
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.hdfs.generated.Status> heartBeat(
        ds.hdfs.generated.BlockReport request) {
      return futureUnaryCall(
          getChannel().newCall(getHeartBeatMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.hdfs.generated.BlockLocationMapping> assignBlocks(
        ds.hdfs.generated.FileMetadata request) {
      return futureUnaryCall(
          getChannel().newCall(getAssignBlocksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.hdfs.generated.BlockLocationMapping> getBlockLocations(
        ds.hdfs.generated.FileMetadata request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockLocationsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HEART_BEAT = 0;
  private static final int METHODID_ASSIGN_BLOCKS = 1;
  private static final int METHODID_GET_BLOCK_LOCATIONS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NameNodeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NameNodeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HEART_BEAT:
          serviceImpl.heartBeat((ds.hdfs.generated.BlockReport) request,
              (io.grpc.stub.StreamObserver<ds.hdfs.generated.Status>) responseObserver);
          break;
        case METHODID_ASSIGN_BLOCKS:
          serviceImpl.assignBlocks((ds.hdfs.generated.FileMetadata) request,
              (io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping>) responseObserver);
          break;
        case METHODID_GET_BLOCK_LOCATIONS:
          serviceImpl.getBlockLocations((ds.hdfs.generated.FileMetadata) request,
              (io.grpc.stub.StreamObserver<ds.hdfs.generated.BlockLocationMapping>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class NameNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NameNodeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ds.hdfs.generated.HDFSFormat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NameNode");
    }
  }

  private static final class NameNodeFileDescriptorSupplier
      extends NameNodeBaseDescriptorSupplier {
    NameNodeFileDescriptorSupplier() {}
  }

  private static final class NameNodeMethodDescriptorSupplier
      extends NameNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NameNodeMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NameNodeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NameNodeFileDescriptorSupplier())
              .addMethod(getHeartBeatMethod())
              .addMethod(getAssignBlocksMethod())
              .addMethod(getGetBlockLocationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
