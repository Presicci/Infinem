package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */
public class IKODInterfaceUpdater {
    private final IKOD kept;
    private final IKODInterfaceFlags flags;
    private final int[] keptIds;

    IKODInterfaceUpdater(IKOD kept, IKODInterfaceFlags flags) {
        this.kept = kept;
        this.flags = flags;
        this.keptIds = new int[4];
        Arrays.fill(this.keptIds, -1);
        int idx = 0;
        for (Item keptItem : kept.kept) {
            this.keptIds[idx] = keptItem.getId();
            idx++;
        }
    }

    public static IKODInterfaceUpdater create(Player player, IKODInterfaceFlags flags) {
        IKOD itemsKeptOnDeath = IKOD.create(player, flags);
        return new IKODInterfaceUpdater(itemsKeptOnDeath, flags);
    }

    public void update(Player player) {
        player.getPacketSender().sendClientScript(972, "iiiiioooos",
                flags.skull ? 1 : 0,
                this.flags.protect ? 1 : 0,
                this.flags.wilderness ? 21 : 0,
                this.flags.killedByAPlayer ? 1 : 0,
                this.kept.kept.size(), this.keptIds[0], this.keptIds[1], this.keptIds[2], this.keptIds[3], "");


        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Item> itemConfigs = new ArrayList<>();

        for (IKODItem item : this.kept.otherItems) {
            items.add(item.item);
            itemConfigs.add(new Item(item.kind.configItem, item.item.getAmount()));
        }

        player.getPacketSender().sendItems(584, items);
        player.getPacketSender().sendItems(468, itemConfigs);

    }
}
