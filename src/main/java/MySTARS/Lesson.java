package MySTARS;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.joda.time.Interval;

public final class Lesson implements Serializable {

    private ClassType classType;
    private Interval time;
    private DateTime startTime;
    private DateTime endTime;
    private DayOfWeek dayOfWeek;
    private String location;
    private String remarks;
    private static final long serialVersionUID = 12L;

    protected Lesson(ClassType classType, DayOfWeek dayOfWeek, int startTime, int endTime, String location) {

        this.classType = classType;
        this.startTime = new DateTime(2020, 1, dayOfWeek.value, startTime/100, startTime%100);
        this.endTime = new DateTime(2020, 1, dayOfWeek.value, endTime/100, endTime%100);
        this.time = new Interval(this.startTime, this.endTime);
        this.dayOfWeek = dayOfWeek;
        this.location = location;
    }

    protected ClassType getType() {

        return this.classType;
    }

    protected Interval getTime() {

        return this.time;
    }

    protected DateTime getStartTime() {

        return this.startTime;
    }

    protected DateTime getEndTime() {

        return this.endTime;
    }

    protected void setTime(int startTime, int endTime) {

        this.startTime = new DateTime(2020, 1, this.dayOfWeek.value, startTime/100, startTime%100);
        this.endTime = new DateTime(2020, 1, this.dayOfWeek.value, endTime/100, endTime%100);
        this.time = new Interval(this.startTime, this.endTime);
    }

    protected String getLocation() {

        return this.location;
    }

    protected void setLocation(String location) {

        this.location = location;
    }

    protected void setRemarks(String remarks) {

        this.remarks = remarks;
    }

    protected String getRemarks() {

        return this.remarks;
    }
}
