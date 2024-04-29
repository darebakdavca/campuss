package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    public Button button;

    public static String text = "Uschování proběhlo úspěšně!";
    public static String textButton = "Uschovat další věc";
    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        potrvzeniText.setText(text);
        button.setText(textButton);
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
        if(potrvzeniText.getText().equals("Uschování proběhlo úspěšně!")) {
        StageManager.switchScene(FXMLView.USCHOVAT1);}
        else {
            StageManager.switchScene(FXMLView.VYZVEDNOUT);
        }
    }
}
