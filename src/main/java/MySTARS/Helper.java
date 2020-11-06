package MySTARS;

import java.util.Scanner;
import org.joda.time.DateTime;

public final class Helper {
    
    protected static Scanner sc = new Scanner(System.in);

    //TODO should this be a static method?
    protected void load() {

        //TODO Change to our loading bars
        System.out.printf("║║║║║║║║║║║║");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    protected static DateTime formatTime(DayOfWeek day, int hour_24, int minute) {
        int dayInt;
        switch(day){
            case MONDAY:
                dayInt = 1;
                break;
            case TUESDAY:
                dayInt = 2;
                break;
            case WEDNESDAY:
                dayInt = 3;
                break;
            case THURSDAY:
                dayInt = 4;
                break;
            case FRIDAY:
                dayInt = 5;
                break;
            case SATURDAY:
                dayInt = 6;
                break;
            default:
                dayInt = 7;
                break;
        }

        String dateStr = "2020-06-0" + dayInt + "T" + hour_24 + ":" + minute + ":00";

        return new DateTime(dateStr);
    }
}
