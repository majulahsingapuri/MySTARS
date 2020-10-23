package MySTARS;

public final class Admin extends User {

    private static final long serialVersionUID = 26L;

    public Admin(String username){
        super(username, AccessLevel.ADMIN);
    }

    public Admin(String username, String password){
        super(username, password, AccessLevel.ADMIN);
    }
}
