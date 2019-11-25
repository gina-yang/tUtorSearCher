package com.example.tutorsearcher.whitebox;

import com.example.tutorsearcher.Availability;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AvailabilityTestSuite
{
    @Test
    public void availabilityConstructorTest()
    {
        Availability temp = new Availability("Fri","10","12");
        //changeTimeslot(String starttime, String endtime)
        assertTrue(temp != null);
        assertEquals("Fri", temp.day);
        assertEquals("10",temp.starttime);
        assertEquals("12",temp.endtime);
    }
    @Test
    public void setAvailabilityTest()
    {
        Availability temp = new Availability("Fri","10","12");
        temp.setTimeslot("Wed","12","15");
        assertEquals("wed",temp.day);
        assertEquals("10",temp.starttime);
        assertEquals("12",temp.endtime);
    }
}
