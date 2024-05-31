package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.*;
import cz.vse.campuss.model.StavUlozeni;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

public class Vyzvednout2Controller {


    private Student student;
    private Stage stage;

    public AnchorPane rootPane;
    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox ovladaciPrvky;
    public HBox blokInformaci;
    public TextField cisloVesak;
    public TextField cisloPodlaha;
    public Text studentJmeno;
    public ImageView studentFoto;
    public Button tlacitkoPotvrdit;
    public HBox zadavaniInformaci;
    public VBox hlavniPrvky;
    public HBox oblastProgress;
    public ProgressIndicator progressIndicator;
    public HBox oblastTextPotvrzeni;
    public Line deliciCara;

    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        boxZavazadlo.setVisible(false);
        boxVesak.setVisible(false);
        studentFoto.setImage(null);
        fadeIn(ovladaciPrvky);
        fadeIn(blokInformaci);
        Platform.runLater(() -> stage = (Stage) ovladaciPrvky.getScene().getWindow());
        rootPane.getChildren().remove(oblastProgress);
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic na základě dat z předchozí obrazovky
     * @param userDataContainer Data z předchozí obrazovky
     */
    public void initData(UserDataContainer userDataContainer) {
        student = userDataContainer.getStudent();
        // získání volného vesaku a podlahy
        int umisteniVesak = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.VESAK);
        int umisteniPodlaha = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.PODLAHA);

        // zobrazení informací o studentovi
        studentJmeno.setText(student.getJmeno() + " " + student.getPrijmeni());
        Image studentImage = new Image("file:src/main/resources/cz/vse/campuss/main/fxml/fotky/" + student.getIsic() + ".png");
        studentFoto.setImage(studentImage);
        fadeIn(studentFoto);
        fadeIn(deliciCara);

        // zobrazení boxů na základě stavu checkboxů
        // pokud jsou oba checkboxy zaškrtnuty, zobrazí se oba boxy
        if (userDataContainer.isPodlahaChecked() && userDataContainer.isVesakChecked()) {
            cisloVesak.setText(String.valueOf(umisteniVesak));
            cisloPodlaha.setText(String.valueOf(umisteniPodlaha));
            fadeIn(boxZavazadlo);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox oblečení, zobrazí se pouze box pro oblečení
        else if (userDataContainer.isVesakChecked()) {
            cisloVesak.setText(String.valueOf(umisteniVesak));
            blokInformaci.getChildren().remove(boxZavazadlo);
            fadeIn(boxVesak);
        }
        // pokud je zaškrtnutý pouze checkbox zavazadlo, zobrazí se pouze box pro zavazadlo
        else if (userDataContainer.isPodlahaChecked()) {
            cisloPodlaha.setText(String.valueOf(umisteniPodlaha));
            blokInformaci.getChildren().remove(boxVesak);
            fadeIn(boxZavazadlo);
        }
    }


    /**
     * Vytvoří záznam o vyzvednutí v historii a potvrdí vyzvednutí věcí
     */
    @FXML
    public void potvrditVyzvednutiKlik(MouseEvent mouseEvent) {
        // odebrání starých prvků a přidání progress prvku
        hlavniPrvky.getChildren().remove(blokInformaci);
        hlavniPrvky.getChildren().remove(ovladaciPrvky);
        rootPane.getChildren().remove(zadavaniInformaci);
        rootPane.getChildren().remove(oblastTextPotvrzeni);
        rootPane.getChildren().add(oblastProgress);

        // získání informací o věcech
        boolean vesak = blokInformaci.getChildren().contains(boxVesak);
        boolean zavazadlo = blokInformaci.getChildren().contains(boxZavazadlo);

        // ID vesaku a podlahy
        int idVesak = -1;
        int idPodlaha = -1;

        // Uložení věcí do databáze pro věšák když je vyzvedáváno oblečení
        if (vesak) {
            idVesak = historieEntryQueryVyzvednuto(student, TypUmisteni.VESAK);
        }

        // Uložení věcí do databáze pro podlahu když je vyzvedáváno zavazadlo
        if (zavazadlo) {
            idPodlaha = historieEntryQueryVyzvednuto(student, TypUmisteni.PODLAHA);
        }

        // získání čísla ISIC karty aktuálního studenta
        String isic = student.getIsic();

        // získání tasku pro odeslání emailu
        Task<Void> task = getOdesliEmailTask(idVesak, idPodlaha, student);

        // spuštění tasku v novém threadu (neblokující tak hlavní Thread kde běží JavaFX a UI)
        new Thread(task).start();

        // odebrání umístění z databáze
        DatabaseHelper.removeLocationFromUmisteniByISIC(isic);
    }


    /**
     * Vytvoří task pro odeslání emailu
     *
     * @param idVesak    ID vesaku
     * @param idPodlaha  ID podlahy
     * @param student    Student, který vyzvedl věc
     * @return Task pro odeslání emailu
     */
    private Task<Void> getOdesliEmailTask(int idVesak, int idPodlaha, Student student) {

        // vytvoření tasku pro odeslání emailu
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MailHelper.sendEmail(student.getEmail(), "Vyzvednutí věci", "src/main/resources/templates/potvrzeni_vyzvednuti.html", MailHelper.getUschovaniInfo(idVesak, idPodlaha));
                return null;
            }
        };

        // nastavení co se má stát po úspěšném dokončení tasku
        task.setOnSucceeded(e -> {
            PotrvzeniController.text = "Vyzvednutí proběhlo úspěšně!";
            PotrvzeniController.textButton = "Vyzvednout další věc";
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
     * Sestaví a odešle dotaz pro DatabaseHelper o vytvoření nového záznamu o vyzvednutí v historii
     *
     * @param student Student, který vyzvedl věc
     * @param typUmisteni Typ umístění, ze kterého se věc vyzvedla
     */
    private int historieEntryQueryVyzvednuto(Student student, TypUmisteni typUmisteni) {
        return DatabaseHelper.createHistorieEntry(student.getJmeno(), student.getPrijmeni(), student.getIsic(), typUmisteni, DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), typUmisteni), StavUlozeni.VYZVEDNUTO);
    }

    /**
     * Metoda pro zrušení uložení věcí
     * Vrátí na obrazovku uschovat1.fxml
     */
    @FXML
    public void zrusitKlik() throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.VYZVEDNOUT1 );
    }


    /**
     * Přejde na domovskou obrazovku
     */
    @FXML
    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }
}
