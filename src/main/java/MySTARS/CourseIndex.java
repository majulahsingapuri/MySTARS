package MySTARS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;

/**
 * Represents one index of a particular course/module
 * @author Timothy
 * @version 1.0
 * @since 2020-11-11
 */

public final class CourseIndex implements Serializable {
    
    /**
     * The number of vacancies for this index (initially set to the maximum class size).
     */
    private int vacancies = 0;

    /**
     * The total enrolment of the class.
     */
    private int classSize = 0;

    /**
     * The course code for associated course (eg. CZ2002)
     */
    private String courseCode;

    /**
     * The index number used to refer to this index by (eg. 100081)
     */
    private String indexNumber;

    /**
     * Hashmap of lessons, to be accessed using {@link Lesson}ID
     */
    private HashMap<Integer, Lesson> lessons = new HashMap<Integer, Lesson>();

    /**
     * The current waitlist for the index.
     * Given as a {@code LinkedList<String>} of matriculation numbers.
     */
    private LinkedList<String> waitlist = new LinkedList<String>();

    /**
     * Hashmap of currently waitlisted students.
     * Matriculation numbers used as keys to access the respective Student objects
     */
    private HashMap<String, Student> waitlistedStudents = new HashMap<String, Student>();

    /**
     * Hashmap of currently enrolled students.
     * Matriculation numbers used as keys to access the respective Student objects
     */
    private HashMap<String, Student> enrolledStudents = new HashMap<String, Student>();

    /**
     * For java serializable
     */
    private static final long serialVersionUID = 11L;

    /**
     * Constructor for course index, with no lessons at the start.
     * @param classSize the class size for the index
     * @param courseCode course code of the course
     * @param indexNumber index number of the index
     */
    public CourseIndex(int classSize, String courseCode, String indexNumber) {

        this.vacancies = classSize;
        this.classSize = classSize;
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
    }
    
    /**
     * Constructor for the course index, and including already existing lessons inside.
     * @param classSize the class size for the index
     * @param courseCode course code of the course
     * @param indexNumber index number of the index
     * @param lessons ArrayList of lessons to include in the index
     */
    public CourseIndex(int classSize, String courseCode, String indexNumber, ArrayList<Lesson> lessons) {

        this.vacancies = classSize; 
        this.classSize = classSize;
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
        addLessons(lessons);
    }

    /**
     * Set the classSize
     * @param classSize total enrolment of the class.
     * @throws Exception if class size is smaller than already registered students.
     */
    public void setClassSize(int classSize) throws Exception {

        if (classSize < this.enrolledStudents.size()) {
            throw new Exception("The new Class Size is too small.");
        }
        this.classSize = classSize;
        this.vacancies = classSize - this.enrolledStudents.size();
    }

    /**
     * Returns the class size for the index.
     * @return class size of the index.
     */
    public int getClassSize() {

        return this.classSize;
    }

    /**
     * Return the number of remaining vacancies.
     * @return the number of remaining vacancies.
     */
    public int getVacancies() {

        return this.vacancies;
    }

    /**
     * Return the course code as a string (eg. CZ2002).
     * @return course code as a string.
     */
    public String getCourseCode() {

        return this.courseCode;
    }

    /**
     * Return the index number of the course index as a string (eg. 100081).
     * @return index number of the course index.
     */
    public String getCourseIndex() {

        return this.indexNumber;
    }

    /**
     * Add a single lesson slot to the index, if it doesn't already exist.
     * Lesson is added into the HashMap {@link lessons}.
     * @param lesson lesson object to be added.
     */
    public void addLesson(Lesson lesson) {

        this.lessons.putIfAbsent(lesson.getLessonID(), lesson);
    }

    /**
     * Add multiple lessons to the index.
     * All lessons are added into the HashMap {@link lessons}.
     * @param lessons ArrayList containing lesson objects.
     */
    public void addLessons(ArrayList<Lesson> lessons) {

        for (Lesson lesson : lessons) {
            this.lessons.put(lesson.getLessonID(), lesson);
        }
    }

    /**
     * Return all the lesson slots in the index.
     * @return ArrayList of all lesson slots in the index.
     */
    public ArrayList<Lesson> getLessons() {

        Collection<Lesson> values = this.lessons.values();
        return new ArrayList<Lesson>(values);
    }

