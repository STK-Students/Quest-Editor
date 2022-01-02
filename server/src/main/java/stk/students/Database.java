package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Role;
import stk.students.Data.User;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    //Attribute
    @Setter @Getter
    private String dbUrl = "jdbc:postgresql://45.89.127.86:5432/appdev_db";
    @Setter @Getter
    private String dbUser = "appdev_user";
    @Setter @Getter
    private String dbPassword = "appdev_user";
    @Setter @Getter
    private Connection dbConnection;
    //Konstruktor
    public Database() throws SQLException {
        this.dbConnection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }


    //Functions
    public ArrayList<Role> loadRoles() throws SQLException {
        ArrayList<Role> lstRoles = new ArrayList<Role>();
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.role");
        while(result.next()){
            lstRoles.add(new Role(result.getString("name"), result.getString("color")));
        }
        return lstRoles;
    }
    public ArrayList<User> loadUser() throws SQLException {
        ArrayList<User> lstUser = new ArrayList<User>();
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery("Select * From public.user");
        while(result.next()){
            lstUser.add(new User(result.getString("email"), result.getString("username"), result.getString("password")));
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
        if(user.getRole() != null){
            assignRoleToUser(user, user.getRole());
        }
    }
    public void saveRole(Role role) throws SQLException {
        String query = "Insert into public.role Values(?,?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setString(2, role.getColor());
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
