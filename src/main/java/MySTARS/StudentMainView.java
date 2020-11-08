public class StudentMainView extends View {
    private int choice;
    public StudentMainView() {}

    protected void print() {
        // TODO Auto-generated method stub
        clearScreen("StudentMain");
        do{
            System.out.println("What would you like to do? Choose one of the options below: ");
            System.out.println("1. Add a new course");
            System.out.println("2. Drop a course");
            System.out.println("3. Change index of one of your current courses");
            System.out.println("4. Swap index of one of your current courses with a peer");
            System.out.println("5. Change your password");
            System.out.println("6. Show timetable");
            System.out.println("7. Logout");
            System.out.print("Enter the number of your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                addCourse();
                break;
                case 2:
                dropCourse();
                break;
                case 3:
                changeIndex();
                break;
                case 4:
                swapIndex();
                break;
                case 5:
                changePassword();
                break;
                case 6:
                PrintTimetableView view = new PrintTimetableView();
                view.print();
                break;
                case 7:
                LogoutView view = new LogoutView();
                view.print();
                break;
                default:
                System.out.println("Please input correct choice number.");
            }
        } while (choice<8);

        
    }
    
    private Student verifySecondUser() {
        System.out.print("Enter username: ");
        username = Helper.sc.nextLine();
        // print enter password
        System.out.print("Enter password: ");
        // call getPassword()
        password = getPassword();
        if (Database.USERS.containsKey(username)) {
            User result = Database.USERS.get(username);
            if (result.checkPassword(password)) {
                try {
                    Student secondUser = (Student) result;
                    return secondUser;
                } catch (Exception e) {
                    // print error message
                    System.out.println("Invalid user. Please enter again!");
                }
            }
            else return null;
        }
        else return null;
    }

    //done
    protected void addCourse(){
        //display all courses
        //parameter passed in CourseStatus.NOT_REGISTERED to display the course
        System.out.print("Enter the course code: ");
        String courseCode = sc.nextline();
        Course course = Database.COURSES.get(courseCode);
        CourseManager.printIndexList(course, true);
        //should get from courseManager
        // print index list, print vacancies true
        //printLesson
        System.out.print("Enter the course index that you wish to add: ");
        String courseIndex = Helper.sc.nextline();
        CourseManager.printLesson(courseIndex);
        /*
        have course, have index that the user wants,
        add it as a new course object to the CurrentUser.addCourse() hashmap
        */
        Database.CURRENT_USER.addCourse(courseCode,courseIndex);
    }

    //done
    protected void dropCourse(){    //get from student
        CourseManager.printCourseList(CourseStatus.REGISTERED, Database.CURRENT_USER);
        System.out.print("Enter the course code to drop: ");
        String courseCode = sc.nextline();
        Database.CURRENT_USER.dropCourse(courseCode);
    } 
    //TODO check that the course code enter by the user is valid

    //done
    protected void changeIndex(){               //get from student
        System.out.print("Enter the course code: ");
        String courseCode = Helper.sc.nextline();
        System.out.print("Enter the new index that you wish to change to: ");
        String newIndex = Helper.sc.nextLine();
        System.out.print("Enter your current index: ");
        String curIndex = Helper.sc.nextLine();
        Database.CURRENT_USER.changeIndex(courseCode, curIndex, newIndex);
    }

    protected void swapIndex() {   //current_user make 1 copy 
        Student secondStudent = verifySecondUser();
        if ( secondStudent != null ){
            //check index of what currentuser wants
            //check index of what seconduser wants
            //check for clash for the currentuser DB
            //check for clash for the seconduser DB
            // database(user class) swap
            // put the Database.currentuser.getindex() courseindex into a variable
            // put the secondstudent.getindex() courseindex into currentuser
            // seconduser = variable
            // Course course = database.courseS.get(coursecode)
            //  index1want = course.getIndex(currentuser want de index)
            // index1want.removeStudent(secondUser)
            //  index2want = course.getIndex(seconduser want de index)
            // CourseIndex.removeStudent(firstUser)
            // CourseIndex.addStudent(secondUser);
            // CourseIndex.addStudent(firstUser);
        }
        else {
            //error statement
            System.out.println("Second Student does not exist!");
        }
    }

    protected void changePassword() {       //call user class
        System.out.println("Enter current password: ");
        String oldPassowrd = Helper.getPasswordInput();
        User result = Database.USERS.get(username);
        if (oldPassword.equals(result)){
            boolean password = false;
            while(!password)
                System.out.println("Enter new password: ");
                String newPassword1 = Helper.getPasswordInput();
                System.out.println("Enter the new password again: ");
                String newPassword2 = Helper.getPasswordInput();
                if (newPassword1.equals(newPassword2)){
                    Database.CURRENT_USER.changePassword(newPassword1);
                    password = true;
                }
                else{
                    System.out.println("The passwords you entered do not match. Please try again.");
                }
        }
    }

    
    
}