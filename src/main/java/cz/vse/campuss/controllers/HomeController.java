package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.NodeHelper;
import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.model.Satnarka;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Třída reprezentující controller pro home.fxml
 */
public class HomeController extends BaseController {

    public HBox volbyCinnosti;
    public AnchorPane rootPane;
    @FXML
    private Text uvitani;

    /**
     * Metoda pro inicializaci controlleru
     */
    @FXML
    private void initialize() {
        Satnarka satnarka = DatabaseHelper.fetchSatnarka(1);
        uvitani.setText("Vítáme uživatele " + satnarka.getJmeno() + "!");
        NodeHelper.fadeIn(volbyCinnosti);
    }

    /**
     * Metoda pro přepnutí na scénu s historií
     * @param mouseEvent Event kliknutí myší
     * @throws IOException Výjimka při chybě při načítání scény
     */
    @FXML
    public void historieKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.HISTORIE);
    }

    /**
     * Metoda pro přepnutí na scénu s výběrem šatny
     * @param mouseEvent Event kliknutí myší
     * @throws IOException Výjimka při chybě při načítání scény
     */
    @FXML
    public void uschovatKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.USCHOVAT1);
    }

    /**
     * Metoda pro přepnutí na scénu s výběrem šatny
     * @param mouseEvent Event kliknutí myší
     * @throws IOException Výjimka při chybě při načítání scény
     */
    @FXML
    public void vyzvednoutKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.VYZVEDNOUT);
    }
}