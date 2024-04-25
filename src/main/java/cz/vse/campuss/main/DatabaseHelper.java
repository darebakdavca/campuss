package cz.vse.campuss.main;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.Umisteni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Třída pro práci s databází
 */
public class DatabaseHelper {
    // URL pro připojení k databázi
    private static final String URL = "jdbc:sqlite:campuss_database.sqlite";

    /**
     * Metoda pro získání připojení k databázi
     * @return Připojení k databázi
     * @throws Exception Pokud se nepodaří připojit k databázi
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    /**
     * Metoda pro získání jména šatnářky na základě jejího ID
     * @param userId ID šatnářky
     * @return Jméno šatnářky
     */
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
    /**
     * Metoda pro získání jména studenta na základě jeho ISIC
     * @param isic ISIC studenta
     * @return Jméno studenta
     */
    public static Student fetchStudentByISIC(String isic) {
        Student student = null;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student WHERE isic = '" + isic + "'")) {
            if (rs.next()) {
                student = new Student(rs.getInt("id_studenta"), rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("isic"));
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return student;
    }


    /**
     * Metoda pro získání všech umístění
     * @param Zavazadlo Zda se jedná o zavazadlo
     * @param Vesak Zda se jedná o věšák
     * @return Seznam umístění
     */
    public static ArrayList<Umisteni> fetchAllUmisteni(boolean Zavazadlo, boolean Vesak) {
        ArrayList<Umisteni> umisteniList = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String query;
            // Pokud je zaškrtnutý pouze checkbox zavazadlo
            if (Zavazadlo && !Vesak) {
                query = "SELECT * FROM Umisteni WHERE (typ_umisteni = 'podlaha' AND id_satny_fk = '1')";
            }
            // Pokud je zaškrtnutý pouze checkbox věšák
            else if (Vesak && !Zavazadlo) {
                query = "SELECT * FROM Umisteni WHERE typ_umisteni = 'věšák' AND id_satny_fk = '1'";
            }
            // Pokud jsou zaškrtnuty oba checkboxy
            else {
                query = "SELECT * FROM Umisteni WHERE id_satny_fk = '1'";
            }

            System.out.println("Executing query: " + query);
            ResultSet rs = stmt.executeQuery(query);
            // Přidání umístění do seznamu umístění
            while (rs.next()) {
                Umisteni umisteni = new Umisteni(rs.getInt("id_umisteni"), rs.getInt("cislo"), rs.getString("typ_umisteni"), rs.getString("student"), rs.getInt("id_satny_fk"));
                umisteniList.add(umisteni);
            }
            System.out.println("Found " + umisteniList.size() + " records");
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return umisteniList;
    }

    /**
     * Metoda pro aktualizaci obsazenosti umístění
     * @param ISIC ISIC studenta
     * @param cislo Číslo umístění
     * @param Zavazadlo Zda se jedná o zavazadlo
     * @param Vesak Zda se jedná o věšák
     */
    public static void updateUmisteni(String ISIC, int cislo, boolean Zavazadlo, boolean Vesak) {
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
            String queryVesak = "";
            String queryZavazadlo = "";
            // Pokud je zaškrtnutý pouze checkbox zavazadlo
            if (Vesak && !Zavazadlo) {
                queryVesak = "UPDATE Umisteni SET student = '" + ISIC +"' WHERE typ_umisteni = 'věšák' AND cislo = " + cislo;
                System.out.println("Executing query: " + queryVesak);
            }
            // Pokud je zaškrtnutý pouze checkbox věšák
            else if (Zavazadlo && !Vesak) {
                queryZavazadlo = "UPDATE Umisteni SET student = '" + ISIC + "' WHERE typ_umisteni = 'podlaha' AND cislo =" + cislo;
                System.out.println("Executing query: " + queryZavazadlo);
            }
            // Pokud jsou zaškrtnuty oba checkboxy
            else {
                queryVesak = "UPDATE Umisteni SET student = '" + ISIC +"' WHERE typ_umisteni = 'věšák' AND cislo = " + cislo;
                queryZavazadlo = "UPDATE Umisteni SET student = '" + ISIC + "' WHERE typ_umisteni = 'podlaha' AND cislo = " + cislo;
                System.out.println("Executing query: " + queryVesak);
                System.out.println("Executing query: " + queryZavazadlo);
            }

            // Aktualizace umístění pro věšák
            if (!queryVesak.isEmpty()) {
                int rowsAffected = stmt.executeUpdate(queryVesak);
                System.out.println("Updated " + rowsAffected + " rows");
            }

            // Aktualizace umístění pro zavazadlo
            if (!queryZavazadlo.isEmpty()) {
                int rowsAffected = stmt.executeUpdate(queryZavazadlo);
                System.out.println("Updated " + rowsAffected + " rows");
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
