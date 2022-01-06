package stk.students.interaction;

import stk.students.Client;
import stk.students.ConfigManager;
import stk.students.QuestService;
import stk.students.commandWindow.DynamicAnswerWindow;
import stk.students.commandWindow.FixedAnswerWindow;
import stk.students.data.Role;
import stk.students.data.User;
import stk.students.utils.Color;
import stk.students.utils.PrintUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static stk.students.utils.PrintUtils.print;
import static stk.students.utils.PrintUtils.printFromConfig;

public class AdminGUI {

    private final ConfigManager config = Client.getInstance().getConfig();
    private final QuestService server = Client.getInstance().getServer();

    public AdminGUI() throws RemoteException {
        showAdminOptions();
    }

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
        DynamicAnswerWindow roleNameWindow = new DynamicAnswerWindow("role.name");
        FixedAnswerWindow roleColorWindow = new FixedAnswerWindow("role.color");
        List<String> colors = Arrays.stream(Color.values()).map(Color::toString).toList();
        roleColorWindow.addValidAnswers(colors);

        String roleName = roleNameWindow.getUserAnswer();
        String roleColor = roleColorWindow.getUserAnswer();
        boolean success = server.createRole(roleName, Color.valueOf(roleColor.toUpperCase(Locale.ROOT)));
        if (success) {
            printFromConfig("createDone.message");
        } else {
            printFromConfig("createDone.error");
        }
        return success;
    }

    private boolean addOrRemoveAssignmentRole() throws RemoteException {
        Map<String, User> users = server.getUsers();
        Map<String, Role> roles = server.getRoles();
        FixedAnswerWindow optionsWindow = new FixedAnswerWindow("role.options", Color.BLUE);

        FixedAnswerWindow userWindow = new FixedAnswerWindow("role.userSelect");
        List<String> userNames = users.values().stream().map(User::getUsername).toList();
        userWindow.addValidAnswers(userNames);

        FixedAnswerWindow roleWindow = new FixedAnswerWindow("role.roleSelect");
        List<String> roleNames = roles.values().stream().map(Role::getName).toList();
        roleWindow.addValidAnswers(roleNames);

        String option = optionsWindow.getUserAnswer();
        String username = userWindow.getUserAnswer();
        String roleName = roleWindow.getUserAnswer();
        boolean success = false;

        switch (option) {
            case "hinzufügen" -> {
                success = server.assignUserToRole(username, roleName);
                if (success) {
                    printFromConfig("role.actions.addSuccess");
                }
            }
            case "entfernen" -> {
                success = server.removeUserFromRole(username, roleName);
                if (success) {
                    printFromConfig("role.actions.removeSuccess");
                }
            }
            case "abbrechen" -> {
                return true;
            }
        }
        if (!success) {
            printFromConfig("error");
        }
        return success;
    }

    public boolean showRolesFromUser() throws RemoteException {
        ArrayList<Role> rolesFromUser;
        FixedAnswerWindow usernameWindow = new FixedAnswerWindow("role.show");
        String username = usernameWindow.getUserAnswer();
        rolesFromUser = server.getRolesFromUser(username);
        for (Role role : rolesFromUser) {
            PrintUtils.printRole(role);
        }
        return true;
    }

}
