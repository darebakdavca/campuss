package cz.vse.campuss.main;

import cz.vse.campuss.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Uschovat1Controller extends BaseController {

    public TextField TextInput;

    @FXML
    private void initialize() {
        TextInput.setOnAction(this::odeslatISIC);
    }


    @FXML
    public void domuKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        try {
            // Load the home.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void zobrazitPoziceKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat2.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void odeslatISIC(ActionEvent actionEvent) {
        Student student = DatabaseHelper.fetchStudentByISIC(TextInput.getText());
    }
}
