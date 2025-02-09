// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hdfsformat.proto

package ds.hdfs.generated;

/**
 * Protobuf type {@code ds.hdfs.BlockReport}
 */
public  final class BlockReport extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ds.hdfs.BlockReport)
    BlockReportOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BlockReport.newBuilder() to construct.
  private BlockReport(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BlockReport() {
    blocks_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BlockReport();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BlockReport(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            ds.hdfs.generated.DataNodeInfo.Builder subBuilder = null;
            if (dataNodeInfo_ != null) {
              subBuilder = dataNodeInfo_.toBuilder();
            }
            dataNodeInfo_ = input.readMessage(ds.hdfs.generated.DataNodeInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(dataNodeInfo_);
              dataNodeInfo_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              blocks_ = new java.util.ArrayList<ds.hdfs.generated.BlockMetadata>();
              mutable_bitField0_ |= 0x00000001;
            }
            blocks_.add(
                input.readMessage(ds.hdfs.generated.BlockMetadata.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        blocks_ = java.util.Collections.unmodifiableList(blocks_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockReport_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockReport_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ds.hdfs.generated.BlockReport.class, ds.hdfs.generated.BlockReport.Builder.class);
  }

  public static final int DATA_NODE_INFO_FIELD_NUMBER = 1;
  private ds.hdfs.generated.DataNodeInfo dataNodeInfo_;
  /**
   * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
   * @return Whether the dataNodeInfo field is set.
   */
  public boolean hasDataNodeInfo() {
    return dataNodeInfo_ != null;
  }
  /**
   * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
   * @return The dataNodeInfo.
   */
  public ds.hdfs.generated.DataNodeInfo getDataNodeInfo() {
    return dataNodeInfo_ == null ? ds.hdfs.generated.DataNodeInfo.getDefaultInstance() : dataNodeInfo_;
  }
  /**
   * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
   */
  public ds.hdfs.generated.DataNodeInfoOrBuilder getDataNodeInfoOrBuilder() {
    return getDataNodeInfo();
  }

  public static final int BLOCKS_FIELD_NUMBER = 2;
  private java.util.List<ds.hdfs.generated.BlockMetadata> blocks_;
  /**
   * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
   */
  public java.util.List<ds.hdfs.generated.BlockMetadata> getBlocksList() {
    return blocks_;
  }
  /**
   * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
   */
  public java.util.List<? extends ds.hdfs.generated.BlockMetadataOrBuilder> 
      getBlocksOrBuilderList() {
    return blocks_;
  }
  /**
   * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
   */
  public int getBlocksCount() {
    return blocks_.size();
  }
  /**
   * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
   */
  public ds.hdfs.generated.BlockMetadata getBlocks(int index) {
    return blocks_.get(index);
  }
  /**
   * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
   */
  public ds.hdfs.generated.BlockMetadataOrBuilder getBlocksOrBuilder(
      int index) {
    return blocks_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (dataNodeInfo_ != null) {
      output.writeMessage(1, getDataNodeInfo());
    }
    for (int i = 0; i < blocks_.size(); i++) {
      output.writeMessage(2, blocks_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (dataNodeInfo_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getDataNodeInfo());
    }
    for (int i = 0; i < blocks_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, blocks_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ds.hdfs.generated.BlockReport)) {
      return super.equals(obj);
    }
    ds.hdfs.generated.BlockReport other = (ds.hdfs.generated.BlockReport) obj;

    if (hasDataNodeInfo() != other.hasDataNodeInfo()) return false;
    if (hasDataNodeInfo()) {
      if (!getDataNodeInfo()
          .equals(other.getDataNodeInfo())) return false;
    }
    if (!getBlocksList()
        .equals(other.getBlocksList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasDataNodeInfo()) {
      hash = (37 * hash) + DATA_NODE_INFO_FIELD_NUMBER;
      hash = (53 * hash) + getDataNodeInfo().hashCode();
    }
    if (getBlocksCount() > 0) {
      hash = (37 * hash) + BLOCKS_FIELD_NUMBER;
      hash = (53 * hash) + getBlocksList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ds.hdfs.generated.BlockReport parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockReport parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockReport parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockReport parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ds.hdfs.generated.BlockReport prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ds.hdfs.BlockReport}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ds.hdfs.BlockReport)
      ds.hdfs.generated.BlockReportOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockReport_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockReport_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ds.hdfs.generated.BlockReport.class, ds.hdfs.generated.BlockReport.Builder.class);
    }

    // Construct using ds.hdfs.generated.BlockReport.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getBlocksFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (dataNodeInfoBuilder_ == null) {
        dataNodeInfo_ = null;
      } else {
        dataNodeInfo_ = null;
        dataNodeInfoBuilder_ = null;
      }
      if (blocksBuilder_ == null) {
        blocks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        blocksBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockReport_descriptor;
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockReport getDefaultInstanceForType() {
      return ds.hdfs.generated.BlockReport.getDefaultInstance();
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockReport build() {
      ds.hdfs.generated.BlockReport result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockReport buildPartial() {
      ds.hdfs.generated.BlockReport result = new ds.hdfs.generated.BlockReport(this);
      int from_bitField0_ = bitField0_;
      if (dataNodeInfoBuilder_ == null) {
        result.dataNodeInfo_ = dataNodeInfo_;
      } else {
        result.dataNodeInfo_ = dataNodeInfoBuilder_.build();
      }
      if (blocksBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          blocks_ = java.util.Collections.unmodifiableList(blocks_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.blocks_ = blocks_;
      } else {
        result.blocks_ = blocksBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ds.hdfs.generated.BlockReport) {
        return mergeFrom((ds.hdfs.generated.BlockReport)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ds.hdfs.generated.BlockReport other) {
      if (other == ds.hdfs.generated.BlockReport.getDefaultInstance()) return this;
      if (other.hasDataNodeInfo()) {
        mergeDataNodeInfo(other.getDataNodeInfo());
      }
      if (blocksBuilder_ == null) {
        if (!other.blocks_.isEmpty()) {
          if (blocks_.isEmpty()) {
            blocks_ = other.blocks_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBlocksIsMutable();
            blocks_.addAll(other.blocks_);
          }
          onChanged();
        }
      } else {
        if (!other.blocks_.isEmpty()) {
          if (blocksBuilder_.isEmpty()) {
            blocksBuilder_.dispose();
            blocksBuilder_ = null;
            blocks_ = other.blocks_;
            bitField0_ = (bitField0_ & ~0x00000001);
            blocksBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBlocksFieldBuilder() : null;
          } else {
            blocksBuilder_.addAllMessages(other.blocks_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      ds.hdfs.generated.BlockReport parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ds.hdfs.generated.BlockReport) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private ds.hdfs.generated.DataNodeInfo dataNodeInfo_;
    private com.google.protobuf.SingleFieldBuilderV3<
        ds.hdfs.generated.DataNodeInfo, ds.hdfs.generated.DataNodeInfo.Builder, ds.hdfs.generated.DataNodeInfoOrBuilder> dataNodeInfoBuilder_;
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     * @return Whether the dataNodeInfo field is set.
     */
    public boolean hasDataNodeInfo() {
      return dataNodeInfoBuilder_ != null || dataNodeInfo_ != null;
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     * @return The dataNodeInfo.
     */
    public ds.hdfs.generated.DataNodeInfo getDataNodeInfo() {
      if (dataNodeInfoBuilder_ == null) {
        return dataNodeInfo_ == null ? ds.hdfs.generated.DataNodeInfo.getDefaultInstance() : dataNodeInfo_;
      } else {
        return dataNodeInfoBuilder_.getMessage();
      }
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public Builder setDataNodeInfo(ds.hdfs.generated.DataNodeInfo value) {
      if (dataNodeInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dataNodeInfo_ = value;
        onChanged();
      } else {
        dataNodeInfoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public Builder setDataNodeInfo(
        ds.hdfs.generated.DataNodeInfo.Builder builderForValue) {
      if (dataNodeInfoBuilder_ == null) {
        dataNodeInfo_ = builderForValue.build();
        onChanged();
      } else {
        dataNodeInfoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public Builder mergeDataNodeInfo(ds.hdfs.generated.DataNodeInfo value) {
      if (dataNodeInfoBuilder_ == null) {
        if (dataNodeInfo_ != null) {
          dataNodeInfo_ =
            ds.hdfs.generated.DataNodeInfo.newBuilder(dataNodeInfo_).mergeFrom(value).buildPartial();
        } else {
          dataNodeInfo_ = value;
        }
        onChanged();
      } else {
        dataNodeInfoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public Builder clearDataNodeInfo() {
      if (dataNodeInfoBuilder_ == null) {
        dataNodeInfo_ = null;
        onChanged();
      } else {
        dataNodeInfo_ = null;
        dataNodeInfoBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public ds.hdfs.generated.DataNodeInfo.Builder getDataNodeInfoBuilder() {
      
      onChanged();
      return getDataNodeInfoFieldBuilder().getBuilder();
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    public ds.hdfs.generated.DataNodeInfoOrBuilder getDataNodeInfoOrBuilder() {
      if (dataNodeInfoBuilder_ != null) {
        return dataNodeInfoBuilder_.getMessageOrBuilder();
      } else {
        return dataNodeInfo_ == null ?
            ds.hdfs.generated.DataNodeInfo.getDefaultInstance() : dataNodeInfo_;
      }
    }
    /**
     * <code>.ds.hdfs.DataNodeInfo data_node_info = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        ds.hdfs.generated.DataNodeInfo, ds.hdfs.generated.DataNodeInfo.Builder, ds.hdfs.generated.DataNodeInfoOrBuilder> 
        getDataNodeInfoFieldBuilder() {
      if (dataNodeInfoBuilder_ == null) {
        dataNodeInfoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            ds.hdfs.generated.DataNodeInfo, ds.hdfs.generated.DataNodeInfo.Builder, ds.hdfs.generated.DataNodeInfoOrBuilder>(
                getDataNodeInfo(),
                getParentForChildren(),
                isClean());
        dataNodeInfo_ = null;
      }
      return dataNodeInfoBuilder_;
    }

    private java.util.List<ds.hdfs.generated.BlockMetadata> blocks_ =
      java.util.Collections.emptyList();
    private void ensureBlocksIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        blocks_ = new java.util.ArrayList<ds.hdfs.generated.BlockMetadata>(blocks_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        ds.hdfs.generated.BlockMetadata, ds.hdfs.generated.BlockMetadata.Builder, ds.hdfs.generated.BlockMetadataOrBuilder> blocksBuilder_;

    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public java.util.List<ds.hdfs.generated.BlockMetadata> getBlocksList() {
      if (blocksBuilder_ == null) {
        return java.util.Collections.unmodifiableList(blocks_);
      } else {
        return blocksBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public int getBlocksCount() {
      if (blocksBuilder_ == null) {
        return blocks_.size();
      } else {
        return blocksBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public ds.hdfs.generated.BlockMetadata getBlocks(int index) {
      if (blocksBuilder_ == null) {
        return blocks_.get(index);
      } else {
        return blocksBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder setBlocks(
        int index, ds.hdfs.generated.BlockMetadata value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.set(index, value);
        onChanged();
      } else {
        blocksBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder setBlocks(
        int index, ds.hdfs.generated.BlockMetadata.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.set(index, builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder addBlocks(ds.hdfs.generated.BlockMetadata value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.add(value);
        onChanged();
      } else {
        blocksBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder addBlocks(
        int index, ds.hdfs.generated.BlockMetadata value) {
      if (blocksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlocksIsMutable();
        blocks_.add(index, value);
        onChanged();
      } else {
        blocksBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder addBlocks(
        ds.hdfs.generated.BlockMetadata.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.add(builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder addBlocks(
        int index, ds.hdfs.generated.BlockMetadata.Builder builderForValue) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.add(index, builderForValue.build());
        onChanged();
      } else {
        blocksBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder addAllBlocks(
        java.lang.Iterable<? extends ds.hdfs.generated.BlockMetadata> values) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, blocks_);
        onChanged();
      } else {
        blocksBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder clearBlocks() {
      if (blocksBuilder_ == null) {
        blocks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        blocksBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public Builder removeBlocks(int index) {
      if (blocksBuilder_ == null) {
        ensureBlocksIsMutable();
        blocks_.remove(index);
        onChanged();
      } else {
        blocksBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public ds.hdfs.generated.BlockMetadata.Builder getBlocksBuilder(
        int index) {
      return getBlocksFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public ds.hdfs.generated.BlockMetadataOrBuilder getBlocksOrBuilder(
        int index) {
      if (blocksBuilder_ == null) {
        return blocks_.get(index);  } else {
        return blocksBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public java.util.List<? extends ds.hdfs.generated.BlockMetadataOrBuilder> 
         getBlocksOrBuilderList() {
      if (blocksBuilder_ != null) {
        return blocksBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(blocks_);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public ds.hdfs.generated.BlockMetadata.Builder addBlocksBuilder() {
      return getBlocksFieldBuilder().addBuilder(
          ds.hdfs.generated.BlockMetadata.getDefaultInstance());
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public ds.hdfs.generated.BlockMetadata.Builder addBlocksBuilder(
        int index) {
      return getBlocksFieldBuilder().addBuilder(
          index, ds.hdfs.generated.BlockMetadata.getDefaultInstance());
    }
    /**
     * <code>repeated .ds.hdfs.BlockMetadata blocks = 2;</code>
     */
    public java.util.List<ds.hdfs.generated.BlockMetadata.Builder> 
         getBlocksBuilderList() {
      return getBlocksFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        ds.hdfs.generated.BlockMetadata, ds.hdfs.generated.BlockMetadata.Builder, ds.hdfs.generated.BlockMetadataOrBuilder> 
        getBlocksFieldBuilder() {
      if (blocksBuilder_ == null) {
        blocksBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            ds.hdfs.generated.BlockMetadata, ds.hdfs.generated.BlockMetadata.Builder, ds.hdfs.generated.BlockMetadataOrBuilder>(
                blocks_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        blocks_ = null;
      }
      return blocksBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ds.hdfs.BlockReport)
  }

  // @@protoc_insertion_point(class_scope:ds.hdfs.BlockReport)
  private static final ds.hdfs.generated.BlockReport DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ds.hdfs.generated.BlockReport();
  }

  public static ds.hdfs.generated.BlockReport getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BlockReport>
      PARSER = new com.google.protobuf.AbstractParser<BlockReport>() {
    @java.lang.Override
    public BlockReport parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BlockReport(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BlockReport> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BlockReport> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ds.hdfs.generated.BlockReport getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

