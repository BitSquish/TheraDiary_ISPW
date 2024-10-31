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
    //Controller applicativi
    exports com.theradiary.ispwtheradiary.controller.application;
    opens com.theradiary.ispwtheradiary.controller.application to javafx.fxml;
    //Engineering classes
    exports com.theradiary.ispwtheradiary.engineering.enums;
    opens com.theradiary.ispwtheradiary.engineering.enums to javafx.fxml;
    //Model
    exports com.theradiary.ispwtheradiary.model.beans;
    opens com.theradiary.ispwtheradiary.model.beans to javafx.fxml;
    exports com.theradiary.ispwtheradiary.model;
    opens com.theradiary.ispwtheradiary.model to javafx.fxml;
}