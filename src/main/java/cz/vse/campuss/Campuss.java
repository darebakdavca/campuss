package cz.vse.campuss;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Hlavní třída aplikace, která se stará pouze o spuštění aplikace a nastavení primární stage.

 */
public class Campuss extends Application {

    /**
     * Spustí aplikaci a nastaví primární stage
     * Také zobrazí první scénu pro přihlášení
     * @param primaryStage Primární stage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        StageManager.setPrimaryStage(primaryStage);
        StageManager.switchScene(FXMLView.PRIHLASOVANI);
    }

    public static void main(String[] args) {
        launch();
    }
}