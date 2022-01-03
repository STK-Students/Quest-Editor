package stk.students.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Role {
    //Attribute
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String color;
    @Getter @Setter
    private ArrayList<User> userList;

    //Konstruktor
    public Role(String name, String color, ArrayList<User> userList){
        this.name = name;
        this.color = color;
        this.userList = userList;
    }
    public Role(String name, String color){
        this.name = name;
        this.color = color;
    }
    public void addUser(User user){
        this.userList.add(user);
    }

}
