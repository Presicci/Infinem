package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.activities.combat.pvminstance.PVMInstance;
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
public class GoblinCave {

    static {
        Tile.getObject(2, 2622, 3392, 0).walkTo = Position.of(2624, 3391, 0);
        ObjectAction.register(2, 2622, 3392, 0, "enter", ((player, obj) -> {
            player.startEvent(e -> {
                player.lock();
                player.animate(2796);
                e.delay(1);
                player.resetAnimation();
                player.getMovement().teleport(2620, 9796, 0);
                player.unlock();
            });
        }));
        ObjectAction.register(13, 2621, 9796, 0, "climb-over", (player, obj) -> Ladder.climb(player, new Position(2624, 3391), true, true, false));
        ObjectAction.register(21800, 2617, 9827, 0, "enter", (player, obj) -> {
            player.dialogue(
                    new MessageDialogue("You have a bad feeling about crawling into the next cavern."),
                    new OptionsDialogue("Do you want to enter the cavern?",
                            new Option("Yes", () -> PVMInstance.enterTimeless(player, InstanceType.BOUNCER_GHOST)),
                            new Option("No")
                    )
            );
        });
        ObjectAction.register(21800, "enter", (player, obj) -> player.getMovement().teleport(2617, 9828, 0));
    }
}
