package stk.students;

import stk.students.data.Role;
import stk.students.data.User;
import stk.students.utils.Color;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface QuestService extends Remote {

    User loginUser(String username, String password) throws RemoteException;

    User registerUser(String username, String email, String password) throws RemoteException;

    void disconnectUser(User user) throws RemoteException;

    boolean createRole(String name, Color color) throws RemoteException;

    void createDefaultRole() throws RemoteException ;

    boolean userAlreadyExists(String username) throws RemoteException;

    boolean roleAlreadyExists(String rolename) throws RemoteException;

    boolean userHasRole(User user, String roleName) throws RemoteException;

    boolean assignRole(String username, String rolename) throws RemoteException;

    boolean removeUserFromRole(String username, String rolename) throws RemoteException;

    Map<String, User> getUsers() throws RemoteException;

    Map<String, Role> getRoles() throws RemoteException;

    Map<String, User> getLoggedInUsers() throws RemoteException;

    ArrayList<Role> getRolesFromUser(String username) throws RemoteException;
}