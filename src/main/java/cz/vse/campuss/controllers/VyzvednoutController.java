package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.helpers.UserDataContainer;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;

public class VyzvednoutController extends BaseController {

    private Stage stage;
    private UserDataContainer userDataContainer;

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

    @FXML
    private void initialize() {
        fadeIn(ovladaciPrvky);
        fadeIn(blokInformaci);
        tlacitkoPotvrdit.setDisable(true);
        studentJmeno.setText("");
        userDataContainer = new UserDataContainer(false, false, null);
        Platform.runLater(() -> {
            stage = (Stage) ovladaciPrvky.getScene().getWindow();
        });
    }
    @FXML
    public void odeslatISIC() {
        studentJmeno.setText("");
        studentFoto.setImage(null);
        cisloVesak.setText("");
        cisloPodlaha.setText("");

        // získání studenta podle ISIC karty
        Student student = DatabaseHelper.fetchStudentByISIC(isicVstup.getText());

        // uložení studenta do aktuální scény
        userDataContainer.setStudent(student);
        stage.getScene().setUserData(userDataContainer);

        // pokud student nebyl nalezen, zobrazí se chybová hláška
        if (student == null) {
            isicVstup.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
            textPotvrzeni.setText("Student s tímto ISIC nebyl nalezen.");
            tlacitkoPotvrdit.setDisable(true);
        }
        // pokud student byl nalezen, zobrazí se jeho jméno a příjmení a vizuálně se upraví pole pro ISIC
        else {
            isicVstup.styleProperty().setValue("-fx-border-color: #90EE90; -fx-border-width: 4px;");
            textPotvrzeni.styleProperty().setValue("-fx-fill: #90EE90;");
            textPotvrzeni.setText("Student nalezen: " + student.getJmeno() + " " + student.getPrijmeni() + " (" + student.getIsic() + ")");
            studentJmeno.setText(student.getJmeno() + " " + student.getPrijmeni());
            tlacitkoPotvrdit.setDisable(false);

            Image studentImage = new Image("file:src/main/resources/cz/vse/campuss/main/fxml/fotky/" + student.getIsic() + ".png");
            studentFoto.setImage(studentImage);

            int vesakLocation = DatabaseHelper.fetchLocationByISIC(student.getIsic(),TypUmisteni.VESAK);
            int podlahaLocation = DatabaseHelper.fetchLocationByISIC(student.getIsic(), TypUmisteni.PODLAHA);

            if (vesakLocation == -1 && podlahaLocation == -1) {
                isicVstup.styleProperty().setValue("-fx-border-color: #FF6347; -fx-border-width: 4px;");
                textPotvrzeni.styleProperty().setValue("-fx-fill: #FF6347;");
                textPotvrzeni.setText("Student nemá nic uloženého");
                blokInformaci.getChildren().remove(boxVesak);
                blokInformaci.getChildren().remove(boxZavazadlo);
                tlacitkoPotvrdit.setDisable(true);
                return;
            }

            if (vesakLocation == -1) {
                blokInformaci.getChildren().remove(boxVesak);
            } else {
                cisloVesak.setText(String.valueOf(vesakLocation));
            }

            if (podlahaLocation == -1) {
                blokInformaci.getChildren().remove(boxZavazadlo);
            } else {
                cisloPodlaha.setText(String.valueOf(podlahaLocation));
            }

        }
    }

    @FXML
    public void domuKlik(MouseEvent mouseEvent) throws IOException {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        StageManager.switchScene(FXMLView.HOME);
    }

    @FXML
    public void potvrditVyzvednutiKlik(MouseEvent mouseEvent) throws IOException {
        String isic = userDataContainer.getStudent().getIsic();

        DatabaseHelper.removeLocationByISIC(isic);

        PotrvzeniController.text = "Vyzvednutí proběhlo úspěšně";
        PotrvzeniController.textButton = "Vyzvednout další věc";

        StageManager.switchScene(FXMLView.POTVRZENI);
    }

    @FXML
    public void zrusitKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchScene(FXMLView.HOME);
    }
}
