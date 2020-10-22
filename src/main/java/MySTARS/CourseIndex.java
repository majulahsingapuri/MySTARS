package MySTARS;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class CourseIndex implements Serializable{
    
    private int vacancies;
    private LinkedList<String> waitlist = new LinkedList<>();
    private String indexNo;
    private HashMap<String, Student> enrolledStudents = new HashMap<>();
    private static final long serialVersionUID = 11L;

    protected CourseIndex(int vacancies, String indexNo) {

        this.vacancies = vacancies; 
        this.indexNo = indexNo;
    }

    protected void setVacancies(int vacancies) {

        this.vacancies = vacancies;
    }

    protected int getVacancies() {

        return this.vacancies;
    }

    protected int getWaitlistLength() {

        return waitlist.size();
    }

    // making waitlist a list of matric numbers instead of names 
    protected void addToWaitlist(String matricNo) {

        waitlist.add(matricNo);
        System.out.println(matricNo + " added to waitlist");
    }

    // method to remove first from waitlist, usually wouldn't need to be used
    protected void removeFromWaitlist() {

        waitlist.removeFirst();
    }

    protected void removeFromWaitlist(String matricNo) {

        if(waitlist.remove(matricNo)) {
            continue;
        } else {
            System.out.println("Name not found");
        }
    }

    protected void enrollStudent(Student student) {

        String matricNo = student.matricNo;
        if(this.vacancies != 0) {
            enrolledStudents.put(matricNo, student);
        } else {
            System.out.print("error, no vacancies ");
            addToWaitlist(matricNo);
        }
    }

    protected void unenrollStudent(Student student) {

        String matricNo = student.matricNo;

        if(enrolledStudents.remove(matricNo) == null) {
            System.out.println("student not found in course register");
        } else {
            System.out.println(matricNo + " removed from course register");
        }
    }

    // need to add a method to pop the first student in waitlist to the class automatically? 
    protected void enrollNextInWaitlist() {

        if(this.vacancies != 0) {
            String matricNo = waitlist.removeFirst();
            System.out.println("removed first student in waitlist");

            //TODO use database users hashmap to look up Student object by matricNo
            
        } else {
            System.out.println("error, no vacancies");
        }
    }
}
