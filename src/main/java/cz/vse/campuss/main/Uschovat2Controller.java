package cz.vse.campuss.main;

import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.Umisteni;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Kontrolér pro obrazovku uschovat2.fxml
 */
public class Uschovat2Controller extends BaseController {
    // Stage a stavy studenta
    private Stage stage;
    private StudentState studentState;
    // Stav checkboxů
    private CheckBoxState checkBoxState;
    private UserDataContainer userDataContainer;
    // List umístění
    private ArrayList<Umisteni> umisteni = new ArrayList<>();

    // FXML elementy
    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox oblastBoxy;
    public Line deliciCara;
    public HBox ovladaciPrvky;
    public TextField vystupCisloZavazadlo;
    public TextField vystupCisloObleceni;

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
        Platform.runLater(() -> {
            stage = (Stage) ovladaciPrvky.getScene().getWindow();
            this.userDataContainer = (UserDataContainer) stage.getUserData();
        });
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic na základě dat z předchozí obrazovky
     * @param userDataContainer Data z předchozí obrazovky
     */
    public void initData(UserDataContainer userDataContainer) {
        // získání stavu checkboxů a studenta
        this.checkBoxState = userDataContainer.getCheckBoxState();
        this.studentState = userDataContainer.getStudentState();
        // získání umístění
        getUmisteni();
        // zobrazení boxů na základě stavu checkboxů
        // pokud jsou oba checkboxy zaškrtnuty, zobrazí se oba boxy
        if (checkBoxState.obleceniChecked && checkBoxState.zavazadloChecked) {
            vystupCisloObleceni.setText(String.valueOf(getVolnyVesak()));
            vystupCisloZavazadlo.setText(String.valueOf(getVolnyPodlaha()));
            fadeIn(deliciCara);
            fadeIn(boxZavazadlo);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox oblečení, zobrazí se pouze box pro oblečení
        else if (checkBoxState.obleceniChecked) {
            vystupCisloObleceni.setText(String.valueOf(getVolnyVesak()));
            oblastBoxy.getChildren().remove(boxZavazadlo);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox zavazadlo, zobrazí se pouze box pro zavazadlo
        else if (checkBoxState.zavazadloChecked){
            vystupCisloZavazadlo.setText(String.valueOf(getVolnyPodlaha()));
            oblastBoxy.getChildren().remove(boxVesak);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxZavazadlo);
        }
    }

    /**
     * Metoda pro zobrazení obrazovky potvrzení.fxml o uložení věcí do databáze
     */
    @FXML
    public void potvrditUschovaniKlik() {
        Student student = studentState.student;
        // Uložení věcí do databáze když jsou zaškrtnuty oba checkboxy
        if (checkBoxState.obleceniChecked && checkBoxState.zavazadloChecked) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloObleceni.getText()), false, true);
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloZavazadlo.getText()), true, false);

        }
        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox oblečení
        else if (checkBoxState.obleceniChecked) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloObleceni.getText()), false, true);

        }
        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox zavazadlo
        else if (checkBoxState.zavazadloChecked) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloZavazadlo.getText()), true, false);
        }
        try {
            // Load the potvrzeni.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/potvrzeni.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zrušení uložení věcí
     * Vrátí na obrazovku uschovat1.fxml
     */
    @FXML
    public void zrusitKlik() {
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat1.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * Vrátí na obrazovku home.fxml
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
     * Metoda pro získání umístění věcí
     */
    private void getUmisteni() {
        umisteni = DatabaseHelper.fetchAllUmisteni(checkBoxState.zavazadloChecked, checkBoxState.obleceniChecked);
    }

    /**
     * Metoda pro získání volného vesaku
     * @return Číslo volného vesaku
     */
    private int getVolnyVesak() {
        int vesakCislo = 0;

        for (Umisteni misto : umisteni) {
            if (misto.getTypUmisteni().equals("věšák") && misto.getStudent() == null) {
                vesakCislo = misto.getCislo();
                break;
            }
        }
        return vesakCislo;
    }

    /**
     * Metoda pro získání volného místa na podlaze
     * @return Číslo volného místa na podlaze
     */
    private int getVolnyPodlaha() {
        int podlahaCislo = 0;

        for (Umisteni misto : umisteni) {
            if (misto.getTypUmisteni().equals("podlaha") && misto.getStudent() == null) {
                podlahaCislo = misto.getCislo();
                break;
            }
        }
        return podlahaCislo;
    }
}
