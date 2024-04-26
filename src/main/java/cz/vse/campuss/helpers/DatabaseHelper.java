package cz.vse.campuss.helpers;
import cz.vse.campuss.model.Satnarka;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import cz.vse.campuss.model.Umisteni;

import java.sql.*;

/**
 * Třída pro práci s databází
 */
public class DatabaseHelper {
    // URL pro připojení k databázi
    private static final String URL = "jdbc:sqlite:campuss_database.sqlite";

    /**
     * Metoda pro získání připojení k databázi
     * @return Připojení k databázi
     * @throws SQLException Pokud se nepodaří připojit k databázi
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Metoda pro získání šatnářky na základě jejího ID
     * @param userId ID šatnářky
     * @return Šatnářka
     */
    public static Satnarka fetchSatnarka(int userId) {
        Satnarka satnarka = null;
        String sql = "SELECT * FROM Satnarka WHERE id_satnarky = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    satnarka = new Satnarka(rs.getInt("id_satnarky"), rs.getString("jmeno"), rs.getString("prijmeni"), rs.getInt("id_satny_fk"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return satnarka;
    }

    /**
     * Metoda pro získání studenta na základě jeho ISIC karty
     * @param isic ISIC karta studenta
     * @return Student
     */
    public static Student fetchStudentByISIC(String isic) {
        Student student = null;
        String sql = "SELECT * FROM Student WHERE isic = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isic);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(rs.getInt("id_studenta"), rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("ISIC"),rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return student;
    }

    /**
     * Metoda pro získání volného umístění v závsáku na typu umístění
     * @param typUmisteni Typ umístění
     * @return Umístění
     */
    public static Umisteni fetchUnusedUmisteni(TypUmisteni typUmisteni) {
        Umisteni umisteni = null;
        String sql = "SELECT * FROM Umisteni WHERE typ_umisteni = ? AND id_satny_fk = '1' AND   student IS NULL LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, typUmisteni.getText());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    umisteni = new Umisteni(rs.getInt("id_umisteni"), rs.getInt("cislo"), TypUmisteni.fromString(rs.getString("typ_umisteni")), rs.getString("student"), rs.getInt("id_satny_fk"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return umisteni;
    }



    /**
     * Metoda pro aktualizaci obsazenosti umístění
     * @param isic isic studenta
     * @param cislo Číslo umístění
     * @param typUmisteni Typ umístění
     */
    public static void updateUmisteni(String isic, int cislo, TypUmisteni typUmisteni) {
        String sql = "UPDATE Umisteni SET student = ? WHERE typ_umisteni = ? AND cislo = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isic);
            pstmt.setInt(3, cislo);
            if (typUmisteni == TypUmisteni.VESAK) {
                pstmt.setString(2, "věšák");
            } else if (typUmisteni == TypUmisteni.PODLAHA) {
                pstmt.setString(2, "podlaha");
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
