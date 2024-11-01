package com.theradiary.ispwtheradiary.engineering.exceptions;

import javafx.scene.control.Alert;

public class EmptyFieldException extends Exception {
    public EmptyFieldException(String message) {
        super(message);
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText("Compila tutti i campi");
        alert.showAndWait();
    }
}
