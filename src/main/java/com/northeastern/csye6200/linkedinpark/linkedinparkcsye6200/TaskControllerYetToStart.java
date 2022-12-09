package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskControllerYetToStart implements Initializable {
    private Stage stage;
    private Scene scene;

    public static ArrayList<DisplayTaskClass> taskList = new ArrayList<DisplayTaskClass>();
    @FXML
    Label work_id;
    @FXML
    Label work_name;
    @FXML
    Label assigned_to;

    @FXML
    Button prioIcon;

    @FXML
    protected void changeTheTaskValues(ActionEvent e) throws Exception {
        TaskChangerController.workID = work_id.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/taskChanger.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        TaskChangerController.workID = work_id.getText();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void deleteTask(ActionEvent e) throws Exception {
        DatabaseConnector dbs = new DatabaseConnector();
        dbs.deleteTask(work_id.getText(), LoggedInUser.username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    protected void showDiscussion(ActionEvent e) throws Exception {
        DiscussionsController.taskId = Integer.valueOf(work_id.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Discussions.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
