package model.ircevent;

import model.Channel;
import model.User.AdminUser;
import model.User.NormalUser;
import model.User.VoiceUser;

public class ModeEvent extends IRCEvent {
    private final String user;
    private final String new_mode;
    private final String affected_user;

    public ModeEvent(String user, String channel, String affected_user,
                     String new_mode) {
        super("MODE", channel);
        this.user = user;
        this.new_mode = new_mode;
        this.affected_user = affected_user;
    }

    @Override
    public String generateRawString() {
        return this.raw_string + " " + this.channel + " " + this.new_mode + " " + this.affected_user;
    }

    @Override
    public String generateDisplayString() {
        return String
                .format("<font color = '#FFFFFF'>--&gt;Użytkownik %s zmienia tryb użytkownika %s na %s</font>",
                        this.user, this.affected_user, this.new_mode);
    }

    @Override
    public void visit(Channel channel) {
        String new_mode = this.getNewMode();
        String au = this.getAffectedUser();
        if (channel.getUserByName(au) != null) {
            if (new_mode.equals("+o")) {
                AdminUser adminUser = new AdminUser(au);
                channel.removeUser(au);
                channel.getUsers().add(adminUser);
            } else if (new_mode.equals("+v")) {
                VoiceUser voiceUser = new VoiceUser(au);
                channel.removeUser(au);
                channel.getUsers().add(voiceUser);
            } else if (new_mode.equals("-o")) {
                NormalUser normalUser = new NormalUser(au);
                channel.removeUser(au);
                channel.getUsers().add(normalUser);
            } else if (new_mode.equals("-v")) {
                NormalUser normalUser = new NormalUser(au);
                channel.removeUser(au);
                channel.getUsers().add(normalUser);
            }
        }
    }

    public String getUser() {
        return user;
    }

    public String getNewMode() {
        return new_mode;
    }

    public String getAffectedUser() {
        return affected_user;
    }

}
