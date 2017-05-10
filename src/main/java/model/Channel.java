package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.User.AdminUser;
import model.User.NormalUser;
import model.User.User;
import model.User.VoiceUser;
import model.ircevent.IRCEvent;
import model.ircevent.JoinEvent;
import model.ircevent.ModeEvent;
import model.ircevent.NamesEvent;
import model.ircevent.NickEvent;
import model.ircevent.PartEvent;
import model.ircevent.TopicChangeEvent;
import model.ircevent.TopicEvent;

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
        this.topic = new String("");
        this.users = new ArrayList<User>();
        this.events = new ArrayList<IRCEvent>();
    }

    /**
     * Add new event to channel
     *
     * @param e Event to add
     */
    public synchronized void addEvent(IRCEvent e) {
        this.events.add(e);
        e.visit(this);
        this.notifyAll();
    }

    /**
     * Find user on channel
     *
     * @param name name of user
     * @return User object on channel
     */
    public User getUserByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name))
                return u;
        }
        return null;
    }

    /**
     * Remove user from channel
     *
     * @param name name of user to remove
     */
    public void removeUser(String name) {
        int i = 0;
        for (User u : this.users) {
            if (u.getName().equals(name)) {
                this.users.remove(i);
                return;
            }
            i++;
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
        ArrayList<IRCEvent> e = (ArrayList<IRCEvent>) this.events.clone();
        return e;
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
        ArrayList<IRCEvent> e = (ArrayList<IRCEvent>) this.events.clone();
        return e;
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
            File f = new File(file_name);
            BufferedWriter w = new BufferedWriter(new FileWriter(f));
            w.write("<body bgcolor = '#1C1C1C'><font color = 'white' face = 'monospace'>");
            for (IRCEvent e : this.events) w.write(e.generateDisplayString() + "<br>");
            w.write("</font></body>");
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
