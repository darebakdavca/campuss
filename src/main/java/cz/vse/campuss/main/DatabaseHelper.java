package cz.vse.campuss.main;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.Umisteni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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


    public static ArrayList<Umisteni> fetchAllUmisteni(boolean Zavazadlo, boolean Vesak) {
        ArrayList<Umisteni> umisteniList = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String query;
            if (Zavazadlo && !Vesak) {
                query = "SELECT * FROM Umisteni WHERE (typ_umisteni = 'podlaha' AND id_satny_fk = '1')";
            } else if (Vesak && !Zavazadlo) {
                query = "SELECT * FROM Umisteni WHERE typ_umisteni = 'věšák' AND id_satny_fk = '1'";
            } else {
                query = "SELECT * FROM Umisteni WHERE id_satny_fk = '1'";
            }
            System.out.println("Executing query: " + query);
            ResultSet rs = stmt.executeQuery(query);
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

    public static void updateUmisteni(String ISIC, int cislo, boolean Zavazadlo, boolean Vesak) {
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
            String queryVesak = "";
            String queryZavazadlo = "";
            if (Vesak && !Zavazadlo) {
                queryVesak = "UPDATE Umisteni SET student = '" + ISIC +"' WHERE typ_umisteni = 'věšák' AND cislo = " + cislo;
                System.out.println("Executing query: " + queryVesak);
            } else if (Zavazadlo && !Vesak) {
                queryZavazadlo = "UPDATE Umisteni SET student = '" + ISIC + "' WHERE typ_umisteni = 'podlaha' AND cislo =" + cislo;
                System.out.println("Executing query: " + queryZavazadlo);
            } else {
                queryVesak = "UPDATE Umisteni SET student = '" + ISIC +"' WHERE typ_umisteni = 'věšák' AND cislo = " + cislo;
                queryZavazadlo = "UPDATE Umisteni SET student = '" + ISIC + "' WHERE typ_umisteni = 'podlaha' AND cislo = " + cislo;
                System.out.println("Executing query: " + queryVesak);
                System.out.println("Executing query: " + queryZavazadlo);
            }

            if (!queryVesak.isEmpty()) {
                int rowsAffected = stmt.executeUpdate(queryVesak);
                System.out.println("Updated " + rowsAffected + " rows");
            }

            if (!queryZavazadlo.isEmpty()) {
                int rowsAffected = stmt.executeUpdate(queryZavazadlo);
                System.out.println("Updated " + rowsAffected + " rows");
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
