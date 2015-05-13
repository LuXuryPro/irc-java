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
import java.awt.Panel;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;

/**
 *
 * @author radek
 */
public class View {
	private final JFrame frame;
	private final ConnectionList connectionList;
	private final UsersList usersList;
	private final JPanel channel_panel;
	private final JTextField entry;
	private final JLabel topic;

	public ConnectionList getConnectionList() {
		return connectionList;
	}

	public UsersList getUsersList() {
		return usersList;
	}

	public JTextField getEntry() {
		return entry;
	}

	public JTextArea getText() {
		return text;
	}

	public JScrollPane getText_scroll() {
		return text_scroll;
	}
	private final JTextArea text;
	private final JScrollPane text_scroll;
		
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
		this.usersList = new UsersList();
		this.usersList.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.usersList.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.text = new JTextArea();
		this.text.setEditable(false);
		this.text.setBackground(new Color(0x1C, 0x1C, 0x1C));
		this.text.setForeground(new Color(0xFF, 0xFF, 0xFF));
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text_scroll = new JScrollPane(this.text,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
		this.channel_panel.add(entry ,BorderLayout.SOUTH);
		this.channel_panel.add(p, BorderLayout.CENTER);
		this.channel_panel.add(this.usersList, BorderLayout.EAST);
		this.frame.add(this.channel_panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public void setListener(Controller c) {
		this.entry.addActionListener(c);
		this.connectionList.setListener(c);
	}


	public void setText(String text) {
		this.text.setText(text);
		this.text.setCaretPosition(this.text.getDocument().getLength());
	}
	
	public void setTopic(String text) {
		this.topic.setText(text);
	}
	public void clearText() {
		this.text.setText("");
	}
    
}
