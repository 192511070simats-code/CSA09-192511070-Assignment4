
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(
                     "SELECT * FROM events")) {

            System.out.println("MySQL connection successful!");
            System.out.println("Available campus events:");

            while (result.next()) {
                System.out.println(
                        result.getInt("event_id") + " - " +
                        result.getString("event_name")
                );
            }

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
