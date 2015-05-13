/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.IRCEvent;

/**
 *
 * @author radek
 */
public class PartEvent extends IRCEvent{
    private final String message;
    private final String user;

    public PartEvent(String channel_name, String message, String user) {
        super("PART",channel_name);
        this.message = message;
        this.user = user;
    }

    public PartEvent(String channel_name, String user) {
        this(channel_name,"",user);
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.channel + " " + this.message;
    }

	@Override
	public String generateDisplayString() {
		return String.format("<--Użytkownik %s wychodzi z kanału %s [%s]", this.user, this.channel, this.message); 
	}
	public synchronized String getUser() {
		return user;
	}
}

