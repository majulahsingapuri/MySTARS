package MySTARS;

import java.util.HashMap;
import java.io.*;

public class Course implements Serializable{

    private String courseCode;
    private HashMap<String, CourseIndex> courseIndices = new HashMap<>();
    private String description;
    private CourseStatus status = CourseStatus.NONE;
    private static final long serialVersionUID = 10L;

    protected Course(String courseCode) {

        this.courseCode = courseCode;
    }

    protected Course(String courseCode, String description) {

        this.courseCode = courseCode;
        this.description = description;
    }

    protected void setDescription(String description) {

        this.description = description;
    }

    protected String getDescription() {

        return this.description;
    }

    protected void setCourseCode(String courseCode) {

        this.courseCode = courseCode;
    }

    protected String getCourseCode() {

        return this.courseCode;
    }

    protected void setStatus(CourseStatus status) {

        this.status = status;
    }

    protected CourseStatus getStatus() {

        return this.status;
    }

    protected CourseIndex getIndex(String index) {

        return courseIndices.get(index);
    }

    protected void addIndices(int numIndices) {

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            String courseIndex = Helper.sc.nextLine(); 

            System.out.print("Enter number of vacancies: ");
            int vacancies = Helper.sc.nextInt();            

            // create new CourseIndex object and add to hashmap courseIndices
            CourseIndex index = new CourseIndex(vacancies, courseIndex);
            courseIndices.put(courseIndex, index);
            System.out.println(courseIndex + " added to courseIndices");
        }
    }
}
