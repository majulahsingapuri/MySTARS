import java.io.Serializable;
import java.util.Date;

// This class contains information specific to lesson types

class Lesson implements Serializable {

    private ClassType type;
    private Date time;
    private String location;
    private String remarks;

    public Lesson() {}

    public Lesson(ClassType type, Date time, String location) {
        this.type = type;
        this.time = time;
        this.location = location;
    }

    protected ClassType getType() {
        return this.type;
    }

    protected Date getTime() {
        return this.time;
    }

    protected String getLocation() {
        return this.location;
    }

    protected void setType(ClassType type) {
        this.type = type;
    }

    protected void setTime(Date time) {
        this.time = time;
    }

    protected void setLocation(String location) {
        this.location = location;
    }

    protected void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}