package cz.vse.campuss.main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontrolér pro obrazovku potrvzeni.fxml
 */
public class PotrvzeniController extends BaseController {
    // Stage
    private Stage stage;

    // FXML elementy
    public VBox hlavniPrvky;
    public Text potrvzeniText;

    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        potrvzeniText.setText("Uschování proběhlo úspěšně!");
        fadeIn(hlavniPrvky);
        Platform.runLater(() -> stage = (Stage) potrvzeniText.getScene().getWindow());
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     */
    @FXML
    public void domuKlik() {
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zobrazení obrazovky pro uschování
     */
    @FXML
    public void uschovatKlik() {
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat1.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
