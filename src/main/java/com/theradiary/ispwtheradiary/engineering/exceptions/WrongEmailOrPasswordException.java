package com.theradiary.ispwtheradiary.engineering.exceptions;

import javafx.scene.control.Alert;

public class WrongEmailOrPasswordException extends Exception {
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }


}
