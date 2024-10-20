package io.ruin.model.inter.handlers.makeover;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Style;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2023
 */
public class BodyTypeInterface {

    private static void apply(Player player) {
        boolean male = player.getTemporaryAttribute(AttributeKey.SELECTED_GENDER);
        int skinColor = player.getTemporaryAttributeIntOrZero(AttributeKey.SELECTED_SKIN_COLOR);
        int pronoun = player.getTemporaryAttributeIntOrZero("SELECTED_PRONOUN");
        if (male == player.getAppearance().isMale()
                && skinColor == player.getAppearance().colors[4]
                && pronoun == Config.PRONOUNS.get(player)) {
            player.sendMessage("You haven't changed anything yet.");
            return;
        }
        if (skinColor >= 8) {
            switch (skinColor) {
                case 8:
                    if (!player.hasAttribute("GREEN_SKIN")) {
                        player.dialogueKeepInterfaces(new MessageDialogue("You haven't unlocked that skin colour."));
                        return;
                    }
                    break;
                case 9:
                    if (!player.hasAttribute("BLACK_SKIN")) {
                        player.dialogueKeepInterfaces(new MessageDialogue("You haven't unlocked that skin colour."));
                        return;
                    }
                    break;
                case 10:
                    if (!player.hasAttribute("WHITE_SKIN")) {
                        player.dialogueKeepInterfaces(new MessageDialogue("You haven't unlocked that skin colour."));
                        return;
                    }
                    break;
                case 11:
                    if (!player.hasAttribute("BLUE_SKIN")) {
                        player.dialogueKeepInterfaces(new MessageDialogue("You haven't unlocked that skin colour."));
                        return;
                    }
                    break;
                case 12:
                    if (!player.hasAttribute("PURPLE_SKIN")) {
                        player.dialogueKeepInterfaces(new MessageDialogue("You haven't unlocked that skin colour."));
                        return;
                    }
                    break;
            }
        }
        player.getAppearance().setGender(male ? 0 : 1);
        Style.Companion.updateAll(player);
        player.getAppearance().modifyColor((byte) 4, (byte) skinColor);
        Config.PRONOUNS.set(player, pronoun);
    }

    private static void confirm(Player player) {
        apply(player);
        player.closeInterface(InterfaceType.MAIN);
    }

    public static void open(Player player) {
        player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, player.getAppearance().isMale());
        player.putTemporaryAttribute(AttributeKey.SELECTED_SKIN_COLOR, player.getAppearance().colors[4]);
        player.getPacketSender().sendVarp(261, player.getAppearance().isMale() ? 0 : 1);
        player.getPacketSender().sendVarp(262, player.getAppearance().colors[4]);
        player.getPacketSender().sendVarp(263, Config.PRONOUNS.get(player));
        Config.varpbit(4803, false).set(player, 1);
        Config.varpbit(4804, false).set(player, 1);
        Config.varpbit(6007, false).set(player, 1);
        player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
        player.getPacketSender().sendAccessMask(Interface.MAKE_OVER_MAGE, 13, 0, 12, AccessMasks.ClickOp1); // Colors
        player.getPacketSender().sendAccessMask(Interface.MAKE_OVER_MAGE, 32, 9, 24, AccessMasks.ClickOp1); // Dropdown navigation
        player.startEvent(e -> {
            while (player.isVisibleInterface(516) || player.isVisibleInterface(205)) {
                World.sendGraphics(2372, 0, 0, player.getPosition());
                e.delay(1);
            }
        });
    }

    private static void clickMakeoverType(Player player, int slot) {
        if (slot == 14) {
            BodyTypeInterface.open(player);
        } else {
            int index = (slot - 10) / 2;
            MakeoverType type = MakeoverType.values()[index];
            MakeoverInterface.open(player, type);
        }
    }

    static {
        InterfaceHandler.register(Interface.MAKE_OVER_MAGE, h -> {
            h.actions[2] = (SimpleAction) player -> {
                player.getPacketSender().sendVarp(261, 0);
                player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, true);
                if (Config.PRONOUNS.get(player) != 2) {
                    player.getPacketSender().sendVarp(263, 0);
                    player.putTemporaryAttribute("SELECTED_PRONOUN", 0);
                }
            };
            h.actions[8] = (SimpleAction) player -> {
                player.getPacketSender().sendVarp(261, 1);
                player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, false);
                if (Config.PRONOUNS.get(player) != 2) {
                    player.getPacketSender().sendVarp(263, 1);
                    player.putTemporaryAttribute("SELECTED_PRONOUN", 1);
                }
            };
            h.actions[13] = (DefaultAction)  (player, option, slot, itemId) -> {
                int value = slot == 0 ? 7 : slot >= 8 && slot <= 12 ? slot : slot - 1;
                player.getPacketSender().sendVarp(262, value);
                player.putTemporaryAttribute(AttributeKey.SELECTED_SKIN_COLOR, value);
            };
            h.actions[17] = (SimpleAction) BodyTypeInterface::apply;
            h.actions[18] = (SimpleAction) BodyTypeInterface::confirm;
            h.actions[21] = (SimpleAction) player -> {
                player.getPacketSender().sendVarp(263, 0);
                player.putTemporaryAttribute("SELECTED_PRONOUN", 0);
            };
            h.actions[24] = (SimpleAction) player -> {
                player.getPacketSender().sendVarp(263, 2);
                player.putTemporaryAttribute("SELECTED_PRONOUN", 2);
            };
            h.actions[27] = (SimpleAction) player -> {
                player.getPacketSender().sendVarp(263, 1);
                player.putTemporaryAttribute("SELECTED_PRONOUN", 1);
            };
            h.actions[32] = (SlotAction) BodyTypeInterface::clickMakeoverType;
        });
    }
}
