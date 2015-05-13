/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.IRCEvent;

/**
 *
 * @author radek
 */
public class UserEvent extends IRCEvent{
    private final String login;
    public UserEvent(String login) {
        super("USER");
        this.login = login;
    }
    
    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.login + " 8 * :Java IRC Hacks Bot";
    }

	@Override
	public String generateDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
