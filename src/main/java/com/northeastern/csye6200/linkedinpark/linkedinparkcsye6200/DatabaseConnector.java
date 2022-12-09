package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import com.mysql.cj.log.Log;

import java.sql.*;
import java.time.LocalDate;
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
//            String query = "SELECT * FROM tasks WHERE assigned_by_username='"+username+"';";
            String query = "SELECT task_id, task_name, task_description, task_status, isPriority, \n" +
                    "assigned_by_username, assigned_by_name, assigned_to_username, assigned_to_name, finish_date, \n" +
                    "CASE WHEN finish_date < CURDATE() Then 1 ELSE 0 END AS 'IsDeadlineMissed' FROM tasks \n" +
                    "ORDER BY isPriority DESC, IsDeadlineMissed DESC, task_id ASC";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);

            // Clear previous list before new list is loaded

            TaskControllerYetToStart.taskList.clear();
            TaskControllerRunning.taskList.clear();
            TaskControllerDone.taskList.clear();


            // load new list
            while(rs.next()) {
                DisplayTaskClass objTask = new DisplayTaskClass();
                objTask.taskId = rs.getInt(1);
                objTask.task_name = rs.getString(2);
                objTask.task_description = rs.getString(3);
                objTask.task_status = rs.getString(4);
                objTask.isPriority = rs.getBoolean(5);
                objTask.assignedByUserName = rs.getString(6);
                objTask.assignedByName = rs.getString(7);
                objTask.assignedUserName = rs.getString(8);
                objTask.assignedName = rs.getString(9);
                objTask.finish_date = rs.getDate(10);
                objTask.isDeadlineMissed = rs.getBoolean(11);

                if(objTask.task_status.equals("Start")) {
                    System.out.println(objTask.task_status+" - "+objTask.taskId+" - "+objTask.task_name+" - "+objTask.assignedName+" - "+objTask.assignedUserName);
                    TaskControllerYetToStart.taskList.add(objTask);
                }
                else if(objTask.task_status.equals("Running")) {
                    TaskControllerRunning.taskList.add(objTask);
                }
                else if(objTask.task_status.equals("Done")) {
                    TaskControllerDone.taskList.add(objTask);
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

    public void updateTask(String workID, String TName, String status, String UName, String Name, String description, LocalDate finishDate) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "UPDATE tasks SET task_name='"+TName+"', task_status='"+status+"', assigned_to_username='"+UName+"', assigned_to_name='"+Name+"', task_description='"+description+"', finish_date='"+finishDate+"' WHERE task_id='"+workID+"';";
            st.executeUpdate(query);
            st.close();
            conn.close();
        }
        else {
            System.out.println("Unable to Connect to database");
        }
    }

    public String getUsername(String name) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM login_details WHERE name = '"+name+"'");
            rs.next();
            String username = rs.getString("username");
            st.close();
            conn.close();
            return username;
        }
        else {
            System.out.println("Unable to Connect to database");
            return "Database Not Connected to Get Username";
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
//
//    public ArrayList<Users> getTeamMembersList() throws Exception {
//        ArrayList<Users> outPutList = new ArrayList<Users>();
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/csye6200", "root", "rootadmin");
//        if(conn!=null) {
//            System.out.println("Connected to database");
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT name, username FROM login_details WHERE role = \"Team Member\";");
//            while(rs.next()) {
//                Users objUser = new Users();
//                objUser.name = rs.getString(1);
//                objUser.username = rs.getString(2);
//                outPutList.add(objUser);
//            }
//        }
//        else {
//            System.out.println("Unable to Connect to database");
//        }
//        return outPutList;
//    }
//
//
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

    // Get all discussion messages for a given task
    public ArrayList<DiscussionClass> FetchAllMessages(Integer TaskId) throws Exception {
        ArrayList<DiscussionClass> messageList = new ArrayList<DiscussionClass>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT message_id, task_id, message, sendDate, sendByUserName, sendByName FROM discussion WHERE task_id = "+TaskId+" ORDER BY sendDate ASC;");
            while(rs.next()) {
                DiscussionClass objMessage = new DiscussionClass();
                objMessage.message_id = rs.getInt(1);
                objMessage.task_id = rs.getInt(2);
                objMessage.message = rs.getString(3);
                objMessage.sendByUserName = rs.getString(5);;
                objMessage.sendByName = rs.getString(6);
                messageList.add(objMessage);
            }
        }
        else {
            System.out.println("Unable to Connect to database");
        }

        return messageList;
    }

    // Send message save
    public void InsertNewMessage(String Message, Integer task_id, String sendByUserName, String sendByName) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        if(conn!=null) {
            System.out.println("Connected to database");
            Statement st = conn.createStatement();
            String query = "INSERT INTO discussion (task_id, message, sendByUserName, sendByName)\n" +
                    "VALUES ("+task_id+", '"+Message+"', '"+sendByUserName+"', '"+sendByName+"')";
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
