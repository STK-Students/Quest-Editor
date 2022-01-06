package stk.students.utils;

public enum Color {
    //This resets all color formatting
    RESET("0"),

    //Normal Colours
    RED("31"),
    GREEN("32"),
    YELLOW("33"),
    BLUE("34"),
    PURPLE("35"),
    CYAN("36"),
    WHITE("97"),

    //Light Colours
    LIGHT_RED("91"),
    LIGHT_GREEN("92"),
    LIGHT_YELLOW("93"),
    LIGHT_BLUE("94"),
    LIGHT_PURPLE("95"),
    LIGHT_CYAN("96"),

    //Bold
    BOLD("1"),

    //Underline and no Underline
    UNDERLINE("4"),
    NO_UNDERLINE("24"),

    //Crazy shit
    BLINK("5"),

    //Default
    DEFAULT("0");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Builds an ASCII color code
     * @return a valid ASCII color code
     */
    public String getColorCode() {
        return "\033[" + colorCode + "m";
    }

}
