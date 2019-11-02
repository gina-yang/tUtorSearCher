package com.example.tutorsearcher;

public class Request {
    public String tutee;
    public String tutor;
    public String status;//is either accepted, rejected. or pending
    public String course;//holds the class ex: CSCI310
    public String time;

    public Request(String tutee, String tutor, String status, String course, String time) {
        this.tutee = tutee;
        this.tutor = tutor;
        this.status = status;
        this.course = course;
        this.time = time;
    }
}
