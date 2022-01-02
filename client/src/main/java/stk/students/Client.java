package stk.students;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class Client {

    public static String ipAddress = "127.0.0.1";

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        QuestService server = (QuestService) Naming.lookup("rmi://" + ipAddress + ":1099/Quest_Server");

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
