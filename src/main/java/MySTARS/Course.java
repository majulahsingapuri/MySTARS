package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

/**
 * Represents one course
 * @author DSAI2 Group 1
 * @version 1.0
 * @since 2020-11-1
 */
public final class Course implements Serializable {

    /**
     * The course code (eg. CZ2002).
     */
    private String courseCode = "";

    /**
     * Full course name (eg. Object Oriented Design Programming).
     */
    private String courseName = "";

    /**
     * Course description.
     */
    private String description = "";

    /**
     * Number of AUs credited for passing the course (default = 1).
     */
    private AU acadUnits = AU.ONE;

    /**
     * Hashmap of all available indices in the course.
     */
    private HashMap<String, CourseIndex> courseIndices = new HashMap<>();

    /**
     * Status of a the course in relation to a student (eg. registered, waitlisted etc.)
     */
    private CourseStatus courseStatus = CourseStatus.NONE;

    /**
     * For Java serializable.
     */
    private static final long serialVersionUID = 10L;

    /**
     * Constructor for course object, to be created without a description and course status.
     * @param coursecode course code (eg. cz2002).
     * @param coursename course name  (eg. object oriented design programming).
     * @param acadunits number of aus credited upon completion of course.
     */
    protected Course(String courseCode, String courseName, AU acadUnits) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
    }

    /**
     * Constructor for course object, to be created with a description but without course status.
     * @param coursecode course code (eg. cz2002).
     * @param coursename course name  (eg. object oriented design programming).
     * @param acadunits number of aus credited upon completion of course.
     * @param description course description.
     */
    protected Course(String courseCode, String courseName, AU acadUnits, String description) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
    }

    /**
     * Constructor for course object, to be created with a description and with course status.
     * @param coursecode course code (eg. cz2002).
     * @param coursename course name  (eg. object oriented design programming).
     * @param acadunits number of aus credited upon completion of course.
     * @param description course description.
     * @param courseStatus enrollment status of a particular student.
     */
    protected Course(String courseCode, String courseName, AU acadUnits, String description, CourseStatus courseStatus) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.courseStatus = courseStatus;
    }

    //TODO: should this taken in a list of courses instead?
    /**
     * Constructor for course object, creates course with description, course status, and an initial course index.
     * @param coursecode course code (eg. cz2002).
     * @param coursename course name  (eg. object oriented design programming).
     * @param acadunits number of aus credited upon completion of course.
     * @param description course description.
     * @param courseStatus enrollment status of a particular student.
     * @param courseIndex course index to initialize with this course.
     */
    protected Course(String courseCode, String courseName, AU acadUnits, String description, CourseStatus courseStatus, CourseIndex courseIndex) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.courseStatus = courseStatus;
        this.courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
    }

    /**
     * Set course code.
     * @param courseCode the course code as a string.
     */
    protected void setCourseCode(String courseCode) {

        this.courseCode = courseCode;
    }

    /**
     * Returns the course code.
     * @return course code.
     */
    protected String getCourseCode() {

        return this.courseCode;
    }

    /**
     * Set course name.
     * @param courseName the name of the course.
     */
    protected void setCourseName(String courseName) {

        this.courseName = courseName;
    }

    /**
     * Return the course name.
     * @return the name of the course.
     */
    protected String getCourseName() {

        return this.courseName;
    }

    /**
     * Set the course description.
     * @param description the course description.
     */
    protected void setDescription(String description) {

        this.description = description;
    }

    /**
     * Return the course description.
     * @return the course description.
     */
    protected String getDescription() {

        return this.description;
    }

    /**
     * Set the number of AUs to be credited upon completion of the course.
     * @param acadUnits number of AUs to be credited upon completion of the course.
     */
    protected void setCourseAU(AU acadUnits) {

        this.acadUnits = acadUnits;
    }

    /**
     * Return the number of AUs credited upon completion of the course.
     * @return the number of AUs credited upon completion of the course.
     */
    protected AU getCourseAU() {

        return this.acadUnits;
    }

    /**
     * Set the status of a student's enrollment in the course.
     * @param courseStatus the status of a student's enrollment in the course.
     */
    protected void setStatus(CourseStatus courseStatus) {

        this.courseStatus = courseStatus;
    }

    /**
     * Return the status of a student's enrollment in the course.
     * @return the status of a student's enrollment in the course.
     */
    protected CourseStatus getStatus() {

        return this.courseStatus;
    }

    /**
     * Returns a course index object by taking the index number as a parameter.
     * @param courseIndex the index number of the course to be returned.
     * @return a course index object corresponding to the specified key value.
     */
    protected CourseIndex getIndex(String courseIndex) {

        return courseIndices.get(courseIndex);
    }

    /**
     * Check if the course has a particular index number.
     * @param courseIndex number of the index that is being queried.
     * @return
     */
    protected boolean containsIndex(String courseIndex) {

        return null != courseIndices.get(courseIndex);
    }


    /**
     * Add a user defined number of indices to the course.
     * Includes prompts for the user to enter all relevant details for each index and lesson.
     * @param numIndices integer number of indices to add to the course.
     */
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

    /**
     * Return an array all index numbers registered the course.
     * @return an array all index numbers registered the course.
     */
    protected String[] getIndicesString() {
        return courseIndices.keySet().toArray(new String[courseIndices.size()]);
    }

    /**
     * Return an array of all indices registered under the course.
     * @return an array of all indices registered under the course.
     */
    protected CourseIndex[] getIndices() {
        return courseIndices.values().toArray(new CourseIndex[courseIndices.size()]);
    }

    //TODO: Need to clarify what this is meant to do
    /**
     * Make a simple copy of the course.
     * @param status 
     * @param courseIndex
     * @return
     */
    protected Course simpleCopy(CourseStatus status, CourseIndex courseIndex){
        return new Course(this.courseCode, this.courseName, this.acadUnits, this.description, status, courseIndex);
    }
}
