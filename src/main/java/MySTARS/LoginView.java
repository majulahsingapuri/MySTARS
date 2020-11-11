package MySTARS;

import java.io.Console;
import org.joda.time.DateTime;

public final class LoginView extends View {

    private String password;
    private String username;
    private String domain;
    private static DateTime loginStartDate;
    private static DateTime loginEndDate;

    public LoginView() {}
    
    protected void print() {
        clearScreen("Login");

        while (true) {
            System.out.print("Enter the domain (student or admin): ");
            domain = Helper.sc.nextLine();

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
                    if (domain.equals("student")) {
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
                    } else if (domain.equals("admin")){
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

    private boolean isValidLoginDate() {

        return LoginView.loginStartDate.isBeforeNow() && LoginView.loginEndDate.isAfterNow();
    }
}