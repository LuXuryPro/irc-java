/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ircevent;

import model.Channel;

public class NickEvent extends IRCEvent {
    private final String new_nick;
    private final String old_nick;

    public NickEvent(String new_nick) {
        this("", new_nick);
    }

    public NickEvent(String old_nick, String new_nick) {
        super("NICK");
        this.new_nick = new_nick;
        this.old_nick = old_nick;
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.new_nick;
    }

    @Override
    public String generateDisplayString() {
        return String
                .format("<font color = '#FF8700'> Użytkownik %s zmienia nick na %s</font>",
                        this.old_nick, this.new_nick);
    }

    @Override
    public void visit(Channel channel) {
        channel.getUserByName(this.getOldNick()).setName(this.getNewNick());
    }

    public String getNewNick() {
        return new_nick;
    }

    public String getOldNick() {
        return old_nick;
    }

}
