package com.example.tutorsearcher.whitebox;

import com.example.tutorsearcher.Tutee;
import com.example.tutorsearcher.Tutor;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getProfileCommandWrapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {
    private DBAccessor dba;

    @Before
    public void setup(){
        dba = new DBAccessor();
        dba.addNewUser("ginaprtesttutee@usc.edu", "password", "Tutee");
        dba.addNewUser("ginaprtesttutor@usc.edu", "password", "Tutor");
    }

    @Test
    public void testGetProfile() {
        class tuteeProfileCheck extends getProfileCommandWrapper {
            public void execute(User u){
                assertTrue(u.getName().equals("Your Name"));
                assertTrue(u.getEmail().equals("ginaprtesttutee@usc.edu"));
                assertTrue(u.getGender().equals("NA"));
                assertEquals(u.getAge(), -1);
                assertEquals(u.getNumRatings(), 0);
                assertTrue(u.getType().equals("tutee"));
            }
        }
        class tutorProfileCheck extends getProfileCommandWrapper {
            public void execute(User u){
                assertTrue(u.getName().equals("Your Name"));
                assertTrue(u.getEmail().equals("ginaprtesttutor@usc.edu"));
                assertTrue(u.getGender().equals("NA"));
                assertEquals(u.getAge(), -1);
                assertEquals(u.getNumRatings(), 0);
                assertTrue(u.getType().equals("tutor"));
            }
        }
        dba.getProfile("ginaprtesttutee@usc.edu", "tutee", new tuteeProfileCheck());
        dba.getProfile("ginaprtesttutor@usc.edu", "tutor", new tutorProfileCheck());
    }

    @Test
    public void testUpdateProfile() {
        User u = new Tutee("ginaprtesttutee@usc.edu");
        u.setName("Test Tutee");
        u.setAge(20);
        u.setGender("F");
        dba.updateProfile(u);

        User u1 = new Tutor("ginaprtesttutee@usc.edu");
        u1.setName("Test Tutor");
        u1.setAge(19);
        u1.setGender("M");
        dba.updateProfile(u1);

        class tuteeProfileCheck extends getProfileCommandWrapper {
            public void execute(User u){
                assertTrue(u.getName().equals("Test Tutee"));
                assertTrue(u.getEmail().equals("ginaprtesttutee@usc.edu"));
                assertTrue(u.getGender().equals("F"));
                assertEquals(u.getAge(), 20);
                assertEquals(u.getNumRatings(), 0);
                assertTrue(u.getType().equals("tutee"));
            }
        }

        class tutorProfileCheck extends getProfileCommandWrapper {
            public void execute(User u){
                assertTrue(u.getName().equals("Test Tutor"));
                assertTrue(u.getEmail().equals("ginaprtesttutor@usc.edu"));
                assertTrue(u.getGender().equals("M"));
                assertEquals(u.getAge(), 19);
                assertEquals(u.getNumRatings(), 0);
                assertTrue(u.getType().equals("tutor"));
            }
        }
        dba.getProfile("ginaprtesttutee@usc.edu", "tutee", new tuteeProfileCheck());
        dba.getProfile("ginaprtesttutor@usc.edu", "tutor", new tutorProfileCheck());
    }
}