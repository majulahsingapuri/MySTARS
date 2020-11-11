package MySTARS;

import java.util.HashMap;
import java.io.*;

public final class Database {
    
    protected static HashMap<String, Course> COURSES = new HashMap<String, Course>();
    protected static HashMap<String, User> USERS = new HashMap<String, User>();
    protected static User CURRENT_USER = null;
    protected static AccessLevel CURRENT_ACCESS_LEVEL = AccessLevel.NONE;

    protected Database() {

        
        deserialise(FileType.COURSES);
        deserialise(FileType.USERS);
        resetUsers();
    }

    @SuppressWarnings("unchecked")
    protected static boolean deserialise(FileType fileType) {

        String fileName;

        if (fileType == FileType.COURSES) {
            fileName = "Courses";
        } else if (fileType == FileType.USERS) {
            fileName = "Users";
        } else {
            System.out.println("Incorrect file specifier");
            return false;
        }
        
        try {
            
            FileInputStream fileInput = new FileInputStream("./src/main/java/Files/" + fileName + ".dsai");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Object object = objectInput.readObject();

            if (fileType == FileType.COURSES && object != null) {
                COURSES = (HashMap<String, Course>)object;
                objectInput.close();
                fileInput.close();
            } else if (fileType == FileType.USERS && object != null) {
                USERS = (HashMap<String, User>) object;
                objectInput.close();
                fileInput.close();
            } else {
                objectInput.close();
                fileInput.close();
                System.out.println("Unable to cast to a type");
                return false;
            }
        } catch (Exception e) {
            
            System.out.println("Failed to read file with exception" + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    protected static boolean serialise(FileType fileType) {

        String fileName;

        if (fileType == FileType.COURSES) {
            fileName = "Courses";
        } else if (fileType == FileType.USERS) {
            fileName = "Users";
        } else {
            System.out.println("Incorrect file specifier");
            return false;
        }
        
        try {
            
            FileOutputStream fileOutput = new FileOutputStream("./src/main/java/Files/" + fileName + ".dsai");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            if (fileType == FileType.COURSES) {
                objectOutput.writeObject(COURSES);
                objectOutput.close();
                fileOutput.close();
            } else if (fileType == FileType.USERS) {
                objectOutput.writeObject(USERS);
                objectOutput.close();
                fileOutput.close();
            } else {
                objectOutput.close();
                fileOutput.close();
                System.out.println("Unable to write file");
                return false;
            }
        } catch (Exception e) {
            
            System.out.println("Failed to write file with exception" + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private void resetUsers() {
        USERS.put("Admin", new Admin("Admin", "AdminPassword"));
        USERS.put("Bhargav", new Admin("Bhargav", "OODPisgood"));
        USERS.put("Timothy", new Admin("Timothy", "IloveOODP"));
        USERS.put("Esther", new Admin("Esther", "JavaForLife"));
        USERS.put("Nicolette", new Admin("Nicolette", "this.isBest"));
        USERS.put("JiaHui", new Admin("JiaHui", "return"));
    }
}
