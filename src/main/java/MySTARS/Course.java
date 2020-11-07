package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public final class Course implements Serializable {

    private String courseCode = "";
    private String courseName = "";
    private String description = "";
    private AU acadUnits = AU.ONE;
    private HashMap<String, CourseIndex> courseIndices = new HashMap<>();
    private CourseStatus courseStatus = CourseStatus.NONE;
    private static final long serialVersionUID = 10L;

    protected Course(String courseCode, String courseName, AU acadUnits) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
    }

    protected Course(String courseCode, String courseName, AU acadUnits, String description) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
    }

    protected Course(String courseCode, String courseName, AU acadUnits, String description, CourseStatus courseStatus) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.courseStatus = courseStatus;
    }

    protected Course(String courseCode, String courseName, AU acadUnits, String description, CourseStatus courseStatus, CourseIndex courseIndex) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.courseStatus = courseStatus;
        this.courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
    }

    protected void setCourseCode(String courseCode) {

        this.courseCode = courseCode;
    }

    protected String getCourseCode() {

        return this.courseCode;
    }

    protected void setCourseName(String courseName) {

        this.courseName = courseName;
    }

    protected String getCourseName() {

        return this.courseName;
    }

    protected void setDescription(String description) {

        this.description = description;
    }

    protected String getDescription() {

        return this.description;
    }

    protected void setCourseAU(AU acadUnits) {

        this.acadUnits = acadUnits;
    }

    protected AU getCourseAU() {

        return this.acadUnits;
    }

    protected void setStatus(CourseStatus courseStatus) {

        this.courseStatus = courseStatus;
    }

    protected CourseStatus getStatus() {

        return this.courseStatus;
    }

    protected CourseIndex getIndex(String courseIndex) {

        return courseIndices.get(courseIndex);
    }

    protected boolean containsIndex(String courseIndex) {

        return null == courseIndices.get(courseIndex);
    }

    protected void addIndices(int numIndices) {
        
        String courseIndex, classType;
        int vacancies, numLessons, startTime, endTime;

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            courseIndex = Helper.sc.nextLine(); 

            System.out.print("Enter number of vacancies: ");
            vacancies = Helper.sc.nextInt();            

            if (!this.containsIndex(courseIndex)) {
                ArrayList<Lesson> lessons = new ArrayList<Lesson>();
                do {
                    System.out.print("Enter number of lessons: ");
                    numLessons = Helper.sc.nextInt();
                    if (numLessons <= 0 || numLessons >= 5) {
                        System.out.println("You have entered an incorrect number of lessons!");
                    }
                } while (numLessons <= 0 || numLessons >= 5);

                for (int j = 0; j < numLessons; j++) {
                    System.out.print("Enter the class Type: ");
                    classType = Helper.sc.nextLine();
                    // TODO put down the time format
                    System.out.print("Enter the class start time: ");
                    startTime = Helper.sc.nextInt();
                    System.out.print("Enter the class end time: ");
                    endTime = Helper.sc.nextInt();
                    // TODO Add new lesson that is correctly formatted with appropriate constructor
                    // lessons.add(new Lesson(type, time, location));
                }
                CourseIndex index = new CourseIndex(vacancies, this.courseCode, courseIndex, lessons);
                courseIndices.put(courseIndex, index);
            }
            System.out.println(courseIndex + " added to courseIndices!");
        }
    }

    protected String[] getIndicesString() {
        return courseIndices.keySet().toArray(new String[courseIndices.size()]);
    }

    protected CourseIndex[] getIndices() {
        return courseIndices.values().toArray(new CourseIndex[courseIndices.size()]);
    }

    protected Course simpleCopy(CourseStatus status, CourseIndex courseIndex){
        return new Course(this.courseCode, this.courseName, this.acadUnits, this.description, status, courseIndex);
    }
}
