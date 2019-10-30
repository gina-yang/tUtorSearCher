package com.example.tutorsearcher;
import java.util.ArrayList;

public abstract class User
{
    private String name;
    private int age;
    private String gender; //male, female, other
    private String profilePic; //url to the profile pic
    private String email; //should be a usc email
    private String type; //"tutor" or "tutee"
    private ArrayList<Request> requests;

    // Constructor
    public User(String email_, String type_)
    {
        email = email_;
        type = type_;
        // default values
        name = "";
        age = -1;
        gender = "";
        profilePic = "";
        requests = new ArrayList<Request>();
    }

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
    public String getProfilePic()
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
    public String getType() {return type;}

    // TUTOR ONLY BELOW... these will be overridden by polymorphism in Tutor class---
    public ArrayList<String> getAvailability()
    {
        return null;
    }
    public ArrayList<String> getCourses()
    {
        return null;
    }
    public int getNumRatings() {return -1;}
    public double getRating() {return -1;}

    //setters
    public void setProfilePic(String newPic)
    {
        profilePic = newPic;
    }
    public void setName(String newName){ name = newName; } // *** added setter for name
    public void setAge(int newAge){ age = newAge; }
    public void setGender(String newGender){ gender = newGender; }

    // TUTOR ONLY BELOW... these will be overridden by polymorphism in Tutor class---
    public void setAvailability(ArrayList<String> newAvailabilityList){
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
    public void setCourses(ArrayList<String> newCourses){
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
    public void addCourses(String addCourse){
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
    public void setNumRatings(int newNumRatings) {
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
    public void setRating(double newRating) {
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
    public void addRating(double newRating)
    {
        throw new RuntimeException("This method is not supported except by an instance of a Tutor class");
    }
}
