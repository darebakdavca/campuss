package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class VyzvednoutController extends BaseController {

    public HBox ovladaciPrvky;
    public HBox blokInformaci;

    @FXML
    private void initialize() {
        fadeIn(ovladaciPrvky);
        fadeIn(blokInformaci);
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
    public void potvrditVyzvednutiKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/potvrzeni.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void zrusitKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
