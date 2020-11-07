package MySTARS;

import java.util.Arrays;
import java.util.HashMap;

public class CourseManager {

    protected CourseManager() {}

    // TODO: Once Course has getCourseName() update to reflect values here
    protected static void printCourseList(CourseStatus status) {

        for (HashMap.Entry<String, Course> courseEntry : Database.COURSES.entrySet()) {

            Course course = courseEntry.getValue();

            if (course.getStatus() == status) {
                System.out.println(course.getCourseCode() + " ║ " /* + String.format("%-30s", course.getCourseName()) + " ║ " */ + String.format("%-20s", course.getDescription()));
                printLine();
            }
        }
    }

    // TODO: once Student has getCourses that return an array of courses
    // TODO: Once Course has getCourseName() update to reflect values here
    protected static void printCourseList(CourseStatus status, Student Student) {

        for (Course course: Student.getCourses(status)) {

            System.out.println(course.getCourseCode() + " ║ " /* + String.format("%-30s", course.getCourseName()) + " ║ " */ + String.format("%-20s", course.getDescription()));
            printLine();
        }
    }

    // TODO: once Course has getIndices that return an array of Indices
    // TODO: once CourseIndex has getIndexNo that returns the String index no
    protected static void printIndexList(Course course, boolean printVacancies) {

        for (CourseIndex courseIndex : course.getIndices()) {
            
            System.out.print(courseIndex.getIndexNo());

            if (printVacancies) {
                System.out.print(" ║ " + String.format("%4d", courseIndex.getVacancies()));
            }

            System.out.println();
            printLine();
        }
    }

    // TODO: once CourseIndex has getLessons that returns the Lessons as an Array
    // TODO: Check that accessing raw value of Enum is done correctly
    protected static void printLesson(CourseIndex index) {

        for (Lesson lesson : index.getLessons()) {
            System.out.println(lesson.getType().label + " ║ " + lesson.getTime() + " ║ " + lesson.getLocation());
            printLine();
        }
    }

    // TODO: once Course has getIndices that return an array of Indices
    protected static void printStudentListByCourse(Course course, boolean sorted) {

        for (CourseIndex courseIndex : course.getIndices()) {
            printStudentListByIndex(courseIndex, sorted);
        }
    }

    // TODO: once CourseIndex has getStudents that returns an array of Students
    // TODO: Check that accessing raw value of Enum is done correctly
    protected static void printStudentListByIndex(CourseIndex courseIndex, boolean sorted) {

        Student[] studentsArray = courseIndex.getStudents();

        if (sorted) {
            Arrays.sort(studentsArray);
        }

        for (Student student : studentsArray) {
            System.out.println(student.getMatricNumber() + " ║ " + student.getUsername() + " ║ " + student.getGender().label);
            printLine();
        }
    }

    private static void printLine() {
        String line = String.format("%" + 74 + "s", "").replace(" ", "═");
        System.out.println(line);
    }
}
