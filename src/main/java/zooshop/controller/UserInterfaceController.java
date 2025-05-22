package zooshop.controller;

import zooshop.model.Product;
import zooshop.service.ShopService;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceController {
    private final Scanner scanner = new Scanner(System.in);
    private final ShopService shopService = new ShopService();

    public void run() {
        while (true) {
            System.out.println("\n1. Show products\n2. Buy product\n3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showProducts();
                case "2" -> buyProduct();
                case "3" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void showProducts() {
        List<Product> products = shopService.getAvailableProducts();
        products.forEach(p ->
                System.out.printf("%s - $%.2f%n", p.getName(), p.getPrice())
        );
    }

    private void buyProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        String result = shopService.purchaseProduct(name);
        System.out.println(result);
    }
}