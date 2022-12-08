module com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200.javafxlinkedinpark {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
            
    opens com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200.javafxlinkedinpark to javafx.fxml;
    exports com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200.javafxlinkedinpark;
}