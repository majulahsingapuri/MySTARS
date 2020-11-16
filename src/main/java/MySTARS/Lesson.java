package MySTARS;

import java.io.Serializable;
import java.util.Random;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Represents one lesson timeslot
 * @author Timothy
 * @version 1.0
 * @since 2020-11-1
 */

public final class Lesson implements Serializable {

    /**
     * The type of class (lecture, tutorial etc.) which is an enum ClassType.
     */
    private ClassType classType;
    
    /**
     * The allocated block of time for the lesson, of type Interval.
     */
    private Interval time;

    /**
     * The start time of the lesson in 24h time (4 digit integer).
     */
    private DateTime startTime;

    /**
     * The end time of the lesson in 24h time (4 digit integer).
     */
    private DateTime endTime;

    /**
     * The day of the week when the lesson occurs (Monday - Sunday).
     */
    private DayOfWeek dayOfWeek;

    /**
     * Location of the lesson 
     */
    private String location;

    /**
     * Additional remarks (eg. If lesson only occurs every other week).
     */
    private String remarks = "";

    /**
     * Lesson ID, used to identify and update lessons.
     */
    private final Integer lessonID;

    /**
     * For java serializable
     */
    private static final long serialVersionUID = 12L;


    /**
     * Lesson constructor, creates new lesson of specified ClassType on a certain day of the week.
     * 
     * @param classType an enum giving the type of class (lecture, tutorial etc.).
     * @param dayOfWeek integer representing the day of the week.
     * @param startTime start time of lesson in 24h format.
     * @param endTime end time of lesson in 24h format.
     * @param location location of lesson.
     */
    protected Lesson(ClassType classType, DayOfWeek dayOfWeek, int startTime, int endTime, String location) {

        this.lessonID = Integer.valueOf(10000 + new Random().nextInt(90000));
        this.classType = classType;
        this.startTime = new DateTime(2020, 1, dayOfWeek.value, startTime/100, startTime%100);
        this.endTime = new DateTime(2020, 1, dayOfWeek.value, endTime/100, endTime%100);
        this.time = new Interval(this.startTime, this.endTime);
        this.dayOfWeek = dayOfWeek;
        this.location = location;
    }

    /**
     * Method to prompt user to choose the type of class
     * @return The corresponding ClassType object
     */
    protected static ClassType chooseClassType() {
        
        System.out.println("1. Lecture");
        System.out.println("2. Lab");
        System.out.println("3. Tutorial");
        System.out.println("4. Seminar");
        System.out.println("5. Online");
        System.out.println("Enter the lesson type for this lesson:");

        int classTypeChoice; 

        do{
            classTypeChoice = Helper.sc.nextInt();
            Helper.sc.nextLine();
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
            }
        } while(true);
    }

    /**
     * Returns a ClassType enum specifying the type of the specified lesson.
     * @return ClassType enum specifying the type of the specified lesson.
     */
    protected ClassType getType() {

        return this.classType;
    }

    /**
     * Returns the interval of time where the lesson occurs, as a JodaTime Interval object.
     * @return interval of time where the lesson occurs, as a JodaTime Interval object.
     */
    protected Interval getTime() {

        return this.time;
    }

    /**
     * Returns starting time of the lesson slot, in JodaTime DateTime object (year, month, day of week, hour, minute).
     * @return starting time of the lesson slot, in JodaTime DateTime object (year, month, day of week, hour, minute).
     */
    protected DateTime getStartTime() {

        return this.startTime;
    }

    /**
     * Returns end time of the lesson slot, in JodaTime DateTime object (year, month, day of week, hour, minute).
     * @return end time of the lesson slot, in JodaTime DateTime object (year, month, day of week, hour, minute).
     */
    protected DateTime getEndTime() {

        return this.endTime;
    }

    /**
     * Set start and end time of lesson slot.
     * @param startTime start time of lesson in 24h format (4 digit integer).
     * @param endTime end time of lesson in 24h format (4 digit integer).
     */
    protected void setTime(int startTime, int endTime) {

        this.startTime = new DateTime(2020, 1, this.dayOfWeek.value, startTime/100, startTime%100);
        this.endTime = new DateTime(2020, 1, this.dayOfWeek.value, endTime/100, endTime%100);
        this.time = new Interval(this.startTime, this.endTime);
    }

    /**
     * Returns location of lesson as a string.
     * @return location of lesson as a string.
     */
    protected String getLocation() {

        return this.location;
    }

    /**
     * Sets the location of the lesson.
     * @param location location of the lesson as a string.
     */
    protected void setLocation(String location) {

        this.location = location;
    }

    /**
     * Sets any relevant remarks for the lesson slot.
     * @param remarks additional remarks for the lesson, in a string.
     */
    protected void setRemarks(String remarks) {

        this.remarks = remarks;
    }

    /**
     * Returns the remarks for this lesson slot.
     * @return remarks for this lesson slot as a string.
     */
    protected String getRemarks() {

        return this.remarks;
    }

    /**
     * Returns Lesson ID for current lesson.
     * @return lesson ID.
     */
    protected Integer getLessonID() {

        return this.lessonID;
    }
}
