package MySTARS;

/**
 * Create new {@link Course} object and fills in necessary information.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class AddCourseView extends View {

    /**
     * Prints prompts for the User to key in so that the relevant information for a new {@link Course} is keyed in. Required method from View.
     */
    public void print() {
        
        clearScreen("Admin Main > Add Course");

        while (true) {
            System.out.print(String.format("%-50s: ", "Enter new course code or Q to quit"));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Helper.checkCourseCodeFormat(courseCode)) {
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
}
