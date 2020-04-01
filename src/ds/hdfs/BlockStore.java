package ds.hdfs;

import ds.hdfs.generated.Block;
import ds.hdfs.generated.BlockMetadata;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class BlockStore {

    private String STORE_PATH = "persist/block_store/";

    private Map<BlockMetadata, Block> blockMap = new HashMap<>();

    public BlockStore(String pathToBlockStore) {
        STORE_PATH = pathToBlockStore;
        updateBlockMap();
    }

    public BlockStore() {
        updateBlockMap();
    }

    public void persistBlock(Block block) throws IOException {
        String fileName = STORE_PATH + block.getBlockInfo().getFileName() + "_" + block.getBlockInfo().getIndex();

        FileOutputStream file = new FileOutputStream(fileName);
        block.writeTo(file);

        updateBlockMap();
    }

    public Block getBlock(BlockMetadata blockMetadata) throws NoSuchElementException {
        Block block = blockMap.get(blockMetadata);

        // TODO: wrap this in a try .. catch block
        //  and when the error is thrown catch it and log an error so the program doesnt exit
        if(block == null) {
            throw new NoSuchElementException("unable to find block: " + blockMetadata.getFileName() + "_" + blockMetadata.getIndex());
        }

        return block;
    }

    public List<BlockMetadata> getMetaDataList() {
        return new ArrayList<>(blockMap.keySet());
    }

    private void updateBlockMap() {
        File dir = new File(STORE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File[] storeDirectory = dir.listFiles();
        if (storeDirectory != null) {
            for (File file : storeDirectory) {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    Block block = Block.parseDelimitedFrom(inputStream);
                    blockMap.put(block.getBlockInfo(), block);
                } catch (IOException e) {
                    // TODO more detailed error handling
                    System.out.println(e);
                }
            }
        } else {
            // TODO Better error handling
            System.out.println("Datanode: no files available in block store");
            System.out.println("path: " + STORE_PATH);
        }
    }

}
