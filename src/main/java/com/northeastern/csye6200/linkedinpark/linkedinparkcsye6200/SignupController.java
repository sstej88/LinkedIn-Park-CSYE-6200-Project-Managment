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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SignupController implements Initializable {

    private Stage stage;
    private Scene scene;
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

    @FXML
    protected void onClickingSignup(ActionEvent e) throws IOException {

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
