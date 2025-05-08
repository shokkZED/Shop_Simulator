package zooshop.repository;

import zooshop.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("Cat", 199.99));
        products.add(new Product("Dog", 299.99));
    }

    public static List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public static synchronized void purchaseProduct(String productName) {
        products.removeIf(p -> p.getName().equals(productName));
    }
}