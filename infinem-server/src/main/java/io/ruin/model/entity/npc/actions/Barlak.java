package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/24/2024
 */
public class Barlak {

    @AllArgsConstructor
    private enum Bone {
        LONG_BONE(Items.LONG_BONE, 1000, StatType.Construction, 4500),
        CURVED_BONE(Items.CURVED_BONE, 2000, StatType.Construction, 6750),
        SNAIL_SHELL(Items.SNAIL_SHELL, 600, null, 0),
        PERFECT_SNAIL_SHELL(Items.PERFECT_SNAIL_SHELL, 600, StatType.Crafting, 500),
        TORTOISE_SHELL(Items.TORTOISE_SHELL, 600, null, 0),
        PERFECT_SHELL(Items.PERFECT_SHELL, 600, StatType.Crafting, 500);

        private final int itemId, coins;
        private final StatType experienceType;
        private final double experience;
    }

    public static void dialogue(Player player, NPC npc) {
        if (player.getInventory().hasAtLeastOneOf(Items.LONG_BONE, Items.CURVED_BONE, Items.SNAIL_SHELL, Items.PERFECT_SNAIL_SHELL, Items.TORTOISE_SHELL, Items.PERFECT_SHELL)) {
            List<Item> items = new ArrayList<>();
            int coins = 0;
            double conExperience = 0;
            double craftExperience = 0;
            for (Bone bone : Bone.values()) {
                int amt = player.getInventory().getAmount(bone.itemId);
                if (amt <= 0) continue;
                items.addAll(player.getInventory().collectItems(bone.itemId));
                coins += (amt * bone.coins);
                if (bone.experienceType == StatType.Construction) {
                    conExperience += (amt * bone.experience);
                } else if (bone.experienceType == StatType.Crafting) {
                    craftExperience += (amt * bone.experience);
                }
            }
            boolean bones = conExperience > 0;
            int finalCoins = coins;
            double finalCraftExperience = craftExperience;
            double finalConExperience = conExperience;
            player.dialogue(
                    new NPCDialogue(npc, "Those " + (bones ? "bones" : "shells") + "! Those are exactly the sort of thing I need! Will you sell them?"),
                    new NPCDialogue(npc, "I'll give you " + NumberUtils.formatNumber(coins) + " gp for what you're carrying."),
                    new NPCDialogue(npc, (
                            conExperience > 0 ? "I'll try to teach you something about Construction as well, but it's highly technical so you won't understand if you don't already have level 30 Construction."
                            : craftExperience > 0 ? "I'll try to give you some advice on Crafting while I'm at it."
                                    : "What do you say?")),
                    new OptionsDialogue(
                            new Option("Okay.",
                                    new PlayerDialogue("Okay"),
                                    new NPCDialogue(npc, "Thanks! Here you are."),
                                    new ActionDialogue(() -> {
                                        items.forEach(Item::remove);
                                        player.getInventory().add(995, finalCoins);
                                        if (finalConExperience > 0 && player.getStats().get(StatType.Construction).fixedLevel >= 30) {
                                            player.getStats().addXp(StatType.Construction, finalConExperience, true);
                                        }
                                        if (finalCraftExperience > 0) {
                                            player.getStats().addXp(StatType.Crafting, finalCraftExperience, true);
                                        }
                                    })),
                            new Option("No. I'll keep the " + (bones ? "bones" : "shells") + ".", new PlayerDialogue("No thanks."))
                    )
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Bones!"),
                    new PlayerDialogue("What?"),
                    new NPCDialogue(npc, "I don't have any bones!"),
                    new ActionDialogue(() -> options(player, npc))
            );
        }
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Then how do you stand up?",
                                new PlayerDialogue("Then how do you stand up?"),
                                new NPCDialogue(npc, "What?"),
                                new PlayerDialogue("How do you stand up if you have no bones? Shouldn't you collapse into a gelatinous blob?"),
                                new NPCDialogue(npc, "Ha, ha, ha, ha, ha!"),
                                new ActionDialogue(() -> options(player, npc))
                        ),
                        new Option("What do you need bones for?",
                                new PlayerDialogue("What do you need bones for?"),
                                new NPCDialogue(npc, "To stand up properly. Otherwise I'd collapse into a gelatinous blob!"),
                                new NPCDialogue(npc, "Ha, ha, ha, ha, ha!"),
                                new NPCDialogue(npc, "No, seriously, I need bones to use as a Construction material. We always need big bones to prop up the mine shafts and to make other temporary structures."),
                                new ActionDialogue(() -> options(player, npc))
                        ),
                        new Option("Will you buy anything besides bones?",
                                new PlayerDialogue("Will you buy anything besides bones?"),
                                new NPCDialogue(npc, "Well, I've had a few people bring me some interesting giant shells. They say they came from giant snails and giant tortoises."),
                                new NPCDialogue(npc, "Of course, like the bones, some shells are better than others. I'll give you 250gp for ordinary shells, but what I really need are perfect shells. I'll pay double for them and give you a few tips about Crafting too."),
                                new ActionDialogue(() -> options(player, npc))
                        ),
                        new Option("What kind of bones do you need?",
                                new PlayerDialogue("What kind of bones do you need?"),
                                new NPCDialogue(npc, "Enormous bones, as big as you can get. Not just any big bones will do."),
                                new NPCDialogue(npc, "Sometimes you might find a particularly long, straight bone. That's the kind of thing I need; I'll give you 1,000gp for one of them."),
                                new NPCDialogue(npc, "Occasionally, you might find a long curved bone - these are especially valuable - I'll give you 2,000gp for them."),
                                new NPCDialogue(npc, "I'll also teach you a bit about how we use the bones in Construction, if you like."),
                                new ActionDialogue(() -> options(player, npc))
                        )
                )
        );
    }

    static {
        NPCAction.register(2344, "talk-to", Barlak::dialogue);
    }
}
