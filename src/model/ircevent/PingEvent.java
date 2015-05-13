/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ircevent;

import java.util.Calendar;

/**
 *
 * @author radek
 */
public class PingEvent extends IRCEvent{
   private final String id;
    public PingEvent(String id) {
        super("PING");
        this.id = id;
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.id ;
    }

    public String getId() {
        return id;
    }

	@Override
	public String generateDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
