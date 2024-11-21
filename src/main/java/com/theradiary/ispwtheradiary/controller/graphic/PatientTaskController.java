package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PatientTaskController extends CommonController {
    public PatientTaskController(Session session) {
        super(session);
    }
    @FXML
    private Label fullName;


}