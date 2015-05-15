package model;

import java.util.ArrayList;

import model.User.UserMode;
import model.ircevent.IRCEvent;
import model.ircevent.JoinEvent;
import model.ircevent.ModeEvent;
import model.ircevent.NamesEvent;
import model.ircevent.PartEvent;
import model.ircevent.TopicChangeEvent;
import model.ircevent.TopicEvent;

public class Channel {
	private String name;
	private String topic;
	private ArrayList<User> users;
	private ArrayList<IRCEvent> events;
	private boolean nicks_recived = false;

	/**
	 * Create new channel
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
	 * @param e Event to add
	 */
	public synchronized void addEvent(IRCEvent e) {
		this.events.add(e);
		if (e instanceof NamesEvent) {
			for (User s : ((NamesEvent) e).getNicks()) {
				if (this.getUserByName(s.getName()) == null)
					this.users.add(s);
				else if (this.getUserByName(s.getName()).getMode() != s.getMode())
				{
					this.users.remove(this.getUserByName(s.getName()));
					this.users.add(s);
				}
			}
		}
		else if (e instanceof JoinEvent) {
			if (this.getUserByName(((JoinEvent) e).getUser()) == null)
				this.users.add(new User(((JoinEvent) e).getUser(),UserMode.NORMAL));
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
		else if (e instanceof ModeEvent) {
			String new_mode =((ModeEvent) e).getNewMode();
			String au = ((ModeEvent) e).getAffectedUser();
			if (this.getUserByName(au) != null)
			{
				if (new_mode.equals("+o"))
					this.getUserByName(au).setMode(UserMode.OP);
				else if (new_mode.equals("+v"))
					this.getUserByName(au).setMode(UserMode.VOICE);
				else if (new_mode.equals("-o"))
					this.getUserByName(au).setMode(UserMode.NORMAL);
				else if (new_mode.equals("-o"))
					this.getUserByName(au).setMode(UserMode.NORMAL);
			}
		}
		this.notifyAll();
	}

	/**
	 * Find user on channel
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
	 * @param name name of user to remove
	 */
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
		ArrayList<IRCEvent> e = (ArrayList<IRCEvent>) this.events.clone();
		return e;
	}

	/**
	 * Wait on channel's mutex for new events 
	 * @return copy of all events in channel including new events
	 * @throws InterruptedException
	 */
	public synchronized ArrayList<IRCEvent> waitForEvents()
			throws InterruptedException {
		wait();
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
	 * @param file_name name of output file
	 */
	public void saveToFile(String file_name)
	{}
	
	

}
