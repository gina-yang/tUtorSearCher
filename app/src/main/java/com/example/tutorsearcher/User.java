package com.example.tutorsearcher;

import java.util.ArrayList;

import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.db.DBAccessor;

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
