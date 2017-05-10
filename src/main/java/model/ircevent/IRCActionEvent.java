package model.ircevent;


public class IRCActionEvent extends PrivmsgEvent {

	public IRCActionEvent(String channel, String msg, String user) {
		super(channel, msg, user);
	}

	@Override
	public String generateRawString() {
		return this.raw_string + " " + this.channel + " :\u0001" + "ACTION " + this.msg
				+ '\u0001';
	}

	@Override
	public String generateDisplayString() {
		return String.format("<font color = '#AF5FFF'> *** %s %s ***</font> ",
				this.user, this.msg);
	}

}
