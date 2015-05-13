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
public class NickEvent extends IRCEvent{
    private final String nick;

    public NickEvent(String nick) {
        super("NICK");
        this.nick = nick;
    }
    
    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.nick ;
    }

	@Override
	public String generateDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
