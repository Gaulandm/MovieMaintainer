package gaulandm;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private String room;
    private Date date;

    private int max;
    private final List<User> registeredUsers;

    public Movie() {
        this("", "", new Date(), 0);
    }

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

    public void removeUser(int index) {
        registeredUsers.remove(index);
    }

    public void addUser(String name) {
        if (isRegistered(name)) {
            //TODO Exception handling
            return;
        }

        if (registeredUsers.size() >= max) {
            return;
        }
        registeredUsers.add(new User(name));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setDate(Date date) {
        this.date = date;
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

        if (registeredUsers.isEmpty()) {
            return false;
        }

        for (User registeredUser : registeredUsers) {
            if (registeredUser.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
