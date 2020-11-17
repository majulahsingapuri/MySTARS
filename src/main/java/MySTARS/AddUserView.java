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
    public void print() {
        
        clearScreen("AdminMain > Add User");

        while (true) {
            System.out.print("Enter username or Q to quit: ");
            String username = Helper.readLine();
            if (username.equals("Q")) {
                break;
            }
            if (!Database.USERS.containsKey(username) || !username.matches("(^\\p{Alnum}+$)")) {
                String accessLevel;
                System.out.println("Enter user domain");
                System.out.println("1. Admin");
                System.out.println("2. Student");
                while (true) {
                    System.out.print(String.format("%-50s: ", "Choice"));
                    accessLevel = Helper.readLine();
                    
                    if (accessLevel.equals("1") || accessLevel.equals("2")) {
                        break;
                    } else {
                        System.out.println("Invalid input");
                    }
                }

                if (accessLevel.equals("1")) {
                    String password1, password2;
                    while (true) {
                        System.out.print(String.format("%-50s: ", "Enter the password"));
                        password1 = Helper.getPasswordInput();
                        System.out.print(String.format("%-50s: ", "Enter the password again"));
                        password2 = Helper.getPasswordInput();
                        if (password1.equals(password2)) {
                            break;
                        } else {
                            System.out.println("The passwords do not match!");
                        }
                    }
                    Admin admin = new Admin(username, password1);
                    Database.USERS.put(username, admin);
                    Database.serialise(FileType.USERS);
                    System.out.println("New admin added successfully.");
                    Helper.pause();
                } else {

                    System.out.print(String.format("%-50s: ", "Enter the Student's First Name"));
                    String firstName = Helper.readLine();

                    System.out.print(String.format("%-50s: ", "Enter the Student's Last Name"));
                    String lastName = Helper.readLine();

                    int genderChoice;
                    Gender gender;
                    
                    while (true) {
                        try {
                            System.out.println("Enter the Student's Gender\n1. F\n2. M\n3. NB\n4. PNTS");
                            System.out.print(String.format("%-50s: ", "Default (PNTS)"));
                            genderChoice = Integer.parseInt(Helper.readLine());
                            if (genderChoice < 1 || genderChoice > 4) {
                                throw new Exception();
                            }
                            gender = Gender.getGender(genderChoice);
                            break;
                        } catch (Exception e) {
                            System.out.println("Please enter a valid number.");
                        }
                    }

                    System.out.print(String.format("%-50s: ", "Enter the Student's Nationality"));
                    String nationality = Helper.readLine();
                    
                    String matricNumber;
                    while (true) {
                        System.out.print(String.format("%-50s: ", "Enter the Student's Matric number"));
                        matricNumber = Helper.readLine();
                        if (Student.isValidNewMatricNo(matricNumber)) {
                            break;
                        } else {
                            System.out.println("Invalid Matric Number!");
                        }
                    }

                    Helper.printLine(74);

                    System.out.println("\n\nNew Student Details:");
                    System.out.println(String.format("%-50s: ", "Name") + firstName + " " + lastName);
                    System.out.println(String.format("%-50s: ", "Network Username") + username);
                    System.out.println(String.format("%-50s: ", "Matric No.") + matricNumber);
                    System.out.println(String.format("%-50s: ", "Gender") + gender.label);
                    System.out.println(String.format("%-50s: ", "Nationality") + nationality);

                    System.out.print(String.format("\n%-50s: ", "Confirm? y/n"));
                    String confirm = Helper.readLine();
                    if (confirm.equals("Y")) {
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
