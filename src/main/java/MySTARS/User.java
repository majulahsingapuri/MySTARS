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

    protected boolean changePassword() {

        while (true) {
            System.out.print("Enter current password or Q to quit: ");
            String oldPassword = Helper.getPasswordInput();
            if (oldPassword.equals("Q")) {
                break;
            }
            if (this.checkPassword(oldPassword)) {
                System.out.print("Enter new password: ");
                String newPassword1 = Helper.getPasswordInput();
                System.out.print("Enter the new password again: ");
                String newPassword2 = Helper.getPasswordInput();
                if (newPassword1.equals(newPassword2)){
                    this.password = newPassword1;
                    Database.serialise(FileType.USERS);
                    System.out.println("Password updated successfully.");
                    return true;
                } else {
                    System.out.println("The passwords you entered do not match. Please try again.");
                    Helper.pause();
                }
            } else {
                System.out.println("Invalid password!");
                Helper.pause();
            }
        }
        return false;
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
