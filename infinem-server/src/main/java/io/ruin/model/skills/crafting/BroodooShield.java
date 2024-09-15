package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.stat.StatType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/16/2024
 */
public class BroodooShield {

    private static void create(Player player, Item mask, int shieldId) {
        if (!player.getStats().check(StatType.Crafting, 35, "craft a broodoo shield")) return;
        if (!Tool.HAMMER.hasTool(player)) {
            player.sendMessage("You need a hammer to make a broodoo shield.");
            return;
        }
        int nail = 0;
        for (int i : Material.NAIL_TYPES) {
            if (player.getInventory().contains(i, 8)) {
                nail = i;
                break;
            }
        }
        if (nail == 0 || player.getInventory().getAmount(Items.SNAKESKIN) < 2) {
            player.sendMessage("You need 8 nails and 2 snakeskins to make a broodoo shield.");
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.getInventory().remove(nail, 8);
        player.getInventory().remove(Items.SNAKESKIN, 2);
        mask.remove();
        player.getInventory().add(shieldId);
        player.getStats().addXp(StatType.Crafting, 100, true);
        player.animate(1249);
        player.sendMessage("You craft the mask into a shield.");
    }

    private static final Map<Integer, Integer> MASKS = new HashMap<Integer, Integer>() {{
        put(Items.TRIBAL_MASK_3, Items.BROODOO_SHIELD_10_3);
        put(Items.TRIBAL_MASK_2, Items.BROODOO_SHIELD_10_2);
        put(Items.TRIBAL_MASK, Items.BROODOO_SHIELD_10);
    }};

    static {
        for (int maskId : MASKS.keySet()) {
            ItemItemAction.register(Items.HAMMER, maskId, (player, hammer, mask) -> create(player, mask, MASKS.get(maskId)));
            ItemItemAction.register(Items.SNAKESKIN, maskId, (player, snakeskin, mask) -> create(player, mask, MASKS.get(maskId)));
            for (int nailId : Material.NAIL_TYPES) {
                ItemItemAction.register(nailId, maskId, (player, nail, mask) -> create(player, mask, MASKS.get(maskId)));
            }
        }
    }
}
