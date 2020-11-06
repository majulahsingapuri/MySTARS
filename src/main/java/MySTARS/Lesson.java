package MySTARS;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.joda.time.Interval;

// This class contains information specific to lesson types

public final class Lesson implements Serializable {

    private ClassType type;
    private Interval time;  // TODO use jodatime to sort out timetable stuff
    private DateTime startTime;
    private DateTime endTime;
    private String location;
    private String remarks;
    private static final long serialVersionUID = 12L;

    protected Lesson(ClassType type, Date time, String location) {
        this.type = type;
        this.time = time;
        this.location = location;
    }

    protected ClassType getType() {
        return this.type;
    }

    protected void setType(ClassType type) {
        this.type = type;
    }

    protected Date getTime() {
        return this.time;
    }

    protected void setTime(Date time) {
        this.time = time;
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