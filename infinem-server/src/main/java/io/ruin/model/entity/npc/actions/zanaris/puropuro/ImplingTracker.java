package io.ruin.model.entity.npc.actions.zanaris.puropuro;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.hunter.Impling;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2024
 */
public class ImplingTracker {

    private static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.IMPLING_TRACKER);
        sendCounts(player);
    }

    private static void sendCounts(Player player) {
        StringBuilder surface = new StringBuilder();
        StringBuilder puroPuro = new StringBuilder();
        for (Impling impling : Impling.values()) {
            surface.append(StringUtils.capitalizeFirst(impling.name().toLowerCase()));
            surface.append(":<br>");
            surface.append("<col=ffffff>");
            surface.append(player.getAttributeIntOrZero(impling.getCounterKey()));

            puroPuro.append(StringUtils.capitalizeFirst(impling.name().toLowerCase()));
            puroPuro.append(":<br>");
            puroPuro.append("<col=ffffff>");
            puroPuro.append(player.getAttributeIntOrZero(impling.getPuroCounterKey()));

            if (impling.ordinal() != Impling.values().length - 1) {
                surface.append("|");
                puroPuro.append("|");
            }
        }
        player.getPacketSender().sendClientScript(1327, "iiiiiiiiss",
                Interface.IMPLING_TRACKER << 16 | 4,
                Interface.IMPLING_TRACKER << 16 | 6,
                Interface.IMPLING_TRACKER << 16 | 5,
                Interface.IMPLING_TRACKER << 16 | 3,
                Interface.IMPLING_TRACKER << 16 | 8,
                Interface.IMPLING_TRACKER << 16 | 10,
                Interface.IMPLING_TRACKER << 16 | 9,
                Interface.IMPLING_TRACKER << 16 | 7,
                surface.toString(),
                puroPuro.toString());
    }

    static {
        ItemAction.registerInventory(Items.BUTTERFLY_NET, "check totals", (player, item) -> open(player));
        ItemAction.registerInventory(Items.MAGIC_BUTTERFLY_NET, "check totals", (player, item) -> open(player));
        ItemAction.registerEquipment(Items.BUTTERFLY_NET, "check totals", (player, item) -> open(player));
        ItemAction.registerEquipment(Items.MAGIC_BUTTERFLY_NET, "check totals", (player, item) -> open(player));
    }
}
