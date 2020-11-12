package MySTARS;

public enum CourseStatus {
    
    REGISTERED("RGSTED"),
    NOT_REGISTERED("NOTREG"),
    WAITLIST("WLIST"),
    NONE("NONE");

    public final String label;

    private CourseStatus(String label) {
        this.label = label;
    }
}
