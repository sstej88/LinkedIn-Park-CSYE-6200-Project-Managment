package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    DatabaseConnector dbs;
    private Stage stage;
    private Scene scene;
    @FXML
    VBox newTasks;

    @FXML
    VBox currentTasks;

    @FXML
    VBox completedTasks;

    @FXML
    Label welcomeAndName;
    @FXML
    Label position;
    @FXML
    Hyperlink usernameAndSignout;

    @FXML
    protected void signout(ActionEvent e) throws Exception {
        LoggedInUser.name = "";
        LoggedInUser.role = "";
        LoggedInUser.username = "";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/master.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void getTasks() throws Exception {
        dbs.getTasksAssignedBy(LoggedInUser.username, "Start");
        dbs.getTasksAssignedBy(LoggedInUser.username, "Running");
        dbs.getTasksAssignedBy(LoggedInUser.username, "Done");
        for(int i=0; i<TaskControllerYetToStart.assignedToUsernames.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerYetToStart.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerYetToStart controller = fxmlLoader.<TaskControllerYetToStart>getController();
            controller.work_id.setText(TaskControllerYetToStart.workIDs.get(i));
            controller.work_name.setText(TaskControllerYetToStart.workNames.get(i));
            controller.assigned_to.setText(TaskControllerYetToStart.assignedToUsernames.get(i));
            newTasks.getChildren().add(workItem);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbs = new DatabaseConnector();
        welcomeAndName.setText("Welcome, "+LoggedInUser.name);
        position.setText(LoggedInUser.role);
        usernameAndSignout.setText("Signout ("+LoggedInUser.username+")");
        try {
            getTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}