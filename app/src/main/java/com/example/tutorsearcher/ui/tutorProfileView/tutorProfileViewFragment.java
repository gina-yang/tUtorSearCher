package com.example.tutorsearcher.ui.tutorProfileView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.activity.MainActivity;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getProfileCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.example.tutorsearcher.ui.login.LoginViewModel;
import com.example.tutorsearcher.ui.login.LoginViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class tutorProfileViewFragment extends AppCompatActivity {

    private TutorProfileViewModel dashboardViewModel;
    private DBAccessor dba = new DBAccessor();
    EditText nametext;
    EditText agetext;
     EditText gendertext;
     TextView rating_text;
     TextView num_ratings_text;
     EditText courses_text;
     EditText availability_text;

     TextView ratings_label;
     TextView num_ratings_label;
     TextView courses_label;
     TextView availability_label;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tutor_profile_view);
//        TutorProfileViewFragment = ViewModelProviders.of(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);

        // Create items in view
        dashboardViewModel =
                ViewModelProviders.of(this).get(TutorProfileViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        nametext = findViewById(R.id.name_text);
        agetext = findViewById(R.id.age_text);
        gendertext = findViewById(R.id.gender_text);
        rating_text = findViewById(R.id.rating_text);
        num_ratings_text = findViewById(R.id.num_ratings_text);
        courses_text = findViewById(R.id.courses_text);
        availability_text = findViewById(R.id.availability_text);

        ratings_label = findViewById(R.id.textView3);
        num_ratings_label = findViewById(R.id.textView4);
        courses_label = findViewById(R.id.textView5);
        availability_label = findViewById(R.id.textView6);

        // Load profile data from DB
        User u = LoginActivity.tutorToView;
        dba.getProfile(u.getEmail(), u.getType(), new LoadAndDisplayProfileWrapper(null));

        // make EditText fields non-editable by default
        nametext.setEnabled(false);
        agetext.setEnabled(false);
        gendertext.setEnabled(false);
        courses_text.setEnabled(false);
        availability_text.setEnabled(false);


        ImageView profileImageView = findViewById(R.id.profile_picture);

        String url = "http://i.imgur.com/DvpvklR.png";

        if (profileImageView == null) {
            Log.d("Help", "profile image view is null");
        } else {
            Picasso.get().load(url).fit().into(profileImageView);
        }
    }

    class LoadAndDisplayProfileWrapper extends getProfileCommandWrapper
    {
        private View view;

        public LoadAndDisplayProfileWrapper(View view_)
        {
            view = view_;
        }

        public void execute(User u)
        {
            // update loggedInUser to u
            LoginActivity.tutorToView = u;
            nametext.setText(u.getName());
            agetext.setText(((Long)(u.getAge())).toString());
            gendertext.setText(u.getGender());
            if(u.getType().equals("tutor"))
            {
                Log.d("ben", "Rating: "+((Double)(u.getRating())).toString());
                rating_text.setText(((Double)(u.getRating())).toString());
                num_ratings_text.setText(((Long)(u.getNumRatings())).toString());

                // Put courses in giant string
                StringBuilder coursesList = new StringBuilder();
                if( !u.getCourses().isEmpty() ){
                    for( String s : u.getCourses() ){
                        coursesList.append(s + "\n");
                    }
                    courses_text.setText(coursesList.toString());
                }

                // Put availability in giant string
                StringBuilder availList = new StringBuilder();
                if( !u.getAvailability().isEmpty() && u.getAvailability() != null) {
                    for( String s : u.getAvailability() ){
                        availList.append(s + "\n");
                    }
                    availability_text.setText(availList.toString());
                }
            }
        }
    }
}

