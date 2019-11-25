package com.example.tutorsearcher.whitebox;

import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.isNewUserCommandWrapper;
import com.example.tutorsearcher.db.validateUserCommandWrapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginRegisterTest {
    private DBAccessor dba;

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
                assertFalse(isNew);
            }
        }
        class wrongRole extends isNewUserCommandWrapper {
            public void execute(boolean isNew){
                assertTrue(isNew);
            }
        }
        class doesntExist extends isNewUserCommandWrapper {
            public void execute(boolean isNew){
                assertTrue(isNew);
            }
        }
        dba.isNewUser("utesttutee@usc.edu", "tutee", new userExists());
        dba.isNewUser("utesttutee@usc.edu", "tutor", new wrongRole());
        dba.isNewUser("utesttutor@usc.edu", "tutee", new wrongRole());
        dba.isNewUser("blahblahblah@usc.edu", "tutee", new doesntExist());
    }

    @Test
    public void testValidateUser(){
        class valUser extends validateUserCommandWrapper {
            public void doValidate(boolean loggedin){
                assertTrue(loggedin);
            }
        }
        class wrongPass extends validateUserCommandWrapper {
            public void doValidate(boolean loggedin){
                assertFalse(loggedin);
            }
        }
        class userDNE extends validateUserCommandWrapper {
            public void doValidate(boolean loggedin){
                assertFalse(loggedin);
            }
        }

        dba.validateUser("ginatesttutee@usc.edu", "password", "Tutee", new valUser());
        dba.validateUser("ginatesttutor@usc.edu", "password", "Tutor", new valUser());
        dba.validateUser("ginatesttutee@usc.edu", "passw", "Tutee", new valUser());
        dba.validateUser("ginatesttutor@usc.edu", "12345", "Tutor", new valUser());
        dba.validateUser("dnetutor@usc.edu", "password", "Tutor", new valUser());
    }
}
