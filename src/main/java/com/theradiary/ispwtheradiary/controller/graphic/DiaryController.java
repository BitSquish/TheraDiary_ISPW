package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DiaryController extends CommonController {
    DiaryController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private TextArea diary;
    @FXML
    private Text date;

    @FXML
    public void initializeDiary() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(currentDate.format(formatter));
    }

    @FXML
    protected void back(MouseEvent event){
        try{
            FXMLLoader loader;
            if(session.getUser()==null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            }else{
                loader=new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_AND_TASKS_PATH)));
                loader.setControllerFactory(c->new DiaryAndTasksController(fxmlPathConfig,session));
            }
            Parent root=loader.load();
            changeScene(root,event);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
    }
    @FXML
    protected void saveDiary(MouseEvent event){
        String diaryContent = diary.getText();
        System.out.println("Diario salvato:"+diaryContent);
    }

}
