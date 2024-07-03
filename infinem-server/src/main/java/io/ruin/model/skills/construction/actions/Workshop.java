package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Flatpack;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class Workshop {

    public static void repair(Player player, Item item, int price, int fixedId) {
        int finalPrice = (int) ((1d - (player.getStats().get(StatType.Smithing).currentLevel / 200d)) * price);
        player.dialogue(new YesNoDialogue("Repair the item?", "Repairing this item will cost " + NumberUtils.formatNumber(finalPrice) + " coins. Continue?", fixedId, 1, () -> {
            if (!player.getInventory().contains(COINS_995, finalPrice)) {
                player.dialogue(new MessageDialogue("Not enough coins in your inventory."));
                return;
            }
            player.getInventory().remove(COINS_995, finalPrice);
            item.setId(fixedId);
        }));
    }

    static {
        //Tool stores
        ItemDispenser.register(6786, Items.SAW, Tool.HAMMER, Tool.CHISEL, Tool.SHEARS);
        ItemDispenser.register(6787, Tool.EMPTY_BUCKET, Tool.KNIFE, Tool.SPADE, Tool.TINDER_BOX);
        ItemDispenser.register(6788, 1757, Tool.GLASSBLOWING_PIPE, Tool.NEEDLE);
        ItemDispenser.register(6789, 1592, 1597, 1595, 11065, 5523, 1599);
        ItemDispenser.register(6790, Tool.RAKE, Tool.SPADE, Tool.TROWEL, Tool.SEED_DIBBER, Tool.WATERING_CAN);

        //Workbenches
        ObjectAction.register(Buildable.WOODEN_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 20));
        ObjectAction.register(Buildable.OAK_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 40));
        ObjectAction.register(Buildable.STEEL_FRAMED_WORKBENCH.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 60));
        ObjectAction.register(Buildable.BENCH_WITH_VICE.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 80));
        ObjectAction.register(Buildable.BENCH_WITH_LATHE.getBuiltObjects()[0], 1, (player, obj) -> Flatpack.openFlatpackCategories(player, 99));
    }


}
