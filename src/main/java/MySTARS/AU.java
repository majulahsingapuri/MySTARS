package MySTARS;

/**
 * An enum that keeps track of the numebr of Academic Units that each {@link Course} is worth.
 * @author Bhargav
 * @version 1.0
 * @since 2020-11-1
 */
public enum AU {

    /**
     * An enum representing 1 Academic Unit.
     */
    ONE(1),

    /**
     * An enum representing 2 Academic Units.
     */
    TWO(2),

    /**
     * An enum representing 3 Academic Units.
     */
    THREE(3),
    
    /**
     * An enum representing 4 Academic Units.
     */
    FOUR(4);

    /**
     * An Integer value that is associated with each Enum value.
     */
    public final Integer value;

    /**
     * Initialiser for the Enum that ensures each Enum value has a corresponding Integer value.
     * @param value The Integer value of the Enum.
     */
    private AU(Integer value) {
        this.value = value;
    }

    /**
     * A helper method that converts an input integer value to the appropriate AU value.
     * @param acadUnit The integer value to compare with.
     * @return The relevant AU value for a given integer. Defaults to AU.ONE if no match found.
     */
    protected static AU getAU(int acadUnit) {

        for (AU au : AU.values()) {
            if (au.value == acadUnit) {
                return au;
            }
        }

        return AU.ONE;
    }
}
