package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Role;
import stk.students.Data.User;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Hello world!
 */
public class Client {

    public static final String ipAddress = "127.0.0.1";
    public static ConfigManager config = new ConfigManager();
    private static QuestService remote;
    @Setter
    @Getter
    private static User currentUser;

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
        boolean successLogin = false;
        while (successLogin == false) {
            FixedAnswerWindow intro = new FixedAnswerWindow("intro", Color.BLUE);
            if ("anmelden".equals(intro.getUserAnswer())) {
                successLogin = login();
            } else if ("registrieren".equals(intro.getUserAnswer())) {
                successLogin = register();
            } else {
                System.out.println(config.getMessage("intro.error"));
            }
        }
        showWelcomeMessage();
        boolean successUserAuth = showActiveUser();
        if (successUserAuth) {
            roleAdministration();
        }
    }

    private static boolean login() throws RemoteException {
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("loginusername");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("loginpassword");

        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        if (remote.userAlreadyExists(username)) {
            User user = remote.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private static boolean register() throws RemoteException {
        DynamicAnswerWindow emailWindow = new DynamicAnswerWindow("onboarding.email");
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("onboarding.username");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("onboarding.password");


        String email = emailWindow.getUserAnswer();
        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        return remote.registerUser(username, email, password);
    }

    private static void showWelcomeMessage() throws RemoteException {
        System.out.println(config.getMessage("onboarding.done.message"));
    }

    private static boolean showActiveUser() throws RemoteException {
        System.out.println(config.getMessage("overview.message"));
        Map<String, User> activeUser = remote.getActiveUsers();
        for (User user : activeUser.values()) {
            printUser(user);
        }
        if (remote.userHasRole(currentUser, "Administrator")) {
            return true;
        } else {
            remote.disconnectUser(currentUser);
            System.exit(0);
        }
        return false;
    }

    public static void printUser(User user) {
        System.out.println("_________________________________");
        System.out.println("Username: " + user.getUsername());
        System.out.println("E-Mail: " + user.getEmail());
        System.out.println("_________________________________");
    }

    public static void printRole(Role role) {
        System.out.println("_________________________________");
        System.out.println("Name: " + role.getName());
        System.out.println("Farbe: " + role.getColor());
        System.out.println("_________________________________");
    }

    private static void roleAdministration() throws RemoteException {
        FixedAnswerWindow roleAdministrationWindow = new FixedAnswerWindow("adminOptions", Color.BLUE);
        while(true){
            if ("Rolle zuweisen".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                addOrRemoveAssignmentRole();
            } else if ("Rolle erstellen".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                createRole();
            } else if ("Programm beenden".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                remote.disconnectUser(currentUser);
            }else{
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
    }
    private static boolean createRole() throws RemoteException {
        DynamicAnswerWindow roleNameWindow = new DynamicAnswerWindow("createrole.name");
        DynamicAnswerWindow roleColorWindow = new DynamicAnswerWindow("createrole.color");
        String roleName = roleNameWindow.getUserAnswer();
        String roleColor = roleColorWindow.getUserAnswer();
        boolean success = remote.createRole(roleName, Color.valueOf(roleColor));
        if (success) {
            return true;
        } else {
            System.out.println(config.getMessage("createrole.error"));
        }
        return false;
    }

    private static boolean addOrRemoveAssignmentRole() throws RemoteException {
        Map<String, User> users = remote.getUsers();
        Map<String, Role> roles = remote.getRoles();
        FixedAnswerWindow optionsRole = new FixedAnswerWindow("assignroleoptions", Color.BLUE);
        DynamicAnswerWindow userWindow = new DynamicAnswerWindow("assignroleuserhierachy");
        for (User user : users.values()) {
            printUser(user);
        }
        DynamicAnswerWindow roleWindow = new DynamicAnswerWindow("assignroleuserroleoverview");
        for (Role role : roles.values()) {
            printRole(role);
        }
        FixedAnswerWindow optionsWindow = new FixedAnswerWindow("assignroleoptions", Color.BLUE);
        String username = userWindow.getUserAnswer();
        String rolename = roleWindow.getUserAnswer();
        String options = optionsWindow.getUserAnswer();
        boolean success = false;
        while (success == false) {
            if (options.equalsIgnoreCase("Hinzufügen")) {
                success = remote.assignUserToRole(username, rolename);
            } else if (options.equalsIgnoreCase("Entfernen")) {
                success = remote.removeUserFromRole(username, rolename);
            } else if (options.equalsIgnoreCase("Abbrechen")) {
                break;
            } else {
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
        return success;
    }

}
