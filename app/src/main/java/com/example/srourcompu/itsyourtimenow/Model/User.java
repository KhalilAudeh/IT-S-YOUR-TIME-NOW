package com.example.srourcompu.itsyourtimenow.Model;

/**
 * Created by srourcompu on 11/2/2018.
 */

public class User {
    private String UserId;
    private String Username;
    private String Phone;
    private String Date_Of_Birth;
    private String Password;

    public User(){

    }

    public User(String userId, String username, String  phone, String date_Of_Birth, String password){
        UserId = userId;
        Username = username;
        Phone = phone;
        Date_Of_Birth = date_Of_Birth;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }
}
