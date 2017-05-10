package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import model.ircevent.QuitEvent;
import model.ircevent.IRCEvent;
import model.ircevent.JoinEvent;
import model.ircevent.NickEvent;
import model.ircevent.PingEvent;
import model.ircevent.PongEvent;
import model.ircevent.PrivmsgEvent;
import model.ircevent.UserEvent;

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
    private boolean disconnecting = false;

    /**
     * @param nick
     *            nick to set on server
     * @param host
     *            host name
     * @throws UnknownHostException
     * @throws IOException
     */
    public Server(String nick, String host) throws UnknownHostException, IOException {
        this(nick, host, 6667);
    }

    /**
     * @param nick
     *            nick to set on server
     * @param host
     *            host name
     * @param port
     *            port of host
     * @throws UnknownHostException
     * @throws IOException
     */
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

    /**
     * connect to server
     */
    public void connect() {
        this.in_thread.start();
        this.out_thread.start();
    }

    public void disconnect() {
        try {
            this.disconnecting = true;
            this.in_thread.interrupt();
            this.out_thread.interrupt();
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) {
        }
    }

    private void in() {
        String line;
        while (true) {
            try {
                if ((line = this.reader.readLine()) != null) {
                    if (line.startsWith("PING ")) {
                        IRCEvent e = new PingEvent(line.substring(5));
                        this.sendEvent(new PongEvent(((PingEvent) e).getId()));
                    } else if (line.startsWith(":")) {
                        if (line.contains("004")) {
                            this.setRegistred(true);
                            this.out_thread.interrupt();
                        }
                        IRCEvent e = Parser.parse(line);
                        if (e != null) {
                            if (e instanceof PrivmsgEvent) {
                                if (((PrivmsgEvent) e).getMsg().contains(this.nick))
                                    ((PrivmsgEvent) e).setImortant(true);
                            } else if (e instanceof NickEvent) {
                                for (Channel c : this.channels) {
                                    if (c.getUserByName(((NickEvent) e).getOldNick()) != null) {
                                        c.addEvent(e);
                                    }
                                }
                                if (((NickEvent) e).getOldNick().equals(this.nick))
                                    this.nick = ((NickEvent) e).getNewNick();
                                continue;
                            }
                            this.getChannel(e.getChannel()).addEvent(e);
                        }
                    }
                }
            } catch (IOException ex) {
                if (this.disconnecting)
                    break;
            }
        }
    }

    private void out() {
        this.sendEvent(new UserEvent(this.nick, "real_name"));
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
                    if (m instanceof QuitEvent)
                        this.disconnecting = true;
                }

            } catch (IOException | InterruptedException ex) {
                if (this.disconnecting)
                    break;
            }
        }
    }

    /**
     * Check if user is registered on server
     *
     * @return true if user is registered
     */
    public synchronized boolean isRegistred() {
        return this.registred;
    }

    /**
     * Set if user is registered
     *
     * @param registred
     *            new boolean registered value
     */
    public synchronized void setRegistred(boolean registred) {
        this.registred = registred;
    }

    /**
     * Send event to server
     *
     * @param m
     *            Event to send
     */
    public void sendEvent(IRCEvent m) {
        if (!this.isRegistred()
            && (m instanceof NickEvent || m instanceof UserEvent || m instanceof PongEvent)) {
            register_events.offer(m);
        } else {
            output_events.offer(m);
        }
    }

    /**
     * @return server's current channel selected for communication
     */
    public synchronized Channel getCurrentChannel() {
        return current_channel;
    }

    /**
     * Set channel to be current
     *
     * @param name
     *            name of channel to set
     */
    public synchronized void setCurrentChannel(String name) {
        if (name == null) {
            this.current_channel = this.root_channel;
            return;
        }

        for (int i = 0; i < this.channels.size(); i++) {
            if (this.channels.get(i).getName() == name) {
                this.current_channel = this.channels.get(i);
                return;
            }
        }
    }

    /**
     * Get channel by name
     *
     * @param name
     *            name of channel
     * @return Channel of given name
     */
    public synchronized Channel getChannel(String name) {
        if (name == null) {
            return this.root_channel;
        }

        for (int i = 0; i < this.channels.size(); i++) {
            if (this.channels.get(i).getName().equals(name)) {
                return this.channels.get(i);
            }
        }
        return this.root_channel;
    }

    /**
     * @return server's host name
     */
    public synchronized String getHost() {
        return host;
    }

    /**
     * Create new channel
     *
     * @param name
     *            name of new channel
     */
    public void addChannel(String name) {
        this.channels.add(new Channel(name));
    }

    /**
     * Remove channel from server by name
     *
     * @param name
     *            name of channel to remove
     */
    public void removeChannel(String name) {
        if (name == null) {
            return;
        }

        for (int i = 0; i < this.channels.size(); i++) {
            if (this.channels.get(i).getName().equals(name)) {
                this.channels.remove(i);
                return;
            }
        }
    }

    /**
     * @return nick of user on server
     */
    public synchronized String getNick() {
        return nick;
    }
}
