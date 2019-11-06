// BMa: DEPRECATED

package com.example.tutorsearcher;

public class Availability {
    // Change this to "Day" object later for calendar
    public String day;

    // Change these to "Time" objects later for calendar
    public String starttime;
    public String endtime;

    /**
     * Changes the timeslot for availability on a certain day
     * @param starttime new start time
     * @param endtime new end time
     */
    public void changeTimeslot(String starttime, String endtime){
        this.starttime = starttime;
        this.endtime = endtime;
    }
}
