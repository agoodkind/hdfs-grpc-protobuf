curdir:=$(shell pwd)
classpath:=$(curdir)/lib/*.jar
out:=$(curdir)/bin
hdfs_sources:=$(curdir)/src/ds/hdfs/*.java

all: hdfs mapreduce

hdfs:
	javac -d $(out) -cp $(classpath) $(hdfs_sources)
mapreduce: