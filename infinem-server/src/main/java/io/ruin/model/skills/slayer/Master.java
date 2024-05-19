package io.ruin.model.skills.slayer;

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
import io.ruin.model.stat.StatType;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/18/2024
 */
@Getter
public enum Master {

    TURAEL(1, 1, 401, 1),
    MAZCHNA(1, 20, 402, 2),
    VANNAKA(1, 40, 403, 3),
    CHAELDAR(1, 70, 404, 4),
    NIEVE(1, 85, 6797, 5),
    DURADEL(50, 100, 405, 6),
    KRYSTILIA(1, 1, 7663, 7, "Yeah? What do you want?"),
    KONAR(1, 75, 8623, 8, "Bringer of death, have you come to serve the balance?"),
    STEVE(1, 85, 6798, 9);

    private final int slayerRequirement, combatRequirement, npcId, configIndex;
    private final String greeting;

    Master(int slayerRequirement, int combatRequirement, int npcId, int configIndex) {
        this(slayerRequirement, combatRequirement, npcId, configIndex, "'Ello, and what are you after then?");
    }

    Master(int slayerRequirement, int combatRequirement, int npcId, int configIndex, String greeting) {
        this.slayerRequirement = slayerRequirement;
        this.combatRequirement = combatRequirement;
        this.npcId = npcId;
        this.configIndex = configIndex;
        this.greeting = greeting;
        NPCAction.register(npcId, 1, (player, npc) -> handleInteraction(player, npc, 1));
        NPCAction.register(npcId, 2, (player, npc) -> handleInteraction(player, npc, 2));
        NPCAction.register(npcId, 3, (player, npc) -> handleInteraction(player, npc, 3));
        NPCAction.register(npcId, 4, (player, npc) -> handleInteraction(player, npc, 4));
        NPCAction.register(npcId, 5, (player, npc) -> handleInteraction(player, npc, 5));
    }

    private void assignTask(Player player) {
        SlayerMaster master = SlayerMaster.master(npcId);
        if (master == null)
            return;
        SlayerTaskDef def = master.randomTask(player);
        if (def == null)
            return;
        Config.SLAYER_MASTER.set(player, configIndex);
        Slayer.setTask(player, def.getCreatureUid());
        if (this == KONAR) KonarData.assignLocation(player, def.getCreatureUid());
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
        Slayer.setTaskAmount(player, task_amt);
    }

