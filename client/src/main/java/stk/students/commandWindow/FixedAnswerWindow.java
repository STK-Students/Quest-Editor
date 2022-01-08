package stk.students.commandWindow;

import stk.students.Client;
import stk.students.ConfigManager;
import stk.students.utils.Color;
import stk.students.utils.ColorUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static stk.students.utils.PrintUtils.print;
import static stk.students.utils.PrintUtils.printFromConfig;
import static stk.students.utils.PrintUtils.println;
import static stk.students.utils.PrintUtils.printlnFromConfig;

public class FixedAnswerWindow {
    private final ConfigManager config = Client.getInstance().getConfig();

    private final Color[] messageColor;
    private String message;
    private List<String> readableAnswers;
    private List<String> validAnswers;
    private String errorMessage;

    /**
     * Creates a fixed answer window. This means only pre-defined answers are valid.
     * <br><br>
     * The instruction is a config section that must contain keys that determine the behaviour of this window:
     * <br>
     * 'message': This <b>string</b> is shown to the user before prompting an answer.<br>
     * 'error': This <b>string</b> is shown when the user enters an invalid answer.<br>
     * 'answers': All entries of this <b>list</b> are deemed valid answers.
     *
     * <br><br>
     * More valid answers may be added during runtime using {@link FixedAnswerWindow#addValidAnswers(List)}
     * <br><br>
     * Params:
     * configKey – path to the instruction in a resource file
     * colors – determines the color of the message
     * @param configKey path to a config section with a relevant instruction
     * @param colors determines the color of the message
     */
    public FixedAnswerWindow(final String configKey, Color... colors) {
        parseInstruction(configKey);
        this.messageColor = colors;
    }

    /**
     * Adds valid answers.
     * Use this to add answers that can only be determined during runtime.
     *
     * @param answers added answers.
     */
    public void addValidAnswers(List<String> answers) {
        this.readableAnswers = answers;
        this.validAnswers = answers.stream().map(String::toLowerCase).toList();
    }

    /**
     * Shows the defined message and all valid answers to the user.
     * Then prompts them to enter an answer. Only valid answers are accepted, otherwise the prompt is reprinted.
     *
     * @return user answer in lowercase
     */
    public String getUserAnswer() {
        printMessage();
        return readUserAnswer().toLowerCase(Locale.ROOT);
    }

    private void parseInstruction(final String configKey) {
        Map instruction = (Map) config.get(configKey);

        message = (String) instruction.get("message");
        errorMessage = (String) instruction.get("error");

        List<String> answers = (List<String>) instruction.get("answers");
        if (answers != null) {
            addValidAnswers(answers);
        }
    }

    private String readUserAnswer() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        printFromConfig("prefix.input", Color.WHITE);
        String userAnswer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

        if (!validAnswers.contains(userAnswer)) {
            println(errorMessage, Color.RED, Color.BLINK);
            readUserAnswer();
        }
        print("\n");
        return userAnswer;
    }

    private void printMessage() {
        println(config.get("prefix.output") + ColorUtil.colorize(message, messageColor));
        printFromConfig("possible_answer");
        print(" ");
        println(readableAnswers.toString(), Color.UNDERLINE, Color.GREEN);
    }
}
