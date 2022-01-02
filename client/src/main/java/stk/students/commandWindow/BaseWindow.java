package stk.students.commandWindow;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

public abstract class BaseWindow {

    @Setter
    String message;
    @Getter
    String userAnswer;

    public BaseWindow(final String message) {
        this.message = message;
    }

    private void startScanning() {
        Scanner scanner = new Scanner(System.in);
        userAnswer = scanner.nextLine();
    }

    public void display() {
        System.out.println(message);
        startScanning();
    }
}
