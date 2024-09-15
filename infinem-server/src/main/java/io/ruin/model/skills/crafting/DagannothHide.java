package io.ruin.model.skills.crafting;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.scroll.DiaryScroll;
import io.ruin.model.content.tasksystem.areas.AreaTaskTier;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/13/2024
 */
@AllArgsConstructor
public enum DagannothHide {
    ROCKSHELL_HELM(1, Items.ROCKSHELL_CHUNK, 5000, Items.ROCKSHELL_HELM),
    ROCKSHELL_PLATE(3, Items.ROCKSHELL_SHARD, 10000, Items.ROCKSHELL_PLATE),
    ROCKSHELL_LEGS(2, Items.ROCKSHELL_SPLINTER, 7500, Items.ROCKSHELL_LEGS),
    SKELETAL_HELM(1, Items.SKULL_PIECE, 5000, Items.SKELETAL_HELM),
    SKELETAL_TOP(3, Items.RIBCAGE_PIECE, 10000, Items.SKELETAL_TOP),
    SKELETAL_BOTTOMS(2, Items.FIBULA_PIECE, 7500, Items.SKELETAL_BOTTOMS),
    SPINED_HELM(1, Items.CIRCULAR_HIDE, 5000, Items.SPINED_HELM),
    SPINED_BODY(3, Items.FLATTENED_HIDE, 10000, Items.SPINED_BODY),
    SPINED_CHAPS(2, Items.STRETCHED_HIDE, 7500, Items.SPINED_CHAPS);

    private final int hidesRequired, otherItem, cost, productId;

    private void craft(Player player, int amount, NPC npc) {
        boolean isNPC = npc != null;
        if (isNPC) {
            if (player.getInventory().getAmount(995) < cost) {
                player.dialogue(new NPCDialogue(npc, "I'm charging " + NumberUtils.formatNumber(cost) + " coins to make " + ItemDefinition.get(productId).descriptiveName + "."));
                return;
            }
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amount) {
                if (!isNPC && player.getInventory().getAmount(Items.DAGANNOTH_HIDE) < hidesRequired) {
                    return;
                }
                if (!isNPC && !player.getInventory().hasId(otherItem)) {
                    return;
                }
                if (isNPC) {
                    if (player.getInventory().getAmount(995) < cost)
                        return;
                    player.getInventory().remove(995, cost);
                }
                player.getInventory().remove(Items.DAGANNOTH_HIDE, hidesRequired);
                player.getInventory().remove(otherItem, 1);
                player.getInventory().add(productId);
                if (!isNPC) {
                    player.animate(1249);
                    player.getStats().addXp(StatType.Crafting, 100 * hidesRequired, true);
                }
                if (!isNPC && !player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    player.sendFilteredMessage("You make " + ItemDefinition.get(productId).descriptiveName + ".");
                    event.delay(2);
                }
            }
            if (isNPC) {
                player.dialogue(new NPCDialogue(npc, "Here you are."));
            }
        });
    }

    public static void showRecipes(Player player) {
        List<String> lines = new ArrayList<>();
        for (DagannothHide hide : values()) {
            lines.add(Color.BLACK.wrap(ItemDefinition.get(hide.productId).name));
            lines.add(Color.DARK_RED.wrap(hide.hidesRequired + "x Dagannoth Hide"));
            lines.add(Color.DARK_RED.wrap("1x " + ItemDefinition.get(hide.otherItem).name));
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Dagannoth Hide Recipes"), lines);
        scroll.open(player);
    }

    private static void craftFromInventory(Player player) {
        if (!player.getInventory().hasId(Items.THREAD) || !player.getInventory().hasId(Items.NEEDLE)) {
            player.sendMessage("You need a needle and thread to work with dagannoth hide.");
            return;
        }
        if (player.getStats().get(StatType.Crafting).currentLevel < 80) {
            player.dialogue(new MessageDialogue("You need a crafting level of 80 to work with dagannoth hide.<br>Bring the materials to Skulgrimen (melee), Sigli the Huntsman (ranged), or Peer the Seer (magic) to have them armour for you."));
            return;
        }
        int hideAmt = player.getInventory().getAmount(Items.DAGANNOTH_HIDE);
        List<SkillItem> itemList = new ArrayList<>();
        for (DagannothHide hide : values()) {
            if (hideAmt < hide.hidesRequired) continue;
            if (!player.getInventory().hasId(hide.otherItem)) continue;
            itemList.add(new SkillItem(hide.productId).addAction((p, amount, event) -> hide.craft(player, amount, null)));
        }
        if (!itemList.isEmpty()) {
            SkillDialogue.make(player, itemList.toArray(new SkillItem[0]));
        } else {
            player.dialogue(
                    new MessageDialogue("You don't have the required materials to craft any armour. Would you like to view the recipes?"),
                    new OptionsDialogue("View recipes?",
                            new Option("View Recipes", () -> showRecipes(player)),
                            new Option("No")
                    )
            );
        }
    }

    private static void craftFromNPC(Player player, NPC npc, DagannothHide... products) {
        int hideAmt = player.getInventory().getAmount(Items.DAGANNOTH_HIDE);
        List<SkillItem> itemList = new ArrayList<>();
        for (DagannothHide hide : products) {
            if (hideAmt < hide.hidesRequired) continue;
            if (!player.getInventory().hasId(hide.otherItem)) continue;
            itemList.add(new SkillItem(hide.productId).addAction((p, amount, event) -> hide.craft(player, amount, npc)));
        }
        if (!itemList.isEmpty()) {
            SkillDialogue.make(player, itemList.toArray(new SkillItem[0]));
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "You don't have the required materials for me to craft any armour. Would you like to view the recipes?"),
                    new OptionsDialogue("View recipes?",
                            new Option("View Recipes", () -> showRecipes(player)),
                            new Option("No")
                    )
            );
        }
    }

    static {
        ItemItemAction.register(Items.DAGANNOTH_HIDE, Items.NEEDLE, (player, primary, secondary) -> craftFromInventory(player));
        // Sigli
        ItemNPCAction.register(Items.DAGANNOTH_HIDE, 3924, (player, item, npc) -> craftFromNPC(player, npc, SPINED_HELM, SPINED_BODY, SPINED_CHAPS));
        // Skulgrimen
        ItemNPCAction.register(Items.DAGANNOTH_HIDE, 3935, (player, item, npc) -> craftFromNPC(player, npc, ROCKSHELL_HELM, ROCKSHELL_PLATE, ROCKSHELL_LEGS));
        // Peer
        ItemNPCAction.register(Items.DAGANNOTH_HIDE, 3895, (player, item, npc) -> craftFromNPC(player, npc, SKELETAL_HELM, SKELETAL_TOP, SKELETAL_BOTTOMS));
    }
}
