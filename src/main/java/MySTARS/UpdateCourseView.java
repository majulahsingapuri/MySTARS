package MySTARS;

/**
 * Updates {@link Course} object and with new information.
 */
public class UpdateCourseView extends View {

    /**
     * Integer that keeps track of the choice that the User made.
     */
    private int choice;

    /**
     * Prints prompts for the User to key in so that the relevant information to update a {@link Course} is keyed in. Required method from View.
     */
    protected void print() {

        clearScreen("Admin Main > Update Course");

        do {
            System.out.println("1: Change course code.");
            System.out.println("2: Change course description.");
            System.out.println("3: Change course vacancies.");
            System.out.println("4: Add indices to Course.");
            System.out.println("5: Change Class location.");
            System.out.println("6: Return to AdminMain");
            
            choice = Helper.sc.nextInt();
            Helper.sc.nextLine();

            switch (choice) {

                case 1: 
                    changeCourseCode();
                    break;

                case 2:
                    changeCourseDescription();
                    break;

                case 3:
                    changeCourseVacancies();
                    break;

                case 4:
                    addIndices();
                    break;
                
                case 5:
                    changeClassLocation();
                    break;

                case 6:
                    return;
            }
        } while (true);
    }

    /**
     * Changes the Course code for {@link Course}s in the {@link Database} and {@link Student} objects.
     */
    protected void changeCourseCode() {

        clearScreen("Admin Main > Update Course > Change Course Code");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            if (Database.COURSES.containsKey(courseCode)) {

                System.out.print("Enter new Course Code: ");
                String newCourseCode = Helper.sc.nextLine();

                System.out.print("The course code will be changed from " + courseCode + " to " + newCourseCode + "\n\nConfirm? y/n:");
                String confirm = Helper.sc.nextLine();
                if (confirm.equals("y")) {

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

                    System.out.println("Course Code changed Successfully!");
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
    protected void changeCourseDescription() {

        clearScreen("Admin Main > Update Course > Change Course Description");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                System.out.print("Enter new Course Description: ");
                String newDescription = Helper.sc.nextLine();

                System.out.print("This Description will be added to Course " + course.getCourseCode() + ":\n" + newDescription + "\n\nConfirm? y/n:");
                String confirm = Helper.sc.nextLine();
                if (confirm.equals("y")) {
                
                    course.setDescription(newDescription);

                    for (CourseIndex courseIndex : course.getIndices()) {
                        for (Student student : courseIndex.getStudents()) {
                            Student databaseStudent = (Student) Database.USERS.get(student.getUsername());
                            databaseStudent.getCourse(courseCode).setDescription(newDescription);
                        }
                    }

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
    protected void changeCourseVacancies() {

        clearScreen("Admin Main > Update Course > Change Course Vacancies");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                while (true) {

                    CourseManager.printIndexList(course, true);

                    System.out.print("Enter the Course Index or Q to Quit: ");
                    String index = Helper.sc.nextLine();
                    if (index.equals("Q")) {
                        break;
                    }

                    CourseIndex courseIndex = course.getIndex(index);
                    if (courseIndex != null) {
                        
                        System.out.print("Enter new number of Vacancies: ");
                        int newVacancies = Helper.sc.nextInt();
                        Helper.sc.nextLine();

                        if (newVacancies > courseIndex.getStudents().length) {
                            
                            System.out.print("The Vacancies for the Index " + courseIndex.getCourseIndex() + " will be changed from " + courseIndex.getVacancies() + " to " + newVacancies + "\n\nConfirm? y/n:");
                            String confirm = Helper.sc.nextLine();
                            if (confirm.equals("y")) {
                            
                                courseIndex.setVacancies(newVacancies);
                                System.out.println("Course Description changed Successfully!");
                            } else {
                                System.out.println("Aborting");
                                Helper.pause();
                            }
                        } else {
                            System.out.println("New Vacancies are lesser than Currently Enrolled Students!");
                            Helper.pause();
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
    protected void addIndices() {

        clearScreen("Admin Main > Update Course > Add indices");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                CourseManager.printIndexList(course, false);

                System.out.print("Enter the number of indices to add: ");
                int numIndices = Helper.sc.nextInt();
                Helper.sc.nextLine();

                //TODO: Confirm it works properly after error checking on Course Side.
                course.addIndices(numIndices);
            } else {
                System.out.println("Course does not yet exist!");
                Helper.pause();
            }
        }
    }

    //TODO: check if this works properly.
    // - What if there are multiple of the same lesson type? do we need a lesson ID number?
    /**
     * Changes the location for a class.
     */
    protected void changeClassLocation() {

        clearScreen("Admin Main > Update Course > Change Class Location");

        while (true) {

            CourseManager.printCourseList(CourseStatus.NONE);

            System.out.print("Enter the Course Code or Q to Quit: ");
            String courseCode = Helper.sc.nextLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {
                
                while (true) {
                    
                    CourseManager.printIndexList(course, true);

                    System.out.print("Enter the Index whose location you want to change or Q to Quit: ");
                    String index = Helper.sc.nextLine();
                    if (index.equals("Q")) {
                        break;
                    }

                    CourseIndex courseIndex = course.getIndex(index);
                    if (courseIndex != null) {
                        
                        while (true) {
                            
                            CourseManager.printLesson(courseIndex);

                            System.out.print("Enter the Lesson ID whose location you want to change or Q to Quit: ");
                            String id = Helper.sc.nextLine();
                            if (id.equals("Q")) {
                                break;
                            }

                            Integer lessonID;
                            try {
                                lessonID = Integer.parseInt(id);
                            } catch (Exception e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                            
                            Lesson lesson = courseIndex.getLesson(lessonID);
                            if (lesson != null) {

                                System.out.print("Enter the new lesson location: ");
                                    String newLocation = Helper.sc.nextLine();

                                    System.out.print("The location for the lesson will change from " + lesson.getLocation() + " to " + newLocation + "\n\nConfirm? y/n:");
                                    String confirm = Helper.sc.nextLine();
                                    if (confirm.equals("y")) {
                                    
                                        lesson.setLocation(newLocation);

                                        for (CourseIndex studentCourseIndex : course.getIndices()) {
                                            for (Student student : studentCourseIndex.getStudents()) {
                                                Student databaseStudent = (Student) Database.USERS.get(student.getUsername());
                                                databaseStudent.getCourse(courseCode).getIndex(index).getLesson(lessonID).setLocation(newLocation);    
                                            }
                                        }

                                        System.out.println("Lesson location changed Successfully!");
                                    } else {
                                        System.out.println("Aborting");
                                        Helper.pause();
                                    }
                            } else {
                                System.out.println("Lesson does not yet exist!");
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


                                    