/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import controller.Controller;


public class View {
	private final JFrame frame;
	private final ConnectionList connectionList;
	private final UsersList usersList;
	private final JPanel channel_panel;
	private final JTextField entry;
	private final JLabel topic;
	private final JLabel users_count;
	private final JLabel nick;
	private final JEditorPane text;
	private final JScrollPane text_scroll;

	/**
	 * @return ConnectionList object of view
	 */
	public ConnectionList getConnectionList() {
		return connectionList;
	}

	/**
	 * @return UsersList object
	 */
	public UsersList getUsersList() {
		return usersList;
	}

	/**
	 * @return main text field object
	 */
	public JTextField getEntry() {
		return entry;
	}

	/**
	 * @return main text object
	 */
	public JEditorPane getText() {
		return text;
	}

	/**
	 * @return text scroll object
	 */
	public JScrollPane getText_scroll() {
		return text_scroll;
	}
		
	public View() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5,5));
		frame.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.connectionList = new ConnectionList();
		this.frame.add(connectionList,BorderLayout.WEST);
		this.entry = new JTextField();
		this.entry.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.entry.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.entry.setCaretColor(Color.WHITE);
		this.entry.setFont(new Font("Monospace", Font.PLAIN, 15));
		this.nick = new JLabel("",SwingConstants.CENTER);
		this.nick.setOpaque(true);
		this.nick.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.nick.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.nick.setPreferredSize(new Dimension(50, 20));
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout(5,5));
		p3.add(nick,BorderLayout.WEST);
		p3.add(entry,BorderLayout.CENTER);
		this.usersList = new UsersList();
		this.usersList.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.usersList.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.text = new JEditorPane();
		this.text.setContentType("text/html");
		this.text.setEditable(false);
		this.text.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.text.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.text_scroll = new JScrollPane(this.text,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(5,5));
		this.topic = new JLabel();
		this.topic.setOpaque(true);
		this.topic.setPreferredSize(new Dimension(0, 20));
		this.topic.setFont(new Font("Monospace", Font.PLAIN, 15));
		this.topic.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.topic.setForeground(new Color(0xFF, 0xFF, 0xFF));
		p.add(this.topic, BorderLayout.NORTH);
		p.add(this.text_scroll, BorderLayout.CENTER);
		this.channel_panel = new JPanel();
		this.channel_panel.setLayout(new BorderLayout(5,5));
		this.channel_panel.add(p3 ,BorderLayout.SOUTH);
		this.channel_panel.add(p, BorderLayout.CENTER);
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout(5,5));
		this.users_count = new JLabel("",SwingConstants.CENTER);
		this.users_count.setPreferredSize(new Dimension(0, 20));
		this.users_count.setOpaque(true);
		this.users_count.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.users_count.setForeground(new Color(0xFF, 0xFF, 0xFF));
		p2.add(this.users_count, BorderLayout.NORTH);
		p2.add(this.usersList,BorderLayout.CENTER);
		this.channel_panel.add(p2, BorderLayout.EAST);
		this.frame.add(this.channel_panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	/**
	 * Set action listener of View
	 * @param c Controller to delegate as listener
	 */
	public void setListener(Controller c) {
		this.entry.addActionListener(c);
		this.connectionList.setListener(c);
	}


	/**
	 * Set main text area to display given text
	 * @param text text to display
	 */
	public void setText(String text) {
		this.text.setText(text);
		this.text.setCaretPosition(this.text.getDocument().getLength());
	}
	public void setNick(String nick)
	{
		this.nick.setText(nick);
	}
	/**
	 * Set topic label to given text
	 * @param text text to set in topic label
	 */
	public void setTopic(String text) {
		this.topic.setText(text);
	}
	/**
	 * Clear main view area 
	 */
	public void clearText() {
		this.text.setText("");
	}
    public void dispose()
    {
    	this.frame.setVisible(false);
    	this.frame.dispose();
    }

	public JLabel getUsersCount() {
		return users_count;
	}
}
