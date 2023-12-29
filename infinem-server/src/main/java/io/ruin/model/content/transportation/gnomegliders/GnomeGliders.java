package io.ruin.model.content.transportation.gnomegliders;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class GnomeGliders {

    public static void travel(Player player, GliderSpots spot) {
        if (player.getPosition().distance(spot.pos) < 10) {
            player.sendMessage("You are already here!");
            return;
        }
        Traveling.fadeTravel(player, spot.pos, () -> player.getTaskManager().doLookupByUUID(919));  // Fly on a Gnome Glider
        player.closeInterface(InterfaceType.MAIN);
    }

    private static final int[] npcs = {
            10467, 10479, 10459, 10452, 10467, 7517, 7178
    };

    private static void openInterface(Player player) {
        Config.varp(416, false).set(player, 200);       // Unlocks feldip
        Config.varp(1339, true).set(player, 195);      // Unlocks ape atoll
        player.openInterface(InterfaceType.MAIN, Interface.GLIDER);
    }

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hello, would you like to fly somewhere?"),
                new OptionsDialogue(
                        new Option("Sure.", () -> openInterface(player)),
                        new Option("Nope.")
                )
        );
    }

    static {
        for (int id : npcs) {
            NPCAction.register(id, "glider", (player, npc) -> openInterface(player));
            NPCAction.register(id, "talk-to", (player, npc) -> dialogue(player, npc));
        }

        InterfaceHandler.register(Interface.GLIDER, h -> {
            for (GliderSpots glider : GliderSpots.values()) {
                h.actions[glider.component] = (SimpleAction) p -> travel(p, glider);
            }
        });
    }
}
