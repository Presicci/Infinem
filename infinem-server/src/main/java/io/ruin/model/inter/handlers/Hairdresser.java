package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Style;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.*;
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
                                    new ActionDialogue(() -> open(player, npc.getId(), true))),
                            new Option("I'd like a shave please.",
                                    new PlayerDialogue("I'd like a shave please."),
                                    new NPCDialogue(npc, "Please select the facial hair you would like from this brochure. I'll even throw in a free recolour."),
                                    new ActionDialogue(() -> open(player, npc.getId(), false))),
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
                                    new ActionDialogue(() -> open(player, npc.getId(), true))),
                            new Option("No thank you.",
                                    new PlayerDialogue("No thank you."),
                                    new NPCDialogue(npc, "Very well. Come back if you change your mind."))
                    )
            );
        }

    }

    public static void open(Player player, int npc, boolean haircut) {
        boolean male = player.getAppearance().isMale();
        if (!haircut && !male) {
            player.sendMessage("You don't have any facial hair to shave.");
            return;
        }
        int hair = Style.HAIR.getIndexById(male, player.getAppearance().styles[0]);
        int beard = Style.JAW.getIndexById(male, player.getAppearance().styles[1]);
        int color = player.getAppearance().colors[0];
        player.putTemporaryAttribute(AttributeKey.SELECTED_HAIR_STYLE, hair);
        player.putTemporaryAttribute(AttributeKey.SELECTED_BEARD_STYLE, beard);
        player.putTemporaryAttribute(AttributeKey.SELECTED_HAIR_COLOR, color);
        player.getPacketSender().sendVarp(261, haircut ? hair : beard);
        player.getPacketSender().sendVarp(263, color);
        if (haircut) {  // Order matters here for some reason, so send differently based on hair/beard
            Config.HAIRCUT.set(player, 1);
            Config.MAKEOVER_INTERFACE.set(player, male ? 0 : 1);
        } else {
            Config.MAKEOVER_INTERFACE.set(player, male ? 0 : 1);
            Config.HAIRCUT.set(player, 2);
        }
        player.putTemporaryAttribute("BARBER_NPC", npc);
        player.openInterface(InterfaceType.MAIN, Interface.HAIRDRESSER);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 2, 0, 23, 2);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 8, 0, 24, 2);
        player.getPacketSender().sendString(82, 9, "CONFIRM - (" + (PRICE == 0 || npc < 0 ? "Free!" : NumberUtils.formatNumber(PRICE) + " coins") + ")");
    }

    private static void confimDialogue(Player player, int npcId, boolean haircut) {
        if (haircut) {
            if (player.getAppearance().isMale()) {
                player.dialogue(new NPCDialogue(npcId, "Hope you like the new do!"));
            } else {
                player.dialogue(new NPCDialogue(npcId, "It really suits you!"));
            }
        } else {
            player.dialogue(new NPCDialogue(npcId, "Mmm... very distinguished!"));
        }
    }

    static {
        NPCAction.register(1305, "talk-to", Hairdresser::dialogue);
        NPCAction.register(1305, "haircut", ((player, npc) -> {
            if (player.getAppearance().isMale()) {
                player.dialogue(new OptionsDialogue("What would you like?",
                        new Option("Haircut", () -> open(player, npc.getId(), true)),
                        new Option("Shave", () -> open(player, npc.getId(), false))
                ));
            } else {
                open(player, npc.getId(), true);
            }
        }));
        NPCAction.register(12116, "talk-to", Hairdresser::dialogue);
        NPCAction.register(12116, "haircut", (player, npc) -> open(player, npc.getId(), true));
        NPCAction.register(12116, "shave", (player, npc) -> open(player, npc.getId(), false));
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
                player.putTemporaryAttribute(AttributeKey.SELECTED_HAIR_COLOR, slot);
            };
            h.actions[9] = (SimpleAction)  (player) -> {
                boolean haircut = Config.HAIRCUT.get(player) == 1;
                boolean male = player.getAppearance().isMale();
                if (!haircut && !male) {
                    haircut = true;
                }
                val slot = player.getTemporaryAttributeIntOrZero(haircut ? AttributeKey.SELECTED_HAIR_STYLE : AttributeKey.SELECTED_BEARD_STYLE);
                val style = haircut ? Style.HAIR.getIdAtIndex(male, slot) : Style.JAW.getIdAtIndex(true, slot);
                val colour = player.getTemporaryAttributeIntOrZero(AttributeKey.SELECTED_HAIR_COLOR);
                val npcId = player.getTemporaryAttributeOrDefault("BARBER_NPC", -1);
                player.removeTemporaryAttribute("BARBER_NPC");
                if (style == -1 || slot == -1 || colour == -1) {
                    if (npcId > 0) {
                        player.dialogue(new NPCDialogue(npcId, "You must select a hair style and colour first!"));
                    } else {
                        player.dialogue(new MessageDialogue("You must select a hair style and colour first!"));
                    }
                    return;
                }
                if (npcId > 0 && !player.getInventory().contains(995, PRICE)) {
                    player.closeInterface(InterfaceType.MAIN);
                    player.dialogue(new NPCDialogue(npcId, "You don't have enough money for a haircut."));
                    return;
                }
                player.getAppearance().modifyAppearance((byte) (haircut ? 0 : 1), (short) style);
                player.getAppearance().modifyColor((byte) 0, (byte) colour);
                player.closeInterface(InterfaceType.MAIN);
                if (npcId == 1305)
                    player.getTaskManager().doLookupByUUID(435, 1); // Get a Haircut at the Barber in Falador
                if (npcId > 0) {
                    player.getInventory().remove(995, PRICE);
                    confimDialogue(player, npcId, haircut);
                } else {
                    player.dialogue(new PlayerDialogue("Damn, I look good."));
                }
            };
        });
    }
}
