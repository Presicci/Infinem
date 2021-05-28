package io.ruin.model.content.charterships;

import io.ruin.model.content.gnomegliders.GliderSpots;
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
public class CharterShips {
    //302 -> 61 Port phasmatys
    //655 -> 140 Mos Le'Harmless
    //365 -> 2 && 150 -> 160 Shipyard
    //328 -> 15 Port tyras
    //176 -> 2 prif

    public static void travel(Player player, CharterPoints ship) {
        if (player.getPosition().distance(ship.pos) < 10) {
            player.sendMessage("You are already here!");
            return;
        }
        Traveling.fadeTravel(player, ship.pos);
        player.closeInterface(InterfaceType.MAIN);
    }

    private static final int npcs[] = {
        1334, 1331, 1333, 1330
    };

    static {
        for (int index : npcs) {
            NPCAction.register(index, "charter", ((player, npc) -> {
                Config.varp(302, false).set(player, 61);        // Unlocks port phasmatys
                Config.varp(655, false).set(player, 140);       // Unlocks mos le'harmless
                Config.varp(365, false).set(player, 2);         // Unlocks shipyard
                Config.varp(150, false).set(player, 160);       // Unlocks shipyard
                Config.varp(328, false).set(player, 15);        // Unlocks port tyras
                //Config.varp(176, false).set(player, 2);         // Unlocks crandor
                Config.varpbit(9016, false).set(player, 200);   // Unlocks prif
                player.openInterface(InterfaceType.MAIN, Interface.CHARTER);
            }));
        }

        InterfaceHandler.register(Interface.CHARTER, h -> {
            for (CharterPoints ship : CharterPoints.values()) {
                h.actions[ship.component] = (SimpleAction) p -> travel(p, ship);
            }
        });
    }
}
