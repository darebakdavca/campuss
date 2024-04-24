package cz.vse.campuss.main;

import cz.vse.campuss.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Uschovat1Controller extends BaseController {

    public TextField vstupISIC;
    public Button tlacitkoOdeslat;
    public Text textPotvrzeni;
    public CheckBox checkBoxObleceni;
    public CheckBox checkBoxZavazadlo;
    public Text textKontrolaZaskrtnuti;
    public VBox hlavniPrvky;
    public HBox zadavaniISIC;


    /**
     * Třída pro uchování stavu checkboxů
     */
    public static class CheckBoxState {
        public boolean obleceniChecked;
        public boolean zavazadloChecked;

        public CheckBoxState(boolean obleceniChecked, boolean zavazadloChecked) {
            this.obleceniChecked = obleceniChecked;
            this.zavazadloChecked = zavazadloChecked;
        }
    }

    @FXML
    private void initialize() {
        vstupISIC.setOnAction(this::odeslatISIC);
        fadeIn(hlavniPrvky);
        fadeIn(zadavaniISIC);
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * @param mouseEvent Kliknutí na tlačítko
     */
    @FXML
    public void domuKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        try {
            // Load the home.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic
     * @param mouseEvent    Kliknutí na tlačítko
     */
    @FXML
    public void zobrazitPoziceKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        // kontrola zda je zaškrtnutý alespoň jeden z obou checkboxů
        if (!checkBoxZavazadlo.isSelected() & !checkBoxObleceni.isSelected()) {
            textKontrolaZaskrtnuti.styleProperty().setValue("-fx-fill: #FF6347;");
            textKontrolaZaskrtnuti.setText("Musíte zaškrtnout alespoň jednu možnost.");
        }
        // pokud je nějaký checkbox zaškrtnutý, zobrazí se další obrazovka dle zaškrtnutých políček
        else {
            // uložení stavu checkboxů
            CheckBoxState checkBoxState = new CheckBoxState(checkBoxObleceni.isSelected(), checkBoxZavazadlo.isSelected());
            // uložení stavu checkboxů do aktuální scény
            stage.getScene().setUserData(checkBoxState);
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
                controller.initData(mouseEvent);
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
        // pokud student nebyl nalezen, zobrazí se chybová hláška
        if (student == null) {
            vstupISIC.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Student s tímto ISIC nebyl nalezen.");
            checkBoxZavazadlo.setDisable(true);
            checkBoxObleceni.setDisable(true);
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
     * Metoda pro vymazání chybové hlášky při kliknutí na checkbox
     * @param actionEvent Kliknutí na checkbox
     */
    public void checkboxKlik(ActionEvent actionEvent) {
        textKontrolaZaskrtnuti.setText("");
    }
}
