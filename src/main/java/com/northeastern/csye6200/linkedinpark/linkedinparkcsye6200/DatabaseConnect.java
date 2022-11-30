package com.northeastern.csye6200.linkedinpark.linkedinparkcsye6200;

import java.util.ArrayList;

public class DatabaseConnect {
    public static ArrayList<UserData> adminStorage = new ArrayList<UserData>();
    public static ArrayList<UserData> memberStorage = new ArrayList<UserData>();

    public void showData() {
        System.out.println("Showing Admin Data");
        for(int i=0; i<adminStorage.size(); i++) {
            System.out.println(adminStorage.get(i).username);
        }
        System.out.println("Showing Members Data");
        for(int i=0; i<memberStorage.size(); i++) {
            System.out.println(memberStorage.get(i).username);
        }
    }

    public boolean checkForUniquenessOfUsername(String usr) {
        if(!usr.equals("login failed")) {
            for(int i=0; i<adminStorage.size(); i++) {
                if(adminStorage.get(i).username.toLowerCase().equals(usr.toLowerCase())) {
                    return false;
                }
            }
            for(int i=0; i<memberStorage.size(); i++) {
                if(memberStorage.get(i).username.toLowerCase().equals(usr.toLowerCase())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public UserData loginValidation(String usr, String password) {
        for(int i=0; i<adminStorage.size(); i++) {
            if(adminStorage.get(i).username.toLowerCase().equals(usr.toLowerCase()) && adminStorage.get(i).password.equals(password)) {
                return adminStorage.get(i);
            }
        }
        for(int i=0; i<memberStorage.size(); i++) {
            if(memberStorage.get(i).username.toLowerCase().equals(usr.toLowerCase()) && memberStorage.get(i).password.equals(password)) {
                return memberStorage.get(i);
            }
        }
        UserData failedLogin = new UserData();
        failedLogin.name = "login failed";
        failedLogin.username = "login failed";
        failedLogin.role = "login failed";
        failedLogin.password = "login failed";
        return failedLogin;
    }
}
