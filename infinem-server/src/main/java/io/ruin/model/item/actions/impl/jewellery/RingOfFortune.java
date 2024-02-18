package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerBoolean;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/17/2024
 */
public class RingOfFortune {

    private static final int RING_OF_FORTUNE = 6583; // Old - ring of stone

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", true,
                new JeweleryTeleports.Teleport("Miscellania", JewelleryTeleportBounds.MISCELLANIA.getBounds()),
                new JeweleryTeleports.Teleport("Grand Exchange", JewelleryTeleportBounds.GRAND_EXCHANGE.getBounds()),
                new JeweleryTeleports.Teleport("Falador", JewelleryTeleportBounds.FALADOR.getBounds()),
                new JeweleryTeleports.Teleport("Dondakan", JewelleryTeleportBounds.DONDAKAN.getBounds())
        );
        teleports.register(RING_OF_FORTUNE, -1, -1);
        ItemAction.registerInventory(RING_OF_FORTUNE, "features", RingOfFortune::displayFeatures);
        ItemAction.registerEquipment(RING_OF_FORTUNE, "boss log", (player, item) -> KillCounter.openOwnBoss(player));
        ItemAction.registerEquipment(RING_OF_FORTUNE, "coin collection", (player, item) -> toggleCoinCollect(player));
    }

    private static void displayFeatures(Player player, Item item) {
        player.dialogue(new OptionsDialogue(
                        new Option("Auto collect coins: " + (PlayerBoolean.ROW_DISABLED.has(player) ? Color.DARK_GREEN.wrap("Disabled") : Color.DARK_RED.wrap("Enabled")), () -> toggleCoinCollect(player)),
                        new Option("View boss log", () -> KillCounter.openOwnBoss(player))
                )
        );
    }

    private static void toggleCoinCollect(Player player) {
        boolean disabled = PlayerBoolean.ROW_DISABLED.toggle(player);
        if (disabled)
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of fortune will no longer automatically collect coin drops from monsters."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("Your ring of fortune will now collect all coin drops from monsters."));
    }
}
