package model.User;

public class NormalUser extends User {
    public NormalUser(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof NormalUser) && super.equals(o);
    }
}
