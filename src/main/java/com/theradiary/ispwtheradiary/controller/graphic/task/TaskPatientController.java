package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class TaskPatientController extends CommonController {
    public TaskPatientController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    PatientBean patientBean = (PatientBean) session.getUser();
    @FXML
    private TableView<TaskBean> taskTableView;
    @FXML
    private TableColumn<TaskBean, String> taskNameColumn;

    @FXML
    private TableColumn<TaskBean, String> taskDeadlineColumn;

    @FXML
    private TableColumn<TaskBean, String> taskStatusColumn;


    public void initializeTaskList(PatientBean patientBean) {
        ObservableList<TaskBean> taskList = FXCollections.observableArrayList();
        TaskAndToDo.retrieveTasks(patientBean);
        taskList.addAll(patientBean.getTasks());
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        taskTableView.setItems(taskList);
    }
    private final List<TaskBean> modifiedTasks = new ArrayList<>();
    @FXML
    public void modifyTask(MouseEvent event){
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if(selectedTask!=null){
            String newStatus=showInputDialog("Modifica stato", "Inserisci il nuovo stato del task (completato/non completato)",selectedTask.getTaskStatus());
            if(newStatus!=null){
                try{
                    selectedTask.setTaskStatus(newStatus);
                    taskTableView.refresh();
                    if(!modifiedTasks.contains(selectedTask)){
                        modifiedTasks.add(selectedTask);
                    }

                    showMessage(Alert.AlertType.INFORMATION,"Modifica completata","Modifica completata con successo");
                }catch(Exception e){
                    showMessage(Alert.AlertType.ERROR,"Errore","Errore durante la modifica");

                }
            }
        }
    }
    @FXML
    public void saveTask(MouseEvent event){
        if(!modifiedTasks.isEmpty()){
            for(TaskBean task:modifiedTasks){
                TaskAndToDo.updateTasks(patientBean,task);

            }
            modifiedTasks.clear();
            showMessage(Alert.AlertType.INFORMATION,"Salvataggio completato","Salvataggio completato con successo");
        }else {
            showMessage(Alert.AlertType.INFORMATION,"Salvataggio completato","Nessuna modifica da salvare");
        }

    }
}
