package stk.students.commandWindow;

import stk.students.ColorUtil;

import java.util.Collections;
import java.util.List;

public class FixedAnswerWindow extends BaseWindow {

   private final List<String> validAnswers;

    public FixedAnswerWindow(final String message, List<String> validAnswers,  ColorUtil.Color... colors) {
        super(message, colors);
        this.validAnswers = Collections.unmodifiableList(validAnswers);
    }

    private boolean isValidAnswer() {
        for (String validAnswer : validAnswers) {
            if (validAnswer.equals(userAnswer)) {
                return true;
            }
        }
        return false;
    }
}
