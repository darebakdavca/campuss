package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.model.PolozkaHistorie;
import cz.vse.campuss.model.StavUlozeni;
import cz.vse.campuss.model.TypUmisteni;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Kontroler pro obrazovku historie1.fxml
 */
public class HistorieController extends BaseController {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchSubmitButton;
    @FXML
    private TableView historieTable;
    @FXML
    private TableColumn jmenoColumn;
    @FXML
    private TableColumn prijmeniColumn;
    @FXML
    private TableColumn isicColumn;
    @FXML
    private TableColumn satnaColumn;
    @FXML
    private TableColumn typColumn;
    @FXML
    private TableColumn cisloColumn;
    @FXML
    private TableColumn stavColumn;
    @FXML
    private TableColumn casZmenyColumn;


    /**
     * Po přepnutí na obrazovku historie se nastaví továrny a aktualizuje se tabulka
     */
    @FXML
    private void initialize() {
        nastavitTovarny();
        aktualizujHistorii();
    }

    /**
     * Nastavení továren pro jednotlivé sloupce v tabulce
     */
    private void nastavitTovarny() {
        jmenoColumn.setCellValueFactory(new PropertyValueFactory<>("jmenoStudenta"));
        prijmeniColumn.setCellValueFactory(new PropertyValueFactory<>("prijmeniStudenta"));
        isicColumn.setCellValueFactory(new PropertyValueFactory<>("isicStudenta"));
        satnaColumn.setCellValueFactory(new PropertyValueFactory<>("satnaNazev"));
        typColumn.setCellValueFactory(new PropertyValueFactory<>("umisteniTyp"));
        cisloColumn.setCellValueFactory(new PropertyValueFactory<>("umisteniCislo"));
        stavColumn.setCellValueFactory(new PropertyValueFactory<>("stav"));
        casZmenyColumn.setCellValueFactory(new PropertyValueFactory<>("casZmenyStavu"));
    }

    /**
     * Získá data z databáze a nastaví je do tabulky
     */
    private void aktualizujHistorii() {
        ObservableList<PolozkaHistorie> data = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Historie");
            try (ResultSet rs = pstmt.executeQuery()) {
                fetchHistorieData(rs, data);
                historieTable.setItems(data);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());;
        }
    }

    /**
     * Po kliknutí na "Odeslat" se vyfiltreují záznamy v tabulce podle zadaného ISICU,
     * pokud je pole prázdné, zobrazí se všechny záznamy
     *
     * @param mouseEvent The MouseEvent triggering the searchSubmitButtonKlik method.
     */
    @FXML
    private void searchSubmitButtonKlik (MouseEvent mouseEvent){
        String searchText = searchField.getText();
        try {
            Connection conn = DatabaseHelper.getConnection();
            String query = searchText.isEmpty() ? "SELECT * FROM Historie" :
                    "SELECT * FROM Historie WHERE isic_studenta = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            if (!searchText.isEmpty()) {
                pstmt.setString(1, searchText);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                ObservableList<PolozkaHistorie> data = FXCollections.observableArrayList();
                fetchHistorieData(rs, data);
                historieTable.setItems(data);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Přejde na domovskou obrazovku
     */
    @FXML
    public void domuKlik() throws IOException {
        StageManager.switchScene(FXMLView.HOME);
    }

    /**
     * Získaná data z databáze převede na vhodný formát a vloží je do ObservableListu
     */
    private void fetchHistorieData(ResultSet rs, ObservableList<PolozkaHistorie> data) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (rs.next()) {
            data.add(new PolozkaHistorie(rs.getInt("id"), rs.getString("jmeno_studenta"), rs.getString("prijmeni_studenta"), rs.getString("isic_studenta"), rs.getString("satna_nazev"), TypUmisteni.fromString(rs.getString("umisteni_typ")), rs.getInt("umisteni_cislo"), StavUlozeni.fromString(rs.getString("stav")), rs.getTimestamp("cas_zmeny_stavu").toLocalDateTime().format(formatter), rs.getInt("satnarka_id")));
        }
    }
}
