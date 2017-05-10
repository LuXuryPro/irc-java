package model.ircevent;
import model.User.UserMode;

public class ModeEvent extends IRCEvent {
	private final String user;
	private final String new_mode;
	private final String affected_user;

	public ModeEvent(String user, String channel, String affected_user,
			String new_mode) {
		super("MODE", channel);
		this.user = user;
		this.new_mode = new_mode;
		this.affected_user = affected_user;
	}

	@Override
	public String generateRawString() {
		return this.raw_string  + " " + this.channel + " " + this.new_mode + " " + this.affected_user;
	}

	@Override
	public String generateDisplayString() {
		return String
				.format("<font color = '#FFFFFF'>--&gt;Użytkownik %s zmienia tryb użytkownika %s na %s</font>",
						this.user, this.affected_user, this.new_mode);
	}

	public String getUser() {
		return user;
	}

	public String getNewMode() {
		return new_mode;
	}

	public String getAffectedUser() {
		return affected_user;
	}

}
