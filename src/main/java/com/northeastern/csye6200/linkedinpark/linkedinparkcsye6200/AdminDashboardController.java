package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private Label name;
    @FXML
    private Hyperlink username;

    @FXML
    private Label role;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("Welcome, "+LoggedInData.usdata.name);
        username.setText(LoggedInData.usdata.username+" (Logout)");
        role.setText("You are "+LoggedInData.usdata.role);
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

}
