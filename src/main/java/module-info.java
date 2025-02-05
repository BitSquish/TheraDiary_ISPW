module com.theradiary.ispwtheradiary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    //Main
    exports com.theradiary.ispwtheradiary.start;
    opens com.theradiary.ispwtheradiary.start to javafx.fxml;
    //Controller grafici

    exports com.theradiary.ispwtheradiary.controller.graphic.gui;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.login;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.login to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.modify;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.modify to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.appointments;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.appointments to javafx.fxml;
    //Controller applicativi
    exports com.theradiary.ispwtheradiary.controller.application;
    opens com.theradiary.ispwtheradiary.controller.application to javafx.fxml;
    //Engineering classes
    exports com.theradiary.ispwtheradiary.engineering.enums;
    opens com.theradiary.ispwtheradiary.engineering.enums to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.exceptions;
    opens com.theradiary.ispwtheradiary.engineering.exceptions to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others;
    opens com.theradiary.ispwtheradiary.engineering.others to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.patterns.factory;
    opens com.theradiary.ispwtheradiary.engineering.patterns.factory to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.patterns.observer;
    opens com.theradiary.ispwtheradiary.engineering.patterns.observer to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.dao;
    opens com.theradiary.ispwtheradiary.engineering.dao to javafx.fxml;
    //Model
    exports com.theradiary.ispwtheradiary.model;
    opens com.theradiary.ispwtheradiary.model to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others.beans;
    opens com.theradiary.ispwtheradiary.engineering.others.beans to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.homepage;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.homepage to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.task;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.task to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others.mappers;
    opens com.theradiary.ispwtheradiary.engineering.others.mappers to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.dao.demo;
    opens com.theradiary.ispwtheradiary.engineering.dao.demo to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.dao.full.json;
    opens com.theradiary.ispwtheradiary.engineering.dao.full.json to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.dao.full.sql;
    opens com.theradiary.ispwtheradiary.engineering.dao.full.sql to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.gui.account;
    opens com.theradiary.ispwtheradiary.controller.graphic.gui.account to javafx.fxml;



}