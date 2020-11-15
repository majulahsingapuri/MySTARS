package MySTARS;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class LoginView extends View {

    private String password;
    private String username;
    private String domain;
    private static DateTime loginStart;
    private static DateTime loginEnd;

    /**
     * Constructor method
     */
    public LoginView() {

        if (loginStart == null || loginEnd == null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm:ss");
            loginStart = formatter.parseDateTime("01/01/2020 00:00:00");
            loginEnd = formatter.parseDateTime("31/12/2020 23:59:59");
        }
    }

    /**
     * Gets the time when the student first enters the LoginView
     * @return date in dd/MM/yyyy, time in hh:mm:ss
     */

    protected DateTime getStartTime() {
        
        return LoginView.loginStart;
    }

    /**
     * Gets the time when the student exits the LoginView
     * @return date in dd/MM/yyyy, time in hh:mm:ss
     */

    protected DateTime getEndTime() {
        
        return LoginView.loginEnd;
    }

    /**
     * Displays login view for student, asks student to input login details 
     */
    
    protected void print() {
        clearScreen("Login");

        //TODO: Print current time and log in timings for student

        LocalDate dt = LocalDate.now(); 
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm:ss");
        LocalDate parsedDate = LocalDate.parse(dt,formatter);

        System.out.print("Student's login date and time is: " + LoginView.loginStart);


        // System.out.println("%s", dt);
        System.out.println(LoginView.loginStart);

        while (true) {
            System.out.print("Enter the domain (Student or Admin): ");
            domain = Helper.sc.nextLine();

            //TODO print soemthing to let the user know how to quitAdmin
            if (domain.equals("Quit")) {
                break;
            }
            
            System.out.print("Enter username: ");
            username = Helper.sc.nextLine();
            
            System.out.print("Enter password: ");
            password = Helper.getPasswordInput();
            
            if (Database.USERS.containsKey(username)) {
                User result = Database.USERS.get(username);
                if (result.checkPassword(password)) {
                    if (domain.equals("Student") && result.getAccessLevel() == AccessLevel.STUDENT) {
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
                    } else if (domain.equals("Admin") && result.getAccessLevel() == AccessLevel.ADMIN) {
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
 * Assigns login time to 'start' variable, logout time to 'end' variable 
 * @param start login time in DateTime format 
 * @param end logout time in DateTime format 
 */
    protected static void setLoginTime(DateTime start, DateTime end) {

        LoginView.loginStart = start;
        LoginView.loginEnd = end;
        Database.SETTINGS.put("loginStart", start);
        Database.SETTINGS.put("loginEnd", end);
        Database.serialise(FileType.MISC);
    }

    /**
     * Checks if student is allowed to access MySTARS 
     * @return true if access allowed, false if access denied
     */
    private boolean isValidLoginDate() {

        return LoginView.loginStart.isBeforeNow() && LoginView.loginEnd.isAfterNow();
    }
}