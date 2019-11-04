package com.example.tutorsearcher.db;

import com.example.tutorsearcher.Request;
import com.example.tutorsearcher.User;

import java.util.ArrayList;

public abstract class getRequestsCommandWrapper {
    public abstract void execute(ArrayList<Request> requests);
}
