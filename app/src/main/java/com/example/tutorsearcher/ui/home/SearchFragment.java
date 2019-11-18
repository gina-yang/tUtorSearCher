package com.example.tutorsearcher.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;
import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.Tutee;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.searchCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment{
    LinearLayout searchResultsContainer;
    //this hashmap stores all of the search result's button
    //the key is the button, and the value is the user that holds the data that correlates to that button
    HashMap<Button, User> buttonMap = new HashMap<Button, User>();
    Spinner daySpinner;//drop down menu for the day to be searched
    Spinner timeSpinner;//drop down menu for the time to be searched
    EditText textView;//hold the course user is searching for
    //create a db acessor that will be used to do search
    DBAccessor mDBAccessor = new DBAccessor();
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
        textView = root.findViewById(R.id.searchText);
        final Button searchButton = root.findViewById(R.id.searchButton);
        //this is the vertical scrolling layout that will hold all of the search results
        searchResultsContainer = (LinearLayout) root.findViewById(R.id.searchResultsContainer);

        //spinner stuff below
        //drop down menu for day
        daySpinner = (Spinner)root.findViewById(R.id.daySpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.DaySpinnerOptions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(myAdapter);
        //drop down menu for time
        timeSpinner = (Spinner)root.findViewById(R.id.timeSpinner);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.TimeSpinnerOptions));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        //what happens when we click in search button?
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchResultsContainer.removeAllViews();//clear the search result container
                String className = textView.getText().toString();//class user is searching for
                String day = daySpinner.getSelectedItem().toString();//day user is searching for
                String time =timeSpinner.getSelectedItem().toString();//time user is searching for
                String dayTimeStr = day + " " + time;
                //TODO: use search prefs to search thru firebase to find any relavent times
                DBAccessor dba = new DBAccessor();
                searchResultWrapper resultWrapper = new searchResultWrapper();
                dba.search(className, dayTimeStr, resultWrapper);//search for tutor with the given class and time
                //for testing puposes, Jane Doe teaches CS 310 Monday 14 o clock

                /*
                //TEST CODE
                //create view to add to the linear layout
                LinearLayout testview = new LinearLayout(getContext());//make view to hold image and text button
                testview.setOrientation(LinearLayout.HORIZONTAL);//make layout horizontal
                Button myButton = new Button(getContext());//button to hold text
                myButton.setText("Bob");

                myButton.setTextAppearance(getContext(), R.style.SearchResultStyle);//set style of button to our stored style
                //add button to the view
                testview.addView(myButton);
                searchResultsContainer.addView(testview);//add the view containing the info to the screen

                 */
            }
        });

        return root;
    }

    /**
     * Conducts the search, and updates the app to display all of the results found
     */
    public class searchResultWrapper extends searchCommandWrapper
    {
        public ArrayList<User> results = new ArrayList<User>();
        // email_results is an ArrayList of all the matching emails that fit the search criteria
        public void execute2(ArrayList<User> user_results)
        {
            results = user_results;
            Log.d("kara", ""+user_results.size());
            for(int i = 0; i < results.size(); i++)//iterate through all the results
            {
                Log.d("kara1","result is " + results.get(i));
                //add all of the search results found in this method
                //create view to add to the linear layout
                LinearLayout imagetextview = new LinearLayout(getContext());//make view to hold image and text button
                imagetextview.setOrientation(LinearLayout.HORIZONTAL);//make layout horizontal
                //ImageView Setup
                ImageView imageView = new ImageView(getContext());
                //setting image resource
                float scale = getContext().getResources().getDisplayMetrics().density;//scale to convert to dp units
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams((int) (85 * scale + 0.5f),(int) (85 * scale + 0.5f));//setting image width and height
                imageView.setLayoutParams(parms);
                imageView.setImageResource(getContext().getResources().getIdentifier("drawable/" + results.get(i).getProfilePic(), null, getContext().getPackageName()));

                imageView.setMaxHeight(85);
                imageView.setMaxWidth(85);
                //add the image to the view


                final Button myButton = new Button(getContext());//button to hold text
                int width = (int) (213 * scale + 0.5f);//convert width/height to dp units
                int height = (int) (85 * scale + 0.5f);//convert width/height to dp units
                //add button to the hashmap, along with a new user
                User tempUser = results.get(i);
                buttonMap.put(myButton, tempUser);
                //set when button is clicked
                //when button is clicked, a rwquest should be sent to the tutor. The info in the request will be held in the button text
                myButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.d("click!","in onClick");
                        //create a new request and send it
                        User tutor = buttonMap.get(myButton);//this is the tutor to send request to
                        String tuteeName = LoginActivity.loggedInUser.getName();
                        String tutorName = tutor.getName();
                        String tuteeEmail = LoginActivity.loggedInUser.getEmail();
                        String tutorEmail = tutor.getEmail();
                        String course = textView.getText().toString();
                        String time = daySpinner.getSelectedItem().toString() + " " + timeSpinner.getSelectedItem().toString();
                        Request request = new Request(tuteeName,tutorName,tuteeEmail,tutorEmail,"pending",course,time);
                        //now add the request to the database
                        mDBAccessor.addRequest(LoginActivity.loggedInUser.getEmail(),request);
                        String requestMessage = "Sent request to "+tutorName+" for the class "+course+" at "+time+".";
                        Toast toast = Toast.makeText(getContext(), requestMessage, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                //myButton.setOnClickListener(this); // calling onClick() method
                myButton.setWidth(width);
                myButton.setHeight(height);
                myButton.setText(results.get(i).getName());//button will contain the tutor's name
                myButton.setTextAppearance(getContext(), R.style.SearchResultStyle);//set style of button to our stored style
                //add button to the horizontal view
                imagetextview.addView(myButton);
                searchResultsContainer.addView(imagetextview);//add the view containing the info to the screen
            }

        }
    }
}