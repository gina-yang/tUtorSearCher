package com.example.tutorsearcher.db;

import androidx.annotation.NonNull;

import com.example.tutorsearcher.Availability;
import com.example.tutorsearcher.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
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

    // Update tutor profile (Ben)

    // Update tutee profile (Ben)

    // Get profile (Ben)

}


