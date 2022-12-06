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
import org.controlsfx.control.action.Action;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MasterController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button login;
    @FXML
    Label statusLabel;
    @FXML
    Hyperlink signupHyperlink;

    @FXML
    protected void moveToSignup(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/signup.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML void loginIntoApplication(ActionEvent e) throws Exception {
        if( username.getText().equals(null) || username.getText().equals("")) {
            statusLabel.setText("Enter a valid username");
            statusLabel.setTextFill(Color.RED);
        }
        else if(password.getText().equals(null) || password.getText().equals("")) {
            statusLabel.setText("Enter a valid password");
            statusLabel.setTextFill(Color.RED);
        }
        else {
            DatabaseConnector dbc = new DatabaseConnector();
            if(dbc.checkForLogin(username.getText(), password.getText())!=0) {
                statusLabel.setText("Login is successful");
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText(LoggedInUser.name+" - "+LoggedInUser.username+" - "+LoggedInUser.role);
                if(LoggedInUser.role.equals("Team Manager")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/adminDashboard.fxml"));
                    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
            }
            else {
                statusLabel.setText("Invalid Credentials");
                statusLabel.setTextFill(Color.RED);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusLabel.setText("");
    }
}