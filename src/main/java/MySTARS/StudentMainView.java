package MySTARS;

/**
 * The main menu for the Student side of the Application. Extends View.
 * @author Bhargav, Kah Ee
 * @version 1.0
 * @since 2020-11-1
 */
public final class StudentMainView extends View {

    /**
     * Constructor methodf for object.
     */
    public StudentMainView() {}

    /**
     * Prints menu options for the User to select from. Required method from View.
     */
    protected void print() {

        int choice;
        do {
            clearScreen("Student Main");
            System.out.println("What would you like to do? Choose one of the options below: ");
            System.out.println("1. Add a new course");
            System.out.println("2. Drop a course");
            System.out.println("3. Change index of one of your current courses");
            System.out.println("4. Swap index of one of your current courses with a peer");
            System.out.println("5. Change your password");
            System.out.println("6. Show timetable");
            System.out.println("7. Show courses on waitlist");
            System.out.println("8. Logout");
            System.out.print("Enter the number of your choice: ");

            try{
                choice = Helper.sc.nextInt();
                Helper.readLine();
            } catch (Exception e) {
                choice = -1;
                Helper.readLine();
            }

            switch (choice) {
                case 1:
                    AddCourseView addCourseView = new AddCourseView();
                    addCourseView.print();
                    break;
                case 2:
                    clearScreen("Student Main > Drop Course");
                    dropCourse();
                    break;
                case 3:
                    ChangeIndexView changeIndexView = new ChangeIndexView();
                    changeIndexView.print();
                    break;
                case 4:
                    SwapIndexView swapIndexView = new SwapIndexView();
                    swapIndexView.print();
                    break;
                case 5:
                    clearScreen("Student Main > Change Password");
                    changePassword();
                    break;
                case 6:
                    PrintTimeTableView timetableView = new PrintTimeTableView();
                    timetableView.print();
                    Helper.pause();
                    break;
                case 7:
                    clearScreen("Student Main > View Courses on Waitlist");
                    viewWaitlistedCourses();
                    break;
                case 8:
                    LogoutView logoutView = new LogoutView();
                    logoutView.print();
                    return;
                default:
                    System.out.println("Please input correct choice number.");
                    Helper.pause();
            }
        } while (true);
    }

    /**
     * Removes the {@link User} from the selected {@link Course}.
     */
    protected void dropCourse() {

        while (true) {
            CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
            System.out.print("Enter the course code to drop or Q to quit: ");
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            try {
                currentUser.dropCourse(courseCode);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
                System.out.println(courseCode + " has been dropped successfully.");
                Helper.pause();
                break;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                Helper.pause();
            }
        }
    }

    /**
     * Changes the Password for the {@link Database.CURRENT_USER}.
     */
    protected void changePassword() {
        
        Database.CURRENT_USER.changePassword();
        Helper.pause();
    } 

    /**
     * Displays the {@link Course}s that are on {@link CourseStatus.WAITLIST}.
     */
    protected void viewWaitlistedCourses() {
        Student currentUser = (Student) Database.CURRENT_USER;
        CourseManager.printCourseList(CourseStatus.WAITLIST, currentUser);
        Helper.pause();
    }
}
