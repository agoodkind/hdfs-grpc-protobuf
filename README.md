# hdfs-mapr-protobuf
project for rutgers cs417 distributed systems

a basic implementation of HDFS' put, get, and list implemented in gRPC

*contributers:* sal fakhri (sf595), alex goodkind (amg540)


---
### building
```shell script
make all
```
### configuration
after building, the default configuration is generated under `config/default_config.json` 

if you are _not_ running all services on same machine, make sure to change `name_node_ip`

the default config is self-explanatory and looks like this:
```json
{
  "block_size_bytes": 64,
  "name_node_ip": "127.0.0.1",
  "name_node_port": 50051,
  "replication_factor": 2,
  "heartbeat_interval_ms": 10000,
  "name_node_metadata_persist_file": "persist/name_node_metadata.protobin",
  "data_node_block_store_path": "persist/block_store/",
  "client_dn_deadline_ms": 2000
}
```

## running
you can either run with the shell script, or using java directly

### NameNode
```shell script
$> ./run.sh namenode <config>
```
### DataNode
```shell script
$> ./run.sh datanode <port> [config]
```
### Client
```shell script
$> ./run.sh client <command <filename> <filename>> [config]
```

all commands support `help`, eg `$> ./run.sh namenode help`

## misc
*all persisted files are written as a protobuf binary file*
block files are written to `persist/block_store/*` eg. `persist/block_store/test_file.jpg_0`

namenode metadata is written to `name_node_metadata.protobin` everytime a new file is `put` from the client

# Design
Client
* CLI 
* Get file
* Put file
* List files
* based on the response from NameNodes: Breaks up a file into blocks and sends them to  DataNodes


NameNode
* Stores meta data for files
* Which DataNodes have blocks for a file
* Decides how to save a file
* Which DataNodes to send the blocks too
* Receives heart beats from DataNodes
* Heart beat tells NameNode which DataNodes are available to store blocks in
* Also tells NameNode which file blocks are in each data node
* Get Block information from a data node
* For a given file, which blocks a DataNode has


DataNode
* Stores file blocks
* Blocks stored in files named “file_name_block#”
* Sends Heart beats to NameNode
* Send name node which file blocks the data node has




Put command:
* send metadata to NameNode and based on response break up blocks and send data to DateNodes


Get command
* Ask name node for a list of data nodes mapped to corresponding blocks and the client will assemble them




Questions:


How does the Client connect to DataNodes?
* using gRPC the client will contact the DataNode to request / send blocks of data
 How do they get the ip address for each DataNode?
* after sending a command to NameNode, it will return a response with the a map between IP’s & file blocks
Does the NameNode send this info to the client?
* yes
Protobuf


Service NameNode
* heartBeat(BlockReport) returns Status
   * Used by DataNode
* assignBlocks(FileMetadata) returns BlockLocationMapping
   * Used by client
   * The returned list of locations are the datanodes that the given block should be written to (basically a mapping)
* getBlockLocations(FileMetadata) returns BlockLocationMapping
   * Used by client
* listFiles(...) returns FileList
   * Used by client


Service DataNode
* readBlock(BlockMetadata) returns Block
   * used by client to read a single block
* writeBlock(Block) returns Status
   * used by client to write a single block
* requestReportNow(...) returns BlockReport
   * used by NameNode to request a BlockReport from DataNode arbitrarily, used when the NameNode needs a BlockReport from a DataNode outside of just the regular HeartBeat that is sent to the NameNode


message Status
* bool success


message Block
* BlockMetadata block_info
* bytes content
  
message FileMetadata
* String name
* long size


message BlockMetadata
* int index
* int block_size
* String file_name


message DataNodeInfo
* String ip
* int port


message BlockLocation
*  DataNodeInfo data_node_info
*  BlockMetadata block_info


message BlockLocationMapping
* repeated BlockLocation mapping
* FileMetadata file_info


message BlockReport
* DataNodeInfo data_node_info
* repeated BlockMetadata blocks


message FileList
* repeated FileMetadata
