package zooshop;

import zooshop.controller.ApiController;

public class Server {
    public static void main(String[] args) {
        ApiController api = new ApiController();
        api.initializeRoutes();
    }
}