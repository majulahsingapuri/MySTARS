package MySTARS;

public enum AU {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    public final Integer value;

    private AU(Integer value) {
        this.value = value;
    }

    protected static AU getAU(int acadUnit) {

        for (AU au : AU.values()) {
            if (au.value == acadUnit) {
                return au;
            }
        }

        return AU.ONE;
    }
}
