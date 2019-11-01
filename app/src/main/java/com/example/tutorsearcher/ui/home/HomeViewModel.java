package com.example.tutorsearcher.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getProfileCommandWrapper;
import com.example.tutorsearcher.db.searchCommandWrapper;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        // TODO: DELETE
        BenTest();
    }

    public LiveData<String> getText() {
        return mText;
    }

    // This class just contains one method: execute
    // Which tells you what to do when you have gotten the ArrayList<User> of search results
    // This is necessary because you need some way of passing the action to execute post-load via a method parameter
    public class printSearchResultWrapper extends searchCommandWrapper
    {
        // email_results is an ArrayList of all the matching emails that fit the search criteria
        public void execute(ArrayList<String> email_results)
        {
            Log.d("ben", "email_results size: "+((Integer)(email_results.size())).toString());
        }
    }

    public class printProfileNameWrapper extends getProfileCommandWrapper
    {
        public void execute(User u)
        {
            Log.d("ben", "User name: "+u.getName());
        }
    }

    // You can define different wrapper functions, as long as they extend the base wrapper for the func
    public class printProfileGenderWrapper extends getProfileCommandWrapper
    {
        public void execute(User u)
        {
            Log.d("ben", "Gender: "+u.getGender());
        }
    }

    // TODO: DELETE
    public void BenTest()
    {
        DBAccessor dba = new DBAccessor();
        searchCommandWrapper resultWrapper = new printSearchResultWrapper();
        dba.search("CS 310", "Mon 14", resultWrapper);
        dba.getProfile("janedoe@usc.edu", "tutor", new printProfileNameWrapper());
        dba.getProfile("janedoe@usc.edu", "tutor", new printProfileGenderWrapper());
    }
}