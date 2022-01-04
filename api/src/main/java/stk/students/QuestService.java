package stk.students;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface QuestService extends Remote {
    boolean loginUser(String email, String password) throws RemoteException;
    boolean registerUser(String email, String username, String password) throws RemoteException;
}