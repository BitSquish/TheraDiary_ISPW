package com.theradiary.ispwtheradiary.view.gui.task;


import com.theradiary.ispwtheradiary.controller.TaskAndToDoPtController;
import com.theradiary.ispwtheradiary.view.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.TaskBean;
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


public class TaskPatientGUI extends CommonGUI {
    public TaskPatientGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    TaskAndToDoPtController taskAndToDoController = new TaskAndToDoPtController();
    PatientBean patientBean = (PatientBean) session.getUser();
    private final ObservableList<TaskBean> tasks = FXCollections.observableArrayList();
    private final List<TaskBean> modifiedTasks = new ArrayList<>();
    @FXML
    private TableView<TaskBean> taskTableView;
    @FXML
    private TableColumn<TaskBean, String> taskNameColumn;

    @FXML
    private TableColumn<TaskBean, String> taskDeadlineColumn;

    @FXML
    private TableColumn<TaskBean, String> taskStatusColumn;

    public void initializeTaskList(PatientBean patientBean) {
        //recupero i task
        taskAndToDoController.retrieveTasks(patientBean);
        tasks.clear();
        if(patientBean.getTasks().isEmpty()){
            showMessage(Alert.AlertType.INFORMATION, "Task", "Il tuo psicologo non ha ancora assegnato delle task");
        }
        List<TaskBean> uniqueTasks = removeDuplicates(patientBean.getTasks());
        tasks.addAll(uniqueTasks);
        //configuro le colonne
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        //imposto gli elementi della tabella
        taskTableView.setItems(tasks);
    }

    @FXML
    public void modifyTask(MouseEvent event){
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if(selectedTask!=null){
            String newStatus=showInputDialog("Modifica stato", "Inserisci il nuovo stato del task (completato/non completato)",selectedTask.getTaskStatus());
            if(newStatus!=null){
                try{
                    selectedTask.setTaskStatus(newStatus);
                    if (!modifiedTasks.contains(selectedTask)) {
                        modifiedTasks.add(selectedTask);
                    }
                    taskTableView.refresh();

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
            try {
                for (TaskBean task : modifiedTasks) {
                    taskAndToDoController.updateTasks(patientBean, task);
                }
                modifiedTasks.clear();
                showMessage(Alert.AlertType.INFORMATION, "Salvataggio completato", "Salvataggio completato con successo");
            }catch(Exception e){
                showMessage(Alert.AlertType.ERROR,"Errore","Errore durante il salvataggio");
            }
        }else {
            showMessage(Alert.AlertType.INFORMATION,"Salvataggio completato","Nessuna modifica da salvare");
        }

    }
    private List<TaskBean> removeDuplicates(List<TaskBean> tasks){
        return tasks.stream().distinct().toList();
    }
}
