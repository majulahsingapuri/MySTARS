package MySTARS;

import java.util.Scanner;
import org.joda.time.DateTime;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class Helper {
    
    protected static Scanner sc = new Scanner(System.in);

    //TODO should this be a static method?
    protected void load() {

        //TODO Change to our loading bars
        System.out.printf("║║║║║║║║║║║║");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    protected static DateTime formatTime(DayOfWeek day, int hour_24, int minute) {
        int dayInt;
        switch(day){
            case MONDAY:
                dayInt = 1;
                break;
            case TUESDAY:
                dayInt = 2;
                break;
            case WEDNESDAY:
                dayInt = 3;
                break;
            case THURSDAY:
                dayInt = 4;
                break;
            case FRIDAY:
                dayInt = 5;
                break;
            case SATURDAY:
                dayInt = 6;
                break;
            default:
                dayInt = 7;
                break;
        }

        String dateStr = "2020-06-0" + dayInt + "T" + hour_24 + ":" + minute + ":00";

        return new DateTime(dateStr);
    }

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
			message.setText("Dear " + receipient.getFirstName() + ","
				+ "\n\nYou have successfully been added to: " + course + "!\n\nThis is a system generated email.\n\nRegards,\nMySTARS ADMIN");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

    }
}
