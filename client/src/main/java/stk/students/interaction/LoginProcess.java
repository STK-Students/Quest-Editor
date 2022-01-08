package stk.students.interaction;

import stk.students.Client;
import stk.students.QuestService;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;
import stk.students.data.User;
import stk.students.utils.Color;
import stk.students.utils.PrintUtils;

import java.rmi.RemoteException;

import static stk.students.utils.PrintUtils.printFromConfig;
import static stk.students.utils.PrintUtils.printUser;
import static stk.students.utils.PrintUtils.printlnFromConfig;

public class LoginProcess {

    private final QuestService server = Client.getInstance().getServer();

    public LoginProcess() throws RemoteException {
        showLoginAndRegisterWindow();

        User currentUser = Client.getInstance().getCurrentUser();
        if (server.userHasRole(currentUser, "Administrator")) {
            new AdminGUI();
        }

        server.disconnectUser(Client.getInstance().getCurrentUser());
    }

    private void showLoginAndRegisterWindow() throws RemoteException {
        User user = null;
        while (user == null) {

            FixedAnswerWindow intro = new FixedAnswerWindow("intro", Color.BLUE);
            String answer = intro.getUserAnswer();
            if ("anmelden".equals(answer)) {
                user = login();
            } else if ("registrieren".equals(answer)) {
                user = register();
            }
        }
        Client.getInstance().setCurrentUser(user);
        printlnFromConfig("login.success", Color.GREEN);

        showLoggedInUsers();
    }

    private User login() throws RemoteException {
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("login.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("login.password");

        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        User user =  server.loginUser(username, password);
        if (user == null) {
            printlnFromConfig("login.error", Color.RED, Color.BLINK);
            return login();
        }
        return user;
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
        PrintUtils.printlnFromConfig("overview");
        for (User user : server.getLoggedInUsers().values()) {
            printUser(user);
        }
    }

}
