package model.ircevent;

import model.Channel;

public class QuitEvent extends IRCEvent {
	private final String exit_message;

	public QuitEvent(String exit_message) {
		super("QUIT");
		this.exit_message = exit_message;
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.exit_message;
	}

	@Override
	public String generateDisplayString() {
		return null;
	}

	@Override
	public void visit(Channel channel) {

	}

}
