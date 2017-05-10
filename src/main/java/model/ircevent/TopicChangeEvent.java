package model.ircevent;

public class TopicChangeEvent extends IRCEvent {
	private String user;
	private String topic;
	public TopicChangeEvent(String user, String channel, String topic) {
		super("TOPIC", channel);
		this.user = user;
		this.topic = topic;
	}

	public TopicChangeEvent(String channel, String topic) {
		this("", channel, topic);
		
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.channel + " :" + this.topic;
	}

	@Override
	public String generateDisplayString() {
		return String.format("*** Użytkownik %s zmieniena temat kanału na %s ***", this.user, this.topic); 
	}

	/**
	 * @return the topic
	 */
	public synchronized String getTopic() {
		return topic;
	}

}
