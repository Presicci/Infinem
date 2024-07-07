package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

import java.util.function.Supplier;

public enum Pickaxe {

    BRONZE(1, 1265, 625, 6753, 8),
    IRON(1, 1267, 626, 6754, 7),
    STEEL(6, 1269, 627, 6755, 6),
    BLACK(11, 12297, 6108, 3866, 5),
    MITHRIL(21, 1273, 629, 6757, 5),
    ADAMANT(31, 1271, 628, 6756, 4),
    RUNE(41, 1275, 624, 6752, 3),
    DRAGON(61, 11920, 7139, 6758, 3),
    THIRD_AGE(61, 20014, 7283, 7282, 3),
    DRAGON_OR(61, 12797, 642, 335, 3),
    INFERNAL(61, 13243, 4482, 4481, 3),
    INFERNAL_UNCHARGED(61, 13244, 4482, 4481, 3);

    public final int levelReq, id, regularAnimationID, crystalAnimationID, ticks;

    Pickaxe(int levelReq, int id, int regularAnimationID, int crystalAnimationID, int ticks) {
        this.levelReq = levelReq;
        this.id = id;
        this.regularAnimationID = regularAnimationID;
        this.crystalAnimationID = crystalAnimationID;
        this.ticks = ticks;
    }

    private static Pickaxe compare(Player player, Item item, Pickaxe best) {
        if (item == null)
            return best;
        Pickaxe pickaxe = item.getDef().pickaxe;
        if (pickaxe == null)
            return best;
        if (player.getStats().get(StatType.Mining).fixedLevel < pickaxe.levelReq)
            return best;
        if (best == null)
            return pickaxe;
        if (pickaxe.levelReq < best.levelReq)
            return best;
        return pickaxe;
    }

    public static Pickaxe find(Player player) {
        Pickaxe bestPickaxe = null;
        for (Item item : player.getInventory().getItems())
            bestPickaxe = compare(player, item, bestPickaxe);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        return compare(player, weapon, bestPickaxe);
    }

    static {
        for (Pickaxe pickaxe : values())
            ItemDefinition.get(pickaxe.id).pickaxe = pickaxe;
    }
}
