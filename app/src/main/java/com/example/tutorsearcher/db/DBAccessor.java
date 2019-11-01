package com.example.tutorsearcher.db;

import android.util.Log;

import com.example.tutorsearcher.User;
import com.example.tutorsearcher.Tutor;
import com.example.tutorsearcher.Tutee;

import androidx.annotation.NonNull;

import com.example.tutorsearcher.Request;
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
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import static com.firebase.ui.auth.AuthUI.TAG;

public class DBAccessor {
    private boolean exists;
    private boolean loggedin;
    private ArrayList<Request> rlist;

    public FirebaseFirestore db;
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
        exists = false;
        Query query = roleColl.whereEqualTo("email", email);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if( document.exists() ){
                            exists = true;
                            Log.d("success","User exists!");
                        }
                    }
                } else {
                    Log.d("failure","User doesn't exist");
                }
            }
        });
        return exists;
    }

    /**
     * Checks if user with given email, password, and role exists in the database
     * @param email email
     * @param password password
     * @param role user role (tutor or tutee)
     * @return false if login failed. true if login exists
     */
    public boolean validateUser(String email, String password, String role){
        loggedin = false;
        if( !this.isNewUser(email, role) ){
            return loggedin;  // User email doesn't exist in database; return false
        }
        // Otherwise, check for password
        if( role.equals("tutor")){
            db.collection("tutors").whereEqualTo("email", email).whereEqualTo("password", password)
                .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                loggedin = true;
                            } else {
                                loggedin = false;
                            }
                        }
                    });
        }
        else if( role.equals("tutee")){
            db.collection("tutees").whereEqualTo("email", email).whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                loggedin = true;
                            } else {
                                loggedin = false;
                            }
                        }
                    });
        }
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
                        Log.d("success","DocumentSnapshot added with user email");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("failure", "Error adding document");
                    }
                });
    }

    /**
     * Adds a tutee request to the database
     * @param email tutee's email
     */
    public void addRequest(String email, Request r){
        Map<String, Object> request = new HashMap<>();
        request.put("course", r.course);
        request.put("status", r.status);
        request.put("time", r.time);
        request.put("tutor", r.tutorEmail);
        request.put("tutee", r.tuteeEmail);

        db.collection("requests")
                .add(request)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("failure", "Error adding document");
                    }
                });
    }

    /**
     * Gets all requests associated with a user
     * @param email user's email
     * @param role user's role (tutor or tutee)
     * @return ArrayList of Request objects
     */
    public ArrayList<Request> getAllRequests(String email, String role){
        rlist = new ArrayList<Request>(); // ? A way to make it not final?
        CollectionReference reqRef = db.collection("requests");
        reqRef.whereEqualTo(role, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(document.getId() + " => " + document.getData());
                                Request r = new Request(String.valueOf(document.get("tutee")), String.valueOf(document.get("tutor")), String.valueOf(document.get("status")), String.valueOf(document.get("course")), String.valueOf(document.get("time")));
                                rlist.add(r);
                            }
                        } else {
                            Log.d("failure", "Error getting documents: ");
                        }
                    }
                });
        return rlist;
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
        if(u.getType().equals("tutor"))
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

    // Get profile (Ben)
    // Automatically creates a Tutor or Tutee based on the role in the email
    // Returns that object
    /**
     * Fetches the profile of a user
     * @param email user email
     * @param role user role (tutee or tutor)
     * @return a User class instance (Tutor or Tutee) with info corresponding to profile
     */
    public User getProfile(String email, String role)
    {
        final String role_ = role;
        final User[] result = new User[1]; // ugly hack to get around anonymous class problem, as detailed in : https://stackoverflow.com/questions/5977735/setting-outer-variable-from-anonymous-inner-class

        // Check DB for email
        DocumentReference docRef = db.collection(role+"s").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String email = (String)document.get("email");

                        // Appropriately create a Tutor or Tutee instance
                        User u;
                        if(role_.equals("tutor"))
                        {
                            u = new Tutor(email);
                        }
                        else
                        {
                            u = new Tutee(email);
                        }

                        // Add all the generic User info
                        int age = (Integer)document.get("email");
                        String gender = (String)document.get("gender");
                        String name = (String)document.get("name");
                        String profilePic = (String)document.get("pic");
                        u.setAge(age);
                        u.setGender(gender);
                        u.setName(name);
                        u.setProfilePic(profilePic);

                        // If it's a tutor, add all Tutor-specific info
                        if(role_.equals("tutor"))
                        {
                            int numRatings = (Integer)document.get("numratings");
                            double rating = (Double)document.get("rating");
                            ArrayList<String> courses = (ArrayList<String>)document.get("courses");
                            ArrayList<String> availability = (ArrayList<String>)document.get("availability");
                            u.setNumRatings(numRatings);
                            u.setRating(rating);
                            u.setCourses(courses);
                            u.setAvailability(availability);
                        }
                        result[0] = u;
                    } else {
                        result[0] = null;
                    }
                } else {
                    result[0] = null;
                }
            }
        });

        return result[0];
    }

    // inner class to help with search
