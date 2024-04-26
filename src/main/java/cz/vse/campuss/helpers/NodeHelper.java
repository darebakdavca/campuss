package cz.vse.campuss.helpers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Třída pro práci s Node
 */
public class NodeHelper {

    /**
     * metoda pro zobrazení animace fadeIn pro vybraný node
     * @param node Node, který se má zobrazit
     */
    static public void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        node.setVisible(true);
    }

    /**
     * metoda pro zobrazení animace fadeOut pro vybraný node
     * @param node Node, který se má skrýt
     */
    static public void hideAfterSeconds(Node node) {
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
