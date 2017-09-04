package com.tatlicilar.visualeducation;

import java.util.ArrayList;

/**
 * Created by Asus on 24.08.2017.
 */

public class Users {

    public  String userSchool;
    public  String userEmail;
    public  String userPassword;
    public  String userType;
    public  String userName;
    public  String userSchYear;

    public String getUserSchYear() {
        return userSchYear;
    }

    public void setUserSchYear(String userSchYear) {
        this.userSchYear = userSchYear;
    }

    public  Users()
    {
        //Default Constructor
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Users( String userName, String userEmail,String userPassword, String userType, String userSchool,String userSchYear) {
      // this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.userSchool = userSchool;
        this.userSchYear=userSchYear;

    }


}
