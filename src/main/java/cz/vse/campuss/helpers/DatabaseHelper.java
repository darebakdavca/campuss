package cz.vse.campuss.helpers;
import cz.vse.campuss.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
     * Metoda pro získání volného umístění v závislosti na typu umístění
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

    /**
     * Metoda pro vytvoření záznamu v historii
     * @param jmenoStudenta Jméno studenta
     * @param prijmeniStudenta Příjmení studenta
     * @param isic ISIC karta studenta
     * @param typUmisteni Typ umístění
     * @param cisloUmisteni Číslo umístění
     */
    public static void createHistorieEntry(String jmenoStudenta, String prijmeniStudenta, String isic, TypUmisteni typUmisteni, int cisloUmisteni, StavUlozeni stavUlozeni) {
        String sql = "INSERT INTO Historie (jmeno_studenta, prijmeni_studenta, isic_studenta, satna_nazev, umisteni_typ, umisteni_cislo, stav, cas_zmeny_stavu, satnarka_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jmenoStudenta); //jméno studenta
            pstmt.setString(2, prijmeniStudenta); //příjmení studenta
            pstmt.setString(3, isic); //isic studenta
            pstmt.setString(4, SatnaSelection.getInstance().getSelectedSatna());  //název šatny

            if (typUmisteni == TypUmisteni.VESAK) { //typ umístění
                pstmt.setString(5, "věšák");
            } else if (typUmisteni == TypUmisteni.PODLAHA) {
                pstmt.setString(5, "podlaha");
            }

            pstmt.setInt(6, cisloUmisteni); //číslo umístění

            if (stavUlozeni == StavUlozeni.USCHOVANO) { // stav uložení
                pstmt.setString(7,"uschováno");
            } else if (stavUlozeni == StavUlozeni.VYZVEDNUTO) {
                pstmt.setString(7,"vyzvednuto");
            }

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            pstmt.setString(8, currentTime); //čas změny stavu

            pstmt.setInt(9, 1); // id šatnářky - ještě není automatické

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Vrátí data z Historie podle zadaného isicu.
     * Pokud není isic specifikován, vrátí všechna data z Historie.
     *
     * @param isic podle kterého se mají data Historie filtrovat
     * @return data Historie
     */
    public static ObservableList<PolozkaHistorie> fetchHistorie(String isic) {
        ObservableList<PolozkaHistorie> data = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseHelper.getConnection();
            String query = (isic == null || isic.isEmpty()) ? "SELECT * FROM Historie" :
                    "SELECT * FROM Historie WHERE isic_studenta = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            if (!(isic == null || isic.isEmpty())) {
                pstmt.setString(1, isic);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                while (rs.next()) {
                    data.add(new PolozkaHistorie(rs.getInt("id"), rs.getString("jmeno_studenta"), rs.getString("prijmeni_studenta"), rs.getString("isic_studenta"), rs.getString("satna_nazev"), TypUmisteni.fromString(rs.getString("umisteni_typ")), rs.getInt("umisteni_cislo"), StavUlozeni.fromString(rs.getString("stav")), rs.getTimestamp("cas_zmeny_stavu").toLocalDateTime().format(formatter), rs.getInt("satnarka_id")));
                }
                return data;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return data;
    }

    /**
     * Metoda pro získání umístění studenta na základě jeho ISIC karty a typu umístění
     * @param isic ISIC karta studenta
     * @param typUmisteni Typ umístění
     * @return Číslo umístění
     */
    public static int fetchLocationNumberByISIC(String isic, TypUmisteni typUmisteni) {
        int locationNumber = -1;
        String sql = "SELECT cislo FROM Umisteni WHERE student = ? AND typ_umisteni = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isic);
            if (typUmisteni == TypUmisteni.VESAK) { //typ umístění
                pstmt.setString(2, "věšák");
            } else if (typUmisteni == TypUmisteni.PODLAHA) {
                pstmt.setString(2, "podlaha");
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    locationNumber = rs.getInt("cislo");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return locationNumber;
    }

    /**
     * Metoda pro odstranění isic studenta u umístění na základě jeho ISIC karty
     * @param isic ISIC karta studenta
     */
    public static void removeLocationFromUmisteniByISIC(String isic) {
        String sql = "UPDATE Umisteni SET student = NULL WHERE student = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isic);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
