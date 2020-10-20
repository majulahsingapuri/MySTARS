package MySTARS;

public class Admin extends User {
    private String defaultPasswrd = "OODP1sth3B3ST!";

    public Admin(String username){
        super(username,defaultPasswrd);
    }

    public Admin(String username, String password){
        super(username, password);
    }
}
