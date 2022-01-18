package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class Keldagrim {
    static {
        // Ge trapdoor
        ObjectAction.register(16168, "inspect", (player, obj)  -> {
            player.dialogue(new MessageDialogue("The trapdoor leads to Keldagrim."));
        });
        ObjectAction.register(16168, "travel", (player, obj)  -> Traveling.fadeTravel(player, new Position(2909, 10174, 0)));
    }
}
