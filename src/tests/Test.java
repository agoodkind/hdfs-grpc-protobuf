package tests;

import ds.hdfs.generated.BlockLocation;
import ds.hdfs.generated.BlockLocationMapping;
import ds.hdfs.generated.BlockMetadata;
import ds.hdfs.generated.DataNodeInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public ConcurrentHashMap<String, BlockLocationMapping> test = new ConcurrentHashMap<>();
    public ConcurrentHashMap<DataNodeInfo, BlockLocationMapping> test3 = new ConcurrentHashMap<>();

    public Test() {


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

        test3.put(DataNodeInfo.newBuilder().setPort(69).build(), BlockLocationMapping.newBuilder().addMapping(BlockLocation
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
                        .build()).build()).build());

        System.out.println(new ArrayList<>(test3.keySet()).get(0).hashCode());

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

    public static void main(String[] args) throws IOException {

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

//        System.out.print(test.test2(test2));



        DataNodeInfo test31 = DataNodeInfo.newBuilder().setPort(69).build();

//        System.out.println(new ArrayList<>(test.test3.keySet()).get(0).hashCode());
        System.out.println(test31.hashCode());


        RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "rw");

        byte[] testb = {60,61,63,64,65,66};

        randomAccessFile.write(testb, 0, 2);
//        randomAccessFile.close();
        byte[] testa = new byte[5];
        randomAccessFile.seek(0);
        randomAccessFile.read(testa);

        System.out.println(testb[4]);
        System.out.println(testa[0]);
    }

}



