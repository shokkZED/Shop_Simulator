package zooshop.model;

public class Customer extends User {
    public Customer(String username) {
        super(username);
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}