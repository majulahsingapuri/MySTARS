package MySTARS;

/**
 * FileType Enum that corresponds to the different files that the Application will use.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public enum FileType {
    
    /**
     * File type corresponding to the Courses file.
     */
    COURSES("Courses"),

    /**
     * File type corresponding to the Users file.
     */
    USERS("Users"),

    /**
     * File type corresponding to the Miscellaneous file.
     */
    MISC("Misc");

    /**
     * A String value for the FileType so that it is easy to retrieve.
     */
    public final String fileName;

    /**
     * Initialiser for the FileType Enum.
     * @param fileName Assigns a fileName to each Enum value so files are easy to retrieve.
     */
    private FileType(String fileName) {
        this.fileName = fileName;
    }
}
