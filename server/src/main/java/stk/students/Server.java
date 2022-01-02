package stk.students;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

/**
 * Hello world!
 */
public class Server {
    public static String ipAdress ="127.0.0.1";
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, java.net.MalformedURLException {
        final QuestService questService = new QuestServiceImpl();
        QuestService stub = (QuestService) UnicastRemoteObject.exportObject(questService, 1099);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("QuestService", stub);

        Naming.bind("rmi://"+ ipAdress +":1099/Quest_Server", questService);

        }
    }

