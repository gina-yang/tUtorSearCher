package com.example.tutorsearcher;

public abstract class User {
    public String firstname;
    public String lastname;
    public String gender;
    public int age;
    public String profilePic;
    public String email;
    public String password;

    // tutor or tutee? set in child class
    public String type;
}
