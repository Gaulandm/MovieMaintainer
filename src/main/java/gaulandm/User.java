package gaulandm;

import java.io.Serializable;

public class User implements Serializable {
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
