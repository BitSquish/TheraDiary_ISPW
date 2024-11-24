package com.theradiary.ispwtheradiary.controller.graphic;
import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
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

    private PatientBean currentPatientBean;


    @FXML
    public void patientTask(PatientBean patientBean) {
        this.currentPatientBean = patientBean;

        // Imposta il nome completo del paziente
        fullName.setText(patientBean.getFullName());

        // Configura la tabella dei Task
        configureTaskTable(patientBean);

        // Configura la lista To-Do
        configureToDoList(patientBean);

        // Configura il diario
        configureDiary(patientBean);
    }

    private void configureTaskTable(PatientBean patientBean) {
        TaskAndToDo task = new TaskAndToDo();
        task.retrieveTaskList(patientBean);
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        taskTableView.setItems(patientBean.getTasks());
    }

    private void configureToDoList(PatientBean patientBean) {
        toDoListItems = FXCollections.observableArrayList();
        for (ToDoItemBean toDoItem : patientBean.getToDoList()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(toDoItem.isCompleted());

            TextField textField = new TextField(toDoItem.getToDo());
            textField.setEditable(false);

            HBox hBox = new HBox(checkBox, textField);
            toDoListItems.add(hBox);
        }
        toDoListView.setItems(toDoListItems);
    }

    private void configureDiary(PatientBean patientBean) {
        String diaryText = patientBean.getDiary();
        diaryTextArea.setText(diaryText == null || diaryText.isEmpty() ? "Diario vuoto" : diaryText);
    }


    @FXML
    public void modifyTask() {
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            TextInputDialog dialog = new TextInputDialog(selectedTask.getTaskName());
            dialog.setTitle("Modifica Task");
            dialog.setHeaderText("Modifica il nome del task selezionato:");
            dialog.setContentText("Nuovo nome del task:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newTaskName -> {
                selectedTask.setTaskName(newTaskName);
                taskTableView.refresh();
            });
        } else {
            showErrorMessage("Errore", "Seleziona un task da modificare.");
        }
    }

    /**
     * Elimina il task selezionato.
     */
    @FXML
    public void deleteTask() {
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma eliminazione");
            alert.setHeaderText("Sei sicuro di voler eliminare il task?");
            alert.setContentText("Task: " + selectedTask.getTaskName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                taskTableView.getItems().remove(selectedTask);
                currentPatientBean.getTasks().remove(selectedTask);
                System.out.println("Task eliminato: " + selectedTask.getTaskName());
            }
        } else {
            showErrorMessage("Errore", "Seleziona un task da eliminare.");
        }
    }

    /**
     * Abilita la modifica degli elementi To-Do.
     */
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

    /**
     * Salva le modifiche alla lista To-Do.
     */
    @FXML
    public void saveToDo() {
        for (HBox hBox : toDoListItems) {
            CheckBox checkBox = (CheckBox) hBox.getChildren().get(0);
            TextField textField = (TextField) hBox.getChildren().get(1);

            // Aggiorna lo stato e il contenuto dell'elemento To-Do
            if (checkBox.isSelected()) {
                System.out.println("Task completato: " + textField.getText());
            }
            textField.setEditable(false);
            checkBox.setDisable(true);
        }
        save.setDisable(true);
    }

    /**
     * Gestisce il cambiamento di selezione nei Tab.
     */
    @FXML
    private void handleTabSelectionChanged() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            String selectedTabText = selectedTab.getText();
            switch (selectedTabText) {
                case "Task":
                    configureTaskTable(currentPatientBean);
                    break;
                case "To-do List":
                    configureToDoList(currentPatientBean);
                    break;
                case "Diary":
                    configureDiary(currentPatientBean);
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
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
                root = loader.load();
            } else {
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                List<PatientBean> patientBeans = new Account().retrievePatientList(psychologistBean);
                loader = new FXMLLoader(getClass().getResource(PATIENT_LIST));
                loader.setControllerFactory(c -> new PatientListController(session));
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
