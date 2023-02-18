package io.ruin.model.skills.smithing;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;

public class RingOfForging {

    private static final int MAX_CHARGES = 60;
    private static final int RING_ID = 2568;
    private static float getChance(SmithBar bar) {
        switch (bar) {
            case BRONZE:
                return .5f;
            case IRON:
                return .25f;
            case STEEL:
                return .2f;
            case MITHRIL:
                return .15f;
            case ADAMANT:
                return .1f;
            case RUNITE:
                return .05f;
            default:
                return 0f;
        }
    }

    private static void spendCharge(Player player, Item item) {
        AttributeExtensions.incrementCharges(item, 1);
        if (AttributeExtensions.getCharges(item) >= MAX_CHARGES) {
            item.remove();
            player.sendMessage("<col=ff0000>Your ring of forging has shattered!");
        }
    }

    public static boolean onSmelt(Player player, SmithBar bar) {
        if (!player.getEquipment().hasId(RING_ID))
            return false;
        double random = Random.get();
        if (random > getChance(bar))
            return false;
        player.getBank().add(bar.itemId, 1);
        player.sendFilteredMessage(Color.DARK_GREEN.wrap("Your ring of forging makes an extra bar! It has been sent to your bank."));
        spendCharge(player, player.getEquipment().get(Equipment.SLOT_RING));
        return true;
    }

    private static void check(Player player, Item item) {
        int charge = MAX_CHARGES - AttributeExtensions.getCharges(item);
        player.sendMessage("This ring of forging has " + charge + " charge" + (charge > 1 ? "s" : "") + " remaining.");
    }

    static {
        ItemAction.registerEquipment(RING_ID, 2, RingOfForging::check);
    }
}
