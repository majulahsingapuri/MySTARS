package MySTARS;

/**
 * This is the starting point of the application
 * @author Bhargav, Kah Ee, Jia Hui, Nicolette, Timothy
 * @version 1.0
 * @since 2020-11-1
 */

public final class MySTARSApp {

    /**
     * Main function that is the starting point of the application. Initialises a new {@link MySTARS.Database} and loads in the first view.
     * @param args Arguments passsed to the App.
     */
    public static void main( String[] args ) {
        new Database();
        LoginView view = new LoginView();
        view.print();
    }
}
