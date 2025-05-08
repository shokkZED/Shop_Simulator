package zooshop.service;

import zooshop.model.Product;
import zooshop.repository.ProductRepository;
import java.util.List;

public class ShopService {
    public List<Product> getAvailableProducts() {
        return ProductRepository.getAllProducts();
    }

    public String purchaseProduct(String productName) {
        ProductRepository.purchaseProduct(productName);
        return "Purchased: " + productName;
    }
}