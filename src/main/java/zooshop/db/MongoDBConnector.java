package zooshop.db;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoDBConnector {
    private static final String CONNECTION_STRING = "...";
    private static final String DB_NAME = "Shop";

    private static MongoClient mongoClient;

    static {
        mongoClient = MongoClients.create(CONNECTION_STRING);
    }

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DB_NAME);
    }

    public static MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);
    }
}
