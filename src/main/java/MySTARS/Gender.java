package MySTARS;

/**
 * Gender Enum that keeps track of the different Genders of the students.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public enum Gender {
    
    /**
     * Enum value corresponding to Female persons.
     */
    FEMALE("F"),

    /**
     * Enum value corresponding to Male persons.
     */
    MALE("M"),

    /**
     * Enum value corresponding to Non-Binary persons.
     */
    NONBINARY("NB"),

    /**
     * Enum value corresponding Persons that prefer not to say their Genders.
     */
    PREFER_NOT_TO_SAY("PNTS");

    /**
     * A String value corresponding to each Gender Enum.
     */
    public final String label;

    /**
     * An initialiser for each Gender Enum.
     * @param label The Sting value corresponding to the Enum.
     */
    private Gender(String label) {
        this.label = label;
    }

    /**
     * A helper method to get the Gender from a String input.
     * @param gender The desired Gender as a String.
     * @return The matching Gender. Defaults to Prefer Not To Say for invalid inputs.
     */
    protected static Gender getGender(String gender) {

        for (Gender g : Gender.values()) {
            if (g.label.equals(gender)) {
                return g;
            }
        }

        return Gender.PREFER_NOT_TO_SAY;
    }
}
