import java.util.ArrayList;
public class Availability
{
    /*The list stores the time slots the tutor is available in strings formatted with
    day and then time (ex. “Sat 9 am - 10 am” or “Mon 11 am - 12 pm”)*/
    ArrayList<String> availabilityList = new ArrayList<String>();

    //costructor
    Availability(ArrayList<String> list)
    {
        availabilityList = list;
    }

    public void changeAvailability(ArrayList<String> list)
    {
        availabilityList = list;
    }

    /**
     * The method accepts input as a string formatted with day and then time
     * (ex. “Sat 9 am - 10 am” or “Mon 11 am - 12 pm”) and checks against the
     * AvaliabilityList to see if the tutor is available for the given time slot
     */
    public boolean isAvailable(String time)
    {
        //iterate through all the strings in the availability list
        for(int i = 0; i < availabilityList.size(); i++)
        {
            //see if inputted time matches anything
            if(availabilityList.get(i).equals(time))
            {//found a time that is equal to input
                return true;
            }
        }
        return false;
    }

    public void removeTimeslot(String time)
    {
        //search for this timeslot in the list
        for(int i = 0; i < availabilityList.size(); i++)
        {
            if(availabilityList.get(i).equals(time))
            {//we found the time to be removed from the availability list
                availabilityList.remove(i);//remove it
            }
        }
    }
}

