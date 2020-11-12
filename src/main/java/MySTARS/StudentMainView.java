package MySTARS;

public final class StudentMainView extends View {
    private int choice;
    public StudentMainView() {}

    protected void print() {

        do {
            clearScreen("StudentMain");
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
            Helper.sc.nextLine();

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
                    // PrintTimetableView timetableView = new PrintTimetableView();
                    // timetableView.print();
                    System.out.println("Will open timetable view");
                    break;
                case 7:
                    LogoutView logoutView = new LogoutView();
                    logoutView.print();
                    return;
                default:
                    System.out.println("Please input correct choice number.");
            }
        } while (true);
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
        Student currentUser = (Student) Database.CURRENT_USER;
        while (true) {
            CourseManager.printCourseList(CourseStatus.NONE);
            System.out.print("Enter the course code or Q to quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                Course[] studentCourses = currentUser.getCourses(CourseStatus.NONE);
                for (Course studentCourse : studentCourses){
                    if (studentCourse.getCourseCode().equals(courseCode)){
                        if (studentCourse.getStatus() == CourseStatus.REGISTERED){
                            System.out.println("You are already registered in this course!");
                        }
                        else{
                            System.out.println("You are already on the waitlist for this course!");
                        }
                        System.out.println("Enter anything to continue...");
                        Helper.sc.nextLine();
                        courseCode = 'Q';
                        break;
                    }
                }

                if (courseCode.equals('Q')){
                    break;
                }

                CourseManager.printIndexList(course, true);
                System.out.print("Enter the course index that you wish to add or Q to quit: ");
                String courseIndex = Helper.sc.nextLine();
                if (courseIndex.equals("Q")) {
                    break;
                }

                CourseIndex index = course.getIndex(courseIndex);
                if (index != null) {
                    System.out.println();
                    CourseManager.printLesson(index);
                    System.out.print("These lesson timings will be added to your timetable. Confirm? y/n: ");
                    String answer = Helper.sc.nextLine();

                    if (answer.equals("y")) {
                        try {
                            currentUser.addCourse(courseCode,courseIndex);
                            Database.serialise(FileType.USERS);
                            Database.serialise(FileType.COURSES);
                            //FIXME add confirmation message and wait for user input to continue
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                } else {
                    System.out.println("The course index that you have entered is invalid!");
                }
            } else {
                System.out.println("The course code you entered is invalid!");
            }
        }
    }

    protected void dropCourse() {

        CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
        while (true) {
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
                break;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

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
                }
                else{
                    try {
                        currentUser.changeIndex(courseCode, curIndex, newIndex);
                        Database.serialise(FileType.USERS);
                        Database.serialise(FileType.COURSES);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            } else {
                System.out.println("You are not registerd for this Course!");
            }
        }
    }

    protected void swapIndex() {

        CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
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
                    CourseIndex currentUserIndex = currentUser.getCourse(courseCode).getIndices()[0];
                    CourseIndex secondUserIndex = secondUser.getCourse(courseCode).getIndices()[0];
                    if (!currentUser.clashes(courseCode, secondUserIndex.getCourseIndex()) && !secondUser.clashes(courseCode, currentUserIndex.getCourseIndex())) {
                        currentUser.swapIndex(courseCode, secondUserIndex);
                        secondUser.swapIndex(courseCode, currentUserIndex);
                        Course course = Database.COURSES.get(courseCode);
                        currentUserIndex = course.getIndex(currentUserIndex.getCourseIndex());
                        secondUserIndex = course.getIndex(secondUserIndex.getCourseIndex());
                        Student temp = currentUserIndex.removeStudent(currentUser.getUsername());
                        secondUserIndex.addStudent(temp);
                        temp = secondUserIndex.removeStudent(secondUser.getUsername());
                        currentUserIndex.addStudent(temp);
                        Database.serialise(FileType.USERS);
                        Database.serialise(FileType.COURSES);
                        break;
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
            System.out.print("Enter current password or Q to quit: ");
            String oldPassowrd = Helper.getPasswordInput();
            if (oldPassowrd.equals("Q")) {
                break;
            }
            if (Database.CURRENT_USER.checkPassword(oldPassowrd)) {
                System.out.print("Enter new password: ");
                String newPassword1 = Helper.getPasswordInput();
                System.out.print("Enter the new password again: ");
                String newPassword2 = Helper.getPasswordInput();
                if (newPassword1.equals(newPassword2)){
                    Database.CURRENT_USER.changePassword(newPassword1);
                    Database.serialise(FileType.USERS);
                    break;
                } else{
                    System.out.println("The passwords you entered do not match. Please try again.");
                }
            }
        }
    } 
}