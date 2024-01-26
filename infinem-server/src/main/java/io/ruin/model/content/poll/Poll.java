package io.ruin.model.content.poll;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public final class Poll {

    /**
     * The id of this poll.
     */
    @Expose @Getter @Setter private int pollId;

    /**
     * The title of the poll.
     */
    @Expose @Getter @Setter private String title;

    /**
     * The description of the poll, lines separated using the | character.
     */
    @Expose @Getter @Setter private String description;

    /**
     * The hyperlink if applicable. The format should be as follows..
     * The text user to overlay the link, https://zenyte.com/community/
     */
    @Expose @Getter @Setter private String hyperlink;

    /**
     * The local date when the poll was made available.
     */
    @Expose @Getter @Setter private LocalDate startDate;

    /**
     * The local date when the poll was closed.
     */
    @Expose @Getter @Setter private LocalDate endDate;

    /**
     * The number of votes in the poll.
     */
    @Expose @Getter @Setter private int votes;

    /**
     * The array of poll questions.
     */
    @Expose @Getter @Setter private PollQuestion[] questions;

    /**
     * Whether the votes in this poll can be amended or not.
     */
    @Expose @Getter @Setter private boolean amendable;

    public void castVote(int[] answers) {
        votes++;
        for (int index = 0; index < questions.length; index++) {
            PollQuestion question = questions[index];
            if (index > answers.length) break;
            int answerIndex = answers[index] - 1;
            if (answers[index] == 0) continue;
            PollAnswer answer = question.getAnswers()[answerIndex];
            answer.incrementVotes();
        }
        for (PollQuestion question : questions) {
            for (PollAnswer answer : question.getAnswers()) {
                System.out.println(answer.getChoice() + ": " + answer.getVotes());
            }
        }
    }

    public void updateVote(AnsweredPoll.AnsweredPollQuestion[] oldAnswers, int[] answers) {
        for (int index = 0; index < questions.length; index++) {
            PollQuestion question = questions[index];
            if (index > answers.length) break;
            int answerIndex = answers[index] - 1;
            if (answers[index] == 0) continue;
            PollAnswer answer = question.getAnswers()[answerIndex];
            if (oldAnswers[index] != null) {
                String oldAnswerString = oldAnswers[index].getAnswer();
                if (!oldAnswerString.equals(answer.getChoice())) {
                    int oldIndex = question.getAnswerIndex(oldAnswerString);
                    if (oldIndex != -1) {
                        question.getAnswers()[oldIndex].decrementVotes();
                    }
                    answer.incrementVotes();
                }
                continue;
            }
            answer.incrementVotes();
        }
        for (PollQuestion question : questions) {
            for (PollAnswer answer : question.getAnswers()) {
                System.out.println(answer.getChoice() + ": " + answer.getVotes());
            }
        }
    }

    public String getFormattedEndDate() {
        final int endDay = endDate.getDayOfMonth();
        final String dayOfWeek = StringUtils.initialCaps(endDate.getDayOfWeek().toString());
        final String endMonth = StringUtils.initialCaps(endDate.getMonth().toString());
        if (this.isClosed())
            return "This poll closed on " + dayOfWeek + " " + endDay + getSuffix(endDay % 10) + " " + endMonth + ", " + this.endDate.getYear() + ".";
        return "This poll will close on " + dayOfWeek + " " + endDay + getSuffix(endDay % 10) + " " + endMonth + ".";
    }

    /**
     * Formats the poll start and end date into the format seen on the interface.
     * @return a formatted string of the dates.
     */
    public String getFormattedPollDates() {
        final int startDay = startDate.getDayOfMonth();
        final String startMonth = StringUtils.initialCaps(startDate.getMonth().toString());
        final String startDate = startDay + getSuffix(startDay % 10) + " " + startMonth;
        final int endDay = endDate.getDayOfMonth();
        final String endMonth = StringUtils.initialCaps(endDate.getMonth().toString());
        final String endDate = endDay + getSuffix(endDay % 10) + " " + endMonth;
        return startDate + " - " + endDate + " " + this.endDate.getYear();
    }

    /**
     * Gets the suffix for the day.
     * @param day the day value to obtain the suffix for.
     * @return the suffix for the day, e.g. "st", "nd", "rd", "th".
     */
    private String getSuffix(final int day) {
        return day == 1 ? "st" : day == 2 ? "nd" : day == 3 ? "rd" : "th";
    }

    /**
     * Whether the poll is closed or not
     * @return if closed or not.
     */
    public boolean isClosed() {
        final LocalDate now = LocalDate.now();
        if (now.getYear() > endDate.getYear())
            return true;
        return now.getDayOfYear() >= endDate.getDayOfYear();
    }

}

