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

        return null != courseIndices.get(courseIndex);
    }


    protected void addIndices(int numIndices) {
        
        String courseIndex;
        int vacancies;
        int numLessons;

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            courseIndex = Helper.sc.nextLine();

            System.out.print("Enter number of vacancies: ");
            vacancies = Helper.sc.nextInt();
            Helper.sc.nextLine();           

            if (!this.containsIndex(courseIndex)) {  
                ArrayList<Lesson> lessons = new ArrayList<>();
                do {
                    System.out.println("Setup for index no. " + courseIndex);
                    System.out.print("Enter number of lessons: ");
                    numLessons = Helper.sc.nextInt();
                    Helper.sc.nextLine();
                    if (numLessons <= 0 || numLessons >= 5) {
                        System.out.println("Number of lessons for a particular index should be between 0 and 5");
                    }
                } while (numLessons <= 0 || numLessons >= 5);

                for (int j = 0; j < numLessons; j++) {

                    
                    String location;
                    DayOfWeek dayOfWeek;
                    int startTime;
                    int endTime;
                
                    System.out.println("Enter details for lesson " + (j+1));
                    ClassType classType = Lesson.chooseClassType();

                    //FIXME error checking!!!
                    System.out.print("Enter day of week for lesson (1: Monday, 2: Tuesday, etc.): ");
                    dayOfWeek = DayOfWeek.getDayOfWeek(Helper.sc.nextInt());
                    Helper.sc.nextLine();

                    System.out.print("Enter the class start time in 24h format (eg. 1430): ");
                    startTime = Helper.sc.nextInt();
                    Helper.sc.nextLine();

                    System.out.print("Enter the class end time in 24h format (eg. 1530): ");
                    endTime = Helper.sc.nextInt();
                    Helper.sc.nextLine();

                    System.out.print("Enter lesson location: ");
                    location = Helper.sc.nextLine();

                    Lesson lesson = new Lesson(classType, dayOfWeek, startTime, endTime, location);
                    lessons.add(lesson);
                }
                CourseIndex index = new CourseIndex(vacancies, this.courseCode, courseIndex, lessons);
                courseIndices.put(courseIndex, index);
            }
            System.out.println(courseIndex + " added to courseIndices!");
        }
    }

    protected void addIndex(CourseIndex courseIndex) {

        courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
        Database.serialise(FileType.COURSES);
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
