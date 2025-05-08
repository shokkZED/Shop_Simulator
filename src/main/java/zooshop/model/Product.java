package zooshop.model;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Геттеры
    public String getName() { return name; }
    public double getPrice() { return price; }
}