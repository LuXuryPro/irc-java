/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

import Model.User.UserMode;
import Model.IRCEvent.IRCEvent;
import Model.IRCEvent.JoinEvent;
import Model.IRCEvent.NamesEvent;
import Model.IRCEvent.PartEvent;
import Model.IRCEvent.TopicChangeEvent;
import Model.IRCEvent.TopicEvent;

/**
 *
 * @author radek
 */
public class Channel {
	private String name;
	private String topic;
	private ArrayList<User> users;
	private ArrayList<IRCEvent> events;

	public Channel(String name) {
		this.name = name;
		this.topic = new String("");
		this.users = new ArrayList<User>();
		this.events = new ArrayList<IRCEvent>();
	}

	public synchronized void addEvent(IRCEvent e) {
		this.events.add(e);
		if (e instanceof NamesEvent) {
			this.users.clear();
			for (User s : ((NamesEvent) e).getNicks()) {
					this.users.add(s);
			}
		}
		else if (e instanceof JoinEvent) {
			if (this.getUserByName(((JoinEvent) e).getUser()) == null)
				this.users.add(new User(((JoinEvent) e).getUser(),
						UserMode.NORMAL));
			}
		else if (e instanceof PartEvent) {
			if (this.getUserByName(((PartEvent) e).getUser()) != null)
				this.removeUser(((PartEvent) e).getUser());
		}
		else if (e instanceof TopicEvent) {
			this.topic = ((TopicEvent) e).getTopic();
		}
		else if (e instanceof TopicChangeEvent) {
			this.topic = ((TopicChangeEvent) e).getTopic();
		}
		this.notifyAll();
	}

	public User getUserByName(String name) {
		for (User u : users) {
			if (u.getName().equals(name))
				return u;
		}
		return null;
	}

	
	public void removeUser(String name)
	{
		int i = 0;
		for (User u : this.users)
		{
			if (u.getName().equals(name))
			{
				this.users.remove(i);
				return;
			}
			i++;
		}
	}
	public synchronized String getName() {
		return name;
	}

	public synchronized ArrayList<IRCEvent> getEvents() {
		ArrayList<IRCEvent> e = (ArrayList<IRCEvent>) this.events.clone();
		return e;
	}

	public synchronized ArrayList<IRCEvent> waitForEvents()
			throws InterruptedException {
		wait();
		ArrayList<IRCEvent> e = (ArrayList<IRCEvent>) this.events.clone();
		return e;
	}

	public synchronized ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * @return the topic
	 */
	public synchronized String getTopic() {
		return topic;
	}
	
	

}
