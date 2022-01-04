package stk.students;


import static stk.students.Color.RESET;

public class ColorUtil {

    public static String colorize(String input, Color... colors) {
        StringBuilder result = new StringBuilder();
        for (Color color : colors) {
            result.append(color.getColorCode());
        }
        return result + input + RESET.getColorCode();
    }
}
