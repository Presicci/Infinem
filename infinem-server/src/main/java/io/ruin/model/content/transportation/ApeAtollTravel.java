package io.ruin.model.content.transportation;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/17/2023
 */
public class ApeAtollTravel {

    private static final int DAERO = 1444, WAYDAR = 1446, WAYDARBACK = 1446, LUMBO = 1454;

    private static final Config WAYDAR_CONFIG = Config.varpbit(124, false).defaultValue(1);
    private static final Config LUMBO_CONFIG = Config.varpbit(125, false).defaultValue(3);

    static {
        NPCAction.register(DAERO, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("I need to return to Crash Island."),
                new NPCDialogue(npc, "You know the routine..."),
                new MessageDialogue("You wear the blindfold Daero hands you."),
                new ActionDialogue(() -> Traveling.fadeTravel(player, new Position(2392, 9884, 0)))
        ));
        NPCAction.register(WAYDAR, "talk-to", (player, npc) -> {
            boolean crashIsland = npc.getAbsY() < 9000;
            player.dialogue(
                    new NPCDialogue(npc, "Shall we go to " + (crashIsland ? "the hanger?" : "Crash Island?")),
                    new OptionsDialogue(
                            new Option("Yes",
                                    new PlayerDialogue("Yes, let's go."),
                                    new NPCDialogue(npc, "As you wish."),
                                    new ActionDialogue(() -> {
                                        if (crashIsland)
                                            Traveling.fadeTravel(player, new Position(2392, 9888, 0));
                                        else
                                            Traveling.fadeTravel(player, new Position(2896, 2726, 0));
                                    })),
                            new Option("No", new PlayerDialogue("Not yet. I'll be back later."))
                    )
            );
        });
        NPCAction.register(WAYDAR, "travel", (player, npc) -> {
            if (npc.getAbsY() < 9000)
                Traveling.fadeTravel(player, new Position(2392, 9888, 0));
            else
                Traveling.fadeTravel(player, new Position(2896, 2726, 0));
        });
        NPCAction.register(LUMBO, "talk-to", (player, npc) -> {
            boolean crashIsland = npc.getAbsX() > 2841;
            player.dialogue(
                    new NPCDialogue(npc, "Shall we go to " + (crashIsland ? "Ape Atoll?" : "Crash Island?")),
                    new OptionsDialogue(
                            new Option("Yes",
                                    new PlayerDialogue("Yes, let's go."),
                                    new NPCDialogue(npc, "As you wish."),
                                    new ActionDialogue(() -> {
                                        if (crashIsland)
                                            Traveling.fadeTravel(player, new Position(2803, 2709, 0));
                                        else
                                            Traveling.fadeTravel(player, new Position(2896, 2726, 0));
                                    })),
                            new Option("No", new PlayerDialogue("Not yet. I'll be back later."))
                    )
            );
        });
        NPCAction.register(LUMBO, "travel", (player, npc) -> {
            if (npc.getAbsX() > 2841)
                Traveling.fadeTravel(player, new Position(2803, 2709, 0));
            else
                Traveling.fadeTravel(player, new Position(2896, 2726, 0));
        });
        ObjectAction.register(4869, "operate", (player, obj) -> Traveling.fadeTravel(player, new Position(2483, 3486, 1)));
    }
}
