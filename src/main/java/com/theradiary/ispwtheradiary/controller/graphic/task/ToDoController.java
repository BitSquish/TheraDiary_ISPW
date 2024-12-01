package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.ToDoItemBean;
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

public class ToDoController extends CommonController {
    protected ToDoController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    @FXML
    private ListView<HBox> toDoListView;
    @FXML
    private ObservableList<HBox> toDoListItems= FXCollections.observableArrayList();
    @FXML
    public void initializeToDoList(PatientBean patientBean) {
        TaskAndToDo taskAndToDo = new TaskAndToDo();
        taskAndToDo.ToDoList(patientBean);
        List< ToDoItemBean > toDoItems = patientBean.getToDoList();
        if (toDoItems != null) {
            for (ToDoItemBean toDoItem : toDoItems) {
                HBox itemBox = createToDoItem(toDoItem.getToDo(), toDoItem.isCompleted());
                toDoListItems.add(itemBox);
                toDoListView.setItems(toDoListItems);
            }
        }
        toDoListView.setItems(toDoListItems);

    }
    private HBox createToDoItem(String description, boolean completed) {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(completed);

        Label label = new Label(description);
        label.setPrefWidth(200);


        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(checkBox,label);

        return hbox;
    }
    @FXML
    public void saveToDoList(MouseEvent event) {
        PatientBean patientBean = (PatientBean) session.getUser();
        ObservableList<ToDoItemBean> savedToDoItems = FXCollections.observableArrayList();
        for(HBox itemBox : toDoListItems) {
            CheckBox checkBox = (CheckBox) itemBox.getChildren().get(0);
            Label label = (Label) itemBox.getChildren().get(1);
            savedToDoItems.add(new ToDoItemBean(label.getText(), checkBox.isSelected()));
        }
        try {
            TaskAndToDo taskAndToDo = new TaskAndToDo();
            taskAndToDo.saveToDoList(savedToDoItems, patientBean);
            showMessage(Alert.AlertType.INFORMATION, "Salvataggio", "Lista ToDo salvata correttamente");
        }catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Errore", "Errore durante il salvataggio della lista ToDo");
        }
    }
    private void showMessage(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_AND_TASKS_PATH)));
                loader.setControllerFactory(c -> new DiaryAndTasksController(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
    }

}
