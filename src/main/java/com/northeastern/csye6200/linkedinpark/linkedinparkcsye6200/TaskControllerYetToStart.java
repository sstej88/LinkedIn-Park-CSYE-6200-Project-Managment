package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TaskControllerYetToStart {
    private Stage stage;
    private Scene scene;
    public static ArrayList<String> workIDs = new ArrayList<>();
    public static ArrayList<String> workNames = new ArrayList<>();
    public static ArrayList<String> assignedToUsernames = new ArrayList<>();
    public static ArrayList<String> assignedToNames = new ArrayList<>();
    @FXML
    Label work_id;
    @FXML
    Button work_name;
    @FXML
    Label assigned_to;

    @FXML
    protected void changeTheTaskValues(ActionEvent e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/taskChanger.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        TaskChangerController.workID = work_id.getText();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
