package ds.hdfs;

import ds.hdfs.generated.Block;
import ds.hdfs.generated.BlockMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BlockStore {

    File[] blockFiles;

    public BlockStore(String pathToBlockStore) {
        blockFiles = new File(pathToBlockStore).listFiles();
    }

    public void persistBlock(Block block) throws IOException {
        String fileName = block.getBlockInfo().getFileName() + "_" + block.getBlockInfo().getIndex();

        FileOutputStream file = new FileOutputStream(fileName);

        block.writeTo(file);
    }

    public Block getBlock(BlockMetadata blockMetadata) throws IOException {
        String fileName = blockMetadata.getFileName() + "_" + blockMetadata.getIndex();

        FileInputStream file = new FileInputStream(fileName);
        Block block = Block.parseDelimitedFrom(file);

        return block;
    }

    public List<BlockMetadata> getMetaDataList() {
        List<BlockMetadata> metadataList = new ArrayList<>();

        return metadataList;
    }

}
