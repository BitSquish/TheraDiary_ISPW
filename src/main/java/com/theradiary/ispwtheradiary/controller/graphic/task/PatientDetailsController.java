package com.theradiary.ispwtheradiary.controller.graphic.task;
import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class PatientDetailsController extends CommonController {

    public PatientDetailsController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    private Label fullName;

    @FXML
    private TabPane tabPane;


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
    private ObservableList<HBox> toDoListItems= FXCollections.observableArrayList();

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
       configureToDoList();

        // Configura il diario
        configureDiary();
    }

   /* private void configureTaskTable(PatientBean patientBean) {
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));*/





    //Diario
    private void configureDiary() {
        TaskAndToDo taskAndToDo = new TaskAndToDo();
        taskAndToDo.getDiaryForToday(patientBean);
        String diaryText = patientBean.getDiary();
        diaryTextArea.setText(diaryText == null || diaryText.isEmpty() ? "Diario vuoto" : diaryText);
    }
    //ToDoList
    private void configureToDoList() {
        TaskAndToDo.toDoList(patientBean);
        List<ToDoItemBean> toDoItems=patientBean.getToDoList();
        for(ToDoItemBean item:toDoItems){
            HBox itemBox=createToDoItem(item.getToDo(),item.isCompleted());
            toDoListItems.add(itemBox);
        }
        toDoListView.setItems(toDoListItems);
    }
    private HBox createToDoItem(String taskDescription,boolean isCompleted){
        CheckBox checkBox=new CheckBox();
        checkBox.setSelected(isCompleted);
        TextField textField=new TextField(taskDescription);
        textField.setPrefWidth(200);
        Button deleteButton=new Button("Elimina");
        deleteButton.setOnAction(e->{
            toDoListItems.removeIf(hBox -> hBox.getChildren().contains(deleteButton));
            toDoListView.setItems(toDoListItems);
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(checkBox,textField);
        return hbox;
    }

    @FXML
    private void modifyToDo(MouseEvent mouseEvent) {
        HBox itemBox=createToDoItem("",false);
        toDoListItems.add(itemBox);
        toDoListView.getItems().setAll(toDoListItems);
    }

    @FXML
    public void saveToDo(){
        configureToDoList();
        for(ToDoItemBean item:patientBean.getToDoList()){
            TaskAndToDo.saveToDo(item,patientBean);
        }
    }
    @FXML
    public void modifyTask() {}
    @FXML
    public void deleteTask(){}
    @FXML
    private void handleTabSelectionChanged() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab == null) {
            System.out.println("Nessuna tab selezionata");
            return;
        }
        String selectedTabText = selectedTab.getText();
            switch (selectedTabText) {
                case "Task":
                    System.out.println("Task");
                    /*configureTaskTable(patientBean);*/
                    break;
                case "To-do List":
                   configureToDoList();
                    break;
                case "Diary":
                    configureDiary();
                    break;
                default:
                    break;
            }
    }



    private void showMessage(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
