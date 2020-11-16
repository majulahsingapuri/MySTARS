package MySTARS;

/**
 * Create new {@link Course} object and fills in necessary information.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class AddCourseView extends View {

    /**
     * Required method from View.
     */
    protected void print() {
        
        if (Database.CURRENT_ACCESS_LEVEL == AccessLevel.ADMIN) {
            addAdminCourse();
        } else if (Database.CURRENT_ACCESS_LEVEL == AccessLevel.STUDENT) {
            addStudentCourse();
        } else {
            System.out.println("Invalid AddCourse option.");
        }
    }

    /**
     * Prints prompts for the User to key in so that the relevant information for a new {@link Course} is keyed in. 
     */
    protected void addAdminCourse() {
        
        clearScreen("Admin Main > Add Course");

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
     * Method to add the {@link User} to a {@link Course} of the User's choice.
     */
    protected void addStudentCourse() {

        clearScreen("Student Main > Add Course");
        
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
                for (Course studentCourse : currentUser.getCourses(CourseStatus.NONE)){
                    if (studentCourse.getCourseCode().equals(courseCode)){
                        if (studentCourse.getStatus() == CourseStatus.REGISTERED){
                            System.out.println("You are already registered in this course!");
                        } else {
                            System.out.println("You are already on the waitlist for this course!");
                        }
                        courseCode = "Q";
                        break;
                    }
                }

                if (courseCode.equals("Q")){
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
                            System.out.println(courseCode + " has been added successfully.");
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                            Helper.pause();
                        }
                    }
                } else {
                    System.out.println("The course index that you have entered is invalid!");
                    Helper.pause();
                }
            } else {
                System.out.println("The course code you entered is invalid!");
                Helper.pause();
            }
        }
        
        System.out.println("Going back to main menu...");
    }
}
