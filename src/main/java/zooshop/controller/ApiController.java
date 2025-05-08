package zooshop.controller;

import zooshop.service.ShopService;
import static spark.Spark.*;

public class ApiController {
    private ShopService shopService = new ShopService();

    public void initializeRoutes() {
        get("/products", (req, res) -> shopService.getAvailableProducts(), new JsonTransformer());

        post("/purchase", (req, res) -> {
            String productName = req.queryParams("product");
            return shopService.purchaseProduct(productName);
        }, new JsonTransformer());
    }
}