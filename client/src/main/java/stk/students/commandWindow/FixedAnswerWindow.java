package stk.students.commandWindow;

import stk.students.Color;
import stk.students.ColorUtil;
import stk.students.Data.Prefix;

import java.util.List;
import java.util.Locale;

import static stk.students.Client.config;

public class FixedAnswerWindow extends BaseWindow {

    private List<String> validAnswers;
    private List<String> lowercaseAnswers;
    private String errorMessage;

    public FixedAnswerWindow(final String configKey, Color... colors) {
        super(configKey, colors);
    }

    @Override
    public void parseInstruction(final String configKey) {
        super.parseInstruction(configKey);
        validAnswers = (List<String>) instruction.get("answers");
        lowercaseAnswers = validAnswers.stream().map((entry) -> entry.toLowerCase(Locale.ROOT)).toList();
        errorMessage = (String) instruction.get("error");
    }

    @Override
    public void printMessage(Color... colors) {
        super.printMessage(colors);
        System.out.println( Prefix.OUTPUT_PREFIX + config.getMessage("possible_answer"));
        System.out.println( Prefix.OUTPUT_PREFIX + ColorUtil.colorize(validAnswers.toString(), Color.UNDERLINE, Color.GREEN));
    }

    @Override
    public void readUserAnswer() {
        super.readUserAnswer();
        if (!lowercaseAnswers.contains(getUserAnswer())) {
            System.out.println(ColorUtil.colorize(errorMessage, Color.RED, Color.BLINK));
            readUserAnswer();
        }
    }

}
