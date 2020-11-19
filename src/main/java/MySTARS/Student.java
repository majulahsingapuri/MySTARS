package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents one Student
 * @author Jia Hui
 * @version 1.0
 * @since 2020-11-11
 */

public final class Student extends User implements Comparable<Student> {

    /**
     * The unique ID of the class for Serialisation.
     */
    private static final long serialVersionUID = 77L;

    /**
     * The student's unique, 9-character matriculation number.
     */
    private String matricNumber;

    /**
     * The student's first name.
     */
    private String firstName;

    /**
     * The student's last name.
     */
    private String lastName;

    /**
     * A list of courses the student is registered under or on waitlist for.
     */
    private HashMap<String, Course> courses = new HashMap<String, Course>();

    /**
     * The student's {@link Gender}.
     */
    private Gender gender = Gender.PREFER_NOT_TO_SAY;

    /**
     * The student's nationality.
     */
    private String nationality = "";

    /**
     * The student's number of registered Academic Units.
     */
    private int registeredAUs = 0;

    /**
     * The maximum number of AUs that a Student can take.
     */
    public static final int maxAUs = 21;

    /**
     * The constructor with the minimum required number of parameters.
     * @param userName the student's unique username (eg.HKIM007)
     * @param matricNumber the student's unique, 9-character matriculation number
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param gender the student's gender
     * @param nationality the student's nationality
    */
    public Student(String userName, String matricNumber, String firstName, String lastName, Gender gender, String nationality) {

        super(userName, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

    /**
     * The constructor with all possible parameters.
     * @param userName the student's unique username (eg.HKIM007)
     * @param matricNumber the student's unique, 9-character matriculation number
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param password the student's password for accessing the STARS system
     * @param gender the student's gender
     * @param nationality the student's nationality
    */
    public Student(String userName, String matricNumber, String firstName, String lastName, String password, Gender gender, String nationality) {

        super(userName, password, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

    /**
     * Checks if a String can be used as a matriculation number.
     * @param matricNo the matriculation number.
     * @return whether the passed in value is in a valid matriculation number format.
    */
    public static boolean isValidMatricNo(String matricNo) {

        // should be two upper case letters with 7 lower case letter in between
        if(matricNo.matches("\\p{Upper}\\d{7}\\p{Upper}")) {
            return true;
        } else {
            System.out.println("Invalid format. Matric number must be two letters separated by 7 digits.");
            return false;
        }
    }


    /**
     * Checks if a String can be used as a new matriculation number.
     * @param matricNo the matriculation number.
     * @return returns whether the passed in value is in a valid matriculation number format and has not been used yet.
    */
    public static boolean isValidNewMatricNo(String matricNo) {

        if (!Student.isValidMatricNo(matricNo)) {return false;}

        //check that the matric number hasn't been used yet.
        for (User u : Database.USERS.values()) {
            if (u.getAccessLevel() == AccessLevel.STUDENT) {
                Student s = (Student) u;
                if (s.getMatricNumber().equals(matricNo)) {
                    System.out.println("Matric number already taken.");
                    return false;
                }
            }
        }
        //all conditions have been passed
        return true;
    }

    /**
     * Returns the student's matriculation number.
     * @return the student's unique matriculation number
    */
    public String getMatricNumber() {

        return this.matricNumber;
    }

    /**
     * Returns the student's first name.
     * @return the student's first name
    */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the student's last name.
     * @return the student's last name
    */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * Returns the student's gender.
     * @return the student's gender
    */
    public Gender getGender() {

        return this.gender;
    }

    /**
     * Returns the student's nationality.
     * @return the student's nationality
    */
    public String getNationality() {

        return this.nationality;
    }

    /**
     * Returns a {@link Course} object that the Student has registered for.
     * @param courseCode The String value of the course.
     * @return A Course Object.
     */
	public Course getCourse(String courseCode) {

        return courses.get(courseCode);
    }

    /**
     * Returns a list of the student's courses, based on the desired course status.
     * Eg. If courseStatus = CourseStatus.REGISTERED, then only registered courses will be returned.
     * @param courseStatus defines the desried course status used to filter courses
     * @return a list of the student's courses that have the desired course status
    */
    public Course[] getCourses(CourseStatus courseStatus) {

        ArrayList<Course> courseIDs = new ArrayList<Course>();
        for (Course course : courses.values()) {
            if (courseStatus == CourseStatus.NONE || course.getStatus() == courseStatus) {
                courseIDs.add(course);
            }
        }

        return courseIDs.toArray(new Course[courseIDs.size()]);
    }

    /**
     * Directly removes a {@link Course} from the courses Hashmap.
     * @param courseCode The code of the course to be removed
     * @return a Course object that is removed from the Hashmap.
     */
    protected Course removeCourse(String courseCode) {

        return this.courses.remove(courseCode);
    }

    /**
     * Directly adds a {@link Course} to the Hashmap.
     * @param course The course to be added.
     */
    protected void setCourse(Course course) {

        this.courses.put(course.getCourseCode(), course);
    }

    /**
     * Returns a list of the student's course indices, based on the desired course status.
     * Eg. If courseStatus = CourseStatus.REGISTERED, then only registered course indices will be returned.
     * @param courseStatus defines the desried course status used to filter courses.
     * @return a list of the student's course indices that have the desired course status.
    */
    public CourseIndex[] getIndices(CourseStatus courseStatus) {

        ArrayList<CourseIndex> courseInds = new ArrayList<CourseIndex>();
        for (Course course : courses.values()) {
            if (course.getStatus() == courseStatus) {
                for (CourseIndex courseIndex : course.getIndices()) {
                    courseInds.add(courseIndex);
                }
            }
        }
        return courseInds.toArray(new CourseIndex[courseInds.size()]);
    }

    /**
     * Returns the student's current number of registered Academic Units (AUs).
     * @return the student's current number of registered Academic Units (AUs)
    */
    public int getAU() {

        return this.registeredAUs;
    }

    /**
     * Adds the specified number of {@link AU}s to the student's registered AUs.
     * @param acadUnits the number of AUs to be added
     */
    private void addAU(AU acadUnits) {

        this.registeredAUs += acadUnits.value;
        Database.serialise(FileType.USERS);
    }

    /**
     * Removes the specified number of {@link AU}s from the student's registered AUs.
     * @param acadUnits the number of AUs to be removed
     */
    private void removeAU(AU acadUnits) {

        this.registeredAUs -= acadUnits.value;
    
        if (this.registeredAUs < 0){
            this.registeredAUs = 0;
        }
        Database.serialise(FileType.USERS);
    }

    /**
     * Adds {@link Course}s to the Student's plan.
     * @param course The course to be added.
     * @param courseIndex The index to be added.
     * @return {@link CourseStatus#NOT_REGISTERED}
     */
    public CourseStatus planCourse(Course course, CourseIndex courseIndex) {
        this.courses.put(course.getCourseCode(), course.simpleCopy(CourseStatus.NOT_REGISTERED, courseIndex.simpleCopy()));
        return CourseStatus.NOT_REGISTERED;
    }

    /**
     * Adds a course to the {@link Student}'s timetable. If there is no timetable clash, will try to register the course.
     * If the course has no vacancies, the student will be put on the waitlist.
     * @param courseCode the {@link Course} code of the desired course to be added, the student cannot be registered in the course nor on its waitlist.
     * @param courseIndex the couse index of the desired course to be added.
     * @return the course status that the course has been successfully added as.
     * @exception Exception if the courseCode does not exist or there is a timetable clash.
    */
    public CourseStatus addCourse(String courseCode, String courseIndex) throws Exception {
        
        if (!Database.COURSES.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " does not exist!");
        } else if (clashes(courseCode, courseIndex)) {
            throw new Exception("Timetable clash!");
        }
        
        Course course = Database.COURSES.get(courseCode);
        CourseIndex courseInd = course.getIndex(courseIndex);
        
        if (courseInd.getVacancies() > 0) {
            
            if (course.getCourseAU().value + this.registeredAUs > Student.maxAUs){
                throw new Exception("Cannot register more than the maximum " + Student.maxAUs + " AUs!");
            }

            this.courses.put(courseCode, course.simpleCopy(CourseStatus.REGISTERED, courseInd.simpleCopy()));
            courseInd.enrollStudent(this);
            addAU(course.getCourseAU());
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.REGISTERED;
        } else {

            this.courses.put(courseCode, course.simpleCopy(CourseStatus.WAITLIST, courseInd.simpleCopy()));
            courseInd.addToWaitlist(this.simpleCopy());
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.WAITLIST;
        }
    }

    /**
     * Drops a course from the student's timetable.
     * @param courseCode the course code of the desired course to be dropped
     * @exception Exception if the courseCode does not exist has not been added by the student
    */
    public void dropCourse(String courseCode) throws Exception {

        if (!Database.COURSES.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " does not exist!");
        } else if (!this.courses.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " has not been added by Student!");
        }

        Course course = Database.COURSES.get(courseCode);
        CourseIndex courseInd = course.getIndex(this.courses.get(courseCode).getIndices()[0].getCourseIndex());
        CourseStatus courseStatus = this.courses.get(courseCode).getStatus();
        
        switch (courseStatus) {
            case REGISTERED:
                courseInd.unenrollStudent(this);
                removeAU(course.getCourseAU());
                this.courses.remove(courseCode);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
                break;
            case WAITLIST:
                courseInd.removeFromWaitlist(this.getUsername());
                this.courses.remove(courseCode);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
                break;
            case NOT_REGISTERED:
                this.courses.remove(courseCode);
                System.out.println("Course Removed from Plan");
                break;
            case NONE:
                throw new Exception("Invalid course courseStatus!");
        }
    }

    /**
     * Puts the student in a different index of a registered course.
     * @param code the course code of the registered course whose index we want to change
     * @param currentInd the current registered index of the registered course
     * @param newInd the new index of the registered course that the student is to be switched into
     * @exception Exception if the courseCode does not exist or has not been registered by the student
     * @exception Exception if the indices are not part of the course or the student is not registered in currentInd
     * @exception Exception if the new index clashes with the student's timetable or the new index has no vancancies
    */
    public void changeIndex(String code, String currentInd, String newInd) throws Exception {

        if (!Database.COURSES.containsKey(code)) {
            throw new Exception("Course " + code + " does not exist!");
        } else if (!this.courses.containsKey(code)) {
            throw new Exception("Course " + code + " has not been added by Student!");
        } else if (!Database.COURSES.get(code).containsIndex(currentInd)) {
            throw new Exception("Course " + code + " does not contain index " + currentInd + "!");
        } else if (!Database.COURSES.get(code).containsIndex(newInd)) {
            throw new Exception("Course " + code + " does not contain index " + newInd + "!");
        } else if (!this.courses.get(code).getIndices()[0].getCourseIndex().equals(currentInd)) {
            throw new Exception("Student is not in index " + currentInd + "!");
        } else if (this.courses.get(code).getStatus() != CourseStatus.REGISTERED) {
            throw new Exception("Student not registered in course " + code + ", index " + currentInd + "!");
        } else if (clashes(code,newInd)) {
            throw new Exception("New index clashes with current timetable!");
        }

        CourseIndex newCourseInd = Database.COURSES.get(code).getIndex(newInd);
        if (newCourseInd.getVacancies()<=0) {
            throw new Exception("New index " + newInd + " has no vacancies!");
        }

        dropCourse(code);
        addCourse(code, newInd);

        Database.serialise(FileType.USERS);
        Database.serialise(FileType.COURSES);
    }

    /**
     * Checks if a course index of a course clashes with the student's timetable.
     * Only checks with registered courses that are different from the course to be checked.
     * @param courseCode the course code of the course to be checked
     * @param index the couse index to be checked
     * @return true if a clash is found, false if there are no clashes
    */
    public boolean clashes(String courseCode, String index) {

        CourseIndex addCourseIndex = Database.COURSES.get(courseCode).getIndex(index);

        for (Course studentCourse : this.courses.values()) {
            if (!studentCourse.getCourseCode().equals(courseCode) && studentCourse.getStatus() == CourseStatus.REGISTERED) {
                CourseIndex studentCourseIndex = studentCourse.getIndices()[0];
                for (Lesson registeredLesson : studentCourseIndex.getLessons()) {
                    for (Lesson addCourseLesson : addCourseIndex.getLessons()) {
                        if (registeredLesson.getTime().overlaps(addCourseLesson.getTime())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Registers the student from waitlist.
     * @param courseIndex the course index to be registered
     * @return true if registered successfully, false otherwise
     * @exception Exception from the {@link #dropCourse(String)} and {@link #addCourse(String, String)} methods
    */
	public boolean addCourseFromWaitlist(CourseIndex courseIndex) throws Exception {
        String courseCode = courseIndex.getCourseCode();
        Course myCourse = this.courses.get(courseCode);

        if (myCourse == null || myCourse.getStatus() != CourseStatus.WAITLIST) {
            return false;
        }

        CourseIndex myIndex = myCourse.getIndex(courseIndex.getCourseIndex());
        if (myIndex == null) {
            return false;
        }
    
        if (!clashes(courseCode, myIndex.getCourseIndex()) && myCourse.getCourseAU().value + this.registeredAUs <= Student.maxAUs) {
            myCourse.setStatus(CourseStatus.REGISTERED);
            Helper.sendMailNotification(this, courseCode);
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
            return true;
        } else{
            return false;
        }
    }

    public void setIndex(String courseCode, CourseIndex courseIndex) {
        
        courses.get(courseCode).addIndex(courseIndex);
        Database.serialise(FileType.USERS);
    }

    public CourseIndex removeIndex(String courseCode, String courseIndex) {

        CourseIndex removedIndex = courses.get(courseCode).removeIndex(courseIndex);
        Database.serialise(FileType.USERS);
        return removedIndex;
    }
    
    /**
     * Creates a copy of this student object with minimal information.
     * Used in CourseIndex objects to store tjust the necessary student information.
     * @return a stripped down copy of this student object
    */
    public Student simpleCopy() {

        return new Student(this.getUsername(), this.matricNumber, this.firstName, this.lastName, this.gender, this.nationality);
    }

    /**
     * Compares this student with the specified student for order.
     * Returns a negative integer, zero, or a positive integer as this student is less than, equal to, or greater than the specified student.
     * Order is based on first name, then last name, followed lastly by matriculation number.
     * @param o the student to be compared
     * @return a negative integer, zero, or a positive integer as this student is less than, equal to, or greater than the specified student
     */
    public int compareTo(Student o) {
        
        int answer = this.firstName.compareTo(o.firstName);

        if (answer == 0) {
            answer = this.lastName.compareTo(o.lastName);
            if (answer == 0) {
                return this.matricNumber.compareTo(o.matricNumber);
            } else {
                return answer;
            }
        } else {
            return answer;
        }
    }
}
