package model;

import model.User.AdminUser;
import model.User.NormalUser;
import model.User.User;
import model.User.VoiceUser;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void testIfNormalUserWorks() {
        String data = "normal user";
        User user = User.buildUserFromRawString(data);
        assertTrue(user instanceof NormalUser);
        assertEquals(data, user.getName());
    }

    @Test
    public void testIfVoiceUserWorks() {
        String data = "+voice user";
        User user = User.buildUserFromRawString(data);
        assertTrue(user instanceof VoiceUser);
        assertEquals(data.substring(1), user.getName());
    }

    @Test
    public void testIfAdminUserWorks() {
        String data = "@admin user";
        User user = User.buildUserFromRawString(data);
        assertTrue(user instanceof AdminUser);
        assertEquals(data.substring(1), user.getName());

    }

}