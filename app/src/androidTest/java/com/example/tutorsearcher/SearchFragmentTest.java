package com.example.tutorsearcher;

import android.util.Log;

import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.searchCommandWrapper;
import com.example.tutorsearcher.ui.home.SearchFragment;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static org.junit.Assert.assertEquals;

/**
 * Tests if searching for something in the data base works as it should
 */
public class SearchFragmentTest{

    DBAccessor dba;
    static int flag = 1;//for testing purposes, i hate firebase
    ArrayList<User> results = new ArrayList<User>();
    @Before
    public void setup(){
        dba = new DBAccessor();

//        dba.addNewUser("utesttutee@usc.edu", "password", "Tutee");
//        dba.addNewUser("utesttutor@usc.edu", "password", "Tutor");
        Log.d("hello","hello");
    }
    @Test
    public void searchTest() throws Exception {
        Log.d("searchdb accessor test","searchunittest");
        String className = "TEST100";//we will test searching for AMST100
        String dayTimeStr = "Wed 14";

        /**
         * Conducts the search, and updates the app to display all of the results found
         */
        class searchResultWrapper extends searchCommandWrapper
        {

            // email_results is an ArrayList of all the matching emails that fit the search criteria
            @Test
            public void execute2(ArrayList<User> user_results)
            {
                results = user_results;
                System.out.println("# of search results in wrapper"+ ""+user_results.size());
                SearchFragmentTest.flag = 2;
                assertEquals("Test resgegTutor",user_results.get(0).getName());

            }
        }
        searchResultWrapper resultWrapper = new searchResultWrapper();
        dba.search(className, dayTimeStr, resultWrapper);//search for tutor with the given class and time
        System.out.println("# of search results"+ " "+results.size());
        System.out.println(SearchFragmentTest.flag);
        //FLAG ISN'T CHANGING TO 2??????
        //assertEquals("Test Tutor",results.get(0).getName());
    }
    /**
     * test if search in the database functions properly
     */
    //    @Test
    //    public void searchDBAccessorTest()
    //    {
    //        Log.d("searchdb accessor test","searchunittest");
    //        String className = "TEST100";//we will test searching for AMST100
    //        String dayTimeStr = "Wed 14";
    //        SearchUnitTest.searchResultWrapper resultWrapper = new SearchUnitTest.searchResultWrapper();
    //        if(this == null)
    //        {
    //            System.out.println("this is null");
    //        }
    //        else
    //        {
    //            System.out.println("this is not null");
    //        }
    //        FirebaseApp.initializeApp(this);
    //        dba = new DBAccessor();
    //        dba.search(className, dayTimeStr, resultWrapper);//search for tutor with the given class and time
    //    }
}
