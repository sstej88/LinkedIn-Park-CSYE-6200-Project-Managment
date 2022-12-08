package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import java.sql.*;

public class DatabaseConnector {
    public void insertLoginDetailsToDB(String name, String username, String password, String role) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "INSERT INTO login_details VALUES ('"+name+"','"+username+"','"+password+"','"+role+"')";
            System.out.println(query);
            st.execute(query);
            st.close();
            conn.close();
        }
        else {
            System.out.println("Not connected to database");
        }
    }

    public int checkForLoginDetails(String username) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM login_details where LOWER(username)='"+username+"'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            int numberOfRows = 0;
            while(rs.next()) {
                numberOfRows++;
            }
            st.close();
            conn.close();
            return numberOfRows;
        }
        else {
            System.out.println("Not connected to database");
            return -1;
        }
    }

    public int checkForLogin(String username, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM login_details WHERE username='"+username+"' AND password='"+password+"'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            int numberOfRows = 0;
            while(rs.next()) {
                numberOfRows++;
                LoggedInUser.name = rs.getString(1);
                LoggedInUser.username = rs.getString(2);
                LoggedInUser.role = rs.getString(4);
            }
            st.close();
            conn.close();
            return numberOfRows;
        }
        else {
            System.out.println("Not connected to database");
            return -1;
        }
    }

    public void getTasksAssignedBy(String username) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");

        TaskControllerYetToStart.workIDs.clear();
        TaskControllerYetToStart.workNames.clear();
        TaskControllerYetToStart.assignedToUsernames.clear();
        TaskControllerYetToStart.assignedToNames.clear();

        TaskControllerRunning.workIDs.clear();
        TaskControllerRunning.workNames.clear();
        TaskControllerRunning.assignedToUsernames.clear();
        TaskControllerRunning.assignedToNames.clear();

        TaskControllerDone.workIDs.clear();
        TaskControllerDone.workNames.clear();
        TaskControllerDone.assignedToUsernames.clear();
        TaskControllerDone.assignedToNames.clear();

        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM tasks WHERE assigned_by_username='"+username+"';";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                String taskNumber = rs.getString(1);
                String taskName = rs.getString(2);
                String assignedToUsername = rs.getString(6);
                String assignedToName = rs.getString(7);
                String taskStatus = rs.getString(3);
                if(taskStatus.equals("Start")) {
                    System.out.println(taskStatus+" - "+taskNumber+" - "+taskName+" - "+assignedToName+" - "+assignedToUsername);
                    TaskControllerYetToStart.workIDs.add(taskNumber);
                    TaskControllerYetToStart.workNames.add(taskName);
                    TaskControllerYetToStart.assignedToUsernames.add(assignedToUsername);
                    TaskControllerYetToStart.assignedToNames.add(assignedToName);
                }
                else if(taskStatus.equals("Running")) {
                    TaskControllerRunning.workIDs.add(taskNumber);
                    TaskControllerRunning.workNames.add(taskName);
                    TaskControllerRunning.assignedToUsernames.add(assignedToUsername);
                    TaskControllerRunning.assignedToNames.add(assignedToName);
                }
                else if(taskStatus.equals("Done")) {
                    TaskControllerDone.workIDs.add(taskNumber);
                    TaskControllerDone.workNames.add(taskName);
                    TaskControllerDone.assignedToUsernames.add(assignedToUsername);
                    TaskControllerDone.assignedToNames.add(assignedToName);
                }
            }
            st.close();
            conn.close();
        }
        else {
            System.out.println("Not connected to database");
        }
    }

    public void getTaskFromDatabase(String workId) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tasks WHERE task_id='"+workId+"'");
            while(rs.next()) {

            }
        }
        else {
            System.out.println("Unable to Connect to database");
        }
    }

    public void deleteTask(String workId, String username) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "DELETE FROM tasks WHERE task_id='"+workId+"';";
            st.executeUpdate(query);
            getTasksAssignedBy(username);
            st.close();
            conn.close();
        }
        else {
            System.out.println("Unable to Connect to database");
        }
    }

    public void getTaskInfo(String workID) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM tasks WHERE task_id='"+workID+"';";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                TaskChangerController.taskName =  rs.getString("task_name");
                TaskChangerController.taskStatus =  rs.getString("task_status");
                TaskChangerController.assignedByUsername =  rs.getString("assigned_by_username");
                TaskChangerController.assignedByName =  rs.getString("assigned_by_name");
                TaskChangerController.assignedToUsername =  rs.getString("assigned_to_username");
                TaskChangerController.assignedToName =  rs.getString("assigned_to_name");
                TaskChangerController.taskDescription =  rs.getString("task_description");
                TaskChangerController.isPriority =  rs.getString("isPriority");
                TaskChangerController.finishDate =  rs.getString("finish_date");
            }
            st.close();
            conn.close();
        }
        else {
            System.out.println("Unable to Connect to database");
        }
    }
}
