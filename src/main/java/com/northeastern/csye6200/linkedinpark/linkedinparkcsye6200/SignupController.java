package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField name;

    @FXML
    private RadioButton teamManager;
    @FXML
    private RadioButton teamMember;

    private ToggleGroup role = new ToggleGroup();

    @FXML
    private Label statusText;
    @FXML
    private CheckBox agreeCheckbox;

    private boolean isCheckboxClicked = false;

    @FXML
    protected void checkBoxClicked() throws IOException {
        isCheckboxClicked = !isCheckboxClicked;
    }

    @FXML
    protected void onClickingSignUp(ActionEvent event) throws IOException {
        statusText.setText(role.getSelectedToggle()+" = "+teamMember.isSelected()+" - "+teamManager.isSelected());
        DatabaseConnect dbConnect = new DatabaseConnect();
        UserData usdata = new UserData();
        usdata.name = name.getText();
        usdata.role = teamManager.isSelected()?"Team Manager":"Team Member";
        usdata.username = username.getText();
        usdata.password = username.getText();

        if(dbConnect.checkForUniquenessOfUsername(usdata.username)) {
            if(usdata.role=="Team Manager") {
                dbConnect.adminStorage.add(usdata);
            }
            else {
                dbConnect.memberStorage.add(usdata);
            }
            dbConnect.showData();
            statusText.setText("Profile created Successfully!");
            statusText.setTextFill(Color.GREEN);
        }
        else {
            statusText.setText("Username already exists!");
            statusText.setTextFill(Color.RED);
        }
    }
    @FXML
    protected void onClickingSignin(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/master.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teamManager.setToggleGroup(role);
        teamMember.setToggleGroup(role);
    }
}