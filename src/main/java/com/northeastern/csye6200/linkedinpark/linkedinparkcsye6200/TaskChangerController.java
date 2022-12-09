package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class TaskChangerController implements Initializable {
    private Stage stage;
    private Scene scene;

    private ArrayList<Users> memberList = new ArrayList<Users>();

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
    Label work_id;

    @FXML
    RadioButton start;

    @FXML
    RadioButton running;

    @FXML
    RadioButton done;

    @FXML
    ToggleGroup status_input = new ToggleGroup();

    @FXML
    TextArea description_input;

    @FXML
    DatePicker finish_date_input;

    @FXML
    ComboBox assignTo;

    @FXML
    Button deleteButton;

    @FXML
    Button updateButton;

    @FXML
    Label statusMessage;

    @FXML
    CheckBox isPriorityTask;

    @FXML
    Label assignedLabel;

    @FXML
    protected void getBackIntoWorkItems(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/adminDashboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void deleteTask(ActionEvent e) throws Exception {
        DatabaseConnector dbs = new DatabaseConnector();
        dbs.deleteTask(workID, assignedByUsername);
        getBackIntoWorkItems(e);
    }
    @FXML
    protected void updateTask(ActionEvent e) throws Exception {
        DatabaseConnector dbs = new DatabaseConnector();
        String status = "";
        String assignedTo = assignedToName;
        String isPriorityTaskLocal = "0";
        if(isPriorityTask.isSelected()) {
            isPriorityTaskLocal = "1";
        }
        if(start.isSelected()) {
            status = "Start";
        }
        else if(running.isSelected()) {
            status = "Running";
        }
        else {
            status = "Done";
        }
        if(assignTo.getValue() != null) {
            assignedTo = (String)assignTo.getValue();
        }
//        System.out.println(workID);
//        System.out.println(task_name_input.getText());
//        System.out.println(status);
//        System.out.println(assignedTo);
//        System.out.println(description_input.getText());
//        System.out.println(localDate);
        if(finish_date_input.getValue() != null) {
            if(finish_date_input.getValue().isBefore(LocalDate.now())) {
                statusMessage.setText("You have selected previous date as finish date! Please select other date and try.");
                statusMessage.setTextFill(Color.RED);
            }
            else {
                dbs.updateTask(workID, isPriorityTaskLocal, task_name_input.getText(), status, dbs.getUsername(assignedTo), assignedTo, description_input.getText(), finish_date_input.getValue());
                getBackIntoWorkItems(e);
            }
        }
        else {
            dbs.updateTask(workID, isPriorityTaskLocal, task_name_input.getText(), status, dbs.getUsername(assignedTo), assignedTo, description_input.getText());
            getBackIntoWorkItems(e);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusMessage.setText("");
        work_id.setText("View and Update the Task : Work ID - "+workID);
        DatabaseConnector dbs = new DatabaseConnector();
        try {
            dbs.getTaskInfo(workID);
            work_id_input.setText(workID);
            work_id_input.setDisable(true);
            task_name_input.setText(taskName);
            start.setToggleGroup(status_input);
            running.setToggleGroup(status_input);
            done.setToggleGroup(status_input);

            if(taskStatus.equals("Start")) {
                start.setSelected(true);
            }
            else if(taskStatus.equals("Running")) {
                running.setSelected(true);
            }
            else if(taskStatus.equals("Done")) {
                done.setSelected(true);
            }

            description_input.setText(taskDescription);

            finish_date_input.setPromptText(finishDate);

            try {
                dbs = new DatabaseConnector();
                memberList = dbs.getTeamMembersList();

                memberList = memberList;

                for (Users element : memberList) {
                    assignTo.getItems().add(element.name);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            assignTo.setPromptText(assignedToName);

            if(isPriority.equals("0")) {
                isPriorityTask.setSelected(false);
            }
            else {
                isPriorityTask.setSelected(true);
            }

            if(LoggedInUser.role.equals("Team Member")) {
                isPriorityTask.setDisable(true);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(LoggedInUser.role.equals("Team Member")) {
            deleteButton.setDisable(true);
            finish_date_input.setDisable(true);
            assignTo.setDisable(true);
            assignedLabel.setText("Assigned By : ");
        }
    }
}
