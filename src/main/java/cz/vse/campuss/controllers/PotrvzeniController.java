package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

/**
 * Kontrolér pro obrazovku potrvzeni.fxml
 */
public class PotrvzeniController extends BaseController {
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
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     */
    @FXML
    public void domuKlik() throws IOException {
        StageManager.switchScene(FXMLView.HOME);
    }

    /**
     * Metoda pro zobrazení obrazovky pro uschování
     */
    @FXML
    public void uschovatKlik() throws IOException {
        StageManager.switchScene(FXMLView.USCHOVAT1);
    }
}
