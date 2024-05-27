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
    /*MOONCLAN(MagicTabletType.LUNAR),
    OURANIA(MagicTabletType.LUNAR),
    WATERBIRTH(MagicTabletType.LUNAR),
    BARBARIAN_OUTPOST(MagicTabletType.LUNAR),
    PORT_KHAZARD(MagicTabletType.LUNAR),
    FISHING_GUILD(MagicTabletType.LUNAR),
    CATHERBY(MagicTabletType.LUNAR),
    ICE_PLATEAU(MagicTabletType.LUNAR),
    // Arceuus
    ARCEUUS_LIBRARY(MagicTabletType.ARCEUUS),
    DRAYNOR_MANOR(MagicTabletType.ARCEUUS),
    BATTLEFRONT(MagicTabletType.ARCEUUS),
    MIND_ALTAR(MagicTabletType.ARCEUUS),
    SALVE_GRAVEYARD(MagicTabletType.ARCEUUS),
    FENKENSTRAINS_CASTLE(MagicTabletType.ARCEUUS),
    WEST_ARDOUGNE(MagicTabletType.ARCEUUS),
    HARMONY_ISLAND(MagicTabletType.ARCEUUS),
    WILDERNESS_CEMETARY(MagicTabletType.ARCEUUS),
    BARROWS(MagicTabletType.ARCEUUS),
    APE_ATOLL(MagicTabletType.ARCEUUS)*/;

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
