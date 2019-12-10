package com.example.tutorsearcher.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.example.tutorsearcher.Tutor;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.activity.MainActivity;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getRequestsCommandWrapper;
import com.example.tutorsearcher.db.searchCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.example.tutorsearcher.ui.tutorProfileView.tutorProfileViewFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment{
    LinearLayout searchResultsContainer;
    //this hashmap stores all of the search result's button
    //the key is the button, and the value is the user that holds the data that correlates to that button
    HashMap<Button, User> buttonMap = new HashMap<Button, User>();
    Spinner daySpinner;//drop down menu for the day to be searched
    Spinner timeSpinner;//drop down menu for the time to be searched
    Spinner courseSpinner;//drop down menu for the course to be searched
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
        //textView = root.findViewById(R.id.searchText);
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

        //drop down menu for courses
        courseSpinner = (Spinner)root.findViewById(R.id.courseSpinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.CourseSpinnerOptions));
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        //what happens when we click in search button?
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchResultsContainer.removeAllViews();//clear the search result container
                String className = courseSpinner.getSelectedItem().toString();//class user is searching for
                String day = daySpinner.getSelectedItem().toString();//day user is searching for
                String time =timeSpinner.getSelectedItem().toString();//time user is searching for
                String dayTimeStr = day + " " + time;
                //TODO: use search prefs to search thru firebase to find any relavent times
                DBAccessor dba = new DBAccessor();
                searchResultWrapper resultWrapper = new searchResultWrapper();
                dba.search(className, dayTimeStr, resultWrapper);//search for tutor with the given class and time
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
            Log.d("kara1", ""+user_results.size());
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

                TextView nameText = new TextView(getContext());//button to hold text
                int width = (int) (129 * scale + 0.5f);//convert width/height to dp units
                int height = (int) (85 * scale + 0.5f);//convert width/height to dp units
                //add button to the hashmap, along with a new user
                User tempUser = results.get(i);
                //set when button is clicked
                nameText.setWidth(width);
                nameText.setHeight(height);
                nameText.setText(results.get(i).getName());//button will contain the tutor's name
                nameText.setTextSize(14);
                nameText.setTextAppearance(getContext(), R.style.SearchResultStyle);//set style of button to our stored style
                //add button to the horizontal view
                imagetextview.addView(nameText);
                //create button view profile

                final Button viewProfileBtn = new Button(getContext());//button to hold text
                buttonMap.put(viewProfileBtn, tempUser);
                //set when button is clicked
                //when button is clicked, a rwquest should be sent to the tutor. The info in the request will be held in the button text
                viewProfileBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        //when viewProfileBtn, user is taken to tutor profile
                        User tutor = buttonMap.get(viewProfileBtn);//this is the tutor to send request to
                        String tutorEmail = tutor.getEmail();
                        Log.d("click","click on tuor profile");
                        LoginActivity.tutorToView = new Tutor(tutorEmail);
                        openTutorProfile();

                    }
                });
                viewProfileBtn.setWidth(width);
                viewProfileBtn.setHeight(height);
                viewProfileBtn.setText("View Profile");//button will contain the tutor's name
                viewProfileBtn.setTextAppearance(getContext(), R.style.SearchResultStyle);//set style of button to our stored style
                //add button to the horizontal view
                imagetextview.addView(viewProfileBtn);

                final Button sendRequestBtn = new Button(getContext());//button to send request
                buttonMap.put(sendRequestBtn, tempUser);
                //set when button is clicked
                //when button is clicked, a rwquest should be sent to the tutor. The info in the request will be held in the button text
                sendRequestBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //when send request is clicked, we first need to check if this request has been sent already or not
