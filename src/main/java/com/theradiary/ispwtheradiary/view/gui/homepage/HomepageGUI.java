package com.theradiary.ispwtheradiary.view.gui.homepage;

import com.theradiary.ispwtheradiary.view.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomepageGUI extends CommonGUI {
    public HomepageGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private void startButton(MouseEvent event){
        goToAccount(event);
    }


}