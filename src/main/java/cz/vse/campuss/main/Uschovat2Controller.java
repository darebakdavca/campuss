package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

import static cz.vse.campuss.main.Uschovat1Controller.*;

public class Uschovat2Controller extends BaseController {


    public VBox boxZavazadlo;
    public VBox boxVesak;
    public HBox oblastBoxy;
    public Line deliciCara;
    public HBox ovladaciPrvky;

    @FXML
    private void initialize() {
        boxZavazadlo.setVisible(false);
        boxVesak.setVisible(false);
        deliciCara.setVisible(false);
        fadeIn(ovladaciPrvky);
    }

    /**
     * Metoda pro zobrazení potvrzení o uložení věcí
     * Vrátí na obrazovku potvrzeni.fxml
     * @param mouseEvent Kliknutí na tlačítko
     */
    @FXML
    public void potvrditUschovaniKlik(MouseEvent mouseEvent) {
        // Get the stage of the current scene
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            // Load the potvrzeni.fxml file
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/potvrzeni.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zrušení uložení věcí
     * Vrátí na obrazovku uschovat1.fxml
     * @param mouseEvent Kliknutí na tlačítko
     */
    @FXML
    public void zrusitKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat1.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zobrazení domovské obrazovky
     * Vrátí na obrazovku home.fxml
     * @param mouseEvent Kliknutí na tlačítko
     */
    @FXML
    public void domuKlik(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            showScene(stage, "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zobrazení obrazovky pro ukázání pozic na základě stavu checkboxů z předchozí obrazovky
     * @param mouseEvent Kliknutí na tlačítko
     */
    public void initData(MouseEvent mouseEvent) {
        // získání stavu checkboxů z předchozí obrazovky
        CheckBoxState checkBoxState = (CheckBoxState) ((Node) mouseEvent.getSource()).getScene().getUserData();
        // zobrazení boxů na základě stavu checkboxů
        if (checkBoxState.obleceniChecked && checkBoxState.zavazadloChecked) {
            fadeIn(deliciCara);
            fadeIn(boxZavazadlo);
            fadeIn(boxVesak);
        } else if (checkBoxState.obleceniChecked) {
            oblastBoxy.getChildren().remove(boxZavazadlo);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxVesak);
        } else if (checkBoxState.zavazadloChecked){
            oblastBoxy.getChildren().remove(boxVesak);
            oblastBoxy.getChildren().remove(deliciCara);
            fadeIn(boxZavazadlo);
        }
    }
}
