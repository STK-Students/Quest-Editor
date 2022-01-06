package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface QuestService extends Remote {

    User loginUser(String username, String password) throws RemoteException;

    boolean registerUser(String username, String email, String password) throws RemoteException;

    void disconnectUser(User user) throws RemoteException;

    boolean createRole(String name, Color color) throws RemoteException;

    void createDefaultRole() throws RemoteException ;

    boolean userAlreadyExists(String username) throws RemoteException;

    boolean roleAlreadyExists(String rolename) throws RemoteException;

    boolean userHasRole(User user, String roleName) throws RemoteException;

    boolean assignUserToRole(String username, String rolename) throws RemoteException;

    boolean removeUserFromRole(String username, String rolename) throws RemoteException;

    Map<String, User> getUsers() throws RemoteException;

    Map<String, Role> getRoles() throws RemoteException;

    Map<String, User> getActiveUsers() throws RemoteException;

    ArrayList<Role> getRolesFromUser(String username) throws RemoteException;
}