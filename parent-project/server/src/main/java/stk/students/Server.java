package stk.students;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Hello world!
 */
public class Server {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        final QuestService server = new QuestServiceImpl();
        QuestService stub = (QuestService) UnicastRemoteObject.exportObject(server, 1099);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("QuestService", stub);

    }
}
