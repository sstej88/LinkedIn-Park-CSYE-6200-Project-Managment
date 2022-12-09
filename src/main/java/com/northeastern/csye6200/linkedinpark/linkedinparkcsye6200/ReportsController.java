package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    public static ArrayList<PendingTask> tasks = new ArrayList<>();

    @FXML
    HBox pendingTasks;

    @FXML
    Label total_tasks;

    @FXML
    Label pending_tasks;

    @FXML
    Label overdue_tasks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnector dbs = new DatabaseConnector();
        try {
            dbs.getPendingTasks();
            for(int i=0; i<tasks.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("fxml/pendingTaskLayout.fxml"));
                AnchorPane taskItem;
                try {
                    taskItem = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                PendingTasksController controller = fxmlLoader.<PendingTasksController>getController();

                controller.task_id.setText(tasks.get(i).taskID);
                controller.task_name.setText(tasks.get(i).taskName);
                controller.task_status.setText(tasks.get(i).taskStatus);
                controller.assigned_to_username.setText(tasks.get(i).assignedTo);
                pendingTasks.getChildren().add(taskItem);
            }

            ResultSet rs = dbs.getStatistics();
            rs.next();
            total_tasks.setText(rs.getString("TotalTasks"));
            pending_tasks.setText(rs.getString("PendingTasks"));
            overdue_tasks.setText(rs.getString("MissedDeadlines"));
            overdue_tasks.setTextFill(Color.RED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



//System.out.println(tasks.size());
