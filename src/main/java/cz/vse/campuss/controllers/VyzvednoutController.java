package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.*;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

public class VyzvednoutController {

    private Stage stage;
    public AnchorPane rootPane;
    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox ovladaciPrvky;
    public HBox blokInformaci;
    public TextField cisloVesak;
    public TextField cisloPodlaha;
    public TextField isicVstup;
    public Text studentJmeno;
    public ImageView studentFoto;
    public Text textPotvrzeni;
    public Button tlacitkoPotvrdit;
    public HBox zadavaniInformaci;
    public Button tlacitkoOdeslat;

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
        fadeIn(zadavaniInformaci);
        tlacitkoPotvrdit.setDisable(true);
        Platform.runLater(() -> stage = (Stage) ovladaciPrvky.getScene().getWindow());
    }

    /**
     * Odešle ISIC karty a na základě toho upraví UI
     */
    @FXML
    public void odeslatISIC() {
        // získání studenta podle ISIC karty
        Student student = DatabaseHelper.fetchStudentByISIC(isicVstup.getText());

        // pokud student nebyl nalezen, zobrazí se chybová hláška
        if (student == null) {
            isicVstup.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Student s tímto ISIC nebyl nalezen.");
            studentJmeno.setText("");
            studentFoto.setImage(null);
            cisloVesak.setText("");
            cisloPodlaha.setText("");
            tlacitkoPotvrdit.setDisable(true);
        }

        else {
            int vesakLocation = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.VESAK);
            int podlahaLocation = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.PODLAHA);

            // pokud student byl nalezen, zobrazí se jeho jméno a příjmení a vizuálně se upraví pole pro ISIC
            if (vesakLocation != -1 || podlahaLocation != -1) {
                isicVstup.styleProperty().setValue("-fx-border-color: #90EE90; -fx-border-width: 4px;");
                textPotvrzeni.styleProperty().setValue("-fx-fill: #90EE90;");
                textPotvrzeni.setText("Student nalezen: " + student.getJmeno() + " " + student.getPrijmeni() + " (" + student.getIsic() + ")");
                tlacitkoPotvrdit.setDisable(false);

                studentJmeno.setText(student.getJmeno() + " " + student.getPrijmeni());
                Image studentImage = new Image("file:src/main/resources/cz/vse/campuss/main/fxml/fotky/" + student.getIsic() + ".png");
                studentFoto.setImage(studentImage);
                fadeIn(studentFoto);
                if (vesakLocation != -1 && podlahaLocation != -1) {
                    fadeIn(boxVesak);
                    fadeIn(boxZavazadlo);
                    cisloVesak.setText(String.valueOf(vesakLocation));
                    cisloPodlaha.setText(String.valueOf(podlahaLocation));
                }

                else if (vesakLocation != -1) {
                    fadeIn(boxVesak);
                    cisloVesak.setText(String.valueOf(vesakLocation));
                    blokInformaci.getChildren().remove(boxZavazadlo);
                }

                else {
                    fadeIn(boxZavazadlo);
                    cisloPodlaha.setText(String.valueOf(podlahaLocation));
                    blokInformaci.getChildren().remove(boxVesak);
                }
            }

            if (vesakLocation == -1 && podlahaLocation == -1) {
                isicVstup.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
                textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
                textPotvrzeni.setText("Student nemá nic uloženého");
            }
        }
    }

    /**
     * Přejde na domovskou obrazovku
     */
    @FXML
    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.HOME);
    }

    /**
     * Potvrdí vyzvednutí věcí
     */
    @FXML
    public void potvrditVyzvednutiKlik(MouseEvent mouseEvent) throws IOException {
        String isic = isicVstup.getText();

        DatabaseHelper.removeLocationFromUmisteniByISIC(isic);

        PotrvzeniController.text = "Vyzvednutí proběhlo úspěšně";
        PotrvzeniController.textButton = "Vyzvednout další věc";

        StageManager.switchFXML(rootPane, FXMLView.POTVRZENI);
    }
}
