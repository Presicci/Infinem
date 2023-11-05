package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class Keldagrim {
    static {
        // Ice mountain -> Keldagrim
        ObjectAction.register(7029, 2995, 9836, 0, "ride", (player, obj) -> Traveling.fadeTravel(player, new Position(2911, 10176, 0)));
        // Keldagrim -> Ice mountain
        ObjectAction.register(7028, 2911, 10175, 0, "ride", (player, obj) -> Traveling.fadeTravel(player, new Position(2995, 9837, 0), () -> {
            player.getTaskManager().doLookupByUUID(923);    // Ride a Minecart out of Keldagrim
        }));
        // GE -> Keldagrim
        ObjectAction.register(16168, "inspect", (player, obj) -> player.dialogue(new MessageDialogue("The trapdoor leads to Keldagrim.")));
        ObjectAction.register(16168, "travel", (player, obj) -> Traveling.fadeTravel(player, new Position(2909, 10172, 0)));
        // Keldagrim -> GE
        ObjectAction.register(7029, 2909, 10171, 0, "ride", (player, obj) -> Traveling.fadeTravel(player, new Position(3141, 3504, 0), () -> {
            player.getTaskManager().doLookupByUUID(923);    // Ride a Minecart out of Keldagrim
        }));
        // White wolf mountain -> Keldagrim
        ObjectAction.register(7030, 2875, 9868, 0, "ride", (player, obj) -> Traveling.fadeTravel(player, new Position(2911, 10170, 0)));
        // Keldagrim -> White wolf mountain
        ObjectAction.register(7030, 2911, 10169, 0, "ride", (player, obj) -> Traveling.fadeTravel(player, new Position(2874, 9868, 0), () -> {
            player.getTaskManager().doLookupByUUID(923);    // Ride a Minecart out of Keldagrim
        }));

        // Keldagrim entrance
        ObjectAction.register(5014, 2771, 10161,0, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(2730, 3713, 0)));
        ObjectAction.register(5008, 2731, 3712,0, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(2773, 10162, 0)));
        ObjectAction.register(5973, 2781, 10161,0, "go-through", (player, obj) -> Traveling.fadeTravel(player, new Position( 2838, 10124, 0)));
        ObjectAction.register(5998, 2838, 10123,0, "go-through", (player, obj) -> Traveling.fadeTravel(player, new Position(2780, 10161, 0)));
    }
}
