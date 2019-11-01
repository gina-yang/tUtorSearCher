package com.example.tutorsearcher.db;

import com.example.tutorsearcher.User;

abstract public class getProfileCommandWrapper {
    // since getProfile "returns" a User, our execute function uses that User object
    // note: User will be null if not found
    public abstract void execute(User u);
}
