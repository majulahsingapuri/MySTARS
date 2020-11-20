package MySTARS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * CourseManager that prints information regarding courses in the correct format.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class CourseManager {

    /**
     * Prints a list of courses based on the {@link CourseStatus} from {@link Database}.
     * @param status The desired CourseStatus.
     */
    public static void printCourseList(CourseStatus status) {

        printLine();
        System.out.println(String.format("%12.12s ║ %-35.35s ║ %-15.15s ║ %-82.82s", "Course Code", "Course Name", "Course School", "Course Description"));
        printLine();

        for (HashMap.Entry<String, Course> courseEntry : Database.COURSES.entrySet()) {

            Course course = courseEntry.getValue();

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s ║ %-35.35s ║ %-15.15s ║ %-82.82s", course.getCourseCode(), course.getCourseName(), course.getSchool(), course.getDescription()));
                printLine();
            }
        }
    }

    /**
     * Prints a list of courses based on the {@link CourseStatus} from a {@link Student}'s courses.
     * @param status The desired CourseStatus.
     * @param student The Student whose courses are to be printed.
     */
    public static void printCourseList(CourseStatus status, Student student) {

        printLine();
        System.out.println(String.format("%12.12s ║ %-35.35s ║ %-15.15s ║ %-82.82s", "Course Code", "Course Name", "Course School", "Course Description"));
        printLine();

        for (Course course: student.getCourses(status)) {

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s ║ %-35.35s ║ %-15.15s ║ %-82.82s", course.getCourseCode(), course.getCourseName(), course.getSchool(), course.getDescription()));   
            }
            printLine();
        }
    }

    /**
     * Prints the full details of a course.
     * @param course The course to be printed
     */
    public static void printCourseDetails(Course course) {

        printLine();
        System.out.println(String.format("%6s ║ %-50s ║ %2s ║ %5s ║ %10s ║ %4s ║ %3s ║ %-13s ║ %-10s ║ %-22s", "Course", "Title", "AU", "Index", "Class Size", "Type", "Day", "Time", "Venue", "Remark"));
        printLine();

        for (CourseIndex courseIndex : course.getIndices()) {
            ArrayList<Lesson> lessons = courseIndex.getLessons();
            for (int i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                DayOfWeek day = DayOfWeek.getDayOfWeek(lesson.getTime().getStart().getDayOfMonth());
                String[] time = lesson.getTime().toString().split("[T:/]");
                if (i == 0) {
                    System.out.println(String.format("%6s ║ %-50.50s ║ %2s ║ %5s ║ %10s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10.10s ║ %-22s", course.getCourseCode(), course.getCourseName(), course.getCourseAU().value, courseIndex.getCourseIndex(), courseIndex.getClassSize(), lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                } else {
                    System.out.println(String.format("%85s ║ %4s ║ %3s ║ %2s:%2s - %2s:%2s ║ %-10.10s ║ %-22s", "", lesson.getType().label, day.label, time[1], time[2], time[6], time[7], lesson.getLocation(), lesson.getRemarks()));
                }
                printLine();
            }
        }        
    }

    /**
     * Prints a list of {@link CourseIndex} for a particular {@link Course} with the option to print the number of vacanices.
     * @param course The desired Course to print the values from.
     * @param printVacancies Indication to print the number of vacancies.
     */
    public static void printIndexList(Course course, boolean printVacancies) {

        printLine();
        System.out.print("Course Index");
        if (printVacancies) {
            System.out.print(" ║ Vacancies ║ Class Size");
        }
        System.out.println();
        printLine();

        for (CourseIndex courseIndex : course.getIndices()) {
            
            System.out.print(String.format("%12.12s", courseIndex.getCourseIndex()));

            if (printVacancies) {
                System.out.print(" ║ " + String.format("%-9d ║ %-4d", courseIndex.getVacancies(), courseIndex.getClassSize()));
            }

            System.out.println();
            printLine();
        }
    }

    /**
     * Prints a list of {@link Lesson} for a given {@link CourseIndex}.
     * @param index The CourseIndex whose Lessons need printing.
     */
    public static void printLesson(CourseIndex index) {

        printLine();
        System.out.println(String.format("%-10s ║ Lesson Type ║ Class Size ║ Day ║ %-13.13s ║ Location", "Lesson ID.", "Lesson Time"));
        printLine();

        for (Lesson lesson : index.getLessons()) {
            
            DayOfWeek day = DayOfWeek.getDayOfWeek(lesson.getTime().getStart().getDayOfMonth());
            String[] time = lesson.getTime().toString().split("[T:/]");
            System.out.println(String.format("%-10d ║ %-11.11s ║ %-10.10s ║ %-3.3s ║ %2s:%2s - %2s:%2s ║ %-48.48s",lesson.getLessonID(), lesson.getType().label, index.getClassSize(), day.label,time[1], time[2], time[6], time[7], lesson.getLocation()));
            printLine();
        }
    }

    /**
     * Prints a lesson information for a given {@link Lesson}.
     * @param lesson The Lesson that needs printing.
     */
    public static void printLesson(Lesson lesson) {

        printLine();
        System.out.println(String.format("%-10s ║ Lesson Type ║ Day ║ %-13.13s ║ Location", "Lesson ID.", "Lesson Time"));
        printLine();

        DayOfWeek day = DayOfWeek.getDayOfWeek(lesson.getTime().getStart().getDayOfMonth());
        String[] time = lesson.getTime().toString().split("[T:/]");
        System.out.println(String.format("%-10d ║ %-11.11s ║ %-3.3s ║ %2s:%2s - %2s:%2s ║ %-48.48s",lesson.getLessonID(), lesson.getType().label, day.label,time[1], time[2], time[6], time[7], lesson.getLocation()));
        printLine();
        
    }

    /**
     * Prints a list of students in a given {@link Course} with the option to have the list sorted.
     * @param course The Course from which to print the list of Students.
     * @param sorted The option to sort the Students by name.
     */
    public static void printStudentListByCourse(Course course, boolean sorted) {

        for (CourseIndex courseIndex : course.getIndices()) {
            printStudentListByIndex(courseIndex, sorted);
            System.out.println("\n");
        }
    }

    /**
     * Prints a list of students in a given {@link CourseIndex}.
     * @param courseIndex The CourseIndex from which to print the list of Students.
     * @param sorted The option to sort the Students by name.
     */
    public static void printStudentListByIndex(CourseIndex courseIndex, boolean sorted) {

        ArrayList<Student> studentsArray = courseIndex.getStudents();

        if (sorted) {
            Collections.sort(studentsArray);
        }

        System.out.println("Index Number: " + courseIndex.getCourseIndex() + "");
        printLine();
        System.out.println(String.format("Matric No. ║ %-70.70s ║ %-6.6s ║ %-10.10s", "Name", "Gender", "Status"));
        printLine();

        for (Student student : studentsArray) {
            System.out.println(String.format("%-10.10s ║ %-70.70s ║ %-6.6s ║ %-10.10s", student.getMatricNumber(), student.getFirstName() + " " + student.getLastName(), student.getGender().label, CourseStatus.REGISTERED.label));
            printLine();
        }

        ArrayList<Student> waitlistArray = courseIndex.getWaitlistStudents();
        if (sorted) {
            Collections.sort(waitlistArray);
        }
        for (Student student : waitlistArray) {
            System.out.println(String.format("%-10.10s ║ %-70.70s ║ %-6.6s ║ %-10.10s", student.getMatricNumber(), student.getFirstName() + " " + student.getLastName(), student.getGender().label, CourseStatus.WAITLIST.label));
            printLine();
        }
    }

    /**
     * Prints a horizontal line across the screen.
     */
    private static void printLine() {
        Helper.printLine(150);
    }
}
