package io.ruin.model.skills.slayer.master;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.Tuple;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.slayer.*;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class Konar {
    public static final int KONAR = 8623;

    private static void assignTask(Player player) {
        SlayerMaster master = SlayerMaster.master(KONAR);

        if (master == null)
            return;

        SlayerTaskDef def = master.randomTask(player);

        if (def == null)
            return;

        Config.SLAYER_MASTER.set(player, SlayerMaster.KONAR_ID);
        Config.SLAYER_TASK_1.set(player, def.getCreatureUid());

        KonarData.assignLocation(player, def.getCreatureUid());

        int min = def.getMin();
        int max = def.getMax();
        // Handle task extensions
        for (Tuple<Integer, Config> creature : SlayerUnlock.multipliable) {
            if (creature.first() == def.getCreatureUid()) {
                if (creature.second().get(player) != 0) {
                    min = def.getMinExtended();
                    max = def.getMaxExtended();
                    break;
                }
            }
        }
        int task_amt = Random.get(min, max);

        Config.SLAYER_TASK_AMOUNT.set(player, task_amt);
        player.slayerTaskRemaining = Config.SLAYER_TASK_AMOUNT.get(player);
    }

    public static void handleInteraction(Player player, NPC npc, int option) {
        boolean talked = player.getStats().get(StatType.Slayer).experience > 0;

        switch (option) {
            case 1:
                if (talked) {
                    player.dialogue(
                            new NPCDialogue(KONAR, "Bringer of death, have you come to serve the balance?"),
                            new OptionsDialogue(new Option("I need another assignment.", () -> {
                                player.dialogue(new PlayerDialogue("I need another assignment.").action(() -> giveTask(player)));
                            }), new Option("Have you any rewards for me, or anything to trade?", () -> {
                                player.dialogue(
                                        new PlayerDialogue("Have you any rewards for me, or anything to trade?"),
                                        new NPCDialogue(KONAR, "I have quite a few rewards you can earn, and a wide<br>variety of Slayer equipment for sale."),
                                        new OptionsDialogue(new Option("Look at rewards", () -> {
                                            SlayerUnlock.openRewards(player);
                                        }), new Option("Look at shop", () -> {
                                            SlayerUnlock.openShop(player, npc);
                                        }), new Option("Cancel", player::closeDialogue))
                                );
                            }), new Option("Let's talk about the difficulty of my assignments.", () -> {
                                player.dialogue(new PlayerDialogue("Let's talk about the difficulty of my assignments."));

                                if (!player.slayerCombatCheck) {
                                    player.dialogue(new NPCDialogue(KONAR, "The Slayer Masters will take your combat level into<br>account when choosing tasks for you, so you shouldn't<br>get anything too hard."),
                                            new OptionsDialogue(new Option("That's fine - I don't want anything too tough.", () -> {
                                                player.dialogue(new PlayerDialogue("That's fine - I don't want anything too tough."),
                                                        new NPCDialogue(KONAR, "Okay, we'll keep checking your combat level."));
                                            }), new Option("Stop checking my combat level - I can take anything!", () -> {
                                                player.slayerCombatCheck = true;
                                                player.dialogue(new PlayerDialogue("Stop checking my combat level - I can take anything!"),
                                                        new NPCDialogue(KONAR, "Okay, from now on, all the Slayer Masters will assign<br>you anything from their lists, regardless of your combat<br>level."));
                                            })));
                                } else {
                                    player.dialogue(new NPCDialogue(KONAR, "The Slayer Masters may currently assign you any<br>task in our lists, regardless of your combat level."),
                                            new OptionsDialogue(new Option("That's fine - I can handle any task.", () -> {
                                                player.dialogue(new PlayerDialogue("That's fine - I can handle any task."),
                                                        new NPCDialogue(KONAR, "That's the spirit!"));
                                            }), new Option("In future, please don't give anything too rough.", () -> {
                                                player.slayerCombatCheck = false;
                                                player.dialogue(new PlayerDialogue("In future, please don't give anything too rough."),
                                                        new NPCDialogue(KONAR, "Okay, from now on, all the Slayer Masters will take<br>your combat level into account when choosing tasks for<br>you, so you shouldn't get anything too hard."));
                                            })));
                                }
                            }), new Option("I'd rather not.", () -> player.dialogue(new PlayerDialogue("I'd rather not."))))
                    );
                } else {
                    player.dialogue(new OptionsDialogue(new Option("Who are you?", () -> {
                        player.dialogue(new PlayerDialogue("Who are you?"),
                                new NPCDialogue(KONAR, "I'm one of the elite Slayer Masters."),
                                new OptionsDialogue(new Option("What's a slayer?", () -> {
                                    player.dialogue(new PlayerDialogue("What's a slayer?"),
                                            new NPCDialogue(KONAR, "Oh dear, what do they teach you in school?"),
                                            new PlayerDialogue("Well... er..."),
                                            new NPCDialogue(KONAR, "I suppose I'll have to educate you then. A slayer is<br>someone who is trained " +
                                                    "to fight specific creatures. They<br>know these creatures' every weakness and strength. " +
                                                    "As<br>you can guess it makes killing them a lot easier."),
                                            new OptionsDialogue(new Option("Wow, can you teach me?", () -> {
                                                player.dialogue(new PlayerDialogue("Wow, can you teach me?"),
                                                        new NPCDialogue(KONAR, "Hmmm well I'm not so sure..."),
                                                        new PlayerDialogue("Pleeeaasssse!"),
                                                        new NPCDialogue(KONAR, "Oh okay then, you twisted my arm. You'll have to train<br>against specific groups of creatures."),
                                                        new PlayerDialogue("Okay, what's first?").action(() -> {
                                                            player.getInventory().addOrDrop(new Item(4155, 1));
                                                            assignTask(player);

                                                            SlayerCreature task = SlayerCreature.lookup(Config.SLAYER_TASK_1.get(player));

                                                            if (task != null) {
                                                                int num = Config.SLAYER_TASK_AMOUNT.get(player);
                                                                player.dialogue(new NPCDialogue(KONAR, "We'll start you off hunting " + SlayerCreature.taskName(player, task.getUid()) + ", you'll need to kill " + num + "<br>of them."));
                                                            }
                                                        }));
                                            }), new Option("Sounds useless to me", () -> {
                                                player.dialogue(new PlayerDialogue("Sounds useless to me."),
                                                        new NPCDialogue(KONAR, "Suit yourself."));
                                            })));
                                }), new Option("Never heard of you...", () -> {
                                    player.dialogue(new PlayerDialogue("Never heard of you..."),
                                            new NPCDialogue(KONAR, "That's because my foe never lives to tell of me. We<br>slayers are a dangerous bunch."));
                                })));
                    }), new Option("I'd rather not.", () -> {
                        player.dialogue(new PlayerDialogue("I'd rather not."));
                    })));
                }
                break;
            case 3:
                giveTask(player);
                break;
            case 4:
                SlayerUnlock.openShop(player, npc);
                break;
            case 5:
                SlayerUnlock.openRewards(player);
                break;
        }
    }

    private static void giveTask(Player player) {
        if (player.getCombat().getLevel() < 75) {
            player.dialogue(new NPCDialogue(KONAR, "Sorry, but you're not strong enough to be taught by me, you need 75 combat!"));
            return;
        }

        int left = Config.SLAYER_TASK_AMOUNT.get(player);

        if (left > 0 && SlayerCreature.taskName(player, Config.SLAYER_TASK_1.get(player)).equalsIgnoreCase("null")) {
            String text = SlayerMaster.getTaskText(player, left);
            player.dialogue(new NPCDialogue(KONAR, text));
            return;
        }

        assignTask(player);

        SlayerCreature task = SlayerCreature.lookup(Config.SLAYER_TASK_1.get(player));
        left = Config.SLAYER_TASK_AMOUNT.get(player);
        String location = KonarData.TaskLocation.values()[player.slayerLocation].getName();

        if (task.equals(SlayerCreature.BOSSES)) {
            player.dialogue(
                    new NPCDialogue(KONAR, "Your new task is to bring balance to " + SlayerCreature.taskName(player, task.getUid()) +
                            ". How many would you like to slay?"),
                    new ActionDialogue(() -> {
                        player.integerInput("How many would you like to slay? (3-35)", (i) -> {
                            if (i < 3)
                                i = 3;

                            if (i > 35)
                                i = 35;

                            Config.SLAYER_TASK_AMOUNT.set(player, i);
                            player.slayerTaskRemaining = Config.SLAYER_TASK_AMOUNT.get(player);

                            player.dialogue(
                                    new NPCDialogue(KONAR, "Excellent. You're now assigned to bring balance to " + SlayerCreature.taskName(player, task.getUid()) + " boss " + i + " times."),
                                    new OptionsDialogue(new Option("Got any tips for me?", () -> player.dialogue(
                                            new PlayerDialogue("Got any tips for me?"),
                                            new NPCDialogue(KONAR, SlayerCreature.getTipFor(task)),
                                            new PlayerDialogue("Great, thanks!"))), new Option("Great, thanks!", () -> {
                                        player.dialogue(new PlayerDialogue("Okay, great!"));
                                    }))
                            );
                        });
                    })
            );
        } else {
            player.dialogue(
                    new NPCDialogue(KONAR, "You are to bring balance to " + left + " " + SlayerCreature.taskName(player, task.getUid()) + " at the " + location + "."),
                    new OptionsDialogue(new Option("Got any tips for me?", () -> player.dialogue(
                            new PlayerDialogue("Got any tips for me?"),
                            new NPCDialogue(KONAR, SlayerCreature.getTipFor(task)),
                            new PlayerDialogue("Great, thanks!"))), new Option("Great, thanks!", () -> {
                        player.dialogue(new PlayerDialogue("Okay, great!"));
                    }))
            );
        }
    }

    static {
        NPCAction.register(KONAR, 1, (player, npc) -> handleInteraction(player, npc, 1));
        NPCAction.register(KONAR, 2, (player, npc) -> handleInteraction(player, npc, 2));
        NPCAction.register(KONAR, 3, (player, npc) -> handleInteraction(player, npc, 3));
        NPCAction.register(KONAR, 4, (player, npc) -> handleInteraction(player, npc, 4));
        NPCAction.register(KONAR, 5, (player, npc) -> handleInteraction(player, npc, 5));
    }
}
