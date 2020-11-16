package MySTARS;

/**
 * Ads a new {@link Student} or {@link Admin} to the {@link Database}.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class AddUserView extends View {

    /**
     * Required method from View. Prints prompts for the user to input so that all key information to create {@link Student} or {@link Admin} is present.
     */
    protected void print() {
        
        clearScreen("AdminMain > Add User");

        while (true) {
            System.out.print("Enter username or Q to quit: ");
            String username = Helper.sc.nextLine();
            if (username.equals("Q")) {
                break;
            }
            if (!Database.USERS.containsKey(username)) {
                String accessLevel;
                do {
                    System.out.print("Enter account type (Admin/Student): ");
                    accessLevel = Helper.sc.nextLine();
                } while (!accessLevel.equals("Admin") && !accessLevel.equals("Student"));

                if (accessLevel.equals("Admin")) {
                    String password1, password2;
                    while (true) {
                        System.out.print("Enter the password: ");
                        password1 = Helper.getPasswordInput();
                        System.out.print("Enter the password again: ");
                        password2 = Helper.getPasswordInput();
                        if (password1.equals(password2)) {
                            break;
                        } else {
                            System.out.println("The passwords do not match!");
                        }
                    }
                    Admin admin = new Admin(username, password1);
                    Database.USERS.put(username, admin);
                    System.out.println("New admin added successfully.");
                    Helper.pause();
                } else {

                    System.out.print("Enter the Student's First Name: ");
                    String firstName = Helper.sc.nextLine();
                    System.out.print("Enter the Student's Last Name: ");
                    String lastName = Helper.sc.nextLine();
                    System.out.print("Enter the Student's Gender (M/F/NB/PNTS): ");
                    //FIXME error check gender
                    Gender gender = Gender.getGender(Helper.sc.nextLine());
                    System.out.print("Enter the Student's Nationality: ");
                    String nationality = Helper.sc.nextLine();
                    
                    String matricNumber;
                    while (true) {
                        System.out.print("Enter the Student's Matric number: ");
                        matricNumber = Helper.sc.nextLine();
                        if (Student.isValidNewMatricNo(matricNumber)) {
                            break;
                        } else {
                            System.out.println("Invalid Matric Number!");
                        }
                    }

                    System.out.println("\n\nNew Student Details:");
                    System.out.println(String.format("%-20s:", "Name") + firstName + " " + lastName);
                    System.out.println(String.format("%-20s:", "Network Username") + username);
                    System.out.println(String.format("%-20s:", "Matric No.") + matricNumber);
                    System.out.println(String.format("%-20s:", "Gender") + gender.label);
                    System.out.println(String.format("%-20s:", "Nationality") + nationality);

                    System.out.print("\nConfirm? y/n: ");
                    String confirm = Helper.sc.nextLine();
                    if (confirm.equals("y")) {
                        Student student = new Student(username, matricNumber, firstName, lastName, gender, nationality);
                        Database.USERS.put(username, student);
                        Database.serialise(FileType.USERS);
                        System.out.println("New student added successfully.");
                        Helper.pause();
                    } else {
                        System.out.println("Aborting");
                    }
                }
            } else {
                System.out.println("User has already been added!");
            }
        }
    }
}
