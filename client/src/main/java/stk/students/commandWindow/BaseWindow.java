package stk.students.commandWindow;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static stk.students.ColorUtil.Color;
import static stk.students.ColorUtil.colorize;

public abstract class BaseWindow {

    @Getter
    String userAnswer;

    public BaseWindow(final String message, Color... colors) {
        System.out.println(colorize(message, colors));
        startScanning();
    }

    private void startScanning() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        userAnswer = scanner.nextLine();
    }
}
