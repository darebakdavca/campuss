package cz.vse.campuss.controllers;

import java.io.IOException;

import cz.vse.campuss.helpers.*;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import cz.vse.campuss.model.Umisteni;
import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

/**
 * Kontrolér pro obrazovku uschovat2.fxml
 */
public class Uschovat2Controller extends BaseController {
    // Stage a stavy studenta
    private Stage stage;
    // Stav checkboxů
    private UserDataContainer userDataContainer;

    // FXML elementy
    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox oblastBoxy;
    public Line deliciCara;
    public HBox ovladaciPrvky;
    public TextField vystupCisloPodlaha;
    public TextField vystupCisloVesaku;

    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        boxZavazadlo.setVisible(false);
        boxVesak.setVisible(false);
        deliciCara.setVisible(false);
        fadeIn(ovladaciPrvky);
        // získání stage a stavu studenta
        Platform.runLater(() -> stage = (Stage) ovladaciPrvky.getScene().getWindow());
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic na základě dat z předchozí obrazovky
     * @param userDataContainer Data z předchozí obrazovky
     */
    public void initData(UserDataContainer userDataContainer) {
        this.userDataContainer = userDataContainer;
        // získání volného vesaku a podlahy
        Umisteni umisteniVesak = DatabaseHelper.fetchUnusedUmisteni(TypUmisteni.VESAK);
        Umisteni umisteniPodlaha = DatabaseHelper.fetchUnusedUmisteni(TypUmisteni.PODLAHA);

        // zobrazení boxů na základě stavu checkboxů
        // pokud jsou oba checkboxy zaškrtnuty, zobrazí se oba boxy
        if (userDataContainer.isPodlahaChecked() && userDataContainer.isVesakChecked()) {
            vystupCisloVesaku.setText(String.valueOf(umisteniVesak.getCislo()));
            vystupCisloPodlaha.setText(String.valueOf(umisteniPodlaha.getCislo()));
            fadeIn(deliciCara);
            fadeIn(boxZavazadlo);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox oblečení, zobrazí se pouze box pro oblečení
        else if (userDataContainer.isVesakChecked()) {
            vystupCisloVesaku.setText(String.valueOf(umisteniVesak.getCislo()));
            oblastBoxy.getChildren().remove(boxZavazadlo);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox zavazadlo, zobrazí se pouze box pro zavazadlo
        else if (userDataContainer.isPodlahaChecked()) {
            vystupCisloPodlaha.setText(String.valueOf(umisteniPodlaha.getCislo()));
            oblastBoxy.getChildren().remove(boxVesak);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxZavazadlo);
        }
    }

    /**
     * Metoda pro zobrazení obrazovky potvrzení.fxml o uložení věcí do databáze
     */
    @FXML
    public void potvrditUschovaniKlik() throws IOException {
        Student student = userDataContainer.getStudent();
        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox oblečení
        if (userDataContainer.isVesakChecked()) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloVesaku.getText()), TypUmisteni.VESAK);
        }
        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox zavazadlo
        if (userDataContainer.isPodlahaChecked()) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloPodlaha.getText()), TypUmisteni.PODLAHA);
        }
        StageManager.switchScene(FXMLView.POTVRZENI);
    }

    /**
     * Metoda pro zrušení uložení věcí
     * Vrátí na obrazovku uschovat1.fxml
     */
    @FXML
    public void zrusitKlik() throws IOException {
        StageManager.switchScene(FXMLView.USCHOVAT1);
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * Vrátí na obrazovku home.fxml
     */
    @FXML
    public void domuKlik() throws IOException {
        StageManager.switchScene(FXMLView.HOME);
    }
}
