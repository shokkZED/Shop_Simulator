import zooshop.controller.ApiController;

public class Server {
    public static void main(String[] args) {
        new ApiController().initializeRoutes();
        System.out.println("Server started at http://localhost:4567");
    }
}