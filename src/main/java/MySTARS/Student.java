package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents one Student
 * @author DSAI2 Group 1
 * @version 1.0
 * @since 2020-11-11
 */

public final class Student extends User {

    private static final long serialVersionUID = 77L;
    private String matricNumber;
    private String firstName;
    private String lastName;
    private HashMap<String, Course> courses = new HashMap<String, Course>();
    private Gender gender = Gender.PREFER_NOT_TO_SAY;
    private String nationality = "";
    private int registeredAUs = 0;

    /**
     * The constructor with the minimum required number of parameters.
     * @param userName the student's unique username (eg.HKIM007)
     * @param matricNumber the student's unique, 9-character matriculation number
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param gender the student's gender
     * @param nationality the student's nationality
    */
    protected Student(String userName, String matricNumber, String firstName, String lastName, Gender gender, String nationality) {

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
    protected Student(String userName, String matricNumber, String firstName, String lastName, String password, Gender gender, String nationality) {

        super(userName, password, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

    /**
     * Checks if a String can be used as a matriculation number.
     * @param matricNo the string you are testing
     * @return returns whether the passed in value is in a valid matriculation number format
    */
    protected static boolean isValidMatricNo(String matricNo) {

        //has to have 9 characters
        if (matricNo.length() != 9) {return false;}

        //the first character has to be an uppercase letter
        if (matricNo.charAt(0) < 'A' || matricNo.charAt(0) > 'Z') {return false;}

        //the next 7 characters have to be digits
        for (int i = 1; i < 8; i++){
            if (matricNo.charAt(i) < '0' || matricNo.charAt(i) > '9'){
                return false;
            }
        }

        //the last character has to be an uppercase letter
        if (matricNo.charAt(0) < 'A' || matricNo.charAt(0) > 'Z') {return false;}

        //all conditions have been passed
        return true;
    }

    /**
     * Checks if a String can be used as a new matriculation number.
     * @param matricNo the string you are testing
     * @return returns whether the passed in value is in a valid matriculation number format and has not been used yet
    */
    protected static boolean isValidNewMatricNo(String matricNo) {

        if (!isValidMatricNo(matricNo)) {return false;}

        //check that the matric number hasn't been used yet.
        for (User u : Database.USERS.values()) {
            if (u.getAccessLevel() == AccessLevel.STUDENT) {
                Student s = (Student) u;
                if (s.getMatricNumber().equals(matricNo)) {
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
    protected String getMatricNumber() {

        return this.matricNumber;
    }

    /**
     * Returns the student's first name.
     * @return the student's first name
    */
    protected String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the student's last name.
     * @return the student's last name
    */
    protected String getLastName() {

        return this.lastName;
    }

    /**
     * Returns the student's gender.
     * @return the student's gender
    */
    protected Gender getGender() {

        return this.gender;
    }

    /**
     * Returns the student's nationality.
     * @return the student's nationality
    */
    protected String getNationality() {

        return this.nationality;
    }

    /**
     * Returns the student's first name.
     * @return the student's first name
    */
	protected Course getCourse(String courseCode) {

        return courses.get(courseCode);
    }

    /**
     * Returns a list of the student's courses, based on the desired course status.
     * Eg. If courseStatus = CourseStatus.REGISTERED, then only registered courses will be returned.
     * @param courseStatus defines the desried course status used to filter courses
     * @return a list of the student's courses that have the desired course status
    */
    protected Course[] getCourses(CourseStatus courseStatus) {

        if (courseStatus == CourseStatus.NONE) {
            return courses.keySet().toArray(new Course[courses.size()]);
        }
        
        ArrayList<Course> courseIDs = new ArrayList<Course>();
        for (Course course : courses.values()) {
            if (course.getStatus() == courseStatus) {
                courseIDs.add(course);
            }
        }

        return courseIDs.toArray(new Course[courseIDs.size()]);
    }

    /**
     * Returns a list of the student's course indices, based on the desired course status.
     * Eg. If courseStatus = CourseStatus.REGISTERED, then only registered course indices will be returned.
     * @param courseStatus defines the desried course status used to filter courses
     * @return a list of the student's course indices that have the desired course status
    */
    protected CourseIndex[] getIndices(CourseStatus courseStatus) {

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
    protected int getAU() {

        return this.registeredAUs;
    }

    private void addAU(AU acadUnits) {

        this.registeredAUs += acadUnits.value;
        Database.serialise(FileType.USERS);
    }

    private void removeAU(AU acadUnits) {

        this.registeredAUs -= acadUnits.value;
    
        if (this.registeredAUs < 0){
            this.registeredAUs = 0;
        }
        Database.serialise(FileType.USERS);
    }

    /**
     * Adds a course to the student's timetable. If there is no timetbale clash, will try to register the course.
     * If the course has no vacancies, the student will be put on the waitlist.
     * @param courseCode the course code of the desired course to be added, the student cannot be registered in the course nor on its waitlist
     * @param courseIndex the couse index of the desired course to be added
     * @return the course status that the course has been successfully added as
     * @exception Exception if the courseCode does not exist or there is a timetable clash
    */
    protected CourseStatus addCourse(String courseCode, String courseIndex) throws Exception {
        
        //TODO make sure calling class checks that course is not yet registered nor on the waitlist
        Database.deserialise(FileType.COURSES);
        if (!Database.COURSES.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " does not exist!");
        } else if (clashes(courseCode, courseIndex)) {
            throw new Exception("Timetable clash!");
        }
        
        Course course = Database.COURSES.get(courseCode);
        CourseIndex courseInd = course.getIndex(courseIndex);
        
        if (courseInd.getVacancies() > 0) {
            
            this.courses.put(courseCode, course.simpleCopy(CourseStatus.REGISTERED, courseInd.simpleCopy()));
            courseInd.enrollStudent(this);
            addAU(course.getCourseAU());
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.REGISTERED;
        } else {

            this.courses.put(courseCode, course.simpleCopy(CourseStatus.WAITLIST, courseInd.simpleCopy()));
            courseInd.addToWaitlist(this.matricNumber);
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
    protected void dropCourse(String courseCode) throws Exception {

        Database.deserialise(FileType.COURSES);

        if (!Database.COURSES.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " does not exist!");
        } else if (!this.courses.containsKey(courseCode)) {
            throw new Exception("Course " + courseCode + " has not been added by Student!");
        }

        Course course = Database.COURSES.get(courseCode);
        CourseIndex courseInd = course.getIndex(this.courses.get(courseCode).getIndices()[0].getCourseIndex());
        CourseStatus courseStatus = this.courses.get(courseCode).getStatus();
        
        if (courseStatus == CourseStatus.REGISTERED) {
            courseInd.unenrollStudent(this);
            removeAU(course.getCourseAU());
            this.courses.remove(courseCode);
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
        } else if (courseStatus == CourseStatus.WAITLIST) {
            courseInd.removeFromWaitlist(this.matricNumber);
            this.courses.remove(courseCode);
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
        } else {
            //ensure logic is not broken
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
    protected void changeIndex(String code, String currentInd, String newInd) throws Exception {

        Database.deserialise(FileType.COURSES);

        if (!Database.COURSES.containsKey(code)) {
            throw new Exception("Course " + code + " does not exist!");
        } else if (this.courses.containsKey(code)) {
            throw new Exception("Course " + code + " has not been added by Student!");
        } else if (!Database.COURSES.get(code).containsIndex(currentInd)) {
            throw new Exception("Course " + currentInd + " does not contain index " + currentInd + "!");
        } else if (!Database.COURSES.get(code).containsIndex(newInd)) {
            throw new Exception("Course " + newInd + " does not contain index " + newInd + "!");
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
    protected boolean clashes(String courseCode, String index) {

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
	protected boolean addCourseFromWaitlist(CourseIndex courseIndex) throws Exception {
        String courseCode = courseIndex.getCourseCode();
        Course myCourse = this.courses.get(courseCode);

        if (myCourse == null || myCourse.getStatus() != CourseStatus.WAITLIST) {
            return false;
        }

        CourseIndex myIndex = myCourse.getIndex(courseIndex.getCourseIndex());
        if (myIndex == null) {
            return false;
        }
    
        if (!clashes(courseCode, myIndex.getCourseIndex())) {
            dropCourse(courseCode);
            addCourse(courseCode, myIndex.getCourseIndex());
            return true;
        } else{
            return false;
        }
    }

    /**
     * Used to swap indices with another student.
     * Different from changeIndex(...) as vacacies should not be checked.
     * Timetable clashes will have already been checked before this method is called.
     * @param courseCode the course code of the course to be updated
     * @param newIndex the course index to be swapped to
    */
	protected void swapIndex(String courseCode, CourseIndex newIndex) {
        //TODO ensure called class checkd there are no errors (courseCode and index is registered by student and newIndex is valid)
        Course course = Database.COURSES.get(courseCode);

        CourseIndex currentIndex = course.getIndex(this.courses.get(courseCode).getIndicesString()[0]);
        currentIndex.removeStudent(this.getUsername());
        
        newIndex.addStudent(this);
        this.courses.put(courseCode, course.simpleCopy(CourseStatus.REGISTERED, newIndex));

        Database.serialise(FileType.USERS);
        Database.serialise(FileType.COURSES);
    }
    
    /**
     * Creates a copy of this student object with minimal information.
     * Used in CourseIndex objects to store tjust the necessary student information.
     * @return a stripped down copy of this student object
    */
    protected Student simpleCopy() {

        return new Student(this.getUsername(), this.matricNumber, this.firstName, this.lastName, this.gender, this.nationality);
    }
}
