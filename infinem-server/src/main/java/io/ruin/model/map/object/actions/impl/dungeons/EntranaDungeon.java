package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2023
 */
public class EntranaDungeon {

    static {
        // Ladder entrance
        ObjectAction.register(2408, 2820, 3374, 0, "climb-down", (player, obj) -> Ladder.climb(player, 2822, 9774, 0, false, true, false));
        ObjectAction.register(2407, 2874, 9750, 0, "open", (player, obj) -> {
            player.dialogue(
                    new MessageDialogue("This door leads to level 32 wilderness. Are you sure you want to proceed through?"),
                    new OptionsDialogue(
                            new Option("Yes (Enter level 32 wilderness)", () -> Traveling.fadeTravel(player, new Position(3250, 3769, 0))),
                            new Option("No way")
                    )
            );
        });
    }
}
