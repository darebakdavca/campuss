module cz.vse.campuss {
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;


    exports cz.vse.campuss.controllers;
    opens cz.vse.campuss.controllers to javafx.fxml;
    exports cz.vse.campuss.helpers;
    opens cz.vse.campuss.helpers to javafx.fxml;
    exports cz.vse.campuss;
    opens cz.vse.campuss to javafx.fxml;
    opens cz.vse.campuss.model to javafx.base;
}