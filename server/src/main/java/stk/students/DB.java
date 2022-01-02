package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Role;
import stk.students.Data.User;
import java.util.ArrayList;

public class DB {
    //Attribute
    @Setter @Getter
    private String dbConnection;
    @Setter @Getter
    private String dbUser;
    @Setter @Getter
    private String dbPassword;

    //Konstruktor
    public DB(){

    }


    //Functions
    public ArrayList<Role> loadRoles(){
        return null;
    }
    public ArrayList<User> loadUser(){
        return null;
    }
    public void saveUser(User user){

    }
    public void saveRole(Role role){

    }
    public void assignRoleToUser(User user, Role role){

    }
}
