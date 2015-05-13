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
public class PongEvent extends IRCEvent{
    private final String id;
    public PongEvent(String id) {
        super("PONG");
        this.id = id;
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.id;
    }

	@Override
	public String generateDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
