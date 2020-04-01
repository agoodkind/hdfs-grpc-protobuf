curdir:=$(shell pwd)
out:=$(curdir)/bin
hdfs_java_sources:=$(curdir)/src/ds/hdfs
classpath:=$(curdir)/lib/*:$(curdir)/bin/
proto_sources:=$(curdir)/src/ds/hdfs/protos
protoc_plugin_path:=$(curdir)/plugins

UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Linux)
	protoc_grpc_plugin_exe=protoc-gen-grpc-java-1.28.0-linux-x86_64.exe
	protoc_exe=protoc-3.11.4-linux-x86_64/bin/protoc
endif
ifeq ($(UNAME_S),Darwin)
	protoc_grpc_plugin_exe+=protoc-gen-grpc-java-1.28.0-osx-x86_64.exe
	protoc_exe=protoc-3.11.4-osx-x86_64/bin/protoc
endif

all: proto hdfs mapreduce default_config
hdfs:
	mkdir -p $(curdir)/bin
	javac -d $(out) -cp $(classpath) $(hdfs_java_sources)/generated/*.java $(hdfs_java_sources)/*.java
mapreduce:
proto: clean
	chmod +x $(protoc_plugin_path)/$(protoc_grpc_plugin_exe)
	chmod +x $(protoc_plugin_path)/$(protoc_exe)
	$(protoc_plugin_path)/$(protoc_exe) --plugin=protoc-gen-grpc-java=$(protoc_plugin_path)/$(protoc_grpc_plugin_exe) --grpc-java_out=src --proto_path=$(proto_sources) --java_out=src $(proto_sources)/hdfsformat.proto
default_config:
	java -cp $(classpath) ds.hdfs.Config
clean:
	rm -rf $(curdir)/bin/* $(curdir)/persist $(curdir)/src/ds/hdfs/generated/*
test: