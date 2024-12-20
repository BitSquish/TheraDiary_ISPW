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
    exports com.theradiary.ispwtheradiary.controller.graphic;
    opens com.theradiary.ispwtheradiary.controller.graphic to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.account;
    opens com.theradiary.ispwtheradiary.controller.graphic.account to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.login;
    opens com.theradiary.ispwtheradiary.controller.graphic.login to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.modify;
    opens com.theradiary.ispwtheradiary.controller.graphic.modify to javafx.fxml;
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
    exports com.theradiary.ispwtheradiary.controller.graphic.homepage;
    opens com.theradiary.ispwtheradiary.controller.graphic.homepage to javafx.fxml;
    exports com.theradiary.ispwtheradiary.controller.graphic.task;
    opens com.theradiary.ispwtheradiary.controller.graphic.task to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.patterns;
    opens com.theradiary.ispwtheradiary.engineering.patterns to javafx.fxml;
    exports com.theradiary.ispwtheradiary.engineering.others.mappers;
    opens com.theradiary.ispwtheradiary.engineering.others.mappers to javafx.fxml;


}