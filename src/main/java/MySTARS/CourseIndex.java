package MySTARS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public final class CourseIndex implements Serializable {
    
    private int vacancies = 0;
    private String courseCode;
    private String indexNumber;
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    private LinkedList<String> waitlist = new LinkedList<String>();
    private HashMap<String, Student> enrolledStudents = new HashMap<String, Student>();
    private static final long serialVersionUID = 11L;

    protected CourseIndex(int vacancies, String courseCode, String indexNumber, Lesson lesson) {

        this.vacancies = vacancies; 
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
        this.lessons.add(lesson);
    }
    protected CourseIndex(int vacancies, String courseCode, String indexNumber, ArrayList<Lesson> lessons) {

        this.vacancies = vacancies; 
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
        this.lessons = lessons;
    }

    protected void setVacancies(int vacancies) {

        this.vacancies = vacancies;
    }

    protected int getVacancies() {

        return this.vacancies;
    }

    protected String getCourseCode() {

        return this.courseCode;
    }

    protected String getCourseIndex() {

        return this.indexNumber;
    }

    protected void addLesson(Lesson lesson) {

        this.lessons.add(lesson);
    }

    protected void addLessons(ArrayList<Lesson> lessons) {

        this.lessons.addAll(lessons);
    }

    protected ArrayList<Lesson> getLessons() {
        
        return this.lessons;
    }

    protected int getWaitlistLength() {

        return this.waitlist.size();
    }

    protected void addToWaitlist(String username) {

        waitlist.add(username);
        System.out.println(username + " added to waitlist");
    }

    protected void removeFromWaitlist(String username) {

        //TODO have a better implementation for this. Leave this first for testing purposes but change in final version.
        if(waitlist.remove(username)) {
            System.out.println("Successfully removed student");
        } else {
            System.out.println("Name not found");
        }
    }
    
    protected Student[] getStudents() {
        
        return this.enrolledStudents.values().toArray(new Student[enrolledStudents.size()]);
    }

    protected void enrollStudent(Student student) {

        String username = student.getUsername();
        if(this.vacancies != 0) {
            enrolledStudents.put(username, student);
            this.vacancies -= 1;
        } else {
            String answer;
            do {
                System.out.print("Error: no more vacancies! do you want to be added to waitlist? y/n: ");
                answer = Helper.sc.nextLine();
            } while (answer.equals("y") || answer.equals("n"));

            if (answer.equals("y")) {
                addToWaitlist(username);
            }
        }
    }

    protected void unenrollStudent(Student student) {

        String username = student.getUsername();

        if(enrolledStudents.remove(username) == null) {
            System.out.println("student not found in course register");
        } else {
            System.out.println(username + " removed from course register");
            this.vacancies += 1;
            enrollNextInWaitlist();
        }
    }

    // need to add a method to pop the first student in waitlist to the class automatically? 
    protected void enrollNextInWaitlist() {

        if(this.vacancies != 0) {
            String username = waitlist.removeFirst();
            System.out.println("removed first student in waitlist");
            
            Student student = (Student) Database.USERS.get(username);
            if (student != null) {
                try {
                    if (student.addCourseFromWaitlist(this)){
                        enrolledStudents.put(username, student.simpleCopy());
                        this.vacancies -= 1;
                    }
                    else{
                        enrollNextInWaitlist();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                enrollNextInWaitlist();
            }
        } else {
            System.out.println("error, no vacancies");
        }
    }

    protected CourseIndex simpleCopy() {
        return new CourseIndex(0, this.courseCode, this.indexNumber, this.lessons);
    }
}
