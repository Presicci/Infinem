package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.npc.actions.RepairNPC;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.actions.Workshop;

public enum ItemBreaking {
    BARRONITE_MACE(25641, 25625, 10500),

    // Capes
    FIRE_CAPE(6570, 20445, 150000),
    INFERNAL_CAPE(21295, 21287, 225000),
    AVAS_ASSEMBLER(22109, 21914, 240000),
    IMBUED_GUTHIX_CAPE(21793, 24240, 75000),
    IMBUED_SARA_CAPE(21791, 24236, 75000),
    IMBUED_ZAMMY_CAPE(21795, 24244, 75000),

    // Max capes
    FIRE_MAX_CAPE(13329, 20447, 99000),
    INFERNAL_MAX_CAPE(21284, 21289, 99000),
    ASSEMBLER_MAX_CAPE(21898, 21916, 99000),
    ASSEMBLER_GUTHIX_CAPE(21784, 24242, 99000),
    ASSEMBLER_SARADOMIN_CAPE(21776, 24238, 99000),
    ASSEMBLER_ZAMORAK_CAPE(21780, 24246, 99000),

    // Defenders
    BRONZE_DEFENDER(8844, 20449, 1000),
    IRON_DEFENDER(8845, 20451, 2000),
    STEEL_DEFENDER(8846, 20453, 2500),
    BLACK_DEFENDER(8847, 20455, 5000),
    MITHRIL_DEFENDER(8848, 20457, 15000),
    ADAMANT_DEFENDER(8849, 20459, 25000),
    RUNE_DEFENDER(8850, 20461, 35000),
    DRAGON_DEFENDER(12954, 20463, 240000),
    AVERNIC_DEFENDER(22322, 22441, 1000000),

    // Void
    VOID_KNIGHT_MELEE_HELMET(11665, 20481, 160000),
    VOID_KNIGHT_MAGE_HELMET(11663, 20477, 160000),
    VOID_KNIGHT_RANGE_HELMET(11664, 20479, 160000),
    VOID_KNIGHT_TOP(8839, 20465, 180000),
    VOID_KNIGHT_ROBE(8840, 20469, 180000),
    VOID_KNIGHT_MACE(8841, 20473, 20000),
    VOID_GLOVES(8842, 20475, 120000),
    ELITE_VOID_TOP(13072, 20467, 250000),
    ELITE_VOID_ROBE(13073, 20471, 250000),

    // Barbarian assault
    FIGHTER_HAT(10548, 20507, 45000),
    RANGER_HAT(10550, 20509, 45000),
    HEALER_HAT(10547, 20511, 45000),
    FIGHTER_TORSO(10551, 20513, 150000),
    PENANCE_SKIRT(10555, 20515, 20000),

    // Decorative
    DECORATIVE_SWORD(4508, 20483, 5000),
    DECORATIVE_TOP_GOLD(4509, 20485, 5000),
    DECORATIVE_LEGS_GOLD(4510, 20487, 5000),
    DECORATIVE_HELM_GOLD(4511, 20489, 5000),
    DECORATIVE_SHIELD_GOLD(4512, 20491, 5000),
    DECORATIVE_SKIRT_GOLD(11895, 20493, 5000),
    DECORATIVE_ROBE_TOP(11896, 20495, 5000),
    DECORATIVE_ROBE_SKIRT(11897, 20497, 5000),
    DECORATIVE_HAT(11898, 20499, 5000),
    DECORATIVE_RANGE_BODY(11899, 20501, 5000),
    DECORATIVE_CHAPS(11900, 20503, 5000),
    DECORATIVE_QUIVER(11901, 20505, 5000),

    // Halos
    GUTHIX_HALO(12639, 20541, 25000),
    SARADOMIN_HALO(12637, 20537, 25000),
    ZAMORAK_HALO(12638, 20539, 25000),
    ARMADYL_HALO(24192, 24147, 25000),
    BANDOS_HALO(24195, 24149, 25000),
    SEREN_HALO(24198, 24151, 25000),
    ANCIENT_HALO(24201, 24153, 25000),
    BRASSICA_HALO(24204, 24155, 25000);

    public final int fixedId, brokenId, coinRepairCost;

    ItemBreaking(int fixedId, int brokenId, int coinRepairCost) {
        this.fixedId = fixedId;
        this.brokenId = brokenId;
        this.coinRepairCost = coinRepairCost;
        ItemDefinition.get(fixedId).breakTo = this;
        ItemDefinition.get(brokenId).brokenFrom = this;
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