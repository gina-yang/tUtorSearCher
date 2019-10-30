import java.util.ArrayList;
public class SearchView {
    private ArrayList<Tutor> searchResult = new ArrayList<Tutor>();
    SearchView(){}
    public ArrayList<Tutor> search(String course, String time) // find all the matches for Tutors with time and course
    {
        ArrayList<Tutor> total = new ArrayList<Tutor>(); // load the data from firebase

        for(int i = 0; i < total.size(); i++){
            if(total.get(i).getCourses().contains(course)){ // make a getClass function for
                if(total.get(i).getAvailability().isAvailable(time)){
                    searchResult.add(total.get(i));
                }
            }
        }
        return searchResult;
    }
}
