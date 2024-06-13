package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

import java.util.Arrays;

public enum ItemImbue {
    BLACK_MASK(8921, 11784, 1_250_000, 25276, 500, 26781),
    SLAYER_HELMET(11864, 11865, 1_250_000, 25177, 500, 26674),
    BLACK_SLAYER_HELMET(19639, 19641, 1_250_000, 25179, 500, 26675),
    GREEN_SLAYER_HELMET(19643, 19645, 1_250_000, 25181, 500, 26676),
    RED_SLAYER_HELMET(19647, 19649, 1_250_000, 25183, 500, 26677),
    PURPLE_SLAYER_HELMET(21264, 21266, 1_250_000, 25185, 500, 26678),
    TURQUOISE_SLAYER_HELMET(21888, 21890, 1_250_000, 25187, 500, 26679),
    HYDRA_SLAYER_HELMET(23073, 23075, 1_250_000, 25189, 500, 26680),
    TWISTED_SLAYER_HELMET(24370, 24444, 1_250_000, 25191, 500, 26681),
    TZTOK_SLAYER_HELMET(25898, 25900, 1_250_000, 25902, 500, 26682),
    VAMPYRIC_SLAYER_HELMET(25904, 25906, 1_250_000, 25908, 500, 26683),
    TZKAL_SLAYER_HELMET(25910, 25912, 1_250_000, 25914, 500, 26684),
    SALVE_AMULET(4081, 12017, 800_000, 25250, 320, 26763),
    SALVE_AMULET_E(10588, 12018, 800_000, 25278, 320, 26782),
    RING_OF_THE_GODS(12601, 13202, 650_000, 25248, 260, 26764),
    RING_OF_SUFFERING(19550, 19710, 725_000, 25252, 300, 26761),
    RING_OF_SUFFERING_R(20655, 20657, 725_000, 25246, 300, 26762),
    BERSERKER_RING(6737, 11773, 650_000, 25264, 260, 26770),
    WARRIOR_RING(6735, 11772, 650_000, 25262, 260, 26769),
    ARCHERS_RING(6733, 11771, 650_000, 25260, 260, 26768),
    SEERS_RING(6731, 11770, 650_000, 25258, 260, 26767),
    TYRANNICAL_RING(12603, 12691, 650_000, 25254, 260, 26765),
    TREASONOUS_RING(12605, 12692, 650_000, 25256, 260, 26766),
    GRANITE_RING(21739, 21752, 500_000, 25193, 200, 26685);

    public final int regularId, nmzImbue, nmzCost, soulWarsImbue, soulWarsCost, emirImbue;

    ItemImbue(int regularId, int nmzImbue, int nmzCost, int soulWarsImbue, int soulWarsCost, int emirImbue) {
        this.regularId = regularId;
        this.nmzImbue = nmzImbue;
        this.nmzCost = nmzCost;
        this.soulWarsImbue = soulWarsImbue;
        this.soulWarsCost = soulWarsCost;
        this.emirImbue = emirImbue;

        ItemDefinition regularDef = ItemDefinition.get(regularId);
        for (int imbuedId : Arrays.asList(nmzImbue, soulWarsImbue, emirImbue)) {
            ItemDefinition upgradeDef = ItemDefinition.get(imbuedId);
            if(upgradeDef.protectValue < regularDef.protectValue)
                upgradeDef.protectValue = regularDef.protectValue;
            upgradeDef.upgradedFrom = this;
        }
    }

    public void uncharge(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Uncharge the item?", "Uncharging will return 100% of the Nightmare Zone Point cost.", item, () -> {
            item.setId(regularId);
            Config.NMZ_REWARD_POINTS_TOTAL.increment(player, nmzCost);
        }));
    }

    public static ItemImbue getImbue(int id) {
        for (ItemImbue imbue : values())
            if (imbue.regularId == id)
                return imbue;
        return null;
    }

    static {
        for (ItemImbue imbue : values()) {
            ItemAction.registerInventory(imbue.nmzImbue, "uncharge", imbue::uncharge);
        }
    }
}