    private void giveTask(Player player) {
        if (player.getStats().get(StatType.Slayer).fixedLevel < slayerRequirement && player.getCombat().getLevel() < combatRequirement) {
            player.dialogue(new NPCDialogue(npcId, "Sorry, but you're not strong enough to be taught by me, you need " + (slayerRequirement > 1 ? slayerRequirement + " slayer and " : "") + combatRequirement + " combat!"));
            return;
        }
        int left = Slayer.getTaskAmount(player);
        if (left > 0 && !SlayerCreature.taskName(player, Slayer.getTask(player)).equalsIgnoreCase("null")) {
            String text = SlayerMaster.getTaskText(player, left);
            player.dialogue(new NPCDialogue(npcId, text));
            return;
        }
        assignTask(player);
        SlayerCreature task = SlayerCreature.lookup(Slayer.getTask(player));
        left = Slayer.getTaskAmount(player);
        boolean isKrystilia = this == KRYSTILIA;
        boolean isKonar = this == KONAR;
        if (task.equals(SlayerCreature.BOSSES)) {
            player.dialogue(
                    new NPCDialogue(npcId, "Your new task is to "
                            + (isKonar ? "bring balance to " : "kill ")
                            + SlayerCreature.taskName(player, task.getUid())
                            + (isKrystilia ? " in the wilderness" : "") +
                            ". How many would you like to slay?") {
                        public void closed(Player player) {
                            player.integerInput("How many would you like to slay? (3-35)", (i) -> {
                                if (i < 3)
                                    i = 3;

                                if (i > 35)
                                    i = 35;

                                Slayer.setTaskAmount(player, i);

                                player.dialogue(
                                        new NPCDialogue(npcId, "Excellent. You're now assigned to kill "
                                                + SlayerCreature.taskName(player, task.getUid())
                                                + " boss " + i + " times"
                                                + (isKrystilia ? " in the wilderness" : "")
                                                + "."),
                                        new OptionsDialogue(new Option("Got any tips for me?", () -> player.dialogue(
                                                new PlayerDialogue("Got any tips for me?"),
                                                new NPCDialogue(npcId, SlayerCreature.getTipFor(task)),
                                                new PlayerDialogue("Great, thanks!"))), new Option("Great, thanks!", () -> {
                                            player.dialogue(new PlayerDialogue("Okay, great!"));
                                        }))
                                );
                            });
                        }
                    }
            );
        } else {
            if (Config.SLAYER_POINTS.get(player) >= 30) {
                player.dialogue(
                        new NPCDialogue(npcId, "Excellent, you're doing great. Your new task is to "
                                + (isKonar ? "bring balance to " : "kill ")
                                + left + " "
                                + SlayerCreature.taskName(player, task.getUid())
                                + (isKrystilia ? " in the wilderness" : "")
                                + (isKonar ? " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName() : "")
                                + "."),
                        new OptionsDialogue(
                                new Option("Got any tips for me?", new PlayerDialogue("Got any tips for me?"), new NPCDialogue(npcId, SlayerCreature.getTipFor(task)), new PlayerDialogue("Great, thanks!")),
                                new Option("Great, thanks!", new PlayerDialogue("Okay, great!")),
                                new Option("No thanks. (Reroll task, costs 30 Slayer points)", new NPCDialogue(npcId, "Very well."), new ActionDialogue(() -> {
                                    Config.SLAYER_POINTS.set(player, Config.SLAYER_POINTS.get(player) - 30);
                                    Slayer.resetTask(player);
                                    giveTask(player);
                                }))
                        )
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npcId, "Excellent, you're doing great. Your new task is to "
                                + (isKonar ? "bring balance to " : "kill ")
                                + left + " "
                                + SlayerCreature.taskName(player, task.getUid())
                                + (isKrystilia ? " in the wilderness" : "")
                                + (isKonar ? " at the " + KonarData.TaskLocation.values()[player.slayerLocation].getName() : "")
                                + "."),
                        new OptionsDialogue(
                                new Option("Got any tips for me?", new PlayerDialogue("Got any tips for me?"), new NPCDialogue(npcId, SlayerCreature.getTipFor(task)), new PlayerDialogue("Great, thanks!")),
                                new Option("Great, thanks!", new PlayerDialogue("Okay, great!"))
                        )
                );
            }
        }
    }

    public void handleInteraction(Player player, int option) {
        handleInteraction(player, new NPC(npcId), option);
    }

