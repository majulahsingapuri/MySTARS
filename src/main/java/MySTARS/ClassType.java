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

    /**
     * Takes in a string indicating the class type, and returns the corresponding {@link ClassType} enum.
     * @param type string indicating the class type (corresponds to a {@link ClassType} label).
     * @return corresponding {@link ClassType}.
     */
    public static ClassType getClassType(String type) {

        for (ClassType classType : ClassType.values()) {
            if (classType.label.equals(type)) {
                return classType;
            }
        }
        return ClassType.TUTORIAL;
    }
}
