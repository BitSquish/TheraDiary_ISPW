module com.theradiary.ispwtheradiary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    //exports com.theradiary.ispwtheradiary;
    exports com.theradiary.ispwtheradiary.start;
    opens com.theradiary.ispwtheradiary.start to javafx.fxml;
}