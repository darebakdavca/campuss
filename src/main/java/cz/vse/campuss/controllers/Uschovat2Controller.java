package cz.vse.campuss.controllers;

import java.io.IOException;

import cz.vse.campuss.helpers.*;
import cz.vse.campuss.model.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

/**
 * Kontrolér pro obrazovku uschovat2.fxml
 */
public class Uschovat2Controller {

    // Stav checkboxů
    private UserDataContainer userDataContainer = UserDataContainer.getInstance();

    // FXML elementy
    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox oblastBoxy;
    public Line deliciCara;
    public HBox ovladaciPrvky;
    public TextField vystupCisloPodlaha;
    public TextField vystupCisloVesaku;
    public AnchorPane rootPane;
    public VBox hlavniPrvky;
    public ProgressIndicator progressIndicator;
    public HBox oblastProgress;

    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        // skrytí nepotřebných prvků
        boxZavazadlo.setVisible(false);
        boxVesak.setVisible(false);
        deliciCara.setVisible(false);
        // zobrazení ovládacích prvků
        fadeIn(ovladaciPrvky);
        // skrytí progress baru
        rootPane.getChildren().remove(oblastProgress);
        // získání dat z předchozí obrazovky
        initData() ;
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic na základě dat z předchozí obrazovky
     */
    public void initData() {
        Satna satna = SatnaSelection.getInstance().getSelectedSatna();

        // získání volného vesaku a podlahy
        Umisteni umisteniVesak = DatabaseHelper.fetchUnusedUmisteni(TypUmisteni.VESAK, satna);
        Umisteni umisteniPodlaha = DatabaseHelper.fetchUnusedUmisteni(TypUmisteni.PODLAHA, satna);

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
    public void potvrditUschovaniKlik() {
        // odebrání starých prvků a přidání progress baru
        hlavniPrvky.getChildren().remove(oblastBoxy);
        hlavniPrvky.getChildren().remove(ovladaciPrvky);
        rootPane.getChildren().add(oblastProgress);

        // získání studenta
        Student student = userDataContainer.getStudent();

        // získání vybrané šatny
        Satna satna = SatnaSelection.getInstance().getSelectedSatna();

        // ID vesaku a podlahy
        int idVesak = -1;
        int idPodlaha = -1;

        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox oblečení
        if (userDataContainer.isVesakChecked()) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            // aktualizace umístění v databázi - přiřazení isic k číslu umístění
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloVesaku.getText()), TypUmisteni.VESAK, satna.getId());
            // vytvoření záznamu v historii a vrácení čísla id
            idVesak = DatabaseHelper.createHistorieEntry(student.getJmeno(), student.getPrijmeni(), student.getIsic(), TypUmisteni.VESAK, Integer.parseInt(vystupCisloVesaku.getText()), StavUlozeni.USCHOVANO, satna);
        }

        // Uložení věcí do databáze když je zaškrtnutý pouze checkbox zavazadlo
        if (userDataContainer.isPodlahaChecked()) {
            if (student == null) {
                // handle the case where the student is null
                System.out.println("No student found");
                return;
            }
            // aktualizace umístění v databázi - přiřazení isic k číslu umístění
            DatabaseHelper.updateUmisteni(student.getIsic(), Integer.parseInt(vystupCisloPodlaha.getText()), TypUmisteni.PODLAHA, satna.getId());
            // vytvoření záznamu v historii a vrácení čísla id
            idPodlaha = DatabaseHelper.createHistorieEntry(student.getJmeno(), student.getPrijmeni(), student.getIsic(), TypUmisteni.PODLAHA, Integer.parseInt(vystupCisloPodlaha.getText()), StavUlozeni.USCHOVANO, satna);
        }

        // získání tasku pro odeslání emailu
        Task<Void> task = getOdesliEmailTask(idVesak, idPodlaha, student);

        // spuštění tasku v novém threadu (neblokující tak hlavní Thread kde běží JavaFX a UI)
        new Thread(task).start();
    }

    /**
     * Metoda pro vytvoření tasku odeslání emailu s potvrzením o uložení věcí
     * @param idVesak ID vesaku
     * @param idPodlaha ID podlahy
     * @param student Student
     * @return Task pro odeslání emailu
     */
    private Task<Void> getOdesliEmailTask(int idVesak, int idPodlaha, Student student) {

        // vytvoření tasku pro odeslání emailu
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MailHelper.sendEmail(student.getEmail(), "Uschování věcí", "src/main/resources/templates/potvrzeni_uschovani.html", MailHelper.getUschovaniInfo(idVesak, idPodlaha));
                return null;
            }
        };

        // nastavení co se má stát po úspěšném dokončení tasku
        task.setOnSucceeded(e -> {
            PotrvzeniController.text = "Uschování proběhlo úspěšně!";
            PotrvzeniController.textButton = "Uschovat další věc";
            // přepnutí na obrazovku potvrzení.fxml
            try {
                StageManager.switchFXML(rootPane, FXMLView.POTVRZENI);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // nastavení co se má stát po neúspěšném dokončení tasku
        task.setOnFailed(e -> System.out.println("Odeslání emailu se nezdařilo: " + e.getSource().getException().getMessage()));

        return task;
    }

    /**
     * Metoda pro zrušení uložení věcí
     * Vrátí na obrazovku uschovat1.fxml
     */
    @FXML
    public void zrusitKlik() throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.USCHOVAT1);
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * Vrátí na obrazovku home.fxml
     */
    @FXML
    public void domuKlik() throws IOException {
        userDataContainer.setStudent(null);
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }
}
