#!/bin/bash
curdir=$(pwd)
classpath="$curdir/lib/*:$curdir/bin"
javarun="java -cp $classpath ds.hdfs"
args=${*:2}

if [ $1 = 'client' ]; then
  eval "$javarun.Client $args"
elif [ $1 = 'datanode' ]; then
  eval "$javarun.DataNode $args"
elif [ $1 = 'namenode' ]; then
  eval "$javarun.NameNode $args"
else
  echo "expected client, datanode, or namenode"
fi
