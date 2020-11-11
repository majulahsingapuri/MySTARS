package MySTARS;

public class StudentMainView extends View {
    private int choice;
    public StudentMainView() {}

    protected void print() {

        clearScreen("StudentMain");

        do {
            System.out.println("What would you like to do? Choose one of the options below: ");
            System.out.println("1. Add a new course");
            System.out.println("2. Drop a course");
            System.out.println("3. Change index of one of your current courses");
            System.out.println("4. Swap index of one of your current courses with a peer");
            System.out.println("5. Change your password");
            System.out.println("6. Show timetable");
            System.out.println("7. Logout");
            System.out.print("Enter the number of your choice: ");

            choice = Helper.sc.nextInt();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    dropCourse();
                    break;
                case 3:
                    changeIndex();
                    break;
                case 4:
                    swapIndex();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    PrintTimetableView view = new PrintTimetableView();
                    view.print();
                    break;
                case 7:
                    LogoutView view = new LogoutView();
                    view.print();
                    break;
                default:
                    System.out.println("Please input correct choice number.");
            }
        } while (choice < 8);

        
    }
    
    private Student verifySecondUser(String courseCode) {

        while (true) {
            System.out.print("Enter Second User's username or Q to quit: ");
            String username = Helper.sc.nextLine();
            if (username.equals("Q")) {
                return null;
            }
            System.out.print("Enter password: ");
            String password = Helper.getPasswordInput();
            if (Database.USERS.containsKey(username)) {
                User result = Database.USERS.get(username);
                if (result.checkPassword(password)) {
                    try {
                        Student secondUser = (Student) result;
                        Course course = secondUser.getCourse(courseCode);
                        if (course != null && course.getStatus() == CourseStatus.REGISTERED) {
                            return secondUser;
                        } else {
                            System.out.println("Second User is not registered for this course!");
                        }
                    } catch (Exception e) {
                        // print error message
                        System.out.println("Invalid user. Please enter again!");
                    }
                }
            }
        }
    }

    protected void addCourse() {

        // TODO: make this work with course manager after merge
        //display all courses
        //parameter passed in CourseStatus.NOT_REGISTERED to display the course
        System.out.print("Enter the course code: ");
        String courseCode = Helper.sc.nextLine();
        Course course = Database.COURSES.get(courseCode);
        CourseManager.printIndexList(course, true);
        //should get from courseManager
        // print index list, print vacancies true
        //printLesson
        System.out.print("Enter the course index that you wish to add: ");
        String courseIndex = Helper.sc.nextLine();
        CourseManager.printLesson(courseIndex);
        /*
        have course, have index that the user wants,
        add it as a new course object to the CurrentUser.addCourse() hashmap
        */
        Database.CURRENT_USER.addCourse(courseCode,courseIndex);
    }

    protected void dropCourse() {

        CourseManager.printCourseList(CourseStatus.REGISTERED, Database.CURRENT_USER);
        System.out.print("Enter the course code to drop: ");
        String courseCode = Helper.sc.nextLine();
        Student currentUser = (Student) Database.CURRENT_USER;
        try {
            currentUser.dropCourse(courseCode);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    protected void changeIndex() {

        CourseManager.printCourseList(CourseStatus.REGISTERED, Database.CURRENT_USER);
        while (true) {
            System.out.print("Enter the course code or Q to quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            if (currentUser.getCourse(courseCode) != null) {
                Course course = Database.COURSES.get(courseCode);
                CourseManager.printIndexList(course, true);
                System.out.print("Enter your current index: ");
                String curIndex = Helper.sc.nextLine();
                System.out.print("Enter the new index that you wish to change to: ");
                String newIndex = Helper.sc.nextLine();
                Student currentUser = (Student) Database.CURRENT_USER;
                try {
                    currentUser.changeIndex(courseCode, curIndex, newIndex);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            } else {
                System.out.println("You are not registerd for this Course!");
            }
        }
    }

    protected void swapIndex() {

        CourseManager.printCourseList(CourseStatus.REGISTERED, Database.CURRENT_USER);
        while (true) {
            System.out.print("Enter the course code to swap with a friend or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }
            Student currentUser = (Student) Database.CURRENT_USER;
            if (currentUser.getCourse(courseCode) != null) {
                Student secondUser = verifySecondUser(courseCode);
                if (secondUser == null) {
                    break;
                } else {
                    CourseIndex currentUserIndex = currentUser.getCourse(courseCode).getIndices()[0]
                    CourseIndex secondUserIndex = secondUser.getCourse(courseCode).getIndices()[0];
                    if (!currentUser.clashes(courseCode, secondUserIndex.getCourseIndex()) && !secondUser.clashes(courseCode, currentUserIndex.getCourseIndex())) {
                        currentUser.setIndex(courseCode, secondUserIndex);
                        secondUser.setIndex(courseCode, currentUserIndex);
                        Course course = Database.COURSES.get(courseCode);
                        currentUserIndex = course.getIndex(currentUserIndex.getCourseIndex());
                        secondUserIndex = course.getIndex(secondUserIndex.getCourseIndex());
                        Student temp = currentUserIndex.removeStudent(currentUser.getUsername());
                        secondUserIndex.addStudent(temp);
                        temp = secondUserIndex.removeStudent(secondUser.getUsername());
                        currentUserIndex.addStudent(temp);
                    } else {
                        System.out.println("There is a clash of index!");
                    }
                }
            } else {
                System.out.println("You have not registered for this course!");
            }
        }
    }

    protected void changePassword() {

        while (true) {
            System.out.println("Enter current password or Q to quit: ");
            String oldPassowrd = Helper.getPasswordInput();
            if (oldPassowrd.equals("Q")) {
                break;
            }
            if (Database.CURRENT_USER.checkPassword(oldPassowrd)) {
                System.out.println("Enter new password: ");
                String newPassword1 = Helper.getPasswordInput();
                System.out.println("Enter the new password again: ");
                String newPassword2 = Helper.getPasswordInput();
                if (newPassword1.equals(newPassword2)){
                    Database.CURRENT_USER.changePassword(newPassword1);
                } else{
                    System.out.println("The passwords you entered do not match. Please try again.");
                }
            }
        }
    } 
}