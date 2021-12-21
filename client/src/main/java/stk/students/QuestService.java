package stk.students;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface QuestService extends Remote {

    String sendMessage(String clientMessage) throws RemoteException;

    List<String> sort(ArrayList<String> unsorted) throws RemoteException;

}
