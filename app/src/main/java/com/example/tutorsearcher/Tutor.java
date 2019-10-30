package com.example.tutorsearcher;

import java.util.ArrayList;

public class Tutor extends User {
    private ArrayList<Availability> availabilityList;
    private ArrayList<String> courses;
    private int numRatings;
    private double rating;

    // Constructor
    public Tutor(String email_)
    {
        super(email_, "tutor");
        // set defaults
        availabilityList = new ArrayList<Availability>();
        courses = new ArrayList<String>();
        numRatings = 0;
        rating = 0;
    }

    // getters
    public ArrayList<Availability> getAvailability()
    {
        return availabilityList;
    }
    public ArrayList<String> getCourses()
    {
        return courses;
    }
    public int getNumRatings() {return numRatings;}
    public double getRating() {return rating;}

    // setters
    public void setAvailability(ArrayList<Availability> newAvailabilityList){ availabilityList = newAvailabilityList; }
    public void setCourses(ArrayList<String> newCourses){ courses = newCourses; } // *** added setter for courses
    public void addCourses(String addCourse){ courses.add(addCourse); }
    public void setNumRatings(int newNumRatings) {numRatings = newNumRatings;}
    public void setRating(double newRating) {rating = newRating;}
    public void addRating(double newRating)
    {
        rating = (rating*numRatings + newRating)/(numRatings+1);
        numRatings += 1;
    }
}
