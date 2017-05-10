package model.ircevent;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class IRCEvent {
	protected final String raw_string;
	protected final String channel;
	protected final Calendar timestamp;

	/**
	 * Create new event
	 * 
	 * @param raw_string
	 *            string form server
	 * @param timestamp
	 *            date and time of event
	 * @param channel
	 *            destination channel
	 */
	public IRCEvent(String raw_string, Calendar timestamp, String channel) {
		this.raw_string = raw_string;
		this.timestamp = timestamp;
		this.channel = channel;
	}

	/**
	 * Create new IRCEvent
	 * 
	 * @param raw_string
	 *            string form server (not parsed)
	 */
	public IRCEvent(String raw_string) {
		this(raw_string, new GregorianCalendar(), null);
	}

	/**
	 * Create IRCEvent
	 * 
	 * @param raw_string
	 *            string from server
	 * @param channel
	 *            destination channel
	 */
	public IRCEvent(String raw_string, String channel) {
		this(raw_string, new GregorianCalendar(), channel);
	}

	/**
	 * @return String readable for server
	 */
	public abstract String generateRawString();

	/**
	 * @return String that is human readable
	 */
	public abstract String generateDisplayString();

	/**
	 * @return destination channel of message
	 */
	public String getChannel() {
		return this.channel;
	}
}
