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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTaskController implements Initializable {
    DatabaseConnector dbs;

    private ArrayList<Users> memberList = new ArrayList<Users>();
    private Stage stage;
    private Scene scene;
//    public static String workID;

    @FXML
    TextField taskTitle;
    @FXML
    TextArea taskDescription;
    @FXML
    ComboBox assignTo;
    @FXML
    DatePicker finishByDate = new DatePicker();

    @FXML
    CheckBox togglePriority;

    @FXML
    Label errorLabel;

    @FXML
    protected void ReturnToDashboard(ActionEvent e) throws Exception {
        AdminDashboardController.isComingBack = true;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/adminDashboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

//    protected void ReturnToDashboard() throws Exception {
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }
    @FXML
    protected void ValidateNewTask(ActionEvent e) throws Exception {
        String newTitle = taskTitle.getText();
        String newDescription = taskDescription.getText();
//        LocalDate localDate = finishByDate.getValue();
        Date getDatePickerDate = null;
        Boolean isPriority = togglePriority.isSelected();
        String assignedName = "";
        String assignedUserName = "";
        String assignedByUserName = "";

        if(newTitle.equals(null) || newTitle.equals("")){
            errorLabel.setText("Please enter a title.");
            errorLabel.setTextFill(Color.RED);
            return;
        } if(assignTo.getValue() == null) {
            errorLabel.setText("Please assign the task to a team member.");
            errorLabel.setTextFill(Color.RED);
            return;
        }
        if(finishByDate.getValue() != null) {
            getDatePickerDate = java.sql.Date.valueOf(finishByDate.getValue());
        }
        assignedName = assignTo.getValue().toString();

        // get id of assigned user
        for (Users users : memberList) {
            if (users.name.equals(assignedName)) {
                assignedUserName = users.username;
                break;
            }
        }

        SaveNewTask(newTitle, newDescription,   getDatePickerDate, isPriority, assignedUserName, assignedName, LoggedInUser.username, LoggedInUser.name);
        ReturnToDashboard(e);
    }

    private void SaveNewTask(String Title, String Description, Date FinishByDate, Boolean isPriority, String assignedUserName, String assignedName, String assignedByUserName, String assignedByName) throws Exception {
        dbs.InsertNewTask(Title, Description, FinishByDate, isPriority, assignedUserName, assignedName, assignedByUserName, assignedByName);
    }
}
