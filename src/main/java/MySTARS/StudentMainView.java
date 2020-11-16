package MySTARS;

/**
 * The main menu for the Student side of the Application. Extends View.
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
                Helper.sc.nextLine();
            }
            catch (Exception e){
                choice = -1;
                Helper.sc.nextLine();
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
                    clearScreen("Student Main > Change Index");
                    changeIndex();
                    break;
                case 4:
                    clearScreen("Student Main > Swap Index with Peer");
                    swapIndex();
                    break;
                case 5:
                    clearScreen("Student Main > Change Password");
                    changePassword();
                    break;
                case 6:
                    PrintTimeTableView timetableView = new PrintTimeTableView();
                    timetableView.print();
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
            }
            Helper.pause();
        } while (true);
    }

    /**
     * For swapping index with a peer, this method checks if peer is a valid user that is registered for the relevant course.
     * @param courseCode {@link CourseCode}, e.g CZ2002
     * @return {@link Student} object for the second user
     */
    private Student verifySecondUser(String courseCode) {

        while (true) {
            System.out.print("Enter Second User's username or Q to quit: ");
            String username = Helper.sc.nextLine();
            if (username.equals("Q")) {
                return null;
            }

            if (!Database.USERS.containsKey(username)) {
                System.out.println(username + " does not exist!");
                Helper.pause();
            } else {
                System.out.print("Enter password: ");
                String password = Helper.getPasswordInput();
                User result = Database.USERS.get(username);
                if (result.checkPassword(password)) {
                    try {
                        Student secondUser = (Student) result;
                        Course course = secondUser.getCourse(courseCode);
                        if (course != null && course.getStatus() == CourseStatus.REGISTERED) {
                            return secondUser;
                        } else {
                            System.out.println("Second User is not registered for this course!");
                            Helper.pause();
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid user. Please enter again!");
                        Helper.pause();
                    }
                } else {
                    System.out.println("Invalid Password!");
                    Helper.pause();
                }
            }
        }
    }

    /**
     * Removes the {@link User} from the selected {@link Course}.
     */
    protected void dropCourse() {

        while (true) {
            CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
            System.out.print("Enter the course code to drop or Q to quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            try {
                currentUser.dropCourse(courseCode);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
                System.out.println(courseCode + " has been dropped successfully.");
                break;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                Helper.pause();
            }
        }

        System.out.println("Going back to main menu...");
    }

    /**
     * Changes the index of the {@link Course} selected by {@link User}. 
     */
    protected void changeIndex() {

        while (true) {
            CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
            System.out.print("Enter the course code or Q to quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            if (currentUser.getCourse(courseCode) != null) {
                Course course = Database.COURSES.get(courseCode);
                CourseManager.printIndexList(course, true);
                String curIndex = currentUser.getCourse(courseCode).getIndicesString()[0];
                System.out.println("Your current index is: " + curIndex);
                System.out.print("Enter the new index that you wish to change to: ");
                String newIndex = Helper.sc.nextLine();
                if (newIndex.equals(curIndex)){
                    System.out.println("You are already in this index!");
                    Helper.pause();
                } else {
                    try {
                        currentUser.changeIndex(courseCode, curIndex, newIndex);
                        Database.serialise(FileType.USERS);
                        Database.serialise(FileType.COURSES);
                        System.out.println("Changed " + courseCode + " to index " + newIndex + " succesfully.");
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                        Helper.pause();
                    }
                }
            } else {
                System.out.println("You are not registerd for this Course!");
                Helper.pause();
            }
        }

        System.out.println("Going back to main menu...");
    }

    /**
     * Verifies a second {@link User} from {@link Database.USERS} and swaps the indices for both the Users.
     */
    protected void swapIndex() {

        while (true) {
            CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
            System.out.print("Enter the course code to swap with a friend or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            if (currentUser.getCourse(courseCode) != null) {
                Student secondUser = verifySecondUser(courseCode);
                if (secondUser == null) {
                    //User has entered "Q"
                    break;
                } else {
                    CourseIndex currentUserIndex = currentUser.getCourse(courseCode).getIndices()[0];
                    CourseIndex secondUserIndex = secondUser.getCourse(courseCode).getIndices()[0];
                    if (!currentUser.clashes(courseCode, secondUserIndex.getCourseIndex()) && !secondUser.clashes(courseCode, currentUserIndex.getCourseIndex())) {
                        currentUser.swapIndex(courseCode, secondUserIndex);
                        secondUser.swapIndex(courseCode, currentUserIndex);
                        System.out.println("Swapped successfully.");
                        break;
                    } else {
                        System.out.println("There is a clash of index!");
                        Helper.pause();
                    }
                }
            } else {
                System.out.println("You are not registered in this course!");
                Helper.pause();
            }
        }

        System.out.println("Going back to main menu...");
    }

    /**
     * Changes the Password for the {@link Database.CURRENT_USER}.
     */
    protected void changePassword() {
        
        Database.CURRENT_USER.changePassword();
        System.out.println("Going back to main menu...");
    } 

    /**
     * Displays the {@link Course}s that are on {@link CourseStatus.WAITLIST}.
     */
    protected void viewWaitlistedCourses() {
        Student currentUser = (Student) Database.CURRENT_USER;
        CourseManager.printCourseList(CourseStatus.WAITLIST, currentUser);
    }
}
