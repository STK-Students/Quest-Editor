package stk.students;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class Client {
 public static String ipAdress;
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        QuestService server = (QuestService) registry.lookup("rmi://"+ ipAdress +":1099/Quest_Server");

        String responseMessage = server.sendMessage("Hallo!");
        System.out.println("[Server]: " + responseMessage);

        ArrayList<String> list = new ArrayList<>();
        list.add("Erster");
        list.add("Zweiter");
        list = (ArrayList<String>) server.sort(list);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
