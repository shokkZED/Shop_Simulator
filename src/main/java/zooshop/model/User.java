package zooshop.model;

public abstract class User {
    protected String username;

    public User(String username) {
        this.username = username;
    }

    public abstract String getRole();
}
