// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hdfsformat.proto

package ds.hdfs.generated;

/**
 * Protobuf type {@code ds.hdfs.BlockLocationMapping}
 */
public  final class BlockLocationMapping extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ds.hdfs.BlockLocationMapping)
    BlockLocationMappingOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BlockLocationMapping.newBuilder() to construct.
  private BlockLocationMapping(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BlockLocationMapping() {
    mapping_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BlockLocationMapping();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BlockLocationMapping(
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              mapping_ = new java.util.ArrayList<ds.hdfs.generated.BlockLocation>();
              mutable_bitField0_ |= 0x00000001;
            }
            mapping_.add(
                input.readMessage(ds.hdfs.generated.BlockLocation.parser(), extensionRegistry));
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
        mapping_ = java.util.Collections.unmodifiableList(mapping_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockLocationMapping_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockLocationMapping_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ds.hdfs.generated.BlockLocationMapping.class, ds.hdfs.generated.BlockLocationMapping.Builder.class);
  }

  public static final int MAPPING_FIELD_NUMBER = 1;
  private java.util.List<ds.hdfs.generated.BlockLocation> mapping_;
  /**
   * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
   */
  public java.util.List<ds.hdfs.generated.BlockLocation> getMappingList() {
    return mapping_;
  }
  /**
   * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
   */
  public java.util.List<? extends ds.hdfs.generated.BlockLocationOrBuilder> 
      getMappingOrBuilderList() {
    return mapping_;
  }
  /**
   * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
   */
  public int getMappingCount() {
    return mapping_.size();
  }
  /**
   * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
   */
  public ds.hdfs.generated.BlockLocation getMapping(int index) {
    return mapping_.get(index);
  }
  /**
   * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
   */
  public ds.hdfs.generated.BlockLocationOrBuilder getMappingOrBuilder(
      int index) {
    return mapping_.get(index);
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
    for (int i = 0; i < mapping_.size(); i++) {
      output.writeMessage(1, mapping_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < mapping_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, mapping_.get(i));
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
    if (!(obj instanceof ds.hdfs.generated.BlockLocationMapping)) {
      return super.equals(obj);
    }
    ds.hdfs.generated.BlockLocationMapping other = (ds.hdfs.generated.BlockLocationMapping) obj;

    if (!getMappingList()
        .equals(other.getMappingList())) return false;
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
    if (getMappingCount() > 0) {
      hash = (37 * hash) + MAPPING_FIELD_NUMBER;
      hash = (53 * hash) + getMappingList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ds.hdfs.generated.BlockLocationMapping parseFrom(
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
  public static Builder newBuilder(ds.hdfs.generated.BlockLocationMapping prototype) {
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
   * Protobuf type {@code ds.hdfs.BlockLocationMapping}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ds.hdfs.BlockLocationMapping)
      ds.hdfs.generated.BlockLocationMappingOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockLocationMapping_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockLocationMapping_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ds.hdfs.generated.BlockLocationMapping.class, ds.hdfs.generated.BlockLocationMapping.Builder.class);
    }

    // Construct using ds.hdfs.generated.BlockLocationMapping.newBuilder()
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
        getMappingFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (mappingBuilder_ == null) {
        mapping_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        mappingBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ds.hdfs.generated.HDFSFormat.internal_static_ds_hdfs_BlockLocationMapping_descriptor;
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockLocationMapping getDefaultInstanceForType() {
      return ds.hdfs.generated.BlockLocationMapping.getDefaultInstance();
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockLocationMapping build() {
      ds.hdfs.generated.BlockLocationMapping result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ds.hdfs.generated.BlockLocationMapping buildPartial() {
      ds.hdfs.generated.BlockLocationMapping result = new ds.hdfs.generated.BlockLocationMapping(this);
      int from_bitField0_ = bitField0_;
      if (mappingBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          mapping_ = java.util.Collections.unmodifiableList(mapping_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.mapping_ = mapping_;
      } else {
        result.mapping_ = mappingBuilder_.build();
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
      if (other instanceof ds.hdfs.generated.BlockLocationMapping) {
        return mergeFrom((ds.hdfs.generated.BlockLocationMapping)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ds.hdfs.generated.BlockLocationMapping other) {
      if (other == ds.hdfs.generated.BlockLocationMapping.getDefaultInstance()) return this;
      if (mappingBuilder_ == null) {
        if (!other.mapping_.isEmpty()) {
          if (mapping_.isEmpty()) {
            mapping_ = other.mapping_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMappingIsMutable();
            mapping_.addAll(other.mapping_);
          }
          onChanged();
        }
      } else {
        if (!other.mapping_.isEmpty()) {
          if (mappingBuilder_.isEmpty()) {
            mappingBuilder_.dispose();
            mappingBuilder_ = null;
            mapping_ = other.mapping_;
            bitField0_ = (bitField0_ & ~0x00000001);
            mappingBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getMappingFieldBuilder() : null;
          } else {
            mappingBuilder_.addAllMessages(other.mapping_);
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
      ds.hdfs.generated.BlockLocationMapping parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ds.hdfs.generated.BlockLocationMapping) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<ds.hdfs.generated.BlockLocation> mapping_ =
      java.util.Collections.emptyList();
    private void ensureMappingIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        mapping_ = new java.util.ArrayList<ds.hdfs.generated.BlockLocation>(mapping_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        ds.hdfs.generated.BlockLocation, ds.hdfs.generated.BlockLocation.Builder, ds.hdfs.generated.BlockLocationOrBuilder> mappingBuilder_;

    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public java.util.List<ds.hdfs.generated.BlockLocation> getMappingList() {
      if (mappingBuilder_ == null) {
        return java.util.Collections.unmodifiableList(mapping_);
      } else {
        return mappingBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public int getMappingCount() {
      if (mappingBuilder_ == null) {
        return mapping_.size();
      } else {
        return mappingBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public ds.hdfs.generated.BlockLocation getMapping(int index) {
      if (mappingBuilder_ == null) {
        return mapping_.get(index);
      } else {
        return mappingBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder setMapping(
        int index, ds.hdfs.generated.BlockLocation value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.set(index, value);
        onChanged();
      } else {
        mappingBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder setMapping(
        int index, ds.hdfs.generated.BlockLocation.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.set(index, builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder addMapping(ds.hdfs.generated.BlockLocation value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.add(value);
        onChanged();
      } else {
        mappingBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder addMapping(
        int index, ds.hdfs.generated.BlockLocation value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.add(index, value);
        onChanged();
      } else {
        mappingBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder addMapping(
        ds.hdfs.generated.BlockLocation.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.add(builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder addMapping(
        int index, ds.hdfs.generated.BlockLocation.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.add(index, builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder addAllMapping(
        java.lang.Iterable<? extends ds.hdfs.generated.BlockLocation> values) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, mapping_);
        onChanged();
      } else {
        mappingBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder clearMapping() {
      if (mappingBuilder_ == null) {
        mapping_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        mappingBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public Builder removeMapping(int index) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.remove(index);
        onChanged();
      } else {
        mappingBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public ds.hdfs.generated.BlockLocation.Builder getMappingBuilder(
        int index) {
      return getMappingFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public ds.hdfs.generated.BlockLocationOrBuilder getMappingOrBuilder(
        int index) {
      if (mappingBuilder_ == null) {
        return mapping_.get(index);  } else {
        return mappingBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public java.util.List<? extends ds.hdfs.generated.BlockLocationOrBuilder> 
         getMappingOrBuilderList() {
      if (mappingBuilder_ != null) {
        return mappingBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(mapping_);
      }
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public ds.hdfs.generated.BlockLocation.Builder addMappingBuilder() {
      return getMappingFieldBuilder().addBuilder(
          ds.hdfs.generated.BlockLocation.getDefaultInstance());
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public ds.hdfs.generated.BlockLocation.Builder addMappingBuilder(
        int index) {
      return getMappingFieldBuilder().addBuilder(
          index, ds.hdfs.generated.BlockLocation.getDefaultInstance());
    }
    /**
     * <code>repeated .ds.hdfs.BlockLocation mapping = 1;</code>
     */
    public java.util.List<ds.hdfs.generated.BlockLocation.Builder> 
         getMappingBuilderList() {
      return getMappingFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        ds.hdfs.generated.BlockLocation, ds.hdfs.generated.BlockLocation.Builder, ds.hdfs.generated.BlockLocationOrBuilder> 
        getMappingFieldBuilder() {
      if (mappingBuilder_ == null) {
        mappingBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            ds.hdfs.generated.BlockLocation, ds.hdfs.generated.BlockLocation.Builder, ds.hdfs.generated.BlockLocationOrBuilder>(
                mapping_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        mapping_ = null;
      }
      return mappingBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ds.hdfs.BlockLocationMapping)
  }

  // @@protoc_insertion_point(class_scope:ds.hdfs.BlockLocationMapping)
  private static final ds.hdfs.generated.BlockLocationMapping DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ds.hdfs.generated.BlockLocationMapping();
  }

  public static ds.hdfs.generated.BlockLocationMapping getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BlockLocationMapping>
      PARSER = new com.google.protobuf.AbstractParser<BlockLocationMapping>() {
    @java.lang.Override
    public BlockLocationMapping parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BlockLocationMapping(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BlockLocationMapping> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BlockLocationMapping> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ds.hdfs.generated.BlockLocationMapping getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

