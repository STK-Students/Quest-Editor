package stk.students;

import stk.students.commandWindow.DynamicAnswerWindow;
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
        FixedAnswerWindow intro = new FixedAnswerWindow("intro", Color.BLUE);

        if ("anmelden".equals(intro.getUserAnswer())) {
            login();

        } else if ("registrieren".equals(intro.getUserAnswer())) {
            register();
        }
    }

    private static void login() throws RemoteException {
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("login.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("login.password");

        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        if (remote.userAlreadyExists(username)) {
            boolean success = remote.loginUser(username, password);

            if (!success);
        }
    }

    private static void register() throws RemoteException {
        DynamicAnswerWindow emailWindow = new DynamicAnswerWindow("onboarding.email");
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("onboarding.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("onboarding.password");
        System.out.println(config.getMessage("onboarding.done.message"));

        String email = emailWindow.getUserAnswer();
        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        boolean success = remote.registerUser(username, email, password);
    }
}
