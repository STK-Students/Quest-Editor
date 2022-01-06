package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.Data.Prefix;
import stk.students.Data.Role;
import stk.students.Data.User;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

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
        System.out.println(Prefix.OUTPUT_PREFIX + "Login/Registrierung erfolgreich!");

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
        DynamicAnswerWindow emailWindow = new DynamicAnswerWindow("onboardingemail");
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("onboardingusername");
        DynamicAnswerWindow passwordWindow = new DynamicAnswerWindow("onboardingpassword");


        String email = emailWindow.getUserAnswer();
        String username = usernameWindow.getUserAnswer();
        String password = passwordWindow.getUserAnswer();
        return remote.registerUser(username, email, password);
    }

    private static boolean showActiveUser() throws RemoteException {
        Map<String, User> activeUser = remote.getActiveUsers();
        System.out.println(Prefix.OUTPUT_PREFIX + "Aktive Benutzer");
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
        while(true){
            FixedAnswerWindow roleAdministrationWindow = new FixedAnswerWindow("adminOptions", Color.BLUE);
            if ("Rolle zuweisen".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                addOrRemoveAssignmentRole();
            } else if ("Rolle erstellen".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                createRole();
            } else if("Rollen anzeigen".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                showRolesFromUser();
            } else if ("Programm beenden".equalsIgnoreCase(roleAdministrationWindow.getUserAnswer())) {
                remote.disconnectUser(currentUser);
                break;
            }else{
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
    }
    private static boolean createRole() throws RemoteException {
        DynamicAnswerWindow roleNameWindow = new DynamicAnswerWindow("createrolename");
        DynamicAnswerWindow roleColorWindow = new DynamicAnswerWindow("createrolecolor");
        String roleName = roleNameWindow.getUserAnswer();
        String roleColor = roleColorWindow.getUserAnswer();
        boolean success = remote.createRole(roleName, Color.valueOf(roleColor.toUpperCase(Locale.ROOT)));
        if (success) {
            System.out.println("Operation erfolgreich");
            return true;
        } else {
            System.out.println(config.getMessage("createrole.error"));
        }
        return false;
    }

    private static boolean addOrRemoveAssignmentRole() throws RemoteException {
        Map<String, User> users = remote.getUsers();
        Map<String, Role> roles = remote.getRoles();
        FixedAnswerWindow optionsWindow = new FixedAnswerWindow("assignroleoptions", Color.BLUE);
        for (User user : users.values()) {
            printUser(user);
        }
        DynamicAnswerWindow userWindow = new DynamicAnswerWindow("assignroleuserhierachy");
        for (Role role : roles.values()) {
            printRole(role);
        }
        DynamicAnswerWindow roleWindow = new DynamicAnswerWindow("assignroleuserroleoverview");
        String username = userWindow.getUserAnswer();
        String rolename = roleWindow.getUserAnswer();
        String option = optionsWindow.getUserAnswer();
        System.out.println(username);
        System.out.println(rolename);
        boolean success = false;
        while (true) {
            if (option.equalsIgnoreCase("Hinzufügen")) {
                success = remote.assignUserToRole(username, rolename);
                System.out.println(success);
                break;
            } else if (option.equalsIgnoreCase("Entfernen")) {
                success = remote.removeUserFromRole(username, rolename);
                System.out.println(success);
                break;
            } else if (option.equalsIgnoreCase("Abbrechen")) {
                break;
            } else {
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
        if (success){
            System.out.println("Operation erfolgreich");
        }
        return success;
    }
    public static boolean showRolesFromUser() throws RemoteException{
        ArrayList<Role> rolesFromUser;
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("showrolesfromuser");
        String username = usernameWindow.getUserAnswer();
        rolesFromUser = remote.getRolesFromUser(username);
        for(Role role : rolesFromUser){
            printRole(role);
        }
        return true;
    }

}
