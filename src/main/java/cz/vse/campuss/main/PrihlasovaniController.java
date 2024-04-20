package cz.vse.campuss.main;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PrihlasovaniController extends BaseController {
    @FXML
    public CheckBox CheckBoxStudent;
    @FXML
    public CheckBox CheckBoxSatnarka;
    @FXML
    public Text errorText;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text errorTextBoth;

    @FXML
    private void initialize() {
        rootPane.requestFocus();
    }

    @FXML
    public void prihlaseniKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        if (CheckBoxStudent.isSelected() && CheckBoxSatnarka.isSelected()) {
            errorText.setVisible(false);
            errorTextBoth.setVisible(true);
            hideAfterSeconds(errorTextBoth);
        } else if (CheckBoxStudent.isSelected()) {
            // Load the student.fxml file
            try {
                showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/student.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (CheckBoxSatnarka.isSelected()) {
            // Load the home.fxml file
            try {
                showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorTextBoth.setVisible(false);
            errorText.setVisible(true);
            hideAfterSeconds(errorText);
        }
    }

    private void hideAfterSeconds(Node node) {
        // Create a FadeTransition for the fade-in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Create a PauseTransition to keep the error message visible for a few seconds
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(4));

        // When the fade-in effect is over, start the PauseTransition
                fadeIn.setOnFinished(event -> visiblePause.play());

        // Create a FadeTransition for the fade-out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // When the PauseTransition is over, play the fade-out effect
        visiblePause.setOnFinished(event -> fadeOut.play());
    }
}
