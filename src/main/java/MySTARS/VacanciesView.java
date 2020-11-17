package MySTARS;

    /**
     * Gives the {@link User} the ability to check vacancies in a {@link Course}.
     */
    
public class VacanciesView extends View {
    
    
    public void print() {

        if (Database.CURRENT_ACCESS_LEVEL == AccessLevel.ADMIN) {
            clearScreen("Admin Main > Check Course Vacanices");
        } else if (Database.CURRENT_ACCESS_LEVEL == AccessLevel.STUDENT) {
            clearScreen("Student Main > Check Course Vacancies");
        }

        while (true) {
            CourseManager.printCourseList(CourseStatus.NONE);
            System.out.print(String.format("%-50s: ", "Enter the course code or Q to quit"));
            String courseCode = Helper.readLine();
            if (courseCode.equals("Q")) {
                break;
            }

            Course course = Database.COURSES.get(courseCode);
            if (course != null) {

                Helper.printSmallSpace();

                CourseManager.printIndexList(course, true);
                System.out.print(String.format("%-50s: ", "Enter the course index or Q to quit"));
                String courseIndex = Helper.readLine();
                if (courseIndex.equals("Q")) {
                    break;
                }

                CourseIndex index = course.getIndex(courseIndex);
                if (index != null) {
                    Helper.printSmallSpace();
                    CourseManager.printLesson(index);
                    
                    if (Database.CURRENT_ACCESS_LEVEL == AccessLevel.STUDENT) {
                        Student currentUser = (Student) Database.CURRENT_USER;
                        Course studentCourse = currentUser.getCourse(courseCode);
                        if (studentCourse == null) {
                            System.out.print(String.format("%-50s: ", "Add these lesson timings to your timetable? y/n"));
                            String answer = Helper.readLine();

                            if (answer.equals("Y")) {
                                try {
                                    currentUser.addCourse(courseCode,courseIndex);
                                    Database.serialise(FileType.USERS);
                                    Database.serialise(FileType.COURSES);
                                    System.out.println(courseCode + " has been added successfully.");
                                    Helper.pause();
                                    break;
                                } catch (Exception e) {
                                    System.out.println(e.getLocalizedMessage());
                                }
                            }
                        }
                    }
                    Helper.pause();
                    Helper.printMediumSpace();
                } else {
                    System.out.println("The course index that you have entered is invalid!");
                    Helper.pause();
                }
            } else {
                System.out.println("The course code you entered is invalid!");
                Helper.pause();
            }
        }
    }
}
