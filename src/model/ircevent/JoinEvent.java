package model.ircevent;

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
		return String.format("<font color = '#87FF00'>--&gt;Użytkownik %s wchodzi na kanał %s</font>", this.user, this.channel); 
	}

	public synchronized String getUser() {
		return user;
	}

}
