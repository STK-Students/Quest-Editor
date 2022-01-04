package stk.students.commandWindow;

import lombok.Getter;
import stk.students.Color;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import static stk.students.Client.config;

public abstract class BaseWindow {

    Map instruction;
    String message;

    @Getter
    String userAnswer;

    public BaseWindow(String configKey, Color... colors) {
        instruction = (Map) config.getConfigData().get(configKey);
        parseInstruction(configKey);
        printMessage(colors);
        startScanning();
    }


    public void parseInstruction(String configKey) {
        message = (String) instruction.get("message");
    }

    public void printMessage(Color... colors) {
        System.out.println(message);
    }

    public void startScanning() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        userAnswer = scanner.nextLine();
    }
}
