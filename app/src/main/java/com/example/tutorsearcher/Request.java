package com.example.tutorsearcher;

public class Request {
    public String tuteeEmail;
    public String tutorEmail;
    public String status;
    public String course;
    public String time;

    public Request(String tuteeEmail, String tutorEmail, String status, String course, String time) {
        this.tuteeEmail = tuteeEmail;
        this.tutorEmail = tutorEmail;
        this.status = status;
        this.course = course;
        this.time = time;
    }
}
