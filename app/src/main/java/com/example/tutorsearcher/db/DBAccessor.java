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

    /**
     * Returns the database in Firebase
     * @return Firestore database instance
     */
    public FirebaseFirestore getDB(){
        return this.db;
    }
}


