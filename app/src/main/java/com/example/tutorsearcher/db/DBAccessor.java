package com.example.tutorsearcher.db;

import android.util.Log;

import com.example.tutorsearcher.User;
import com.example.tutorsearcher.Tutor;
import com.example.tutorsearcher.Tutee;

import androidx.annotation.NonNull;

import com.example.tutorsearcher.Availability;
import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAccessor {
    private boolean loggedin;

    private FirebaseFirestore db;

    /**
     * Constructor method
     * Initializes instance of the Firestore database
     */
    public DBAccessor(){
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Checks if user is new by looking in database
     * @param email email to check
     * @param role role to check
     * @return boolean true if user is new
     */
    public boolean isNewUser(String email, String role){
        CollectionReference roleColl = null;
        if( role.equals("tutor")){
            roleColl = db.collection("tutors");
        }
        else if( role.equals("tutee")){
            roleColl = db.collection("tutees");
        }
        loggedin = false;
        Query query = roleColl.whereEqualTo("email", email);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if( document.exists() ){
                            loggedin = true;
                            System.out.println("User logged in!");
                        }
                    }
                } else {
                    System.out.println("Error getting documents");
                }
            }
        });
        return loggedin;
    }

    /**
     * Adds a new user to the database
     * @param email user email
     * @param password user password
     * @param role user role (tutee or tutor)
     */
    public void addNewUser(String email, String password, String role){
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("email", email);
        newUser.put("password", password);

        db.collection(role).document(email)
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added with user email");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }

    /**
     * Adds availability of tutor to database
     * @param email email of tutor
     * @param a String representing availability
     */
    public void addAvailability(String email, String a){
        Map<String, Object> availability = new HashMap<>();
        String[] sp = a.split(" ", 2);
        availability.put("day", sp[0]);
        availability.put("starttime", sp[1]);

        db.collection("tutors").document(email)
                .collection("availabilitylist")
                .add(availability)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }

    /**
     * TODO
     * Gets all of a tutor's availability
     * @param email tutor's email
     * @return ArrayList of Strings representing availability
     */
    public ArrayList<String> getAllAvailability(String email){
        ArrayList<String> alist = new ArrayList<String>();

        return alist;
    }

    /**
     * Adds a tutee request to the database
     * @param email tutee's email
     */
    public void addRequest(String email, Request r){
        Map<String, Object> request = new HashMap<>();
        request.put("course", r.course);
        request.put("status", r.status);
        request.put("starttime", r.starttime);
        request.put("endtime", r.endtime);
        request.put("tutor", r.tutorEmail);
        request.put("tutee", r.tuteeEmail);

        db.collection("requests")
                .add(request)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }

    /**
     * TODO
     * Gets all requests associated with a user (either tutor or tutee)
     * @return ArrayList of Request objects
     */
    public ArrayList<Request> getAllRequests(){
        ArrayList<Request> r = new ArrayList<Request>();

        return r;
    }

    // Get availability at certain time?

    // Update user profile [works for both tutor and tutee using polymorphism] (Ben)
    // Takes in a User and updates all fields in Firebase
    /**
     * Update user profile [works for both tutor and tutee using polymorphism]
     * @param u user to be updated
     */
    public void updateProfile(User u)
    {
        // create new map and courses and availabilities
        Map<String, Object> profileMap = new HashMap<>();

        profileMap.put("email", u.getEmail());
        profileMap.put("age", u.getAge());
        profileMap.put("gender", u.getGender());
        profileMap.put("name", u.getName());
        profileMap.put("pic", u.getProfilePic());

        // if it's a tutor, add tutor-specific fields to map and update courses and availabilities
        if(u.getType()=="tutor")
        {
            profileMap.put("numratings", u.getNumRatings());
            profileMap.put("rating", u.getRating());
            profileMap.put("courses", u.getCourses());
            profileMap.put("availability", u.getAvailability());
        }

        // connect to DB and overwrite this profile
        db.collection(u.getType()+"s").document(u.getEmail())
                .update(profileMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Profile overwritten with user email");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }

    /**
     * Fetches the profile of a user
     * @param email user email
     * @param role user role (tutee or tutor)
     * @param wrapper a getProfileWrapper class containing instructions to execute on profile load
     */
    public void getProfile(final String email, final String role, final getProfileCommandWrapper wrapper)
    {
        DocumentReference docRef = db.collection(role+"s").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        // Appropriately create a Tutor or Tutee instance
                        User u;
                        if(role=="tutor")
                        {
                            u = new Tutor(email);
                        }
                        else
                        {
                            u = new Tutee(email);
                        }

                        // Add all the generic User info
                        long age = (Long)document.get("age");
                        String gender = (String)document.get("gender");
                        String name = (String)document.get("name");
                        String profilePic = (String)document.get("pic");
                        u.setAge(age);
                        u.setGender(gender);
                        u.setName(name);
                        u.setProfilePic(profilePic);

                        // If it's a tutor, add all Tutor-specific info
                        if(role=="tutor")
                        {
                            long numRatings = (Long)document.get("numratings");
                            double rating = (Double)document.get("rating");
                            ArrayList<String> courses = (ArrayList<String>)document.get("courses");
                            ArrayList<String> availability = (ArrayList<String>)document.get("availability");
                            u.setNumRatings(numRatings);
                            u.setRating(rating);
                            u.setCourses(courses);
                            u.setAvailability(availability);
                        }
                        wrapper.execute(u);
                    }
                    else
                    {
                        Log.d("ben", "document not found: "+email);
                        wrapper.execute(null); // if we get here, we didn't find a matching profile
                    }
                } else {
                    Log.d("ben", "getProfile task unsuccessful");
                    wrapper.execute(null); // if we get here, we didn't find a matching profile
                }
            }
        });
    }

    /**
     * Executes an action after searching for all the tutors who match a given course and timeslot
     * @param course the course
     * @param timeslot the timeslot
     * @param wrapper a onSearchResultLoadWrapper class containing instructions to execute on results load
     */
    public void search(final String course, final String timeslot, final searchCommandWrapper wrapper)
    {
        // Query against the DB
        final ArrayList<String> emails = new ArrayList<String>();

        db.collection("tutors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // go through all documents in the collection...
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> docData = document.getData();
                                ArrayList<String> courses = (ArrayList<String>) document.get("courses");;
                                ArrayList<String> availability = (ArrayList<String>) document.get("availability");
                                // if this document has the correct course and timeslot...
                                if(courses.contains(course) && availability.contains(timeslot))
                                {
                                    emails.add(document.getId()); // add this doc ID (the email) to the emails list
                                }
                            }
                        }
                        // finally, execute the searchCommandWrapper
                        wrapper.execute(emails);
                    }
                });
    }
}


