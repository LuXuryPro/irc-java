/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.User;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    /**
     * @return user nickname
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public static User buildUserFromRawString(String raw_string) {
        if (raw_string.startsWith("@")) {
            String name = raw_string.substring(1);
            return new AdminUser(name);
        } else if (raw_string.startsWith("+")) {
            String name = raw_string.substring(1);
            return new VoiceUser(name);
        } else {
            String name = raw_string;
            return new NormalUser(name);
        }
    }

    @Override
    public boolean equals(Object o) {
        return this.name == ((User) o).getName();
    }
}
