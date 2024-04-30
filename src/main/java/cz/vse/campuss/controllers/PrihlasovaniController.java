package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.hideAfterSeconds;

public class PrihlasovaniController extends BaseController {

    @FXML
    public Text errorText;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public RadioButton rButtonRole2;
    @FXML
    public RadioButton rButtonRole1;

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        rButtonRole1.setToggleGroup(group);
        rButtonRole2.setToggleGroup(group);
    }

    @FXML
    public void prihlaseniKlik(MouseEvent mouseEvent) throws IOException {

        if (rButtonRole2.isSelected()) {
            StageManager.switchFXML(rootPane, FXMLView.STUDENT);
        } else if (rButtonRole1.isSelected()) {
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } else {
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }
    }
}
