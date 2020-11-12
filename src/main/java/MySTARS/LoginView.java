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

    public LoginView() {

        if (loginStart == null || loginEnd == null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
            loginStart = formatter.parseDateTime("01/01/2020 00:00:00");
            loginEnd = formatter.parseDateTime("31/12/2020 23:59:59");
        }
    }

    protected DateTime getStartTime() {
        
        return LoginView.loginStart;
    }

    protected DateTime getEndTime() {
        
        return LoginView.loginEnd;
    }
    
    protected void print() {
        clearScreen("Login");

        //TODO: Print current time and log in timings for student

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
                    if (domain.equals("Student")) {
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
                    } else if (domain.equals("Admin")){
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

    protected static void setLoginTime(DateTime start, DateTime end) {

        LoginView.loginStart = start;
        LoginView.loginEnd = end;
    }

    private boolean isValidLoginDate() {

        return LoginView.loginStart.isBeforeNow() && LoginView.loginEnd.isAfterNow();
    }
}