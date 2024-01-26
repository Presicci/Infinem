package io.ruin.model.content.poll;

import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.impl.polls;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public class PollManager {

    @Getter @Setter private Poll poll;
    @Getter @Setter private int[] votes;
    @Getter private final Map<Integer, AnsweredPoll> answeredPolls = new HashMap<>();
    private final Player player;

    public PollManager(Player player) {
        this.player = player;
    }

    private void newPoll(Poll poll) {
        this.poll = poll;
        this.votes = new int[poll.getQuestions().length];
    }

    public void viewPoll(Poll poll) {
        if (this.poll != poll) newPoll(poll);
        PollInterface.sendPollInterface(player, poll);
    }

    public void clearVotes() {
        this.votes = new int[poll.getQuestions().length];
    }

    public void refreshPoll() {
        this.poll = polls.POLLS.get(poll.getPollId());
        PollInterface.sendPollInterface(player, poll);
    }

    public void reloadVotes() {
        AnsweredPoll answeredPoll = getAnsweredPolls().get(poll.getPollId());
        if (answeredPoll != null) {
            int[] votes = player.getPollManager().getVotes();
            for (int i = 0; i < poll.getQuestions().length; i++) {
                PollQuestion question = poll.getQuestions()[i];
                votes[i] = question.getAnswerIndex(answeredPoll.getQuestions()[i].getAnswer()) + 1;
            }
        }
    }

    /**
     * Submits the players votes and saves them. Sends a dialogue that informs the player so.
     */
    public void submitVotes() {
        player.lock();
        player.startEvent(e -> {
            player.dialogue(false, new MessageDialogue("Submitting your vote...").hideContinue());
            e.delay(1);
            final boolean increaseNumbers = polls.POLLS.containsKey(poll.getPollId());
            final PollQuestion[] questions = poll.getQuestions();
            final int length = questions.length;
            if (increaseNumbers) {
                if (answeredPolls.containsKey(poll.getPollId())) {
                    polls.POLLS.get(poll.getPollId()).updateVote(answeredPolls.get(poll.getPollId()).getQuestions(), votes);
                } else {
                    polls.POLLS.get(poll.getPollId()).castVote(votes);
                }
            }
            final AnsweredPoll answeredPoll = new AnsweredPoll();
            answeredPoll.setPollId(poll.getPollId());
            answeredPoll.setSubmitDate(LocalDateTime.now());
            answeredPoll.setTitle(poll.getTitle());
            final AnsweredPoll.AnsweredPollQuestion[] answeredQuestions = new AnsweredPoll.AnsweredPollQuestion[length];
            for (int i = 0; i < length; i++) {
                final AnsweredPoll.AnsweredPollQuestion q = new AnsweredPoll.AnsweredPollQuestion();
                if (i >= votes.length) {
                    continue;
                }
                q.setQuestion(questions[i].getQuestion());
                q.setAnswer(questions[i].getAnswers()[votes[i] - 1].getChoice());
                answeredQuestions[i] = q;
            }
            answeredPoll.setQuestions(answeredQuestions);
            answeredPolls.put(poll.getPollId(), answeredPoll);
            saveAnsweredPolls();
            player.unlock();
            viewPoll(poll);
            player.dialogue(false, new MessageDialogue("Thank you for voting!"));
        });

    }

    /**
     * Saves all the polls this player has answered. Executed on a thread pool to avoid locking the main thread up.
     */
    public void saveAnsweredPolls() {
        Server.worker.execute(() -> {
            try {
                final Collection<AnsweredPoll> polls = this.answeredPolls.values();
                final String toJson = JsonUtils.GSON_PRETTY.toJson(polls);
                final File file = new File("data/polls/answers/" + player.getName() + ".json");
                final PrintWriter pw = new PrintWriter(file, "UTF-8");
                pw.println(toJson);
                pw.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Loads all the polls that the player has answered in the past. Executed on a thread pool to avoid locking the main thread up.
     */
    public void loadAnsweredPolls() {
        Server.worker.execute(() -> {
            try {
                final File file = new File("data/polls/answers/" + player.getName() + ".json");
                if (!file.exists()) {
                    System.out.println("not exist: " + file.getAbsolutePath());
                    return;
                }
                final BufferedReader br = new BufferedReader(new FileReader(file));
                final AnsweredPoll[] polls = JsonUtils.GSON.fromJson(br, AnsweredPoll[].class);
                for (int i = polls.length - 1; i >= 0; i--) {
                    final AnsweredPoll poll = polls[i];
                    if (poll == null) {
                        continue;
                    }
                    this.answeredPolls.put(poll.getPollId(), poll);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }
}
