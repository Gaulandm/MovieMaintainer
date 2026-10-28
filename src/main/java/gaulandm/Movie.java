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

    void removeUser(User us) {
        registeredUsers.remove(us);
    }

    void addUser(User us) {
        registeredUsers.add(us);
    }

    String getTitle() {
        return title;
    }

    String getRoom() {
        return room;
    }

    Date getDate() {
        return date;
    }

    int getMaxUsers() {
        return max;
    }

    List<User> getUsers() {
        return registeredUsers;
    }


}
