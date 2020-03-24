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
public final class DataNodeGrpc {

  private DataNodeGrpc() {}

  public static final String SERVICE_NAME = "ds.hdfs.DataNode";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ds.hdfs.generated.BlockMetadata,
      ds.hdfs.generated.Block> getReadBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readBlock",
      requestType = ds.hdfs.generated.BlockMetadata.class,
      responseType = ds.hdfs.generated.Block.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.hdfs.generated.BlockMetadata,
      ds.hdfs.generated.Block> getReadBlockMethod() {
    io.grpc.MethodDescriptor<ds.hdfs.generated.BlockMetadata, ds.hdfs.generated.Block> getReadBlockMethod;
    if ((getReadBlockMethod = DataNodeGrpc.getReadBlockMethod) == null) {
      synchronized (DataNodeGrpc.class) {
        if ((getReadBlockMethod = DataNodeGrpc.getReadBlockMethod) == null) {
          DataNodeGrpc.getReadBlockMethod = getReadBlockMethod =
              io.grpc.MethodDescriptor.<ds.hdfs.generated.BlockMetadata, ds.hdfs.generated.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.BlockMetadata.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.Block.getDefaultInstance()))
              .setSchemaDescriptor(new DataNodeMethodDescriptorSupplier("readBlock"))
              .build();
        }
      }
    }
    return getReadBlockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ds.hdfs.generated.Block,
      ds.hdfs.generated.Status> getWriteBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "writeBlock",
      requestType = ds.hdfs.generated.Block.class,
      responseType = ds.hdfs.generated.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.hdfs.generated.Block,
      ds.hdfs.generated.Status> getWriteBlockMethod() {
    io.grpc.MethodDescriptor<ds.hdfs.generated.Block, ds.hdfs.generated.Status> getWriteBlockMethod;
    if ((getWriteBlockMethod = DataNodeGrpc.getWriteBlockMethod) == null) {
      synchronized (DataNodeGrpc.class) {
        if ((getWriteBlockMethod = DataNodeGrpc.getWriteBlockMethod) == null) {
          DataNodeGrpc.getWriteBlockMethod = getWriteBlockMethod =
              io.grpc.MethodDescriptor.<ds.hdfs.generated.Block, ds.hdfs.generated.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "writeBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.Block.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.hdfs.generated.Status.getDefaultInstance()))
              .setSchemaDescriptor(new DataNodeMethodDescriptorSupplier("writeBlock"))
              .build();
        }
      }
    }
    return getWriteBlockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataNodeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeStub>() {
        @java.lang.Override
        public DataNodeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeStub(channel, callOptions);
        }
      };
    return DataNodeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataNodeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeBlockingStub>() {
        @java.lang.Override
        public DataNodeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeBlockingStub(channel, callOptions);
        }
      };
    return DataNodeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataNodeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataNodeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataNodeFutureStub>() {
        @java.lang.Override
        public DataNodeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataNodeFutureStub(channel, callOptions);
        }
      };
    return DataNodeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DataNodeImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void readBlock(ds.hdfs.generated.BlockMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getReadBlockMethod(), responseObserver);
    }

    /**
     */
    public void writeBlock(ds.hdfs.generated.Block request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getWriteBlockMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReadBlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.hdfs.generated.BlockMetadata,
                ds.hdfs.generated.Block>(
                  this, METHODID_READ_BLOCK)))
          .addMethod(
            getWriteBlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.hdfs.generated.Block,
                ds.hdfs.generated.Status>(
                  this, METHODID_WRITE_BLOCK)))
          .build();
    }
  }

  /**
   */
  public static final class DataNodeStub extends io.grpc.stub.AbstractAsyncStub<DataNodeStub> {
    private DataNodeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public void readBlock(ds.hdfs.generated.BlockMetadata request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void writeBlock(ds.hdfs.generated.Block request,
        io.grpc.stub.StreamObserver<ds.hdfs.generated.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getWriteBlockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DataNodeBlockingStub extends io.grpc.stub.AbstractBlockingStub<DataNodeBlockingStub> {
    private DataNodeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public ds.hdfs.generated.Block readBlock(ds.hdfs.generated.BlockMetadata request) {
      return blockingUnaryCall(
          getChannel(), getReadBlockMethod(), getCallOptions(), request);
    }

    /**
     */
    public ds.hdfs.generated.Status writeBlock(ds.hdfs.generated.Block request) {
      return blockingUnaryCall(
          getChannel(), getWriteBlockMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DataNodeFutureStub extends io.grpc.stub.AbstractFutureStub<DataNodeFutureStub> {
    private DataNodeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataNodeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataNodeFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * used by the Client
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.hdfs.generated.Block> readBlock(
        ds.hdfs.generated.BlockMetadata request) {
      return futureUnaryCall(
          getChannel().newCall(getReadBlockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.hdfs.generated.Status> writeBlock(
        ds.hdfs.generated.Block request) {
      return futureUnaryCall(
          getChannel().newCall(getWriteBlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_READ_BLOCK = 0;
  private static final int METHODID_WRITE_BLOCK = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DataNodeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DataNodeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_READ_BLOCK:
          serviceImpl.readBlock((ds.hdfs.generated.BlockMetadata) request,
              (io.grpc.stub.StreamObserver<ds.hdfs.generated.Block>) responseObserver);
          break;
        case METHODID_WRITE_BLOCK:
          serviceImpl.writeBlock((ds.hdfs.generated.Block) request,
              (io.grpc.stub.StreamObserver<ds.hdfs.generated.Status>) responseObserver);
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

  private static abstract class DataNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DataNodeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ds.hdfs.generated.HDFSFormat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DataNode");
    }
  }

  private static final class DataNodeFileDescriptorSupplier
      extends DataNodeBaseDescriptorSupplier {
    DataNodeFileDescriptorSupplier() {}
  }

  private static final class DataNodeMethodDescriptorSupplier
      extends DataNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DataNodeMethodDescriptorSupplier(String methodName) {
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
      synchronized (DataNodeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DataNodeFileDescriptorSupplier())
              .addMethod(getReadBlockMethod())
              .addMethod(getWriteBlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
