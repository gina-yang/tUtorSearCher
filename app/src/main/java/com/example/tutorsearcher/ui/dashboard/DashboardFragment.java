package com.example.tutorsearcher.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // get profile, which loads info into loggedInUser and populates view fields
        User u = LoginActivity.loggedInUser;
        new DBAccessor().getProfile(u.getEmail(), u.getType(), new LoadAndDisplayProfileWrapper(dashboardViewModel));


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
    private DashboardViewModel dashboardViewModel;

    public LoadAndDisplayProfileWrapper(DashboardViewModel dashboardViewModel_)
    {
        dashboardViewModel = dashboardViewModel_;
    }

    public void execute(User u)
    {
        // update loggedInUser to u
        LoginActivity.loggedInUser = u;

        // populate profile view fields
        // TODO: USE DASHBOARD VIEW TO POPULATE FIELDS
    }
}