package stk.students.commandWindow;

import stk.students.utils.Color;
import stk.students.utils.ColorUtil;

import java.util.List;
import java.util.Locale;

import static stk.students.utils.PrintUtils.print;

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
        if (validAnswers != null) {
            lowercaseAnswers = validAnswers.stream().map(String::toLowerCase).toList();
        }
        errorMessage = (String) instruction.get("error");
    }

    @Override
    public void printMessage(Color... colors) {
        super.printMessage(colors);
        print(config.getMessage("prefix.output") + config.getMessage("possible_answer"));
        print(config.getMessage("prefix.output") + ColorUtil.colorize(validAnswers.toString(), Color.UNDERLINE, Color.GREEN));
    }

    @Override
    public void readUserAnswer() {
        super.readUserAnswer();
        if (!lowercaseAnswers.contains(getUserAnswer())) {
            System.out.println(ColorUtil.colorize(errorMessage, Color.RED, Color.BLINK));
            readUserAnswer();
        }
    }

    /**
     * Adds more valid answers.
     * This is useful for answers that can only be determined during runtime.
     *
     * @param answers a list of answers to add
     */
    public void addValidAnswers(List<String> answers) {
        validAnswers.addAll(answers.stream().map(String::toLowerCase).toList());
    }

}
