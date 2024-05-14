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
    public static Umisteni updateUmisteni(String isic, int cislo, TypUmisteni typUmisteni) {
        Umisteni umisteni = null;
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
        return umisteni;
    }

    /**
     * Metoda pro vytvoření záznamu v historii
     * @param jmenoStudenta Jméno studenta
     * @param prijmeniStudenta Příjmení studenta
     * @param isic ISIC karta studenta
     * @param typUmisteni Typ umístění
     * @param cisloUmisteni Číslo umístění
     */
    public static int createHistorieEntry(String jmenoStudenta, String prijmeniStudenta, String isic, TypUmisteni typUmisteni, int cisloUmisteni, StavUlozeni stavUlozeni) {
        int id = -1;

        String sql = "INSERT INTO Historie (jmeno_studenta, prijmeni_studenta, isic_studenta, satna_nazev, umisteni_typ, umisteni_cislo, stav, cas_zmeny_stavu, satnarka_id, umisteni_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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


            // získání aktuálního času
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);

            pstmt.setString(8, currentTime); //čas změny stavu

            pstmt.setInt(9, 1); // id šatnářky - ještě není automatické

            pstmt.setInt(10, fetchLocationNumberByISIC(isic, typUmisteni)); // id umístění

            pstmt.executeUpdate();

            // získání id nového záznamu
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return id;
    }

    /**
     * Vrátí data z Historie podle zadaného isicu.
     * Pokud není isic specifikován, vrátí všechna data z Historie.
     *
     * @param isic podle kterého se mají data Historie filtrovat
     * @return data Historie
     */
    public static ObservableList<PolozkaHistorie> fetchHistorieAll(String isic) {
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
                    data.add(new PolozkaHistorie(rs.getInt("id"), rs.getString("jmeno_studenta"), rs.getString("prijmeni_studenta"), rs.getString("isic_studenta"), rs.getString("satna_nazev"), TypUmisteni.fromString(rs.getString("umisteni_typ")), rs.getInt("umisteni_cislo"), StavUlozeni.fromString(rs.getString("stav")), rs.getTimestamp("cas_zmeny_stavu").toLocalDateTime().format(formatter), rs.getInt("satnarka_id"), rs.getInt("umisteni_id")));
                }
                return data;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return data;
    }



    public static PolozkaHistorie fetchActiveUschovani(int id) {
        PolozkaHistorie polozkaHistorie = null;
        String sql = "SELECT * FROM Historie WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    polozkaHistorie = new PolozkaHistorie(rs.getInt("id"), rs.getString("jmeno_studenta"), rs.getString("prijmeni_studenta"), rs.getString("isic_studenta"), rs.getString("satna_nazev"), TypUmisteni.fromString(rs.getString("umisteni_typ")), rs.getInt("umisteni_cislo"), StavUlozeni.fromString(rs.getString("stav")), rs.getTimestamp("cas_zmeny_stavu").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), rs.getInt("satnarka_id"), rs.getInt("umisteni_id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return polozkaHistorie;
    }


//



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



    public static String getSatnaFromISIC(String isic_studenta) {
        String satnaNazev = null;
        String sql = "SELECT satna_nazev FROM Historie WHERE isic_studenta = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isic_studenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    satnaNazev = rs.getString("satna_nazev");
                    return satnaNazev;
                } else {
                    System.out.println("No records found for ISIC: " + isic_studenta);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return satnaNazev;
    }

    public static String getISICByEmail(String email) {
        String isic = null;
        String sql = "SELECT ISIC FROM Student WHERE email = ?";
        try (Connection conn = getConnection(); // Use the centralized connection method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    isic = rs.getString("ISIC");
                } else {
                    System.out.println("No ISIC found for the email: " + email);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return isic;
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