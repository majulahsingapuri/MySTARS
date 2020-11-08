package MySTARS;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public final class CourseIndex implements Serializable {
    
    private int vacancies;
    private LinkedList<String> waitlist = new LinkedList<>();
    private String indexNumber;
    private HashMap<String, Student> enrolledStudents = new HashMap<>();
    private static final long serialVersionUID = 11L;

    protected CourseIndex(int vacancies, String indexNumber) {

        this.vacancies = vacancies; 
        this.indexNumber = indexNumber;
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

    protected String getCourseIndex() {

        return indexNumber;
    }

    protected void addToWaitlist(String matricNumber) {

        waitlist.add(matricNumber);
        System.out.println(matricNumber + " added to waitlist");
    }

    protected void removeFromWaitlist(String matricNumber) {

        //TODO have a better implementation for this. Leave this first for testing purposes but change in final version.
        if(waitlist.remove(matricNumber)) {
            System.out.println("Successfully removed student");
        } else {
            System.out.println("Name not found");
        }
    }

    protected void enrollStudent(Student student) {

        String matricNumber = student.getMatricNumber();
        if(this.vacancies != 0) {
            enrolledStudents.put(matricNumber, student);
        } else {
            System.out.print("error, no vacancies ");
            addToWaitlist(matricNumber);
        }
    }

    protected void unenrollStudent(Student student) {

        String matricNumber = student.getMatricNumber();

        if(enrolledStudents.remove(matricNumber) == null) {
            System.out.println("student not found in course register");
        } else {
            System.out.println(matricNumber + " removed from course register");
        }
    }

    // need to add a method to pop the first student in waitlist to the class automatically? 
    protected void enrollNextInWaitlist() {

        if(this.vacancies != 0) {
            String matricNumber = waitlist.removeFirst();
            System.out.println("removed first student in waitlist");

            //TODO use database users hashmap to look up Student object by matricNumber
        } else {
            System.out.println("error, no vacancies");
        }
    }

    //TODO addStudent
    //TODO removeStudent
}
