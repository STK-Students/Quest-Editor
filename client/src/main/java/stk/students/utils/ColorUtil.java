package stk.students.utils;


import stk.students.utils.Color;

import static stk.students.utils.Color.RESET;

public class ColorUtil {

    public static String colorize(String input, Color... colors) {
        StringBuilder result = new StringBuilder();
        for (Color color : colors) {
            result.append(color.getColorCode());
        }
        return result + input + RESET.getColorCode();
    }
}
