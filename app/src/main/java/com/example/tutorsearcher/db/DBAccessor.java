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

import static android.icu.lang.UCharacter.toLowerCase;
import static com.firebase.ui.auth.AuthUI.TAG;

public class DBAccessor {
    private boolean exists;
    private boolean loggedin;
    private ArrayList<Request> rlist;

    private FirebaseFirestore db;

    /**
     * Constructor method
     * Initializes instance of the Firestore database
     */
    public DBAccessor(){
        db = FirebaseFirestore.getInstance();
    }

//    /**
//     * Checks if user is new by looking in database
//     * @param email email to check
//     * @param role role to check
//     * @return boolean true if user is new
//     */
//    public boolean isNewUser(String email, String role){
//        CollectionReference roleColl = null;
//        if( role.equals("tutor")){
//            roleColl = db.collection("tutors");
//        }
//        else if( role.equals("tutee")){
//            roleColl = db.collection("tutees");
//        }
//        exists = false;
//        Query query = roleColl.whereEqualTo("email", email);
//
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if( document.exists() ){
//                            exists = true;
//                            Log.d("success","User exists!");
//                        }
//                    }
//                } else {
//                    Log.d("failure","User doesn't exist");
//                }
//            }
//        });
//        return exists;
//    }

    /**
     * Checks if user with given email, password, and role exists in the database
     * @param email email
     * @param password password
     * @param role user role (tutor or tutee)
     * @return false if login failed. true if login successful (user exists)
     */
    public boolean validateUser(String email, String password, String role){
        final String pw = password;  // To look up in db
        role = role.toLowerCase();  // role is passed as "Tutor" or "Tutee" which doesn't match db
        Log.d("email", email);
        Log.d("password", password);
        Log.d("role", role);

        db.collection(role+"s")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("loginsuccess", document.getId() + " => " + document.getData());
                                if( document.get("password").equals(pw) ) {
                                    loggedin = true;
                                    break;
                                }
                            }
                        } else {
                            Log.d("loginfailure", "Error getting documents: ", task.getException());
                            loggedin = false;
                        }
                    }
                });
        Log.d("userexists", Boolean.toString(loggedin));
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
        request.put("tutorName", r.tutorName);
        request.put("tuteeName", r.tuteeName);
        request.put("tutorEmail", r.tutorEmail);
        request.put("tuteeEmail", r.tuteeEmail);

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
                                Request r = new Request(String.valueOf(document.get("tuteeName")), String.valueOf(document.get("tutorName")),
                                        String.valueOf(document.get("tuteeEmail")),String.valueOf(document.get("tutorEmail")),String.valueOf(document.get("status")),
                                        String.valueOf(document.get("course")), String.valueOf(document.get("time")));
                                rlist.add(r);
                            }
                        } else {
                            Log.d("failure", "Error getting documents: ");
                        }
                    }
                });
        return rlist;
    }

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
        final ArrayList<User> users = new ArrayList<User>();

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
                                    //this tutor matches the search preferences
                                    User u = new Tutor((String)document.get("email"));
                                    // Add all the generic User info
                                    long age = (Long)document.get("age");
                                    String gender = (String)document.get("gender");
                                    String name = (String)document.get("name");
                                    String profilePic = (String)document.get("pic");
                                    u.setAge(age);
                                    u.setGender(gender);
                                    u.setName(name);
                                    u.setProfilePic(profilePic);
                                    users.add(u); // add this doc ID (the email) to the emails list
                                }
                            }
                        }
                        // finally, execute the searchCommandWrapper
                        wrapper.execute2(users);
                    }
                });
    }
}


