package MySTARS;

import java.util.HashMap;

public class Student extends User{

    private static final long serialVersionUID = 77L;
    private String matricNo;
    private HashMap<String, Course> courses; //<username, course> where course only contains the student's course index
    private Gender gender = Gender.PREFER_NOT_TO_SAY;
    private String nationality = "";

    public Student(String name, String matricNo){
        super(name, AccessLevel.STUDENT);
        this.matricNo = matricNo;
        courses = new HashMap<String, Course>();
    }

    public Student(String name, String matricNo, String password, Gender gender, String nationality){
        super(name, password, AccessLevel.STUDENT);
        this.gender = gender;
        this.nationality = nationality;
    }

    protected String getMatricNo(){
        return matricNo;
    }

    //FIXME This needs to be tested after the Course class is implemented!!!
    protected String[] getCourses(CourseStatus status){
        if (status == CourseStatus.ALL){
            return courses.keySet().toArray(new String[courses.size()]);
        }

        int count = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == status)
                count++;
        }
        String[] courseIds = new String[count];
        int i = 0
        for (Course course : courses.values()){
            if (course.getStatus() == status)
                courseIds[i++] = course.getCourseCode();
        }

        return courseIds;
    }

    //FIXME This needs to be tested after the Course class is implemented!!!
    protected String[] getIndices(CourseStatus status){
        int count = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == status)
                count++;
        }

        String[] courseInds = new String[courses.size()];
        int i = 0;
        for (Course course : courses.values()){
            if (course.getStatus() == status)
                courseInds[i++] = course.getIndices()[0];
        }
        return courseInds;
    }

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
            courses.put(code, new Course(code, ind, CourseStatus.WAITLIST));
            courseInd.addToWaitlist(this.matricNo);
            return false;
        }
        else{
            //add course successfully
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
        CourseStatus status = this.courses.get(code).getStatus();
        
        if (status == CourseStatus.REGISTERED){
            courseInd.unenrollStudent(this);
            this.courses.remove(code);
        }
        else if (status == CourseStatus.WAITLIST){
            courseInd.removeFromWaitlist(this.matricNo);
            this.courses.remove(code);
        }
        else{
            throw new Exception("Invalid course status!");
        }
    }

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

}
