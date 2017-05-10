/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class User {
	private String name;
	/**
	 * User ranks on channel
	 */
	public enum UserMode {NORMAL,VOICE,OP};
	private UserMode mode;

	/**
	 * @return user nickname
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set user name
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return user mode
	 */
	public UserMode getMode() {
		return mode;
	}

	/**
	 * Set user mode
	 * @param mode mode to set
	 */
	public void setMode(UserMode mode) {
		this.mode = mode;
	}

	/**
	 * Create new user
	 * @param name nick of user
	 * @param mode user rank in channel
	 */
	public User(String name, UserMode mode) {
		this.name = name;
		this.mode = mode;
	}
	
	/**
	 * Create new user
	 * @param raw_string - raw sting from server
	 */
	public User(String raw_string) {
		if (raw_string.startsWith("@"))
		{
			this.mode = UserMode.OP;
			this.name = raw_string.substring(1);
		}
		else if (raw_string.startsWith("+"))
		{
			this.mode = UserMode.VOICE;
			this.name = raw_string.substring(1);
		}
		else
		{
			this.mode = UserMode.NORMAL;
			this.name = raw_string;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String m = null;
		if (this.mode == UserMode.NORMAL)
			m = "";
		else if (this.mode == UserMode.VOICE)
			m = "+";
		else if (this.mode == UserMode.OP)
			m = "@";
		return m +this.name;
	}
}
