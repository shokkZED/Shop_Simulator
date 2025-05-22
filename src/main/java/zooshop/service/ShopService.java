package zooshop.service;

import zooshop.model.Product;
import zooshop.repository.ProductRepository;

import java.util.List;

public class ShopService {
    private final ProductRepository productRepo = new ProductRepository();

    public List<Product> getAvailableProducts() {
        return productRepo.findAll();
    }

    public String purchaseProduct(String name) {
        boolean success = productRepo.deleteByName(name);
        System.out.println(success ? "Purchased: " + name : "Not found: " + name);
        return success ? "Purchased: " + name : "Not found: " + name;
    }

    public void addProduct(Product p) {
        productRepo.insert(p);
    }
}
