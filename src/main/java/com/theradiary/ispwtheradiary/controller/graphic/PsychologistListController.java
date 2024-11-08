package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PsychologistListController extends CommonController{
    protected PsychologistListController(Session session) {
        super(session);
    }
    @FXML
    private ListView<String> listPsychologist;
}