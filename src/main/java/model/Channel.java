package model;

import model.User.User;
import model.ircevent.IRCEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Channel {
    private String name;
    private String topic;
    private ArrayList<User> users;
    private ArrayList<IRCEvent> events;

    /**
     * Create new channel
     *
     * @param name channel name
     */
    public Channel(String name) {
        this.name = name;
        this.topic = "";
        this.users = new ArrayList<User>();
        this.events = new ArrayList<IRCEvent>();
    }

    /**
     * Add new event to channel
     *
     * @param ircEvent Event to add
     */
    public synchronized void addEvent(IRCEvent ircEvent) {
        this.events.add(ircEvent);
        ircEvent.visit(this);
        this.notifyAll();
    }

    /**
     * Find user on channel
     *
     * @param name name of user
     * @return User object on channel
     */
    public User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name))
                return user;
        }
        return null;
    }

    /**
     * Remove user from channel
     *
     * @param name name of user to remove
     */
    public void removeUser(String name) {
        int index = 0;
        for (User user : this.users) {
            if (user.getName().equals(name)) {
                this.users.remove(index);
                return;
            }
            index++;
        }
    }

    /**
     * @return name of channel
     */
    public synchronized String getName() {
        return name;
    }

    /**
     * @return list of events on channel
     */
    public synchronized ArrayList<IRCEvent> getEvents() {
        @SuppressWarnings("unchecked")
        ArrayList<IRCEvent> ircEvents = (ArrayList<IRCEvent>) this.events.clone();
        return ircEvents;
    }

    /**
     * Wait on channel's monitor for new events
     *
     * @return copy of all events in channel including new events
     * @throws InterruptedException
     */
    public synchronized ArrayList<IRCEvent> waitForEvents() throws InterruptedException {
        wait();
        @SuppressWarnings("unchecked")
        ArrayList<IRCEvent> ircEvents = (ArrayList<IRCEvent>) this.events.clone();
        return ircEvents;
    }

    /**
     * @return list of users on channel
     */
    public synchronized ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @return topic of channel
     */
    public synchronized String getTopic() {
        return topic;
    }

    /**
     * Save all events on channel to file
     *
     * @param file_name name of output file
     */
    public synchronized void saveToFile(String file_name) {
        try {
            File file = new File(file_name);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("<body bgcolor = '#1C1C1C'><font color = 'white' face = 'monospace'>");
            for (IRCEvent ircEvent : this.events)
                writer.write(ircEvent.generateDisplayString() + "<br>");
            writer.write("</font></body>");
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
