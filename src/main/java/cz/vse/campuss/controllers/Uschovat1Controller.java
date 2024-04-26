package cz.vse.campuss.controllers;

import java.io.IOException;
import java.net.URL;

import cz.vse.campuss.model.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.helpers.UserDataContainer;
import cz.vse.campuss.helpers.NodeHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.DatabaseHelper;


/**
 * Kontrolér pro obrazovku uschovat1.fxml
 */
public class Uschovat1Controller extends BaseController {
    private Stage stage;
    private UserDataContainer userDataContainer;

    public TextField vstupISIC;
    public Button tlacitkoOdeslat;
    public Text textPotvrzeni;
    public CheckBox checkBoxObleceni;
    public CheckBox checkBoxZavazadlo;
    public Text textKontrolaZaskrtnuti;
    public VBox hlavniPrvky;
    public HBox zadavaniISIC;
    public Button tlacitkoZobrazit;

    /**
     * Inicializace kontroléru
     */
    @FXML
    private void initialize() {
        // nastavení akce při stisku enter klávesy
        vstupISIC.setOnAction(this::odeslatISIC);
        // plynulé zobrazení ovládacích prvků
        NodeHelper.fadeIn(hlavniPrvky);
        NodeHelper.fadeIn(zadavaniISIC);
        userDataContainer = new UserDataContainer(false, false, null);
        Platform.runLater(() -> {
            stage = (Stage) hlavniPrvky.getScene().getWindow();
        });
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     */
    @FXML
    public void domuKlik() throws IOException {
        StageManager.switchScene(FXMLView.HOME);
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic
     */
    @FXML
    public void zobrazitPoziceKlik() {
        // kontrola zda je zaškrtnutý alespoň jeden z obou checkboxů
        if (!checkBoxZavazadlo.isSelected() && !checkBoxObleceni.isSelected()) {
            NodeHelper.hideAfterSeconds(textKontrolaZaskrtnuti);
        }
        // pokud je nějaký checkbox zaškrtnutý, zobrazí se další obrazovka dle zaškrtnutých políček
        else {
            // uložení stavu checkboxů
            userDataContainer.setPodlahaChecked(checkBoxZavazadlo.isSelected());
            userDataContainer.setVesakChecked(checkBoxObleceni.isSelected());
            // uložení stavu checkboxů do aktuální scény
            stage.getScene().setUserData(userDataContainer);
            // zobrazení nové scény
            try {
                // načtení FXML souboru
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(new URL("file:src/main/resources/cz/vse/campuss/main/fxml/uschovat2.fxml"));
                // načtení nové scény
                Scene scene = new Scene(loader.load());
                // nastavení scény do stage
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setTitle("Campuss");
                // získání controlleru nové scény
                Uschovat2Controller controller = loader.getController();
                // inicializace dat nové scény
                controller.initData(userDataContainer);
                // zobrazení stage
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda pro odeslání ISIC karty
     * @param actionEvent Kliknutí na tlačítko
     */
    @FXML
    public void odeslatISIC(ActionEvent actionEvent) {
        // získání studenta podle ISIC karty
        Student student = DatabaseHelper.fetchStudentByISIC(vstupISIC.getText());

        // uložení studenta do aktuální scény
        userDataContainer.setStudent(student);
        stage.getScene().setUserData(userDataContainer);

        // pokud student nebyl nalezen, zobrazí se chybová hláška
        if (student == null) {
            vstupISIC.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Student s tímto ISIC nebyl nalezen.");
            checkBoxZavazadlo.setDisable(true);
            checkBoxZavazadlo.setSelected(false);
            checkBoxObleceni.setDisable(true);
            checkBoxObleceni.setSelected(false);
        }
        // pokud student byl nalezen, zobrazí se jeho jméno a příjmení a vizuálně se upraví pole pro ISIC
        else {
            vstupISIC.styleProperty().setValue("-fx-border-color: #90EE90; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #90EE90;");
            textPotvrzeni.setText("Student nalezen: " + student.getJmeno() + " " + student.getPrijmeni() + " (" + student.getIsic() + ")");
            checkBoxZavazadlo.setDisable(false);
            checkBoxObleceni.setDisable(false);
        }
    }

    /**
     * Metoda pro pro zobrazení varování při kliknutí na checkbox který není možné zaškrtnout
     */
    public void checkboxKlik() {
        if (checkBoxObleceni.isDisabled() && checkBoxZavazadlo.isDisabled()) {
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Nejdříve zadejte platnou ISIC kartu.");
        }
    }
}
