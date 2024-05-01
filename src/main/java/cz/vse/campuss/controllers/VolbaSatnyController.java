package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
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
        fadeIn(ovladaciPrvky);
    }

//TODO: umožnit předávání informace o výběru budovy šatny do dalších obrazovek

    public void pokracovatKlik(MouseEvent mouseEvent) throws IOException {
        if (italskaButton.isSelected()) {
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } else if (novaButton.isSelected()) {
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } else {
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }
    }
}
