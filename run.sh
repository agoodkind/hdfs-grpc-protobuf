#!/bin/bash
curdir=$(pwd)
classpath="$curdir/lib/protobuf-java-3.9.2.jar"
javarun="java -cp bin -Djava.rmi.server.codebase=file:bin/ -Djava.security.policy=src/permission.policy ds.hdfs"
javarmi="(cd $curdir/bin && /usr/lib/jvm/java-11/bin/rmiregistry)"

if [ $1 = 'client' ]; then
  eval "$javarun.Client $2"
elif [ $1 = 'datanode' ]; then
  eval "$javarun.DataNode $2"
  echo "test"
elif [ $1 = 'namenode' ]; then
  echo "test"
fi
