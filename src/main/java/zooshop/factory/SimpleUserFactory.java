package zooshop.factory;

import zooshop.model.User;
import zooshop.model.Customer;

public class SimpleUserFactory implements UserFactory {
    @Override
    public User createUser(String username) {
        return new Customer(username);
    }
}