package stk.students.commandWindow;

import lombok.Getter;
import stk.students.Color;
import stk.students.ColorUtil;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static stk.students.Client.config;

public abstract class BaseWindow {

    Map instruction;
    String message;

    private String userAnswer;

    public BaseWindow(String configKey, Color... colors) {
        instruction = (Map) config.getConfigData().get(configKey);
        parseInstruction(configKey);
        printMessage(colors);
        readUserAnswer();
    }

    public void parseInstruction(String configKey) {
        message = (String) instruction.get("message");
    }

    public void printMessage(Color... colors) {
        System.out.println(ColorUtil.colorize(message, colors));
    }

    public void readUserAnswer() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        userAnswer = scanner.nextLine();
    }

    public String getUserAnswer() {
        return userAnswer.toLowerCase(Locale.ROOT);
    }
}
