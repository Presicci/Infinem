package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.attributes.AttributeKey;
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
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2023
 */
public class Makeover {

    private static final int PRICE = 1000;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there! I am known as the Makeover Mage! I have spent many years researching magics that can change your physical appearance!"),
                new NPCDialogue(npc, "I can alter your physical form with my magic for " + (PRICE == 0 ? "free!" : NumberUtils.formatNumber(PRICE) + " coins.")
                        + " Would you like me to perform my magics upon you?"),
                new ActionDialogue(() -> options(player, npc))
        );
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Tell me more about this 'make-over'.",
                                new PlayerDialogue("Player: Tell me more about this 'make-over'."),
                                new NPCDialogue(npc, "Why, of course! Basically, and I will try and explain this so that you will understand it correctly,"),
                                new NPCDialogue(npc, "I use my secret magical technique to melt your body down into a puddle of its elements."),
                                new NPCDialogue(npc, "When I have broken down all trace of your body, I then rebuild it into the form I am thinking of!"),
                                new NPCDialogue(npc, "Or, you know, somewhere vaguely close enough anyway."),
                                new PlayerDialogue("Uh... that doesn't sound particularly safe to me..."),
                                new NPCDialogue(npc, "It's as safe as houses! Why, I have only had thirty-six major accidents this month!"),
                                new NPCDialogue(npc, "So what do you say? Feel like a change? There's no fee."),
                                new ActionDialogue(() -> options(player, npc))
                        ),
                        new Option("Sure, I'll have a makeover.",
                                new PlayerDialogue("Sure, I'll have a makeover."),
                                new NPCDialogue(npc, "Good choice, good choice. You wouldn't want to carry on looking like that, I'm sure!"),
                                new ActionDialogue(() -> open(player, npc))
                        ),
                        new Option("Cool amulet! Can I have one?",
                                new PlayerDialogue("Cool amulet! Can I have one?"),
                                new NPCDialogue(npc, "No problem, but please remember that the amulet I will sell you is only a copy of my own. It contains no magical powers, and as such will only cost you 100 coins."),
                                new ActionDialogue(() -> {
                                    if (player.getInventory().getAmount(995) < 100) {
                                        player.dialogue(
                                                new PlayerDialogue("Oh, I don't have enough money for that."),
                                                new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                new ActionDialogue(() -> options(player, npc))
                                        );
                                    } else {
                                        if (!player.getInventory().hasFreeSlots(1) && player.getInventory().getAmount(995) != 100) {
                                            player.dialogue(
                                                    new PlayerDialogue("I don't have room to hold it. Maybe another time."),
                                                    new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                    new ActionDialogue(() -> options(player, npc))
                                            );
                                        } else {
                                            player.dialogue(
                                                    new OptionsDialogue(
                                                            new Option("Sure, here you go.",
                                                                    new PlayerDialogue("Sure, here you go."),
                                                                    new ActionDialogue(() -> {
                                                                        player.getInventory().remove(995, 100);
                                                                        player.getInventory().add(Items.YIN_YANG_AMULET);
                                                                        player.dialogue(
                                                                                new ItemDialogue().one(Items.YIN_YANG_AMULET, "You receive an amulet in exchange for 100 coins."),
                                                                                new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                                                new ActionDialogue(() -> options(player, npc))
                                                                        );
                                                                    })
                                                            ),
                                                            new Option("No way! That's far too expensive.",
                                                                    new PlayerDialogue("No way! That's far too expensive."),
                                                                    new NPCDialogue(npc, "That's fair enough, my jewellery is not to everyone's taste."),
                                                                    new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                                    new ActionDialogue(() -> options(player, npc))

                                                            )
                                                    )
                                            );
                                        }
                                    }
                                })
                        ),
                        new Option("No thanks.",
                                new PlayerDialogue("No thanks."),
                                new NPCDialogue(npc, "Ehhh... suit yourself.")
                        )
                )
        );
    }

    private static void open(Player player, NPC npc) {
        player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, player.getAppearance().isMale());
        player.putTemporaryAttribute(AttributeKey.SELECTED_SKIN_COLOR, player.getAppearance().colors[4]);
        player.getPacketSender().sendVarp(261, player.getAppearance().isMale() ? 0 : 1);
        player.getPacketSender().sendVarp(263, player.getAppearance().colors[4]);
        Config.varpbit(4803, false).set(player, 1);
        Config.varpbit(4804, false).set(player, 1);
        Config.varpbit(6007, false).set(player, 1);
        player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
        player.getPacketSender().sendAccessMask(Interface.MAKE_OVER_MAGE, 9, 0, 12, AccessMasks.ClickOp1);
        player.getPacketSender().sendString(Interface.MAKE_OVER_MAGE, 10, "CONFIRM - (" + (PRICE == 0 ? "Free!" : NumberUtils.formatNumber(PRICE) + " coins") + ")");
    }

    static {
        NPCAction.register(1307, "talk-to", Makeover::dialogue);
        NPCAction.register(1307, "makeover", Makeover::open);
        NPCAction.register(1306, "talk-to", Makeover::dialogue);
        NPCAction.register(1306, "makeover", Makeover::open);

        SpawnListener.register(1307, (npc -> {
            World.startEvent(e -> {
                while (true) {
                    e.delay(17);
                    npc.forceText(Random.rollDie(2, 1) ? "Ahah!" : "Ooh!");
                    npc.graphics(86);
                    npc.transform(npc.getId() == 1306 ? 1307 : 1306);
                }
            });
        }));


        InterfaceHandler.register(Interface.MAKE_OVER_MAGE, h -> {
            h.actions[2] = (SimpleAction) (player) -> {
                player.getPacketSender().sendVarp(261, 0);
                player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, true);
            };
            h.actions[6] = (SimpleAction) (player) -> {
                player.getPacketSender().sendVarp(261, 1);
                player.putTemporaryAttribute(AttributeKey.SELECTED_GENDER, false);
            };
            h.actions[9] = (DefaultAction)  (player, option, slot, itemId) -> {
                int value = slot == 0 ? 7 : slot >= 8 && slot <= 12 ? slot : slot - 1;
                player.getPacketSender().sendVarp(262, value);
                player.putTemporaryAttribute(AttributeKey.SELECTED_SKIN_COLOR, value);
            };
            h.actions[10] = (SimpleAction) (player) -> {
                boolean male = player.getTemporaryAttribute(AttributeKey.SELECTED_GENDER);
                int skinColor = player.getTemporaryAttributeIntOrZero(AttributeKey.SELECTED_SKIN_COLOR);
                int npcId = 1305;
                if (male == player.getAppearance().isMale() && skinColor == player.getAppearance().colors[4]) {
                    player.sendMessage("You haven't changed anything yet.");
                    return;
                }
                if (!player.getInventory().contains(995, PRICE)) {
                    player.closeInterface(InterfaceType.MAIN);
                    player.dialogue(new NPCDialogue(npcId, "You don't have enough money for a haircut."));
                    return;
                }
                player.getAppearance().setGender(male ? 0 : 1);
                Style.Companion.updateAll(player);
                player.getAppearance().modifyColor((byte) 4, (byte) skinColor);
                player.getInventory().remove(995, PRICE);
                player.closeInterface(InterfaceType.MAIN);
            };
        });
    }
}
