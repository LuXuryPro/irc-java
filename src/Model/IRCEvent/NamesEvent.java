package Model.IRCEvent;

import java.util.ArrayList;

import Model.User;

public class NamesEvent extends IRCEvent {
	private ArrayList<User> nicks;
	public NamesEvent(String channel) {
		super("NAMES", channel);
		this.nicks = new ArrayList<User>();
	}

	@Override
	public String generateRawString() {
		return null;
	}

	@Override
	public String generateDisplayString() {
		return this.generateRawString();
	}


	public ArrayList<User> getNicks() {
		return nicks;
	}
}
