package model.ircevent;

public class TopicEvent extends IRCEvent {
	private String topic;

	public TopicEvent(String channel, String topic) {
		super("TOPIC", channel);
		this.topic = topic;
		
	}

	@Override
	public String generateRawString() {
		return null;
	}

	@Override
	public String generateDisplayString() {
		return null;
	}
	public String getTopic() {
		return topic;
	}

}
