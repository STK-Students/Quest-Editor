package stk.students;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuestService extends Remote {

    String sendMessage(String clientMessage) throws RemoteException;
}
