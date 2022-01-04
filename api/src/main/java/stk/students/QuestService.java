package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuestService extends Remote {

    boolean loginUser(String email, String password) throws RemoteException;

    boolean registerUser(String email, String username, String password) throws RemoteException;

    void disconnectUser(User user) throws RemoteException;

    boolean createRole(String name, Color color);

    void createDefaultRole() throws RemoteException;

    boolean userAlreadyExists(String username);

    boolean userHasRole(User user, Role role);

    void assignUserToRole(User user, Role role);

    boolean roleAlreadyExists(String roleName);
}