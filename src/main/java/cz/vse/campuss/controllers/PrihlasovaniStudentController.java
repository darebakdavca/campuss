package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.*;
import cz.vse.campuss.model.Satna;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import javafx.scene.text.Text;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;
import static cz.vse.campuss.helpers.NodeHelper.hideAfterSeconds;


/**
 * Třída StudentLoginController slouží jako controller pro studentLogin.fxml
 * Třída obsahuje metody pro odeslání loginu a přechod na stránku s informacemi
 */
public class PrihlasovaniStudentController {
    @FXML
    public TextField emailVstup;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text errorText;

    private UserDataContainer userDataContainer;


    /**
     * Inicializační metoda
     */
    @FXML
    private void initialize() {
        fadeIn(rootPane);
        fadeIn(emailVstup);
        userDataContainer = UserDataContainer.getInstance();
        emailVstup.setOnAction(actionEvent -> {
            try {
                odeslatLogin(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Metoda pro odeslání loginu
     * @param actionEvent Event stisknutí tlačítka
     * @throws IOException Výjimka při chybě při načítání scény
     */
    @FXML
    public void odeslatLogin(ActionEvent actionEvent) throws IOException {
        String isic = DatabaseHelper.getISICByEmail(emailVstup.getText());
        Student student = DatabaseHelper.fetchStudentByISIC(isic);
        Satna satna = SatnaSelection.getInstance().getSelectedSatna();

        if (isic != null) {
            int vesakLocation = DatabaseHelper.fetchLocationNumberByISIC(isic, TypUmisteni.VESAK, satna.getId());
            int podlahaLocation = DatabaseHelper.fetchLocationNumberByISIC(isic, TypUmisteni.PODLAHA, satna.getId());

            userDataContainer.setStudent(student);

            if (vesakLocation == -1 && podlahaLocation == -1) {
                errorText.setVisible(true);
                hideAfterSeconds(errorText);
                return;
            }

            StageManager.switchFXML(rootPane, FXMLView.STUDENT);

        } else {
            errorText.setText("Uživatel nenalezen.");
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }

    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * @param mouseEvent Event kliknutí myší
     * @throws IOException Výjimka při chybě při načítání scény
     */
    @FXML
    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.PRIHLASOVANI);
    }
}
