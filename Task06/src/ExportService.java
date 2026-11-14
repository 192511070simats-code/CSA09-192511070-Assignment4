
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExportService {

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    public void exportToCSV(List<Registration> registrations, File file)
            throws IOException {

        try (PrintWriter writer = new PrintWriter(
                file, StandardCharsets.UTF_8)) {

            writer.println(
                "Registration ID,Student ID,Student Name," +
                "Department,Event,Registration Date"
            );

            for (Registration r : registrations) {
                writer.println(
                    r.getRegistrationId() + "," +
                    escape(r.getStudentId()) + "," +
                    escape(r.getStudentName()) + "," +
                    escape(r.getDepartment()) + "," +
                    escape(r.getEventName()) + "," +
                    escape(r.getRegistrationDate().toString())
                );
            }
        }
    }
}
