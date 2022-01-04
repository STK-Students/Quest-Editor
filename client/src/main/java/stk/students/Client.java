package stk.students;

import stk.students.commandWindow.FixedAnswerWindow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 */
public class Client {

    public static final String ipAddress = "127.0.0.1";
    public static ConfigManager config = new ConfigManager();

    private static QuestService remote;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        remote = (QuestService) Naming.lookup("rmi://" + ipAddress + ":1099/Quest_Server");

        try {
            new ConfigManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showLoginAndRegisterWindow();
    }

    private static void showLoginAndRegisterWindow() throws RemoteException {
        FixedAnswerWindow email = new FixedAnswerWindow("intro", Color.BLUE);
        System.out.println(email.getUserAnswer());

        remote.loginUser("salty@gmail.com", "2134");
    }
}
