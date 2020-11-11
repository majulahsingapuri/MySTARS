package MySTARS;

import java.util.Scanner;
import org.joda.time.DateTime;
public class AdminMainView2 extends View {
    public int choice;
    Scanner sc = new Scanner(System.in);

    public AdminMainView() {}

    protected void print() {
        clearScreen("AdminMainView");
        do {
            System.out.println("What would you like to perform?");
            System.out.println("1: Add course to MySTARS"); 
            System.out.println("2: Update course in MySTARS"); 
            System.out.println("3: Change student's entry timing to MySTARS"); 
            System.out.println("4: Add student user to MySTARS"); 
            choice = sc.nextInt();

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

                default:
                    System.out.println("Please enter valid option.");
            }
            // System.out.println("Quit admin view");
            // System.exit(0);
        } while (choice < 5);
    }

    protected void addCourse() {
        System.out.println("Adding course to MySTARS...");
        System.out.println("Enter course code: ");
        String courseCode = sc.nextLine();
        Course course = Database.COURSES.set(courseCode).addCourse();

        System.out.println("Enter course index: ");
        String courseIndex = sc.nextLine();
        CourseIndex courseIndex = Database.COURSES.set(courseIndex).addIndices();

        System.out.println("Enter no. of AUs: ");
        int acadUnits = sc.nextInt();
        AU acadUnits = Database.COURSES.set(acadUnits).setCourseAU();

        System.out.println("Enter course description: ");
        String description = sc.nextLine();
        Course description = Database.COURSES.set(courseCode).setDescription();

        System.out.println("Enter course status: ");
        String courseStatus = sc.nextLine();
        CourseStatus courseStatus = Database.COURSES.set(courseStatus).setStatus();
    }

    protected void updateCourse() {
        // modify string
        System.out.println("Updating course...");
        System.out.println("Enter course code: ");
        String oldCourseCode = Helper.getCourseCode();
        AdminMainView2 courseCode; 
        courseCode = new AdminMainView2();
        // courseCode.str ??? create new string object??
        Course course = Database.COURSES.set(courseCode);

        System.out.println("Enter course index: "); //indices?
        String oldCourseIndex = Helper.getCourseIndex();
        AdminMainView2 courseIndex;
        courseIndex = new AdminMainView2();
        // creating new string object again
        Course course = Database.COURSES.set(courseIndex);

        System.out.println("Enter no. of AUs: ");
        int acadUnits = Helper.removeAU();
        AdminMainView2 acadUnits;
        acadUnits = new AdminMainView2;
        Course course = Database.COURSES.addAU(acadUnits);

        System.out.println("Enter course description: ");
        String oldDescription = Helper.getDescription();
        AdminMainView2 description;
        description = new AdminMainView2();
        // new string object
        Course course = Database.COURSES.set(description);
    }

    protected void changeEntryTiming() { // jodatime setting period?
        private static DateTime entryStartDate;
        private static DateTime entryEndDate;

        System.out.println("Enter no. of days: ");
        int numDays = sc.nextInt();
        int numDays = Days.daysBetween(startDate, endDate);
        int result = numDays.getDays();

        DateTime startDate = new DateTime(myDate);
        DateTime endDate = new DateTime(); 

        // student end: boolean return LoginView.loginStartDate.isBeforeNow() && LoginView.loginEndDate.isAfterNow();

    }

    protected void addUser() {
        System.out.println("Adding new student user...");
        System.out.println("Enter username: ");
        String userName = sc.nextLine();
        User student = Database.USERS.set(userName);

        System.out.println("Enter matric number: ");
        String matricNumber = sc.nextLine();
        matricNumber = getMatricNumber();

        // check matric number ???
        // if (Database.USERS.containsKey(matricNumber)) {
        //     User result = Database.USERS.get(matricNumber);
        //     if (result.isValidNewMatricNo(matricNo)) {
        //         try {
        //             Student matricNumber = (Student) result;
        //             return matricNumber;
        //         }
        //         catch (Exception e) {
        //             System.out.println("Invalid matric number, please enter again.");
        //         }
        //     }
        // }
        MatricNo matricNumber = Database.USERS.set(matricNo).getMatricNumber();


        // helper methods......? 
        // ?
        System.out.println("Enter first name: ");
        String firstName = sc.nextLine();
        FirstName firstName = Database.USERS.set(firstName).getFirstName(); //wna call from student class helper?

        System.out.println("Enter last name: ");
        String lastName = sc.nextLine();
        LastName lastName = Database.USERS.set(lastName).getLastName();

        System.out.println("Enter gender: ");
        String gender = sc.nextLine();
        Gender gender = Database.USERS.set(gender).getGender();

        System.out.println("Enter nationality: ");
        String nationality = sc.nextLine();
        Nationality nationality = Database.USERS.set(nationality).getNationality();

    }
}
