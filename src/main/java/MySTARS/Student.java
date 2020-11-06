package MySTARS;

import java.util.HashMap;

public final class Student extends User {

    private static final long serialVersionUID = 77L;
    private String matricNumber;
    private String firstName;
    private String lastName;
    //TODO possibly rename this to something that doesn't clash with Database.COURSES?
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

        return matricNumber;
    }

    protected String getFirstName(){
        return firstName;
    }

    protected String getLastName(){
        return lastName;
    }

    //FIXME This needs to be tested after the Course class is implemented!!!
    protected String[] getCourses(CourseStatus courseStatus) {

        if (courseStatus == CourseStatus.NONE){
            return courses.keySet().toArray(new String[courses.size()]);
        }

        int count = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                count++;
        }
        
        String[] courseIDs = new String[count];
        int i = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                courseIDs[i++] = course.getCourseCode();
        }

        return courseIDs;
    }

    //FIXME This needs to be tested after the Course class is implemented!!!
    protected String[] getIndices(CourseStatus courseStatus){
        int count = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                count++;
        }

        String[] courseInds = new String[count];
        int i = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                courseInds[i++] = course.getIndices()[0];
        }
        return courseInds;
    }

    protected int getAU(){
        return this.registeredAUs;
    }

    protected void addAU(int au){
        this.registeredAUs += au;
    }

    protected void removeAU(int au){
        this.registeredAUs -= au;
        //TODO is this needed...? should this method throw an exception instead?
        //ensure logic is not broken
        if (this.registeredAUs < 0){
            this.registeredAUs = 0;
        }
    }

    //TODO is there a better way to do this? return the CourseStatus rather than a boolean to make it more usable?
    /*
    returns the course's new CourseStatus
    throws exceptions when a timetable clash is found or the course code does not exist
    */
    protected CourseStatus addCourse(String code, String ind) throws Exception{
        //TODO make sure calling class checks that course is not yet registered nor on the waitlist
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
            //TODO Need to modify this to pass in CourseIndex rather than just the string of the Index. That is more versatile
            courses.put(code, new Course(code, courseInd, CourseStatus.WAITLIST));
            courseInd.addToWaitlist(this.matricNumber);
            return CourseStatus.WAITLIST;
        }
        else{
            //add course successfully
            //TODO Need to modify this to pass in CourseIndex rather than just the string of the Index. That is more versatile
            Course newCourse = new Course(code, courseInd, CourseStatus.REGISTERED);
            courses.put(code, newCourse);
            courseInd.enrollStudent(this);
            addAU(newCourse.getAU());
            return CourseStatus.REGISTERED;
        }
    }

    protected void dropCourse(String code) throws Exception{
        if (!Database.COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (!courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        Course course = Database.COURSES.get(code);
        CourseIndex courseInd = course.getIndex(this.courses.get(code).getIndices()[0]);
        CourseStatus courseStatus = this.courses.get(code).getStatus();
        
        if (courseStatus == CourseStatus.REGISTERED){
            courseInd.unenrollStudent(this);
            removeAU(course.getAU());
            this.courses.remove(code);
        }
        else if (courseStatus == CourseStatus.WAITLIST){
            courseInd.removeFromWaitlist(this.matricNumber);
            this.courses.remove(code);
        }
        else{
            throw new Exception("Invalid course courseStatus!");
        }
    }


    //TODO can this be changed to if-elses rather than a bunch of ifs?
    //FIXME calling parameters rather than methods. Check for such errors
    //can only change index of registered courses
    protected void changeIndex(String code, String currentInd, String newInd) throws Exception{
        if (!Database.COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (!courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        if (!Database.COURSES.get(code).containsIndex(currentInd))
            throw new Exception("Course " + currentInd + " does not contain index " + currentInd + "!");
        if (!Database.COURSES.get(code).containsIndex(newInd))
            throw new Exception("Course " + newInd + " does not contain index " + newInd + "!");
        if (!courses.get(code).getIndices()[0].equals(currentInd))
            throw new Exception("Student is not in index " + currentInd + "!");
        if (clashes(code,newInd))
            throw new Exception("New index clashes with current timetable!");
        if (this.courses.get(code).getStatus() != CourseStatus.REGISTERED)
            throw new Exception("Student not resgistered in course " + code + ", index " + currentInd + "!");
        
        CourseIndex newCourseInd = courses.get(code).getIndex(newInd);
        if (newCourseInd.getVacancies()<=0)
            throw new Exception("New index " + newInd + " has no vacancies!");
        dropCourse(code);
        addCourse(code, newInd);
    }

    /*
    checks potential clash with REGISTERED courses
    return true if found a clash
    otherwise, return false
    Assumes that the code passed in will correspond to
    a course that has not yet been registered nor waitlisted.
    */
    protected boolean clashes(String code){
        //TODO complete time table clash check
        boolean doesClash = false;
        Course c = Database.COURSES.get(code);
        String[] indices = c.getIndices();
        //loop through all indices
        for (String ind : indices){
            CourseIndex courseIndex = c.getIndex(ind);
            doesClash = false;
            //loop through all lessons in courseIndex
                //TODO check the timings
            if (!doesClash){ //there is an index that does not clash!
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
        //TODO complete time table clash check

        return false;
    }

    protected Student simpleCopy(){
        return new Student(this.getUsername(), this.matricNumber, this.firstName, this.lastName);
    }
}
