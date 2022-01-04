package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, Role> lstRoles;
    @Setter
    @Getter
    private HashMap<String, User> lstUser;

    //Konstruktor
    public Database() throws SQLException {
        this.dbConnection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }


    //Functions
    public HashMap<String, Role> loadRoles() throws SQLException {
        this.lstRoles = new HashMap<String, Role>();
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.role");
        while (result.next()) {
            this.lstRoles.put(result.getString("name"), new Role(result.getString("name"), Color.valueOf(result.getString("color")), null));
        }
        return lstRoles;
    }

    public void loadRelationTable() throws SQLException {
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.assigned_to");
        while (result.next()) {
            this.lstUser.get(result.getString("user_email")).addRole(lstRoles.get(result.getString("role_name")));
            this.lstRoles.get(result.getString("role_name")).addUser(lstUser.get(result.getString("user_email")));
        }
    }

    public HashMap<String, User> loadUser() throws SQLException {
        this.lstUser = new HashMap<String, User>();
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.user");
        while (result.next()) {
            User user = new User(result.getString("email"), result.getString("username"), result.getString("password"));
            this.lstUser.put(result.getString("email"), user);
        }
        return lstUser;
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
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, role.getName());
        preparedStatement.execute();
    }

    public void closeConnection() throws SQLException {
        this.dbConnection.close();
    }
}
