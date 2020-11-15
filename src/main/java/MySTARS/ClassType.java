package MySTARS;

/**
 * An Enum that keeps track of the different class types.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public enum ClassType {
    
    /**
     * Enum value for Lectures.
     */
    LECTURE("LEC"),

    /**
     * Enum value for Labs.
     */
    LAB("LAB"),

    /**
     * Enum value for Tutorials.
     */
    TUTORIAL("TUT"),

    /**
     * Enum value for Seminars.
     */
    SEMINAR("SEM"),

    /**
     * Enum value for Online lessons.
     */
    ONLINE("ONL");

    /**
     * String label that is associated with each Enum value. Used for printing timetable, as well as lesson list.
     */
    public final String label;

    /**
     * Initialiser for ClassType Enum.
     * @param label The String label for the Enum value.
     */
    private ClassType(String label) {
        this.label = label;
    }
}
