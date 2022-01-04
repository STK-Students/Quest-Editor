package stk.students.Data;

import lombok.Getter;
import lombok.Setter;
import stk.students.Color;

import java.util.ArrayList;

public class Role {
    //Attribute
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Color color;
    @Getter @Setter
    private ArrayList<User> userList;

    //Konstruktor
    public Role(String name, Color color, ArrayList<User> userList){
        this.name = name;
        this.color = color;
        this.userList = userList;
    }
    public Role(String name, Color color){
        this.name = name;
        this.color = color;
    }
    public void addUser(User user){
        this.userList.add(user);
    }

}
