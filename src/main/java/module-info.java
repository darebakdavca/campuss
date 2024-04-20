module cz.vse.campuss {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;


    opens cz.vse.campuss.main to javafx.fxml;
    exports cz.vse.campuss.main;
}