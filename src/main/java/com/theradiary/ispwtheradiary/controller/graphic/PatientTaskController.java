package com.theradiary.ispwtheradiary.controller.graphic;
import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.beans.TaskBean;
import com.theradiary.ispwtheradiary.model.beans.ToDoItemBean;
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
import java.util.Optional;

public class PatientTaskController extends CommonController {

    public PatientTaskController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
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
    private PatientBean patientBean;




    @FXML
    public void patientTask(PatientBean patientBean) {
        this.patientBean = patientBean;

        // Imposta il nome completo del paziente
        fullName.setText(patientBean.getFullName());

        // Configura la tabella dei Task
        /*configureTaskTable(patientBean);*/

        // Configura la lista To-Do
       /* configureToDoList(patientBean);*/

        // Configura il diario
        configureDiary(patientBean);
    }

   /* private void configureTaskTable(PatientBean patientBean) {
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
    }*/





    private void configureDiary(PatientBean patientBean) {
        TaskAndToDo taskAndToDo = new TaskAndToDo();
        taskAndToDo.getDiaryForToday(patientBean);
        String diaryText = patientBean.getDiary();
        diaryTextArea.setText(diaryText == null || diaryText.isEmpty() ? "Diario vuoto" : diaryText);
    }
   /* private void configureToDoList(PatientBean patientBean) {
        toDoListItems = FXCollections.observableArrayList();
        for (ToDoItemBean toDoItemBean : patientBean.getToDoList()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(toDoItemBean.isCompleted());
            checkBox.setDisable(true);
            TextField textField = new TextField(toDoItemBean.getToDo());
            textField.setEditable(false);
            HBox hBox = new HBox(checkBox, textField);
            toDoListItems.add(hBox);
        }
        toDoListView.setItems(toDoListItems);
    }*/

    @FXML
    public void modifyTask() {}
    @FXML
    public void modifyToDo() {}
    @FXML
    public void deleteTask(){}

    @FXML
    public void saveToDo() {}
    @FXML
    private void handleTabSelectionChanged() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            String selectedTabText = selectedTab.getText();
            switch (selectedTabText) {
                case "Task":
                    System.out.println("Task");
                    /*configureTaskTable(patientBean);*/
                    break;
                case "To-do List":
                    System.out.println("To-do List");
                   /* configureToDoList(patientBean);*/
                    break;
                case "Diary":
                    configureDiary(patientBean);
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
                root = loader.load();
            } else {
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                List<PatientBean> patientBeans = new Account().retrievePatientList(psychologistBean);
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_LIST_PATH)));
                loader.setControllerFactory(c -> new PatientListController(fxmlPathConfig, session));
                root = loader.load();
                ((PatientListController) loader.getController()).printPatient(event, patientBeans);
            }
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }

    private void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
