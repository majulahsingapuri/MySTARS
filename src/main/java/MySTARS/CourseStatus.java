package MySTARS;

/**
 * An Enum that keeps track of the Status of a particular course.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public enum CourseStatus {
    
    /**
     * Enum value corresponding to a Registered Course.
     */
    REGISTERED("RGSTED"),

    /**
     * Enum value corresponding to a Not Registered Course.
     */
    NOT_REGISTERED("NOTREG"),

    /**
     * Enum value corresponding to a course on Waitlist.
     */
    WAITLIST("WLIST"),

    /**
     * Enum value corresponding to a course not affiliated with a {@link Student}.
     */
    NONE("NONE");

    /**
     * String value corresponding to each Enum value.
     */
    public final String label;

    /**
     * Initialiser for CourseStatus with label value.
     * @param label Value assigned to each CourseStatus Enum.
     */
    private CourseStatus(String label) {
        this.label = label;
    }
}
