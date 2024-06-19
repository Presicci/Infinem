package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.GhostSpeak;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2023
 */
public class ShipNPC {

    /**
     * Registers the 'talk-to' and 'travel' options of the NPC to take you to the destination.
     * @param npcId ID of the NPC
     * @param destinationName Formatted name of the destination name that will be fed to the dialogue.
     * @param destination The coordinates of the destination.
     */
    public static void registerShipNPC(int npcId, String destinationName, Position destination) {
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Would you like me to take you to " + destinationName + "?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> ShipNPC.setSail(player, destinationName, destination)),
                            new Option("No")
                    )
            );
        }));
        NPCAction.register(npcId, "travel", (player, npc) -> ShipNPC.setSail(player, destinationName, destination));
        NPCAction.register(npcId, "pay-fare", (player, npc) -> ShipNPC.setSail(player, destinationName, destination));
        NPCAction.register(npcId, destinationName, (player, npc) -> ShipNPC.setSail(player, destinationName, destination));
    }

    /**
     * Takes the player to the destination.
     * @param player The player being transported.
     * @param destinationName Formatted name of the destination name that will be fed to the dialogue.
     * @param destination The coordinates of the destination.
     */
    private static void setSail(Player player, String destinationName, Position destination) {
        player.dialogue(new MessageDialogue("You set sail for " + destinationName + ".").hideContinue());
        Traveling.fadeTravel(player, destination, 4, player::closeDialogue);
    }

    static {
        // Void Knight Outpost
        registerShipNPC(1770, "the Void Knights' Outpost", new Position(2659, 2676, 0));
        registerShipNPC(1769, "Port Sarim", new Position(3040, 3202, 0));
        // Crandor
        registerShipNPC(819, "Crandor", new Position(2853, 3238, 0));
        // Karamja
        registerShipNPC(3644, "Karamja", new Position(2956, 3146, 0));
        registerShipNPC(3645, "Karamja", new Position(2956, 3146, 0));
        registerShipNPC(3646, "Karamja", new Position(2956, 3146, 0));
        registerShipNPC(3648, "Port Sarim", new Position(3029, 3217, 0));
        // Corsair cove
        registerShipNPC(7967, "Corsair Cove", new Position(2578, 2840, 0));
        registerShipNPC(7965, "Port Sarim", new Position(2909, 3226, 0));
        // Fishing platform
        registerShipNPC(5069, "the Fishing Platform", new Position(2782, 3274, 0));
        registerShipNPC(5070, "Witchaven", new Position(2719, 3303, 0));
        // Piscatoris <-> Gnome rowboat
        registerShipNPC(4298, "the Fishing Colony", new Position(2356, 3640));
        registerShipNPC(4299, "the Gnome Stronghold", new Position(2367, 3485));
        // Fremmy Province -> Weiss
        registerShipNPC(828, "Weiss", new Position(2851, 3968));
        // Fremmy <-> Isle of Stone
        registerShipNPC(9272, "Rellekka", new Position(2621, 3689, 0));
        registerShipNPC(9270, "the Isle of Stone", new Position(2471, 3996, 0));
        // Phasmatys <-> Slepe
        registerShipNPC(8267, "Slepe", new Position(3661, 3278, 0));
        registerShipNPC(8268, "Port Phasmatys", new Position(3672, 3544, 0));
        ObjectAction.register(32601, "travel", (player, obj) -> ShipNPC.setSail(player, "Slepe", new Position(3661, 3278, 0)));
        ObjectAction.register(32602, "travel", (player, obj) -> ShipNPC.setSail(player, "Port Phasmatys", new Position(3672, 3544, 0)));
        // Phasmatys <-> Dragontooth
        NPCAction.register(3005, "talk-to", (player, npc) -> {
            boolean hasGhostspeak = GhostSpeak.canSpeak(player);
            if (npc.getAbsX() < 3736) {
                player.dialogue(
                        new NPCDialogue(npc.getId(), hasGhostspeak ? "Would you like me to take you to Dragontooth Island?" : "oooh oooo oooh oh?"),
                        new OptionsDialogue(
                                new Option("Yes" + (hasGhostspeak ? "" : "?"), () -> ShipNPC.setSail(player, "Dragontooth Island", new Position(3792, 3559, 0))),
                                new Option("No" + (hasGhostspeak ? "" : "?"))
                        )
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc.getId(), hasGhostspeak ? "Would you like me to take you to Port Phasmatys?" : "oooooh wooo ooh?"),
                        new OptionsDialogue(
                                new Option("Yes" + (hasGhostspeak ? "" : "?"), () -> ShipNPC.setSail(player, "Port Phasmatys", new Position(3702, 3487, 0))),
                                new Option("No" + (hasGhostspeak ? "" : "?"))
                        )
                );
            }
        });
        // Morton
        NPCAction.register(5046, "talk-to", ((player, npc) -> {
            if (npc.getAbsY() > 3373) {
                player.dialogue(
                        new NPCDialogue(npc.getId(), "Would you like me to take you to Mort'ton?"),
                        new OptionsDialogue(
                                new Option("Yes", () -> ShipNPC.setSail(player, "Mort'ton", new Position(3522, 3285, 0))),
                                new Option("No")
                        )
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc.getId(), "Would you like me to take you to Mort Myre?"),
                        new OptionsDialogue(
                                new Option("Yes", () -> ShipNPC.setSail(player, "Mort Myre", new Position(3498, 3380, 0))),
                                new Option("No")
                        )
                );
            }
        }));
    }
}
