package io.ruin.model.content.poll;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public final class AnsweredPoll {

    /**
     * The id of this poll.
     */
    @Expose @Getter @Setter private int pollId;

    /**
     * The title of the poll.
     */
    @Expose @Getter @Setter private String title;

    /**
     * The array of poll questions.
     */
    @Expose @Getter @Setter private AnsweredPollQuestion[] questions;

    /**
     * The date and time when the player submitted their answered to this poll.
     */
    @Expose @Getter @Setter private LocalDateTime submitDate;

    public static final class AnsweredPollQuestion {

        /**
         * The question in the poll.
         */
        @Expose @Getter @Setter private String question;

        /**
         * The answer the player chose.
         */
        @Expose @Getter @Setter private String answer;
    }

}