    private void handleInteraction(Player player, NPC npc, int option) {
        boolean talked = player.getStats().get(StatType.Slayer).experience > 0;

        switch (option) {
            case 1:
                if (talked) {
                    player.dialogue(
                            new NPCDialogue(npcId, greeting),
                            new OptionsDialogue(new Option("I need another assignment.", () -> {
                                player.dialogue(new PlayerDialogue("I need another assignment.").action(() -> giveTask(player)));
                            }), new Option("Have you any rewards for me, or anything to trade?", () -> {
                                player.dialogue(
                                        new PlayerDialogue("Have you any rewards for me, or anything to trade?"),
                                        new NPCDialogue(npcId, "I have quite a few rewards you can earn, and a wide<br>variety of Slayer equipment for sale."),
                                        new OptionsDialogue(new Option("Look at rewards", () -> {
                                            SlayerUnlock.openRewards(player);
                                        }), new Option("Look at shop", () -> {
                                            SlayerUnlock.openShop(player, npc);
                                        }), new Option("Cancel", player::closeDialogue))
                                );
                            }), new Option("Let's talk about the difficulty of my assignments.", () -> {
                                player.dialogue(new PlayerDialogue("Let's talk about the difficulty of my assignments."));

                                if (!player.slayerCombatCheck) {
                                    player.dialogue(new NPCDialogue(npcId, "The Slayer Masters will take your combat level into<br>account when choosing tasks for you, so you shouldn't<br>get anything too hard."),
                                            new OptionsDialogue(new Option("That's fine - I don't want anything too tough.", () -> {
                                                player.dialogue(new PlayerDialogue("That's fine - I don't want anything too tough."),
                                                        new NPCDialogue(npcId, "Okay, we'll keep checking your combat level."));
                                            }), new Option("Stop checking my combat level - I can take anything!", () -> {
                                                player.slayerCombatCheck = true;
                                                player.dialogue(new PlayerDialogue("Stop checking my combat level - I can take anything!"),
                                                        new NPCDialogue(npcId, "Okay, from now on, all the Slayer Masters will assign<br>you anything from their lists, regardless of your combat<br>level."));
                                            })));
                                } else {
                                    player.dialogue(new NPCDialogue(npcId, "The Slayer Masters may currently assign you any<br>task in our lists, regardless of your combat level."),
                                            new OptionsDialogue(new Option("That's fine - I can handle any task.", () -> {
                                                player.dialogue(new PlayerDialogue("That's fine - I can handle any task."),
                                                        new NPCDialogue(npcId, "That's the spirit!"));
                                            }), new Option("In future, please don't give anything too rough.", () -> {
                                                player.slayerCombatCheck = false;
                                                player.dialogue(new PlayerDialogue("In future, please don't give anything too rough."),
                                                        new NPCDialogue(npcId, "Okay, from now on, all the Slayer Masters will take<br>your combat level into account when choosing tasks for<br>you, so you shouldn't get anything too hard."));
                                            })));
                                }
                            }), new Option("Er... Nothing...", () -> {
                                player.dialogue(new PlayerDialogue("Er... Nothing..."));
                            }))
                    );
                } else {
                    player.dialogue(new OptionsDialogue(new Option("Who are you?", () -> {
                        player.dialogue(new PlayerDialogue("Who are you?"),
                                new NPCDialogue(npcId, "I'm one of the elite Slayer Masters."),
                                new OptionsDialogue(new Option("What's a slayer?", () -> {
                                    player.dialogue(new PlayerDialogue("What's a slayer?"),
                                            new NPCDialogue(npcId, "Oh dear, what do they teach you in school?"),
                                            new PlayerDialogue("Well... er..."),
                                            new NPCDialogue(npcId, "I suppose I'll have to educate you then. A slayer is<br>someone who is trained " +
                                                    "to fight specific creatures. They<br>know these creatures' every weakness and strength. " +
                                                    "As<br>you can guess it makes killing them a lot easier."),
                                            new OptionsDialogue(new Option("Wow, can you teach me?", () -> {
                                                player.dialogue(new PlayerDialogue("Wow, can you teach me?"),
                                                        new NPCDialogue(npcId, "Hmmm well I'm not so sure..."),
                                                        new PlayerDialogue("Pleeeaasssse!"),
                                                        new NPCDialogue(npcId, "Oh okay then, you twisted my arm. You'll have to train<br>against specific groups of creatures."),
                                                        new PlayerDialogue("Okay, what's first?").action(() -> {
                                                            player.getInventory().addOrDrop(new Item(4155, 1));
                                                            assignTask(player);

                                                            SlayerCreature task = SlayerCreature.lookup(Slayer.getTask(player));

                                                            if (task != null) {
                                                                int num = Slayer.getTaskAmount(player);
                                                                player.dialogue(new NPCDialogue(npcId, "We'll start you off hunting " + SlayerCreature.taskName(player, task.getUid()) + ", you'll need to kill " + num + "<br>of them."));
                                                            }
                                                        }));
                                            }), new Option("Sounds useless to me", () -> {
                                                player.dialogue(new PlayerDialogue("Sounds useless to me."),
                                                        new NPCDialogue(npcId, "Suit yourself."));
                                            })));
                                }), new Option("Never heard of you...", () -> {
                                    player.dialogue(new PlayerDialogue("Never heard of you..."),
                                            new NPCDialogue(npcId, "That's because my foe never lives to tell of me. We<br>slayers are a dangerous bunch."));
                                })));
                    }), new Option("Er... Nothing...", () -> {
                        player.dialogue(new PlayerDialogue("Er... Nothing..."));
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
}
