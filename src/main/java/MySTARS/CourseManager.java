package MySTARS;

import java.util.Arrays;
import java.util.HashMap;

public class CourseManager {

    // TODO: Check print formatting
    protected static void printCourseList(CourseStatus status) {

        for (HashMap.Entry<String, Course> courseEntry : Database.COURSES.entrySet()) {

            Course course = courseEntry.getValue();

            if (course.getStatus() == status) {
                System.out.println(course.getCourseCode() + " ║ " + String.format("%-55s", course.getCourseName()));
                printLine();
            }
        }
    }

    // TODO check print formatting
    protected static void printCourseList(CourseStatus status, Student Student) {

        for (Course course: Student.getCourses(status)) {

            System.out.println(course.getCourseCode() + " ║ " + String.format("%-30s", course.getCourseName()) + " ║ " + String.format("%-20s", course.getDescription()));
            printLine();
        }
    }

    protected static void printIndexList(Course course, boolean printVacancies) {

        for (CourseIndex courseIndex : course.getIndices()) {
            
            System.out.print(courseIndex.getCourseIndex());

            if (printVacancies) {
                System.out.print(" ║ " + String.format("%4d", courseIndex.getVacancies()));
            }

            System.out.println();
            printLine();
        }
    }

    // TODO: Check Regex
    protected static void printLesson(CourseIndex index) {

        for (Lesson lesson : index.getLessons()) {

            String[] time = lesson.getTime().toString().split("[T:/]");
            System.out.println(lesson.getType().label + " ║ " + String.format("%s:%s - %s:%s", time[1], time[2], time[5], time[6]) + " ║ " + String.format("%55s", lesson.getLocation()));
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

        for (Student student : studentsArray) {
            System.out.println(String.format("%9s", student.getMatricNumber()) + " ║ " + String.format("%-61s", student.getFirstName()) + " ║ " + String.format("%4s", student.getGender().label));
            printLine();
        }
    }

    private static void printLine() {
        String line = String.format("%" + 74 + "s", "").replace(" ", "═");
        System.out.println(line);
    }
}
