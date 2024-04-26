package cz.vse.campuss;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Campuss extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        StageManager.setPrimaryStage(primaryStage);
        StageManager.switchScene(FXMLView.PRIHLASOVANI);
    }

    public static void main(String[] args) {
        launch();
    }
}