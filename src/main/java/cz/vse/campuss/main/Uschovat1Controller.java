package cz.vse.campuss.main;

import cz.vse.campuss.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Uschovat1Controller extends BaseController {

    public TextField vstupISIC;
    public Button tlacitkoOdeslat;
    public Text textPotvrzeni;
    public CheckBox checkBoxObleceni;
    public CheckBox checkBoxZavazadlo;
    public Text textKontrolaZaskrtnuti;

    @FXML
    private void initialize() {
        vstupISIC.setOnAction(this::odeslatISIC);
    }


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

    @FXML
    public void zobrazitPoziceKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (!checkBoxZavazadlo.isSelected() & !checkBoxObleceni.isSelected()) {
            textKontrolaZaskrtnuti.styleProperty().setValue("-fx-fill: #FF6347;");
            textKontrolaZaskrtnuti.setText("Musíte zaškrtnout alespoň jednu možnost.");
        } else {
            try {
                showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat2.fxml");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void odeslatISIC(ActionEvent actionEvent) {
        Student student = DatabaseHelper.fetchStudentByISIC(vstupISIC.getText());

        if (student == null) {
            vstupISIC.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Student s tímto ISIC nebyl nalezen.");
            checkBoxZavazadlo.setDisable(true);
            checkBoxObleceni.setDisable(true);
        } else {
            vstupISIC.styleProperty().setValue("-fx-border-color: #90EE90; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #90EE90;");
            textPotvrzeni.setText("Student nalezen: " + student.getJmeno() + " " + student.getPrijmeni() + " (" + student.getIsic() + ")");
            checkBoxZavazadlo.setDisable(false);
            checkBoxObleceni.setDisable(false);
        }
    }

    public void checkboxKlik(ActionEvent actionEvent) {
        textKontrolaZaskrtnuti.setText("");
    }
}
