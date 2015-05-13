/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ircevent;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author radek
 */
public abstract class IRCEvent {
    protected final String raw_string;
    protected final String channel;
    protected final Calendar timestamp;
   
    public IRCEvent(String raw_string, Calendar timestamp, String channel) {
        this.raw_string = raw_string;
        this.timestamp = timestamp;
        this.channel = channel;
    }
    public IRCEvent(String raw_string) {
        this(raw_string, new GregorianCalendar(),null);
    }

    public IRCEvent(String raw_string, String channel) {
        this(raw_string, new GregorianCalendar(),channel);
    }
    public abstract String generateRawString();
    public abstract String generateDisplayString();
    public String getChannel()
    {
    	return this.channel;
    }
}
