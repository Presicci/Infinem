package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.smithing.SmeltBar;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
public class BlastFurnaceBelt {

    private static boolean hasSecondaryOres(Player player) {
        return player.getInventory().hasAtLeastOneOf(BlastFurnaceOre.TIN.getOreId(), BlastFurnaceOre.COAL.getOreId());
    }

    private static void loadBelt(Player player) {
        if (!BlastFurnaceCoffer.hasAmount(player, 1)) {
            player.dialogue(new ItemDialogue().one(1000, "You must put money in the coffer to pay the workers."));
            return;
        }
        if (!player.getInventory().hasAtLeastOneOf(BlastFurnaceOre.ORE_IDS_ARRAY)) {
            player.dialogue(new MessageDialogue("You don't have any suitable ores to place onto the conveyor belt."));
            return;
        }
        int primaryOres = BlastFurnace.getPrimaryOreAmount(player);
        if (primaryOres >= 28 && !hasSecondaryOres(player)) {
            player.dialogue(new MessageDialogue("You should make sure all your ore smelts before adding any more."));
            return;
        }
        if (BlastFurnace.getTotalBars(player) >= 28 && !hasSecondaryOres(player)) {
            player.dialogue(new MessageDialogue("You should collect your bars before making any more."));
            return;
        }
        boolean secondaryOnly = primaryOres >= 28;
        boolean hasPrimaryOverflow = false;
        boolean hasSecondaryOverflow = false;
        boolean addedOre = false;
        for (BlastFurnaceOre ore : BlastFurnaceOre.values()) {
            if (secondaryOnly && ore.isPrimaryOre()) continue;
            if (!player.getInventory().contains(ore.getOreId())) continue;
            SmithBar bar = SmithBar.getDataByBar(ore.getBarId());
            if (bar == null) continue;
            if (player.getStats().get(StatType.Smithing).currentLevel < bar.smeltLevel) {
                player.sendMessage("You need to have level " + bar.smeltLevel + " smithing to put " + ore.toString().toLowerCase().replace("_", " ") + " on the conveyor belt.");
                continue;
            }
            if (ore.isPrimaryOre() && primaryOres >= 28 && !player.getInventory().hasAtLeastOneOf(BlastFurnaceOre.TIN.getOreId(), BlastFurnaceOre.COAL.getOreId())) {
                hasPrimaryOverflow = true;
                continue;
            }
            if (!ore.isPrimaryOre()) {
                if (BlastFurnace.getOre(player, BlastFurnaceOre.COAL) == 254 && !player.getInventory().contains(BlastFurnaceOre.TIN.getOreId())) {
                    hasSecondaryOverflow = true;
                    continue;
                }
                if (BlastFurnace.getOre(player, BlastFurnaceOre.TIN) == 254 && !player.getInventory().contains(BlastFurnaceOre.COAL.getOreId(), 1)) {
                    hasSecondaryOverflow = true;
                    continue;
                }
            }
            int amount = player.getInventory().getAmount(ore.getOreId());
            amount = !ore.isPrimaryOre() ? (BlastFurnace.getOre(player, ore) + amount > 254 ? (254 - BlastFurnace.getOre(player, ore)) : amount) :
                    (primaryOres + amount > 28 ? (28 - primaryOres) : amount);
            player.getInventory().remove(ore.getOreId(), amount);
            BlastFurnace.addOre(player, ore, amount);
            addedOre = true;
        }
        if (hasPrimaryOverflow || hasSecondaryOverflow) {
            player.sendMessage("Please allow your ore to smelt before adding more to the melting pot.");
        } else if (addedOre) {
            BlastFurnace.processBars(player);
            player.sendMessage("All your ore goes onto the conveyor belt.");
        }
    }

    private static void putOnBelt(Player player, Item item, BlastFurnaceOre ore) {
        if (!BlastFurnaceCoffer.hasAmount(player, 100)) {
            player.dialogue(new ItemDialogue().one(1000, "You must put money in the coffer to pay the workers."));
            return;
        }
        SmithBar bar = SmithBar.getDataByBar(ore.getBarId());
        if (!player.getStats().check(StatType.Smithing, bar.smeltLevel, "do this")) {
            return;
        }
        if (BlastFurnace.getBar(player, bar) >= 28) {
            player.dialogue(new MessageDialogue("You should collect your bars before making any more."));
            return;
        }
        boolean oreOverflow = ore.isPrimaryOre() ? BlastFurnace.getOre(player, ore) + 1 >= 28 : BlastFurnace.getOre(player, ore) + 1 >= 254;
        if (oreOverflow || BlastFurnace.getPrimaryOreAmount(player) >= 28) {
            player.dialogue(new MessageDialogue("You should make sure all your ore smelts before adding any more."));
            return;
        }
        item.remove();
        // Add to belt
        BlastFurnace.addOre(player, ore, 1);
        BlastFurnace.processBars(player);
    }

    static {
        for (BlastFurnaceOre ore : BlastFurnaceOre.values()) {
            ItemObjectAction.register(ore.getOreId(), 9100, (player, item, obj) -> putOnBelt(player, item, ore));
        }
        ObjectAction.register(9100, "put-ore-on", (player, obj) -> loadBelt(player));
    }
}
