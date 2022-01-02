package stk.students.Data;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private Role role;

    public User(String email, String username, String password, Role role){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
