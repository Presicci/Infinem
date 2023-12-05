package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/4/2023
 */
public class SieveProducts {

    private static void anchovyOil(Player player, Item ingredient) {
        Item vial = player.getInventory().findItem(Items.VIAL);
        if (vial == null) {
            player.dialogue(new MessageDialogue("You need a vial to drain the oil into."));
            return;
        }
        if (!player.getInventory().hasId(Items.SIEVE)) {
            player.dialogue(new MessageDialogue("You need a sieve to get the oil out of the anchovy paste."));
            return;
        }
        if (ingredient.getAmount() < 8) {
            player.dialogue(new MessageDialogue("You need 8 anchovy paste to make anchovy oil."));
            return;
        }
        ingredient.remove(8);
        vial.setId(Items.ANCHOVY_OIL);
        player.sendMessage("You fill the vial with anchovy oil.");
    }

    private static void impRepellent(Player player, Item ingredient) {
        if (!player.getInventory().hasId(Items.SIEVE) || !player.getInventory().hasId(Items.PESTLE_AND_MORTAR)) {
            player.dialogue(new MessageDialogue("You need a sieve and pestle and mortar to make imp repellent."));
            return;
        }
        Item anchovyOil = player.getInventory().findItem(Items.ANCHOVY_OIL);
        if (anchovyOil == null) {
            player.dialogue(new MessageDialogue("You need anchovy oil to crush the flowers into."));
            return;
        }
        player.animate(364);
        ingredient.remove(1);
        anchovyOil.setId(Items.IMP_REPELLENT);
        player.getStats().addXp(StatType.Herblore, 5.0, true);
        player.sendMessage("You crush the flowers into the oil to create imp repellent.");
    }

    private static final int[] FLOWERS =  {
            Items.MARIGOLDS, Items.ROSEMARY, Items.NASTURTIUMS, Items.WOAD_LEAF, 22932,
            Items.ASSORTED_FLOWERS, Items.BLACK_FLOWERS, Items.BLUE_FLOWERS, Items.MIXED_FLOWERS,
            Items.ORANGE_FLOWERS, Items.PURPLE_FLOWERS, Items.RED_FLOWERS, Items.WHITE_FLOWERS,
            Items.YELLOW_FLOWERS
    };

    static {
        ItemItemAction.register(Items.ANCHOVY_PASTE, Items.SIEVE, (player, primary, secondary) -> anchovyOil(player, primary));
        ItemItemAction.register(Items.ANCHOVY_PASTE, Items.VIAL, (player, primary, secondary) -> anchovyOil(player, primary));
        for (int flower : FLOWERS) {
            ItemItemAction.register(flower, Items.SIEVE, (player, primary, secondary) -> impRepellent(player, primary));
            ItemItemAction.register(flower, Items.PESTLE_AND_MORTAR, (player, primary, secondary) -> impRepellent(player, primary));
            ItemItemAction.register(flower, Items.ANCHOVY_OIL, (player, primary, secondary) -> impRepellent(player, primary));
        }
    }
}
