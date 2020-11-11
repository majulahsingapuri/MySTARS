package MySTARS;

/**
 * This is the starting point of our application
 * @author Bhargav, Kah Ee, Jia Hui, Nicolette, Timothy
 */

public final class MySTARSApp {
    public static void main( String[] args ) {
        new Database();
        LoginView view = new LoginView();
        view.print();
    }
}
