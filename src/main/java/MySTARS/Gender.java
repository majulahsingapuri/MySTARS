package MySTARS;

public enum Gender {
    
    FEMALE("F"),
    MALE("M"),
    NONBINARY("NB"),
    PREFER_NOT_TO_SAY("PNTS");

    public final String label;

    private Gender(String label) {
        this.label = label;
    }
}
