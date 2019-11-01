package com.example.tutorsearcher.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

//        // TODO: DELETE
//        BenTest();
    }

    public LiveData<String> getText() {
        return mText;
    }

//    // TODO: DELETE
//    public void BenTest()
//    {
//        DBAccessor dba = new DBAccessor();
//        ArrayList<User> results = dba.search("CS 310", "Mon 14");
//        Log.d("ben", "results size: "+((Integer)(results.size())).toString());
//    }
}