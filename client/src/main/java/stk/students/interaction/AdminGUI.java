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
            FixedAnswerWindow roleAdministrationWindow = new FixedAnswerWindow("adminOptions", Color.CYAN);
            String selectedOption = roleAdministrationWindow.getUserAnswer();
            if ("nutzer verwalten".equals(selectedOption)) {
                manageRoles();
            } else if ("rolle erstellen".equals(selectedOption)) {
                createRole();
            } else if ("rollen anzeigen".equals(selectedOption)) {
                showRolesFromUser();
            } else if ("programm beenden".equals(selectedOption)) {
                server.disconnectUser(Client.getInstance().getCurrentUser());
                break;
            } else {
                System.out.println(config.getMessage("adminOptions.error"));
            }
        }
    }

    private void createRole() throws RemoteException {
        DynamicAnswerWindow roleNameWindow = new DynamicAnswerWindow("role.name");
        String roleName = roleNameWindow.getUserAnswer();

        FixedAnswerWindow roleColorWindow = new FixedAnswerWindow("role.color");
        List<String> colors = Arrays.stream(Color.values()).map(Color::toString).toList();
        roleColorWindow.addValidAnswers(colors);
        String roleColor = roleColorWindow.getUserAnswer();

        boolean success = server.createRole(roleName, Color.valueOf(roleColor.toUpperCase(Locale.ROOT)));
        if (success) {
            PrintUtils.printlnFromConfig("role.createDone.message");
        } else {
            PrintUtils.printlnFromConfig("role.createDone.error");
        }
    }

    private void manageRoles() throws RemoteException {
        Map<String, User> users = server.getUsers();
        Map<String, Role> roles = server.getRoles();
        FixedAnswerWindow optionsWindow = new FixedAnswerWindow("role.options", Color.BLUE);
        String option = optionsWindow.getUserAnswer();

        boolean success = false;

        if (option.equalsIgnoreCase("abbrechen")) {
            return;
        } else if (option.equalsIgnoreCase("entfernen")) {
            String username = showRolesFromUser();

            FixedAnswerWindow roleWindow = new FixedAnswerWindow("role.roleSelect");
            List<String> roleNames = users.get(username).getRoleList().stream().map(Role::getName).toList();
            roleWindow.addValidAnswers(roleNames);
            String roleName = roleWindow.getUserAnswer();

            success = server.removeUserFromRole(username, roleName);
            if (success) {
                PrintUtils.printlnFromConfig("role.actions.removeSuccess");
            }
        } else if (option.equalsIgnoreCase("hinzufügen")){
            FixedAnswerWindow userWindow = new FixedAnswerWindow("role.userSelect");
            List<String> userNames = users.values().stream().map(User::getUsername).toList();
            userWindow.addValidAnswers(userNames);
            String username = userWindow.getUserAnswer();

            FixedAnswerWindow roleWindow = new FixedAnswerWindow("role.roleSelect");
            List<String> roleNames = roles.values().stream().map(Role::getName).toList();
            roleWindow.addValidAnswers(roleNames);
            String roleName = roleWindow.getUserAnswer();

            success = server.assignRole(username, roleName);
            if (success) {
                PrintUtils.printlnFromConfig("role.actions.addSuccess");
            }
        }

        if (!success) {
            PrintUtils.printlnFromConfig("error");
        }
    }

    private String showRolesFromUser() throws RemoteException {
        FixedAnswerWindow usernameWindow = new FixedAnswerWindow("role.show");
        Map<String, User> users = server.getUsers();
        usernameWindow.addValidAnswers(users.keySet().stream().toList());
        String username = usernameWindow.getUserAnswer();

        List<Role> roles = server.getRolesFromUser(username);
        PrintUtils.printFromConfig("role.show.result");
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            print(role.getName(), role.getColor());
            if (i < roles.size() - 1) {
                print(", ");
            }
        }
        if (roles.size() == 0) {
            printFromConfig("role.show.no-roles", Color.RED);
        }
        print("\n");
        return username;
    }

}
