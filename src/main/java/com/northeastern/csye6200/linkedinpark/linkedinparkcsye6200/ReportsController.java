package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    private Stage stage;
    private Scene scene;
    public static ArrayList<PendingTask> tasks = new ArrayList<>();

    @FXML
    HBox pendingTasks;

    @FXML
    VBox lowPerformanceTasks;

    @FXML
    Label total_tasks;

    @FXML
    Label pending_tasks;

    @FXML
    Label overdue_tasks;

    @FXML
    Button back;

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

            ResultSet poorPerformanceRS = dbs.getPoorPerformance();
            LowPerformanceReports.pfm.clear();
            while(poorPerformanceRS.next()) {
                PerformanceMetricsLayout pl = new PerformanceMetricsLayout();
                pl.name = poorPerformanceRS.getString("name");
                pl.totalTasks = poorPerformanceRS.getString("TotalTasks");
                pl.pendingTasks = poorPerformanceRS.getString("PendingTasks");
                pl.overdueTasks = poorPerformanceRS.getString("MissedDeadlines");
                LowPerformanceReports.pfm.add(pl);
            }
            System.out.println(LowPerformanceReports.pfm.size());
            for(int i=0; i<LowPerformanceReports.pfm.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("fxml/lowPerformanceReports.fxml"));
                AnchorPane taskItem;
                try {
                    taskItem = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LowPerformanceReports controller = fxmlLoader.<LowPerformanceReports>getController();

                controller.name.setText("Name : "+LowPerformanceReports.pfm.get(i).name);
                controller.totalTasks.setText("Total Tasks : "+LowPerformanceReports.pfm.get(i).totalTasks);
                controller.pendingTasks.setText("Pending Tasks : "+LowPerformanceReports.pfm.get(i).pendingTasks);
                controller.overdueTasks.setText("Overdue Tasks : "+LowPerformanceReports.pfm.get(i).overdueTasks);
                lowPerformanceTasks.getChildren().add(taskItem);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



//System.out.println(tasks.size());
