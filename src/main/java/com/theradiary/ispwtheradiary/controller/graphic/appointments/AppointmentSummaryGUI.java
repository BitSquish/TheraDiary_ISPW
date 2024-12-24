package com.theradiary.ispwtheradiary.controller.graphic.appointments;

import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AppointmentSummaryGUI extends CommonGUI {
    protected AppointmentSummaryGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private TableView<AppointmentBean> appointmentsReqTableView;
    @FXML
    private TableColumn<AppointmentBean, String> patientCol;
    @FXML
    private TableColumn<AppointmentBean, String> dayOfTheWeekCol;
    @FXML
    private TableColumn<AppointmentBean, String> timeSlotCol;
    @FXML
    private TableColumn<AppointmentBean, String> modalityCol;

    @FXML
    public void printAppointment(MouseEvent event, List<AppointmentBean> appointmentBeans) {
        // Creazione della lista osservabile
        ObservableList<AppointmentBean> appointmentBeanObservableList = FXCollections.observableArrayList(appointmentBeans);
        // Configurazione delle colonne
        patientCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean()));
        dayOfTheWeekCol.setCellValueFactory(cellData -> new SimpleStringProperty(DayOfTheWeek.translateDay(cellData.getValue().getDay().getId())));
        timeSlotCol.setCellValueFactory(cellData -> new SimpleStringProperty(TimeSlot.translateTimeSlot(cellData.getValue().getTimeSlot().getId())));
        modalityCol.setCellValueFactory(cellData -> {
            boolean inPerson = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(inPerson ? "In presenza" : "Online");
        });
        appointmentsReqTableView.setItems(appointmentBeanObservableList);
    }



    }
