package cz.vse.campuss.main;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Text uvitani;
    @FXML
    private void initialize() {
        uvitani.setText("Vítejte Marcelko!");
    }
}