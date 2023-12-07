package com.example.myfirstapp;

public class Announcement {
    private String AdminID;
    private String date;
    private String Content;

    public Announcement(String AdminID, String date, String Content){
        this.AdminID = AdminID;
        this.date = date;
        this.Content = Content;
    }

    public String getAdminID(){
        return AdminID;
    }

    public void setAdminID(String AdminID){
        this.AdminID = AdminID;
    }

    public String getDate(){
        return date;
    }

    public void  setDate(String date){
        this.date = date;
    }

    public String getContent(){
        return Content;
    }

    public void setContent(String Content){
        this.Content = Content;
    }
}
