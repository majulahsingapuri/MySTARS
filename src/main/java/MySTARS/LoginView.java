package MySTARS;

import java.io.Console;
import org.joda.time.DateTime;

public class LoginView extends View {

    private String password;
    private String username;
    private String domain;
    private static DateTime loginStart;
    private static DateTime loginEnd;

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
            password = getPassword();
            
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
                            StudentAdminView view = new StudentAdminView();
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

    protected static void setLoginTime(DateTime start, DateTime end) {

        LoginView.loginStart = start;
        LoginView.loginEnd = end;
    }

    private boolean isValidLoginDate() {

        return LoginView.loginStart.isBeforeNow() && LoginView.loginEnd.isAfterNow();
    }

    private String getPassword(){
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

}