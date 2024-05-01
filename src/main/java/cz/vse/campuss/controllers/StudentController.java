package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentController {
    @FXML
    public TextField satnaOut;
    @FXML
    public TextField obleceniOut;
    @FXML
    public TextField zavazadlaOut;
    @FXML
    public TextField timeOut;
    @FXML
    public TextField closingOut;
    @FXML
    public AnchorPane rootPane;

    @FXML
    private void initialize() {
        satnaOut.setText("loremsatna");
        obleceniOut.setText("loremobleceni");
        zavazadlaOut.setText("loremzavazadla");
        timeOut.setText("loremtime");
        closingOut.setText("loremclosing");
    }

    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }
}
