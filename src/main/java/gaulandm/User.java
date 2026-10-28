package gaulandm;

public class User {
    private String name;

    public User(String _name) {
        name = _name;
    }

    public boolean isOperator() {
        return name.equals("gaulandm");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
