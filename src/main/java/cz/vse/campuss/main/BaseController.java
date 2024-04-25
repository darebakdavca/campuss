package cz.vse.campuss.main;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

/**
 * Abstraktní třída pro všechny kontrolery která obsahuje metody společné pro všechny kontrolery obrazovek.
 */
public abstract class BaseController {

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

    /**
     * metoda pro zobrazení animace fadeIn pro vybraný node
     * @param node Node, který se má zobrazit
     */
    protected void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        node.setVisible(true);
    }

    protected void hideAfterSeconds(Node node) {
        // Create a FadeTransition for the fade-in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Create a PauseTransition to keep the error message visible for a few seconds
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));

        // When the fade-in effect is over, start the PauseTransition
        fadeIn.setOnFinished(event -> visiblePause.play());

        // Create a FadeTransition for the fade-out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // When the PauseTransition is over, play the fade-out effect
        visiblePause.setOnFinished(event ->
            fadeOut.play()
        );
    }
}