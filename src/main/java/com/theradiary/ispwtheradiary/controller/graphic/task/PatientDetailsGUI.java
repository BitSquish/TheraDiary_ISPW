package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;

import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;

import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class PatientDetailsGUI extends CommonGUI {

    private final TaskAndToDoController taskAndToDoController = new TaskAndToDoController();
    public PatientDetailsGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }


    @FXML
    private Label fullName;
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
    private ObservableList<HBox> toDoListItems = FXCollections.observableArrayList();

    @FXML
    private TextArea diaryTextArea;
    private PatientBean patientBean;
    private static  final String ERROR="Errore";

    @FXML
    public void patientTask(PatientBean patientBean) {
        this.patientBean = patientBean;

        // Imposta il nome completo del paziente
        fullName.setText(patientBean.getFullName());

        // Configura la tabella dei Task
        configureTaskTable(patientBean);

        // Configura la lista To-Do
        configureToDoList();

        // Configura il diario
        configureDiary();
    }

    private void configureTaskTable(PatientBean patientBean) {
        taskAndToDoController.retrieveTasks(patientBean);
        ObservableList<TaskBean> tasks = FXCollections.observableArrayList(patientBean.getTasks());
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("taskDeadline"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        taskTableView.setItems(tasks);
    }

    private final List<TaskBean> listTask = new ArrayList<>();

    @FXML
    public void modifyTask(MouseEvent event) {
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            modify(selectedTask);
        } else {
            addTask();
        }
    }

    @FXML
    public void deleteTask(MouseEvent event) {
        TaskBean selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskTableView.getItems().remove(selectedTask);
            listTask.remove(selectedTask);
            taskAndToDoController.deleteTask(selectedTask, patientBean);
            showMessage(Alert.AlertType.INFORMATION, "Eliminazione", "Eliminazione completata");
        } else {
            showMessage(Alert.AlertType.ERROR, ERROR, "Seleziona un task da eliminare");
        }
    }

    private void modify(TaskBean selectedTask) {
        // Mostra la finestra di input per il nuovo nome del task
        String newTaskName = showInputDialog("Modifica Task", "Inserisci il nuovo nome del task", selectedTask.getTaskName());

        // Mostra la finestra di input per la nuova scadenza del task (formattata come stringa per la visualizzazione)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String newTaskDeadline = showInputDialog("Modifica Scadenza", "Inserisci la nuova scadenza del task", selectedTask.getTaskDeadline().format(formatter));

        if (newTaskName != null && newTaskDeadline != null) {
            try {
                // Converte la data inserita in LocalDate usando il formato specificato
                LocalDate taskDeadlineDate = LocalDate.parse(newTaskDeadline, formatter);

                // Imposta i nuovi valori per il task
                selectedTask.setTaskName(newTaskName);
                selectedTask.setTaskDeadline(String.valueOf(taskDeadlineDate)); // Impostiamo la scadenza come LocalDate
                //aggiungo alla lista
                TaskBean modifiedTask = new TaskBean(newTaskName, taskDeadlineDate, "non completato");
                listTask.add(modifiedTask);

                // Rende visibile la modifica nella tabella
                taskTableView.refresh();

                // Mostra il messaggio di conferma
                showMessage(Alert.AlertType.INFORMATION, "Modifica", "Modifica completata");

            } catch (Exception e) {
                // Mostra il messaggio di errore se il formato della data non è valido
                showMessage(Alert.AlertType.ERROR, ERROR, "Formato data non valido");
            }
        }
    }

    private void addTask() {
        // Mostra la finestra di input per il nome del nuovo task
        String taskName = showInputDialog("Aggiungi Task", "Inserisci il nome del task", "");

        // Mostra la finestra di input per la scadenza del task (formattata come stringa per la visualizzazione)
        String taskDeadline = showInputDialog("Aggiungi Scadenza", "Inserisci la scadenza del task", "");

        if (taskName != null && taskDeadline != null) {
            try {
                // Converte la data inserita in LocalDate usando il formato specificato
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate taskDeadlineDate = LocalDate.parse(taskDeadline, formatter);

                // Crea un nuovo task con i dati inseriti
                TaskBean newTask = new TaskBean(taskName, taskDeadlineDate, "non completato");

                // Aggiunge il nuovo task alla tabella
                taskTableView.getItems().add(newTask);
                listTask.add(newTask);

                // Mostra il messaggio di conferma
                showMessage(Alert.AlertType.INFORMATION, "Aggiunta", "Aggiunta completata");

            } catch (Exception e) {
                // Mostra il messaggio di errore se il formato della data non è valido
                showMessage(Alert.AlertType.ERROR, ERROR, "Formato data non valido");
            }
        }
    }


    @FXML
    public void saveTask(MouseEvent event) {
        for (TaskBean task : listTask) {
            taskAndToDoController.saveTasks(patientBean, task);
        }
        showMessage(Alert.AlertType.INFORMATION, "Salvataggio", "Salvataggio completato");
    }


    //Diario
    private void configureDiary() {
        taskAndToDoController.getDiaryForToday(patientBean);
        String diaryText = patientBean.getDiary();
        diaryTextArea.setText(diaryText == null || diaryText.isEmpty() ? "Diario vuoto" : diaryText);
    }

    //ToDoList
    private void configureToDoList() {
        toDoListItems.clear();
        taskAndToDoController.toDoList(patientBean);
        List<ToDoItemBean> toDoItems = patientBean.getToDoList();
        for (ToDoItemBean item : toDoItems) {
            if (toDoListItems.stream().noneMatch(hBox -> ((TextField) hBox.getChildren().get(1)).getText().equals(item.getToDo()))) {
                HBox itemBox = createToDoItem(item);
                toDoListItems.add(itemBox);
            }
        }
        toDoListView.setItems(toDoListItems);
    }

    private HBox createToDoItem(ToDoItemBean toDoItemBean) {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(toDoItemBean.isCompleted());
        TextField textField = new TextField(toDoItemBean.getToDo());
        textField.setPrefWidth(200);
        textField.textProperty().addListener((obs, oldValue, newValue) -> toDoItemBean.setToDo(newValue.trim()));
        Button deleteButton = new Button("Elimina");
        deleteButton.setOnAction(e -> {
            toDoListItems.removeIf(hBox -> hBox.getChildren().contains(deleteButton));
            patientBean.removeToDoItem(toDoItemBean);
            taskAndToDoController.deleteToDo(toDoItemBean, patientBean);
            toDoListView.setItems(toDoListItems);
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(checkBox, textField, deleteButton);
        return hbox;
    }

    @FXML
    private void modifyToDo(MouseEvent mouseEvent) {
        ToDoItemBean newItem = new ToDoItemBean("", false);
        patientBean.getToDoList().add(newItem);

        HBox itemBox = createToDoItem(newItem);
        toDoListItems.add(itemBox);
        toDoListView.setItems(toDoListItems);
    }

    @FXML
    public void saveToDo(MouseEvent event) {
        List<ToDoItemBean> toDoItems = patientBean.getToDoList();
        Set<String> uniqueDescriptions = new HashSet<>();
        toDoItems.removeIf(item -> !uniqueDescriptions.add(item.getToDo().trim()));

        for (HBox itemBox : toDoListItems) {
            CheckBox checkbox = (CheckBox) itemBox.getChildren().get(0);
            TextField textField = (TextField) itemBox.getChildren().get(1);
            String toDoText = textField.getText().trim();
            if (!toDoText.isEmpty()) {
                toDoItems.add(new ToDoItemBean(toDoText, checkbox.isSelected()));
            }
            taskAndToDoController.saveToDo(new ToDoItemBean(toDoText, checkbox.isSelected()), patientBean);
        }
        showMessage(Alert.AlertType.INFORMATION, "Salvataggio", "Salvataggio completato");

    }
}




