package cz.vse.campuss.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
        Scene scene = getScene(view.getFXMLResource());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Campuss");
        primaryStage.show();
    }


    /**
     * Zobrazí FXML soubor v primárním stage
     * @param rootPane AnchorPane, do kterého se má zobrazit FXML soubor
     * @param fxmlView FXMLView (Obrazovka), která se má zobrazit
     */
    public static void switchFXML(AnchorPane rootPane, final FXMLView fxmlView) throws IOException {
        AnchorPane view = getAnchorPane(fxmlView.getFXMLResource());
        rootPane.getChildren().setAll(view);
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
     * Vrátí AnchorPane z FXML souboru
     * @param fxmlFile Cesta k FXML souboru
     * @return AnchorPane
     */
    public static AnchorPane getAnchorPane(String fxmlFile) throws IOException {
        return FXMLLoader.load(new URL(fxmlFile));
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