//    class searchThread implements Runnable
//    {
//        final ArrayList<String> emails;
//        final String course;
//        final String timeslot;
//        final FirebaseFirestore threadDB;
//        boolean doneFlag;
//
//        public searchThread(ArrayList<String> emails_, String course_, String timeslot_)
//        {
//            emails = emails_;
//            course = course_;
//            timeslot = timeslot_;
//            threadDB = FirebaseFirestore.getInstance();
//            doneFlag = false;
//            Log.d("ben", "searchThread constructed");
//        }
//        public void run(){
//            Log.d("ben", "searchThread starting");
//            threadDB.collection("tutors")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        try {
//                            if (task.isSuccessful()) {
//                                // go through all documents in the collection...
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    ArrayList<String> courses = (ArrayList<String>) document.get("courses");
//                                    ArrayList<String> availability = (ArrayList<String>) document.get("availability");
//                                    if (courses.contains(course) && availability.contains(timeslot)) {
//                                        Log.d("ben", "email found, adding email=" + document.getId());
//                                        emails.add(document.getId()); // add this doc ID (the email) to the emails list
//                                    }
//                                }
//                                Log.d("ben", "finished looking thru docs");
//                            }
//                            Log.d("ben", "finished task successful block");
//                        }
//                        finally {
//                            Log.d("ben", "in finally block");
//                            doneFlag = true;
//                        }
//                    }
//                });
//            while(!doneFlag){ /* wait */ }
//            Log.d("ben", "searchThread finished");
//        }
//    }

    /**
     * Returns all the tutors who match a given course and timeslot
     * @param course the course
     * @param timeslot the timeslot
     * @return an ArrayList of User objects corresponding to Tutors who match the course and timeslot
     */
    public ArrayList<User> search(String course, String timeslot)
    {
        Log.d("ben", "search start");

        // Query against the DB
        final ArrayList<String> emails = new ArrayList<String>();
        final String course_ = course;
        final String timeslot_ = timeslot;
        final boolean[] doneFlag = new boolean[1];
//        Thread queryThread = new Thread(new searchThread(emails, course, timeslot));
//        queryThread.start();
        Log.d("ben", "pre db get");
        db.collection("tutors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (task.isSuccessful()) {
                                // go through all documents in the collection...
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ArrayList<String> courses = (ArrayList<String>) document.get("courses");
                                    ArrayList<String> availability = (ArrayList<String>) document.get("availability");
                                    if (courses.contains(course_) && availability.contains(timeslot_)) {
                                        Log.d("ben", "email found, adding email=" + document.getId());
                                        emails.add(document.getId()); // add this doc ID (the email) to the emails list
                                    }
                                }
                                Log.d("ben", "finished looking thru docs");
                            }
                            Log.d("ben", "finished task successful block");
                        }
                        finally {
                            Log.d("ben", "in finally block");
                            doneFlag[0] = true;
                        }
                    }
                });
        Log.d("ben", "post db get");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(!doneFlag[0]){ /* wait */ }

        // get profiles of all matching documents (by email)
        ArrayList<User> matchingTutors = new ArrayList<User>();
        Log.d("ben", "Emails size="+((Integer)emails.size()).toString());
        for(String email : emails)
        {
            Log.d("ben", "adding profile for email="+email);
            matchingTutors.add(getProfile(email, "tutor"));
        }
        return matchingTutors;
    }
}


