package gaulandm;

public class User {
    private String name;

    public User(String _name) {
        name = _name;
    }

    boolean isOperator() {
        return name.equals("gaulandm");
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

}
