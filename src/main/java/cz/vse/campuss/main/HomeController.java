package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HomeController {
    @FXML
    private Text uvitani;

    @FXML
    private ImageView historie;


    @FXML
    private void initialize() {
        String username = DatabaseHelper.fetchUserNameSatnarka(1);
        uvitani.setText("Vítejte " + username + "!");
    }

    @FXML
    private void historieKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        try {
            // Load the home.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/historie.fxml");

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