package MySTARS;

public enum ClassType {
    
    LECTURE("LEC"),
    LAB("LAB"),
    TUTORIAL("TUT"),
    SEMINAR("SEM"),
    ONLINE("ONL");

    public final String label;

    private ClassType(String label) {
        this.label = label;
    }
}
