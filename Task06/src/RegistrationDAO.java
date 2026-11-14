
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public List<Event> getEvents() throws SQLException {
        List<Event> events = new ArrayList<>();

        String sql = "SELECT event_id, event_name FROM events ORDER BY event_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(new Event(
                    rs.getInt("event_id"),
                    rs.getString("event_name")
                ));
            }
        }

        return events;
    }

    public void addRegistration(String studentId,
                                String studentName,
                                String department,
                                int eventId) throws SQLException {

        String sql = "INSERT INTO registrations " +
                "(student_id, student_name, department, event_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ps.setString(2, studentName);
            ps.setString(3, department);
            ps.setInt(4, eventId);

            ps.executeUpdate();
        }
    }

    public List<Registration> getRegistrations(Integer eventId)
            throws SQLException {

        List<Registration> registrations = new ArrayList<>();

        String sql = "SELECT r.registration_id, r.student_id, " +
                "r.student_name, r.department, e.event_name, " +
                "r.registration_date " +
                "FROM registrations r " +
                "JOIN events e ON r.event_id = e.event_id " +
                (eventId == null ? "" : "WHERE r.event_id = ? ") +
                "ORDER BY r.registration_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (eventId != null) {
                ps.setInt(1, eventId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    registrations.add(new Registration(
                        rs.getInt("registration_id"),
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("department"),
                        rs.getString("event_name"),
                        rs.getTimestamp("registration_date")
                    ));
                }
            }
        }

        return registrations;
    }
}
