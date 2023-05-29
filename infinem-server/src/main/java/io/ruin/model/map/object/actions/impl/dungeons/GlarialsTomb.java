package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public class GlarialsTomb {

    private static void entrance(Player player) {
        player.dialogue(
                new MessageDialogue("There appears to be a ladder down. Do you want to climb into the tomb?"),
                new OptionsDialogue("Enter the tomb?",
                        new Option("Sure, why not", () -> Ladder.climb(player, new Position(2555, 9844), false, true, false)),
                        new Option("No way")
                )
        );
    }

    static {
        ObjectAction.register(1992, 2558, 3444, 0, "read", ((player, obj) -> entrance(player)));
        Tile.getObject(17387, 2556, 9844, 0).walkTo = Position.of(2555, 9844, 0);
        ObjectAction.register(17387, 2556, 9844, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2557, 3444, 0), true, true, false)));
    }
}
