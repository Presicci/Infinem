package io.ruin.model.skills.magic.tablets;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
@Getter
public enum MagicTablet {
    ENCHANT_SAPPHIRE(new MagicTabletType[]{MagicTabletType.OAK, MagicTabletType.OAK_EAGLE, MagicTabletType.OAK_DEMON, MagicTabletType.TEAK_EAGLE, MagicTabletType.TEAK_DEMON,
            MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            7, 17.5, Items.ENCHANT_SAPPHIRE,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.WATER_RUNE)),
    BONES_TO_BANANAS(new MagicTabletType[]{MagicTabletType.OAK_DEMON, MagicTabletType.TEAK_DEMON, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            15, 25, Items.BONES_TO_BANANAS,
            new Item(Items.SOFT_CLAY), new Item(Items.NATURE_RUNE), new Item(Items.WATER_RUNE, 2), new Item(Items.EARTH_RUNE, 2)),
    VARROCK_TELEPORT(new MagicTabletType[]{MagicTabletType.OAK, MagicTabletType.OAK_EAGLE, MagicTabletType.OAK_DEMON, MagicTabletType.TEAK_EAGLE, MagicTabletType.TEAK_DEMON,
            MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            25, 35, Items.VARROCK_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.AIR_RUNE, 3), new Item(Items.FIRE_RUNE)),
    ENCHANT_EMERALD(new MagicTabletType[]{MagicTabletType.OAK_DEMON, MagicTabletType.TEAK_DEMON, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            27, 27.8, Items.ENCHANT_EMERALD,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.AIR_RUNE, 3)),
    LUMBRIDGE_TELEPORT(new MagicTabletType[]{MagicTabletType.OAK_EAGLE, MagicTabletType.TEAK_EAGLE, MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            31, 41, Items.LUMBRIDGE_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.AIR_RUNE, 3), new Item(Items.EARTH_RUNE)),
    FALADOR_TELEPORT(new MagicTabletType[]{MagicTabletType.OAK_EAGLE, MagicTabletType.TEAK_EAGLE, MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            37, 48, Items.FALADOR_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.AIR_RUNE, 3), new Item(Items.WATER_RUNE)),
    HOUSE_TELEPORT(new MagicTabletType[]{MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            40, 30, 8013,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.AIR_RUNE), new Item(Items.EARTH_RUNE)),
    CAMELOT_TELEPORT(new MagicTabletType[]{MagicTabletType.TEAK_EAGLE, MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            45, 55.5, Items.FALADOR_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.AIR_RUNE, 5)),
    KOUREND_TELEPORT(new MagicTabletType[]{MagicTabletType.TEAK_EAGLE, MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            48, 58, Items.KOUREND_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.WATER_RUNE), new Item(Items.FIRE_RUNE)),
    ENCHANT_RUBY(new MagicTabletType[]{MagicTabletType.TEAK_DEMON, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            49, 44.5, Items.ENCHANT_RUBY,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.FIRE_RUNE, 5)),
    ARDOUGNE_TELEPORT(new MagicTabletType[]{MagicTabletType.TEAK_EAGLE, MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            51, 61, Items.ARDOUGNE_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.WATER_RUNE, 2)),
    CIVITAS_FORTIS_TELEPORT(new MagicTabletType[]{MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            54, 64, 28824,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.EARTH_RUNE), new Item(Items.FIRE_RUNE)),
    ENCHANT_DIAMOND(new MagicTabletType[]{MagicTabletType.TEAK_DEMON, MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            57, 50.3, Items.ENCHANT_DIAMOND,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.EARTH_RUNE, 10)),
    WATCHTOWER_TELEPORT(new MagicTabletType[]{MagicTabletType.MAHOGANY_EAGLE, MagicTabletType.MARBLE_LECTERN},
            58, 68, Items.WATCHTOWER_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.EARTH_RUNE, 2)),
    BONES_TO_PEACHES(new MagicTabletType[]{MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            60, 35.5, Items.BONES_TO_PEACHES,
            new Item(Items.SOFT_CLAY), new Item(Items.NATURE_RUNE, 2), new Item(Items.WATER_RUNE, 4), new Item(Items.EARTH_RUNE, 4)),
    ENCHANT_DRAGONSTONE(new MagicTabletType[]{MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            68, 58.5, Items.ENCHANT_DRAGONSTN,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.WATER_RUNE, 15), new Item(Items.EARTH_RUNE, 15)),
    ENCHANT_ONYX(new MagicTabletType[]{MagicTabletType.MAHOGANY_DEMON, MagicTabletType.MARBLE_LECTERN},
            87, 73, Items.ENCHANT_ONYX,
            new Item(Items.SOFT_CLAY), new Item(Items.COSMIC_RUNE), new Item(Items.FIRE_RUNE, 20), new Item(Items.EARTH_RUNE, 20)),
    // Ancient
    PADDEWA(MagicTabletType.ANCIENT, 54, 64, Items.PADDEWWA_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.AIR_RUNE), new Item(Items.FIRE_RUNE)),
    SENNTISTEN(MagicTabletType.ANCIENT, 60, 70, Items.SENNTISTEN_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.SOUL_RUNE)),
    KHARYLL(MagicTabletType.ANCIENT, 66, 76, Items.KHARYRLL_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.BLOOD_RUNE)),
    LASSAR(MagicTabletType.ANCIENT, 72, 82, Items.LASSAR_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.WATER_RUNE, 4)),
    DAREEYAK(MagicTabletType.ANCIENT, 78, 88, Items.DAREEYAK_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.AIR_RUNE, 2), new Item(Items.FIRE_RUNE, 3)),
    CARRALLANGAR(MagicTabletType.ANCIENT, 84, 94, Items.CARRALLANGAR_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.SOUL_RUNE, 2)),
    ANNAKARL(MagicTabletType.ANCIENT, 90, 100, Items.ANNAKARL_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.BLOOD_RUNE, 2)),
    GHORROCK(MagicTabletType.ANCIENT, 96, 106, Items.GHORROCK_TELEPORT,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.WATER_RUNE, 8)),
    // Lunar
    MOONCLAN(MagicTabletType.LUNAR, 69, 66, 24949,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.ASTRAL_RUNE, 2), new Item(Items.EARTH_RUNE, 2)),
    OURANIA(MagicTabletType.LUNAR, 71, 69, 24951,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.ASTRAL_RUNE, 2), new Item(Items.EARTH_RUNE, 6)),
    WATERBIRTH(MagicTabletType.LUNAR, 72, 71, 24953,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE), new Item(Items.ASTRAL_RUNE, 2), new Item(Items.WATER_RUNE)),
    BARBARIAN_OUTPOST(MagicTabletType.LUNAR, 75, 72, 24955,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.ASTRAL_RUNE, 2), new Item(Items.FIRE_RUNE, 3)),
    PORT_KHAZARD(MagicTabletType.LUNAR, 78, 76, 24957,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 2), new Item(Items.ASTRAL_RUNE, 2), new Item(Items.WATER_RUNE, 4)),
    FISHING_GUILD(MagicTabletType.LUNAR, 85, 80, 24959,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 3), new Item(Items.ASTRAL_RUNE, 3), new Item(Items.WATER_RUNE, 10)),
    CATHERBY(MagicTabletType.LUNAR, 87, 89, 24961,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 3), new Item(Items.ASTRAL_RUNE, 3), new Item(Items.WATER_RUNE, 10)),
    ICE_PLATEAU(MagicTabletType.LUNAR, 89, 92, 24963,
            new Item(Items.SOFT_CLAY), new Item(Items.LAW_RUNE, 3), new Item(Items.ASTRAL_RUNE, 3), new Item(Items.WATER_RUNE, 8)),
    // Arceuus
    ARCEUUS_LIBRARY(MagicTabletType.ARCEUUS, 6, 10, 19613,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.EARTH_RUNE, 2)),
    DRAYNOR_MANOR(MagicTabletType.ARCEUUS, 17, 16, 19615,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.EARTH_RUNE), new Item(Items.WATER_RUNE)),
    BATTLEFRONT(MagicTabletType.ARCEUUS, 23, 19, 22949,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.EARTH_RUNE), new Item(Items.FIRE_RUNE)),
    MIND_ALTAR(MagicTabletType.ARCEUUS, 28, 22, 19617,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.MIND_RUNE, 2)),
    SALVE_GRAVEYARD(MagicTabletType.ARCEUUS, 40, 30, 19619,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.SOUL_RUNE, 2)),
    FENKENSTRAINS_CASTLE(MagicTabletType.ARCEUUS, 48, 50, 19621,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.SOUL_RUNE), new Item(Items.EARTH_RUNE)),
    WEST_ARDOUGNE(MagicTabletType.ARCEUUS, 61, 68, 19623,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE, 2), new Item(Items.SOUL_RUNE, 2)),
    HARMONY_ISLAND(MagicTabletType.ARCEUUS, 65, 74, 19625,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.SOUL_RUNE), new Item(Items.NATURE_RUNE)),
    WILDERNESS_CEMETARY(MagicTabletType.ARCEUUS, 71, 82, 19627,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE), new Item(Items.SOUL_RUNE), new Item(Items.BLOOD_RUNE)),
    BARROWS(MagicTabletType.ARCEUUS, 83, 90, 19629,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE, 2), new Item(Items.SOUL_RUNE, 2), new Item(Items.BLOOD_RUNE)),
    APE_ATOLL(MagicTabletType.ARCEUUS, 90, 100, 19631,
            new Item(Items.DARK_ESSENCE_BLOCK), new Item(Items.LAW_RUNE, 2), new Item(Items.SOUL_RUNE, 2), new Item(Items.BLOOD_RUNE, 2));

    private final MagicTabletType[] types;
    private final int levelRequirement, productId;
    private final double experience;
    private final Item baseItem;
    private final Item[] runes;

    MagicTablet(MagicTabletType type, int levelRequirement, double experience, int productId, Item baseItem, Item... runes) {
        this.types = new MagicTabletType[]{type};
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.productId = productId;
        this.baseItem = baseItem;
        this.runes = runes;
    }

    MagicTablet(MagicTabletType[] types, int levelRequirement, double experience, int productId, Item baseItem, Item... runes) {
        this.types = types;
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.productId = productId;
        this.baseItem = baseItem;
        this.runes = runes;
    }

    private void checkTasks(Player player) {
        if (this == CARRALLANGAR) player.getTaskManager().doLookupByUUID(674);  // Make a Carrallanger Teleport Tablet
        if (this == MOONCLAN) player.getTaskManager().doLookupByUUID(942);      // Make a Moonclan Teleport Tablet
        if (this == DRAYNOR_MANOR) player.getTaskManager().doLookupByUUID(943); // Make a Draynor Manor Teleport Tablet
    }

    public void create(Player player, int amount) {
        Inventory inventory = player.getInventory();
        player.startEvent(e -> {
            int left = amount;
            player.closeInterfaces();
            while (left > 0) {
                if (!player.getStats().check(StatType.Magic, levelRequirement, "create this tablet")) {
                    return;
                }
                if (!inventory.contains(baseItem)) {
                    player.sendMessage("A "
                            + (baseItem.getId() == Items.SOFT_CLAY ? "piece of soft clay" : "dark essence block")
                            + " is required to create a tablet.");
                    return;
                }
                RuneRemoval runeRemoval = RuneRemoval.get(player, runes);
                if (runeRemoval == null) {
                    player.sendMessage("You don't have the required runes to create that tablet.");
                    return;
                }
                player.animate(Construction.MAKE_TABLET);
                player.getStats().addXp(StatType.Magic, experience, true);
                inventory.remove(baseItem);
                runeRemoval.remove();
                inventory.add(productId);
                checkTasks(player);
                left--;
                e.delay(5);
            }
        });
    }

    private static final List<MagicTablet> OAK_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> OAK_EAGLE_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> OAK_DEMON_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> TEAK_EAGLE_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> TEAK_DEMON_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> MAHOGANY_EAGLE_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> MAHOGANY_DEMON_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> MARBLE_LECTERN_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> ANCIENT_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> LUNAR_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> ARCEUUS_TABLETS = new ArrayList<>();

    public static List<MagicTablet> getTablets(MagicTabletType type) {
        switch (type) {
            default:
                return OAK_TABLETS;
            case OAK_EAGLE:
                return OAK_EAGLE_TABLETS;
            case OAK_DEMON:
                return OAK_DEMON_TABLETS;
            case TEAK_EAGLE:
                return TEAK_EAGLE_TABLETS;
            case TEAK_DEMON:
                return TEAK_DEMON_TABLETS;
            case MAHOGANY_EAGLE:
                return MAHOGANY_EAGLE_TABLETS;
            case MAHOGANY_DEMON:
                return MAHOGANY_DEMON_TABLETS;
            case MARBLE_LECTERN:
                return MARBLE_LECTERN_TABLETS;
            case ANCIENT:
                return ANCIENT_TABLETS;
            case LUNAR:
                return LUNAR_TABLETS;
            case ARCEUUS:
                return ARCEUUS_TABLETS;
        }
    }

    static {
        for (MagicTablet tablet : values()) {
            for (MagicTabletType type : tablet.types) {
                if (type == MagicTabletType.OAK) OAK_TABLETS.add(tablet);
                if (type == MagicTabletType.OAK_EAGLE) OAK_EAGLE_TABLETS.add(tablet);
                if (type == MagicTabletType.OAK_DEMON) OAK_DEMON_TABLETS.add(tablet);
                if (type == MagicTabletType.TEAK_EAGLE) TEAK_EAGLE_TABLETS.add(tablet);
                if (type == MagicTabletType.TEAK_DEMON) TEAK_DEMON_TABLETS.add(tablet);
                if (type == MagicTabletType.MAHOGANY_EAGLE) MAHOGANY_EAGLE_TABLETS.add(tablet);
                if (type == MagicTabletType.MAHOGANY_DEMON) MAHOGANY_DEMON_TABLETS.add(tablet);
                if (type == MagicTabletType.MARBLE_LECTERN) MARBLE_LECTERN_TABLETS.add(tablet);
                if (type == MagicTabletType.ANCIENT) ANCIENT_TABLETS.add(tablet);
                if (type == MagicTabletType.LUNAR) LUNAR_TABLETS.add(tablet);
                if (type == MagicTabletType.ARCEUUS) ARCEUUS_TABLETS.add(tablet);
            }
        }
    }
}
