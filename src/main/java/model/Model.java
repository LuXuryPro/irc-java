package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.ircevent.IRCEvent;
import model.ircevent.QuitEvent;

public class Model {
	private ArrayList<Server> servers;
	private String default_nick;
	private boolean connected = false;

	private Server current_server = null;

	/**
	 * Create new model
	 * @param default_nick default nick to use in new server connections
	 */
	public Model(String default_nick) {
		super();
		this.default_nick = default_nick;
		this.servers = new ArrayList<Server>();
	}

	/**
	 * @return current default nick
	 */
	public synchronized String getDefaultNick() {
		return default_nick;
	}

	/**
	 * Set default nick
	 * @param default_nick nick to set to be default
	 */
	public synchronized void setDefaultNick(String default_nick) {
		this.default_nick = default_nick;
	}

	/**
	 * Connect model to new server
	 * @param host host to connect to
	 * @return 0 - success;
	 * -1 - if host Unknown;
	 * -2 - if IOException occured (on internet connection)
	 */
	public synchronized int connectToServer(String host) {
		try {
			Server s = new Server(this.default_nick, host);
			this.current_server = s;
			s.connect();
			this.servers.add(s);
			this.connected = true;
			this.notifyAll();
		} catch (UnknownHostException e) {
			return -1;
		} catch (IOException e) {
			return -2;
		}
		return 0;
	}

	/**
	 * Wait for any connection become active
	 * @throws InterruptedException
	 */
	public synchronized void waitIsConnected() throws InterruptedException
	{
		while(!this.connected)
				wait();
	}

	/**
	 * @return current active server
	 */
	public synchronized Server getCurrentServer() {
		return this.current_server;
	}

	/**
	 * Set current active server by host name
	 * @param host name of host
	 */
	public synchronized void setCurrentServer(String host) {
		for (int i = 0; i < this.servers.size(); i++)
		{
			if (this.servers.get(i).getHost().equals(host))
			{
                if (this.current_server != null)
                	this.current_server = this.servers.get(i);
                return;
			}
		}
	}

	public synchronized void removeServer(String host) {
		for (int i = 0; i < this.servers.size(); i++)
		{
			if (this.servers.get(i).getHost().equals(host))
			{
                if (this.current_server == this.servers.get(i))
                	this.current_server = null;
                this.servers.get(i).disconnect();
                this.servers.remove(i);
                if (this.servers.size() == 0)
                	this.connected = false;
                else
                	this.current_server = this.servers.get(0);
                return;
			}
		}
	}

	public void clear()
	{
		for (Server s: this.servers)
		{
			s.sendEvent(new QuitEvent(""));
			s.disconnect();
		}
	}
	/**
	 * Send event to current active server
	 * @param e event to send
	 */
	public void sendEvent(IRCEvent e){
		if (this.current_server != null)
			this.current_server.sendEvent(e);
	}

	public int getServerCount()
	{
		return this.servers.size();
	}
}
