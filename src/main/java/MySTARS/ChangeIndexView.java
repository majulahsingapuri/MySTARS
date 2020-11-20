package MySTARS;

 /**
 * Changes the index of the {@link Course} selected by {@link User}. 
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public class ChangeIndexView extends View {

    /**
     * Required method from {@link View}. Prints Prompts to help the user to change their current index.
     */
    public void print() {

        clearScreen("Student Main > Change Index");
        
        while (true) {
            CourseManager.printCourseList(CourseStatus.REGISTERED, (Student) Database.CURRENT_USER);
            System.out.print(String.format("%-50s: ", "Enter the course code or Q to quit"));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Student currentUser = (Student) Database.CURRENT_USER;
            if (currentUser.getCourse(courseCode) != null) {
                
                Course course = Database.COURSES.get(courseCode);
                Helper.printSmallSpace();
                CourseManager.printIndexList(course, true);

                String curIndex = currentUser.getCourse(courseCode).getIndicesString()[0];
                System.out.println(String.format("%-50s: %s", "Your current index is", curIndex));
                System.out.print(String.format("%-50s: ", "Enter the new index that you wish to change to"));
                String newIndex = Helper.readLine();
                if (newIndex.equals(curIndex)){
                    System.out.println("You are already in this index!");
                    Helper.pause();
                } else {
                    try {
                        currentUser.changeIndex(courseCode, curIndex, newIndex);
                        Database.serialise(FileType.USERS);
                        Database.serialise(FileType.COURSES);
                        System.out.println("Changed " + courseCode + " to index " + newIndex + " succesfully.");
                        Helper.pause();
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                        Helper.pause();
                    }
                }
            } else {
                System.out.println("You are not registerd for this Course!");
                Helper.pause();
            }
        }
    }    
}
