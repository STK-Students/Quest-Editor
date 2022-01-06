package stk.students.utils;

import stk.students.Data.Role;
import stk.students.Data.User;

public class PrintUtils {

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

    public static void print(String message) {
        System.out.println(ColorUtil.colorize(message, Color.WHITE));
    }
    public static void print(String message, Color... color) {
        System.out.println(ColorUtil.colorize(message, color));
    }
}
