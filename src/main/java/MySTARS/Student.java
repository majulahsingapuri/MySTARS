package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;

public final class Student extends User {

    private static final long serialVersionUID = 77L;
    private String matricNumber;
    private String firstName;
    private String lastName;
    private HashMap<String, Course> courses = new HashMap<String, Course>();
    private Gender gender = Gender.PREFER_NOT_TO_SAY;
    private String nationality = "";
    private int registeredAUs = 0;

    protected Student(String userName, String matricNumber, String firstName, String lastName, Gender gender, String nationality) {

        super(userName, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

    protected Student(String userName, String matricNumber, String firstName, String lastName, String password, Gender gender, String nationality) {

        super(userName, password, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

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

    protected String getMatricNumber() {

        return this.matricNumber;
    }

    protected String getFirstName() {
        return this.firstName;
    }

    protected String getLastName() {

        return this.lastName;
    }

    protected Gender getGender() {

        return this.gender;
    }

    protected String getNationality() {

        return this.nationality;
    }

	protected Course getCourse(String courseCode) {

        return courses.get(courseCode);
    }

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

    protected int getAU() {

        return this.registeredAUs;
    }

    protected void addAU(AU acadUnits) {

        this.registeredAUs += acadUnits.value;
        Database.serialise(FileType.USERS);
    }

    protected void removeAU(AU acadUnits) {

        this.registeredAUs -= acadUnits.value;
        //ensures logic is not broken
        if (this.registeredAUs < 0){
            this.registeredAUs = 0;
        }
        Database.serialise(FileType.USERS);
    }

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
            //add course successfully
            this.courses.put(courseCode, course.simpleCopy(CourseStatus.REGISTERED, courseInd.simpleCopy()));
            courseInd.enrollStudent(this);
            addAU(course.getCourseAU());
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.REGISTERED;
        } else {
            //put on waitlist
            this.courses.put(courseCode, course.simpleCopy(CourseStatus.WAITLIST, courseInd.simpleCopy()));
            courseInd.addToWaitlist(this.matricNumber);
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.WAITLIST;
        }
    }

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
            throw new Exception("Invalid course courseStatus!");
        }
    }

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

    protected boolean clashes(String courseCode){
        CourseIndex[] addCourseIndices = Database.COURSES.get(courseCode).getIndices();

        for (Course studentCourse : this.courses.values()) {
            if (!studentCourse.getCourseCode().equals(courseCode) && studentCourse.getStatus() == CourseStatus.REGISTERED) {
                CourseIndex studentCourseIndex = studentCourse.getIndices()[0];
                for (Lesson registeredLesson : studentCourseIndex.getLessons()) {
                    for (CourseIndex addCourseIndex : addCourseIndices) {
                        for (Lesson addCourseLesson : addCourseIndex.getLessons()) {
                            if (registeredLesson.getTime().overlaps(addCourseLesson.getTime())) {
                                return true;
                            }
                        }   
                    }
                }
            }
        }

        return false;
    }

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

	protected void setIndex(String courseCode, CourseIndex courseIndex) {
        
        courses.get(courseCode).addIndex(courseIndex);
    }
    
    protected Student simpleCopy(){
        return new Student(this.getUsername(), this.matricNumber, this.firstName, this.lastName, this.gender, this.nationality);
    }
}
