package MySTARS;

import java.util.ArrayList;

/**
 * A view that prints the Timetable for the currently logged in User.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class PrintTimeTableView extends View {

    /**
     * The main print function of the view, which prints out the timetable in the correct formatting.
     */
    public void print() {

        clearScreen("Student Main > Print Timetable");

        printLine();
        System.out.println(String.format("%6s ║ %-50s ║ %2s ║ %5s ║ %6s ║ %4s ║ %3s ║ %-13s ║ %-10s ║ %-26s", "Course", "Title", "AU", "Index", "Status", "Type", "Day", "Time", "Venue", "Remark"));
        printLine();

        Student currentUser = (Student) Database.CURRENT_USER;

        for (Course course : currentUser.getCourses(CourseStatus.REGISTERED)) {

            CourseIndex courseIndex = course.getIndices()[0];
            ArrayList<Lesson> lessons = courseIndex.getLessons();
            for (int i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                DayOfWeek day = DayOfWeek.getDayOfWeek(lesson.getTime().getStart().getDayOfWeek());
                String[] time = lesson.getTime().toString().split("[T:/]");
                if (i == 0) {
                    System.out.println(String.format("%6s ║ %-50.50s ║ %2s ║ %5s ║ %6s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10.10s ║ %-26s", course.getCourseCode(), course.getCourseName(), course.getCourseAU().value, courseIndex.getCourseIndex(), course.getStatus().label, lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                } else {
                    System.out.println(String.format("%81s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10s ║ %-26s", "", lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                }
                printLine();
            }
        }

        System.out.println("Student Information:");
        System.out.println(String.format("%-30s : %s\n%-30s : %s\n%-30s : %s\n%-30s : %s\n%-30s : %s\n%-30s : %s\n",
                                        "First Name", currentUser.getFirstName(),
                                        "Last Name", currentUser.getLastName(), 
                                        "Matric No.", currentUser.getMatricNumber(),
                                        "Gender:", currentUser.getGender().label,
                                        "Nationality", currentUser.getNationality(),
                                        "Registered AUs", currentUser.getAU()));
        
        return;
    }

    /**
     * Method that prints a horizontal line across the screen.
     */
    private static void printLine() {
        Helper.printLine(150);
    }
}
