module com.theradiary.ispwtheradiary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.theradiary.ispwtheradiary to javafx.fxml;
    exports com.theradiary.ispwtheradiary;
}