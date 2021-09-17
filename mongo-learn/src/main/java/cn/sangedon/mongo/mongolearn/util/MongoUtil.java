package cn.sangedon.mongo.mongolearn.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import jdk.net.SocketFlow.Status;

/**
 * @author dongliangqiong
 */
public class MongoUtil {
    private static MongoClient client = null;

    static {
        if (client == null) {
            MongoCredential credential = MongoCredential.createCredential("haizhi", "admin", "haizhi1234!".toCharArray());

            ServerAddress serverAddress = new ServerAddress("127.0.0.1", 40042);

            client = new MongoClient(serverAddress, Arrays.asList(credential));
        }
    }

    public static MongoDatabase getDataBase(String dbName) {
        return client.getDatabase(dbName);
    }

    public static MongoCollection getCollection(String dbName, String collName) {
        MongoDatabase dataBase = getDataBase(dbName);
        return dataBase.getCollection(collName);
    }
}
