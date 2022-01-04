package stk.students;

import stk.students.Data.Role;
import stk.students.Data.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface QuestService extends Remote {
    boolean loginUser(String email, String password) throws RemoteException;
    boolean registerUser(String email, String username, String password) throws RemoteException;
    void createDefaultRole() throws RemoteException;
    void disconnectUser(User user) throws RemoteException;
    boolean createRole(String name, Color color);
    boolean userAlreadyExists(User user);
    boolean roleAlreadyExists(Role role);
    void assignUserToRole(User user, Role role);
    boolean userHasRole(User user, Role role);
}