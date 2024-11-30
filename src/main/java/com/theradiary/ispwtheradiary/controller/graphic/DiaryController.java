package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
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

public class DiaryController extends CommonController {
    DiaryController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private TextArea diary;
    @FXML
    private Text date;
    TaskAndToDo diaryEntry = new TaskAndToDo();
    PatientBean patientBean = (PatientBean) session.getUser();

    @FXML
    public void initializeDiary() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(currentDate.format(formatter));

        loadDiaryContent();
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
        int maxWords=5000;
        int wordCount=countWords(diaryContent);
        if(diaryContent.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Contenuto vuoto", "Inserisci del testo nel diario prima di salvare.");
            return;
        }
        if(wordCount>maxWords) {
            showAlert(Alert.AlertType.WARNING, "Contenuto troppo lungo", "Il diario non può superare le 1000 parole.");
            return;
        }
        try{
            diaryEntry.saveDiary(diaryContent,patientBean);
            showAlert(Alert.AlertType.INFORMATION, "Salvataggio effettuato", "Il diario è stato salvato correttamente.");
        }catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Errore durante il salvataggio del diario.");
            e.printStackTrace();
        }
    }
    private void loadDiaryContent(){
        try{
            String diaryContent = diaryEntry.getDiaryForToday(patientBean);
            if(diaryContent!=null) {
                diary.setText(diaryContent);
            }else{
                diary.clear();
                showAlert(Alert.AlertType.INFORMATION, "Nuovo Diario", "Puoi iniziare a scrivere il tuo diario per oggi.");
            }
        }catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Errore durante il caricamento", "Impossibile caricare il diario.");
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private int countWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        String[] words = s.trim().split("\\s+");
        return words.length;
    }

}
