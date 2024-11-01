package com.theradiary.ispwtheradiary.controller.graphic.homepage;

import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomepageController extends CommonController {
    public HomepageController(Session session) {
        super(session);
    }

    @FXML
    private void startButton(MouseEvent event){
        goToAccountPage(event);
    }


}