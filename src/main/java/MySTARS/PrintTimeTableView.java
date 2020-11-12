package MySTARS;

import java.util.ArrayList;

public final class PrintTimeTableView extends View {

    protected void print() {

        clearScreen("Main > Print Timetable");

        printLine();
        System.out.println(String.format("%6s ║ %-20s ║ %2s ║ %5s ║ %6s ║ %4s ║ %3s ║ %-13s ║ %-10s ║ %-26s", "Course", "Title", "AU", "Index", "Status", "Type", "Day", "Time", "Venue", "Remark"));
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
                    System.out.println(String.format("%6s ║ %-20.20s ║ %2s ║ %5s ║ %6s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10.10s ║ %-26s", course.getCourseCode(), course.getCourseName(), course.getCourseAU().value, courseIndex.getCourseIndex(), course.getStatus().label, lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                } else {
                    System.out.println(String.format("%51s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10s ║ %-26s", "", lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                }
                printLine();
            }
        }
        
        System.out.println("Press Enter to return to Main Menu");
        Helper.sc.nextLine();
        return;
    }

    private static void printLine() {
        String line = String.format("%" + 120 + "s", "").replace(" ", "═");
        System.out.println(line);
    }
}
