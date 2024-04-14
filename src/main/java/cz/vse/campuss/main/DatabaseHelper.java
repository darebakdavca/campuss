package cz.vse.campuss.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:campuss_database.sqlite";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static String fetchUserNameSatnarka(int userId) {
        String name = "";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT jmeno FROM Satnarka WHERE id_satnarky = " + userId)) {
            if (rs.next()) {
                name = rs.getString("jmeno");
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return name;
    }
}
