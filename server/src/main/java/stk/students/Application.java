package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Application implements QuestService {
    private HashMap<String, Role> lstRole;
    private HashMap<String, User> lstUser;
    private HashMap<String, User> activeUser;
    private Database db;

    public Application(){
        try {
            this.db = new Database(); // initialize database connection
            this.createDefaultRole(); // creates default role
            this.lstRole = db.loadRoles(); // load role from database
            this.lstUser = db.loadUser(); // load user from database
            this.activeUser = new HashMap<>();
            this.db.loadRelationTable(); // load relation table from database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultRole(){
        Role adminRole = new Role("Administrator", Color.RED);
        try {
            this.db.saveRole(adminRole);
            this.lstRole.put(adminRole.getName(), adminRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String email, String password){
        for(String key  : this.lstUser.keySet()){
            User user = lstUser.get(key);
            if(user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)){
                this.activeUser.put(user.getEmail(), user);
                return true;
            }
        }
        return false;
    }
    public void disconnectUser(User user){
        this.activeUser.remove(user.getEmail());
    }

    public boolean registerUser(String email, String username, String password){
        User user = new User(email, username, password);
        try {
            this.db = new Database();
            this.db.saveUser(user);
            this.lstUser.put(user.getEmail(), user);
            if(this.lstUser.size() == 1){
                this.db.assignRoleToUser(user, this.lstRole.get("Administrator"));
            }
            this.loginUser(user.getEmail(), user.getPassword());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createRole(String name, Color color){
        Role role = new Role(name, color);
        try {
            this.db = new Database();
            this.db.saveRole(role);
            this.lstRole.put(role.getName(), role);
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
        for(Role roleItem : user.getRoleList()){
            if(roleItem.getName().equalsIgnoreCase(role.getName())){
                return true;
            }
        }
        return false;
    }
}
