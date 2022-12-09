package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class LowPerformanceReports {
    public static ArrayList<PerformanceMetricsLayout> pfm = new ArrayList<PerformanceMetricsLayout>();

    @FXML
    Label name;

    @FXML
    Label totalTasks;

    @FXML
    Label pendingTasks;

    @FXML
    Label overdueTasks;
}
