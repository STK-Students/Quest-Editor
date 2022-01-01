package stk.students;

public class ColorUtil {

    public static String colorize(String input, Color... colors) {
        StringBuilder result = new StringBuilder();
        for (Color color : colors) {
            result.append(color.getColorCode());
        }
        return result.toString() + input + Color.RESET.getColorCode();
    }

    public enum Color {
        //This
        RESET("\033[0m"),

        //Normal Colours
        RED("\033[31m"),
        GREEN("\033[32m"),
        YELLOW("\033[33m"),
        BLUE("\033[34m"),
        PURPLE("\033[35m"),
        CYAN("\033[36m"),
        WHITE("\033[97m"),

        //Light Colours
        LIGHT_RED("\033[91m"),
        LIGHT_GREEN("\033[92m"),
        LIGHT_YELLOW("\033[93m"),
        LIGHT_BLUE("\033[94m"),
        LIGHT_PURPLE("\033[95m"),
        LIGHT_CYAN("\033[96m"),

        //Bold
        BOLD("\033[1m"),

        //Underline and no Underline
        UNDERLINE("\033[4m"),
        NO_UNDERLINE("\033[24m"),

        //Default
        DEFAULT("\033[0m");

        public String getColorCode() {
            return colorCode;
        }

        private final String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }
    }
}
