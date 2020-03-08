curdir:=$(shell pwd)
classpath:=$(curdir)/lib/*.jar
out:=$(curdir)/bin
sources:=$(curdir)/src/ds/hdfs/*.java
#javarunprefix="java -classpath $classpath:$out"

all:
	javac -d $(out) -cp $(classpath) $(sources)