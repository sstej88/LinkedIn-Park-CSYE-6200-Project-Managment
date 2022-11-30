package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Label statusText;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button signin;

    @FXML
    protected void onClickingSignIn(ActionEvent e) throws IOException {

        DatabaseConnect dbConnect = new DatabaseConnect();
        UserData usdata = dbConnect.loginValidation(username.getText(), password.getText());
        if(usdata.username.equals("login failed")) {
            statusText.setText("Invalid Credentials! Try again or Create account.");
            statusText.setTextFill(Color.RED);
        }
        else {
            statusText.setText("Login Success!");
            statusText.setTextFill(Color.GREEN);
            if(usdata.role.equals("Team Manager")) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/admindashboard.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
            if(usdata.role.equals("Team Member")) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/memberdashboard.fxml"));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    @FXML
    protected void onClickingSignup(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/signup.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}