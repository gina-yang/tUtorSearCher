import java.util.ArrayList;
public abstract class User
{
    private String name = "";
    private int age = 1;
    private String gender = "other";//male, female, other
    private String profilePic = "";//url to the profile pic
    private String email = "";//should be a usc email
    private ArrayList<Request> requests = new ArrayList<Request>();
    private Availability availabilityList; // = new ArrayList<Availability>();// *** added availability
    private ArrayList<String> courses = new ArrayList<String>();// *** adding courses

    //getters
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    public String getGender()
    {
        return gender;
    }
    public String profilePic()
    {
        return profilePic;
    }
    public String getEmail()
    {
        return email;
    }
    public ArrayList<Request> getRequests()
    {
        return requests;
    }
    public Availability getAvailability()
    {
        return availabilityList;
    }
    public ArrayList<String> getCourses()
    {
        return courses;
    }

    //setters
    public void setProfilePic(String newPic)
    {
        profilePic = newPic;
    }
    public void setName(String newName){ name = newName; } // *** added setter for name
    public void setAvailability(Availability newAvailabilityList){ availabilityList = newAvailabilityList; }
     // *** added setter for avail
    public void addAvailabiltiy(String newAvailability){ availabilityList.addTimeslot(newAvailability); }
    public void setCourses(ArrayList<String> newCourses){ courses = newCourses; } // *** added setter for courses
    public void addCourses(String addCourse){ courses.add(addCourse); }

}
