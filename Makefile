curdir:=$(shell pwd)
classpath:="$(curdir)/lib/*"
out:=$(curdir)/bin
hdfs_sources:=$(curdir)/src/ds/hdfs/*.java

all: hdfs mapreduce

hdfs:
	protoc --plugin=protoc-gen-grpc-java=plugins/protoc-gen-grpc-java-1.28.0-osx-x86_64.exe --grpc-java_out=src --proto_path=src/ds/hdfs/test --java_out=src src/ds/hdfs/test/test.proto
	javac -d $(out) -cp $(classpath) $(hdfs_sources)
mapreduce:
