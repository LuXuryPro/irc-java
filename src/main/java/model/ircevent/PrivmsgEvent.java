package model.ircevent;

import model.Channel;

import java.util.Calendar;

public class PrivmsgEvent extends IRCEvent {

	protected final String user;
	protected String msg;
	protected boolean imortant = false;

	public PrivmsgEvent(String channel, String msg, String user) {
		super("PRIVMSG", channel);
		this.msg = msg;
		this.user = user;
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.channel + " :" + this.msg;
	}

	@Override
	public String generateDisplayString() {
		String b = "";
		String eb = "";
		if (this.imortant)
		{
			b = "<b><u>";
			eb = "</u></b>";
		}
		return String
				.format(b + "[%02d:%02d] <font color = '#5FD7FF'>&lt;%s&gt;</font> %s" + eb,
						this.timestamp.get(Calendar.HOUR_OF_DAY),
						this.timestamp.get(Calendar.MINUTE), this.user,
						this.msg.replaceAll("Kappa", "<img src = 'https://static-cdn.jtvnw.net/emoticons/v1/25/1.0'></img>")
						.replaceAll("SSSsss", "<img src = 'https://static-cdn.jtvnw.net/emoticons/v1/46/1.0'></img>") 
						);
	}

	@Override
	public void visit(Channel channel) {

	}

	public String getMsg() {
		return msg;
	}

	public void setImortant(boolean imortand) {
		this.imortant = imortand;
	}
	
}
