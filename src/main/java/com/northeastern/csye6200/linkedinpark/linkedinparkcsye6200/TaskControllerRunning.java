package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class TaskControllerRunning {
    public static ArrayList<String> workIDs = new ArrayList<>();
    public static ArrayList<String> workNames = new ArrayList<>();
    public static ArrayList<String> assignedToUsernames = new ArrayList<>();
    public static ArrayList<String> assignedToNames = new ArrayList<>();
    @FXML
    Label work_id;
    @FXML
    Label work_name;
    @FXML
    Label assigned_to;
}
