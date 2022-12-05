module com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200 {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
//    requires MaterialFX;

    opens com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200 to javafx.fxml;
    exports com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;
}