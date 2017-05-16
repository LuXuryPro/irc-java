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
    private final DefaultListModel<String> list_model;

	public UsersList() {
		super();
		this.setPreferredSize(new Dimension(200,100));
        JList<String> list = new JList<String>();
		list.setBackground(new Color(0x1C, 0x1C, 0x1C));
		list.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.list_model = new DefaultListModel<String>();
		list.setModel(list_model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

	/**
	 * Add user to list view
	 * @param user
	 */
	public void addUser(String user) {
		this.list_model.addElement(user);
		this.sort();
	}

	/**
	 * Replace user with new one
	 * @param old_user
	 * @param new_user
	 */
	public void updateUser(String old_user, String new_user) {
		this.removeUser(old_user);
		this.addUser(new_user);
	}

	/**
	 * Remove user form view by name 
	 * @param user
	 */
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
	/**
	 * Remove all users form view
	 */
	public void clear()
	{
		this.list_model.clear();
	}
}

