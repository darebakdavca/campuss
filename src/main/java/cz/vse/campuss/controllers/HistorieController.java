package cz.vse.campuss.controllers;

import java.io.IOException;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.NodeHelper;
import cz.vse.campuss.helpers.StageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * Kontroler pro obrazovku historie1.fxml
 */
public class HistorieController {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public AnchorPane hlavniPrvky;
    @FXML
    private TextField vstupISIC;
    @FXML
    private Button tlacitkoOdeslat;
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
     * Po přepnutí na obrazovku historie se nastaví továrny, aktualizuje se tabulka a záznamy se seřadí chronologicky
     */
    @FXML
    private void initialize() {
        vstupISIC.setOnAction(this::searchSubmitButtonKlik);
        NodeHelper.fadeIn(hlavniPrvky);
        nastavitTovarny();
        historieTable.setItems(DatabaseHelper.fetchHistorieAll(null));

        casZmenyColumn.setSortType(TableColumn.SortType.DESCENDING);
        historieTable.getSortOrder().add(casZmenyColumn);
        historieTable.sort();
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
     * Po kliknutí na "Odeslat" se vyfiltreují záznamy v tabulce podle zadaného ISICU,
     * pokud je pole prázdné, zobrazí se všechny záznamy
     *
     * @param actionEvent The MouseEvent triggering the searchSubmitButtonKlik method.
     */
    @FXML
    private void searchSubmitButtonKlik (ActionEvent actionEvent){
        String searchText = vstupISIC.getText();
        historieTable.setItems(DatabaseHelper.fetchHistorieAll(searchText));
    }

    /**
     * Přejde na domovskou obrazovku
     */
    @FXML
    public void domuKlik() throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }
}
