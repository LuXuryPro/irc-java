package model.User;

public class VoiceUser extends User {
    public VoiceUser(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "+" + this.getName();
    }
}
