package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DiscussionsController implements Initializable {
    DatabaseConnector dbs = new DatabaseConnector();
    public static Integer taskId = 0;
    private Stage stage;
    private Scene scene;

    @FXML
    TextArea messageInputField;
    @FXML
    Label taskIdLabel;
    @FXML
    VBox messageWrap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskIdLabel.setText(taskId.toString());
//        try {
//            dbs = new DatabaseConnector();
//            memberList = dbs.getTeamMembersList();
//
//            memberList = memberList;
//
//            for (Users element : memberList) {
//                assignTo.getItems().add(element.name);
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    protected void fetchMessages() throws Exception {
        ArrayList<DiscussionClass> fetchedMessages = dbs.FetchAllMessages(taskId);

        messageWrap.getChildren().clear();

        for(int i=0; i<fetchedMessages.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/DiscussionMessageBubble.fxml"));
            BorderPane messageItem = fxmlLoader.load();
            DiscussionMessageBubble controller = fxmlLoader.<DiscussionMessageBubble>getController();
            if(fetchedMessages.get(i).sendByUserName == LoggedInUser.username)
                controller.isLoggedinUsersMessage = true;
            controller.message = fetchedMessages.get(i).message;
            controller.messageSenderName = fetchedMessages.get(i).sendByName;
            messageWrap.getChildren().add(messageItem);
        }
    }
    @FXML
    protected void addMessages(ActionEvent e) throws Exception {
        String message = messageInputField.getText();

        if(message.equals(null) || message.equals("")) {
            return;
        }
        dbs.InsertNewMessage(message, taskId, LoggedInUser.username, LoggedInUser.name);
        fetchMessages();
        messageInputField.setText("");
    }

    @FXML
    protected void ReturnToDashboard(ActionEvent e) throws Exception {
        AdminDashboardController.isComingBack = true;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/adminDashboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
