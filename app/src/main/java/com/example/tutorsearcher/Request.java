package com.example.tutorsearcher;

public class Request
{
    private String status = "";//is either accepted, rejected. or pending
    private String classCode = "";//holds the class ex: CSCI310
    private String time = "";//holds the time
    private Tutor tutor;//the tutor this request is being sent to
    private Tutee tutee;//the tutee that made this request

    Request(Tutee tutee, Tutor tutor, String classCode, String time)
    {
        this.tutee = tutee;
        this.tutor = tutor;
        this.classCode = classCode;
        this.time = time;
        status = "pending";//status pending until tutor accepts or rejects
    }

    //getters
    public String getStatus()
    {
        return status;
    }
    public String getClassCode()
    {
        return classCode;
    }
    public String getTime()
    {
        return time;
    }
    public Tutor getTutor()
    {
        return tutor;
    }
    public Tutee getTutee()
    {
        return tutee;
    }

    //setters
    public void setStatus(String status)
    {
        this.status = status;
    }
}
