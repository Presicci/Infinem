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

    private final MagicTabletType type;
    private final int levelRequirement, experience, productId;
    private final Item baseItem;
    private final Item[] runes;

    MagicTablet(MagicTabletType type, int levelRequirement, int experience, int productId, Item baseItem, Item... runes) {
        this.type = type;
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.productId = productId;
        this.baseItem = baseItem;
        this.runes = runes;
    }

    private void checkTasks(Player player) {
        if (this == CARRALLANGAR) player.getTaskManager().doLookupByUUID(674);  // Make a Carrallanger Teleport Tablet
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

    private static final List<MagicTablet> ANCIENT_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> LUNAR_TABLETS = new ArrayList<>();
    private static final List<MagicTablet> ARCEUUS_TABLETS = new ArrayList<>();

    public static List<MagicTablet> getTablets(MagicTabletType type) {
        switch (type) {
            default:
                return ANCIENT_TABLETS;
            case LUNAR:
                return LUNAR_TABLETS;
            case ARCEUUS:
                return ARCEUUS_TABLETS;
        }
    }

    static {
        for (MagicTablet tablet : values()) {
            if (tablet.type == MagicTabletType.ANCIENT) ANCIENT_TABLETS.add(tablet);
            if (tablet.type == MagicTabletType.LUNAR) LUNAR_TABLETS.add(tablet);
            if (tablet.type == MagicTabletType.ARCEUUS) ARCEUUS_TABLETS.add(tablet);
        }
    }
}
