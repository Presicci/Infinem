package io.ruin.model.content.poll;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public class PollAnswer {
    /**
     * The name of the choice in the poll.
     */
    @Expose @Getter @Setter private String choice;

    /**
     * The number of votes this choice has received.
     */
    @Expose @Getter @Setter private int votes;

    public void incrementVotes() {
        votes++;
    }

    public void decrementVotes() {
        votes--;
    }
}
