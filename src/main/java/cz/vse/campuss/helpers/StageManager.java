package cz.vse.campuss.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Třída pro správu stage a zobrazování scén v něm
 */
public class StageManager {
    private static Stage primaryStage;

    /**
     * Zobrazí scénu v primárním stage
     * @param view FXMLView (Obrazovka), která se má zobrazit
     */
    public static void switchScene(final FXMLView view) throws IOException {
        String title = view.getTitle();
        Scene scene = getScene(view.getFXMLResource());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Vrátí scénu z FXML souboru
     * @param fxmlFile Cesta k FXML souboru
     * @return Scéna
     */
    private static Scene getScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL(fxmlFile));
        return new Scene(fxmlLoader.load());
    }

    /**
     * Nastaví primární stage
     * @param primaryStage Stage, který se má nastavit jako primární
     */
    public static void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
        StageManager.primaryStage.setScene(primaryStage.getScene());
    }
}
