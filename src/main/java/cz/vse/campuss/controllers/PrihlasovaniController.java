package cz.vse.campuss.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    public void prihlaseniKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        if (rButtonRole2.isSelected()) {
            // Load the student.fxml file
            try {
                showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/student.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (rButtonRole1.isSelected()) {
            // Load the home.fxml file
            try {
                showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }
    }
}
