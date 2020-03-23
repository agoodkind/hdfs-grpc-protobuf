package tests;

import ds.hdfs.generated.BlockLocation;
import ds.hdfs.generated.BlockLocationMapping;
import ds.hdfs.generated.BlockMetadata;
import ds.hdfs.generated.DataNodeInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public ConcurrentHashMap<String, BlockLocationMapping> test = new ConcurrentHashMap<>();

    public void test() {
        test.put("test.txt", BlockLocationMapping
                .newBuilder()
                .addMapping(BlockLocation
                        .newBuilder()
                        .setDataNodeInfo(DataNodeInfo
                                .newBuilder()
                                .setPort(69)
                                .setIp("69.69.69.69")
                                .build())
                        .setBlockInfo(BlockMetadata
                                .newBuilder()
                                .setBlockSize(64)
                                .setFileName("test.txt")
                                .setIndex(0)
                                .build()).build())

                .build());

        BlockLocationMapping.Builder current = test.get("test.txt").toBuilder();

        current
                .addMapping(BlockLocation
                        .newBuilder()
                        .setDataNodeInfo(DataNodeInfo
                                .newBuilder()
                                .setPort(69)
                                .setIp("69.69.69.70")
                                .build())
                        .setBlockInfo(BlockMetadata
                                .newBuilder()
                                .setBlockSize(64)
                                .setFileName("test.txt")
                                .setIndex(5)
                                .build()).build());

//        System.out.print(current.build());

        test.put("test.txt", current.build());



    }

    public List<BlockLocation> test2(BlockLocation test2) {
        List<BlockLocation> blockLocationList = new ArrayList<>();

        blockLocationList.add(BlockLocation
                .newBuilder()
                .setDataNodeInfo(DataNodeInfo
                        .newBuilder()
                        .setPort(69)
                        .setIp("69.69.69.70")
                        .build())
                .setBlockInfo(BlockMetadata
                        .newBuilder()
                        .setBlockSize(64)
                        .setFileName("test.txt")
                        .setIndex(5)
                        .build()).build());



        blockLocationList.remove(test2);

        return blockLocationList;
    }

    public static void main(String[] args) {

        Test test = new Test();

        BlockLocation test2 = BlockLocation
                .newBuilder()
                .setDataNodeInfo(DataNodeInfo
                        .newBuilder()
                        .setPort(69)
                        .setIp("69.69.69.70")
                        .build())
                .setBlockInfo(BlockMetadata
                        .newBuilder()
                        .setBlockSize(64)
                        .setFileName("test.txt")
                        .setIndex(5)
                        .build()).build();

        System.out.print(test.test2(test2));

    }

}



