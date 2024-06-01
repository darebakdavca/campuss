package cz.vse.campuss.controllers;

import cz.vse.campuss.helpers.DatabaseHelper;
import cz.vse.campuss.helpers.FXMLView;
import cz.vse.campuss.helpers.StageManager;
import cz.vse.campuss.model.PolozkaHistorie;
import cz.vse.campuss.model.Satna;
import cz.vse.campuss.model.Student;
import cz.vse.campuss.model.TypUmisteni;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
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

import static cz.vse.campuss.helpers.StageManager.switchFXML;

public class StudentController {
    @FXML
    public AnchorPane rootPane;
    public Text closingOut;
    public Text zavazadlaOut;
    public Text obleceniOut;
    public Text satnaOut;
    public Text osloveniOut;

    private String isic;

    private Timer timer;

    // Inicializační metoda
    @FXML
    private void initialize() {

        // Vytvoření nového timeru, který bude každou sekundu volat metodu startCountdown
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown(); // Refresh the countdown
            }
        });
        timer.start(); // Start the timer
    }

    // Metoda pro zobrazení času do zavření
    private void startCountdown() {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        Student student = DatabaseHelper.fetchStudentByISIC(isic);
        String jmeno = student.getJmeno();

        // Nastavení oslovení podle denní doby
        if (now.isAfter(LocalTime.of(5, 0)) && now.isBefore(LocalTime.of(10, 0))) {
            osloveniOut.setText("Dobré ráno, " + jmeno);
        } else if (now.isAfter(LocalTime.of(10, 0)) && now.isBefore(LocalTime.of(12, 0))) {
            osloveniOut.setText("Dobré dopoledne, " + jmeno);
        }else if (now.isAfter(LocalTime.of(12, 0)) && now.isBefore(LocalTime.of(13, 0))) {
            osloveniOut.setText("Dobré poledne, " + jmeno);
        }else if (now.isAfter(LocalTime.of(13, 0)) && now.isBefore(LocalTime.of(18, 0))) {
            osloveniOut.setText("Dobré odpoledne, " + jmeno);
        } else {
            osloveniOut.setText("Dobrý večer, " + jmeno);
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

        // Update the closingOut text field on the JavaFX Application Thread
        Platform.runLater(() -> closingOut.setText(countdownText));
    }


    // Method to fetch data from the database
    @FXML
    public void fetchData() {
        // Get student from database
        Student student = DatabaseHelper.fetchStudentByISIC(isic);

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
            throw new RuntimeException("Student is null - this err should not occur");
        }
    }

    // Method to navigate back to the home screen
    public void domuKlik(MouseEvent mouseEvent) {
        try {
            switchFXML(rootPane, FXMLView.HOME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setISIC(String isic) {
        this.isic = isic;
        fetchData();
    }


    public void logoutKlik(MouseEvent mouseEvent) throws IOException {
        try {
            switchFXML(rootPane, FXMLView.PRIHLASOVANI2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
