import java.util.ArrayList;

public class RequestView {
    ArrayList<Request> requests = new ArrayList<Request>();
    public ArrayList<Request> pullRequests(User user){
        //requests = user.getRequests();
        return user.getRequests(); // all current requests
    }
    public void modifyRequests(Request request, String status){
        if(status.contains("reject")){
            //requests.remove(request);
        }
        else if(status.contains("accept")){
            requests.add(request);
        }
    }
}
