package io.ruin.model.content.transportation.hotairballoons;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/13/2023
 */
public class HotAirBalloonNetwork {

    static {
        for (HotAirBalloon balloon : HotAirBalloon.values()) {
            int npcId = balloon.getNpcId();
            NPCAction.register(npcId, "talk-to", ((player, npc) -> {
                if (balloon.getConfig().get(player) == 0) {
                    unlockDialogue(player, npc, balloon);
                } else {
                    flyDialogue(player, npc, balloon);
                }
            }));
            NPCAction.register(npcId, "fly", ((player, npc) -> {
                if (balloon.getConfig().get(player) == 0) {
                    unlockDialogue(player, npc, balloon);
                } else {
                    fly(player, balloon);
                }
            }));
            ObjectAction.register(balloon.getObjectId(), 1, (player, obj) -> fly(player, balloon));
        }
        LoginListener.register(player -> {
            HotAirBalloon.ENTRANA.getConfig().set(player, 1);
        });
    }

    private static void unlockDialogue(Player player, NPC npc, HotAirBalloon balloon) {
        Item unlockItem = balloon.getUnlockItem();
        player.dialogue(
                new NPCDialogue(npc, "Hello there, how can I help you?"),
                new OptionsDialogue(
                        new Option("Ask about the balloon network",
                                new PlayerDialogue("Can you tell me about the balloon network?"),
                                new NPCDialogue(npc, "Certainly! The balloon network consists of 5 locations outside of Entrana, each requiring a collection of logs to construct."),
                                new NPCDialogue(npc, "Once you have constructed the balloon at a location, you will be able to travel there for free from any other built balloon."),
                                new NPCDialogue(npc, "The Entrana balloon was our first and has already been built."),
                                new NPCDialogue(npc, "If you visit an assistant for a balloon you have already built, they can help you build other balloons without traveling there if you would like.")
                        ),
                        new Option("Construct balloon (" + unlockItem.getAmount() + "x " + unlockItem.getDef().name + ")",
                                new ActionDialogue(() -> {
                                    if (player.getInventory().getAmount(unlockItem.getId()) >= unlockItem.getAmount()) {
                                        player.dialogue(
                                                new PlayerDialogue("I would like to provide the materials to build this balloon."),
                                                new NPCDialogue(npc, "Splendid, let's get to work!"),
                                                new ActionDialogue(() -> {
                                                    player.startEvent(e -> {
                                                        player.lock();
                                                        player.getPacketSender().fadeOut();
                                                        e.delay(1);
                                                        player.getInventory().remove(unlockItem);
                                                        e.delay(3);
                                                        balloon.getConfig().set(player, 1);
                                                        e.delay(1);
                                                        player.getPacketSender().fadeIn();
                                                        e.delay(1);
                                                        checkTask(player);
                                                        player.unlock();
                                                    });
                                                })
                                        );
                                    } else {
                                        player.dialogue(
                                                new PlayerDialogue("I would like to provide the materials to build this balloon."),
                                                new NPCDialogue(npc, "Unfortunately, you do not have the required materials. We need "
                                                        + unlockItem.getAmount() + "x " + unlockItem.getDef().name + " to build this balloon.")
                                        );
                                    }
                                })
                        )
                )
        );
    }

    private static void flyDialogue(Player player, NPC npc, HotAirBalloon balloon) {
        player.dialogue(
                new NPCDialogue(npc, "Hello friend, how can I help you?"),
                new OptionsDialogue(
                        new Option("Ask about the balloon network",
                                new PlayerDialogue("Can you tell me about the balloon network?"),
                                new NPCDialogue(npc, "Certainly! The balloon network consists of 5 locations outside of Entrana, each requiring a collection of logs to construct."),
                                new NPCDialogue(npc, "Once you have constructed the balloon at a location, you will be able to travel there for free from any other built balloon."),
                                new NPCDialogue(npc, "The Entrana balloon was our first and has already been built."),
                                new NPCDialogue(npc, "If you visit an assistant for a balloon you have already built, they can help you build other balloons without traveling there if you would like.")
                        ),
                        new Option("Let's fly!", new PlayerDialogue("Let's fly!"), new ActionDialogue(() -> fly(player, balloon)))
                )
        );
    }

    private static void fly(Player player, HotAirBalloon balloon) {
        OptionScroll.open(player, "Balloon Locations", true, Arrays.stream(HotAirBalloon.values())
                .filter(e -> e != balloon)
                .map(b -> new Option((b.getConfig().get(player) > 0 ? "" : "<str>") + StringUtils.capitalizeFirst(b.name().toLowerCase().replace("_", " ")), () -> travel(player, b)))
                .toArray(Option[]::new));
    }

    private static void travel(Player player, HotAirBalloon balloon) {
        if (isBalloonUnlocked(player, balloon)) {
            Traveling.fadeTravel(player, balloon.getDestination());
        } else {
            unlockBalloon(player, balloon);
        }
    }

    private static void unlockBalloon(Player player, HotAirBalloon balloon) {
        Item unlockItem = balloon.getUnlockItem();
        if (player.getInventory().getAmount(unlockItem.getId()) >= unlockItem.getAmount()) {
            player.dialogue(
                    new MessageDialogue("Would you like to build that balloon location?<br><br>It will cost " + unlockItem.getAmount() + "x " + unlockItem.getDef().name + "."),
                    new OptionsDialogue("Build the balloon?",
                            new Option("Yes (costs " + unlockItem.getAmount() + "x " + unlockItem.getDef().name + ")",
                                    new ActionDialogue(() -> {
                                        player.startEvent(e -> {
                                            player.lock();
                                            player.getPacketSender().fadeOut();
                                            e.delay(1);
                                            player.getInventory().remove(unlockItem);
                                            e.delay(3);
                                            balloon.getConfig().set(player, 1);
                                            e.delay(1);
                                            player.getPacketSender().fadeIn();
                                            e.delay(1);
                                            checkTask(player);
                                            player.dialogue(
                                                    new OptionsDialogue("Would you like to fly to that location?",
                                                            new Option("Yes", new ActionDialogue(() -> travel(player, balloon))),
                                                            new Option("No"))
                                            );
                                            player.unlock();
                                        });
                                    })),
                            new Option("No")
                    )
            );
        } else {
            player.dialogue(new MessageDialogue("To build this balloon location would cost " + unlockItem.getAmount() + "x " + unlockItem.getDef().name + "."));
        }
    }

    private static void checkTask(Player player) {
        if (player.getTaskManager().hasCompletedTask(916))
            return;
        for (HotAirBalloon balloon : HotAirBalloon.values()) {
            if (balloon.getConfig().get(player) == 0)
                return;
        }
        player.getTaskManager().doLookupByUUID(916);    // Build All Hot Air Balloons
    }

    private static boolean isBalloonUnlocked(Player player, HotAirBalloon balloon) {
        return balloon.getConfig().get(player) > 0;
    }
}
