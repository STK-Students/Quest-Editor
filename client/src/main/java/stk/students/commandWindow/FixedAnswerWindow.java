package stk.students.commandWindow;

import stk.students.Color;

import java.util.List;

import static stk.students.Client.config;

public class FixedAnswerWindow extends BaseWindow {

    private List<String> validAnswers;

    public FixedAnswerWindow(final String configKey, Color... colors) {
        super(configKey, colors);
    }

    @Override
    public void parseInstruction(final String configKey) {
        super.parseInstruction(configKey);
        validAnswers = (List<String>) instruction.get("answers");
    }

    private boolean isValidAnswer() {
        for (String validAnswer : validAnswers) {
            if (validAnswer.equals(userAnswer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void printMessage() {
        super.printMessage();
        System.out.println(config.getMessage("possible_answer"));
        for (String validAnswer : validAnswers) {
            System.out.println(validAnswer);
        }
    }
}
