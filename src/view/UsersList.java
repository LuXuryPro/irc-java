package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class UsersList extends ScrollPane {

	public static void main(String[] args) {

		JFrame f = new JFrame("asd");
		f.setPreferredSize(new Dimension(800,600));
		UsersList u = new UsersList();
		f.add(u);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		u.addUser("@Radek");
		u.addUser("+asd");
		u.addUser("+Lasd");
		u.addUser("zasd");
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(ConnectionList.class.getName()).log(Level.SEVERE, null, ex);
		}
		u.removeUser("asd");
		u.updateUser("+Lasd", "@Lasd");
	}
	private final JList<String> list;
	private final DefaultListModel<String> list_model;

	public UsersList() {
		super();
		this.setPreferredSize(new Dimension(200,100));
		this.list = new JList<String>();
		this.list.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.list.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.list_model = new DefaultListModel<String>();
		this.list.setModel(list_model);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(list);
	}

	private void sort() {
		String admins[] = new String[this.list_model.size()];
		String voices[] = new String[this.list_model.size()];
		String users[] = new String[this.list_model.size()];
		int admins_i = 0;
		int voices_i = 0;
		int users_i = 0;
		for (int i = 0; i < this.list_model.size(); i++) {
			String u = (String) this.list_model.getElementAt(i);
			if (u != null) {
				if (u.startsWith("@")) {
					admins[admins_i] = u;
					admins_i++;
				} else if (u.startsWith("+")) {
					voices[voices_i] = u;
					voices_i++;
				} else {
					users[users_i] = u;
					users_i++;
				}
			}
		}
		Arrays.sort(admins, 0, admins_i, String.CASE_INSENSITIVE_ORDER);
		Arrays.sort(voices, 0, voices_i, String.CASE_INSENSITIVE_ORDER);
		Arrays.sort(users, 0, users_i, String.CASE_INSENSITIVE_ORDER);

		this.list_model.clear();
		for (int i = 0; i < admins.length && admins[i] != null; i++) {
			this.list_model.addElement(admins[i]);
		}
		for (int i = 0; i < voices.length && voices[i] != null; i++) {
			this.list_model.addElement(voices[i]);
		}
		for (int i = 0; i < users.length && users[i] != null; i++) {
			this.list_model.addElement(users[i]);
		}
	}

	public void addUser(String user) {
		this.list_model.addElement(user);
		this.sort();
	}

	public void updateUser(String old_user, String new_user) {
		this.removeUser(old_user);
		this.addUser(new_user);
	}

	public void removeUser(String user) {
		String u;
		if (user.startsWith("@") || user.startsWith("+")) {
			user = user.substring(1);
		}
		for (int i = 0; i < this.list_model.size(); i++) {
			u = (String) this.list_model.getElementAt(i);
			if (u.startsWith("@") || u.startsWith("+")) {
				u = u.substring(1);
			}
			if (u.equals(user)) {
				this.list_model.removeElementAt(i);
				this.sort();
				return;
			}
		}
	}
	public void clear()
	{
		this.list_model.clear();
	}
}

