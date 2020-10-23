package MySTARS;

import java.util.HashMap;
import java.io.*;

public final class Course implements Serializable {

    private String courseCode = "";
    private HashMap<String, CourseIndex> courseIndices = new HashMap<>();
    private String description = "";
    private CourseStatus courseStatus = CourseStatus.NONE;
    private static final long serialVersionUID = 10L;

    protected Course(String courseCode) {

        this.courseCode = courseCode;
    }

    protected Course(String courseCode, String description) {

        this.courseCode = courseCode;
        this.description = description;
    }

    protected Course(String courseCode, CourseStatus courseStatus) {

        this.courseCode = courseCode;
        this.courseStatus = courseStatus;
    }

    protected Course(String courseCode, CourseIndex courseIndex, CourseStatus courseStatus) {

        //TODO add shortcut to add in course index to course. 
        // - useful for adding to student class so you can just copy over the CourseIndex and remove any extra bits
        // - Can create a method to replicate this functionality too
        this.courseCode = courseCode;
        this.courseStatus = courseStatus;
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

    protected void setStatus(CourseStatus courseStatus) {

        this.courseStatus = courseStatus;
    }

    protected CourseStatus getStatus() {

        return this.courseStatus;
    }

    protected CourseIndex getIndex(String index) {

        //TODO add console log for null courseIndex
        CourseIndex courseIndex = courseIndices.get(index);
        if (courseIndex != null) {
            return courseIndex;
        } else {
            System.out.println("error, index not found?");
            return null;
        }
    }

    protected void addIndices(int numIndices) {

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            String courseIndex = Helper.sc.nextLine(); 

            System.out.print("Enter number of vacancies: ");
            int vacancies = Helper.sc.nextInt();            

            // create new CourseIndex object and add to hashmap courseIndices
            //TODO check that the course index doesnt already exist in the course to prevent duplicate entries
            CourseIndex index = new CourseIndex(vacancies, courseIndex);
            courseIndices.put(courseIndex, index);
            System.out.println(courseIndex + " added to courseIndices");
        }
    }

    // method to return an array of all the index numbers
    // TODO check that the output of this is correct
    protected String[] getIndices() {
        return courseIndices.keySet().toArray(new String[courseIndices.size()]);
    }
}
