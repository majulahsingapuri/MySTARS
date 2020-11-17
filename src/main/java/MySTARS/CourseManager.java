package MySTARS;

import java.util.Arrays;
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
        System.out.println(String.format("%12.12s ║ %-50.50s ║ %-82.82s", "Course Code", "Course Name", "Course Description"));
        printLine();

        for (HashMap.Entry<String, Course> courseEntry : Database.COURSES.entrySet()) {

            Course course = courseEntry.getValue();

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s ║ %-50.50s ║ %-82.82s", course.getCourseCode(), course.getCourseName(), course.getDescription()));
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
        System.out.println(String.format("%12.12s ║ %-50.50s ║ %-82.82s", "Course Code", "Course Name", "Course Description"));
        printLine();

        for (Course course: student.getCourses(status)) {

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s ║ %-50.50s ║ %-82.82s", course.getCourseCode(), course.getCourseName(), course.getDescription()));   
            }
            printLine();
        }
    }

    /**
     * Prints a list of indices for a particular {@link Course} with the option to print the number of vacanices.
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
     * Prints a list of lessons for a given {@link CourseIndex}.
     * @param index The CourseIndex whose Lessons need printing.
     */
    public static void printLesson(CourseIndex index) {

        printLine();
        System.out.println(String.format("%-10s ║ Lesson Type ║ %-13.13s ║ Location", "Lesson ID.", "Lesson Time"));
        printLine();

        for (Lesson lesson : index.getLessons()) {

            String[] time = lesson.getTime().toString().split("[T:/]");
            System.out.println(String.format("%-10d ║ %-11.11s ║ %2s:%2s - %2s:%2s ║ %-48.48s",lesson.getLessonID(), lesson.getType().label, time[1], time[2], time[6], time[7], lesson.getLocation()));
            printLine();
        }
    }

    /**
     * Prints a lesson information for a given {@link Lesson}.
     * @param lesson The Lesson that needs printing.
     */
    public static void printLesson(Lesson lesson) {

        printLine();
        System.out.println(String.format("%-10s ║ Lesson Type ║ %-13.13s ║ Location", "Lesson ID.", "Lesson Time"));
        printLine();

        String[] time = lesson.getTime().toString().split("[T:/]");
        System.out.println(String.format("%-10d ║ %-11.11s ║ %2s:%2s - %2s:%2s ║ %-48.48s",lesson.getLessonID(), lesson.getType().label, time[1], time[2], time[6], time[7], lesson.getLocation()));
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

        Student[] studentsArray = courseIndex.getStudents();

        if (sorted) {
            Arrays.sort(studentsArray);
        }

        System.out.println("Index Number: " + courseIndex.getCourseIndex());
        printLine();
        System.out.println("Matric No. ║ " + String.format("%-80.80s ║ %6.6s", "Name", "Gender"));
        printLine();

        for (Student student : studentsArray) {
            System.out.println(String.format("%10.10s ║ %-80.80s ║ %-6.6s", student.getMatricNumber(), student.getFirstName() + " " + student.getLastName(), student.getGender().label));
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
