package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.storage.PlankSack;

import java.util.Arrays;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

public enum Material {

    REGULAR_PLANK(960),
    OAK_PLANK(8778),
    TEAK_PLANK(8780),
    MAHOGANY_PLANK(8782),
    NAILS(4819),
    BOLT_OF_CLOTH(8790),
    GOLD_LEAF(8784),
    SOFT_CLAY(1761),
    LIMESTONE_BRICK(3420),
    MARBLE_BLOCK(8786),
    IRON_BAR(2351),
    STEEL_BAR(2353),
    MOLTEN_GLASS(1775),
    PLATINUM_TOKEN(13204),
    CLOCKWORK(8792),
    MAGIC_STONE(8788),
    COINS(COINS_995),
    MALLIGNUM_ROOT_PLANK(21036),
    CONDENSED_GOLD(26266);

    Material(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    private final int itemId;

    public Item item(int amount) {
        return new Item(itemId, amount);
    }

    static boolean hasMaterial(Player player, Item material) {
        if (material.getId() == NAILS.getItemId()) {
            int count = 0;
            for (int id : NAIL_TYPES) {
                count += player.getInventory().getAmount(id);
                if (count >= material.getAmount())
                    return true;
            }
            return false;
        } else if (material.getId() == REGULAR_PLANK.getItemId()
                || material.getId() == OAK_PLANK.getItemId()
                || material.getId() == TEAK_PLANK.getItemId()
                || material.getId() == MAHOGANY_PLANK.getItemId()) {
            return PlankSack.hasMaterials(player, material.getId(), material.getAmount());
        } else
            return player.getInventory().contains(material.getId(), material.getAmount());
    }

    public static void removeMaterial(Player player, Item material) {
        if (material.getId() == NAILS.getItemId()) {
            int removed = 0;
            for (int id : NAIL_TYPES) {
                removed += player.getInventory().remove(id, material.getAmount() - removed);
                if (removed >= material.getAmount())
                    break;
            }
        } else if (material.getId() == REGULAR_PLANK.getItemId()
                || material.getId() == OAK_PLANK.getItemId()
                || material.getId() == TEAK_PLANK.getItemId()
                || material.getId() == MAHOGANY_PLANK.getItemId()) {
            PlankSack.removeMaterials(player, material.getId(), material.getAmount());
        } else {
            player.getInventory().remove(material.getId(), material.getAmount());
        }
    }

    public static final List<Integer> NAIL_TYPES = Arrays.asList(4819,4820,1539,4821,4822,4823,4824);
}
