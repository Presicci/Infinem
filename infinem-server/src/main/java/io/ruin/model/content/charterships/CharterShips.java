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
        if (ship != CharterPoints.CRANDOR)
            Config.CHARTER_PREVIOUS.set(player, ship.ordinal() + 1);
        Traveling.fadeTravel(player, ship.pos);
        player.closeInterface(InterfaceType.MAIN);
    }

    private static final int[] CREWMEMBERS = {
        9312, 9324, 9336, 9348, 9360, 9372
    };

    static {
        for (int member : CREWMEMBERS) {
            for (int index = member; index < member + 12; index++) {
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
            NPCAction.register(member + 1, "Charter-to Brimhaven", ((player, npc) -> travel(player, CharterPoints.BRIMHAVEN)));
            NPCAction.register(member + 2, "Charter-to Catherby", ((player, npc) -> travel(player, CharterPoints.CATHERBY)));
            NPCAction.register(member + 3, "Charter-to Mos Le`Harmless", ((player, npc) -> travel(player, CharterPoints.MOS_LEHARMLESS)));
            NPCAction.register(member + 4, "Charter-to Musa Point", ((player, npc) -> travel(player, CharterPoints.MUSA_POINT)));
            NPCAction.register(member + 5, "Charter-to Port Khazard", ((player, npc) -> travel(player, CharterPoints.PORT_KHAZARD)));
            NPCAction.register(member + 6, "Charter-to Port Phasmatys", ((player, npc) -> travel(player, CharterPoints.PORT_PHASMATYS)));
            NPCAction.register(member + 7, "Charter-to Port Sarim", ((player, npc) -> travel(player, CharterPoints.PORT_SARIM)));
            NPCAction.register(member + 8, "Charter-to Shipyard", ((player, npc) -> travel(player, CharterPoints.SHIP_YARD)));
            NPCAction.register(member + 9, "Charter-to Port Tyras", ((player, npc) -> travel(player, CharterPoints.PORT_TYRAS)));
            NPCAction.register(member + 10, "Charter-to Corsair Cove", ((player, npc) -> travel(player, CharterPoints.CORSAIR_COVE)));
            NPCAction.register(member + 11, "Charter-to Priffddinas", ((player, npc) -> travel(player, CharterPoints.PRIF)));
        }

        InterfaceHandler.register(Interface.CHARTER, h -> {
            for (CharterPoints ship : CharterPoints.values()) {
                h.actions[ship.component] = (SimpleAction) p -> travel(p, ship);
            }
        });
    }
}
