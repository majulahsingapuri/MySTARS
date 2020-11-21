package MySTARS;

/** 
 * Class to handle the admin function of printing the list of students.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class PrintStudentListView extends View {

    /**
     * Displays the a list of {@link Student}s.
     * Gives admin user the option of sorting the list by {@link Course} or {@link CourseIndex}. Required method from {@link View}
     */
    public void print() {

        while (true) {

            clearScreen("Admin Main > Print Student List");
            Helper.printSmallSpace();
            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print(String.format("%-50s: ", "Enter course code or Q to quit: "));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Database.COURSES.containsKey(courseCode)) {
                
                Course course = Database.COURSES.get(courseCode);
                
                int choice;
                System.out.println("Print by:\n1. Course\n2. Index");
                while (true) {
                    try {
                        System.out.print(String.format("%-50s: ", "Choice"));
                        choice = Integer.parseInt(Helper.readLine());
                        if (choice == 1 || choice == 2) {
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number");
                    }
                }

                if (choice == 1) {
                    Helper.printSmallSpace();
                    CourseManager.printStudentListByCourse(course, true);
                    Helper.pause();
                } else {
                    CourseManager.printIndexList(course, false);

                    String courseIndex;
                    while (true) {
                        try {
                            System.out.print(String.format("%-50s: ", "Enter Index Number"));
                            courseIndex = Helper.readLine();
                            if (course.containsIndex(courseIndex)) {
                                Helper.printSmallSpace();
                                CourseManager.printStudentListByIndex(course.getIndex(courseIndex), false);
                                Helper.pause();
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number");
                        }
                    }
                }
            } else {
                Helper.printSmallSpace();
                System.out.println("The course code does not exist.");
                Helper.pause();
            }
        }
    }
}
