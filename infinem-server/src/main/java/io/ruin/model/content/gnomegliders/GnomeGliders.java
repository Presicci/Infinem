package io.ruin.model.content.gnomegliders;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class GnomeGliders {

    public static void travel(Player player, GliderSpots spot) {
        if (player.getPosition().distance(spot.pos) < 10) {
            player.sendMessage("You are already there!");
            return;
        }
        Traveling.fadeTravel(player, spot.pos);
        player.closeInterface(InterfaceType.MAIN);
    }

    private static final int npcs[] = {
            7517, 6092, 6091, 6090, 6089, 7178
    };

    static {
        for (int index : npcs) {
            NPCAction.register(index, "glider", ((player, npc) -> {
                Config.varp(416, false).set(player, 200);       // Unlocks feldip
                //Config.varp(1339, true).set(player, 195);      // Unlocks ape atoll
                player.openInterface(InterfaceType.MAIN, Interface.GLIDER);
            }));
        }

        InterfaceHandler.register(Interface.GLIDER, h -> {
            for (GliderSpots glider : GliderSpots.values()) {
                h.actions[glider.component] = (SimpleAction) p -> travel(p, glider);
            }
        });
    }
}
