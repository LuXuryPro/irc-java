package model.ircevent;

import model.Channel;

public class PingEvent extends IRCEvent {
	private final String id;

	public PingEvent(String id) {
		super("PING");
		this.id = id;
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String generateDisplayString() {
		return null;
	}

	@Override
	public void visit(Channel channel) {

	}

}
