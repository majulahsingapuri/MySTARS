package MySTARS;

/**
 * Updates {@link Course} object and with new information.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public class UpdateCourseView extends View {

    /**
     * Integer that keeps track of the choice that the User made.
     */
    private int choice;

    /**
     * Prints prompts for the User to key in so that the relevant information to update a {@link Course} is keyed in. Required method from View.
     */
    public void print() {

        do {
            clearScreen("Admin Main > Update Course");

            System.out.println("1: Change course code.");
            System.out.println("2: Change course description.");
            System.out.println("3: Change course vacancies.");
            System.out.println("4: Add indices to Course.");
            System.out.println("5: Change Class location.");
            System.out.println("6: Return to AdminMain");
            
            try{
                choice = Integer.parseInt(Helper.readLine());
            } catch (Exception e) {
                choice = -1;
                Helper.readLine();
            }

            switch (choice) {

                case 1: 
                    changeCourseCode();
                    break;

                case 2:
                    changeCourseDescription();
                    break;

                case 3:
                    changeClassSize();
                    break;

                case 4:
                    addIndices();
                    break;
                
                case 5:
                    changeClassLocation();
                    break;

                case 6:
                    return;
                
                default:
                    System.out.println("Please enter valid option.");
                    Helper.pause();
            }
        } while (true);
    }

    /**
     * Changes the Course code for {@link Course}s in the {@link Database} and {@link Student} objects.
     */
    private void changeCourseCode() {

        clearScreen("Admin Main > Update Course > Change Course Code");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Database.COURSES.containsKey(courseCode)) {

                System.out.print("Enter new Course Code: ");
                String newCourseCode = Helper.readLine();

                System.out.print("The course code will be changed from " + courseCode + " to " + newCourseCode + "\n\nConfirm? y/n:");
                String confirm = Helper.readLine();
                if (confirm.equals("Y")) {

                    Course course = Database.COURSES.remove(courseCode);
                    course.setCourseCode(newCourseCode);
                    Database.COURSES.put(newCourseCode, course);

                    for (CourseIndex courseIndex : course.getIndices()) {
                        for (Student student : courseIndex.getStudents()) {
                            Student databaseStudent = (Student) Database.USERS.get(student.getUsername());
                            Course studentCourse = databaseStudent.removeCourse(courseCode);
                            studentCourse.setCourseCode(newCourseCode);
                            databaseStudent.setCourse(studentCourse);
                        }
                    }

                    Database.serialise(FileType.COURSES);
                    Database.serialise(FileType.USERS);

                    System.out.println("Course Code changed Successfully!");
                    Helper.pause();
                } else {
                    System.out.println("Aborting");
                    Helper.pause();
                }
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }

    /**
     * Changes the Course Description for {@link Course}s in the {@link Database} and {@link Student} objects.
     */
    private void changeCourseDescription() {

        clearScreen("Admin Main > Update Course > Change Course Description");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                System.out.print("Enter new Course Description: ");
                String newDescription = Helper.readLine();

                System.out.print("This Description will be added to Course " + course.getCourseCode() + ":\n" + newDescription + "\n\nConfirm? y/n:");
                String confirm = Helper.readLine();
                if (confirm.equals("Y")) {
                
                    course.setDescription(newDescription);

                    for (CourseIndex courseIndex : course.getIndices()) {
                        for (Student student : courseIndex.getStudents()) {
                            Student databaseStudent = (Student) Database.USERS.get(student.getUsername());
                            databaseStudent.getCourse(courseCode).setDescription(newDescription);
                        }
                    }

                    Database.serialise(FileType.COURSES);
                    Database.serialise(FileType.USERS);

                    System.out.println("Course Description changed Successfully!");
                } else {
                    System.out.println("Aborting");
                    Helper.pause();
                }
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }

    /**
     * Changes the Course vacancies for {@link Course}s in the {@link Database} objects.
     */
    private void changeClassSize() {

        clearScreen("Admin Main > Update Course > Change Course Vacancies");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Helper.printSmallSpace();

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                while (true) {

                    CourseManager.printIndexList(course, true);

                    System.out.print("Enter the Course Index or Q to Quit: ");
                    String index = Helper.readLine();
                    if (index.equals("Q")) {
                        break;
                    }

                    Helper.printSmallSpace();

                    CourseIndex courseIndex = course.getIndex(index);
                    if (courseIndex != null) {
                        
                        try {
                            System.out.print(String.format("%-50s: ", "Enter new Class Size"));
                            int newClassSize = Integer.parseInt(Helper.readLine());

                            if (newClassSize >= courseIndex.getStudents().length) {
                                
                                System.out.println("The Class Size for the Index " + courseIndex.getCourseIndex() + " will be changed from " + courseIndex.getClassSize() + " to " + newClassSize);
                                System.out.println(String.format("%-50s: ", "Confirm? y/n"));
                                String confirm = Helper.readLine();
                                if (confirm.equals("Y")) {
                                
                                    courseIndex.setClassSize(newClassSize);

                                    Database.serialise(FileType.COURSES);
                                    Database.serialise(FileType.USERS);

                                    System.out.println("Class Size changed successfully!");
                                } else {
                                    System.out.println("Aborting");
                                    Helper.pause();
                                }
                            } else {
                                System.out.println("New Vacancies are lesser than Currently Enrolled Students!");
                                Helper.pause();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    } else {
                        System.out.println("Course Index does not yet exist!");
                        Helper.pause();
                    }
                }
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }

    /**
     * Adds {@link CourseIndex}s to already existing {@link Course}s.
     */
    private void addIndices() {

        clearScreen("Admin Main > Update Course > Add indices");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                CourseManager.printIndexList(course, false);

                int numIndices;                
                while (true){
                    try {
                        System.out.print(String.format("%-50s: ", "Enter the number of indices (1 - 10 max) to add"));
                        numIndices = Integer.parseInt(Helper.readLine());
                        if (numIndices < 0 || numIndices > 10 - course.getIndices().length) {
                            throw new Exception();
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number");
                    }
                }

                //TODO: Confirm it works properly after error checking on Course Side.
                course.addIndices(numIndices);
                Database.serialise(FileType.COURSES);
                Database.serialise(FileType.USERS);
                Helper.pause();
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }

    /**
     * Changes the location for a class.
     */
    private void changeClassLocation() {

        clearScreen("Admin Main > Update Course > Change Class Location");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print(String.format("%-50s: ", "Enter the Course Code or Q to Quit"));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                while (true) {
                    
                    CourseManager.printIndexList(course, true);

                    System.out.print(String.format("%-50s: ", "Enter the Index or Q to Quit"));
                    String index = Helper.readLine();
                    if (index.equals("Q")) {
                        break;
                    }

                    CourseIndex courseIndex = course.getIndex(index);
                    if (courseIndex != null) {
                        
                        while (true) {
                            
                            CourseManager.printLesson(courseIndex);

                            System.out.print(String.format("%-50s: ", "Enter the Lesson ID or Q to Quit"));
                            String id = Helper.readLine();
                            if (id.equals("Q")) {
                                break;
                            }

                            int lessonID;
                            try {
                                lessonID = Integer.parseInt(id);

                                Lesson lesson = courseIndex.getLesson(lessonID);
                                if (lesson != null) {

                                    System.out.print(String.format("%-50s: ", "Enter the new lesson location"));
                                        String newLocation = Helper.readLine();

                                        System.out.println("The location for the lesson will change from " + lesson.getLocation() + " to " + newLocation);
                                        System.out.println(String.format("%-50s: ", "Confirm? y/n"));
                                        String confirm = Helper.readLine();
                                        if (confirm.equals("Y")) {
                                        
                                            lesson.setLocation(newLocation);

                                            for (CourseIndex studentCourseIndex : course.getIndices()) {
                                                for (Student student : studentCourseIndex.getStudents()) {
                                                    Student databaseStudent = (Student) Database.USERS.get(student.getUsername());
                                                    databaseStudent.getCourse(courseCode).getIndex(index).getLesson(lessonID).setLocation(newLocation);    
                                                }
                                            }

                                            Database.serialise(FileType.COURSES);
                                            Database.serialise(FileType.USERS);

                                            System.out.println("Lesson location changed Successfully!");
                                        } else {
                                            System.out.println("Aborting");
                                            Helper.pause();
                                        }
                                } else {
                                    System.out.println("Lesson does not yet exist!");
                                    Helper.pause();
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter a valid Lesson ID!");
                                Helper.pause();
                            }
                        }
                    } else {
                        System.out.println("Course Index does not yet exist!");
                        Helper.pause();
                    }
                }
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }
}


                                    
