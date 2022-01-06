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

public abstract class BaseWindow {

    protected final ConfigManager config = Client.getInstance().getConfig();
    protected Map instruction;
    protected String message;
    String userAnswer;

    public BaseWindow(String configKey, Color... colors) {
        instruction = (Map) config.get(configKey);
        parseInstruction(configKey);
        printMessage(colors);
        readUserAnswer();
    }

    public void parseInstruction(String configKey) {
        message = (String) instruction.get("message");
    }

    public void printMessage(Color... colors) {
        print(config.getMessage("prefix.output") + ColorUtil.colorize(message, colors));
    }

    public void readUserAnswer() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print(config.getMessage("prefix.input"));
        userAnswer = scanner.nextLine().trim();
    }

    /**
     * Returns the lowercase userAnswer.
     * @return lowercase userAnswer
     */
    public String getUserAnswer() {
        return userAnswer.toLowerCase(Locale.ROOT);
    }
}
