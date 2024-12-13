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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        TaskAndToDo.getDiaryForToday(patientBean);
        String diaryText = patientBean.getDiary();
        diaryTextArea.setText(diaryText == null || diaryText.isEmpty() ? "Diario vuoto" : diaryText);
    }
    //ToDoList
    private void configureToDoList() {
        toDoListItems.clear();
        TaskAndToDo.toDoList(patientBean);
        List<ToDoItemBean> toDoItems = patientBean.getToDoList();
        for(ToDoItemBean item: toDoItems){
            if(toDoListItems.stream().noneMatch(hBox -> ((TextField) hBox.getChildren().get(1)).getText().equals(item.getToDo()))) {
                HBox itemBox = createToDoItem(item);
                toDoListItems.add(itemBox);
            }
        }
        toDoListView.setItems(toDoListItems);
    }
    private HBox createToDoItem(ToDoItemBean toDoItemBean) {
        CheckBox checkBox=new CheckBox();
        checkBox.setSelected(toDoItemBean.isCompleted());
        TextField textField=new TextField(toDoItemBean.getToDo());
        textField.setPrefWidth(200);
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            toDoItemBean.setToDo(newValue.trim());
        });
        Button deleteButton=new Button("Elimina");
        deleteButton.setOnAction(e->{
            toDoListItems.removeIf(hBox -> hBox.getChildren().contains(deleteButton));
            patientBean.removeToDoItem(toDoItemBean);
            TaskAndToDo.deleteToDo(toDoItemBean,patientBean);
            toDoListView.setItems(toDoListItems);
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(checkBox,textField,deleteButton);
        return hbox;
    }

    @FXML
    private void modifyToDo(MouseEvent mouseEvent) {
        ToDoItemBean newItem=new ToDoItemBean("",false);
        patientBean.getToDoList().add(newItem);
        TaskAndToDo.saveToDo(newItem,patientBean);

        HBox itemBox=createToDoItem(newItem);
        toDoListItems.add(itemBox);
        toDoListView.setItems(toDoListItems);
    }

    @FXML
    public void saveToDo(MouseEvent event) {
        List<ToDoItemBean> toDoItems=patientBean.getToDoList();
        Set<String> uniqueDescriptions=new HashSet<>();
        toDoItems.removeIf(item -> !uniqueDescriptions.add(item.getToDo().trim()));

        for(HBox itemBox: toDoListItems){
            CheckBox checkbox=(CheckBox) itemBox.getChildren().get(0);
            TextField textField=(TextField) itemBox.getChildren().get(1);
            String toDoText=textField.getText().trim();
            if(!toDoText.isEmpty()){
                toDoItems.add(new ToDoItemBean(toDoText,checkbox.isSelected()));
            }
            TaskAndToDo.saveToDo(new ToDoItemBean(toDoText,checkbox.isSelected()),patientBean);
        }
        showMessage(Alert.AlertType.INFORMATION,"Salvataggio","Salvataggio completato");

    }
    @FXML
    public void modifyTask() {}
    @FXML
    public void deleteTask(){}
    @FXML
    private void handleTabSelectionChanged() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab == null) return;
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
