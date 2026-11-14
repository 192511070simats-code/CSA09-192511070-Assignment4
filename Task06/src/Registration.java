
import java.sql.Timestamp;

public class Registration {

    private final int registrationId;
    private final String studentId;
    private final String studentName;
    private final String department;
    private final String eventName;
    private final Timestamp registrationDate;

    public Registration(int registrationId, String studentId,
                        String studentName, String department,
                        String eventName, Timestamp registrationDate) {

        this.registrationId = registrationId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.department = department;
        this.eventName = eventName;
        this.registrationDate = registrationDate;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDepartment() {
        return department;
    }

    public String getEventName() {
        return eventName;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
}
