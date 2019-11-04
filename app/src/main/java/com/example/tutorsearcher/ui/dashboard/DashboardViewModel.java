package com.example.tutorsearcher.ui.dashboard;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}