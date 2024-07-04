package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Items;
import io.ruin.model.skills.construction.Buildable;
import lombok.Getter;

@Getter
public enum MahoganyHotspotType {
    SHELVES(Buildable.MH_WOODEN_SHELVES, Buildable.MH_OAK_SHELVES, Buildable.MH_TEAK_SHELVES, Buildable.MH_MAHOGANY_SHELVES),
    BIG_TABLE(Buildable.MH_2X2_WOODEN_TABLE, Buildable.MH_2X2_OAK_TABLE, Buildable.MH_2X2_TEAK_TABLE, Buildable.MH_2X2_MAHOGANY_TABLE),
    TABLE(Buildable.MH_2X1_WOODEN_TABLE, Buildable.MH_2X1_OAK_TABLE, Buildable.MH_2X1_TEAK_TABLE, Buildable.MH_2X1_MAHOGANY_TABLE),
    SMALL_TABLE(Buildable.MH_1X1_WOODEN_TABLE, Buildable.MH_1X1_OAK_TABLE, Buildable.MH_1X1_TEAK_TABLE, Buildable.MH_1X1_MAHOGANY_TABLE),
    CUPBOARD(Buildable.MH_WOODEN_CUPBOARD, Buildable.MH_OAK_CUPBOARD, Buildable.MH_TEAK_CUPBOARD, Buildable.MH_MAHOGANY_CUPBOARD),
    BOOKCASE(Buildable.MH_WOODEN_BOOKCASE, Buildable.MH_OAK_BOOKCASE, Buildable.MH_TEAK_BOOKCASE, Buildable.MH_MAHOGANY_BOOKCASE),
    SMALL_BED(Buildable.MH_2X1_WOODEN_BED, Buildable.MH_2X1_OAK_BED, Buildable.MH_2X1_TEAK_BED, Buildable.MH_2X1_MAHOGANY_BED),
    BED(Buildable.MH_2X2_WOODEN_BED, Buildable.MH_2X2_OAK_BED, Buildable.MH_2X2_TEAK_BED, Buildable.MH_2X2_MAHOGANY_BED),
    DRAWER(Buildable.MH_WOODEN_DRAWER, Buildable.MH_OAK_DRAWER, Buildable.MH_TEAK_DRAWER, Buildable.MH_MAHOGANY_DRAWER),
    CABINET(Buildable.MH_WOODEN_CABINET, Buildable.MH_OAK_CABINET, Buildable.MH_TEAK_CABINET, Buildable.MH_MAHOGANY_CABINET),
    DRESSER(Buildable.MH_WOODEN_DRESSER, Buildable.MH_OAK_DRESSER, Buildable.MH_TEAK_DRESSER, Buildable.MH_MAHOGANY_DRESSER),
    CHAIR(Buildable.MH_WOODEN_CHAIR, Buildable.MH_OAK_CHAIR, Buildable.MH_TEAK_CHAIR, Buildable.MH_MAHOGANY_CHAIR),
    WARDROBE(Buildable.MH_WOODEN_WARDROBE, Buildable.MH_OAK_WARDROBE, Buildable.MH_TEAK_WARDROBE, Buildable.MH_MAHOGANY_WARDROBE),
    // Repairables
    SINK(Items.STEEL_BAR, 120),
    RANGE(Items.STEEL_BAR, 120),
    GRANDFATHER_CLOCK(Items.PLANK, Items.OAK_PLANK, Items.TEAK_PLANK, Items.MAHOGANY_PLANK, 127.5, 160, 190, 240),
    HAT_STAND(Items.PLANK, Items.OAK_PLANK, Items.TEAK_PLANK, Items.MAHOGANY_PLANK, 127.5, 160, 190, 240),
    BATH(Items.STEEL_BAR, 120),
    MIRROR(Items.PLANK, Items.OAK_PLANK, Items.TEAK_PLANK, Items.MAHOGANY_PLANK, 127.5, 160, 190, 240);

    private final Buildable[] buildables;
    private final int[] repairItems;
    private final double[] repairExperience;

    MahoganyHotspotType(Buildable... buildables) {
        this.buildables = buildables;
        this.repairItems = null;
        this.repairExperience = null;
    }

    MahoganyHotspotType(int item, double xp) {
        this.buildables = null;
        this.repairItems = new int[] { item, item, item, item };
        this.repairExperience = new double[] { xp, xp, xp, xp };
    }

    MahoganyHotspotType(int beginnerItem, int noviceItem, int adeptItem, int expertItem, double beginnerXp, double noviceXp, double adeptXp, double expertXp) {
        this.buildables = null;
        this.repairItems = new int[] { beginnerItem, noviceItem, adeptItem, expertItem };
        this.repairExperience = new double[] { beginnerXp, noviceXp, adeptXp, expertXp };
    }

    public boolean isRepairable() {
        return repairItems != null && repairExperience != null;
    }

    public String getName() {
        return name().toLowerCase().replace("_", " ");
    }

    public void sendBuildInterface(Player player) {
        if (buildables == null) return;
        int count = 1;
        player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_FURNITURE_CREATION);
        for (Buildable b : buildables) {
            player.getPacketSender().sendClientScript(1404, "iiisi", count++, b.getItemId(), b.getLevelReq(), b.getCreationMenuString(), b.hasLevelAndMaterials(player) ? 1 : 0);
        }
        player.getPacketSender().sendClientScript(1406, "ii", count - 1, 1);
        player.getPacketSender().sendAccessMask(458, 2, 1, count - 1, 1);
        player.getPacketSender().sendClientScript(2157, "");
    }
}
