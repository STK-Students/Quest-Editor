package stk.students.interaction;

import stk.students.Client;
import stk.students.utils.Color;
import stk.students.ConfigManager;
import stk.students.data.User;
import stk.students.QuestService;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;
import stk.students.utils.ColorUtil;

import java.rmi.RemoteException;

import static stk.students.utils.PrintUtils.print;
import static stk.students.utils.PrintUtils.printFromConfig;
import static stk.students.utils.PrintUtils.printUser;

public class LoginProcess {

    private final ConfigManager config = Client.getInstance().getConfig();
    private final String IN_PREFIX = config.getMessage("prefix.input");
    private final QuestService server = Client.getInstance().getServer();

    public LoginProcess() throws RemoteException {
        showLoginAndRegisterWindow();
    }

    private void showLoginAndRegisterWindow() throws RemoteException {
        User user = null;
        while (user == null) {

            FixedAnswerWindow intro = new FixedAnswerWindow("intro", Color.BLUE);
            if ("anmelden".equals(intro.getUserAnswer())) {
                user = login();
            } else if ("registrieren".equals(intro.getUserAnswer())) {
                user = register();
            } else {
                System.out.println(config.getMessage("intro.error"));
            }
        }
        Client.getInstance().setCurrentUser(user);
        printFromConfig("login.success", Color.GREEN);

        showLoggedInUsers();
        if (server.userHasRole(user, "Administrator")) {
            new AdminGUI();
        }
    }

    private User login() throws RemoteException {
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("login.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("login.password");

        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        return server.loginUser(username, password);
    }

    private User register() throws RemoteException {
        DynamicAnswerWindow emailWindow = new DynamicAnswerWindow("onboarding.email");
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("onboarding.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("onboarding.password");

        String email = emailWindow.getUserAnswer();
        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        return server.registerUser(username, email, password);
    }

    private void showLoggedInUsers() throws RemoteException {
        printFromConfig("overview");
        for (User user : server.getLoggedInUsers().values()) {
            printUser(user);
        }
    }

}
