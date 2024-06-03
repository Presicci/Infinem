package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.npc.actions.RepairNPC;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.actions.Workshop;

public enum ItemBreaking {

    /* capes */
    FIRE_CAPE(6570, 20445, 25, 150000, false),
    INFERNAL_CAPE(21295, 21287, 25, 225000, false),
    FIRE_MAX_CAPE(13329, 20447, 25, 99000, false),
    INFERNAL_MAX_CAPE(21284, 21289, 25, 99000, false),
    AVAS_ASSEMBLER(22109, 21914, 25, 240000, false),
    ASSEMBLER_MAX_CAPE(21898, 21916, 25, 99000, false),

    /* defenders */
    BRONZE_DEFENDER(8844, 20449, 5, 1000, true),
    IRON_DEFENDER(8845, 20451, 5, 2000, true),
    STEEL_DEFENDER(8846, 20453, 5, 2500, true),
    BLACK_DEFENDER(8847, 20455, 5, 5000, true),
    MITHRIL_DEFENDER(8848, 20457, 5, 15000, true),
    ADAMANT_DEFENDER(8849, 20459, 5, 25000, true),
    RUNE_DEFENDER(8850, 20461, 5, 35000, true),
    DRAGON_DEFENDER(12954, 20463, 10, 240000, false),
    AVERNIC_DEFENDER(22322, 22441, 15, 1000000, false),

    /* void */
    VOID_KNIGHT_MELEE_HELMET(11665, 20481, 25, 160000, false),
    VOID_KNIGHT_MAGE_HELMET(11663, 20477, 25, 160000, false),
    VOID_KNIGHT_RANGE_HELMET(11664, 20479, 25, 160000, false),
    VOID_KNIGHT_TOP(8839, 20465, 25, 180000, false),
    VOID_KNIGHT_ROBE(8840, 20469, 25, 180000, false),
    VOID_KNIGHT_MACE(8841, 20473, 15, 20000, true),
    VOID_GLOVES(8842, 20475, 25, 120000, false),
    ELITE_VOID_TOP(13072, 20467, 25, 250000, false),
    ELITE_VOID_ROBE(13073, 20471, 25, 250000, false),

    /* barbarian assault */
    FIGHTER_HAT(10548, 20507, 15, 45000, false),
    RANGER_HAT(10550, 20509, 15, 45000, false),
    HEALER_HAT(10547, 20511, 15, 45000, false),
    FIGHTER_TORSO(10551, 20513, 15, 150000, false),
    PENANCE_SKIRT(10555, 20515, 15, 20000, false),

    /* decorative armour */
    DECORATIVE_SWORD(4508, 20483, 5, 5000, false),
    DECORATIVE_TOP_GOLD(4509, 20485, 5, 5000, false),
    DECORATIVE_LEGS_GOLD(4510, 20487, 5, 5000, false),
    DECORATIVE_HELM_GOLD(4511, 20489, 5, 5000, false),
    DECORATIVE_SHIELD_GOLD(4512, 20491, 5, 5000, false),
    DECORATIVE_SKIRT_GOLD(11895, 20493, 5, 5000, false),
    DECORATIVE_ROBE_TOP(11896, 20495, 5, 5000, false),
    DECORATIVE_ROBE_SKIRT(11897, 20497, 5, 5000, false),
    DECORATIVE_HAT(11898, 20499, 5, 5000, false),
    DECORATIVE_RANGE_BODY(11899, 20501, 5, 5000, false),
    DECORATIVE_CHAPS(11900, 20503, 5, 5000, false),
    DECORATIVE_QUIVER(11901, 20505, 5, 5000, false),

    /* halos */
    SARADOMIN_HALO(12637, 20537, 15, 25000, false),
    ZAMORAK_HALO(12638, 20539, 15, 25000, false),
    GUTHIX_HALO(12639, 20541, 15, 25000, false);

    public final int brokenId, fixedId, bmRepairCost, coinRepairCost;
    public final boolean freeFromShops;

    ItemBreaking(int fixedId, int brokenId, int bmRepairCost, int coinRepairCost, boolean freeFromShops) {
        this.fixedId = fixedId;
        this.brokenId = brokenId;
        this.bmRepairCost = bmRepairCost;
        this.coinRepairCost = coinRepairCost;
        this.freeFromShops = freeFromShops;
        ItemDefinition.get(fixedId).breakTo = this;
    }

    static {
        for (ItemBreaking i : values()) {
            for (int npc : RepairNPC.REPAIR_NPCS) {
                ItemNPCAction.register(i.brokenId, npc, (player, item, npc1) -> RepairNPC.repairItem(player, item, i.coinRepairCost, i.fixedId));
            }
            ItemObjectAction.register(i.brokenId, Buildable.ARMOUR_STAND.getBuiltObjects()[0], (player, item, obj) -> Workshop.repair(player, item, i.coinRepairCost, i.fixedId));
            RepairNPC.REPAIR_COSTS.put(i.brokenId, i.coinRepairCost);
        }
    }
}