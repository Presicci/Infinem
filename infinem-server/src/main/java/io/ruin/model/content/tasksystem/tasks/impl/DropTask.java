package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.item.Items;

public enum DropTask {
    RUNE_SCIMITAR(138, Items.RUNE_SCIMITAR),
    LIGHT_DARK_MYSTIC_PIECE(149,
            Items.MYSTIC_BOOTS_DARK, Items.MYSTIC_BOOTS_LIGHT,
            Items.MYSTIC_HAT_DARK, Items.MYSTIC_HAT_LIGHT,
            Items.MYSTIC_ROBE_TOP_DARK, Items.MYSTIC_ROBE_TOP_LIGHT,
            Items.MYSTIC_ROBE_BOTTOM_DARK, Items.MYSTIC_ROBE_BOTTOM_LIGHT,
            Items.MYSTIC_BOOTS_DARK, Items.MYSTIC_BOOTS_LIGHT
    ),
    TRIMMED_AMULET(168,
            23309,  // Amulet of defence (t)
            Items.AMULET_OF_MAGIC_T,
            Items.STRENGTH_AMULET_T,
            23354,  // Amulet of power (t)
            Items.AMULET_OF_GLORY_T4
    ),
    GRANITE_SHIELD(169, Items.GRANITE_SHIELD),
    LEAD_BLADED_SWORD(170, Items.LEAFBLADED_SWORD),
    LAVA_BATTLESTAFF(171, Items.LAVA_BATTLESTAFF),
    RUNE_CROSSBOW(211, Items.RUNE_CROSSBOW, Items.RUNITE_CROSSBOW_U, Items.RUNITE_LIMBS),
    DRACONIC_VISSAGE(300, Items.DRACONIC_VISAGE),
    ABYSSAL_WHIP(308, Items.ABYSSAL_WHIP),
    HILL_GIANT_CLUB(368, 20756),
    BRYOPHYTAS_ESSENCE(369, 22372),
    DRAGON_LIMBS(377, 21918),
    WYVERN_VISAGE(379, Items.WYVERN_VISAGE),
    ABYSSAL_DAGGER(381, Items.ABYSSAL_DAGGER),
    OBSIDIAN_CAPE(409, Items.OBSIDIAN_CAPE),
    TOKTZ_KET_XIL(410, Items.TOKTZ_KET_XIL),
    TZHAAR_KET_OM(411, Items.TZHAAR_KET_OM),
    TOKTZ_XIL_AK(412, Items.TOKTZ_XIL_AK),
    TOKTZ_XIL_EK(413, Items.TOKTZ_XIL_EK),
    GRANITE_LEGS(448, Items.GRANITE_LEGS),
    DRAGON_BOOTS(467, Items.DRAGON_BOOTS),
    SMOULDERING_STONE(472, Items.SMOULDERING_STONE),
    STAFF_OF_THE_DEAD(473, Items.STAFF_OF_THE_DEAD),
    ZAMORAKIAN_SPEAR(474, Items.ZAMORAKIAN_SPEAR),
    ARMADYL_ARMOUR_PIECE(475, Items.ARMADYL_CHESTPLATE, Items.ARMADYL_CHAINSKIRT, Items.ARMADYL_HELMET),
    SARADOMIN_SWORD(476, Items.SARADOMIN_SWORD),
    BANDOS_ARMOUR_PIECE(477, Items.BANDOS_CHESTPLATE, Items.BANDOS_TASSETS, Items.BANDOS_BOOTS),
    ARMADYL_CROSSBOW(478, Items.ARMADYL_CROSSBOW),
    CERB_CRYSTAL(479, Items.PRIMORDIAL_CRYSTAL, Items.PEGASIAN_CRYSTAL, Items.ETERNAL_CRYSTAL),
    GODSWORD_HILT(480, Items.BANDOS_HILT, Items.ZAMORAK_HILT, Items.SARADOMIN_HILT, Items.ARMADYL_HILT),
    BRINE_SABRE(511, Items.BRINE_SABRE),
    BERSERKER_RING(526, Items.BERSERKER_RING),
    WARRIOR_RING(527, Items.WARRIOR_RING),
    SEERS_RING(528, Items.SEERS_RING),
    ARCHERS_RING(529, Items.ARCHERS_RING),
    MUD_BATTLESTAFF(531, Items.MUD_BATTLESTAFF),
    SEERCULL(532, Items.SEERCULL),
    LEAF_BLADED_BATTLEAXE(533, Items.LEAFBLADED_BATTLEAXE),
    SKELETAL_VISAGE(540, 22006),
    BASILISK_JAW(545, 24268),
    ZENYTE_SHARD(608, Items.ZENYTE_SHARD),
    HEAVY_FRAME(609, Items.HEAVY_FRAME),
    OCCULT_NECKLACE(612, Items.OCCULT_NECKLACE),
    DRAGON_FULL_HELM(613, Items.DRAGON_FULL_HELM),
    DRAGON_CHAINBODY(691, Items.DRAGON_CHAINBODY),
    BLACK_MASK(721, Items.BLACK_MASK_10),
    BARROWS_PIECE(725,
            Items.GUTHANS_HELM, Items.DHAROKS_HELM, Items.KARILS_COIF, Items.TORAGS_HELM, Items.VERACS_HELM, Items.AHRIMS_HOOD,
            Items.GUTHANS_PLATEBODY, Items.DHAROKS_PLATEBODY, Items.KARILS_LEATHERTOP, Items.TORAGS_PLATEBODY, Items.VERACS_BRASSARD, Items.AHRIMS_ROBETOP,
            Items.GUTHANS_CHAINSKIRT, Items.DHAROKS_PLATELEGS, Items.KARILS_LEATHERSKIRT, Items.TORAGS_PLATELEGS, Items.VERACS_PLATESKIRT, Items.AHRIMS_ROBESKIRT,
            Items.GUTHANS_WARSPEAR, Items.DHAROKS_GREATAXE, Items.KARILS_CROSSBOW, Items.TORAGS_HAMMERS, Items.VERACS_FLAIL, Items.AHRIMS_STAFF
    ),
    BLOOD_SHARD(754, 24777),
    DARK_BOW(798, Items.DARK_BOW),
    ELVEN_SIGNET(808, 23943),
    FEDORA(854, Items.FEDORA),
    ECUMENICAL_KEY(855, Items.ECUMENICAL_KEY),
    BRACELET_OF_ETHEREUM(856, Items.BRACELET_OF_ETHEREUM_UNCHARGED),
    DAGONHAI_PIECE(878, 24288, 24291, 24294),
    TREASONOUS_RING(885, Items.TREASONOUS_RING),
    TYRANNICAL_RING(886, Items.TYRANNICAL_RING),
    RING_OF_THE_GODS(887, Items.RING_OF_THE_GODS),
    CRAWS_BOW(888, 22547),
    VIGGORAS_CHAINMACE(889, 22542),
    THAMMARONS_SCEPTRE(890, 22552),
    HOLY_ELIXIR(895, Items.HOLY_ELIXIR),
    ARCANE_OR_SPECTRAL_SIGIL(900, Items.ARCANE_SIGIL, Items.SPECTRAL_SIGIL),
    ELYSIAN_SIGIL(901, Items.ELYSIAN_SIGIL),
    SARACHNIS_CUDGEL(971, 23528),
    PRAYER_SCROLL(978, Items.PRAYER_SCROLL_RIGOUR, Items.PRAYER_SCROLL_AUGURY),
    DRAKES_CLAW(982, 22966),
    HYDRA_LEATHER(985, 22983),
    DINHS_BULWARD(987, Items.DINHS_BULWARK),
    TWISTED_BOW(989, Items.TWISTED_BOW);

    DropTask(int taskUuid, int... itemIds) {
        for (int itemId : itemIds) {
            ItemDefinition.get(itemId).custom_values.put("DROP_TASK", taskUuid);
        }
    }
}
