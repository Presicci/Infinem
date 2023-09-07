package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Style;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/16/2023
 */
public class ThessaliaMakeover {

    private static final int PRICE = 250;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Do you want to buy any fine clothes?"),
                new OptionsDialogue(
                        new Option("What have you got?",
                                new PlayerDialogue("What have you got?"),
                                new NPCDialogue(npc, "Well, I have a number of fine pieces of clothing on sale or, if you prefer, I can offer you an exclusive, total-clothing makeover?"),
                                new OptionsDialogue(
                                        new Option("Tell me more about this makeover.",
                                                new PlayerDialogue("Tell me more about this makeover."),
                                                new NPCDialogue(npc, "Certainly!"),
                                                new NPCDialogue(npc, "Here at Thessalia's fine clothing boutique, we offer a unique service where we will totally revamp your outfit to your choosing."),
                                                new NPCDialogue(npc, (PRICE == 0 ? "It's on the house, completely free!" : "It'll cost you " + PRICE + " coins.") + " Tired of always wearing the same old outfit, day in, day out? This is the service for you!"),
                                                new NPCDialogue(npc, "So what do you say? Interested? We can change either your top or your legwear!"),
                                                new OptionsDialogue(
                                                        new Option("I'd like to change my top please.", () -> open(player, npc, true)),
                                                        new Option("I'd like to change my legwear please.", () -> open(player, npc, false))
                                                )
                                        ),
                                        new Option("I'd just like to buy some clothes.", () -> npc.openShop(player)),
                                        new Option("No, thank you.", new PlayerDialogue("No, thank you."), new NPCDialogue(npc, "Well, please return if you change your mind."))
                                )
                        ),
                        new Option("No, thank you.", new PlayerDialogue("No, thank you."), new NPCDialogue(npc, "Well, please return if you change your mind."))
                )
        );
    }

    private static void randomConfirmationDialogue(Player player, int npcId) {
        switch (Random.get(6)) {
            case 1:
                player.dialogue(
                        new NPCDialogue(npcId, "Wow! Good choice! You're looking great!")
                );
                break;
            case 2:
                player.dialogue(
                        new NPCDialogue(npcId, "Totally cool! That outfit looks great!\n")
                );
                break;
            case 3:
                player.dialogue(
                        new NPCDialogue(npcId, "Woah! Fabulous! You look absolutely great!")
                );
                break;
            case 4:
                player.dialogue(
                        new NPCDialogue(npcId, "WoooOOOooo! Hot stuff!")
                );
                break;
            case 5:
                player.dialogue(
                        new NPCDialogue(npcId, "Fantastic! You should be a model!")
                );
                break;
            default:
                player.dialogue(
                        new NPCDialogue(npcId, "Very stylish! It really suits you!")
                );
                break;
        }
    }

    private static void open(Player player, NPC npc, boolean topwear) {
        boolean male = player.getAppearance().isMale();
        if (topwear) {
            int arm = Style.ARMS.getIndexById(male, player.getAppearance().styles[3]);
            int body = Style.TORSO.getIndexById(male, player.getAppearance().styles[2]);
            int bodyC = player.getAppearance().colors[1];
            player.putTemporaryAttribute(AttributeKey.SELECTED_ARM_STYLE, arm);
            player.putTemporaryAttribute(AttributeKey.SELECTED_BODY_STYLE, body);
            player.putTemporaryAttribute(AttributeKey.SELECTED_BODY_COLOR, bodyC);
            player.getPacketSender().sendVarp(261, body);
            player.getPacketSender().sendVarp(262, arm);
            player.getPacketSender().sendVarp(263, bodyC);
        } else {
            int leg = Style.LEGS.getIndexById(male, player.getAppearance().styles[5]);
            int legC = player.getAppearance().colors[2];
            player.putTemporaryAttribute(AttributeKey.SELECTED_LEG_STYLE, leg);
            player.putTemporaryAttribute(AttributeKey.SELECTED_LEG_COLOR, legC);
            player.getPacketSender().sendVarp(261, leg);
            player.getPacketSender().sendVarp(263, legC);
        }
        Config.MAKEOVER_INTERFACE.set(player, topwear ? 0 : 1);
        Config.THESSALIA_GENDER.set(player, male ? 0 : 1);
        player.openInterface(InterfaceType.MAIN, Interface.THESSALIA_MAKEOVER);
        if (topwear) {
            player.getPacketSender().sendAccessMask(Interface.THESSALIA_MAKEOVER, 3, 0, 13, AccessMasks.ClickOp1);
            player.getPacketSender().sendAccessMask(Interface.THESSALIA_MAKEOVER, 5, 0, 11, AccessMasks.ClickOp1);
        } else {
            player.getPacketSender().sendAccessMask(Interface.THESSALIA_MAKEOVER, 7, 0, male ? 10 : 14, AccessMasks.ClickOp1);
        }
        player.getPacketSender().sendAccessMask(Interface.THESSALIA_MAKEOVER, 13, 0, 28, AccessMasks.ClickOp1);
        player.getPacketSender().sendString(Interface.THESSALIA_MAKEOVER, 14, "CONFIRM - (" + (PRICE == 0 ? "Free!" : NumberUtils.formatNumber(PRICE) + " coins") + ")");
    }

    static {
        NPCAction.registerIncludeVariants(534, "talk-to", ThessaliaMakeover::dialogue);
        NPCAction.registerIncludeVariants(534, "makeover", ((player, npc) -> player.dialogue(new OptionsDialogue("What would you like to change?",
                new Option("Topwear",  () -> open(player, npc, true)),
                new Option("Legwear", () -> open(player, npc, false))
        ))));

        InterfaceHandler.register(Interface.THESSALIA_MAKEOVER, h -> {
            h.actions[3] = (DefaultAction)  (player, option, slot, itemId) -> {
                player.getPacketSender().sendVarp(261, slot);
                player.putTemporaryAttribute(AttributeKey.SELECTED_BODY_STYLE, slot);
            };
            h.actions[5] = (DefaultAction)  (player, option, slot, itemId) -> {
                player.getPacketSender().sendVarp(262, slot);
                player.putTemporaryAttribute(AttributeKey.SELECTED_ARM_STYLE, slot);
            };
            h.actions[7] = (DefaultAction)  (player, option, slot, itemId) -> {
                player.getPacketSender().sendVarp(261, slot);
                player.putTemporaryAttribute(AttributeKey.SELECTED_LEG_STYLE, slot);
            };
            h.actions[13] = (DefaultAction)  (player, option, slot, itemId) -> {
                boolean topwear = Config.MAKEOVER_INTERFACE.get(player) == 0;
                player.getPacketSender().sendVarp(263, slot);
                player.putTemporaryAttribute(topwear ? AttributeKey.SELECTED_BODY_COLOR : AttributeKey.SELECTED_LEG_COLOR, slot);
            };
            h.actions[14] = (DefaultAction)  (player, option, slot, itemId) -> {
                boolean topwear = Config.MAKEOVER_INTERFACE.get(player) == 0;
                int style = player.getTemporaryAttributeIntOrZero(topwear ? AttributeKey.SELECTED_BODY_STYLE : AttributeKey.SELECTED_LEG_STYLE);
                int armStyle = player.getTemporaryAttributeIntOrZero(AttributeKey.SELECTED_ARM_STYLE);
                int colour = player.getTemporaryAttributeIntOrZero(topwear ? AttributeKey.SELECTED_BODY_COLOR : AttributeKey.SELECTED_LEG_COLOR);
                if (((style == -1 || colour == -1 || armStyle == -1) && topwear) || (style == -1 || colour == -1)) {
                    player.sendMessage("You must make a selection from " + (topwear ? "three" : "two") + " categories.");
                    return;
                }
                if (!player.getInventory().contains(995, PRICE)) {
                    player.closeInterface(InterfaceType.MAIN);
                    player.dialogue(new NPCDialogue(534, "You don't have enough money for a makeover."));
                    return;
                }
                boolean male = player.getAppearance().isMale();
                if (topwear) {
                    player.getAppearance().modifyAppearance((byte) 3, (short) Style.ARMS.getIdAtIndex(male, armStyle));
                }
                player.getAppearance().modifyAppearance((byte) (topwear ? 2 : 5), (short) (topwear ? Style.TORSO.getIdAtIndex(male, style) : Style.LEGS.getIdAtIndex(male, style)));
                player.getAppearance().modifyColor((byte) (topwear ? 1 : 2), (byte) colour);
                player.getInventory().remove(995, PRICE);
                player.closeInterface(InterfaceType.MAIN);
                player.getTaskManager().doLookupByUUID(908, 1); // Change Clothes at Thessalia's Makeovers in Varrock
                randomConfirmationDialogue(player, 534);
            };
        });
    }
}
