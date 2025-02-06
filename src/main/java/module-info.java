module com.theradiary.ispwtheradiary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    //Main
    //Controller grafici

    exports com.theradiary.ispwtheradiary.view.gui;
    opens com.theradiary.ispwtheradiary.view.gui to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.login;
    opens com.theradiary.ispwtheradiary.view.gui.login to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.modify;
    opens com.theradiary.ispwtheradiary.view.gui.modify to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.appointments;
    opens com.theradiary.ispwtheradiary.view.gui.appointments to javafx.fxml;
    //Controller applicativi
    exports com.theradiary.ispwtheradiary.controller;
    opens com.theradiary.ispwtheradiary.controller to javafx.fxml;
    //Engineering classes
    exports com.theradiary.ispwtheradiary.engineering.enums;
    opens com.theradiary.ispwtheradiary.engineering.enums to javafx.fxml;
    exports com.theradiary.ispwtheradiary.exceptions;
    opens com.theradiary.ispwtheradiary.exceptions to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others;
    opens com.theradiary.ispwtheradiary.engineering.others to javafx.fxml;
    exports com.theradiary.ispwtheradiary.patterns.factory;
    opens com.theradiary.ispwtheradiary.patterns.factory to javafx.fxml;
    exports com.theradiary.ispwtheradiary.patterns.observer;
    opens com.theradiary.ispwtheradiary.patterns.observer to javafx.fxml;
    exports com.theradiary.ispwtheradiary.dao;
    opens com.theradiary.ispwtheradiary.dao to javafx.fxml;
    //Model
    exports com.theradiary.ispwtheradiary.model;
    opens com.theradiary.ispwtheradiary.model to javafx.fxml;
    exports com.theradiary.ispwtheradiary.beans;
    opens com.theradiary.ispwtheradiary.beans to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.homepage;
    opens com.theradiary.ispwtheradiary.view.gui.homepage to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.task;
    opens com.theradiary.ispwtheradiary.view.gui.task to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others.mappers;
    opens com.theradiary.ispwtheradiary.engineering.others.mappers to javafx.fxml;
    exports com.theradiary.ispwtheradiary.dao.demo;
    opens com.theradiary.ispwtheradiary.dao.demo to javafx.fxml;
    exports com.theradiary.ispwtheradiary.dao.full.json;
    opens com.theradiary.ispwtheradiary.dao.full.json to javafx.fxml;
    exports com.theradiary.ispwtheradiary.dao.full.sql;
    opens com.theradiary.ispwtheradiary.dao.full.sql to javafx.fxml;
    exports com.theradiary.ispwtheradiary.view.gui.account;
    opens com.theradiary.ispwtheradiary.view.gui.account to javafx.fxml;
    exports com.theradiary.ispwtheradiary;
    opens com.theradiary.ispwtheradiary to javafx.fxml;


}