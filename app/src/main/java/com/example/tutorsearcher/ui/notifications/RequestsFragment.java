package com.example.tutorsearcher.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.tutorsearcher.R;
import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.User;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getRequestsCommandWrapper;
import com.example.tutorsearcher.db.searchCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;

import java.util.ArrayList;

public class RequestsFragment extends Fragment {

    private RequestsViewModel requestsViewModel;
    private DBAccessor mDBAccessor = new DBAccessor();
    LinearLayout requestsContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        requestsViewModel =
                ViewModelProviders.of(this).get(RequestsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        requestsContainer = (LinearLayout) root.findViewById(R.id.requestsContainer);
        //add any requests here
        //first check if user logged in is a tutor or tutee
        Log.d("logged in user type: ",LoginActivity.loggedInUser.getType());
        if(LoginActivity.loggedInUser.getType().equals("tutor"))
        {//the user is a tutor
            //display all of the requests sent to the tutor
            //ArrayList<Request> requests =
            //mDBAccessor.getAllRequests(LoginActivity.loggedInUser.getEmail(), LoginActivity.loggedInUser.getType());
           // Log.d("reqeusts size", ""+requests.size());
            float scale = getContext().getResources().getDisplayMetrics().density;//scale to convert to dp units
            //iterate through all the request and show their status
            //TODO this is just a test, we will use the lines above to get the requests in the real version
            ArrayList<Request> requests = new ArrayList<Request>();
            requests.add(new Request("tuteeName", "tutorName","tuteeEmail",
                    "tutorEmail", "pending", "cs310", "mon 3-4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            for(Request request: requests)

            {
                //create view to add to the linear layout
                LinearLayout horizontalLayout = new LinearLayout(getContext());//make view to hold image and text button
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);//make layout horizontal
                TextView requestInfo = new TextView(getContext());//holds the tutee's name, email, class, and time for their request
                requestInfo.setWidth((int) (213 * scale + 0.5f));
                requestInfo.setHeight((int) (85 * scale + 0.5f));
                //get all the info from the tutee request
                requestInfo.setText(request.tuteeName + "\n"+request.course+" "+request.time+"\n"+request.tuteeEmail);
                horizontalLayout.addView(requestInfo);
                if(request.status.equals("pending"))
                {//request is pending, so we want one button to accept, and another to reject
                    //create two buttons that either accepts or rejects the request
                    Button acceptButton = new Button(getContext());//button to accept
                    acceptButton.setGravity(Gravity.RIGHT);
                    acceptButton.setWidth((int) (105 * scale + 0.5f));
                    acceptButton.setHeight((int) (60 * scale + 0.5f));
                    acceptButton.setText("Accept");
                    Button rejectButton = new Button(getContext());//button to reject
                    rejectButton.setGravity(Gravity.RIGHT);
                    rejectButton.setWidth((int) (105 * scale + 0.5f));
                    rejectButton.setHeight((int) (60 * scale + 0.5f));
                    rejectButton.setText("Reject");

                    //add the buttons to the horiz layout
                    horizontalLayout.addView(acceptButton);
                    horizontalLayout.addView(rejectButton);
                    requestsContainer.addView(horizontalLayout);
                }
                else if(request.status.equals("accepted"))
                {
                    //create a button that does nothing, it just says "Accepted"
                    Button acceptedButton = new Button(getContext());//button to accept
                    acceptedButton.setGravity(Gravity.RIGHT);
                    acceptedButton.setWidth((int) (105 * scale + 0.5f));
                    acceptedButton.setHeight((int) (60 * scale + 0.5f));
                    acceptedButton.setText("Accepted");

                    //add the buttons to the horiz layout
                    horizontalLayout.addView(acceptedButton);
                    requestsContainer.addView(horizontalLayout);
                }
            }
        }
        else//the user is a tutee
        {
            //display all of the requests they have made, only show the pending and accepted requests

            //ArrayList<Request> requests =
            //mDBAccessor.getAllRequests(LoginActivity.loggedInUser.getEmail(), LoginActivity.loggedInUser.getType());
            // Log.d("reqeusts size", ""+requests.size());
            float scale = getContext().getResources().getDisplayMetrics().density;//scale to convert to dp units
            //iterate through all the request and show their status
            //TODO this is just a test, we will use the lines above to get the requests in the real version
            ArrayList<Request> requests = new ArrayList<Request>();
            requests.add(new Request("tuteeName", "tutorName","tuteeEmail",
                    "tutorEmail", "pending", "cs310", "mon 3-4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));
            requests.add(new Request("tuteeName2", "tutorName2","tuteeEmail2",
                    "tutorEmail2", "accepted", "cs310", "mon 3-s4pm"));


            for(Request request: requests)
            {
                LinearLayout horizontalLayout = new LinearLayout(getContext());//make view to hold image and text button
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);//make layout horizontal
                TextView requestInfo = new TextView(getContext());//holds the tutors's name, class, and time for their request, and email if request has been accepted
                requestInfo.setWidth((int) (213 * scale + 0.5f));
                requestInfo.setHeight((int) (85 * scale + 0.5f));
                //get all the info from the tutee request
                requestInfo.setText(request.tutorName + "\n"+request.course+" "+request.time);
                horizontalLayout.addView(requestInfo);
                if(request.status.equals("pending"))
                {//request is pending, so the user cannot see the tutor's email
                    Button pendingButton = new Button(getContext());//button to accept
                    pendingButton.setGravity(Gravity.RIGHT);
                    pendingButton.setWidth((int) (105 * scale + 0.5f));
                    pendingButton.setHeight((int) (60 * scale + 0.5f));
                    pendingButton.setText("Pending");

                    //add the buttons to the horiz layout
                    horizontalLayout.addView(pendingButton);
                    requestsContainer.addView(horizontalLayout);
                }
                else if(request.status.equals("accepted"))
                {//request has been accepted, so user can view the tutor's email
                    Button acceptedButton = new Button(getContext());//button to accept
                    acceptedButton.setGravity(Gravity.RIGHT);
                    acceptedButton.setWidth((int) (105 * scale + 0.5f));
                    acceptedButton.setHeight((int) (60 * scale + 0.5f));
                    acceptedButton.setText("Accepted");
                    //create another button that shows the tutor's email for contact
                    Button emailButton = new Button(getContext());//button to accept
                    emailButton.setGravity(Gravity.RIGHT);
                    emailButton.setWidth((int) (105 * scale + 0.5f));
                    emailButton.setHeight((int) (60 * scale + 0.5f));
                    emailButton.setText(request.tutorEmail);

                    //add the buttons to the horiz layout
                    horizontalLayout.addView(acceptedButton);
                    horizontalLayout.addView(emailButton);
                    requestsContainer.addView(horizontalLayout);
                }
            }
        }
        return root;
    }

    public class requestsWrapper extends getRequestsCommandWrapper
    {
        public ArrayList<Request> results = new ArrayList<Request>();
        // email_results is an ArrayList of all the matching emails that fit the search criteria
        public void execute(ArrayList<Request> requests)
        {
            results = requests;
            Log.d("fuck off", ""+results.size());
            for(int i = 0; i < results.size(); i++)//iterate through all the results
            {
                Log.d("kara1","result is " + results.get(i));
            }

        }
    }
}