package stk.students.utils;

import stk.students.Client;
import stk.students.ConfigManager;
import stk.students.data.Role;
import stk.students.data.User;

import java.util.List;

public class PrintUtils {

    private static final ConfigManager config = Client.getInstance().getConfig();
    final static String OUT_PREFIX = (String) config.get("prefix.output");

    public static void printUser(User user) {
        print("\n");


        Color color = null;
        List<Role> roleList = user.getRoleList();
        if (roleList.size() > 0) {
            Role role = roleList.get(0);
            color = role.getColor();
        }
        print(OUT_PREFIX + user.getUsername(), color);
        println(OUT_PREFIX + " E-Mail: " + user.getEmail());
    }

    public static void printRole(Role role) {
        print("\n");
        println(OUT_PREFIX + "Name: " + role.getName(), role.getColor());
        println(OUT_PREFIX + "Farbe: " + role.getColor());
    }


    public static void printlnFromConfig(String configKey) {
        printlnFromConfig(configKey, Color.WHITE);
    }

    public static void printlnFromConfig(String configKey, Color... color) {
        println((String) config.get(configKey), color);
    }

    public static void printFromConfig(String configKey, Color... color) {
        print((String) config.get(configKey), color);
    }

    public static void println(String message) {
        println(message, Color.WHITE);
    }

    public static void println(String message, Color... color) {
        System.out.println(ColorUtil.colorize(message, color));
    }

    public static void print(String message, Color... color) {
        System.out.print(ColorUtil.colorize(message, color));
    }
}
