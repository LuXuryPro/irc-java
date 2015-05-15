package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import view.View;
import model.Model;
import model.User;
import model.ircevent.ModeEvent;
import model.ircevent.QuitEvent;
import model.ircevent.IRCEvent;
import model.ircevent.JoinEvent;
import model.ircevent.NickEvent;
import model.ircevent.PartEvent;
import model.ircevent.PrivmsgEvent;
import model.ircevent.TopicChangeEvent;

/**
 * Controller class of MVC
 */
public class Controller implements TreeSelectionListener, ActionListener {
	/**
	 * Start point of application
	 * 
	 * @param args
	 *            arguments from os
	 */
	public static void main(String[] args) {
		new Controller(new View(), new Model("ArP"));
	}

	private final View view;
	private final Model model;
	private final Thread main_thread;
	private boolean stop = false;

	/**
	 * @param v
	 *            View to control
	 * @param m
	 *            Model to use
	 */
	public Controller(View v, Model m) {
		this.view = v;
		this.model = m;
		this.view.setListener(this);
		this.main_thread = new Thread(new Runnable() {

			@Override
			public void run() {
				main_run();
			}
		});
		this.main_thread.start();
	}

	private void main_run() {
		while (true) {
			try {
				ArrayList<IRCEvent> e;
				model.waitIsConnected();
				e = this.model.getCurrentServer().getCurrentChannel()
						.waitForEvents();
				this.update_gui(e);
			} catch (InterruptedException e1) {
				if(this.model.getCurrentServer() != null)
					this.update_gui(this.model.getCurrentServer().getCurrentChannel().getEvents());
				else
					this.view.setText("");
				if (this.stop)
					break;
			}
		}
	}

	private void update_gui(final ArrayList<IRCEvent> e) {
		if (e == null)
			return;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StringBuffer b = new StringBuffer();
				b.append("<font color = 'white' face = 'monospace'>");
				for (IRCEvent ev : e)
					if (ev.generateDisplayString() != null)
						b.append(ev.generateDisplayString() + "<br>");
				b.append("</font>");
				view.setText(b.toString());
				view.getUsersList().clear();
				for (User u : model.getCurrentServer().getCurrentChannel()
						.getUsers())
					view.getUsersList().addUser(u.toString());
				view.setTopic(model.getCurrentServer().getCurrentChannel()
						.getTopic());
			}
		});
	}

	@Override
	public void valueChanged(final TreeSelectionEvent tse) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (tse.getNewLeadSelectionPath() != null)
					if (tse.getNewLeadSelectionPath().getPath().length == 2) {
						String s = tse.getNewLeadSelectionPath().getPath()[1]
								.toString();
						model.setCurrentServer(s);
						model.getCurrentServer().setCurrentChannel(null);
						main_thread.interrupt();
					} else if (tse.getNewLeadSelectionPath().getPath().length == 3) {
						String s = tse.getNewLeadSelectionPath().getPath()[1]
								.toString();
						String c = tse.getNewLeadSelectionPath().getPath()[2]
								.toString();
						model.setCurrentServer(s);
						model.getCurrentServer().setCurrentChannel(c);
						main_thread.interrupt();
					}
			}
		});

	}

	@Override
	public void actionPerformed(final ActionEvent ae) {
		((JTextField) (ae.getSource())).setText("");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String command = ae.getActionCommand().trim();
				if (command.startsWith("/connect")) {
					String[] server = command.split(" ");
					String host = server[1];
					int status = model.connectToServer(host);
					if (status == 0) {
						view.getConnectionList().addServer(host);
						main_thread.interrupt();
					}
				} else if (command.startsWith("/disconnect")) {
					model.sendEvent(new QuitEvent(""));
					view.getConnectionList().removeServer(model.getCurrentServer().getHost());
					view.getUsersList().clear();
					model.removeServer(model.getCurrentServer().getHost());
					main_thread.interrupt();
				} else if (command.startsWith("/exit")) {
					model.clear();
					stop = true;
					main_thread.interrupt();
					view.dispose();
				} else if (command.startsWith("/nick")) {
					String[] nick = command.split(" ");
					String n_nick = nick[1];
					model.sendEvent(new NickEvent(n_nick));
				} else if (command.startsWith("/join")) {
					String[] nick = command.split(" ");
					String n_nick = nick[1].trim();
					model.sendEvent(new JoinEvent(n_nick, model
							.getCurrentServer().getNick()));
					view.getConnectionList().addChannel(
							model.getCurrentServer().getHost(), n_nick);
				} else if (command.startsWith("/mode")) {
					String[] nick = command.split(" ");
					String n_nick = nick[1].trim();
					String new_m = nick[2].trim();
					model.sendEvent(new ModeEvent("", model.getCurrentServer().getCurrentChannel().getName(), n_nick, new_m));
				} else if (command.startsWith("/part")) {
					String[] nick = command.split(" ");
					String n_nick;
					if (nick.length == 2)
						n_nick = nick[1].trim();
					else
						n_nick = model.getCurrentServer().getCurrentChannel()
								.getName();
					model.sendEvent(new PartEvent(n_nick, model
							.getCurrentServer().getNick()));
					model.getCurrentServer().setCurrentChannel(null);
					model.getCurrentServer().removeChannel(n_nick);
					view.getConnectionList().removeChannel(
							model.getCurrentServer().getHost(), n_nick);
					view.getConnectionList().selectServer(
							model.getCurrentServer().getHost());
				} else if (command.startsWith("/topic ")) {
					String c = model.getCurrentServer().getCurrentChannel()
							.getName();
					model.getCurrentServer().sendEvent(
							new TopicChangeEvent(c, command.substring(7)));
				}

				else
					model.sendEvent(new PrivmsgEvent(model.getCurrentServer()
							.getCurrentChannel().getName(), command, model
							.getCurrentServer().getNick()));
			}
		});
	}
}
