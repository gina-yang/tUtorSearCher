package com.example.tutorsearcher.db;

import java.util.ArrayList;

abstract public class searchCommandWrapper {
    // our search returns an ArrayList of emails as strings
    public abstract void execute(ArrayList<String> arr);
}
