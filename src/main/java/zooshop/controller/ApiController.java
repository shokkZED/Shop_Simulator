package zooshop.controller;

import static spark.Spark.*;

import com.google.gson.Gson;
import zooshop.model.Product;
import zooshop.service.ShopService;

import java.util.List;

public class ApiController {
    private final ShopService shopService = new ShopService();
    private final Gson gson = new Gson();

    public void initializeRoutes() {
        get("/products", (req, res) -> {
            List<Product> products = shopService.getAvailableProducts();
            return gson.toJson(products);
        });

        post("/purchase", (req, res) -> {
            String body = req.body();
            Product request = gson.fromJson(body, Product.class);
            String result = shopService.purchaseProduct(request.getName());
            return gson.toJson(result);
        });

        post("/add", (req, res) -> {
            Product p = gson.fromJson(req.body(), Product.class);
            shopService.addProduct(p);
            System.out.println("Product added!");
            return "Product added!";
        });
    }
}
