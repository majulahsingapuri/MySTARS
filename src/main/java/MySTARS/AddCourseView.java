package MySTARS;

/**
 * Create new {@link Course} object and fills in necessary information.
 */
public final class AddCourseView extends View {

    /**
     * Prints prompts for the User to key in so that the relevant information for a new {@link Course} is keyed in. Required method from View.
     */
    protected void print() {
        
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
}
