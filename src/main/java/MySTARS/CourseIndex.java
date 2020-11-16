package MySTARS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;

/**
 * Represents one index of a particular course/module
 * @author Timothy
 * @version 1.0
 * @since 2020-11-11
 */

public final class CourseIndex implements Serializable {
    
    /**
     * The number of vacancies for this index (initially set to the maximum class size)
     */
    private int vacancies = 0;

    /**
     * The course code for associated course (eg. CZ2002)
     */
    private String courseCode;

    /**
     * The index number used to refer to this index by (eg. 100081)
     */
    private String indexNumber;

    /**
     * Hashmap of lessons, to be accessed using {@link Lesson}ID
     */
    private HashMap<Integer, Lesson> lessons = new HashMap<Integer, Lesson>();

    /**
     * The current waitlist for the index.
     * Given as a {@code LinkedList<String>} of matriculation numbers.
     */
    private LinkedList<String> waitlist = new LinkedList<String>();

    /**
     * Hashmap of currently enrolled students.
     * Matriculation numbers used as keys to access the respective Student objects
     */
    private HashMap<String, Student> enrolledStudents = new HashMap<String, Student>();

    /**
     * For java serializable
     */
    private static final long serialVersionUID = 11L;

    /**
     * Constructor for course index, with no lessons at the start.
     * @param vacancies number of vacancies for the index
     * @param courseCode course code of the course
     * @param indexNumber index number of the index
     */
    public CourseIndex(int vacancies, String courseCode, String indexNumber) {

        this.vacancies = vacancies; 
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
    }
    
    /**
     * Constructor for the course index, and including already existing lessons inside.
     * @param vacancies number of vacancies for the index
     * @param courseCode course code of the course
     * @param indexNumber index number of the index
     * @param lessons ArrayList of lessons to include in the index
     */
    public CourseIndex(int vacancies, String courseCode, String indexNumber, ArrayList<Lesson> lessons) {

        this.vacancies = vacancies; 
        this.courseCode = courseCode;
        this.indexNumber = indexNumber;
        addLessons(lessons);
    }


    /**
     * Prompts user to choose a class type for the lesson.
     * Choose from 5 options: Lecture, lab, tutorial, seminar, online. 
     * @return ClassType enum corresponding to user's choice.
     */
    //FIXME delete? already implemented in Lesson class
    public ClassType chooseClassType() {
        
        System.out.println("1. Lecture");
        System.out.println("2. Lab");
        System.out.println("3. Tutorial");
        System.out.println("4. Seminar");
        System.out.println("5. Online");
        System.out.println("Enter the lesson type for this lesson:");

        int classTypeChoice; 

        do{
            classTypeChoice = Helper.sc.nextInt();
            switch (classTypeChoice) {
                case 1: 
                    return ClassType.LECTURE;
                case 2:
                    return ClassType.LAB;
                case 3:
                    return ClassType.TUTORIAL;
                case 4:
                    return ClassType.SEMINAR;
                case 5:
                    return ClassType.ONLINE;
                default:
                    System.out.println("Error, invalid input");
            }
        } while(true);
    }

    /**
     * Set the number of vacancies
     * @param vacancies number of vacancies
     */
    public void setVacancies(int vacancies) {

        this.vacancies = vacancies;
    }

    /**
     * Return the number of remaining vacancies.
     * @return the number of remaining vacancies.
     */
    public int getVacancies() {

        return this.vacancies;
    }

    /**
     * Return the course code as a string (eg. CZ2002).
     * @return course code as a string.
     */
    public String getCourseCode() {

        return this.courseCode;
    }

    /**
     * Return the index number of the course index as a string (eg. 100081).
     * @return index number of the course index.
     */
    public String getCourseIndex() {

        return this.indexNumber;
    }

    /**
     * Add a single lesson slot to the index, if it doesn't already exist.
     * Lesson is added into the HashMap {@link lessons}.
     * @param lesson lesson object to be added.
     */
    public void addLesson(Lesson lesson) {

        this.lessons.putIfAbsent(lesson.getLessonID(), lesson);
    }

