module cz.vse.campuss {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens cz.vse.campuss.main to javafx.fxml;
    exports cz.vse.campuss.main;
}