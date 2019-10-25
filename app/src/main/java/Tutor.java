public class Tutor extends User
{
    private double rating = 0;//holds 5 star rating of this tutor
    private int numRatings = 0;//number of ratings this tutor has received
    private Availability availability;

    //getters
    public Availability getAvailability()
    {
        return availability;
    }
    public double getRating()
    {
        return rating;
    }
    public int getNumRatings()
    {
        return numRatings;
    }

    //setters
    public void setRating(double rating_)
    {
        rating = rating_;
    }
    public void setNumRatings(int num)
    {
        numRatings = num;
    }
    public void setAvailability(Availability availability_)
    {
        availability = availability_;
    }
}
