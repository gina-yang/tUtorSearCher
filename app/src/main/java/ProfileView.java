public class ProfileView {
    private User user;
    ProfileView(User newUser){
        user = newUser;
    }
    public void updateName(String newName){ // added a setter for user name and class
        user.setName(newName);
    }
    public void updateProfilePic(String newPic){
        user.setProfilePic(newPic);
    }
    public void updateAvailability(String newAvailability){ // adding new availbility ; remove ?
        // update if empty
        if(!user.getAvailability().isAvailable((newAvailability))) {
            user.addAvailabiltiy(newAvailability);
        }
    }
    public void updateClass(String newClass){
        if(!user.getCourses().contains(newClass)) user.addCourses(newClass);
    }
}
