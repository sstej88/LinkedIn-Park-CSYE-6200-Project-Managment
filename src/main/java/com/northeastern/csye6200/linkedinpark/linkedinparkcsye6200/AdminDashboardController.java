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
    public static boolean isComingBack = false;
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
        TaskControllerYetToStart.workIDs.clear();
        TaskControllerYetToStart.workNames.clear();
        TaskControllerYetToStart.assignedToNames.clear();
        TaskControllerYetToStart.assignedToUsernames.clear();
        TaskControllerRunning.workIDs.clear();
        TaskControllerRunning.workNames.clear();
        TaskControllerRunning.assignedToNames.clear();
        TaskControllerRunning.assignedToUsernames.clear();
        TaskControllerDone.workIDs.clear();
        TaskControllerDone.workNames.clear();
        TaskControllerDone.assignedToNames.clear();
        TaskControllerDone.assignedToUsernames.clear();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/master.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void getTasks() throws Exception {
        dbs.getTasksAssignedBy(LoggedInUser.username);
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
        for(int i=0; i<TaskControllerRunning.assignedToUsernames.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerRunning.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerRunning controller = fxmlLoader.<TaskControllerRunning>getController();
            controller.work_id.setText(TaskControllerRunning.workIDs.get(i));
            controller.work_name.setText(TaskControllerRunning.workNames.get(i));
            controller.assigned_to.setText(TaskControllerRunning.assignedToUsernames.get(i));
            currentTasks.getChildren().add(workItem);
        }
        for(int i=0; i<TaskControllerDone.assignedToUsernames.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerDone.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerDone controller = fxmlLoader.<TaskControllerDone>getController();
            controller.work_id.setText(TaskControllerDone.workIDs.get(i));
            controller.work_name.setText(TaskControllerDone.workNames.get(i));
            controller.assigned_to.setText(TaskControllerDone.assignedToUsernames.get(i));
            completedTasks.getChildren().add(workItem);
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

    @FXML
    protected void addTasks(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/addTask.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
