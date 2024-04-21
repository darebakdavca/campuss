package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PotrvzeniController extends BaseController {

    public TextField potrvzeniText;

    @FXML
    private void initialize() {

        potrvzeniText.setText("Potvrzení");

    }

    @FXML
    public void domuKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
