package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

/**
 * Kontrolér pro obrazovku potrvzeni.fxml
 */
public class PotrvzeniController {
    // FXML elementy
    public VBox hlavniPrvky;
    public Text potrvzeniText;
    public Button button;

    public static String text;
    public static String textButton;
    public AnchorPane rootPane;

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
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }

    /**
     * Metoda pro zobrazení obrazovky pro uschování
     */
    @FXML
    public void uschovatKlik() throws IOException {
        if(potrvzeniText.getText().equals("Uschování proběhlo úspěšně!")) {
            StageManager.switchFXML(rootPane, FXMLView.USCHOVAT1);
        }
        else {
            StageManager.switchFXML(rootPane, FXMLView.VYZVEDNOUT);
        }
    }
}