//                        Log.d("click!","in onClick");
//                        //create a new request and send it
                        User tutor = buttonMap.get(sendRequestBtn);//this is the tutor to send request to
                        String tuteeName = LoginActivity.loggedInUser.getName();
                        String tutorName = tutor.getName();
                        String tuteeEmail = LoginActivity.loggedInUser.getEmail();
                        String tutorEmail = tutor.getEmail();
                        String course = courseSpinner.getSelectedItem().toString();
                        String time = daySpinner.getSelectedItem().toString() + " " + timeSpinner.getSelectedItem().toString();
                        Request request = new Request(tuteeName,tutorName,tuteeEmail,tutorEmail,"pending",course,time);
                        //now add the request to the database
                        //TODO: check if request with same time has already been sent or not
                        DBAccessor dba = new DBAccessor();
                        sendRequestFromSearchResultWrapper sendRequestWrapper = new sendRequestFromSearchResultWrapper(tutor);
                        dba.getAllRequests(LoginActivity.loggedInUser.getEmail(), LoginActivity.loggedInUser.getType(), sendRequestWrapper);//search for tutor with the given class and time
                    }
                });
                sendRequestBtn.setWidth(width);
                sendRequestBtn.setHeight(height);
                sendRequestBtn.setText("Send Request");//button will contain the tutor's name
                sendRequestBtn.setTextAppearance(getContext(), R.style.SearchResultStyle);//set style of button to our stored style
                //add button to the horizontal view
                imagetextview.addView(sendRequestBtn);
                searchResultsContainer.addView(imagetextview);//add the view containing the info to the screen
            }
        }
    }

    /**
     * After the search results come up, we need another wrapper to check all the exiting requests between
     * the tutee and tutor being requested
     */
    public class sendRequestFromSearchResultWrapper extends getRequestsCommandWrapper
    {
        User tutor;
        public sendRequestFromSearchResultWrapper(User tutor)
        {
            this.tutor = tutor;
        }
        float scale = getContext().getResources().getDisplayMetrics().density;//scale to convert to dp units
        public ArrayList<Request> results = new ArrayList<Request>();
        // email_results is an ArrayList of all the matching emails that fit the search criteria
        public void execute(ArrayList<Request> requests)
        {
            Log.d("kara", ""+results.size());
            String course = courseSpinner.getSelectedItem().toString();
            String time = daySpinner.getSelectedItem().toString() + " " + timeSpinner.getSelectedItem().toString();
            String tuteeEmail = LoginActivity.loggedInUser.getEmail();
            String tuteeName = LoginActivity.loggedInUser.getName();
            boolean alreadyRequested = false;//tells if tutee already sent this request
            for(Request request: requests)
            {
                //check if this current request has already been sent
                Log.d("requesttime",request.time);
                Log.d("time",time);
                Log.d("requestcourse",request.course);
                Log.d("course",course);
                Log.d("request tutee email",request.tuteeEmail);
                Log.d("tutee email",tuteeEmail);
                Log.d("request tutor email",request.tutorEmail);
                Log.d("tutor email",tutor.getEmail());
                if(request.time.equals(time) && request.course.equals(course)&& request.tuteeEmail.equals(tuteeEmail)
                        && request.tutorEmail.equals(tutor.getEmail()) && !request.status.equals("rejected"))
                {
                    //tuee has already sent this request to tutor
                    alreadyRequested = true;
                    //cannot send request because it has already been sent. make pop up saying request has already been sent
                }
            }
            //now that we checked all existing requests, send request if it hasn't been sent already, don't if it has
            if(alreadyRequested == true)
            {
                //cannot send request because it has already been sent. make pop up saying request has already been sent
                Toast toast = Toast.makeText(getContext(), "Request has already been sent", Toast.LENGTH_LONG);
                toast.show();
            }
            else//request has not been sent yet. send it
            {//(String tuteeName, String tutorName,String tuteeEmail, String tutorEmail, String status, String course, String time)
                Request newRequest = new Request(tuteeName,tutor.getName(),tuteeEmail,tutor.getEmail(),"pending",course,time);
                mDBAccessor.addRequest(LoginActivity.loggedInUser.getEmail(),newRequest);
                String requestMessage = "Sent request to "+tutor.getName()+" for the class "+course+" at "+time+".";
                Toast toast = Toast.makeText(getContext(), requestMessage, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
    public void openTutorProfile()
    {
        Log.d("open tutor profile","in open tutor profile");
        Intent intent = new Intent(getActivity(), tutorProfileViewFragment.class);
        startActivity(intent);
    }
}