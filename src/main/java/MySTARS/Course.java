package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.io.*;

/**
 * Represents one course
 * @author Timothy
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
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     */
    protected Course(String courseCode, String courseName, AU acadUnits) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
    }

    /**
     * Constructor for course object, to be created with a description but without course status.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     */
    protected Course(String courseCode, String courseName, AU acadUnits, String description) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
    }

    /**
     * Constructor for course object, to be created with a description and with course status.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     * @param courseStatus Enrollment status of a particular Student.
     */
    protected Course(String courseCode, String courseName, AU acadUnits, String description, CourseStatus courseStatus) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.courseStatus = courseStatus;
    }

    /**
     * Constructor for course object, creates course with description, course status, and an initial course index.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     * @param courseStatus Enrollment status of a particular student.
     * @param courseIndex Course index to initialize with this course.
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
     * Sets courseCode.
     * @param courseCode The courseCode as a String.
     */
    protected void setCourseCode(String courseCode) {

        this.courseCode = courseCode;
    }

    /**
     * Returns the courseCode.
     * @return courseCode.
     */
    protected String getCourseCode() {

        return this.courseCode;
    }

    /**
     * Set courseName.
     * @param courseName The name of the Course.
     */
    protected void setCourseName(String courseName) {

        this.courseName = courseName;
    }

    /**
     * Return the courseName.
     * @return The name of the Course.
     */
    protected String getCourseName() {

        return this.courseName;
    }

    /**
     * Set the Course description.
     * @param description The Course description.
     */
    protected void setDescription(String description) {

        this.description = description;
    }

    /**
     * Return the Course description.
     * @return The Course description.
     */
    protected String getDescription() {

        return this.description;
    }

    /**
     * Set the number of AUs to be credited upon completion of the Course.
     * @param acadUnits Number of AUs to be credited upon completion of the Course.
     */
    protected void setCourseAU(AU acadUnits) {

        this.acadUnits = acadUnits;
    }

    /**
     * Return the number of AUs credited upon completion of the Course.
     * @return The number of AUs credited upon completion of the Course.
     */
    protected AU getCourseAU() {

        return this.acadUnits;
    }

    /**
     * Set the CourseStatus of a Student's enrollment in the Course.
     * @param courseStatus The status of a Student's enrollment in the Course.
     */
    protected void setStatus(CourseStatus courseStatus) {

        this.courseStatus = courseStatus;
    }

    /**
     * Return the CourseStatus of a Student's enrollment in the Course.
     * @return The CourseStatus of a Student's enrollment in the Course.
     */
    protected CourseStatus getStatus() {

        return this.courseStatus;
    }

    /**
     * Returns a CourseIndex object by taking the index number as a parameter.
     * @param courseIndex The index number of the course to be returned.
     * @return A CourseIndex object corresponding to the specified key value.
     */
    protected CourseIndex getIndex(String courseIndex) {

        return courseIndices.get(courseIndex);
    }

    /**
     * Check if the Course has a particular Index number.
     * @param courseIndex Number of the index that is being queried.
     * @return {@code true} if Course contains given index.
     */
    protected boolean containsIndex(String courseIndex) {

        return null != courseIndices.get(courseIndex);
    }


    /**
     * Add a User defined number of CourseIndices to the course.
     * Includes prompts for the User to enter all relevant details for each CourseIndex and Lesson.
     * @param numIndices Integer number of indices to add to the Course.
     */
    protected void addIndices(int numIndices) {
        
        String courseIndex;
        int vacancies;
        int numLessons;

        for(int i = 0; i < numIndices; i++) {
            System.out.print("Enter index number to add: ");
            courseIndex = Helper.readLine();

            while (true) {
                try {
                    System.out.print("Enter number of vacancies: ");
                    vacancies = Integer.parseInt(Helper.readLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a number");
                }
            }

            if (!this.containsIndex(courseIndex)) {  
                ArrayList<Lesson> lessons = new ArrayList<Lesson>();
                do {
                    System.out.println("Setup for index no. " + courseIndex);

                    while (true) {
                        try {
                            System.out.print("Enter number of lessons: ");
                            numLessons = Integer.parseInt(Helper.readLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("Please enter a number");
                        }
                    }
                    
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

                    while (true) {
                        try {
                            System.out.print("Enter day of week for lesson (1: Monday, 2: Tuesday, etc.): ");
                            int num = Integer.parseInt(Helper.readLine());
                            if (num < 1 || num > 7) {
                                throw new Exception();
                            }
                            dayOfWeek = DayOfWeek.getDayOfWeek(num);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number");
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("Enter the class start time in 24h format (eg. 1430): ");
                            String time = Helper.readLine();
                            if (LocalTime.parse(time, DateTimeFormat.forPattern("HHmm")) != null) {
                                startTime = Integer.parseInt(time);
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid time");
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("Enter the class end time in 24h format (eg. 1530): ");
                            endTime = Integer.parseInt(Helper.readLine());
                            String time = Helper.readLine();
                            if (LocalTime.parse(time, DateTimeFormat.forPattern("HHmm")) != null) {
                                endTime = Integer.parseInt(time);
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number");
                        }
                    }

                    System.out.print("Enter lesson location: ");
                    location = Helper.readLine();

                    Lesson lesson = new Lesson(classType, dayOfWeek, startTime, endTime, location);
                    lessons.add(lesson);
                }
                CourseIndex index = new CourseIndex(vacancies, this.courseCode, courseIndex, lessons);
                courseIndices.put(courseIndex, index);
                Database.serialise(FileType.COURSES);
            }
            System.out.println(courseIndex + " added to courseIndices!");
        }
    }

    /**
     * Directly adds a particular CourseIndex to the current Course.
     * @param courseIndex The index to be added to the Course.
     */
    protected void addIndex(CourseIndex courseIndex) {

        courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
        Database.serialise(FileType.COURSES);
    }

    /**
     * Return an array all index numbers registered the Course.
     * @return An array all index numbers registered the Course.
     */
    protected String[] getIndicesString() {
        return courseIndices.keySet().toArray(new String[courseIndices.size()]);
    }

    /**
     * Returns an array of all indices registered under the Course.
     * @return An array of all indices registered under the Course.
     */
    protected CourseIndex[] getIndices() {
        return courseIndices.values().toArray(new CourseIndex[courseIndices.size()]);
    }

    /**
     * Make a simple copy of the course to be stored under the {@link Student} object.
     * @param status The current status of the Course.
     * @param courseIndex The Index that the Student registered for.
     * @return A Course object with all the basic information with the current CourseStatus and CourseIndex. 
     */
    protected Course simpleCopy(CourseStatus status, CourseIndex courseIndex){
        return new Course(this.courseCode, this.courseName, this.acadUnits, this.description, status, courseIndex);
    }
}
