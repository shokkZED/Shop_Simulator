package zooshop.repository;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

import zooshop.db.MongoDBConnector;
import zooshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final MongoCollection<Document> collection;

    public ProductRepository() {
        this.collection = MongoDBConnector.getCollection("products");
    }

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            Product p = new Product(
                    doc.getString("name"),
                    doc.getDouble("price")
            );
            list.add(p);
        }
        return list;
    }

    public boolean deleteByName(String name) {
        Bson filter = eq("name", name);
        DeleteResult result = collection.deleteOne(filter);
        return result.getDeletedCount() > 0;
    }

    public void insert(Product p) {
        Document doc = new Document("name", p.getName()).append("price", p.getPrice());
        collection.insertOne(doc);
    }
}