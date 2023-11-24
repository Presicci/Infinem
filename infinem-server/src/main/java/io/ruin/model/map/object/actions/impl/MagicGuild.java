package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public class MagicGuild {

    private static boolean cantEnter(Player player) {
        if (player.getStats().get(StatType.Magic).currentLevel < 66) {
            player.dialogue(new NPCDialogue(3248, "You need a magic level of 66. The magical energy in here is unsafe for those below that level."));
            return true;
        }
        return false;
    }

    static {
        // Entrances
        ObjectAction.register(1732, 2584, 3088, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1732, 1733, new Position(2584, 3088), new Position(2584, 3087), true);
        });
        ObjectAction.register(1733, 2584, 3087, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1732, 1733, new Position(2584, 3088), new Position(2584, 3087), true);
        });
        ObjectAction.register(1733, 2597, 3088, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1733, 1732, new Position(2597, 3088), new Position(2597, 3087), false);
        });
        ObjectAction.register(1732, 2597, 3087, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1733, 1732, new Position(2597, 3088), new Position(2597, 3087), false);
        });
        // Basement gates
        ObjectAction.register(2155, 2592, 9490, 0, "open", (player, obj) -> player.dialogue(new NPCDialogue(3246, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")));
        ObjectAction.register(2154, 2593, 9490, 0, "open", (player, obj) -> player.dialogue(new NPCDialogue(3246, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")));
    }
}
