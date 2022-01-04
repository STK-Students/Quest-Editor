package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Application implements QuestService {

    private Map<String, Role> roles;
    private Map<String, User> list;
    private Map<String, User> activeUsers;
    private Database db;

    public Application() {
        try {
            db = new Database(); // initialize database connection
            roles = db.loadRoles(); // load role from database
            list = db.loadUser(); // load user from database
            createDefaultRole(); // creates default role
            activeUsers = new HashMap<>();
            db.loadRelationTable(); // load relation table from database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultRole() {
        Role adminRole = new Role("Administrator", Color.RED);
        boolean temp = false;
        for(String roleItem : roles.keySet()){
            if(roles.get(roleItem).getName().equalsIgnoreCase("Administrator")){
                temp = true;
            }
        }
        if(temp == false){
            try {
                db.saveRole(adminRole);
                roles.put(adminRole.getName(), adminRole);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginUser(String email, String password) {
        for (String key : list.keySet()) {
            User user = list.get(key);
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                activeUsers.put(user.getEmail(), user);
                return true;
            }
        }
        return false;
    }

    public void disconnectUser(User user) {
        activeUsers.remove(user.getEmail());
    }



    public boolean registerUser(String email, String username, String password) {
        User user = new User(email, username, password);
        try {
            db = new Database();
            db.saveUser(user);
            list.put(user.getEmail(), user);
            if (list.size() == 1) {
                db.assignRoleToUser(user, roles.get("Administrator"));
            }
            loginUser(user.getEmail(), user.getPassword());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createRole(String name, Color color) {
        Role role = new Role(name, color);
        try {
            db = new Database();
            db.saveRole(role);
            roles.put(role.getName(), role);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userAlreadyExists(User user) {
        for (String key : list.keySet()) {
            User userItem = list.get(key);
            if (userItem.getEmail().equalsIgnoreCase(user.getEmail()) && userItem.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean roleAlreadyExists(Role role) {
        for (String key : roles.keySet()) {
            Role roleItem = roles.get(key);
            if (roleItem.getName().equals(role.getName())) {
                return true;
            }
        }
        return false;
    }
    public void assignUserToRole(User user, Role role){
        try {
            db.assignRoleToUser(user, role);
            user.addRole(role);
            role.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userHasRole(User user, Role role) {
        for (Role roleItem : user.getRoleList()) {
            if (roleItem.getName().equalsIgnoreCase(role.getName())) {
                return true;
            }
        }
        return false;
    }
}
