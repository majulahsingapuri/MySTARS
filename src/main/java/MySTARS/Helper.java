package MySTARS;

import java.util.Scanner;
import java.io.Console;
import org.joda.time.DateTime;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A Helper Class with all properties and methods that are generally needed across the application.
 * @author Bhargav, Jia Hui
 * @version 1.0
 * @since 2020-11-1
 */
public final class Helper {
	
	/**
	 * A single Scanner item that reads input from console so that memory resources are efficiently used.
	 */
    protected static Scanner sc = new Scanner(System.in);

	/**
	 * A load function that prints progress bars across the screen at 500ms intervals.
	 */
    protected static void load() {

        System.out.printf("║║║║║║║║║║║║");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
	}
	
	/**
	 * A pause function that waits for the user to press the Enter key to proceed on with the next task.
	 */
	protected static void pause(){
		System.out.print("Press <Enter> to continue... ");
		sc.nextLine();
	}

	/**
	 * A date formatter that takes in the current day and time and returns a {@link org.joda.time.DateTime} object.
	 * @param day A DayOfWeek Enum corresponding to the day of the week.
	 * @param hour_24 The Hour in 24 hour format.
	 * @param minute The minutes of the hour.
	 * @return A DateTime Object.
	 */
    protected static DateTime formatTime(DayOfWeek day, int hour_24, int minute) {

        String dateStr = "2020-06-0" + day.value + "T" + hour_24 + ":" + minute + ":00";

        return new DateTime(dateStr);
    }

	/**
	 * A method that sends an email from the Administrative Gmail Account to the user if their course has been registered.
	 * @param receipient The network username of the student.
	 * @param course The String value of the course that they have been successfully registered for.
	 */
    protected static void sendMailNotification(Student receipient, String course) {
        String username = "MySTARSApp1";
        String password = "Il0veOOODP";

        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
        });
        
        try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipient.getUsername() + "@e.ntu.edu.sg"));
			message.setSubject("You have successfully been added to: " + course);
			message.setText("Dear " + receipient.getFirstName() + " " + receipient.getLastName() + ","
				+ "\n\nYou have successfully been added to: " + course + "!\n\nThis is a system generated email.\n\nRegards,\nMySTARS ADMIN");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

    }

	/**
	 * A method to read passowords from Console without displaying the characters on the screen.
	 * @return A String representing the password that was keyed in.
	 */
    protected static String getPasswordInput(){
		Console console = System.console();
		String password = null;
		try {
			char[] input = console.readPassword();
			password = String.copyValueOf(input);
		} catch (Exception e){
			password = Helper.sc.nextLine();
		}
		return password;
	}

	protected static boolean checkCourseCodeFormat(String courseCode) {

		if(courseCode.matches("\\p{Alpha}\\p{Alpha}\\d{4}") || courseCode.equals("Q")) {
			return true;
		} 
		System.out.println("Invalid input. Course code must be two letters followed by four digits");
		return false;
	}
}
