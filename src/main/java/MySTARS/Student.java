package MySTARS;

import java.util.HashMap;

public final class Student extends User {

    private static final long serialVersionUID = 77L;
    private String matricNumber;
    //TODO possibly rename this to something that doesn't clash with Database.COURSES?
    private HashMap<String, Course> courses = new HashMap<String, Course>(); //<username, course> where course only contains the student's course index
    private Gender gender = Gender.PREFER_NOT_TO_SAY;
    private String nationality = "";

    public Student(String name, String matricNumber) {

        super(name, AccessLevel.STUDENT);
        this.matricNumber = matricNumber;
    }

    public Student(String name, String matricNumber, String password, Gender gender, String nationality) {

        super(name, password, AccessLevel.STUDENT);
        this.gender = gender;
        this.nationality = nationality;
    }

    protected String getMatricNumber() {

        return matricNumber;
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

        String[] courseInds = new String[courses.size()];
        int i = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == courseStatus)
                courseInds[i++] = course.getIndices()[0];
        }
        return courseInds;
    }

    //TODO is there a better way to do this? return the CourseStatus rather than a boolean to make it more useable?
    /*
    returning true means the course has been added successfully
    returning false means the student has been added to the waitlist
    */
    protected boolean addCourse(String code, String ind) throws Exception{
        if (clashes(code))
            throw new Exception("Timetable clash!");
        if (!COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        Course course = COURSES.get(code);
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
            courses.put(code, new Course(code, ind, CourseStatus.WAITLIST));
            courseInd.addToWaitlist(this.matricNumber);
            return false;
        }
        else{
            //add course successfully
            //TODO Need to modify this to pass in CourseIndex rather than just the string of the Index. That is more versatile
            courses.put(code, new Course(code, ind, CourseStatus.REGISTERED));
            courseInd.enrollStudent(this);
            return true;
        }
    }

    protected void dropCourse(String code) throws Exception{
        if (!COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (!courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        Course course = COURSES.get(code);
        CourseIndex courseInd = course.getIndex(this.courses.get(code).getIndices()[0]);
        CourseStatus courseStatus = this.courses.get(code).getStatus();
        
        if (courseStatus == CourseStatus.REGISTERED){
            courseInd.unenrollStudent(this);
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
        if (!COURSES.containsKey(code))
            throw new Exception("Course " + code + " does not exist!");
        if (!courses.containsKey(code))
            throw new Exception("Course " + code + " has not been added by Student!");
        if (!COURSES.get(code).containsIndex(currentInd))
            throw new Exception("Course " + currentInd + " does not contain index " + currentInd + "!");
        if (!COURSES.get(code).containsIndex(newInd))
            throw new Exception("Course " + newInd + " does not contain index " + newInd + "!");
        if (courses.get(code).getIndices[0] != currentInd)
            throw new Exception("Student is not in index " + currentInd + "!");
        if (clashes(code,newInd))
            throw new Exception("New index clashes with current timetable!");
        if (this.courses.get(code).getStatus() != CourseStatus.REGISTERED)
            throw new Exception("Student not resgistered in course " + code + ", index " + currentInd + "!");
        
        CourseIndex newCourseInd = course.getIndex(newInd);
        if (newCourseInd.getVacancies<=0)
            throw new Exception("New index " + newInd + " has no vacancies!");
        dropCourse(code);
        addCourse(code, newInd);
    }

    /*
    checks potential clash with REGISTERED courses
    return true if found a clash
    otherwise, return false
    */
    protected boolean clashes(String code){
        //TODO complete time table clash check
    }

    /*
    checks potential clash with REGISTERED courses
    if code is an already registered course, don't check with the registered index of the course
    return true if found a clash
    otherwise, return false
    */
    protected boolean clashes(String code, String index){
        //TODO complete time table clash check
    }

    //TODO add method that returns Student but the stripped down version to be stored in Courses.

}
