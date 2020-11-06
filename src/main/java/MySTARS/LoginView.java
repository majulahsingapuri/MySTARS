import java.util.Date;
import java.time.*;

public class LoginView extends View {

    private String password;
    private String username;
    private String domain;
    private static Date loginStartDate;
    private static Date loginEndDate;

    public LoginView() {}
    
    protected void print() {
        clearScreen("Login");
        // print enter domain
// TODO put inside loop and check if input is correct

/*
        while(true){
            enter the domain
            if domain == student or domain == admin {
                // continue to authenticate user
                get username
                get password

                check if database contains user{
                    get user
                    check if password matches with the user {
                        set database.user to user after casting
                    } else {
                        print invalid input
                    }
                } else {
                    print invalid input
                }
            } else {
                print invalid input
            }
        }

*/      boolean valid = false;
        while (!valid) {
            System.out.print("Enter the domain (student or admin) : ");
            domain = Helper.sc.nextLine(); //take note
            // print enter username
            System.out.print("Enter username: ");
            username = Helper.sc.nextLine();
            // print enter password
            System.out.print("Enter password: ");
            // call getPassword()
            password = getPassword();
            //need to go to database to compare passwords ....????
            // check if user is valid
            if (Database.USERS.containsKey(username)) {
                User result = Database.USERS.get(username);
                if (result.checkPassword(password)) {
                    if (domain.equals("student")) {
                        if (isValidLoginDate(loginStartDate, loginEndDate) ) {
                            try {
                                Database.CURRENT_USER = (Student) result;
                                Database.CURRENT_ACCESS_LEVEL = Database.CURRENT_USER.getAccessLevel();
                                valid = true;
                            } catch (Exception e) {
                                // print error message
                                System.out.println("Invalid user. Please enter again!");
                                valid = false;
                            }
                            // if user is student, open StudentMainView
                            StudentAdminView view = new StudentAdminView();
                            view.print();
                            valid = true;
                        }
                        else{
                            System.out.println("Login at an invalid timeframe! Please log in later.");
                            valid = false;
                        }
                    }

                    else if (domain.equals("admin")){
                        try{
                            Database.CURRENT_USER = (Admin) result;
                            Database.CURRENT_ACCESS_LEVEL = Database.CURRENT_USER.getAccessLevel();
                            valid = true;
                        }
                        catch {
                            System.out.println("Invalid user. Please try again");
                            valid = false;
                        }
                        AdminMainView adminView = new AdminMainView();
                        adminView.print();
                        valid = true;
                    }

                    else {
                        System.out.println("Invalid domain. Please enter domain again.");
                        valid = false;
                    }
                }
                
            }
            else valid = false;        
        }
    }

    private boolean isValidLoginDate(Date startDate, Date endDate) {
        // TODO: change for JodaTime
        LocalDate today = LocalDate.now();
        return startDate.isAfter(today) && endDate.isBefore(today);
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