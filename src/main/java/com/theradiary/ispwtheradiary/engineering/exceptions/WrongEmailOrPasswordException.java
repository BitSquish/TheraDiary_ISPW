package com.theradiary.ispwtheradiary.engineering.exceptions;

import javafx.scene.control.Alert;

public class WrongEmailOrPasswordException extends Exception {
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore nel login");
        alert.setHeaderText(null);
        alert.setContentText("Mail o password errate");
        alert.showAndWait();
    }
}
