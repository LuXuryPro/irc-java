package model.ircevent;

import java.util.ArrayList;

import model.Channel;
import model.User.User;

public class NamesEvent extends IRCEvent {
    private ArrayList<User> nicks;

    public NamesEvent(String channel) {
        super("NAMES", channel);
        this.nicks = new ArrayList<User>();
    }

    @Override
    public String generateRawString() {
        return null;
    }

    @Override
    public String generateDisplayString() {
        return this.generateRawString();
    }

    @Override
    public void visit(Channel channel) {
        for (User s : this.getNicks()) {
            if (channel.getUserByName(s.getName()) == null)
                channel.getUsers().add(s);
            else if (channel.getUserByName(s.getName()).equals(s)) {
                channel.getUsers().remove(channel.getUserByName(s.getName()));
                channel.getUsers().add(s);
            }
        }
    }


    public ArrayList<User> getNicks() {
        return nicks;
    }
}
