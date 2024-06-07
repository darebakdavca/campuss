package cz.vse.campuss.helpers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Třída pro práci s Node, má dvě metody pro zobrazení a skrytí Node
 */
public class NodeHelper {

    /**
     * Metoda pro zobrazení animace fadeIn pro vybraný node
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
     * Metoda pro zobrazení animace fadeIn a fadeOut pro vybraný node
     * @param node Node, který se má skrýt
     */
    static public void hideAfterSeconds(Node node) {
        // Vytvoření FadeTransition pro fade-in efekt
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Vytvoření PauseTransition po fade-in efektu než se spustí fade-out efekt
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(1));

        // Když je fade-in efekt hotový, spusť fade-out efekt
        fadeIn.setOnFinished(event -> visiblePause.play());

        // Vytvoření FadeTransition pro fade-out efekt
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // když je visiblePause hotová, spusť fade-out efekt
        visiblePause.setOnFinished(event ->
                fadeOut.play()
        );
    }
}
