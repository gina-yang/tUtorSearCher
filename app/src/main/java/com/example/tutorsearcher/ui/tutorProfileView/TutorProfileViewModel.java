package com.example.tutorsearcher.ui.tutorProfileView;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tutorsearcher.User;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

public class TutorProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TutorProfileViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}