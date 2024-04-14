package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PrihlasovaniController {
    @FXML
    private void initialize() {
    }

    @FXML
    public void prihlaseniKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        try {
            // Load the home.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showScene(Stage stage, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(fxmlPath));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Campuss");
        stage.show();
    }
}
