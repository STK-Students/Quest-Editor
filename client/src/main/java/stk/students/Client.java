package stk.students;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.rmi.Naming;

/**
 * Hello world!
 */
public class Client {
 public static String ipAdress="127.0.0.1";
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        QuestService server = (QuestService) Naming.lookup("rmi://"+ ipAdress +":1099/Quest_Server");
        //Registry registry = LocateRegistry.getRegistry(1099);


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
