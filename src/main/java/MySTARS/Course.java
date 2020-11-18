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
     * The school that offers this course.
     */
    private String school = "";

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
     * Constructor for course object, to be created without a description, school and course status.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     */
    public Course(String courseCode, String courseName, AU acadUnits) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
    }

    /**
     * Constructor for course object, to be created with a description and school, but without course status.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     * @param school School that offers this course
     */
    public Course(String courseCode, String courseName, AU acadUnits, String description, String school) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.school = school;
    }

    /**
     * Constructor for course object, to be created with a description, school and course status.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     * @param school School that offers this course
     * @param courseStatus Enrollment status of a particular Student.
     */
    public Course(String courseCode, String courseName, AU acadUnits, String description, String school, CourseStatus courseStatus) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.school = school;
        this.courseStatus = courseStatus;
    }

    /**
     * Constructor for course object, creates course with description, school, course status, and an initial course index.
     * @param courseCode Course code (eg. cz2002).
     * @param courseName Course name (eg. object oriented design programming).
     * @param acadUnits Number of AUs credited upon completion of course.
     * @param description Course description.
     * @param school School that offers this course
     * @param courseStatus Enrollment status of a particular student.
     * @param courseIndex Course index to initialize with this course.
     */
    public Course(String courseCode, String courseName, AU acadUnits, String description, String school, CourseStatus courseStatus, CourseIndex courseIndex) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.acadUnits = acadUnits;
        this.description = description;
        this.school = school;
        this.courseStatus = courseStatus;
        this.courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
    }

    /**
     * Sets courseCode.
     * @param courseCode The courseCode as a String.
     */
    public void setCourseCode(String courseCode) {

        this.courseCode = courseCode;
    }

    /**
     * Returns the courseCode.
     * @return courseCode.
     */
    public String getCourseCode() {

        return this.courseCode;
    }

    /**
     * Set courseName.
     * @param courseName The name of the Course.
     */
    public void setCourseName(String courseName) {

        this.courseName = courseName;
    }

    /**
     * Return the courseName.
     * @return The name of the Course.
     */
    public String getCourseName() {

        return this.courseName;
    }

    /**
     * Set the Course description.
     * @param description The Course description.
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Return the Course description.
     * @return The Course description.
     */
    public String getDescription() {

        return this.description;
    }

    /**
     * Set the number of AUs to be credited upon completion of the Course.
     * @param acadUnits Number of AUs to be credited upon completion of the Course.
     */
    public void setCourseAU(AU acadUnits) {

        this.acadUnits = acadUnits;
    }

    /**
     * Return the number of AUs credited upon completion of the Course.
     * @return The number of AUs credited upon completion of the Course.
     */
    public AU getCourseAU() {

        return this.acadUnits;
    }

    /**
     * Sets school.
     * @param school The school that offers this course as a String.
     */
    public void setSchool(String school) {

        this.school = school;
    }

    /**
     * Return the school offering this course.
     * @return The school that offers this course.
     */
    public String getSchool() {

        return this.school;
    }

    /**
     * Set the CourseStatus of a Student's enrollment in the Course.
     * @param courseStatus The status of a Student's enrollment in the Course.
     */
    public void setStatus(CourseStatus courseStatus) {

        this.courseStatus = courseStatus;
    }

    /**
     * Return the CourseStatus of a Student's enrollment in the Course.
     * @return The CourseStatus of a Student's enrollment in the Course.
     */
    public CourseStatus getStatus() {

        return this.courseStatus;
    }

    /**
     * Returns a CourseIndex object by taking the index number as a parameter.
     * @param courseIndex The index number of the course to be returned.
     * @return A CourseIndex object corresponding to the specified key value.
     */
    public CourseIndex getIndex(String courseIndex) {

        return courseIndices.get(courseIndex);
    }

    /**
     * Check if the Course has a particular Index number.
     * @param courseIndex Number of the index that is being queried.
     * @return {@code true} if Course contains given index.
     */
    public boolean containsIndex(String courseIndex) {

        return null != courseIndices.get(courseIndex);
    }


    /**
     * Add a User defined number of CourseIndices to the course.
     * Includes prompts for the User to enter all relevant details for each CourseIndex and Lesson.
     * @param numIndices Integer number of indices to add to the Course.
     */
    public void addIndices(int numIndices) {
        
        String courseIndex;
        int classSize, numLessons;

        for(int i = 0; i < numIndices; i++) {
            
            System.out.println("Setup for index no. " + (i + 1));
            Helper.printLine(150);

            while (true) {
                System.out.print(String.format("%-50s: ", "Enter index number"));
                courseIndex = Helper.readLine();
                if (courseIndex.length() == 5 && courseIndex.matches("(^\\d{5}$)") && !this.containsIndex(courseIndex)) {
                    break;
                } else {
                    System.out.println("Enter a valid 5 digit index number.");
                }
            }

            ArrayList<Lesson> lessons = new ArrayList<Lesson>();

            while (true) {
                try {
                    System.out.print(String.format("%-50s: ", "Enter the class size"));
                    classSize = Integer.parseInt(Helper.readLine());
                    if (classSize < 1 || classSize > 50) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a number");
                }
            }
            
            while (true) {
                try {
                    System.out.print(String.format("%-50s: ", "Enter number of lessons"));
                    numLessons = Integer.parseInt(Helper.readLine());
                    if (numLessons < 1 || numLessons > 5) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a number between 1 and 5");
                }
            }

            Helper.printSmallSpace();

            for (int j = 0; j < numLessons; j++) {

                while (true) {
                    String location;
                    DayOfWeek dayOfWeek;
                    int startTime, endTime;
                    LocalTime startLocalTime, endLocalTime;
                
                    System.out.println("Enter details for lesson " + (j+1));
                    Helper.printLine(150);
                    
                    ClassType classType = Lesson.chooseClassType();

                    while (true) {
                        try {
                            System.out.print(String.format("%-50s: ", "Enter day of week (1: Monday, etc.)"));
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
                        while (true) {
                            try {
                                System.out.print(String.format("%-50s: ", "Enter start time in 24h format (eg 1430)"));
                                String stime = Helper.readLine();
                                startLocalTime = LocalTime.parse(stime, DateTimeFormat.forPattern("HHmm"));
                                if (startLocalTime != null) {
                                    startTime = Integer.parseInt(stime);
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
                                System.out.print(String.format("%-50s: ", "Enter end time in 24h format (eg 1530)"));
                                String etime = Helper.readLine();
                                endLocalTime = LocalTime.parse(etime, DateTimeFormat.forPattern("HHmm"));
                                if (endLocalTime != null) {
                                    endTime = Integer.parseInt(etime);
                                    break;
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter a valid time");
                            }
                        }

                        //TODO: check if this expression evaluates correctly
                        if (endLocalTime.isAfter(startLocalTime) && !startLocalTime.isEqual(endLocalTime)) {
                            break;
                        } else {
                            System.out.println("Start time must be before end time!");
                        }
                    }

                    System.out.print(String.format("%-50s: ", "Enter lesson location"));
                    location = Helper.readLine();

                    Lesson lesson = new Lesson(classType, dayOfWeek, startTime, endTime, location);
                    if (!lesson.clashes(lessons)) {
                        
                        CourseManager.printLesson(lesson);
                        System.out.print(String.format("%-50s: ", "This lesson will be added to " + courseIndex + " confirm? y/n"));
                        String choice = Helper.readLine();
                        if (choice.equals("Y")){
                            lessons.add(lesson);
                            Helper.printSmallSpace();
                            break;
                        } else {
                            System.out.println("The lesson was not added.");
                        }
                    } else {
                        System.out.println("This lesson clashes with the other lessons in the course!");
                        Helper.pause();
                    }

                    Helper.printSmallSpace();
                }
            }
            CourseIndex index = new CourseIndex(classSize, this.courseCode, courseIndex, lessons);
            courseIndices.put(courseIndex, index);
            Database.serialise(FileType.COURSES);
            CourseManager.printCourseDetails(this);
            System.out.println(courseIndex + " added to courseIndices!");
            Helper.printMediumSpace();
        }
    }

    /**
     * Directly removes a particular CourseIndex to the current Course.
     * @param courseIndex The index to be removed to the Course.
     * @return The removed CourseIndex
     */
    public CourseIndex removeIndex(String courseIndex) {

        CourseIndex removedIndex = courseIndices.remove(courseIndex);
        Database.serialise(FileType.COURSES);
        return removedIndex;
    }

    /**
     * Directly adds a particular CourseIndex to the current Course.
     * @param courseIndex The index to be added to the Course.
     */
    public void addIndex(CourseIndex courseIndex) {

        courseIndices.put(courseIndex.getCourseIndex(), courseIndex);
        Database.serialise(FileType.COURSES);
    }

    /**
     * Return an array all index numbers registered the Course.
     * @return An array all index numbers registered the Course.
     */
    public String[] getIndicesString() {
        return courseIndices.keySet().toArray(new String[courseIndices.size()]);
    }

    /**
     * Returns an array of all indices registered under the Course.
     * @return An array of all indices registered under the Course.
     */
    public CourseIndex[] getIndices() {
        return courseIndices.values().toArray(new CourseIndex[courseIndices.size()]);
    }

    /**
     * Make a simple copy of the course to be stored under the {@link Student} object.
     * @param status The current status of the Course.
     * @param courseIndex The Index that the Student registered for.
     * @return A Course object with all the basic information with the current CourseStatus and CourseIndex. 
     */
    public Course simpleCopy(CourseStatus status, CourseIndex courseIndex){
        return new Course(this.courseCode, this.courseName, this.acadUnits, this.description, this.school, status, courseIndex);
    }
}
