package com.theradiary.ispwtheradiary.controller.graphic.homepage;

import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomepageController extends CommonController {
    public HomepageController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private void startButton(MouseEvent event){
        goToAccountPage(event);
    }


}