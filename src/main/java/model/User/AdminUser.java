package model.User;

public class AdminUser extends User {
    public AdminUser(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "@" + this.getName();
    }
}
