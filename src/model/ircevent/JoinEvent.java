/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ircevent;

/**
 *
 * @author radek
 */
public class JoinEvent extends IRCEvent {

	private final String user;
	public JoinEvent(String channel_name,String user) {
		super("JOIN",channel_name);
		this.user = user;

	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.channel ;
	}

	public synchronized String getChannelName() {
		return channel;
	}

	@Override
	public String generateDisplayString() {
		return String.format("-->Użytkownik %s wchodzi na kanał %s", this.user, this.channel); 
	}

	public synchronized String getUser() {
		return user;
	}

}
