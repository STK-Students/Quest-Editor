package stk.students;

import stk.students.commandWindow.DynamicAnswerWindow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 */
public class Client {

    public static String ipAddress = "127.0.0.1";

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        QuestService server = (QuestService) Naming.lookup("rmi://" + ipAddress + ":1099/Quest_Server");

        DynamicAnswerWindow email = new DynamicAnswerWindow("Gib bitte deine Emailadresse ein:");
        email.display();
        System.out.println(email.getUserAnswer());
    }
}
