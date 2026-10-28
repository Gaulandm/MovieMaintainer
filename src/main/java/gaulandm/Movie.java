package gaulandm;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private final String title;
    private final String room;
    private final Date date;

    private final int max;
    private final List<User> registeredUsers;

    public Movie(String title, String room, Date date, int max) {
        this.title = title;
        this.room = room;
        this.date = date;
        this.max = max;

        registeredUsers = new ArrayList<>();
    }



    public void removeUser(String name) {

        registeredUsers.removeIf((registeredUser) -> registeredUser.getName().equals(name));
    }

    public void addUser(String name) {
        if (isRegistered(name)) {
            //TODO Exception handling
            return;
        }
        registeredUsers.add(new User(name));
    }

    public String getTitle() {
        return title;
    }

    public String getRoom() {
        return room;
    }

    public Date getDate() {
        return date;
    }

    public int getMaxUsers() {
        return max;
    }

    public List<User> getUsers() {
        return registeredUsers;
    }

    public boolean isRegistered(String name) {

        for (User registeredUser : registeredUsers) {
            if (registeredUser.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
