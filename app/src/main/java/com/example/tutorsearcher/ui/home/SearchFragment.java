package com.example.tutorsearcher.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;
import com.example.tutorsearcher.ui.login.LoginActivity;

public class SearchFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        /*homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Search");
            }
        });*/
        //final EditText passwordEditText = findViewById(R.id.searchText);
        final EditText textView = root.findViewById(R.id.searchText);
        final Button searchButton = root.findViewById(R.id.searchButton);

        //spinner stuff below
        //drop down menu for day
        final Spinner daySpinner = (Spinner)root.findViewById(R.id.daySpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.DaySpinnerOptions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(myAdapter);
        //drop down menu for time
        final Spinner timeSpinner = (Spinner)root.findViewById(R.id.timeSpinner);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.TimeSpinnerOptions));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        //what happens when we click in search button?
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String className = textView.getText().toString();//class user is searching for
                String day = daySpinner.getSelectedItem().toString();//day user is searching for
                String time =timeSpinner.getSelectedItem().toString();//time user is searching for
                String dayTimeStr = day + " " + time;
                //TODO: use search prefs to search thru firebase to find any relvent times
            }
        });

        return root;
    }

}