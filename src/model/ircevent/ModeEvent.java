package model.ircevent;

public class ModeEvent extends IRCEvent {

	public ModeEvent(String raw_string) {
		super(raw_string);
	}

	@Override
	public String generateRawString() {
		return null;
	}

	@Override
	public String generateDisplayString() {
		return null;
	}

}
