package com.example.tutorsearcher;

public class Request {
    public String tuteeName;
    public String tutorName;
    public String tuteeEmail;
    public String tutorEmail;
    public String status;//is either accepted, rejected. or pending
    public String course;//holds the class ex: CSCI310
    public String time;

    public Request(String tuteeName, String tutorName,String tuteeEmail, String tutorEmail, String status, String course, String time) {
        this.tuteeName = tuteeName;
        this.tutorName = tutorName;
        this.tuteeEmail = tuteeEmail;
        this.tutorEmail = tutorEmail;
        this.status = status;
        this.course = course;
        this.time = time;
    }
}