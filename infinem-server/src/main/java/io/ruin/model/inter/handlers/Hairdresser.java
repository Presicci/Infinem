package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.content.BeardStyle;
import io.ruin.model.content.HairStyle;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2023
 */
public class Hairdresser {

    private static final int PRICE = 500;

    private static void dialogue(Player player, NPC npc) {
        if (player.getAppearance().isMale()) {
            player.dialogue(
                    new NPCDialogue(npc, "Good afternoon sir. In need of a haircut are we? Or perhaps a shave? Currently we're offering this service for " + (PRICE == 0 ? "free!" : NumberUtils.formatNumber(PRICE) + " coins.")),
                    new OptionsDialogue(
                            new Option("I'd like a haircut please.",
                                    new PlayerDialogue("I'd like a haircut please."),
                                    new NPCDialogue(npc, "Please select the hairstyle you would like from this brochure. I'll even throw in a free recolour."),
                                    new ActionDialogue(() -> open(player, npc, true))),
                            new Option("I'd like a shave please.",
                                    new PlayerDialogue("I'd like a shave please."),
                                    new NPCDialogue(npc, "Please select the facial hair you would like from this brochure. I'll even throw in a free recolour."),
                                    new ActionDialogue(() -> open(player, npc, false))),
                            new Option("No thank you.",
                                    new PlayerDialogue("No thank you."),
                                    new NPCDialogue(npc, "Very well. Come back if you change your mind."))
                    )
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Good afternoon madam. In need of a haircut are we? Currently we're offering this service for " + (PRICE == 0 ? "free!" : NumberUtils.formatNumber(PRICE) + " coins.")),
                    new OptionsDialogue(
                            new Option("Yes please.",
                                    new PlayerDialogue("Yes please."),
                                    new NPCDialogue(npc, "Please select the hairstyle and colour you would like from this brochure."),
                                    new ActionDialogue(() -> open(player, npc, true))),
                            new Option("No thank you.",
                                    new PlayerDialogue("No thank you."),
                                    new NPCDialogue(npc, "Very well. Come back if you change your mind."))
                    )
            );
        }

    }

    private static void open(Player player, NPC npc, boolean haircut) {
        player.removeTemporaryAttribute(AttributeKey.SELECTED_HAIR_STYLE);
        player.removeTemporaryAttribute(AttributeKey.SELECTED_HAIR_COLOR);
        player.removeTemporaryAttribute(AttributeKey.SELECTED_BEARD_STYLE);
        player.removeTemporaryAttribute(AttributeKey.SELECTED_BEARD_COLOR);
        player.getPacketSender().sendVarp(261, 0);
        player.getPacketSender().sendVarp(263, 0);
        Config.HAIRCUT.set(player, haircut ? 1 : 2);
        Config.MAKEOVER_INTERFACE.set(player, player.getAppearance().isMale() ? 0 : 1);
        player.openInterface(InterfaceType.MAIN, Interface.HAIRDRESSER);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 2, 0, 23, 2);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 8, 0, 24, 2);
        player.getPacketSender().sendString(82, 9, "CONFIRM - (" + (PRICE == 0 ? "Free!" : NumberUtils.formatNumber(PRICE) + " coins") + ")");
    }

    static {
        NPCAction.register(1305, "talk-to", Hairdresser::dialogue);
        NPCAction.register(1305, "haircut", ((player, npc) -> open(player, npc, true)));
        InterfaceHandler.register(Interface.HAIRDRESSER, h -> {
            h.actions[2] = (DefaultAction) (player, option, slot, itemId) -> {
                boolean haircut = Config.HAIRCUT.get(player) == 1;
                if (!haircut && !player.getAppearance().isMale()) {
                    haircut = true;
                }
                player.getPacketSender().sendVarp(261, slot);
                player.putTemporaryAttribute(haircut ? AttributeKey.SELECTED_HAIR_STYLE : AttributeKey.SELECTED_BEARD_STYLE, slot);
            };
            h.actions[8] = (DefaultAction)  (player, option, slot, itemId) -> {
                boolean haircut = Config.HAIRCUT.get(player) == 1;
                if (!haircut && !player.getAppearance().isMale()) {
                    haircut = true;
                }
                player.getPacketSender().sendVarp(263, slot);
                player.putTemporaryAttribute(haircut ? AttributeKey.SELECTED_HAIR_COLOR : AttributeKey.SELECTED_BEARD_COLOR, slot);
            };
            h.actions[9] = (SimpleAction)  (player) -> {
                boolean haircut = Config.HAIRCUT.get(player) == 1;
                if (!haircut && !player.getAppearance().isMale()) {
                    haircut = true;
                }
                val slot = player.getTemporaryAttributeIntOrZero(haircut ? AttributeKey.SELECTED_HAIR_STYLE : AttributeKey.SELECTED_BEARD_STYLE);
                val style = haircut ? HairStyle.getStyle(slot, player.getAppearance().isMale()) : BeardStyle.getStyle(slot);
                val colour = player.getTemporaryAttributeIntOrZero(haircut ? AttributeKey.SELECTED_HAIR_COLOR : AttributeKey.SELECTED_BEARD_COLOR);
                val npcId = 1305;
                if (style == -1 || slot == -1 || colour == -1) {
                    player.dialogue(new NPCDialogue(npcId, "You must select a hair style and colour first!"));
                    return;
                }
                if (!player.getInventory().contains(995, PRICE)) {
                    player.closeInterface(InterfaceType.MAIN);
                    player.dialogue(new NPCDialogue(npcId, "You don't have enough money for a haircut."));
                    return;
                }
                player.getAppearance().modifyAppearance((byte) (haircut ? 0 : 1), (short) style);
                player.getAppearance().modifyColor((byte) 0, (byte) colour);
                player.closeInterface(InterfaceType.MAIN);
                player.dialogue(new NPCDialogue(npcId, "Hope you like the new look!"));
                player.getInventory().remove(995, PRICE);
                player.getTaskManager().doLookupByUUID(435, 1); // Get a Haircut at the Barber in Falador
            };
        });
    }
}
