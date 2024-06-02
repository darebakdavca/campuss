package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.UserDataContainer;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.io.IOException;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static cz.vse.campuss.helpers.NodeHelper.fadeIn;
import static cz.vse.campuss.helpers.StageManager.switchFXML;

/**
 * Třída StudentController slouží jako controller pro student.fxml
 * Třída obsahuje metody pro získání dat a odhlášení
 */
public class StudentController {
    @FXML
    public AnchorPane rootPane;
    public Text closingOut;
    public Text zavazadlaOut;
    public Text obleceniOut;
    public Text satnaOut;
    public Text osloveniOut;

    private Student student;

    /**
     * Inicializační metoda
     */
    @FXML
    private void initialize() {
        UserDataContainer userDataContainer = UserDataContainer.getInstance();
        student = userDataContainer.getStudent();
        // Vytvoření nového timeru, který bude každou sekundu volat metodu startCountdown
        // Refreshuj odpočet
        Timer timer = new Timer(1, e -> {
            startCountdown(); // Refreshuj odpočet
        });
        timer.start(); // Zahájení odpočtu
        fetchData(); // Získání dat o studentovi a jeho uložení
        fadeIn(rootPane); // Plynulé zobrazení scény
    }

    /**
     * Metoda pro spuštění odpočtu
     */
    private void startCountdown() {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        String jmeno = student.getJmeno();

        /**
         * Podle času vypíše oslovení
         */
        if (now.isAfter(LocalTime.of(5, 0)) && now.isBefore(LocalTime.of(10, 0))) {
            osloveniOut.setText("Dobré ráno, " + jmeno + "!");
        } else if (now.isAfter(LocalTime.of(10, 0)) && now.isBefore(LocalTime.of(12, 0))) {
            osloveniOut.setText("Dobré dopoledne, " + jmeno + "!");
        }else if (now.isAfter(LocalTime.of(12, 0)) && now.isBefore(LocalTime.of(13, 0))) {
            osloveniOut.setText("Dobré poledne, " + jmeno + "!");
        }else if (now.isAfter(LocalTime.of(13, 0)) && now.isBefore(LocalTime.of(18, 0))) {
            osloveniOut.setText("Dobré odpoledne, " + jmeno + "!");
        } else {
            osloveniOut.setText("Dobrý večer, " + jmeno + "!");
        }

        LocalTime closingTime = LocalTime.of(19, 0); // Zavírací doba je 19:00

        // Zobrazení zavřeno, pokud je po zavírací době
        if (now.isAfter(closingTime)) {
            Platform.runLater(() -> closingOut.setText("zavřeno"));
            return;
        }

        long millisecondsUntilClosing = now.until(closingTime, ChronoUnit.MILLIS);

        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            //
            today = today.plusDays(1).with(DayOfWeek.MONDAY);
        }

        String countdownText;

        if (millisecondsUntilClosing >= 3600000) { // If more than an hour left
            long hours = millisecondsUntilClosing / 3600000;
            long minutes = (millisecondsUntilClosing % 3600000) / 60000;
            long seconds = ((millisecondsUntilClosing % 3600000) % 60000) / 1000;
            countdownText = hours + " h, " + minutes + " min, " + seconds + " s do zavření";
        } else if (millisecondsUntilClosing >= 60000) { // If more than a minute left
            long minutes = millisecondsUntilClosing / 60000;
            long seconds = (millisecondsUntilClosing % 60000) / 1000;
            long milliseconds = millisecondsUntilClosing % 1000;
            countdownText = minutes + " m, " + seconds + " s do zavření";
        } else { // Less than a minute left
            long seconds = millisecondsUntilClosing / 1000;
            long milliseconds = millisecondsUntilClosing % 1000;
            countdownText = seconds + " s do zavření, ";
        }

        /**
         * Zobrazení odpočtu
         */

        Platform.runLater(() -> closingOut.setText(countdownText));
    }


    /**
     * Metoda pro získání dat
     */
    @FXML
    public void fetchData() {

        if (student != null) {


            int vesakLocation = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.VESAK);
            int podlahaLocation = DatabaseHelper.fetchLocationNumberByISIC(student.getIsic(), TypUmisteni.PODLAHA);

            String satna = DatabaseHelper.getSatnaFromISIC(student.getIsic());
            if (vesakLocation != -1) {
                obleceniOut.setText(String.valueOf(vesakLocation));
            } else {
                obleceniOut.setText("Neuloženo");
            }

            if (podlahaLocation != -1) {
                zavazadlaOut.setText(String.valueOf(podlahaLocation));
            } else {
                zavazadlaOut.setText("Neuloženo");
            }

            satnaOut.setText(satna);

        } else {
            throw new RuntimeException("Student je prázdný!");
        }
    }

    /**
     * Metoda pro přechod na domovskou obrazovku
     * @param mouseEvent Event kliknutí myší
     */
    public void domuKlik(MouseEvent mouseEvent) {
        try {
            switchFXML(rootPane, FXMLView.HOME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda pro odhlášení
     * @param mouseEvent Event kliknutí myší
     * @throws IOException Výjimka při chybě při načítání scény
     */
    public void logoutKlik(MouseEvent mouseEvent) throws IOException {
        switchFXML(rootPane, FXMLView.PRIHLASOVANI);
    }
}
