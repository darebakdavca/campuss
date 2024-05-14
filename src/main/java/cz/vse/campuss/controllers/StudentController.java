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
import java.time.DayOfWeek;
import java.time.LocalDate;







public class StudentController {
    @FXML
    public TextField satnaOut;
    @FXML
    public TextField obleceniOut;
    @FXML
    public TextField zavazadlaOut;
    @FXML
    public TextField timeOut;
    @FXML
    public TextField closingOut;
    @FXML
    public AnchorPane rootPane;

    private String isic;

    private Timer timer;

    // Initialize method to set default values and initialize database connection
    @FXML
    private void initialize() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown(); // Refresh the countdown
            }
        });
        timer.start(); // Start the timer
    }

    // Method to start the countdown
    private void startCountdown() {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        LocalTime closingTime = LocalTime.of(19, 0); // closing at 9 pm

        // Check if closing time is before the current time
        if (now.isAfter(closingTime)) {
            Platform.runLater(() -> closingOut.setText("zavřeno"));
            return; // Exit the method
        }

        long millisecondsUntilClosing = now.until(closingTime, ChronoUnit.MILLIS);

        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            // Adjust closing time to Monday
            today = today.plusDays(1).with(DayOfWeek.MONDAY);
        }

        String countdownText;

        if (millisecondsUntilClosing >= 3600000) { // If more than an hour left
            long hours = millisecondsUntilClosing / 3600000;
            long minutes = (millisecondsUntilClosing % 3600000) / 60000;
            long seconds = ((millisecondsUntilClosing % 3600000) % 60000) / 1000;
            countdownText = hours + " hours, " + minutes + " minutes, " + seconds + " seconds until closing";
        } else if (millisecondsUntilClosing >= 60000) { // If more than a minute left
            long minutes = millisecondsUntilClosing / 60000;
            long seconds = (millisecondsUntilClosing % 60000) / 1000;
            long milliseconds = millisecondsUntilClosing % 1000;
            countdownText = minutes + " minutes, " + seconds + " seconds, " + milliseconds + " milliseconds until closing";
        } else { // Less than a minute left
            long seconds = millisecondsUntilClosing / 1000;
            long milliseconds = millisecondsUntilClosing % 1000;
            countdownText = seconds + " seconds, " + milliseconds + " milliseconds until closing";
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
            StageManager.switchFXML(rootPane, FXMLView.HOME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setISIC(String isic) {
        this.isic = isic;
        fetchData();
    }

    public void logoutKlik(MouseEvent mouseEvent) throws IOException {
        StageManager.switchFXML(rootPane, FXMLView.PRIHLASOVANI);
    }
}
