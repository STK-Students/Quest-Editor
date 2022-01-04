package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {
    private HashMap<String, Role> lstRole;
    private HashMap<String, User> lstUser;
    private Database db;

    public Application(){
        try {
            this.db = new Database();
            this.lstRole = db.loadRoles();
            this.lstUser = db.loadUser();
            this.db.loadRelationTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean loginUser(String email, String password){
        for(String key  : this.lstUser.keySet()){
            User user = lstUser.get(key);
            if(user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public boolean registerUser(String email, String username, String password){
        try {
            this.db = new Database();
            this.db.saveUser(new User(email, username, password));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createRole(String name, String color){
        try {
            this.db = new Database();
            this.db.saveRole(new Role(name, color));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userAlreadyExists(User user){
        for(String key : this.lstUser.keySet()){
            User userItem = this.lstUser.get(key);
            if(userItem.getEmail().equalsIgnoreCase(user.getEmail()) && userItem.getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }
    public boolean roleAlreadyExists(Role role){
        for(String key : this.lstRole.keySet()){
            Role roleItem = this.lstRole.get(key);
            if(roleItem.getName().equals(role.getName())){
                return true;
            }
        }
        return false;
    }
    public boolean userHasRole(User user, Role role){
        return false;
    }
}
