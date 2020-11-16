package MySTARS;

import java.util.HashMap;
import java.io.*;
import org.joda.time.DateTime;

/**
 * The Class that interacts with the files and handles reading and writing of information.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public final class Database {
    
    /**
     * A {@code Hashmap<String, Course>} that stores all {@link Course}s and its contained information.
     */
    protected static HashMap<String, Course> COURSES = new HashMap<String, Course>();

    /**
     * A {@code Hashmap<String, User>} that stores all {@link User}s and their contained information.
     */
    protected static HashMap<String, User> USERS = new HashMap<String, User>();

    /**
     * A {@code Hashmap<String, Object>} that stores all Miscellaneous settings so that the application can return to its previous state after shutdown. 
     */
    protected static HashMap<String, Object> SETTINGS = new HashMap<String, Object>();

    /**
     * A User object that keeps track of the currently logged in User.
     */
    protected static User CURRENT_USER = null;
    
    /**
     * The current AccessLevel of the currently logged in User.
     */
    protected static AccessLevel CURRENT_ACCESS_LEVEL = AccessLevel.NONE;

    /**
     * The initialiser for the Database class. Reads Files on startup, resets key Admin Users if they have been deleted and retores all Application Settings from last close.
     */
    protected Database() {
  
        deserialise(FileType.COURSES);
        deserialise(FileType.USERS);
        deserialise(FileType.MISC);
        resetUsers();
        restoreSettings();
    }

    /**
     * Reads files into its respective Hashmap.
     * @param fileType The type of file being read of type {@link FileType}.
     * @return {@code true} if successfully read file.
     */
    @SuppressWarnings("unchecked")
    protected static boolean deserialise(FileType fileType) {
        
        try {
            //TODO Change directory before submission
            FileInputStream fileInput = new FileInputStream("./src/main/java/Files/" + fileType.fileName + ".dsai");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Object object = objectInput.readObject();

            if (fileType == FileType.COURSES && object != null) {
                COURSES = (HashMap<String, Course>) object;
                objectInput.close();
                fileInput.close();
            } else if (fileType == FileType.USERS && object != null) {
                USERS = (HashMap<String, User>) object;
                objectInput.close();
                fileInput.close();
            } else if (fileType == FileType.MISC && object != null) {
                SETTINGS = (HashMap<String, Object>) object;
                objectInput.close();
                fileInput.close();
            } else {
                objectInput.close();
                fileInput.close();
                System.out.println("Unable to cast to a type");
                return false;
            }
        } catch (Exception e) {
            
            try {
                new FileOutputStream("./src/main/java/Files/" + fileType.fileName + ".dsai", true).close();
            } catch (Exception f) {
                System.out.println("Failed to write file with exception" + f.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Writes Hashmaps into the respective files.
     * @param fileType The type of the file being written.
     * @return {@code true} if successfully wrote file.
     */
    protected static boolean serialise(FileType fileType) {
        
        try {
            //TODO Change directory before submission
            FileOutputStream fileOutput = new FileOutputStream("./src/main/java/Files/" + fileType.fileName + ".dsai");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            if (fileType == FileType.COURSES) {
                objectOutput.writeObject(COURSES);
                objectOutput.close();
                fileOutput.close();
            } else if (fileType == FileType.USERS) {
                objectOutput.writeObject(USERS);
                objectOutput.close();
                fileOutput.close();
            } else if (fileType == FileType.MISC ) {
                objectOutput.writeObject(SETTINGS);
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

    /**
     * Resets Admin users if they have been deleted.
     */
    private void resetUsers() {
        USERS.put("Admin", new Admin("Admin", "AdminPassword"));
        USERS.put("Bhargav", new Admin("Bhargav", "OODPisgood"));
        USERS.put("Timothy", new Admin("Timothy", "IloveOODP"));
        USERS.put("Esther", new Admin("Esther", "JavaForLife"));
        USERS.put("Nicolette", new Admin("Nicolette", "this.isBest"));
        USERS.put("JiaHui", new Admin("JiaHui", "return"));
    }

    /**
     * Reads and restores key settings from file.
     */
    private void restoreSettings() {

        DateTime startTime = (DateTime) Database.SETTINGS.get("loginStart");
        DateTime endTime = (DateTime) Database.SETTINGS.get("loginEnd");
        try {
            LoginView.setLoginTime(startTime, endTime);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
