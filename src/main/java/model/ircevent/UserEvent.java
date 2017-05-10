package model.ircevent;

import model.Channel;

public class UserEvent extends IRCEvent{
    private final String login;
    private final String real_name;
    public UserEvent(String login, String real_name) {
        super("USER");
        this.login = login;
        this.real_name = real_name;
    }
    
    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.login + " 8 * :" + this.real_name;
    }

	@Override
	public String generateDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void visit(Channel channel) {

    }

}
