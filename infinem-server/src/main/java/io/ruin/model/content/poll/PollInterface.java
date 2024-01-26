package io.ruin.model.content.poll;

import com.google.common.collect.ImmutableList;
import io.ruin.api.utils.NumberUtils;
import io.ruin.data.impl.polls;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.map.object.actions.ObjectAction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public class PollInterface {

    private static final int SHIFTED_POLL_ID = Interface.POLL_RESULTS << 16;
    private static final int SHIFTED_VOTE_ID = Interface.POLL_VOTE << 16;
    private static final int DESCRIPTION_HEX_COLOUR = 16750623;
    private static final int VOTES_HEX_COLOUR = 16777215;

    // Not used anymore but i'm keeping it
    private static void sendLoadingScreen(Player player) {
        Poll poll = player.getPollManager().getPoll();
        player.openInterface(InterfaceType.MAIN, Interface.POLL_RESULTS);
        player.getPacketSender().sendClientScript(603, "iiiiss", SHIFTED_POLL_ID | 2, SHIFTED_POLL_ID | 12,
                SHIFTED_POLL_ID | 11, SHIFTED_POLL_ID | 10, poll.getTitle(), "Loading..."
        );
        player.getPacketSender().sendClientScript(604, "ii", Interface.POLL_RESULTS << 16 | 8, Interface.POLL_RESULTS << 16 | 9);
        player.getPacketSender().sendClientScript(604, "ii", Interface.POLL_RESULTS << 16 | 6, Interface.POLL_RESULTS << 16 | 7);
        player.getPacketSender().sendClientScript(604, "ii", Interface.POLL_RESULTS << 16 | 4, Interface.POLL_RESULTS << 16 | 5);
        player.getPacketSender().sendString(Interface.POLL_RESULTS, 3, "");
        player.getPacketSender().sendVarp(375, 8);
    }

    protected static void sendPollInterface(Player player, Poll poll) {
        player.closeInterface(InterfaceType.MAIN);
        player.openInterface(InterfaceType.MAIN, Interface.POLL_RESULTS);
        // Title and building...
        player.getPacketSender().sendClientScript(603, "siiiis", "Infinem Poll #" + poll.getPollId() + ": " + poll.getTitle(), SHIFTED_POLL_ID | 2, SHIFTED_POLL_ID | 12,
                SHIFTED_POLL_ID | 11, SHIFTED_POLL_ID | 10, "Building...");
        // Hide building...
        player.getPacketSender().setHidden(Interface.POLL_RESULTS, 10, true);
        // Description
        player.getPacketSender().sendClientScript(609, "iiiiiiis", DESCRIPTION_HEX_COLOUR, 0, 495, 495, 12, 5, SHIFTED_POLL_ID | 11,
                poll.getDescription() + "|" + poll.getFormattedEndDate());
        // Forum link
        String hyperlink = poll.getHyperlink();
        if (hyperlink != null) {
            player.getPacketSender().sendClientScript(610, "ssi", "Click here to read the blog.", hyperlink, SHIFTED_POLL_ID | 11);
        }
        // Vote totals
        player.getPacketSender().sendClientScript(609, "siiiiiii", "Votes: " + NumberUtils.formatNumber(poll.getVotes()), VOTES_HEX_COLOUR, 1, 496, 496, 12, 5, SHIFTED_POLL_ID | 11);
        AnsweredPoll answeredPoll = player.getPollManager().getAnsweredPolls().get(poll.getPollId());
        final boolean hasVoted = answeredPoll != null;

        generateQuestionScript(player, false, false, false);

        player.getPacketSender().sendClientScript(609, DESCRIPTION_HEX_COLOUR, 1, 496, 496, 12, 5, SHIFTED_POLL_ID | 11);
        player.getPacketSender().sendClientScript(618, SHIFTED_POLL_ID | 11, SHIFTED_POLL_ID | 12, SHIFTED_POLL_ID | 10, 1);
        player.getPacketSender().sendClientScript(604, SHIFTED_POLL_ID | 8, SHIFTED_POLL_ID | 9, "History");
        final boolean closed = poll.isClosed();
        player.getPacketSender().sendClientScript(604, SHIFTED_POLL_ID | 6, SHIFTED_POLL_ID | 7,
                closed || hasVoted && !poll.isAmendable() ? "" : "Refresh");
        player.getPacketSender().sendClientScript(604, SHIFTED_POLL_ID | 4, SHIFTED_POLL_ID | 5,
                closed || hasVoted && !poll.isAmendable() ? "" : (hasVoted ? "Amend" : "Vote"));
        if (!closed) {
            if (hasVoted) {
                if (poll.isAmendable()) {
                    player.getPacketSender().sendString(Interface.POLL_RESULTS, 3, "Votes in this poll can be amended.");
                } else {
                    player.getPacketSender().sendString(Interface.POLL_RESULTS, 3, "Votes in this poll cannot be amended.");
                }
            } else {
                player.getPacketSender().sendString(Interface.POLL_RESULTS, 3, "You have not yet voted in this poll.");
            }
        } else {
            player.getPacketSender().sendString(Interface.POLL_RESULTS, 3, "This poll has closed.");
        }
        player.getPacketSender().sendClientScript(135, SHIFTED_POLL_ID | 3, 495);
    }

    protected static void sendVoteInterface(Player player, boolean initial, boolean clear, int questionNum) {
        if (clear) {
            player.getPollManager().clearVotes();
        } else if (initial) {
            player.getPollManager().reloadVotes();
        }
        Poll poll = player.getPollManager().getPoll();
        player.openInterface(InterfaceType.MAIN, Interface.POLL_VOTE);
        // Title and building...
        player.getPacketSender().sendClientScript(603, "siiiis", "Infinem Poll #" + poll.getPollId() + ": " + poll.getTitle(), SHIFTED_VOTE_ID | 3, SHIFTED_VOTE_ID | 13, SHIFTED_VOTE_ID | 12, SHIFTED_VOTE_ID | 11, "Building...");
        // Description
        player.getPacketSender().sendClientScript(609, "iiiiiiis", DESCRIPTION_HEX_COLOUR, 0, 495, 495, 12, 5, SHIFTED_VOTE_ID | 10,
                poll.getDescription() + "|" + poll.getFormattedEndDate());
        // Forum link
        String hyperlink = poll.getHyperlink();
        if (hyperlink != null) {
            player.getPacketSender().sendClientScript(610, "si", hyperlink, SHIFTED_VOTE_ID | 10);
        }

        generateQuestionScript(player, initial, true, clear);

        player.getPacketSender().sendClientScript(618, "III1", 26214410, 26214413, 26214409, questionNum == -1 ? 0 : 1);

        if (questionNum == -1) {
            player.getPacketSender().sendString(Interface.POLL_VOTE, 4, "Cast your vote.");
        } else {
            player.getPacketSender().sendString(Interface.POLL_VOTE, 4, "<col=ff0000>Please answer question " + questionNum + ".</col>");
        }

        player.getPacketSender().sendClientScript(604, "IIs", 26214405, 26214406, "Clear");
        player.getPacketSender().sendClientScript(604, "IIs", 26214407, 26214408, "Cancel");
        player.getPacketSender().sendClientScript(604, "IIs", 26214411, 26214412, "Vote");
    }

    public static void sendHistory(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.POLL_HISTORY);
        final StringBuilder titleBuilder = new StringBuilder();
        final StringBuilder dateBuilder = new StringBuilder();
        final int size = polls.POLLS.size();
        for (int i = size; i >= 1; i--) {
            final Poll poll = polls.POLLS.get(i);
            if (poll == null) {
                continue;
            }
            titleBuilder.append("<col=df780f>").append(poll.getTitle()).append("</col>|");
            dateBuilder.append(poll.getFormattedPollDates()).append("|");
        }
        player.getPacketSender().sendClientScript(627, polls.POLLS.size(), titleBuilder.toString(), dateBuilder.toString());
    }

    public static void generateQuestionScript(@Nonnull Player player, boolean initial, boolean voting, boolean clear) {
        Poll poll = player.getPollManager().getPoll();
        for (int i = 0; i < poll.getQuestions().length; i++) {
            PollQuestion question = poll.getQuestions()[i];

            if (voting) {
                int value = player.getPollManager().getVotes()[i] - 1;
                if (clear || initial) {
                    player.getPacketSender().sendClientScript(619, "isi",
                            getQuestionValue(question, i), ("Question " + (i + 1) + "|" + question.getQuestion() + "|||" + question.getFormattedPollAnswers()), value < 0 ? 0 : 1 << value);
                } else {
                    player.getPacketSender().sendClientScript(620, "ii",
                            getQuestionValue(question, i), value < 0 ? 0 : 1 << value);
                }
            } else {
                Object[] parameters = generateParameters(question, i);
                player.getPacketSender().sendClientScript(624, "iisiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", parameters);
            }
        }
    }

    public static Object[] generateParameters(PollQuestion question, int index) {
        List<Integer> parms = new ArrayList<>();
        for (int i = 0; i < question.getAnswers().length; i++) {
            parms.add((Integer) question.getAnswers()[i].getVotes());
        }
        if (question.getAnswers().length < 6) {
            for (int i = 0; i < 6 - question.getAnswers().length; i++) {
                parms.add(0);
            }
        }
        return new Object[]{
                (index + 1), -1, ("Question " + (index + 1) + "|" + question.getQuestion() + generateAnswers(question)),
                (Integer) question.getAnswers().length, 0, (Integer) parms.get(0), (Integer) parms.get(1), (Integer) parms.get(2),
                (Integer) parms.get(3), (Integer) parms.get(4), (Integer) parms.get(5),
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

        };
    }

    public static String generateAnswers(PollQuestion question) {
        return "|||" + question.buildAnswers();
    }

    public static int getQuestionValue(PollQuestion question, int index) {
        return (256 * index) + (4 * question.getAnswers().length);
    }

    private static final ImmutableList<Integer> BOOTHS = ImmutableList.of(26815, 8720);

    static {
        LoginListener.register(player -> {
            player.getPollManager().loadAnsweredPolls();
        });
        for (int id : BOOTHS) {
            ObjectAction.register(id, 1, (player, obj) -> player.getPollManager().viewPoll(polls.POLLS.get(polls.latestPollId)));
        };
        InterfaceHandler.register(Interface.POLL_HISTORY, h -> {
            h.actions[4] = (SlotAction) (player, slot) -> {
                Poll poll = polls.POLLS.get(polls.POLLS.size() - slot);
                if (poll == null) {
                    return;
                }
                player.getPollManager().viewPoll(poll);
            };
        });
        InterfaceHandler.register(Interface.POLL_RESULTS, h -> {
            h.actions[5] = (SimpleAction) player -> sendVoteInterface(player, true, false, -1);
            h.actions[7] = (SimpleAction) player -> player.getPollManager().refreshPoll();
            h.actions[9] = (SimpleAction) PollInterface::sendHistory;
        });
        InterfaceHandler.register(Interface.POLL_VOTE, h -> {
            h.actions[2] = (SlotAction) (player, slot) -> {
                int[] votes = player.getPollManager().getVotes();
                final int questionId = slot >> 5;
                final int answerId = slot % 32;
                if (questionId > votes.length) {
                    return;
                }
                votes[questionId] = answerId + 1;
                sendVoteInterface(player, false, false, -1);
            };
            // Clear
            h.actions[6] = (SimpleAction) player -> sendVoteInterface(player, false, true, -1);
            // Cancel
            h.actions[8] = (SimpleAction) player -> sendPollInterface(player, player.getPollManager().getPoll());
            // Vote
            h.actions[12] = (SimpleAction) player -> {
                int[] votes = player.getPollManager().getVotes();
                for (int i = 0; i < votes.length; i++) {
                    if (votes[i] == 0) {
                        sendVoteInterface(player, false, false, i + 1);
                        return;
                    }
                }
                player.getPollManager().submitVotes();
            };
        });
    }
}
