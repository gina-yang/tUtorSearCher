package com.example.tutorsearcher;

public class Request {
    public String tuteeEmail;
    public String tutorEmail;
    public String status;//is either accepted, rejected. or pending
    public String course;//holds the class ex: CSCI310
    // Change these to "Time" objects later for calendar
    public String starttime;
    public String endtime;

    public Request(Tutee tutee, Tutor tutor, String classCode, String time) {

    }
}
