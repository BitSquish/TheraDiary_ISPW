package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AccountController extends CommonController {

    protected AccountController(Session session) {
        super(session);
    }

    @FXML
    ImageView account;
    /*@FXML
    private List<CheckBox> Box;
    @FXML
     protected void handleCheckBoxSave() {
        Properties properties = new Properties();
        for (int i = 0; i < checkBoxes.size(); i++) {
            properties.setProperty("checkBox" + (i + 1), String.valueOf(checkBoxes.get(i).isSelected()));
        }

        try (FileOutputStream output = new FileOutputStream("checkboxes.properties")) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */

    @FXML
    protected void goToModifyScreen(MouseEvent event) throws IOException {
        /*try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/ModifyPatient.fxml"));
                loader.setControllerFactory(c -> new ModifyPatientController(session));
            } else  {
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/ModifyPsychologist.fxml"));
                loader.setControllerFactory(c -> new ModifyPsychologistController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    @FXML
    protected void joinPag(MouseEvent event){
        session.getUser().setPag(true);
    }//continuare serve salvare il pag nel db

    @FXML
    protected void logout(MouseEvent event) throws IOException {
        session.setUser(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/Login.fxml"));
        loader.setControllerFactory(c -> new LoginController(session));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void handleCheckBoxSave(CheckBox checkBox) {
        // Implementa la logica per salvare lo stato della CheckBox
    }

    @FXML
    protected void goBack(MouseEvent event) throws IOException {
        /*
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/HomepageLoggedPt.fxml"));
                loader.setControllerFactory(c -> new HomepagePtLoggedController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/example/res/view/HomepageLoggedPs.fxml"));
                loader.setControllerFactory(c -> new HomepageLoggedPsController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}


