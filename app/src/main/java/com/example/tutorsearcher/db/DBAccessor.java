package com.example.tutorsearcher.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBAccessor {
    // For authentication later?
    private String db_username;
    private String db_password;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    /**
     * Constructor method
     * Initializes instance of the Firestore database
     */
    public DBAccessor(){
        db = FirebaseFirestore.getInstance();
    }

    // Check if new user

    // Add new user

    // Add availability (tutor)

    // Add request (tutee)

    // Get requests (both)

    // Update tutor profile

    // Update tutee profile

    // Get profile

}


