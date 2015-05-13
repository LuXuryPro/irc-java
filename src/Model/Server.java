/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import Controler.Controller;
import Model.IRCEvent.IRCEvent;
import Model.IRCEvent.JoinEvent;
import Model.IRCEvent.NickEvent;
import Model.IRCEvent.PingEvent;
import Model.IRCEvent.PongEvent;
import Model.IRCEvent.PrivmsgEvent;
import Model.IRCEvent.RAWEvent;
import Model.IRCEvent.UserEvent;

/**
 *
 * @author radek
 */
public class Server {
	private Channel root_channel;
	private Channel current_channel;
	private ArrayList<Channel> channels;
	private String nick;
	private String host;
	private int port;
	private Socket socket;
	private Thread out_thread;
	private Thread in_thread;
	private BufferedWriter writer;
	private BufferedReader reader;
	BlockingQueue<IRCEvent> output_events;
	BlockingQueue<IRCEvent> register_events;
	private boolean registred;

	
	public Server(String nick, String host) throws UnknownHostException, IOException {
		this(nick,host,6667);
	}
	
	public Server(String nick, String host, int port) throws UnknownHostException, IOException {
		super();
		this.nick = nick;
		this.host = host;
		this.port = port;
		this.socket = new Socket(host, port);
		this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.output_events = new LinkedBlockingQueue<>();
		this.register_events = new LinkedBlockingDeque<>();
		this.out_thread = new Thread(new Runnable() {
			@Override
			public void run() {
				out();
			}
		});
		this.in_thread = new Thread(new Runnable() {
			@Override
			public void run() {
				in();
			}
		});
		this.channels = new ArrayList<Channel>();
		this.root_channel = new Channel("");
		this.current_channel = this.root_channel;
	}
	
	public void connect() {

		this.in_thread.start();
		this.out_thread.start();
	}
	
	private void in() {
		String line;
		while (true) {
			try {
				if ((line = this.reader.readLine()) != null) {
					if (line.startsWith("PING ")) {
						IRCEvent e = new PingEvent(line.substring(5));
						this.sendEvent(new PongEvent(((PingEvent) e).getId()));
					} else if (line.contains("004")) {
						this.setRegistred(true);
						this.out_thread.interrupt();
					}
					else if (line.startsWith(":")) {
						IRCEvent e = Parser.parse(line);
						this.getChannel(e.getChannel()).addEvent(e);
					}
				}
			} catch (IOException ex) {
				break;
			}
		}
	}

	private void out() {
        this.sendEvent(new UserEvent(this.nick));
        this.sendEvent(new NickEvent(this.nick));
		while (true) {
			try {
				IRCEvent m;
				if (this.isRegistred()) {
					m = output_events.take();
				} else {
					m = register_events.take();
				}
				if (m != null) {
					if (m instanceof JoinEvent)
						this.channels.add(new Channel(((JoinEvent) m).getChannelName()));
					else if (m instanceof PrivmsgEvent)
						this.getCurrentChannel().addEvent(m);
					writer.write("" + m.generateRawString() + "\r\n");
					writer.flush();
				}

			} catch (IOException | InterruptedException ex) {
			}
		}

	}
	
	
	public synchronized boolean isRegistred() {
		return this.registred;
	}

	public synchronized void setRegistred(boolean registred) {
		this.registred = registred;
	}
	
	public void sendEvent(IRCEvent m) {

		if (!this.isRegistred() && (m instanceof NickEvent
			|| m instanceof UserEvent
			|| m instanceof PongEvent)) {
			register_events.offer(m);
		} else {
			output_events.offer(m);
		}
	}
	
	public synchronized Channel getCurrentChannel() {
		return current_channel;
	}

	public synchronized void setCurrentChannel(String name) {
		if (name == null) {
			this.current_channel = this.root_channel;
			return;
		}
		
		for (int i = 0; i < this.channels.size(); i++)
		{
			if (this.channels.get(i).getName() == name)
			{
				this.current_channel = this.channels.get(i);
				return;
			}
		}
	}

	public synchronized Channel getChannel(String name) {
		if (name == null) {
			return this.root_channel;
		}
		
		for (int i = 0; i < this.channels.size(); i++)
		{
			if (this.channels.get(i).getName().equals(name))
			{
				return this.channels.get(i);
			}
		}
		return this.root_channel;
	}
	
	public synchronized String getHost() {
		return host;
	}
	
	public void addChannel(String name)
	{
		this.channels.add(new Channel(name));
	}
	
	public void removeChannel(String name)
	{
		if (name == null) {
			return;
		}
		
		for (int i = 0; i < this.channels.size(); i++)
		{
			if (this.channels.get(i).getName().equals(name))
			{
				this.channels.remove(i);
				return;
			}
		}
	}
	public synchronized String getNick() {
		return nick;
	}
}
