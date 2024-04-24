package com.example.olamitdbfx.Classes;

import javafx.scene.control.Alert;

public class AlertController {
    Alert alert;
    String title;
    String contentText;

    public AlertController(Alert alert) {
        this.alert = alert;
        alert.showAndWait();
    }

    public AlertController(Alert alert, String title) {
        this.alert = alert;
        alert.setTitle(title);
        alert.showAndWait();
    }

    public AlertController(Alert alert, String title, String headerText) {
        this.alert = alert;
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
