/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author radek
 */
public class User {
	private String name;
	public enum UserMode {NORMAL,VOICE,OP};
	private UserMode mode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserMode getMode() {
		return mode;
	}

	public void setMode(UserMode mode) {
		this.mode = mode;
	}

	public User(String name, UserMode mode) {
		this.name = name;
		this.mode = mode;
	}
	
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
