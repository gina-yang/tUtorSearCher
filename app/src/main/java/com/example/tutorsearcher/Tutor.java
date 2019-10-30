package com.example.tutorsearcher;

import com.example.tutorsearcher.Availability;

import java.util.ArrayList;

public class Tutor extends User {
    public String type = "tutor";
    public int numRatings;
    public double rating;
    public ArrayList<String> courses = new ArrayList<String>();
    public ArrayList<Availability> availability = new ArrayList<Availability>();
}
