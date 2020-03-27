package ds.hdfs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;

public class Config {

    @SerializedName("block_size_bytes")
    public int BLOCK_SIZE_BYTES = 64;
    @SerializedName("name_node_ip")
    public String NAME_NODE_IP = "127.0.0.1";
    @SerializedName("name_node_port")
    public int NAME_NODE_PORT = 50051;
    @SerializedName("replication_factor")
    public int REPLICATION_FACTOR = 2;
    @SerializedName("heartbeat_interval_ms")
    public int HEARTBEAT_INTERVAL_MS = 10000;
    @SerializedName("name_node_metadata_persist_file")
    public String NAME_NODE_METADATA_PERSIST_FILE = "persist/name_node_metadata.json";
    @SerializedName("data_node_block_store_path")
    public String DATA_NODE_BLOCK_STORE_PATH = "persist/block_store/";
    @SerializedName("client_dn_deadline_ms")
    public int CLIENT_DN_DEADLINE_MS = 2000;

    public static Config readConfig(String fileName) throws FileNotFoundException {
        return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), Config.class);
    }

    public static Gson writeDefaultConfig(String fileName) throws IOException {
        new File("config").mkdirs();

        FileWriter out = new FileWriter(fileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
                .setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        gson.toJson(new Config(), out);
        out.flush();
        out.close();

        return gson;
    }

    public static void main(String[] args) throws IOException {
        Config.writeDefaultConfig("config/default_config.json");
    }
}
