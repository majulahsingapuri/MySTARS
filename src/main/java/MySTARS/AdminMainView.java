package MySTARS;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The main menu for the Student side of the Application. Extends View.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class AdminMainView extends View {


    /**
     * Constructor method
     */
    public AdminMainView() {}

    /**
     * Prints menu options for the User to select from. Required method from View.
     */
    public void print() {
        
        int choice;
        do {
            try {
                System.out.println("What would you like to do?");
                System.out.println("1: Add course to MySTARS"); 
                System.out.println("2: Update course in MySTARS"); 
                System.out.println("3: Change student's entry timing to MySTARS"); 
                System.out.println("4: Add new user to MySTARS");
                System.out.println("5: Change Password");
                System.out.println("6: Logout");
                choice = Integer.parseInt(Helper.readLine());

                switch (choice) { 

                    case 1:
                        AddCourseView addCourseView = new AddCourseView();
                        addCourseView.print();
                        break;
                    case 2:
                        UpdateCourseView updateCourseView = new UpdateCourseView();
                        updateCourseView.print();
                        break;
                    case 3:
                        changeEntryTiming();
                        break;
                    case 4:
                        AddUserView addUserView = new AddUserView();
                        addUserView.print();
                        break;
                    case 5:
                        changePassword();
                        break;
                    case 6:
                        LogoutView logoutView = new LogoutView();
                        logoutView.print();
                        return;
                    default:
                        System.out.println("Please enter valid option.");
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } while (true);
    }

    /**
     * Changes MySTARS access start and end dates and times
     * @throws Exception If unable to parse to {@link org.joda.time.DateTime} from String.
     */
    private void changeEntryTiming() {
        
        System.out.println("Changing entry timing for Students");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

        System.out.print("Enter the start date and time in the format (dd/MM/yyyy HH:mm:ss): ");
        String startDateString = Helper.readLine();
        System.out.print("Enter the end date and time in the format (dd/MM/yyyy HH:mm:ss): ");
        String endDateString = Helper.readLine();

        try {
            LoginView.setLoginTime(formatter.parseDateTime(startDateString), formatter.parseDateTime(endDateString));
            System.out.println("Entry time updated succesfully.");
            Helper.pause();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            Helper.pause();
        }    
    }

    /**
     * Changes the Password for the {@link Database.CURRENT_USER}.
     */
    private void changePassword() {

        Database.CURRENT_USER.changePassword();
        Helper.pause();
    }
}