    /**
     * Returns the {@link Lesson} object based on the key value lessonID
     * @param lessonID the Integer identifier for a particular lesson.
     * @return corresponding Lesson object.
     */
    public Lesson getLesson(Integer lessonID) {
        
        return this.lessons.get(lessonID);
    }

    /**
     * Return the length of the waitlist for the index.
     * @return the length of the waitlist for the index.
     */
    public int getWaitlistLength() {

        return this.waitlist.size();
    }

    /**
     * Return an array of {@link Student}s that are on Waitlist.
     * @return A Student array.
     */
    public ArrayList<Student> getWaitlistStudents() {

        Collection<Student> values = this.waitlistedStudents.values();
        return new ArrayList<Student>(values);
    }

    /**
     * Add student to the index waitlist.
     * @param student The Student to be added.
     */
    public void addToWaitlist(Student student) {

        waitlist.add(student.getUsername());
        waitlistedStudents.put(student.getUsername(), student);
        System.out.println(student.getUsername() + " added to waitlist");
    }

    /**
     * Removes a specified student's username from the index waitlist.
     * @param username username of student to be removed.
     */
    public void removeFromWaitlist(String username) {

        if(waitlist.remove(username)) {
            waitlistedStudents.remove(username);
            System.out.println("Successfully removed student");
        } else {
            System.out.println("Name not found");
        }
    }
    
    /**
     * Returns an array of students currently enrolled in the index.
     * @return array of students currently enrolled in the index.
     */
    public ArrayList<Student> getStudents() {
        
        Collection<Student> values = this.enrolledStudents.values();
        return new ArrayList<Student>(values);
    }

    /**
     * Enrolls a specified student object into the index.
     * Prints error message, and gives user choice to add to waitlist instead, if there are no remaining vacancies in the index.
     * @param student student object to be enrolled in the index.
     */
    public void enrollStudent(Student student) {

        String username = student.getUsername();
        if(this.vacancies != 0) {
            enrolledStudents.put(username, student.simpleCopy());
            this.vacancies -= 1;
        } else {
            String answer;
            System.out.print("Error: no more vacancies! do you want to be added to waitlist? y/n: ");
            answer = Helper.readLine();
            if (answer.equals("Y")) {
                addToWaitlist(student);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
            }
        }
    }

    /**
     * Removes student from the enrolled register.
     * Prints error message to user if student is not found.
     * @param student student object to be unenrolled.
     */
    public void unenrollStudent(Student student) {

        String username = student.getUsername();

        if(enrolledStudents.remove(username) == null) {
            System.out.println("student not found in " + this.getCourseCode() + " course register");
        } else {
            System.out.println(username + " removed from " + this.getCourseCode() + " course register");
            this.vacancies += 1;
            if(!this.waitlist.isEmpty()) {
                enrollNextInWaitlist();
            }
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
        }
    }

    /**
     * Removes the student next in line in the waitlist, and enrolls object to course register.
     * Error messages are printed to the user if there are no vacancies in the index, or if the waitlist is empty.
     */
    public void enrollNextInWaitlist() {

        if(this.vacancies > 0 && waitlist.size() > 0) {
            String username = waitlist.removeFirst();
            Student student = waitlistedStudents.remove(username);
            System.out.println("removed first student in waitlist");
            
            if (student != null) {
                try {
                    if (student.addCourseFromWaitlist(this)){
                        enrolledStudents.put(student.getUsername(), student);
                        this.vacancies -= 1;
                        Database.serialise(FileType.COURSES);
                        Database.serialise(FileType.USERS);
                    } else {
                        enrollNextInWaitlist();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                enrollNextInWaitlist();
            }
        }
    }

    /**
     * Directly adds a {@link Student} to the CourseIndex and bypasses all checks.
     * @param student {@link Student} to be added to the Index.
     */
    protected void addStudent(Student student) {
        enrolledStudents.put(student.getUsername(), student.simpleCopy());
        vacancies -= 1;
    }
    
    /**
     * Directly removes a {@link Student} from the CourseIndex.
     * @param username Username of the {@link Student} to be removed.
     * @return a {@link Student} object that was removed.
     */
    protected Student removeStudent(String username) {
        vacancies += 1;
        return enrolledStudents.remove(username);
	}

    /**
     * Creates a simple copy of this course index, with same parameters except for 0 vacancies.
     * @return CourseIndex object, with same parameters except for 0 vacancies. 
     */
    public CourseIndex simpleCopy() {

        return new CourseIndex(0, this.courseCode, this.indexNumber, this.getLessons());
    }
}
