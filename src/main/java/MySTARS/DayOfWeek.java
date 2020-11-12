package MySTARS;

public enum DayOfWeek {
    MONDAY(1, "MON"),
    TUESDAY(2, "TUE"),
    WEDNESDAY(3, "WED"),
    THURSDAY(4, "THU"),
    FRIDAY(5, "FRI"),
    SATURDAY(6, "SAT"),
    SUNDAY(7, "SUN");

    public final Integer value;
    public final String label;

    private DayOfWeek(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    protected static DayOfWeek getDayOfWeek(int day) {

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.value == day) {
                return dayOfWeek;
            }
        }

        return DayOfWeek.MONDAY;
    }
}
