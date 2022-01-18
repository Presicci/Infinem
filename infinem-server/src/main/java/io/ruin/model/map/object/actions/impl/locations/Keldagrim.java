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
        // Keldagrim entrance
        ObjectAction.register(16168, "travel", (player, obj)  -> Traveling.fadeTravel(player, new Position(2909, 10174, 0)));
        ObjectAction.register(5014, 2771, 10161,0, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(2730, 3713, 0)));
        ObjectAction.register(5008, 2731, 3712,0, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(2773, 10162, 0)));
        ObjectAction.register(5973, 2781, 10161,0, "go-through", (player, obj) -> Traveling.fadeTravel(player, new Position( 2838, 10124, 0)));
        ObjectAction.register(5998, 2838, 10123,0, "go-through", (player, obj) -> Traveling.fadeTravel(player, new Position(2780, 10161, 0)));
    }
}
