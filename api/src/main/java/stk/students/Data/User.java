package stk.students.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private ArrayList<Role> roleList = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roleList.add(role);
    }

    public void removeRole(Role role) {
        roleList.removeIf(roleItem -> roleItem.getName().equalsIgnoreCase(role.getName()));
    }
}
