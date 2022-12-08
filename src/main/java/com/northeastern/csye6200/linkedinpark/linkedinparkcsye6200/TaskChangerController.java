package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskChangerController implements Initializable {
    private Stage stage;
    private Scene scene;

    public static String workID;
    public static String taskName;
    public static String taskStatus;
    public static String assignedByUsername;
    public static String assignedByName;
    public static String assignedToUsername;
    public static String assignedToName;
    public static String taskDescription;
    public static String isPriority;
    public static String finishDate;

    @FXML
    TextField work_id_input;

    @FXML
    TextField task_name_input;

    @FXML
    ComboBox task_status_input;

    @FXML
    Label work_id;

    @FXML
    protected void getBackIntoWorkItems(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/adminDashboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        work_id.setText("View and Update the Task : Work ID - "+workID);
        DatabaseConnector dbs = new DatabaseConnector();
        try {
            dbs.getTaskInfo(workID);
            work_id_input.setText(workID);
            work_id_input.setDisable(true);
            task_name_input.setText(taskName);
            task_status_input.getItems().add("Yet to Start");
            task_status_input.getItems().add("Currently Running");
            task_status_input.getItems().add("Completed Successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
