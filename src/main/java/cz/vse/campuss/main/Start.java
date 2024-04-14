package cz.vse.campuss.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        showScene(primaryStage, "file:src/main/resources/cz/vse/campuss/main/fxml/Vyzvednout.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

    private void showScene(Stage stage, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(fxmlPath));
        Scene scene = new Scene(loader.load());
        stage.setMaximized(true);
        stage.setScene(scene);

        stage.setTitle("Campuss");
        stage.show();
    }
}