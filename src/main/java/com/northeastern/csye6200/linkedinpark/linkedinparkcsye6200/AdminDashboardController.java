package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    Button btnAddTask;

    @FXML
    Label BubbleLabelStart;
    @FXML
    Label BubbleLabelProgress;
    @FXML
    Label BubbleLabelCompleted;

    @FXML
    protected void signout(ActionEvent e) throws Exception {
        LoggedInUser.name = "";
        LoggedInUser.role = "";
        LoggedInUser.username = "";
        TaskControllerYetToStart.taskList.clear();
        TaskControllerRunning.taskList.clear();
        TaskControllerDone.taskList.clear();
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
        for(int i=0; i<TaskControllerYetToStart.taskList.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerYetToStart.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerYetToStart controller = fxmlLoader.<TaskControllerYetToStart>getController();

            controller.work_id.setText(TaskControllerYetToStart.taskList.get(i).taskId.toString());
            controller.work_name.setText(TaskControllerYetToStart.taskList.get(i).task_name);
            controller.assigned_to.setText(TaskControllerYetToStart.taskList.get(i).assignedName);

            newTasks.getChildren().add(workItem);

        }
        BubbleLabelStart.setText(TaskControllerYetToStart.taskList.size() + "");
        for(int i=0; i<TaskControllerRunning.taskList.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerRunning.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerRunning controller = fxmlLoader.<TaskControllerRunning>getController();

            controller.work_id.setText(TaskControllerRunning.taskList.get(i).taskId.toString());
            controller.work_name.setText(TaskControllerRunning.taskList.get(i).task_name);
            controller.assigned_to.setText(TaskControllerRunning.taskList.get(i).assignedName);
            currentTasks.getChildren().add(workItem);
        }
        BubbleLabelProgress.setText(TaskControllerRunning.taskList.size() + "");
        for(int i=0; i<TaskControllerDone.taskList.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/taskShowerDone.fxml"));
            BorderPane workItem = fxmlLoader.load();
            TaskControllerDone controller = fxmlLoader.<TaskControllerDone>getController();

            controller.work_id.setText(TaskControllerDone.taskList.get(i).taskId.toString());
            controller.work_name.setText(TaskControllerDone.taskList.get(i).task_name);
            controller.assigned_to.setText(TaskControllerDone.taskList.get(i).assignedName);
            completedTasks.getChildren().add(workItem);
        }
        BubbleLabelCompleted.setText(TaskControllerDone.taskList.size() + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbs = new DatabaseConnector();
        welcomeAndName.setText("Welcome, "+LoggedInUser.name);
        position.setText(LoggedInUser.role);
        usernameAndSignout.setText("Signout ("+LoggedInUser.username+")");

        // Enable/disable add task option according to user role
        if(LoggedInUser.role.equals("Team Manager")) {
            btnAddTask.setVisible(true);
        }
        else  {
            btnAddTask.setVisible(false);
        }

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

    @FXML
    protected void viewReports(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/reports.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
