package MySTARS;

/**
 * This is the starting point of our application
 * @author Bhargav, Kah Ee, Jia Hui, Nicolette, Timothy
 */

//TODO Deserialise files on ititiation step
public final class MySTARSApp {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        LoginView view = new LoginView();
        view.print();
    }
}
