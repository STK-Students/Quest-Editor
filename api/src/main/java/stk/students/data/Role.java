package stk.students.data;

import lombok.Getter;
import lombok.Setter;
import stk.students.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Serializable {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Color color;
    @Getter
    @Setter
    private ArrayList<User> userList = new ArrayList<>();

    public Role(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user){
        userList.removeIf(userItem -> userItem.getEmail().equalsIgnoreCase(user.getEmail()));
    }

}
