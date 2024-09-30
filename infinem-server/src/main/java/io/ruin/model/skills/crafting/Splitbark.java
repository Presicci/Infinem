package io.ruin.model.skills.crafting;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/22/2024
 */
@AllArgsConstructor
public enum Splitbark {
    BOOTS(Items.SPLITBARK_BOOTS, 1, 1000, 60),
    GAUNTLETS(Items.SPLITBARK_GAUNTLETS, 1, 1000, 60),
    HELM(Items.SPLITBARK_HELM, 2, 6000, 61),
    LEGS(Items.SPLITBARK_LEGS, 3, 32000, 62),
    BODY(Items.SPLITBARK_BODY, 4, 37000, 62);

    private final int productId, resourceCost, coinCost, levelRequirement;

    private void jalarastCraft(Player player) {
        craft(player, 1, false);
    }

    private void craft(Player player, int amt, boolean crafting) {
        if (crafting && !player.getStats().check(StatType.Crafting, levelRequirement, "craft that")) {
            return;
        }
        if (!crafting && player.getInventory().getAmount(995) < coinCost) {
            player.dialogue(new NPCDialogue(3232, "I'll need " + NumberUtils.formatNumber(coinCost) + " coins to make that for you."));
            return;
        }
        if (player.getInventory().getAmount(Items.FINE_CLOTH) < resourceCost || player.getInventory().getAmount(Items.BARK) < resourceCost) {
            player.sendMessage("You need " + resourceCost + " fine cloth and bark to craft that.");
            return;
        }
        if (crafting && (!player.getInventory().hasId(Items.NEEDLE) || !player.getInventory().hasId(Items.THREAD))) {
            player.sendMessage("You need a needle and thread to craft splitbark armour.");
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(e -> {
            int amount = amt;
            int prodCount = 0;
            while (amount-- > 0) {
                if (player.getInventory().getAmount(Items.FINE_CLOTH) < resourceCost || player.getInventory().getAmount(Items.BARK) < resourceCost) {
                    player.sendMessage("You've run out of materials.");
                    break;
                }
                if (crafting && (!player.getInventory().hasId(Items.NEEDLE) || !player.getInventory().hasId(Items.THREAD))) {
                    player.sendMessage("You've run out of thread.");
                    break;
                }
                player.getInventory().remove(Items.FINE_CLOTH, resourceCost);
                player.getInventory().remove(Items.BARK, resourceCost);
                player.getInventory().add(productId, 1);
                if (crafting) {
                    player.animate(1249);
                    player.getStats().addXp(StatType.Crafting, 62 * resourceCost, true);
                    if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                        player.sendFilteredMessage("You make " + ItemDefinition.get(productId).descriptiveName + ".");
                        e.delay(2);
                    }
                    if (ProductionMaster.roll(player))
                        prodCount++;
                } else {
                    player.getInventory().remove(995, coinCost);
                    player.dialogue(new NPCDialogue(3232, "There you go, enjoy your new armour!"), new ActionDialogue(() -> jalarastMake(player)));
                    break;
                }
            }
            ProductionMaster.extra(player, prodCount, productId, StatType.Crafting, 62 * resourceCost * prodCount);
        });
    }

    private static final List<SkillItem> SKILL_ITEMS = new ArrayList<>();

    private static void make(Player player) {
        if (player.getInventory().getAmount(Items.FINE_CLOTH) < 1 || player.getInventory().getAmount(Items.BARK) < 1) {
            player.sendMessage("You need fine cloth and bark to craft that.");
            return;
        }
        if (!player.getInventory().hasId(Items.NEEDLE) || !player.getInventory().hasId(Items.THREAD)) {
            player.sendMessage("You need a needle and thread to craft splitbark armour.");
            return;
        }
        SkillDialogue.make(player, SKILL_ITEMS.toArray(new SkillItem[0]));
    }

    private static void jalarastMake(Player player) {
        player.dialogue(
                new OptionsDialogue("What would you like me to make?",
                        new Option("Buy Helm", () -> HELM.jalarastCraft(player)),
                        new Option("Buy Body", () -> BODY.jalarastCraft(player)),
                        new Option("Buy Legs", () -> LEGS.jalarastCraft(player)),
                        new Option("Buy Gauntlets", () -> GAUNTLETS.jalarastCraft(player)),
                        new Option("Buy Boots", () -> BOOTS.jalarastCraft(player))
                )
        );
    }

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there, can I help you?"),
                new OptionsDialogue(
                        new Option("How much for you to make me splitbark armour?",
                                new NPCDialogue(npc, "I'll charge you 1,000 coins for either gloves or boots, 6,000 coins for a hat, 32,000 coins for leggings, and 37,000 for a top."),
                                new ActionDialogue(() -> jalarastMake(player))
                        ),
                        new Option("Can you make me some splitbark armour?", () -> jalarastMake(player))
                )
        );
    }

    static {
        for (Splitbark splitbark : values()) {
            SKILL_ITEMS.add(new SkillItem(splitbark.productId).addAction((player, integer, event) -> splitbark.craft(player, integer, true)));
        }
        ItemItemAction.register(Items.NEEDLE, Items.FINE_CLOTH, (player, primary, secondary) -> make(player));
        ItemItemAction.register(Items.NEEDLE, Items.BARK, (player, primary, secondary) -> make(player));
        NPCAction.register(3232, "talk-to", Splitbark::dialogue);
        NPCAction.register(3232, "trade", (player, npc) -> jalarastMake(player));
    }
}
