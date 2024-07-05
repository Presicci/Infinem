package io.ruin.model.skills.woodcutting;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

public enum Hatchet {

    BRONZE(1, 879, 3291, 1),
    IRON(1, 877, 3290, 1.5),
    STEEL(6, 875, 3289, 2),
    BLACK(6, 873, 3288, 2.25),
    MITHRIL(21, 871, 3287, 2.5),
    ADAMANT(31, 869, 3286, 3),
    RUNE(41, 867, 3285, 3.5),
    GILDED(41, 8303, 8305, 3.5),
    DRAGON(61, 2846, 3292, 3.85),
    THIRD_AGE(61, 7264, 7266, 3.85),
    INFERNAL(61, 2117, 2116, 3.85),
    INFERNAL_UNCHARGED(61, 2117, 2116, 3.85),
    CRYSTAL(71, 8324, 8327, 3.85),
    CRYSTAL_UNCHARGED(71, 8324, 8327, 4.02);

    public final int levelReq, animationId, canoeAnimationId;
    public final double axeMultiplier;

    Hatchet(int levelReq, int animationId, int canoeAnimationId, double axeMultiplier) {
        this.levelReq = levelReq;
        this.animationId = animationId;
        this.canoeAnimationId = canoeAnimationId;
        this.axeMultiplier = axeMultiplier;
    }

    private static Hatchet compare(Player player, Item item, Hatchet best) {
        if (item == null)
            return best;
        Hatchet hatchet = item.getDef().hatchet;
        if (hatchet == null)
            return best;
        if (player.getStats().get(StatType.Woodcutting).fixedLevel < hatchet.levelReq)
            return best;
        if (best == null)
            return hatchet;
        if (hatchet.axeMultiplier < best.axeMultiplier)
            return best;
        if (hatchet.levelReq < best.levelReq)
            return best;
        return hatchet;
    }

    public static Hatchet find(Player player) {
        Hatchet bestHatchet = null;
        for (Item item : player.getInventory().getItems())
            bestHatchet = Hatchet.compare(player, item, bestHatchet);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        return Hatchet.compare(player, weapon, bestHatchet);
    }

    static {
        ItemDefinition.forEach(def -> {
            String name = def.name.toLowerCase();
            for (Hatchet hatchet : Hatchet.values()) {
                if (name.startsWith(hatchet.name().toLowerCase() + " axe"))
                    def.hatchet = hatchet;
            }
        });
        ItemDefinition.get(13242).hatchet = INFERNAL_UNCHARGED;
        ItemDefinition.get(23675).hatchet = CRYSTAL_UNCHARGED;
    }

}
