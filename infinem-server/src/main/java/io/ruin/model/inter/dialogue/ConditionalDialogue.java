package io.ruin.model.inter.dialogue;

import io.ruin.api.utils.Tuple;
import io.ruin.model.entity.player.Player;

import java.util.function.BiPredicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/27/2023
 */
public class ConditionalDialogue extends Dialogue {

    private final BiPredicate<Player, Integer> predicate;
    private final Tuple<Dialogue[], Dialogue[]> dialogues;
    private final int value;

    public ConditionalDialogue(BiPredicate<Player, Integer> predicate, Tuple<Dialogue[], Dialogue[]> dialogues, int value) {
        this.predicate = predicate;
        this.dialogues = dialogues;
        this.value = value;
    }

    @Override
    public void open(Player player) {
        player.dialogue(predicate.test(player, value) ? dialogues.first() : dialogues.second());
    }
}
