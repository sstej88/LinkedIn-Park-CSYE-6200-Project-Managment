package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private Label welcomeText;

    @FXML
    private Label roleText;

    @FXML
    private Hyperlink logoutLink;
    @FXML
    private VBox yetToStart;
    @FXML
    private VBox currentlyRunning;
    @FXML
    private VBox completedTasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Welcome, "+LoggedInData.usdata.name);
        roleText.setText("You are a "+LoggedInData.usdata.role);
        logoutLink.setText(LoggedInData.usdata.username+" (logout)");
    }

    @FXML
    protected void logout(ActionEvent e) throws IOException {
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Project Management System - Logging out!");
        logoutAlert.setContentText("Are you sure to sign out of the application?");
        Optional<ButtonType> logoutButton = logoutAlert.showAndWait();
        if((logoutButton.isPresent())&&(logoutButton.get()==ButtonType.OK)) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/master.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    protected void addTask(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/task.fxml"));
        yetToStart.getChildren().add(fxmlLoader.load());
    }
}
