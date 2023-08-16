package io.ruin.model.content.transportation.charterships;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

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

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Can I help you?"),
                new ActionDialogue(() -> firstOptions(player, npc))
        );
    }

    private static void firstOptions(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Yes, who are you?",
                                new PlayerDialogue("Yes, who are you?"),
                                new NPCDialogue(npc, "I'm one of Trader Stan's crew; we are all part of the largest fleet of trading and sailing vessels to ever sail the seven seas."),
                                new NPCDialogue(npc, "If you want to get to a port in a hurry then you can charter one of our ships to take you there - if the price is right..."),
                                new PlayerDialogue("So, where exactly can I go with your ships?"),
                                new NPCDialogue(npc, "We run ships from Port Phasmatys over to Port Tyras, stopping at Port Sarim, Catherby, Brimhaven, Musa Point, the Shipyard and Port Khazard."),
                                new NPCDialogue(npc, "We might dock at Mos Le'Harmless once in a while, as well, if you catch my meaning..."),
                                new PlayerDialogue("Wow, that's a lot of ports. I take it you have some exotic stuff to trade?"),
                                new NPCDialogue(npc, "We certainly do! We have access to items bought and sold from around the world. Would you like to take a look? Or would you like to charter a ship?"),
                                new ActionDialogue(() -> secondOptions(player, npc))
                        ),
                        new Option("Yes, I would like to charter a ship.", new ActionDialogue(() -> charterDialogue(player, npc))),
                        new Option("No thanks.", new PlayerDialogue("No thanks."))
                )
        );
    }

    private static void secondOptions(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Yes, let's see what you're trading.", new PlayerDialogue("Yes, let's see what you're trading."), new ActionDialogue(() -> npc.openShop(player))),
                        new Option("Yes, I would like to charter a ship.", new ActionDialogue(() -> charterDialogue(player, npc))),
                        new Option("Isn't it tricky to sail about in those clothes?",
                                new PlayerDialogue("Isn't it tricky to sail about in those clothes?"),
                                new NPCDialogue(npc, "Tricky? Tricky!"),
                                new NPCDialogue(npc, "With all due credit, Trader Stan is a great employer, but he insists we wear the latest in high fashion even when sailing."),
                                new NPCDialogue(npc, "Do you have even the slightest idea how tricky it is to sail in this stuff?"),
                                new NPCDialogue(npc, "Some of us tried tearing it and arguing that it was too fragile to wear when on a boat, but he just had it enchanted to re-stitch itself."),
                                new NPCDialogue(npc, "It's hard to hate him when we know how much he shells out on this gear, but if I fall overboard because of this getup one more time, I'm going to quit."),
                                new PlayerDialogue(" Wow, that's kind of harsh."),
                                new NPCDialogue(npc, "Anyway, would you like to take a look at our exotic wares from around the world? Or would you like to charter a ship?"),
                                new ActionDialogue(() -> secondOptions(player, npc))
                        ),
                        new Option("No thanks.", new PlayerDialogue("No thanks."))
                )
        );
    }

    private static void charterDialogue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Yes, I would like to charter a ship."),
                new NPCDialogue(npc, "Certainly [madam/sir]. Where would you like to go?"),
                new ActionDialogue(() -> openInterface(player))
        );
    }

    private static void openInterface(Player player) {
        Config.varp(302, false).set(player, 61);        // Unlocks port phasmatys
        Config.varp(655, false).set(player, 140);       // Unlocks mos le'harmless
        Config.varp(365, false).set(player, 2);         // Unlocks shipyard
        Config.varp(150, false).set(player, 160);       // Unlocks shipyard
        Config.varp(328, false).set(player, 15);        // Unlocks port tyras
        //Config.varp(176, false).set(player, 2);         // Unlocks crandor
        Config.varpbit(9016, false).set(player, 200);   // Unlocks prif
        player.openInterface(InterfaceType.MAIN, Interface.CHARTER);
    }

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
                NPCAction.register(index, "talk-to", (CharterShips::dialogue));
                NPCAction.register(index, "charter", ((player, npc) -> openInterface(player)));
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
