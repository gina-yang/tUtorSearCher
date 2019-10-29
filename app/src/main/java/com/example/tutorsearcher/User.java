package com.example.tutorsearcher;

import java.util.ArrayList;

import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.db.DBAccessor;

public abstract class User extends DBAccessor
{
    private String name = "";
    private int age = 1;
    private String gender = "other";//male, female, other
    private String profilePic = "";//url to the profile pic
    private String email = "";//should be a usc email
    private ArrayList<Request> requests = new ArrayList<Request>();

    //getters
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    public String getGender()
    {
        return gender;
    }
    public String profilePic()
    {
        return profilePic;
    }
    public String getEmail()
    {
        return email;
    }
    public ArrayList<Request> getRequests()
    {
        return requests;
    }

    //setters
    public void setProfilePic(String newPic) {
        profilePic = newPic;

    }
}
