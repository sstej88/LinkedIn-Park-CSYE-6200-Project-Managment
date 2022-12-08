package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class SignupController implements Initializable {

    private Stage stage;
    private Scene scene;

    public Connection conn;
    @FXML
    TextField name;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    RadioButton teamManager;
    @FXML
    RadioButton teamMember;
    ToggleGroup role = new ToggleGroup();
    @FXML
    Button signup;
    @FXML
    Label statusLabel;
    @FXML
    Hyperlink login;

    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @FXML
    protected void onClickingSignup(ActionEvent e) throws Exception {
        Matcher matcher = pattern.matcher(username.getText());
        if(name.getText().equals(null) || name.getText().equals("")) {
            statusLabel.setText("Enter a valid name");
            statusLabel.setTextFill(Color.RED);
        }
        else if(matcher.matches()==false) {
            statusLabel.setText("Enter valid username");
            statusLabel.setTextFill(Color.RED);
        }
        else if(password.getText().equals(null) || password.getText().equals("")) {
            statusLabel.setText("Create a good password!");
            statusLabel.setTextFill(Color.RED);
        }
        else if(teamManager.isSelected()==false && teamMember.isSelected()==false) {
            statusLabel.setText("Select your role before signing up!");
            statusLabel.setTextFill(Color.RED);
        }
        else {
            DatabaseConnector dbc = new DatabaseConnector();
            try {
                if(dbc.checkForLoginDetails(username.getText())==0) {
                    String userRole = teamManager.isSelected()?"Team Manager":"Team Member";
                    dbc.insertLoginDetailsToDB(name.getText(), username.getText(), password.getText(), userRole);
                    statusLabel.setText("Account Created Successfully");
                    statusLabel.setTextFill(Color.GREEN);
                }
                else {
                    statusLabel.setText("Username already exist!");
                    statusLabel.setTextFill(Color.RED);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    @FXML
    protected void onClickingLogin(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/master.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusLabel.setText("");
        teamManager.setToggleGroup(role);
        teamMember.setToggleGroup(role);
    }
}
