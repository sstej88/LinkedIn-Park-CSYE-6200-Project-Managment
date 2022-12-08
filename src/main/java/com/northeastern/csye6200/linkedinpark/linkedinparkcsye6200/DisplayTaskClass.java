package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Date;

public class DisplayTaskClass {
    public Integer taskId = 0;
    public String task_name = "";
    public String task_description = "";
    public String task_status = "";
    public Boolean isPriority = false;
    public String assignedByName = "";
    public String assignedByUserName = "";
    public String assignedName = "";
    public String assignedUserName = "";
    public Date finish_date = null;
    public Boolean isDeadlineMissed = false;

//
//    public static ArrayList<String> workIDs = new ArrayList<>();
//    public static ArrayList<String> workNames = new ArrayList<>();
//    public static ArrayList<String> assignedToUsernames = new ArrayList<>();
//    public static ArrayList<String> assignedToNames = new ArrayList<>();
//    @FXML
//    Label work_id;
//    @FXML
//    Label work_name;
//    @FXML
//    Label assigned_to;
}
