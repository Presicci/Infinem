package io.ruin.model.content.poll;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public final class PollQuestion {

    /**
     * The question in the poll.
     */
    @Expose @Getter @Setter private String question;

    /**
     * The hyperlink if applicable. The format should be as follows..
     * The text user to overlay the link|https://zenyte.com/community/
     */
    @Expose @Getter @Setter private String hyperlink;

    /**
     * An array of available answers for this question.
     */
    @Expose @Getter @Setter private PollAnswer[] answers;

    public String buildAnswers() {
        final StringBuilder answerBuilder = new StringBuilder();
        final int length = answers.length;
        for (int i = 0; i < 32; i++) {
            final PollAnswer answer = i >= length ? null : answers[i];
            if (answer == null)
                continue;
            answerBuilder.append(answer.getChoice() + "|");
        }
        return answerBuilder.toString();
    }

    /**
     * Gets the answers in the format used when opening the actual vote page.
     * @return formatted poll answers.
     */
    public String getFormattedPollAnswers() {
        final StringBuilder answerBuilder = new StringBuilder();
        final int length = answers.length;
        for (int i = 0; i < length; i++) {
            final PollAnswer answer = answers[i];
            if (answer == null)
                continue;
            answerBuilder.append(answer.getChoice() + "|");
        }
        return answerBuilder.toString();
    }

    public int getAnswerIndex(String choice) {
        for (int i = 0; i < this.answers.length; i++) {
            if (this.answers[i].getChoice().equals(choice)) {
                return i;
            }
        }
        return -1;
    }

    public int getAnswer(AnsweredPoll poll, int questionIndex) {
        if (poll == null)
            return 0;
        final AnsweredPoll.AnsweredPollQuestion[] answers = poll.getQuestions();
        if (questionIndex >= answers.length)
            return 0;
        final String answer = answers[questionIndex].getAnswer();
        for (int i = 0; i < this.answers.length; i++) {
            if (this.answers[i].getChoice().equals(answer)) {
                return 1 << i;
            }
        }
        return 0;
    }

}
