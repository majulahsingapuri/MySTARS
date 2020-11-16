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
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Helper.checkCourseCodeFormat(courseCode)) {
                if (!Database.COURSES.containsKey(courseCode)) {
                    try {
                        System.out.print("Enter course name: ");
                        String courseName = Helper.readLine();
                        
                        AU acadUnits;
                        while (true) {
                            try {
                                System.out.print("Enter no. of AUs, default 1: ");
                                int au = Integer.parseInt(Helper.readLine());
                                acadUnits = AU.getAU(au);
                                break;
                            } catch (Exception e) {
                                System.out.println("Please enter valid number.");
                            }
                        }
    
                        System.out.print("Enter course description: ");
                        String description = Helper.readLine();
    
                        Course course = new Course(courseCode, courseName, acadUnits, description);
    
                        int numIndices;
                        while (true) {
                            try {
                                System.out.print("Enter the number of indices (1 - 10) in this course: ");
                                numIndices = Integer.parseInt(Helper.readLine());
                                if (numIndices < 0 || numIndices > 10) {
                                    throw new Exception();
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Please enter valid number.");
                            }
                        }

                        course.addIndices(numIndices);
                        
                        Database.COURSES.put(courseCode, course);
                        Database.serialise(FileType.COURSES);
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                } else {
                    System.out.println("Course already exists!");
                }
            } else {
                System.out.println("Invalid course code format");
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
            String courseCode = Helper.readLine();
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
                String courseIndex = Helper.readLine();
                if (courseIndex.equals("Q")) {
                    break;
                }

                CourseIndex index = course.getIndex(courseIndex);
                if (index != null) {
                    System.out.println();
                    CourseManager.printLesson(index);
                    System.out.print("These lesson timings will be added to your timetable. Confirm? y/n: ");
                    String answer = Helper.readLine();

                    if (answer.equals("Y")) {
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
