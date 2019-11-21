package com.example.tutorsearcher;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserUnitTest
{
    @Test
    public void testTuteeConstructor()
    {
        //test creating a tutee, and its setters/getters for basic info
        User tutee = new Tutee("tuteetest@email.com");
        tutee.setProfilePic("profilepic.jpg");
        tutee.setName("name");
        tutee.setAge(20);
        tutee.setGender("female");
        assertEquals(tutee.getProfilePic(), "profilepic.jpg");
        assertEquals(tutee.getName(),"name");
        assertEquals(tutee.getAge(),20);
        assertEquals(tutee.getGender(),"female");
    }

    /**
     * test constructing a tutor and testing  if its set/get work
     */
    @Test
    public void testTutorConstructor()
    {
        User tutor = new Tutor("tutortest@email.com");
        tutor.setProfilePic("profilepic.jpg");
        tutor.setName("name");
        tutor.setAge(20);
        tutor.setGender("female");
        assertEquals(tutor.getProfilePic(), "profilepic.jpg");
        assertEquals(tutor.getName(),"name");
        assertEquals(tutor.getAge(),20);
        assertEquals(tutor.getGender(),"female");
    }

    /**
     * Test if a tutor can set their availability
     */
    @Test
    public void testTutorSetAvailability()
    {
        User tutor = new Tutor("tutortest@email.com");
        tutor.setProfilePic("profilepic.jpg");
        tutor.setName("name");
        tutor.setAge(20);
        tutor.setGender("female");
        ArrayList<String> newAvailabilityList = new ArrayList<String>();
        newAvailabilityList.add("Mon 15");
        newAvailabilityList.add("Wed 8");
        newAvailabilityList.add("Thu 10");
        tutor.setAvailability(newAvailabilityList);
        assertEquals(tutor.getAvailability(), new ArrayList<String>(Arrays.asList("Mon 15", "Wed 8", "Thu 10")));
    }
    /**
     * test if setting and adding courses to the tutor's list works
     */
    @Test
    public void testTutorSetCourses()
    {
        User tutor = new Tutor("tutortest@email.com");
        tutor.setProfilePic("profilepic.jpg");
        tutor.setName("name");
        tutor.setAge(20);
        tutor.setGender("female");
        ArrayList<String> newCoursesList = new ArrayList<String>();
        newCoursesList.add("CSCI 310");
        newCoursesList.add("CSCI 356");
        newCoursesList.add("CSCI 104");
        tutor.setCourses(newCoursesList);
        tutor.addCourses("CSCI 103");
        assertEquals(tutor.getCourses().get(0), "CSCI 310");
        assertEquals(tutor.getCourses().get(1),"CSCI 356");
        assertEquals(tutor.getCourses().get(2),"CSCI 104");
        assertEquals(tutor.getCourses().get(3),"CSCI 103");
    }
    /**
     * test if the rating system for the tutor functions properly
     */
    @Test
    public void testTutorSetRating()
    {
        User tutor = new Tutor("tutortest@email.com");
        tutor.setProfilePic("profilepic.jpg");
        tutor.setName("name");
        tutor.setAge(20);
        tutor.setGender("female");
        tutor.setNumRatings(10);
        tutor.setRating(4);
        tutor.addRating(5);
        assertEquals(tutor.getNumRatings(),11);
        System.out.println(Double.toString(tutor.getRating()));
        System.out.println(Double.toString((double)((((4.0*10.0)+5.0)/11.0))));
       // Log.d("rating",Double.toString(tutor.getRating()));
        assertTrue(0.0001 > Math.abs(tutor.getRating() - (double)((((4.0*10.0)+5.0)/11.0))));
    }
}
