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

    protected ClassType chooseClassType() {
        
        System.out.println("1. Lecture");
        System.out.println("2. Lab");
        System.out.println("3. Tutorial");
        System.out.println("4. Seminar");
        System.out.println("5. Online");
        System.out.println("Enter the lesson type for this lesson:");

        int classTypeChoice; 

        do{
            classTypeChoice = Helper.sc.nextInt();
            switch (classTypeChoice) {
                case 1: 
                    return ClassType.LECTURE;
                case 2:
                    return ClassType.LAB;
                case 3:
                    return ClassType.TUTORIAL;
                case 4:
                    return ClassType.SEMINAR;
                case 5:
                    return ClassType.ONLINE;
                default:
                    System.out.println("Error, invalid input");
                    return ClassType.LECTURE;  // TODO: Check if this is right?
            }
        } while(classTypeChoice < 0 || classTypeChoice > 5);
    }


    protected void addIndices(int numIndices) {
        
        String courseIndex;
        int vacancies;
        int numLessons;

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            courseIndex = Helper.sc.nextLine(); 
            System.out.println("");

            System.out.print("Enter number of vacancies: ");
            vacancies = Helper.sc.nextInt();            

            if (!this.containsIndex(courseIndex)) {  
                ArrayList<Lesson> lessons = new ArrayList<>();
                do {
                    System.out.println("Setup for index no. " + courseIndex);
                    System.out.print("Enter number of lessons: ");
                    numLessons = Helper.sc.nextInt();
                    if (numLessons <= 0 || numLessons >= 5) {
                        System.out.println("Number of lessons for a particular index should be between 0 and 5");
                    }
                } while (numLessons <= 0 || numLessons >= 5);

                for (int j = 0; j < numLessons; j++) {

                    
                    String location;
                    DayOfWeek dayOfWeek;
                    int startTime;
                    int endTime;
                
                    System.out.println("Enter details for lesson " + j+1);
                    ClassType classType = chooseClassType();

                    System.out.print("Enter day of week for lesson (1: Monday, 2: Tuesday, etc.): ");
                    dayOfWeek = DayOfWeek.getDayOfWeek(Helper.sc.nextInt());

                    System.out.print("Enter the class start time in 24h format (eg. 1430): ");
                    startTime = Helper.sc.nextInt();

                    System.out.print("Enter the class end time in 24h format (eg. 1530): ");
                    endTime = Helper.sc.nextInt();

                    System.out.print("Enter lesson location: ");
                    location = Helper.sc.nextLine();

                    Lesson lesson = new Lesson(classType, dayOfWeek, startTime, endTime, location);
                    lessons.add(lesson);
                }
                CourseIndex index = new CourseIndex(vacancies, courseIndex, lessons);  // add new indices to course list
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
}
