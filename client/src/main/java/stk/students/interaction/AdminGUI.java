package stk.students.interaction;

import stk.students.Client;
import stk.students.utils.Color;
import stk.students.ConfigManager;
import stk.students.Data.Role;
import stk.students.Data.User;
import stk.students.QuestService;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;
import stk.students.utils.PrintUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class AdminGUI {

    private final ConfigManager config = Client.getInstance().getConfig();
    private final QuestService server = Client.getInstance().getServer();

    private void showAdminOptions() throws RemoteException {
        while (true) {
            FixedAnswerWindow roleAdministrationWindow = new FixedAnswerWindow("adminOptions", Color.BLUE);
            if ("rolle zuweisen".equals(roleAdministrationWindow.getUserAnswer())) {
                addOrRemoveAssignmentRole();
            } else if ("rolle erstellen".equals(roleAdministrationWindow.getUserAnswer())) {
                createRole();
            } else if ("rollen anzeigen".equals(roleAdministrationWindow.getUserAnswer())) {
                showRolesFromUser();
            } else if ("programm beenden".equals(roleAdministrationWindow.getUserAnswer())) {
                server.disconnectUser(Client.getInstance().getCurrentUser());
                break;
            } else {
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
    }

    private boolean createRole() throws RemoteException {
        DynamicAnswerWindow roleNameWindow = new DynamicAnswerWindow("createrolename");
        DynamicAnswerWindow roleColorWindow = new DynamicAnswerWindow("createrolecolor");
        String roleName = roleNameWindow.getUserAnswer();
        String roleColor = roleColorWindow.getUserAnswer();
        boolean success = server.createRole(roleName, Color.valueOf(roleColor.toUpperCase(Locale.ROOT)));
        if (success) {
            System.out.println("Operation erfolgreich");
            return true;
        } else {
            System.out.println(config.getMessage("createrole.error"));
        }
        return false;
    }

    private boolean addOrRemoveAssignmentRole() throws RemoteException {
        Map<String, User> users = server.getUsers();
        Map<String, Role> roles = server.getRoles();
        FixedAnswerWindow optionsWindow = new FixedAnswerWindow("assignroleoptions", Color.BLUE);
        for (User user : users.values()) {
            PrintUtils.printUser(user);
        }
        DynamicAnswerWindow userWindow = new DynamicAnswerWindow("assignroleuserhierachy");
        for (Role role : roles.values()) {
            PrintUtils.printRole(role);
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
                success = server.assignUserToRole(username, rolename);
                System.out.println(success);
                break;
            } else if (option.equalsIgnoreCase("Entfernen")) {
                success = server.removeUserFromRole(username, rolename);
                System.out.println(success);
                break;
            } else if (option.equalsIgnoreCase("Abbrechen")) {
                break;
            } else {
                System.out.println(config.getMessage("assignroleoptions.error"));
            }
        }
        if (success) {
            System.out.println("Operation erfolgreich");
        }
        return success;
    }

    public boolean showRolesFromUser() throws RemoteException {
        ArrayList<Role> rolesFromUser;
        DynamicAnswerWindow usernameWindow = new DynamicAnswerWindow("showrolesfromuser");
        String username = usernameWindow.getUserAnswer();
        rolesFromUser = server.getRolesFromUser(username);
        for (Role role : rolesFromUser) {
            PrintUtils.printRole(role);
        }
        return true;
    }

}
