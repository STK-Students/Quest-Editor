package stk.students.commandWindow;

import stk.students.Client;
import stk.students.ConfigManager;
import stk.students.utils.Color;
import stk.students.utils.ColorUtil;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static stk.students.utils.PrintUtils.print;
import static stk.students.utils.PrintUtils.printFromConfig;
import static stk.students.utils.PrintUtils.println;

public class DynamicAnswerWindow {

    private final ConfigManager config = Client.getInstance().getConfig();

    private final String message;
    private String userAnswer;

    /**
     * Creates a dynamic answer window.
     * This means any user answer is valid.
     * <br><br>
     * The instruction is a config section that contains a single key named 'message'.
     * The value of 'message' is show to the user before prompting an answer.
     *
     * @param configKey path to the instruction in a resource file
     * @param colors    determines the color of the message
     */
    public DynamicAnswerWindow(String configKey, Color... colors) {
        Map instruction = (Map) config.get(configKey);
        message = (String) instruction.get("message");
        printMessage(colors);
        readUserAnswer();
        print("\n");
    }

    /**
     * Returns the user answer in lowercase.
     *
     * @return user answer in lowercase
     */
    public String getUserAnswer() {
        return userAnswer.toLowerCase(Locale.ROOT);
    }

    private void printMessage(Color... colors) {
        println(config.get("prefix.output") + ColorUtil.colorize(message, colors));
    }

    private void readUserAnswer() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        printFromConfig("prefix.input", Color.WHITE);
        userAnswer = scanner.nextLine().trim();
    }
}
