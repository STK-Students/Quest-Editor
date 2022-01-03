package stk.students.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class User {
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private ArrayList<Role> roleList;

    public User(String email, String username, String password, ArrayList<Role> roleList){
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role){
        this.roleList.add(role);
    }
}
