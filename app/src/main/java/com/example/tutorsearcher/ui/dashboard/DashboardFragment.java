package com.example.tutorsearcher.ui.dashboard;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getProfileCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        // Create items in view
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final EditText nametext = root.findViewById(R.id.name_text);
        final EditText agetext = root.findViewById(R.id.age_text);
        final EditText gendertext = root.findViewById(R.id.gender_text);
        final TextView rating_text = root.findViewById(R.id.rating_text);
        final TextView num_ratings_text = root.findViewById(R.id.num_ratings_text);
        Button button = root.findViewById(R.id.edit_profile_button);

        // Load profile data from DB
        User u = LoginActivity.loggedInUser;
        new DBAccessor().getProfile(u.getEmail(), u.getType(), new LoadAndDisplayProfileWrapper(root));

        // make EditText fields non-editable by default
        nametext.setEnabled(false);
        agetext.setEnabled(false);
        gendertext.setEnabled(false);

        // hide fields if Tutee
        if(u.getType().equals("tutee"))
        {
            rating_text.setVisibility(View.GONE);
            num_ratings_text.setVisibility(View.GONE);
        }

        // add functionality for button to edit profile fields
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // toggle between "edit text" mode and "submit" mode
                Button b = (Button) v;
                if(b.getText().toString().equals("Edit Profile"))
                {
                    b.setText("Submit");
                    nametext.setEnabled(true);
                    agetext.setEnabled(true);
                    gendertext.setEnabled(true);
                }
                else {
                    b.setText("Edit Profile");
                    nametext.setEnabled(false);
                    agetext.setEnabled(false);
                    gendertext.setEnabled(false);

                    // SET USER PROFILE AND THEN UPDATE DB
                    User u = LoginActivity.loggedInUser;
                    u.setName(nametext.getText().toString());
                    u.setAge(Long.parseLong(agetext.getText().toString()));
                    u.setGender(gendertext.getText().toString());
                    new DBAccessor().updateProfile(u);
                }
            }
        });


        ImageView profileImageView = getActivity().findViewById(R.id.profile_picture);

        String url = "http://i.imgur.com/DvpvklR.png";

        if (profileImageView == null) {
            Log.d("Help", "profile image view is null");
        } else {
            Picasso.get().load(url).fit().into(profileImageView);
        }





        /*
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        return root;
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
        LoginActivity.loggedInUser = u;

        // populate profile view fields
        EditText nametext = view.findViewById(R.id.name_text);
        EditText agetext = view.findViewById(R.id.age_text);
        EditText gendertext = view.findViewById(R.id.gender_text);
        TextView rating_text = view.findViewById(R.id.rating_text);
        TextView num_ratings_text = view.findViewById(R.id.num_ratings_text);
        nametext.setText(u.getName());
        agetext.setText(((Long)(u.getAge())).toString());
        gendertext.setText(u.getGender());
        if(u.getType().equals("tutor"))
        {
            Log.d("ben", "Rating: "+((Double)(u.getRating())).toString());
            rating_text.setText("Rating: "+((Double)(u.getRating())).toString());
            num_ratings_text.setText("Number of ratings: "+((Long)(u.getNumRatings())).toString());
        }
    }
}