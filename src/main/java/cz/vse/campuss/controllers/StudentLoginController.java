package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;


public class StudentLoginController {
    @FXML
    public TextField emailTextField;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text errorText;


    // Initialize method to set default values and initialize database connection
    @FXML
    private void initialize() {

    }

    public void odeslatLogin(MouseEvent mouseEvent) throws IOException {
        String isic = DatabaseHelper.getISICByEmail(emailTextField.getText());

        if (isic != null) {
            int vesakLocation = DatabaseHelper.fetchLocationNumberByISIC(isic, TypUmisteni.VESAK);
            int podlahaLocation = DatabaseHelper.fetchLocationNumberByISIC(isic, TypUmisteni.PODLAHA);

            if (vesakLocation == -1 && podlahaLocation == -1) {
                errorText.setText("Student nemá žádné uložené věci.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/vse/campuss/main/fxml/student.fxml"));
            Parent studentView = loader.load();

            StudentController studentController = loader.getController();
            studentController.setISIC(isic);

            // Switch the content of the rootPane to the loaded STUDENT view
            rootPane.getChildren().setAll(studentView);
        } else {
            errorText.setText("Uživatel nenalezen.");
        }

    }

    @FXML
    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.PRIHLASOVANI);
    }
}
