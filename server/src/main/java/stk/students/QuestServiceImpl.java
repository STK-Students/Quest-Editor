package stk.students;

import stk.students.data.Role;
import stk.students.data.User;
import stk.students.utils.Color;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestServiceImpl implements QuestService {

    private Map<String, Role> roles;
    private Map<String, User> users;
    private Map<String, User> loggedInUsers;
    private Database db;

    public QuestServiceImpl() {
        try {
            db = new Database();
            roles = db.loadRoles();
            users = db.loadUser();
            createDefaultRole();
            loggedInUsers = new HashMap<>();
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

    public User loginUser(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                loggedInUsers.put(user.getUsername(), user);
                return user;
            }
        }
        return null;
    }

    public void disconnectUser(User user) {
        loggedInUsers.remove(user.getUsername());
    }

    public User registerUser(String username, String email, String password) {
        if (userAlreadyExists(username)) {
            return null;
        }
        User user = new User(email, username, password);
        try {
            db = new Database();
            db.saveUser(user);
            users.put(user.getUsername(), user);
            if (users.size() == 1) {
                db.assignRoleToUser(user, roles.get("Administrator"));
            }
            return loginUser(user.getEmail(), user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean roleAlreadyExists(String rolename) {
        for (Role role : roles.values()) {
            if (role.getName().equals(rolename)) {
                return true;
            }
        }
        return false;
    }

    public boolean assignUserToRole(String username, String rolename) {
        User user = users.get(username);
        Role role = roles.get(rolename);
        try {
            db.assignRoleToUser(users.get(username), roles.get(rolename));
            user.addRole(role);
            role.addUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeUserFromRole(String username, String rolename) {
        User user = users.get(username);
        Role role = roles.get(rolename);
        try {
            db.removeRoleFromUser(user, role);
            user.removeRole(role);
            role.removeUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userHasRole(User user, String roleName) {
        for (Role role : user.getRoleList()) {
            if (role.getName().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }
    public Map<String, User> getUsers(){ return users; }
    public Map<String, Role> getRoles(){ return roles; }
    public Map<String, User> getLoggedInUsers(){ return loggedInUsers; }

    public ArrayList<Role> getRolesFromUser(String username) throws RemoteException {
        User user = users.get(username);
        return new ArrayList<>(user.getRoleList());
    }
}
