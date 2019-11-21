package com.example.tutorsearcher;

import android.util.Log;

import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.isNewUserCommandWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginRegisterTest {
    private DBAccessor dba;
    private boolean isNewFlag = true;


    @Before
    public void setup(){
        dba = new DBAccessor();
        dba.addNewUser("ginatesttutee@usc.edu", "password", "Tutee");
        dba.addNewUser("ginatesttutor@usc.edu", "password", "Tutor");
    }

    @Test
    public void testIsNewUser(){
        class userExists extends isNewUserCommandWrapper {
            public void execute(boolean isNew){
                isNewFlag = isNew;
            }
        }

        dba.isNewUser("ginatesttutee@usc.edu", "tutee", new userExists());
        assertFalse(isNewFlag); // We created this user: not new
        dba.isNewUser("ginatesttutor@usc.edu", "tutor", new userExists());
        assertFalse(isNewFlag); // We created this user: not new
        dba.isNewUser("ginatesttutee@usc.edu", "tutor", new userExists());
        assertTrue(isNewFlag); // Wrong role: new
        dba.isNewUser("blahblahblah@usc.edu", "tutee", new userExists());
        assertTrue(isNewFlag);
    }

    @Ignore
    public void testValidateUser(){

    }
}
