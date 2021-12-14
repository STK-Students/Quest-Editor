package stk.students;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        QuestService server = (QuestService) registry.lookup("QuestService");
        String responseMessage = server.sendMessage("Client Message");
        System.out.println(responseMessage);
    }
}
