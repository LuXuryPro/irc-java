package model.ircevent;

public class RAWEvent extends IRCEvent{

	public RAWEvent(String raw_string) {
		super(raw_string);
	}

	@Override
	public String generateRawString() {
		return this.raw_string;
	}

	@Override
	public String generateDisplayString() {
		return this.generateRawString();
	}

   
}