    /**
     * Add multiple lessons to the index.
     * All lessons are added into the HashMap {@link lessons}.
     * @param lessons ArrayList containing lesson objects.
     */
    public void addLessons(ArrayList<Lesson> lessons) {

        for (Lesson lesson : lessons) {
            this.lessons.put(lesson.getLessonID(), lesson);
        }
    }

    /**
     * Return all the lesson slots in the index.
     * @return ArrayList of all lesson slots in the index.
     */
    public ArrayList<Lesson> getLessons() {

        Collection<Lesson> values = this.lessons.values();
        return new ArrayList<Lesson>(values);
    }

    public Lesson getLesson(Integer lessonID) {
        
        return this.lessons.get(lessonID);
    }

    /**
     * Return the length of the waitlist for the index.
     * @return the length of the waitlist for the index.
     */
    public int getWaitlistLength() {

        return this.waitlist.size();
    }

    /**
     * Add student's username to the index waitlist.
     * @param username student's username.
     */
    public void addToWaitlist(String username) {

        waitlist.add(username);
        System.out.println(username + " added to waitlist");
    }

    /**
     * Removes a specified student's username from the index waitlist.
     * @param username username of student to be removed.
     */
    public void removeFromWaitlist(String username) {

        //TODO have a better implementation for this. Leave this first for testing purposes but change in final version.
        if(waitlist.remove(username)) {
            System.out.println("Successfully removed student");
        } else {
            System.out.println("Name not found");
        }
    }
    
    /**
     * Returns an array of students currently enrolled in the index.
     * @return array of students currently enrolled in the index.
     */
    public Student[] getStudents() {
        
        return this.enrolledStudents.values().toArray(new Student[enrolledStudents.size()]);
    }

    /**
     * Enrolls a specified student object into the index.
     * Prints error message, and gives user choice to add to waitlist instead, if there are no remaining vacancies in the index.
     * @param student student object to be enrolled in the index.
     */
    public void enrollStudent(Student student) {

        String username = student.getUsername();
        if(this.vacancies != 0) {
            enrolledStudents.put(username, student.simpleCopy());
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

    /**
     * Removes student from the enrolled register.
     * Prints error message to user if student is not found.
     * @param student student object to be unenrolled.
     */
    public void unenrollStudent(Student student) {

        String username = student.getUsername();

        if(enrolledStudents.remove(username) == null) {
            System.out.println("student not found in " + this.getCourseCode() + " course register");
        } else {
            System.out.println(username + " removed from " + this.getCourseCode() + " course register");
            this.vacancies += 1;
            if(!this.waitlist.isEmpty()) {
                enrollNextInWaitlist();
            }
        }
    }

    /**
     * Removes the student next in line in the waitlist, and enrolls object to course register.
     * Error messages are printed to the user if there are no vacancies in the index, or if the waitlist is empty.
     */
    public void enrollNextInWaitlist() {

        if(this.vacancies != 0 && waitlist.size()>0) {
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
        }
    }

    /**
     * Directly adds a {@link Student} to the CourseIndex and bypasses all checks. Used when 2 peers are swapping indices.
     * @param student {@link Student} to be added to the Index.
     */
    protected void addStudent(Student student) {
        enrolledStudents.put(student.getUsername(), student.simpleCopy());
        vacancies -= 1;
    }
    
    /**
     * Directly removes a {@link Student} from the CourseIndex.
     * @param username Username of the {@link Student} to be removed.
     * @return a {@link Student} object that was removed.
     */
    protected Student removeStudent(String username) {
        vacancies += 1;
        return enrolledStudents.remove(username);
	}

    /**
     * Creates a simple copy of this course index, with same parameters except for 0 vacancies.
     * @return CourseIndex object, with same parameters except for 0 vacancies. 
     */
    public CourseIndex simpleCopy() {

        return new CourseIndex(0, this.courseCode, this.indexNumber, this.getLessons());
    }
}
