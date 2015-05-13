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
public class RAWEvent extends IRCEvent{

	public RAWEvent(String raw_string) {
		super(raw_string);
	}

	@Override
	public String generateRawString() {
		return this.raw_string;
	}

	@Override
	public String generateDisplayString() {
		return this.generateRawString();
	}

   
}
