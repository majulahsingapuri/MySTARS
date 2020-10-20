package MySTARS;

import java.io.Serializable;

public class User implements Serializable{
    private String username;
    protected AccessLevel accessLevel;
    protected String password;

    protected User(String username, String password){
        this.username = username;
        this.password = password;
    }

    protected boolean changePassword(String newPasswrd){
        //reinput password to ensure correct person changing password
        password = newPasswrd;
        return true;
        //if wrong password, return false
    }

    protected boolean checkPasswrd(String input){
        return input.equals(password);
    }

    protected boolean changeAccessLevel(AccessLevel al){
        //reinput password
        accessLevel = al;
        return true;
        //else return false
    }

    protected String getUsername(){
        return username;
    }
}
