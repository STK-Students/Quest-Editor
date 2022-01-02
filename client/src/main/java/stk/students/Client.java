package stk.students;

import stk.students.commandWindow.FixedAnswerWindow;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Hello world!
 */
public class Client {

    public static final String ipAddress = "127.0.0.1";

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //Connect with Server
        //QuestService server = (QuestService) Naming.lookup("rmi://" + ipAddress + ":1099/Quest_Server");

        try {
            new ConfigManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Start GUI Logic
        showLoginAndRegisterWindow();
    }

    private static void showLoginAndRegisterWindow() {
        FixedAnswerWindow email = new FixedAnswerWindow("Möchten Sie sich anmelden oder einen neuen Account registrieren?", List.of("a", "basdasdasdasdadadaddadsa"), ColorUtil.Color.BLUE);
        System.out.println(email.getUserAnswer());
    }
}
