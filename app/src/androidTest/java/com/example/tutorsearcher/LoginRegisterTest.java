package com.example.tutorsearcher;

import android.util.Log;

import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.isNewUserCommandWrapper;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginRegisterTest {
    DBAccessor dba;
    boolean isNewFlag;
    @Before
    public void setup(){
        dba = new DBAccessor();

        dba.addNewUser("utesttutee@usc.edu", "password", "Tutee");
        dba.addNewUser("utesttutor@usc.edu", "password", "Tutor");
        Log.d("hello","hello");
    }

    @Test
    public void testIsNewUser(){


        class userExists extends isNewUserCommandWrapper {
            public void execute(boolean isNew){
//                assertFalse(isNew);
                isNewFlag = false;
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
        System.out.println("testing123");

        dba.isNewUser("utesttutee@usc.edu", "tutee", new userExists());
        assertTrue(isNewFlag);
        dba.isNewUser("utesttutee@usc.edu", "tutor", new wrongRole());
        dba.isNewUser("utesttutor@usc.edu", "tutee", new wrongRole());
        dba.isNewUser("blahblahblah@usc.edu", "tutee", new doesntExist());
    }

    @Ignore
    public void testValidateUser(){

    }
}
