package Model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import Model.IRCEvent.IRCEvent;

public class Model {
	private ArrayList<Server> servers;
	private String default_nick;
	private boolean connected = false;

	private Server current_server = null;
	
	public Model(String default_nick) {
		super();
		this.default_nick = default_nick;
		this.servers = new ArrayList<Server>();
	}
	
	public synchronized String getDefaultNick() {
		return default_nick;
	}

	public synchronized void setDefaultNick(String default_nick) {
		this.default_nick = default_nick;
	}
	
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
	
	public synchronized void waitIsConnected()
	{
		while(!this.connected)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public synchronized Server getCurrentServer() {
		return this.current_server;
	}

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
	
	public void sendEvent(IRCEvent e){
		this.current_server.sendEvent(e);
	}
}
