package com.theradiary.ispwtheradiary.controller.graphic.gui.task;



import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoPtController;
import com.theradiary.ispwtheradiary.controller.graphic.gui.CommonGUI;
import com.theradiary.ispwtheradiary.controller.graphic.gui.login.LoginGUI;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;


public class DiaryPatientGUI extends CommonGUI {
    public DiaryPatientGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    private final TaskAndToDoPtController taskAndToDoController = new TaskAndToDoPtController();
    @FXML
    private TextArea diary;
    @FXML
    private Text date;

    PatientBean patientBean = (PatientBean) session.getUser();

    @FXML
    public void initializeDiary(PatientBean patientBean) {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(currentDate.format(formatter));

        loadDiaryContent(patientBean);
    }
    protected void loadDiaryContent(PatientBean patientBean){
        try{
            String diaryContent = taskAndToDoController.getDiaryForToday(patientBean);
            diary.setText(diaryContent);
        }catch(Exception e){
            showMessage(Alert.AlertType.ERROR, "Errore durante il caricamento", "Impossibile caricare il diario.");
            Logger.getAnonymousLogger().severe("Errore durante il caricamento del diario: " + e.getMessage());
        }
    }

    @FXML
    protected void back(MouseEvent event){
        try{
            FXMLLoader loader;
            if(session.getUser()==null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginGUI(fxmlPathConfig, session));
            }else{
                loader=new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_AND_TASKS_PATH)));
                loader.setControllerFactory(c->new TaskAndToDoPtGUI(fxmlPathConfig,session));
            }
            Parent root=loader.load();
            changeScene(root,event);
        }catch (IOException e) {
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }
    }
    @FXML
    protected void saveDiary(MouseEvent event){
        String diaryContent = diary.getText();
        int maxWords=5000;
        int wordCount=countWords(diaryContent);
        if(wordCount>maxWords) {
            showMessage(Alert.AlertType.WARNING, "Contenuto troppo lungo", "Il diario non può superare le 1000 parole.");
            return;
        }
        try{
            taskAndToDoController.saveDiary(diaryContent,patientBean,LocalDate.now());
            showMessage(Alert.AlertType.INFORMATION, "Salvataggio effettuato", "Il diario è stato salvato correttamente.");
        }catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Errore", "Errore durante il salvataggio del diario.");
            Logger.getAnonymousLogger().severe("Errore durante il salvataggio del diario: " + e.getMessage());
        }
    }


    private int countWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        String[] words = s.trim().split("\\s+");
        return words.length;
    }
    @FXML
    public void goToPage(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_PAGE_PATH)));
            loader.setControllerFactory(c -> new DiaryPagesPatientGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            changeScene(root, event);
        }catch (IOException e) {
            throw new LoadingException("Errore durante il caricamento della scena", e);
        }

    }

}
