package MySTARS;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import jdk.internal.module.SystemModuleReader;

public final class AdminMainView extends View {

    private int choice;

    /**
     * Constructor method
     */

    public AdminMainView() {}

    /**
     * Prints switch cases for the admin to select from
     */

    protected void print() {
        clearScreen("AdminMainView");
        do {
            System.out.println("What would you like to do?");
            System.out.println("1: Add course to MySTARS"); 
            System.out.println("2: Update course in MySTARS"); 
            System.out.println("3: Change student's entry timing to MySTARS"); 
            System.out.println("4: Add new user to MySTARS");
            //TODO: change user's access level?
            System.out.println("5: Change Password");
            System.out.println("6: Logout");
            choice = Helper.sc.nextInt();
            Helper.sc.nextLine();

            switch (choice) { 

                case 1:
                    addCourse();
                    break;
                case 2:
                    updateCourse();
                    break;
                case 3:
                    changeEntryTiming();
                    break;
                case 4:
                    addUser();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    LogoutView view = new LogoutView();
                    view.print();
                    return;
                default:
                    System.out.println("Please enter valid option.");
            }
        } while (true);
    }

    /**
     * Create new course
     */

    protected void addCourse() {
        System.out.println("Adding course to MySTARS...");

        while (true) {
            System.out.print("Enter course code or Q to quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (!Database.COURSES.containsKey(courseCode)) {
                System.out.print("Enter course name: ");
                String courseName = Helper.sc.nextLine();

                System.out.print("Enter no. of AUs: ");
                int au = Helper.sc.nextInt();
                Helper.sc.nextLine();
                AU acadUnits = AU.getAU(au);

                System.out.print("Enter course description: ");
                String description = Helper.sc.nextLine();

                Course course = new Course(courseCode, courseName, acadUnits, description);
                System.out.print("Enter the number of indices in this course: ");
                int numIndices = Helper.sc.nextInt();
                Helper.sc.nextLine();
                course.addIndices(numIndices);
                
                Database.COURSES.put(courseCode, course);
                Database.serialise(FileType.COURSES);
            } else {
                System.out.println("Course already exists!");
            }
        }
    }

    /**
     * Change course attributes, and update course for every registered student
     */

    protected void updateCourse() {

        do {
            System.out.println("1: Change course code");
            // System.out.println("2: Change course name");
            System.out.println("3: Change course index");
            System.out.println("4: Change course description");
            System.out.println("5: Change course vacancies");
            // System.out.println("6: Change acadamic units");
            choice = Helper.sc.nextInt();
            Helper.sc.nextLine();

            switch (choice) {

                case 1: 
                for (CourseIndex courseIndex : course.getIndices()) {
                    Student[] studentsArray = courseIndex.getStudents();
                    for (i=0; i<studentsArray.length; i++) {
                        
        
                        }
                        studentsArray[i].();
                    }
                    System.out.println("Enter course code to update or Q to Quit: ");
        
                }
                    
            }

        

        CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
        while (true) {
            System.out.println("Enter course code to update or Q to Quit: ");


        }
        //TODO the update needs to be on a deeper level than just the database. it has to go and check all of the students and the courses registered by them and update accordingly
        System.out.println("Updating course...");

        
    }

    /**
     * Change MySTARS access start and end date and time
     * @throws Exception handling for access DateTime format
     */

    protected void changeEntryTiming() {
        
        System.out.println("Changing entry timing for Students");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

        System.out.print("Enter the start date and time in the format (dd/MM/yyyy HH:mm:ss): ");
        String startDateString = Helper.sc.nextLine();
        System.out.print("Enter the end date and time in the format (dd/MM/yyyy HH:mm:ss): ");
        String endDateString = Helper.sc.nextLine();

        try {
            LoginView.setLoginTime(formatter.parseDateTime(startDateString), formatter.parseDateTime(endDateString));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }    
    }

    /**
     * If user does not already exist, a new User object is created
     * Can choose to create student or admin User object
     */

    protected void addUser() {
        
        System.out.println("Adding new User...");

        while (true) {
            System.out.print("Enter username or Q to quit: ");
            String username = Helper.sc.nextLine();
            if (username.equals("Q")) {
                break;
            }
            if (!Database.USERS.containsKey(username)) {
                String accessLevel;
                do {
                    System.out.print("Enter account type (Admin/Student): ");
                    accessLevel = Helper.sc.nextLine();
                } while (!accessLevel.equals("Admin") && !accessLevel.equals("Student"));

                if (accessLevel.equals("Admin")) {
                    String password1, password2;
                    while (true) {
                        System.out.print("Enter the password: ");
                        password1 = Helper.getPasswordInput();
                        System.out.print("Enter the password again: ");
                        password2 = Helper.getPasswordInput();
                        if (password1.equals(password2)) {
                            break;
                        } else {
                            System.out.println("The passwords do not match!");
                        }
                    }
                    Admin admin = new Admin(username, password1);
                    Database.USERS.put(username, admin);
                } else {

                    System.out.print("Enter the Student's First Name: ");
                    String firstName = Helper.sc.nextLine();
                    System.out.print("Enter the Student's Last Name: ");
                    String lastName = Helper.sc.nextLine();
                    //TODO clarify what is PNTS?
                    System.out.print("Enter the Student's Gender (M/F/NB/PNTS): ");
                    Gender gender = Gender.getGender(Helper.sc.nextLine());
                    System.out.print("Enter the Student's Nationality: ");
                    String nationality = Helper.sc.nextLine();
                    
                    String matricNumber;
                    while (true) {
                        System.out.print("Enter the Student's Matric number: ");
                        matricNumber = Helper.sc.nextLine();
                        if (Student.isValidNewMatricNo(matricNumber)) {
                            break;
                        } else {
                            System.out.println("Invalid Matric Number!");
                        }
                    }

                    // TODO: change to String.format()
                    System.out.println("\n\nNew Student Details:");
                    System.out.println("Name\t\t\t: " + firstName + " " + lastName);
                    System.out.println("Network Username\t: " + username);
                    System.out.println("Matric No\t\t: " + matricNumber);
                    System.out.println("Gender\t\t\t: " + gender.label);
                    System.out.println("Nationality\t\t: " + nationality);

                    //FIXME add password!!!

                    System.out.print("\nConfirm? y/n: ");
                    String confirm = Helper.sc.nextLine();
                    if (confirm.equals("y")) {
                        Student student = new Student(username, matricNumber, firstName, lastName, gender, nationality); // attributes/variables to create student object?
                        Database.USERS.put(username, student);
                        Database.serialise(FileType.USERS);
                    } else {
                        System.out.println("Aborting");
                    }
                }
            } else {
                System.out.println("User has already been added");
            }
        }
    }

    protected void changePassword() {

        Database.CURRENT_USER.changePassword();
        System.out.println("Going back to main menu...");
    }
}
