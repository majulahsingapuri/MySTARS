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

                clearScreen("Admin Main");

                System.out.println("What would you like to do?");
                System.out.println("1: Add course to MySTARS"); 
                System.out.println("2: Update course in MySTARS");
                System.out.println("3: Print Students list"); 
                System.out.println("4: Change student's entry timing to MySTARS"); 
                System.out.println("5: Add new user to MySTARS");
                System.out.println("6: Change Password");
                System.out.println("7: Logout");
                System.out.print(String.format("%-50s: ", "Choice"));
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
                        printStudentsList();
                        break;
                    case 4:
                        changeEntryTiming();
                        break;
                    case 5:
                        AddUserView addUserView = new AddUserView();
                        addUserView.print();
                        break;
                    case 6:
                        changePassword();
                        break;
                    case 7:
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

    public void printStudentsList() {

        while (true) {

            System.out.print(String.format("%-50s: ", "Enter course code or Q to quit: "));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Database.COURSES.containsKey(courseCode)) {
                
                Course course = Database.COURSES.get(courseCode);
                
                int choice;
                System.out.println("Print by:\n1. Course\n2.Index");
                System.out.print(String.format("%-50s: ", "Choice"));
                while (true) {
                    try {
                        choice = Integer.parseInt(Helper.readLine());
                        if (choice == 1 || choice == 2) {
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number");
                    }
                }

                if (choice == 1) {
                    CourseManager.printStudentListByCourse(course, true);
                } else {
                    CourseManager.printIndexList(course, false);

                    String courseIndex;
                    System.out.print(String.format("%-50s: ", "Enter Index Number"));
                    while (true) {
                        try {
                            courseIndex = Helper.readLine();
                            if (course.containsIndex(courseIndex)) {
                                CourseManager.printStudentListByIndex(course.getIndex(courseIndex), false);
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number");
                        }
                    }
                }
            } else {
                System.out.println("The course code does not exist.");
            }
        }
    }

    /**
     * Changes MySTARS access start and end dates and times
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
     * Changes the Password for the {@link Database}.CURRENT_USER.
     */
    private void changePassword() {

        Database.CURRENT_USER.changePassword();
        Helper.pause();
    }
}
