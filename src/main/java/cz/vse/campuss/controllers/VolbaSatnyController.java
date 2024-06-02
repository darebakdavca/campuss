package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.SatnaSelection;
import cz.vse.campuss.helpers.StageManager;

import cz.vse.campuss.model.Satna;
import javafx.fxml.FXML;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;
import static cz.vse.campuss.helpers.NodeHelper.hideAfterSeconds;

public class VolbaSatnyController {

    @FXML
    public AnchorPane rootPane;
    @FXML
    public RadioButton italskaButton;
    @FXML
    public RadioButton novaButton;
    @FXML
    public Text errorText;
    @FXML
    public VBox ovladaciPrvky;

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        italskaButton.setToggleGroup(group);
        novaButton.setToggleGroup(group);
        fadeIn(ovladaciPrvky);
    }

    /**
     * Metoda pro pokračování na domovskou obrazovku dle a uložení aktivní šatny dle výběru
     */
    public void pokracovatKlik(MouseEvent mouseEvent) throws IOException {
        if (italskaButton.isSelected()) {
            Satna satna = DatabaseHelper.getSatnaFromName("Italská budova");
            SatnaSelection.getInstance().setSelectedSatna(satna);
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } else if (novaButton.isSelected()) {
            Satna satna = DatabaseHelper.getSatnaFromName("Nová budova");
            SatnaSelection.getInstance().setSelectedSatna(satna);
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } else {
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }
    }
}
