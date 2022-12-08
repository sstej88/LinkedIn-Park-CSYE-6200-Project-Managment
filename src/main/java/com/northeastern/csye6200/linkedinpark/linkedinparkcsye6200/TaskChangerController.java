package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
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
        String status;
        if(start.isSelected()) {
            status = "Start";
        }
        else if(running.isSelected()) {
            status = "Running";
        }
        else {
            status = "Done";
        }
        dbs.updateTask(workID, task_name_input.getText(), status, dbs.getUsername((String) assignTo.getValue()), (String) assignTo.getValue(), description_input.getText(), finish_date_input.getValue());
        getBackIntoWorkItems(e);
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
