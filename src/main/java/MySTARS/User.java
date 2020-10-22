package MySTARS;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 29L;
    private String username;
    private AccessLevel accessLevel;
    private String password;

    protected User(String username, String password, AccessLevel accessLevel) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    protected User(String username, AccessLevel accessLevel) {
        this.username = username;
        this.accessLevel = accessLevel;
        this.password = "OODP1s7heB3st";
    }

    protected boolean changePassword(String newPassword) {
        //reinput password to ensure correct person changing password
        password = newPassword;
        return true;
        //if wrong password, return false
    }

    protected boolean checkPassword(String input) {
        return input.equals(password);
    }

    protected boolean changeAccessLevel(AccessLevel accessLevel) {
        //reinput password
        this.accessLevel = accessLevel;
        return true;
        //else return false
    }

    protected String getUsername() {
        return username;
    }

    protected AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
