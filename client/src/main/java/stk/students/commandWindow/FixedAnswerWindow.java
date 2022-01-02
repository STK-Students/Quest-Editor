package stk.students.commandWindow;

import java.util.List;

public class FixedAnswerWindow extends BaseWindow {

    List<String> validAnswers;

    public FixedAnswerWindow(final String message) {
        super(message);
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
