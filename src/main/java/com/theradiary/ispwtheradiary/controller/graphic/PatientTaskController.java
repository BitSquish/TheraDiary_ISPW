package com.theradiary.ispwtheradiary.controller.graphic;
import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.beans.TaskBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class PatientTaskController extends CommonController {
    public PatientTaskController(Session session) {
        super(session);
    }

    @FXML
    private Label fullName;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab task;
    @FXML
    private Tab toDo;
    @FXML
    private Tab diary;
    @FXML
    private TableView<TaskBean> taskTableView;
    @FXML
    private TableColumn<TaskBean, String> taskNameColumn;
    @FXML
    private TableColumn<TaskBean, String> taskDeadlineColumn;
    @FXML
    private TableColumn<TaskBean, String> taskStatusColumn;
    @FXML
    private ListView<HBox> toDoListView;
    @FXML
    private ObservableList<HBox> toDoListItems;
    @FXML
    private TextArea diaryTextArea;
    @FXML
    private Button modify;
    @FXML
    private Button delete;
    @FXML
    private Button modifyToDo;
    @FXML
    private Button save;

    @FXML
    public void patientTask(PatientBean patientBean) {
        ObservableList<TaskBean> taskBeans = FXCollections.observableArrayList();
        fullName.setText(patientBean.getFullName());
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        taskTableView.setItems(patientBean.getTasks());
        toDoListItems = FXCollections.observableArrayList();
        toDoListView.setItems(toDoListItems);
        if(diaryTextArea.getText().isEmpty()) {
            diaryTextArea.setText("Diario vuoto");
        }else{
            diaryTextArea.setText(patientBean.getDiary());
        }
        taskTableView.setItems(taskBeans);
    }

    @FXML
    public void modifyTask() {
        TaskBean taskBean = taskTableView.getSelectionModel().getSelectedItem();
        if (taskBean != null) {
            System.out.println("Modifica task: " + taskBean.getTaskName());
        }
    }

    @FXML
    public void deleteTask() {
        TaskBean taskBean = taskTableView.getSelectionModel().getSelectedItem();
        if (taskBean != null) {
            System.out.println("Elimina task: " + taskBean.getTaskName());
        }
    }

    @FXML
    public void modifyToDo() {
        for (HBox hBox : toDoListItems) {
            CheckBox checkBox = (CheckBox) hBox.getChildren().get(0);
            TextField textField = (TextField) hBox.getChildren().get(1);
            textField.setEditable(true);
            checkBox.setDisable(false);
        }
        save.setDisable(false);
    }

    @FXML
    public void saveToDo() {
        for (HBox hBox : toDoListItems) {
            CheckBox checkBox = (CheckBox) hBox.getChildren().get(0);
            TextField textField = (TextField) hBox.getChildren().get(1);
            if (checkBox.isSelected()) {
                System.out.println("Task completato: " + textField.getText());
            }
        }
    }

    @FXML
    private void handleTabSelectionChanged() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

        if (selectedTab != null) {
            String selectedTabText = selectedTab.getText();

            switch (selectedTabText) {
                case "Task":
                    // Carica o aggiorna i dati per il tab "Task"
                    System.out.println("Tab Task selezionato");
                    break;
                case "To-do List":
                    // Carica o aggiorna i dati per il tab "To-do List"
                    System.out.println("Tab To-do List selezionato");
                    break;
                case "Diary":
                    // Carica o aggiorna i dati per il tab "Diary"
                    System.out.println("Tab Diary selezionato");
                    break;
                default:
                    break;
            }
        }
    }
    @FXML
    protected void back(MouseEvent event){
        try{
            FXMLLoader loader;
            Parent root;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
                root=loader.load();
            }else{
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                List<PatientBean> patientBeans= new Account().retrievePatientList(psychologistBean);
                loader= new FXMLLoader(getClass().getResource(PATIENT_LIST));
                loader.setControllerFactory(c -> new PatientListController(session));
                root=loader.load();
                ((PatientListController)loader.getController()).printPatient(event,patientBeans);
            }
            changeScene(root,event);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena: "+e.getMessage(),e);
        }
    }
}
