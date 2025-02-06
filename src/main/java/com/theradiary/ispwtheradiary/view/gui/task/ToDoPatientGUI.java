package com.theradiary.ispwtheradiary.view.gui.task;


import com.theradiary.ispwtheradiary.controller.TaskAndToDoPtController;
import com.theradiary.ispwtheradiary.view.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.ToDoItemBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Iterator;
import java.util.List;

public class ToDoPatientGUI extends CommonGUI {
    protected ToDoPatientGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    @FXML
    private ListView<HBox> toDoListView;
    @FXML
    private ObservableList<HBox> toDoListItems= FXCollections.observableArrayList();
    private final TaskAndToDoPtController taskAndToDoController = new TaskAndToDoPtController();

    private boolean isDuplicate(String toDo) {
        for (HBox itemBox : toDoListItems) {
            Label label = (Label) itemBox.getChildren().get(1);
            if (label.getText().equals(toDo)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void initializeToDoList(PatientBean patientBean) {
        taskAndToDoController.retrieveToDoList(patientBean);
        List< ToDoItemBean > toDoItems = patientBean.getToDoList();
        if (toDoItems != null) {
            for (ToDoItemBean toDoItem : toDoItems) {
                if(!isDuplicate(toDoItem.getToDo())) {
                    HBox itemBox = createToDoItem(toDoItem.getToDo(), toDoItem.isCompleted());
                    toDoListItems.add(itemBox);
                }
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
    public void completeToDo(MouseEvent event) {
        Iterator<HBox> iterator = toDoListItems.iterator();
        while (iterator.hasNext()) {
            HBox itemBox = iterator.next();
            CheckBox checkBox = (CheckBox) itemBox.getChildren().get(0);
            Label label = (Label) itemBox.getChildren().get(1);

            if (checkBox.isSelected()) {
                String toDoText = label.getText().trim();
                if (!toDoText.isEmpty()) {
                    // Completa il To-Do
                    taskAndToDoController.deleteToDo(new ToDoItemBean(toDoText, true), (PatientBean) session.getUser());
                    // Rimuovi dalla lista in modo sicuro
                    iterator.remove();

                }
            }
        }
        showMessage(Alert.AlertType.INFORMATION, "Completamento", "Elemento completato");
        // Aggiorna la vista dopo aver modificato la lista
        toDoListView.setItems(toDoListItems);


    }


}
