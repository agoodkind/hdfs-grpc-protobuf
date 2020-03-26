#!/bin/bash
curdir=$(pwd)
classpath="$curdir/lib/*:$curdir/bin"
javarun="java -cp $classpath ds.hdfs"

if [ $1 = 'client' ]; then
  eval "$javarun.Client $2"
elif [ $1 = 'datanode' ]; then
  eval "$javarun.DataNode $2"
elif [ $1 = 'namenode' ]; then
  eval "$javarun.NameNode $2"
else
  echo "test"
fi
