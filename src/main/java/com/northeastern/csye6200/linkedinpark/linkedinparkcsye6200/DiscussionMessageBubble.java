package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscussionMessageBubble implements Initializable {
    public static Boolean isLoggedinUsersMessage = false;
    public static String messageSenderName = "adam";
    public static String message = "hey, how are you";

    @FXML
    BorderPane messageBubble;
    @FXML
    Label lblMessage;
    @FXML
    Label lblSenderName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblMessage.setText(message);
        lblSenderName.setText(messageSenderName);
//        if(isLoggedinUsersMessage)
//            messageBubble.setStyle("-fx-background-color: #FFFFFF;");
    }
}
