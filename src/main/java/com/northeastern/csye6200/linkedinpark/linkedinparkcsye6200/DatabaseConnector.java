package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import com.mysql.cj.log.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseConnector {
    private static final String connectionUrl = "jdbc:mysql://127.0.0.1:3306/csye6200";
    private static final String connectionUser = "root";
    private static final String connectionPassword = "rootadmin";

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
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM tasks WHERE assigned_by_username='"+username+"';";
//            String newQuery = "SELECT task_id, task_name, task_description, task_status, isPriority, \n" +
//                    "assigned_by_username, assigned_by_name, assigned_to_username, assigned_to_name, finish_date, \n" +
//                    "CASE WHEN finish_date < CURDATE() Then 1 ELSE 0 END AS 'IsDeadlineMissed' FROM tasks \n" +
//                    "ORDER BY isPriority DESC, IsDeadlineMissed DESC, task_id ASC";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);

            // Clear previous list before new list is loaded
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

            // load new list
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

    public ArrayList<Users> getTeamMembersList() throws Exception {
        ArrayList<Users> outPutList = new ArrayList<Users>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name, username FROM login_details WHERE role = \"Team Member\";");
            while(rs.next()) {
                Users objUser = new Users();
                objUser.name = rs.getString(1);
                objUser.username = rs.getString(2);
                outPutList.add(objUser);
            }
        }
        else {
            System.out.println("Unable to Connect to database");
        }
        return outPutList;
    }
//
    public void InsertNewTask(String Title, String Description, Date FinishByDate, Boolean isPriority, String assignedUserName, String assignedName, String assignedByUserName, String assignedByName) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "INSERT INTO tasks (task_name, task_status, task_description, isPriority, assigned_by_username, assigned_by_name, assigned_to_username, assigned_to_name, finish_date)\n" +
                    "VALUES ('"+Title+"', 'Start', '"+Description+"', "+isPriority+", '"+assignedByUserName+"', '"+assignedByName+"', '"+assignedUserName+"', '"+assignedByName+"', '"+FinishByDate+"');";
//            String query = "INSERT INTO login_details VALUES ('"+name+"','"+username+"','"+password+"','"+role+"')";
            System.out.println(query);
            st.execute(query);
            st.close();
            conn.close();
        }
        else {
            System.out.println("Not connected to database");
        }
    }
}
