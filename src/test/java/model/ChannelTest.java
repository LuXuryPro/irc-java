package model;

import model.ircevent.JoinEvent;
import org.junit.Assert;

public class ChannelTest {
    @org.junit.Test
    public void addEvent() throws Exception {
        Channel channel = new Channel("Name");
        channel.addEvent(new JoinEvent(channel.getName(), "Test User"));

        Assert.assertEquals("Test User", channel.getUsers().get(0).getName());
    }

    @org.junit.Test
    public void getUserByName() throws Exception {
        Channel channel = new Channel("Name");
        channel.addEvent(new JoinEvent(channel.getName(), "Test User Name"));

        Assert.assertEquals("Test User Name", channel.getUserByName("Test User Name").getName());
    }

    @org.junit.Test
    public void removeUser() throws Exception {
    }

    @org.junit.Test
    public void getName() throws Exception {
    }

    @org.junit.Test
    public void getEvents() throws Exception {
    }

    @org.junit.Test
    public void waitForEvents() throws Exception {
    }

    @org.junit.Test
    public void getUsers() throws Exception {
    }

    @org.junit.Test
    public void getTopic() throws Exception {
    }

    @org.junit.Test
    public void saveToFile() throws Exception {
    }
}
