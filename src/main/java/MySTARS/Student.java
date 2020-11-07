package MySTARS;

import java.util.ArrayList;
import java.util.HashMap;
import org.joda.time.Interval;

public final class Student extends User {

    private static final long serialVersionUID = 77L;
    private String matricNumber;
    private String firstName;
    private String lastName;
    private HashMap<String, Course> courses = new HashMap<String, Course>(); //<courseCode, course> where course only contains the student's course index
    private Gender gender = Gender.PREFER_NOT_TO_SAY;
    private String nationality = "";
    private int registeredAUs = 0;

    public Student(String userName, String matricNumber, String firstName, String lastName) {
        super(userName, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String userName, String matricNumber, String firstName, String lastName, String password, Gender gender, String nationality) {
        super(userName, password, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
    }

    public static boolean isValidMatricNo(String matricNo){
        //has to have 9 characters
        if (matricNo.length() != 9){return false;}

        //the first character has to be an uppercase letter
        if (matricNo.charAt(0) < 'A' || matricNo.charAt(0) > 'Z'){return false;}

        //the next 7 characters have to be digits
        for (int i = 1; i < 8; i++){
            if (matricNo.charAt(i) < '0' || matricNo.charAt(i) > '9'){
                return false;
            }
        }

        //the last character has to be an uppercase letter
        if (matricNo.charAt(0) < 'A' || matricNo.charAt(0) > 'Z'){return false;}

        //all conditions have been passed
        return true;
    }

    public static boolean isValidNewMatricNo(String matricNo) {
        if (!isValidMatricNo(matricNo)){return false;}

        //check that the matric number hasn't been used yet.
        for (User u : Database.USERS.values()) {
            if (u.getAccessLevel() == AccessLevel.STUDENT){
                Student s = (Student) u;
                if (s.getMatricNumber().equals(matricNo)){
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

    protected String getFirstName(){
        return this.firstName;
    }

    protected String getLastName(){
        return this.lastName;
    }

    protected Gender getGender(){
        return this.gender;
    }

    protected String getNationality(){
        return this.nationality;
    }

    protected Course[] getCourses(CourseStatus courseStatus) {

        if (courseStatus == CourseStatus.NONE){
            return courses.keySet().toArray(new Course[courses.size()]);
        }
        
        ArrayList<Course> courseIDs = new ArrayList<Course>();
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                courseIDs.add(course);
        }

        return courseIDs.toArray(new Course[courseIDs.size()]);
    }

    protected CourseIndex[] getIndices(CourseStatus courseStatus){

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

    protected int getAU(){
        return this.registeredAUs;
    }

    protected void addAU(AU au){
        this.registeredAUs += au.value;
        Database.serialise(FileType.USERS);
    }

    protected void removeAU(AU au){
        this.registeredAUs -= au.value;
        //ensures logic is not broken
        if (this.registeredAUs < 0){
            this.registeredAUs = 0;
        }
        Database.serialise(FileType.USERS);
    }

    /*
    returns the course's new CourseStatus
    throws exceptions when a timetable clash is found or the course code does not exist
    */
    protected CourseStatus addCourse(String code, String ind) throws Exception{
        //TODO make sure calling class checks that course is not yet registered nor on the waitlist
        Database.deserialise(FileType.COURSES);
        if (clashes(code))
            throw new Exception("Timetable clash!");
        if (!Database.COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        Course course = Database.COURSES.get(code);
        CourseIndex courseInd;
        try{
            courseInd = course.getIndex(ind);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (courseInd.getVacancies()<=0){
            //put on waitlist
            this.courses.put(code, course.simpleCopy(courseInd, CourseStatus.WAITLIST));
            courseInd.addToWaitlist(this.matricNumber);
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.WAITLIST;
        }
        else{
            //add course successfully
            this.courses.put(code, course.simpleCopy(courseInd, CourseStatus.REGISTERED));
            courseInd.enrollStudent(this);
            addAU(course.getCourseAU());
            Database.serialise(FileType.USERS);
            Database.serialise(FileType.COURSES);
            return CourseStatus.REGISTERED;
        }
    }

    protected void dropCourse(String code) throws Exception{
        Database.deserialise(FileType.COURSES);
        if (!Database.COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (!this.courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        Course course = Database.COURSES.get(code);
        CourseIndex courseInd = course.getIndex(this.courses.get(code).getIndices()[0].getCourseIndex());
        CourseStatus courseStatus = this.courses.get(code).getStatus();
        
        if (courseStatus == CourseStatus.REGISTERED){
            courseInd.unenrollStudent(this);
            removeAU(course.getCourseAU());
            this.courses.remove(code);
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
        }
        else if (courseStatus == CourseStatus.WAITLIST){
            courseInd.removeFromWaitlist(this.matricNumber);
            this.courses.remove(code);
            Database.serialise(FileType.COURSES);
            Database.serialise(FileType.USERS);
        }
        else{
            throw new Exception("Invalid course courseStatus!");
        }
    }


    //can only change index of registered courses
    protected void changeIndex(String code, String currentInd, String newInd) throws Exception{
        Database.deserialise(FileType.COURSES);

        if (!Database.COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (this.courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        if (!Database.COURSES.get(code).containsIndex(currentInd))
            throw new Exception("Course " + currentInd + " does not contain index " + currentInd + "!");
        if (!Database.COURSES.get(code).containsIndex(newInd))
            throw new Exception("Course " + newInd + " does not contain index " + newInd + "!");
        if (!this.courses.get(code).getIndices()[0].getCourseIndex().equals(currentInd))
            throw new Exception("Student is not in index " + currentInd + "!");
        if (this.courses.get(code).getStatus() != CourseStatus.REGISTERED)
            throw new Exception("Student not registered in course " + code + ", index " + currentInd + "!");
        if (clashes(code,newInd))
            throw new Exception("New index clashes with current timetable!");
        
        CourseIndex newCourseInd = Database.COURSES.get(code).getIndex(newInd);
        if (newCourseInd.getVacancies()<=0)
            throw new Exception("New index " + newInd + " has no vacancies!");
        dropCourse(code);
        addCourse(code, newInd);

        Database.serialise(FileType.USERS);
        Database.serialise(FileType.COURSES);
    }

    /*
    checks potential clash with REGISTERED courses
    return true if found a clash
    otherwise, return false
    Assumes that the code passed in will correspond to
    a course that has not yet been registered.
    */
    protected boolean clashes(String code){
        ArrayList<Interval> currentIntervals = new ArrayList<Interval>();
        for (Course c : this.courses.values()){
            //only check with registered courses, don't check with same courseCode
            if (c.getStatus()==CourseStatus.REGISTERED){
                if (c.getCourseCode().equals(code)){
                    //FIXME this shouldn't happen! check the logic of calling method, delete this if block after testing is done
                    System.out.println("Debuging: Tried to check is an already registered course clashes with current timetable.");
                    System.out.println(new Throwable().getStackTrace());
                    return true;
                }
                CourseIndex ind = c.getIndex(c.getIndices()[0].getCourseIndex());
                ArrayList<Lesson> lessons = ind.getLessons();
                for (Lesson l : lessons){
                    currentIntervals.add(l.getTime());
                }
            }
        }

        boolean doesClash = false;
        Course c = Database.COURSES.get(code);
        CourseIndex[] indices = c.getIndices();
        

        //loop through all indices
        for (CourseIndex courseIndex : indices){
            doesClash = false;
            ArrayList<Lesson> lessons = courseIndex.getLessons();
            //loop through all lessons in courseIndex
            boolean lessonClashes = false;
            for (Lesson l : lessons){
                Interval interval = l.getTime();
                for (Interval busy : currentIntervals){
                    if (busy.overlaps(interval)){
                        doesClash = true;
                        lessonClashes = true;
                        break;
                    }
                }
                if (lessonClashes){
                    break;
                }
            }

            if (!doesClash){ 
                //there is an index that does not clash!
                return doesClash;
            }
        }
        return doesClash;
    }

    /*
    checks potential clash with REGISTERED courses
    if code is an already registered course, don't check with the registered index of the course
    return true if found a clash
    otherwise, return false
    */
    protected boolean clashes(String code, String index){
        ArrayList<Interval> currentIntervals = new ArrayList<Interval>();
        for (Course c : this.courses.values()){
            //only check with registered courses, don't check with same courseCode
            if (!c.getCourseCode().equals(code) && c.getStatus()==CourseStatus.REGISTERED){
                CourseIndex ind = c.getIndex(c.getIndices()[0].getCourseIndex());
                ArrayList<Lesson> lessons = ind.getLessons();
                for (Lesson l : lessons){
                    currentIntervals.add(l.getTime());
                }
            }
        }
        
        Course c = Database.COURSES.get(code);
        CourseIndex courseIndex = c.getIndex(index);
        

        ArrayList<Lesson> lessons = courseIndex.getLessons();
        boolean doesClash = false;
        boolean lessonClashes = false;
        for (Lesson l : lessons){
            Interval interval = l.getTime();
            for (Interval busy : currentIntervals){
                if (busy.overlaps(interval)){
                    doesClash = true;
                    lessonClashes = true;
                    break;
                }
            }
            if (lessonClashes){
                break;
            }
        }

        return doesClash;
    }

    protected Student simpleCopy(){
        return new Student(this.getUsername(), this.matricNumber, this.firstName, this.lastName);
    }

	public boolean addCourseFromWaitlist(CourseIndex courseIndex) {
        String courseCode = courseIndex.getCourseCode();
        Course myCourse = this.courses.get(courseCode);
        if (myCourse == null || myCourse.getStatus() != CourseStatus.WAITLIST){
            return false;
        }
        CourseIndex myIndex = myCourse.getIndex(courseIndex.getCourseIndex());
        if (myIndex == null){
            return false;
        }
        //all logic requirements met!

        //check if there is a timetable clash (may arise due to new courses being added after this course was put on waitlist)
        if (!clashes(courseCode, myIndex.getCourseIndex())){
            dropCourse(courseCode);
            addCourse(courseCode, myIndex.getCourseIndex());
            return true;
        }
        else{
            return false;
        }
	}
}
