package ds.hdfs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;

public class Config {

    @SerializedName("block_size_bytes")
    public static int BLOCK_SIZE_BYTES = 64;
    @SerializedName("name_node_ip")
    public static String NAME_NODE_IP = "127.0.0.1";
    @SerializedName("name_node_port")
    public static int NAME_NODE_PORT = 50051;
    @SerializedName("replication_factor")
    public static int REPLICATION_FACTOR = 2;
    @SerializedName("heartbeat_interval_ms")
    public static int HEARTBEAT_INTERVAL_MS = 10000;

    public static Config readConfig(String fileName) throws FileNotFoundException {
        return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), Config.class);
    }

    public static Gson writeConfig(String fileName, Config config) throws IOException {
        FileWriter out = new FileWriter(fileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
                .setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        gson.toJson(config, out);
        out.flush();
        out.close();

        return gson;
    }
}
