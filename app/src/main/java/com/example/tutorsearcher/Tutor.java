package com.example.tutorsearcher;

import java.util.ArrayList;

public class Tutor extends User {
    private ArrayList<String> availabilityList;
    private ArrayList<String> courses;
    private long numRatings;
    private double rating;

    // Constructor
    public Tutor(String email_)
    {
        super(email_, "tutor");
        // set defaults
        availabilityList = new ArrayList<String>();
        courses = new ArrayList<String>();
        numRatings = 0;
        rating = 0;
    }

    /**
     * given a date/time, returns whether the tutor is free at that time
     * @param dateTime the date and time we are checking if the tutor is free
     * @return
     */
    public boolean isAvailable(String dateTime)
    {
        //seperate string to get date and time
        String[] datetimearr = dateTime.split(" ");
        String date = datetimearr[0];
        double time = Double.parseDouble(datetimearr[1]);
        //iterate through all the times
        for(int i = 0; i < availabilityList.size(); i++)
        {
            String temp = availabilityList.get(i);
            String[] tempdatetimearr = temp.split(" ");
            String tempdate = datetimearr[0];
            double temptime = Double.parseDouble(tempdatetimearr[1]);
            if(tempdate == date)//check if days are the same
            {
                if(time >= temptime && time < temptime+1)
                {
                    return true;//is available
                }
            }
        }
        return false;
    }
    // getters
    public ArrayList<String> getAvailability()
    {
        return availabilityList;
    }
    public ArrayList<String> getCourses()
    {
        return courses;
    }
    public long getNumRatings() {return numRatings;}
    public double getRating() {return rating;}

    // setters
    public void setAvailability(ArrayList<String> newAvailabilityList){ availabilityList = newAvailabilityList; }
    public void setCourses(ArrayList<String> newCourses){ courses = newCourses; } // *** added setter for courses
    public void addCourses(String addCourse){ courses.add(addCourse); }
    public void setNumRatings(long newNumRatings) {numRatings = newNumRatings;}
    public void setRating(double newRating) {rating = newRating;}
    public void addRating(double newRating)
    {
        rating = (rating*numRatings + newRating)/(numRatings+1);
        numRatings += 1;
    }
}