package com.example.tutorsearcher.whitebox;

import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getRequestsCommandWrapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class RequestTest {
    DBAccessor dba;
    @Before
    public void setup(){
        dba = new DBAccessor();
        dba.addNewUser("ginareqtesttutee@usc.edu", "password", "Tutee");
        dba.addNewUser("ginareqtesttutor@usc.edu", "password", "Tutor");
        Request r = new Request("Your Name", "Your Name", "ginareqtesttutee@usc.edu", "ginareqtesttutor@usc.edu", "pending", "CSCI310", "Mon 14");
        dba.addNewUser("ginareqtesttutee1@usc.edu", "password", "Tutee");
        dba.addNewUser("ginareqtesttutor1@usc.edu", "password", "Tutor");
        Request r1 = new Request("Your Name", "Your Name", "ginareqtesttutee1@usc.edu", "ginareqtesttutor1@usc.edu", "accepted", "CSCI356", "Mon 10");
        dba.addRequest("ginareqtesttutee@usc.edu", r);
        dba.addRequest("ginareqtesttutee1@usc.edu", r1);
    }
    @Test
    public void testGetAllRequests() {
        class getReqs extends getRequestsCommandWrapper {
            public void execute(ArrayList<Request> requests){
                for(Request r : requests){
                    assertTrue(r.course.equals("CSCI310"));
                    assertTrue(r.time.equals("Mon 14"));
                    assertTrue(r.status.equals("pending"));
                    assertTrue(r.tuteeEmail.equals("ginareqtesttutee@usc.edu"));
                    assertTrue(r.tutorEmail.equals("ginareqtesttutor@usc.edu"));
                }
            }
        }
        class getReqs1 extends getRequestsCommandWrapper {
            public void execute(ArrayList<Request> requests){
                for(Request r : requests){
                    assertTrue(r.course.equals("CSCI356"));
                    assertTrue(r.time.equals("Mon 10"));
                    assertTrue(r.status.equals("accepted"));
                    assertTrue(r.tuteeEmail.equals("ginareqtesttutee1@usc.edu"));
                    assertTrue(r.tutorEmail.equals("ginareqtesttutor1@usc.edu"));
                }
            }
        }
        dba.getAllRequests("ginareqtesttutee@usc.edu", "tutee", new getReqs());
        dba.getAllRequests("ginareqtesttutor@usc.edu", "tutor", new getReqs());
        dba.getAllRequests("ginareqtesttutee1@usc.edu", "tutee", new getReqs1());
        dba.getAllRequests("ginareqtesttutor1@usc.edu", "tutor", new getReqs1());
    }

    @Test
    public void testUpdateRequest() {
        Request r = new Request("Your Name", "Your Name", "ginareqtesttutee@usc.edu", "ginareqtesttutor@usc.edu", "accepted", "CSCI310", "Mon 14");
        dba.updateRequest(r);
        class getReqs extends getRequestsCommandWrapper {
            public void execute(ArrayList<Request> requests){
                for(Request r : requests){
                    assertTrue(r.course.equals("CSCI310"));
                    assertTrue(r.time.equals("Mon 14"));
                    assertTrue(r.status.equals("accepted"));
                    assertTrue(r.tuteeEmail.equals("ginareqtesttutee@usc.edu"));
                    assertTrue(r.tutorEmail.equals("ginareqtesttutor@usc.edu"));
                }
            }
        }
        dba.getAllRequests("ginareqtesttutee@usc.edu", "tutee", new getReqs());
        dba.getAllRequests("ginareqtesttutor@usc.edu", "tutor", new getReqs());
    }
}