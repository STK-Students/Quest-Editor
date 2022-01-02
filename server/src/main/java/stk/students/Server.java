package stk.students;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Hello world!
 */
public class Server {

    public static String ipAddress = "127.0.0.1";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, java.net.MalformedURLException {
        QuestService stub = (QuestService) UnicastRemoteObject.exportObject(new QuestServiceImpl(), 1099);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("QuestService", stub);

        Naming.bind("rmi://" + ipAddress + ":1099/Quest_Server", stub);

    }
}

