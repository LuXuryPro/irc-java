/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ircevent;

import model.Channel;

public class PartEvent extends IRCEvent {
    private final String message;
    private final String user;

    public PartEvent(String channel_name, String message, String user) {
        super("PART", channel_name);
        this.message = message;
        this.user = user;
    }

    public PartEvent(String channel_name, String user) {
        this(channel_name, "", user);
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.channel + " " + this.message;
    }

    @Override
    public String generateDisplayString() {
        return String
                .format("<font color = '#D7005F'> &lt;--Użytkownik %s wychodzi z kanału %s [%s]</font>",
                        this.user, this.channel, this.message);
    }

    @Override
    public void visit(Channel channel) {
        if (channel.getUserByName(this.getUser()) != null)
            channel.removeUser(this.getUser());
    }

    public synchronized String getUser() {
        return user;
    }
}
