package com.theradiary.ispwtheradiary.engineering.others;

import javafx.fxml.FXML;
import javafx.scene.control.Label;



import javafx.scene.control.Label;

public class Validator {
    public static boolean isValidMail(String mail, Label errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password, Label errorMessage) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(passwordRegex)) {
            errorMessage.setText("Password non valida");
            errorMessage.setVisible(true);
            return false;
        }
        return true;
    }
}