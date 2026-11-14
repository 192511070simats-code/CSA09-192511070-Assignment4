
import java.sql.SQLException;

public class RegistrationService {

    private final RegistrationDAO dao;

    public RegistrationService() {
        dao = new RegistrationDAO();
    }

    public void registerStudent(String studentId,
                                String studentName,
                                String department,
                                Event event) throws Exception {

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required.");
        }

        if (!studentId.trim().matches("[A-Za-z0-9-]+")) {
            throw new IllegalArgumentException(
                "Student ID must contain only letters, numbers or hyphens."
            );
        }

        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }

        if (!studentName.trim().matches("[\\p{L} .'-]+")) {
            throw new IllegalArgumentException(
                "Student name contains invalid characters."
            );
        }

        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Select a department.");
        }

        if (event == null) {
            throw new IllegalArgumentException("Select an event.");
        }

        try {
            dao.addRegistration(
                studentId.trim(),
                studentName.trim(),
                department,
                event.getEventId()
            );
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                throw new IllegalArgumentException(
                    "This student is already registered for this event."
                );
            }
            throw e;
        }
    }
}
