package cz.vse.campuss.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentController extends BaseController {


    public TextField satnaOut;
    public TextField obleceniOut;
    public TextField zavazadlaOut;
    public TextField timeOut;
    public TextField closingOut;

    @FXML
    private void initialize() {
        satnaOut.setText("loremsatna");
        obleceniOut.setText("loremobleceni");
        zavazadlaOut.setText("loremzavazadla");
        timeOut.setText("loremtime");
        closingOut.setText("loremclosing");
    }

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
}
