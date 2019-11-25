package com.example.tutorsearcher.whitebox;

import com.example.tutorsearcher.Request;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RequestTestSuite
{
    @Test
    public void requestConstructorTest()
    {
        Request request = new Request("tuteeName","tutorName","tuteeemail","tutoremail"
                                        ,"status","TEST100","Mon 10");
        //(String tuteeName, String tutorName,String tuteeEmail, String tutorEmail, String status, String course, String time)
        assertTrue(request != null);
        assertEquals("tuteeName",request.tuteeName);
        assertEquals("tutorName",request.tutorName);
        assertEquals("tuteeemail",request.tuteeEmail);
        assertEquals("tutoremail",request.tutorEmail);
        assertEquals("status",request.status);
        assertEquals("TEST100",request.course);
        assertEquals("Mon 10",request.time);
    }
}

