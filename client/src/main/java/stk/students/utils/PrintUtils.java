package stk.students.utils;

import stk.students.Client;
import stk.students.ConfigManager;
import stk.students.data.Role;
import stk.students.data.User;

public class PrintUtils {

    private static final ConfigManager config = Client.getInstance().getConfig();
    final static String OUT_PREFIX = config.getMessage("prefix.output");

    public static void printUser(User user) {
        print(OUT_PREFIX +"_________________________________");
        print(OUT_PREFIX + "Username: " + user.getUsername(), Color.BLUE);
        print(OUT_PREFIX + "E-Mail: " + user.getEmail());
    }

    public static void printRole(Role role) {
        print(OUT_PREFIX + "_________________________________");
        print(OUT_PREFIX + "Name: " + role.getName(), role.getColor());
        print(OUT_PREFIX + "Farbe: " + role.getColor());
    }


    public static void printFromConfig(String configKey) {
        print(config.getMessage(configKey));
    }

    public static void printFromConfig(String configKey, Color... color) {
        print(config.getMessage(configKey), color);
    }

    public static void print(String message) {
        System.out.println(ColorUtil.colorize(message, Color.WHITE));
    }

    public static void print(String message, Color... color) {
        System.out.println(ColorUtil.colorize(message, color));
    }
}
