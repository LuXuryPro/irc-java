package model;

import model.ircevent.IRCEvent;
import model.ircevent.JoinEvent;
import model.ircevent.PartEvent;
import model.ircevent.TopicEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChannelTest {
    @Test
    public void addEvent() throws Exception {
        Channel channel = new Channel("Name");
        channel.addEvent(new JoinEvent(channel.getName(), "Test User"));

        assertEquals("Test User", channel.getUsers().get(0).getName());
    }

    @Test
    public void getUserByName() throws Exception {
        Channel channel = new Channel("Name");
        channel.addEvent(new JoinEvent(channel.getName(), "Test User Name"));

        assertEquals("Test User Name", channel.getUserByName("Test User Name").getName());
    }

    @org.junit.Test
    public void removeUser() throws Exception {
        Channel channel = new Channel("Name");
        channel.addEvent(new JoinEvent(channel.getName(), "Test User Name"));
        channel.addEvent(new PartEvent(channel.getName(), "Test User Name"));
        assertEquals(0, channel.getUsers().size());
    }

    @Test
    public void getName() throws Exception {
        Channel channel = new Channel("Name");
        assertEquals("Name", channel.getName());
    }

    @Test
    public void getEvents() throws Exception {
        Channel channel = new Channel("Name");
        JoinEvent joinEvent = new JoinEvent(channel.getName(), "Test User Name");
        channel.addEvent(joinEvent);
        PartEvent partEvent = new PartEvent(channel.getName(), "Test User Name");
        channel.addEvent(partEvent);
        assertTrue(channel.getEvents().contains(joinEvent));
        assertTrue(channel.getEvents().contains(partEvent));
    }

    @Test
    public void waitForEvents() throws Exception {
        Channel channel = new Channel("Name");
        JoinEvent joinEvent = new JoinEvent(channel.getName(), "Test User Name");
        channel.addEvent(joinEvent);
        PartEvent partEvent = new PartEvent(channel.getName(), "Test User Name");
        channel.addEvent(partEvent);
        assertTrue(channel.getEvents().contains(joinEvent));
        assertTrue(channel.getEvents().contains(partEvent));
    }

    @Test
    public void getUsers() throws Exception {
        Channel channel = new Channel("Name");
        JoinEvent joinEvent = new JoinEvent(channel.getName(), "Test User Name");
        channel.addEvent(joinEvent);
        assertEquals("Test User Name", channel.getUsers().get(0).getName());
    }

    @Test
    public void getTopic() throws Exception {
        Channel channel = new Channel("Name");
        TopicEvent event = new TopicEvent(channel.getName(), "Topic");
        channel.addEvent(event);
        assertEquals("Topic", channel.getTopic());
    }
}
