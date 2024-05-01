package cz.vse.campuss.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Abstraktní třída pro všechny kontrolery která obsahuje metody společné pro všechny kontrolery obrazovek.
 */
public abstract class BaseController {
    Stage stage;


    /**
     * Metoda pro zobrazení nové scény.
     * @param stage Stage, ve kterém se má scéna zobrazit
     * @param fxmlPath Cesta k FXML souboru, který se má zobrazit
     * @throws IOException Pokud se nepodaří načíst FXML soubor
     */
    protected void showScene(Stage stage, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(fxmlPath));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Campuss");
        stage.show();
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }
}