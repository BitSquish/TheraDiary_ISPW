package com.theradiary.ispwtheradiary.controller.graphic.appointments;

import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentRequestGUI extends CommonGUI {
    protected AppointmentRequestGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private TableView<AppointmentBean> appointmentsReqTableView;
    @FXML
    private TableColumn<AppointmentBean, String> fullNameCol;
    @FXML
    private TableColumn<AppointmentBean, String> cityCol;
    @FXML
    private TableColumn<AppointmentBean, String> dayOfTheWeekCol;
    @FXML
    private TableColumn<AppointmentBean, String> timeSlotCol;



}
