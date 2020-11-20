package MySTARS;

/**
 * Create new {@link Course} object and fills in necessary information.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class AddCourseView extends View {

    /**
     * Required method from {@link View}.
     */
    public void print() {
        
        switch (Database.CURRENT_ACCESS_LEVEL) {
            case ADMIN:
                clearScreen("Admin Main > Add Course");
                adminAddCourse();
                break;
        
            case STUDENT:
                clearScreen("Student Main > Add Course");
                studentAddCourse();
                break;
            
            case NONE:
                break;
        }

        
    }

    /**
     * Prints prompts for the admin user to key in so that the relevant information for a new {@link Course} is keyed in. 
     */
    public void adminAddCourse() {

        while (true) {
            System.out.print(String.format("%-50s: ", "Enter new course code or Q to quit"));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Course.checkCourseCodeFormat(courseCode)) {
                if (!Database.COURSES.containsKey(courseCode)) {
                    try {
                        
                        String courseName;
                        while (true) {
                            System.out.print(String.format("%-50s: ", "Enter course name"));
                            courseName = Helper.readLine();
                            if (courseName.length() <= 50) {
                                break;
                            } else {
                                System.out.println("Course Name is too long!");
                            }
                        }

                        AU acadUnits;
                        while (true) {
                            try {
                                System.out.print(String.format("%-50s: ", "Enter no. of AUs"));
                                int au = Integer.parseInt(Helper.readLine());
                                if (au < 1 || au > 4) {
                                    throw new Exception();
                                }
                                acadUnits = AU.getAU(au);
                                break;
                            } catch (Exception e) {
                                System.out.println("Please enter valid number.");
                            }
                        }
    
                        System.out.print(String.format("%-50s: ", "Enter course description"));
                        String description = Helper.readLine();
    
                        Course course = new Course(courseCode, courseName, acadUnits, description);
    
                        int numIndices;
                        while (true) {
                            try {
                                System.out.print(String.format("%-50s: ", "Enter the number of indices (1 - 10)"));
                                numIndices = Integer.parseInt(Helper.readLine());
                                if (numIndices < 1 || numIndices > 10) {
                                    throw new Exception();
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Please enter valid number.");
                            }
                        }

                        Helper.printSmallSpace();

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
     * Displays all available course information. 
     * Prompts student user to select a course to add to their own timetable.
     */
    public void studentAddCourse() {

        Student currentUser = (Student) Database.CURRENT_USER;

        while (true) {

            Helper.printLine(150);
            System.out.println(String.format("%6s ║ %-50s ║ %2s ║ %5s ║ %6s ║ %4s ║ %3s ║ %-13s ║ %-10s ║ %-26s", "Course", "Title", "AU", "Index", "Status", "Type", "Day", "Time", "Venue", "Remark"));
            Helper.printLine(150);
            PrintTimeTableView.printInformation(CourseStatus.NOT_REGISTERED, currentUser);
            Helper.printMediumSpace();

            if (currentUser.getCourses(CourseStatus.NOT_REGISTERED).length != 0) {
                System.out.print(String.format("%-50s: ", "Enter course code or Q to quit"));
                String courseCode = Helper.readLine();
                if (courseCode.equals("Q")) {
                    break;
                }

                Course course = currentUser.getCourse(courseCode);
                if (course.getStatus() == CourseStatus.NOT_REGISTERED) {

                    CourseIndex courseIndex = course.getIndices()[0];
                    Helper.printSmallSpace();
                    CourseManager.printLesson(courseIndex);
                    System.out.print(String.format("%-50s: ", "Register for this course? y/n"));
                    String answer = Helper.readLine();

                    if (answer.equals("Y")) {
                        try {
                            currentUser.addCourse(courseCode,courseIndex.getCourseIndex());
                            Database.serialise(FileType.USERS);
                            Database.serialise(FileType.COURSES);
                            System.out.println(courseCode + " has been added successfully.");
                            Helper.pause();
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                } else {
                    System.out.println("Please enter a valid course code!");
                }
            } else {
                System.out.println("Please add courses to your plan from Check Vacancies!");
                Helper.pause();
                break;
            }
        }
    }
}
