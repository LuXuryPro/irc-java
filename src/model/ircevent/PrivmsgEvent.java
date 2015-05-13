package model.ircevent;

import java.util.Calendar;

public class PrivmsgEvent extends IRCEvent {

	private final String user;
	private final String msg;

	public PrivmsgEvent(String channel, String msg, String user) {
		super("PRIVMSG",channel);
		this.msg = msg;
		this.user = user;
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.channel + " :" + this.msg;
	}

	@Override
	public String generateDisplayString() {
		return String.format("[%02d:%02d] <%s> %s", this.timestamp.get(Calendar.HOUR_OF_DAY),
				this.timestamp.get(Calendar.MINUTE),this.user, this.msg);
	}

}
