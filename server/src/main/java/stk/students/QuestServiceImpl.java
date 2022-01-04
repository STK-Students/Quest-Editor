package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QuestServiceImpl implements QuestService {

    private Map<String, Role> roles;
    private Map<String, User> users;
    private Map<String, User> activeUsers;
    private Database db;

    public QuestServiceImpl() {
        try {
            db = new Database();
            roles = db.loadRoles();
            users = db.loadUser();
            createDefaultRole();
            activeUsers = new HashMap<>();
            db.loadRelationTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to create the default "Administrator" role.
     */
    public void createDefaultRole() {
        Role adminRole = new Role("Administrator", Color.RED);
        boolean temp = false;
        for (String roleItem : roles.keySet()) {
            if (roles.get(roleItem).getName().equalsIgnoreCase("Administrator")) {
                temp = true;
            }
        }
        if (!temp) {
            try {
                db.saveRole(adminRole);
                roles.put(adminRole.getName(), adminRole);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginUser(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                activeUsers.put(user.getUsername(), user);
                return true;
            }
        }
        return false;
    }

    public void disconnectUser(User user) {
        activeUsers.remove(user.getUsername());
    }


    public boolean registerUser(String username, String email, String password) {
        if (userAlreadyExists(username)) {
            return false;
        }
        User user = new User(email, username, password);
        try {
            db = new Database();
            db.saveUser(user);
            users.put(user.getUsername(), user);
            if (users.size() == 1) {
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

    public boolean userAlreadyExists(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean roleAlreadyExists(String roleName) {
        for (Role role : roles.values()) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void assignUserToRole(User user, Role role) {
        try {
            db.assignRoleToUser(user, role);
            user.addRole(role);
            role.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserFromRole(User user, Role role) {
        try {
            db.removeRoleFromUser(user, role);
            user.removeRole(role);
            role.removeUser(user);
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
