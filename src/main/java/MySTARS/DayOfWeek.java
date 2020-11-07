package MySTARS;

public enum DayOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    public final Integer value;

    private DayOfWeek(Integer value) {
        this.value = value;
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
