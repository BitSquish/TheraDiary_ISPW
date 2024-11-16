package com.theradiary.ispwtheradiary.engineering.exceptions;

import javafx.scene.control.Alert;

public class EmptyFieldException extends Exception {
    public EmptyFieldException(String message) {
        super(message);
    }

}
