package MySTARS;

import java.util.Arrays;
import java.util.HashMap;

public final class CourseManager {

    protected static void printCourseList(CourseStatus status) {

        printLine();
        System.out.println(" Course Code ║ Course Name");
        printLine();

        for (HashMap.Entry<String, Course> courseEntry : Database.COURSES.entrySet()) {

            Course course = courseEntry.getValue();

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s", course.getCourseCode()) + " ║ " + String.format("%-56.56s", course.getCourseName()));
                printLine();
            }
        }
    }

    protected static void printCourseList(CourseStatus status, Student student) {

        printLine();
        System.out.println(String.format("12.12s ║ %-30.30s ║ %-23.23s", "Course Code", "Course Name", "Course Description"));
        printLine();

        for (Course course: student.getCourses(status)) {

            if (course.getStatus() == status) {
                System.out.println(String.format("%12.12s ║ %-30.30s ║ %-23.23s", course.getCourseCode(), course.getCourseName(), course.getDescription()));   
            }
            printLine();
        }
    }

    protected static void printIndexList(Course course, boolean printVacancies) {

        printLine();
        System.out.print("Course Index");
        if (printVacancies) {
            System.out.print(" ║ Vacancies");
        }
        System.out.println();
        printLine();

        for (CourseIndex courseIndex : course.getIndices()) {
            
            System.out.print(String.format("%12.12s", courseIndex.getCourseIndex()));

            if (printVacancies) {
                System.out.print(" ║ " + String.format("%4d", courseIndex.getVacancies()));
            }

            System.out.println();
            printLine();
        }
    }

    // TODO: Check Regex
    protected static void printLesson(CourseIndex index) {

        printLine();
        System.out.println(String.format("Lesson Type ║ %-13.13s ║ Location", "Lesson Time"));
        printLine();

        for (Lesson lesson : index.getLessons()) {

            String[] time = lesson.getTime().toString().split("[T:/]");
            System.out.println(String.format("%11.11s ║ %2s:%2s - %2s:%2s ║ %-48.48s", lesson.getType().label, time[1], time[2], time[6], time[7], lesson.getLocation()));
            printLine();
        }
    }

    protected static void printStudentListByCourse(Course course, boolean sorted) {

        for (CourseIndex courseIndex : course.getIndices()) {
            printStudentListByIndex(courseIndex, sorted);
        }
    }

    protected static void printStudentListByIndex(CourseIndex courseIndex, boolean sorted) {

        Student[] studentsArray = courseIndex.getStudents();

        if (sorted) {
            Arrays.sort(studentsArray);
        }

        printLine();
        System.out.println("Matric No. ║ " + String.format("%-49.49s ║ %6.6s", "First Name", "Gender"));
        printLine();

        for (Student student : studentsArray) {
            System.out.println(String.format("%10.10s ║ %-49.49s ║ %6.6s", student.getMatricNumber(), student.getFirstName(), student.getGender().label));
            printLine();
        }
    }

    private static void printLine() {
        String line = String.format("%" + 71 + "s", "").replace(" ", "═");
        System.out.println(line);
    }
}
