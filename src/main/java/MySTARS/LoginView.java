package MySTARS;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Initial View seen by {@link User}. Used to log in the User to the System.
 * @author Bhargav, Kah Ee
 * @version 1.0
 * @since 2020-11-1
 */
public final class LoginView extends View {

    private static DateTime loginStart;
    private static DateTime loginEnd;

    /**
     * Constructor method that checks if the {@Student} login period has been set. Defaults to the whole of 2020.
     */
    public LoginView() {

        if (loginStart == null || loginEnd == null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
            LoginView.loginStart = formatter.parseDateTime("01/01/2020 00:00:00");
            LoginView.loginEnd = formatter.parseDateTime("31/12/2020 23:59:59");
        }
    }

    /**
     * Getter method for the currently set log in start time.
     * @return date in dd/MM/yyyy, time in HH:mm:ss.
     */
    public DateTime getStartTime() {
        
        return LoginView.loginStart;
    }

    /**
     * Getter method for the currently set log in end time.
     * @return date in dd/MM/yyyy, time in hh:mm:ss.
     */
    public DateTime getEndTime() {
        
        return LoginView.loginEnd;
    }

    /**
     * Displays Login view for {@link User}, checks the credentials and then logs in the User if the credentials are valid.
     */
    public void print() {

        clearScreen("Login");

        DateTime now = DateTime.now();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        String password, username, domain;

        System.out.println("Current Time: " + formatter.print(now));
        System.out.println("Student's login period is: " + formatter.print(LoginView.loginStart) + " - " + formatter.print(LoginView.loginEnd));
        System.out.println("");

        while (true) {

            System.out.println("Enter user domain: ");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.print("Choice: ");
            while (true) {
                domain = Helper.readLine();
                
                if (domain.equals("Q")) {
                    return;
                } else if (domain.equals("1")) {
                    System.out.print("Enter admin username: ");
                    break;
                } else if (domain.equals("2")) {
                    System.out.print("Enter student username: ");
                    break;
                } else {
                    System.out.print("Invalid input, enter choice again: ");
                }
            }

            username = Helper.readLine();
            
            System.out.print("Enter password: ");
            password = Helper.getPasswordInput();
            
            if (Database.USERS.containsKey(username)) {
                User result = Database.USERS.get(username);
                if (result.checkPassword(password)) {
                    if (domain.equals("2") && result.getAccessLevel() == AccessLevel.STUDENT) {
                        if (isValidLoginDate()) {
                            try {
                                Database.CURRENT_USER = (Student) result;
                                Database.CURRENT_ACCESS_LEVEL = Database.CURRENT_USER.getAccessLevel();
                            } catch (Exception e) {
                                System.out.println("Invalid user. Please enter again!");
                            }
                            StudentMainView view = new StudentMainView();
                            view.print();
                        } else{
                            System.out.println("Login at an invalid timeframe! Please log in later.");
                        }
                    } else if (domain.equals("1") && result.getAccessLevel() == AccessLevel.ADMIN) {
                        try{
                            Database.CURRENT_USER = (Admin) result;
                            Database.CURRENT_ACCESS_LEVEL = Database.CURRENT_USER.getAccessLevel();
                        } catch (Exception e) {
                            System.out.println("Invalid user. Please try again");
                        }
                        AdminMainView adminView = new AdminMainView();
                        adminView.print();
                    } else {
                        System.out.println("Invalid domain. Please enter domain again.");
                    }
                } else {
                    System.out.println("Invalid Password");
                }
                
            } else {
                System.out.println("Invalid Username");
            }        
        }
    }
    
    /**
     * Assigns the period of time that the {@link Student} can log in.
     * @param start login time in {@link org.joda.time.DateTime} format.
     * @param end logout time in {@link org.joda.time.DateTime} format.
     * @throws Exception Exception if Start time is after end time.
     */
    public static void setLoginTime(DateTime start, DateTime end) throws Exception {

        if (end.isBefore(start)) {
            throw new Exception("Start time is after End time");
        }
        LoginView.loginStart = start;
        LoginView.loginEnd = end;
        Database.SETTINGS.put("loginStart", start);
        Database.SETTINGS.put("loginEnd", end);
        Database.serialise(FileType.MISC);
    }

    /**
     * Checks if the current time is within the allocated time for {@link Student}s to log in.
     * @return {@code true} if current time is within allocated time.
     */
    private boolean isValidLoginDate() {

        return LoginView.loginStart.isBeforeNow() && LoginView.loginEnd.isAfterNow();
    }
}