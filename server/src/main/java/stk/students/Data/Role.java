package stk.students.Data;

import lombok.Getter;
import lombok.Setter;
import stk.students.Color;

import java.util.ArrayList;
import java.util.List;

public class Role {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Color color;
    @Getter
    @Setter
    private List<User> userList = new ArrayList<>();

    public Role(String name, Color color, ArrayList<User> userList) {
        this.name = name;
        this.color = color;
        this.userList = userList;
    }

    public Role(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void addUser(User user) {
        userList.add(user);
    }

}
