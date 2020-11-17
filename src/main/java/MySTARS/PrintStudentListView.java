package MySTARS;

public class PrintStudentListView extends View {
    

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
                System.out.print(String.format("%-50s: ", "Choice"));
                while (true) {
                    try {
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
