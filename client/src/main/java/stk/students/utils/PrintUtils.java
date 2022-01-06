package stk.students.utils;

import stk.students.Client;
import stk.students.Data.Role;
import stk.students.Data.User;

import java.io.ObjectInputFilter;

public class PrintUtils {

    public static void printUser(User user) {
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "_________________________________", Color.WHITE);
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "Username: " + user.getUsername(), Color.WHITE);
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "E-Mail:" + user.getEmail(), Color.WHITE);
    }

    public static void printRole(Role role) {
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "_________________________________", Color.WHITE);
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "Name: " + role.getName(), Color.WHITE);
        print(Client.getInstance().getConfig().getMessage("prefix.output") + "Farbe: " + role.getColor(), Color.WHITE);
    }

    public static void print(String message) {
        System.out.println(ColorUtil.colorize(message, Color.WHITE));
    }
    public static void print(String message, Color... color) {
        System.out.println(ColorUtil.colorize(message, color));
    }
}
