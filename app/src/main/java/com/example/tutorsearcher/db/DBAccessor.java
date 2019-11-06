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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAccessor {
    private ArrayList<Request> rlist;

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
     * @param wrapper wrapper for this function
     */
    public void isNewUser(String email, String role, final isNewUserCommandWrapper wrapper){
        DocumentReference docRef = db.collection(role+"s").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("isNew", "DocumentSnapshot data: " + document.getData());
                        wrapper.execute(false);
                    } else {
                        Log.d("isNew", "No such document");
                        wrapper.execute(true);
                    }
                } else {
                    Log.d("isNew", "get failed with ", task.getException());
                    wrapper.execute(true);
                }
            }
        });
    }

    /**
     * Checks if user with given email, password, and role exists in the database
     * @param email email
     * @param password password
     * @param r user role ("Tutor" or "Tutee")
     */
    public void validateUser(final String email, final String password, String r, final validateUserCommandWrapper wrapper){
        final String role = r.toLowerCase();  // role is passed as "Tutor" or "Tutee" which doesn't match db
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
                            boolean lg = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("userexists", document.getId() + " => " + document.getData());
                                if( document.get("password").equals(password) ) { // Found a user with matching email
                                    Log.d("passmatch", document.getId() + " => " + document.getData());
                                    lg = true;
                                    break;
                                }
                            }
                            wrapper.doValidate(lg);
                        } else {
                            Log.d("loginfailure", "Error getting documents: ", task.getException());
                            wrapper.doValidate(false);
                        }
                    }
                });
    }

    /**
     * Adds a new user to the database
     * @param email user email
     * @param password user password
     * @param role user role (tutee or tutor)
     */
    public void addNewUser(String email, String password, String role){
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("name", "Your Name");
        newUser.put("email", email);
        newUser.put("gender", "NA");
        newUser.put("password", password);
        newUser.put("age", -1);
        newUser.put("pic", "example.com");

        if (role.toLowerCase().equals("tutor")) {
            newUser.put("numratings", 0);
            newUser.put("rating", -1.5); // Needs to be a decimal value to prevent casting issues
            newUser.put("courses", Arrays.asList("Add class names delimited by newlines", "ex. AMST100"));
            newUser.put("availability", Arrays.asList("Add availability delimited by newlines", "ex. Mon 14 (Monday 2pm-3pm)"));
        }


        db.collection(role.toLowerCase()+"s").document(email)
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
     * @param r Request object
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
     * Gets all requests associated with a user and executes wrapper function
     * @param email user's email
     * @param role user's role (tutor or tutee)
     * @param wrapper a getRequestsCommandWrapper class containing instructions to execute on load
     */
    public void getAllRequests(String email, String role, final getRequestsCommandWrapper wrapper){
        rlist = new ArrayList<Request>(); // ? A way to make it not final?
        CollectionReference reqRef = db.collection("requests");
        reqRef.whereEqualTo(role+"Email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Request r = new Request(String.valueOf(document.get("tuteeName")), String.valueOf(document.get("tutorName")),
                                        String.valueOf(document.get("tuteeEmail")),String.valueOf(document.get("tutorEmail")),String.valueOf(document.get("status")),
                                        String.valueOf(document.get("course")), String.valueOf(document.get("time")));
                                rlist.add(r);
                            }
                            wrapper.execute(rlist);
                        } else {
                            Log.d("failure", "Error getting documents: ");
                        }
                    }
                });
    }

    /**
     * Update request
     * @param r request to be updated
     */
    public void updateRequest(final Request r)
    {
        // create new map and add new request status
        final Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("status", r.status);

        // connect to DB and overwrite this request
        db.collection("requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // go through all documents in the collection...
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String tuteeEmail = (String) document.get("tuteeEmail");
                                String tutorEmail = (String) document.get("tutorEmail");
                                String course = (String) document.get("course");
                                String time = (String) document.get("time");
                                // if this document has the correct tutor, tutee, course, and timeslot...
                                if(tuteeEmail.equals(r.tuteeEmail) &&
                                    tutorEmail.equals(r.tutorEmail) &&
                                    course.equals(r.course) &&
                                    time.equals(r.time) )
                                {
                                    //this request matches the search preferences
                                    db.collection("requests").document(document.getId())
                                            .update(requestMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("ben", "updateRequest() executed successfully");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("ben", "Error in updateRequest()");
                                                }
                                            });
                                }
                            }
                        }
                    }
                });
    }

    // Update user profile [works for both tutor and tutee using polymorphism] (Ben)
    // Takes in a User and updates all fields in Firebase
    /**
     * Update user profile [works for both tutor and tutee using polymorphism]
     * @param u user to be updated
     */
    public void updateProfile(final User u)
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
                        Log.d("ben", "updateProfile() executed successfully on "+u.getEmail());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ben", "Error in updateProfile()");
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
                        if(role.equals("tutor"))
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
                        if(role.equals("tutor"))
                        {
                            long numRatings = (Long)document.get("numratings");
                            double rating = (double)document.get("rating");
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
                        Log.d("ben", "getProfile(): document not found: "+email);
                        wrapper.execute(null); // if we get here, we didn't find a matching profile
                    }
                } else {
                    Log.d("ben", "getProfile() task unsuccessful");
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
                            Log.d("course",course);
                            Log.d("timeslot",timeslot);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList<String> courses = (ArrayList<String>) document.get("courses");;
                                ArrayList<String> availability = (ArrayList<String>) document.get("availability");
                                // if this document has the correct course and timeslot...
                                Log.d("username 409",document.get("email").toString()+" end");
                                Log.d("courses.contains",String.valueOf(courses.contains(course)));
                                Log.d("availability.contains",String.valueOf(availability.contains(course)));
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


