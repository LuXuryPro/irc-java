package model.ircevent;

public class ServerInfoEvent extends IRCEvent {
	public ServerInfoEvent(String raw_string) {
		super(raw_string,null);
	}

	@Override
	public String generateRawString() {
		return null;
	}

	@Override
	public String generateDisplayString() {
		return this.raw_string;
	}

}
