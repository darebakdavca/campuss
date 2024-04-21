package cz.vse.campuss.main;

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
}
