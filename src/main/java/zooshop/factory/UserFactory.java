package zooshop.factory;

import zooshop.model.User;

public interface UserFactory {
    User createUser(String username);
}