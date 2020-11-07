package MySTARS;

/**
 * This is the starting point of our application
 * @author Bhargav, Esther, Jia Hui, Nicolette, Timothy
 */

//TODO Deserialise files on ititiation step
public final class MySTARSApp {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        Student student = new Student("BHARGAV002", "U1920026B", "Bhargav", "Singapuri");

        Helper.sendMailNotification(student, "CZ2002");
    }
}
