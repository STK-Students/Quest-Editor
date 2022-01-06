package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {
    //Attribute
    @Setter
    @Getter
    private String dbUrl = "jdbc:postgresql://45.89.127.86:5432/appdev_db";
    @Setter
    @Getter
    private String dbUser = "appdev_user";
    @Setter
    @Getter
    private String dbPassword = "appdev_user";
    @Setter
    @Getter
    private Connection dbConnection;
    @Setter
    @Getter
    private Map<String, Role> roles = new HashMap<>();
    @Setter
    @Getter
    private Map<String, User> users = new HashMap<>();

    //Konstruktor
    public Database() throws SQLException {
        dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }


    public Map<String, Role> loadRoles() throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.role");
        while (result.next()) {
            roles.put(result.getString("name"), new Role(result.getString("name"), Color.valueOf(result.getString("color"))));
        }
        return roles;
    }

    public void loadRelationTable() throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.assigned_to");
        while (result.next()) {
            User user = users.get(result.getString("user_username"));
            user.addRole(roles.get(result.getString("role_name")));
            Role role = roles.get(result.getString("role_name"));
            role.addUser(users.get(result.getString("user_username")));
        }
    }

    public Map<String, User> loadUser() throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.user");
        while (result.next()) {
            User user = new User(result.getString("username"), result.getString("email"),  result.getString("password"));
            users.put(result.getString("username"), user);
        }
        return users;
    }

    public void saveUser(User user) throws SQLException {
        String query = "Insert into public.user Values(?,?,?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.execute();
        if (user.getRoleList().size() != 0) {
            for (Role role : user.getRoleList()) {
                assignRoleToUser(user, role);
            }
        }
    }

    public void saveRole(Role role) throws SQLException {
        String query = "Insert into public.role Values(?,?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setString(2, role.getColor().toString());
        preparedStatement.execute();
    }

    public void assignRoleToUser(User user, Role role) throws SQLException {
        String query = "Insert into public.assigned_to Values(?,?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.execute();
    }
    public void removeRoleFromUser(User user, Role role) throws SQLException {
        String query = "Delete From public.assigned_to Where user_email=? and role_name=?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, role.getName());
    }
    public void closeConnection() throws SQLException {
        dbConnection.close();
    }
}
