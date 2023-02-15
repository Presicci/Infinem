package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/14/2022
 */
public class NightmareStaffs {

    /**
     * Orbs
     */
    private static final int HARMONISED_ORB = 24511;
    private static final int VOLATILE_ORB = 24514;
    private static final int ELDRITCH_ORB = 24517;

    /**
     * Nightmare Staff
     */
    private static final int NIGHTMARE_STAFF = 24422;

    /**
     * Nightmare staffs with Orb
     */
    private static final int HARMONISED_NIGHTMARE_STAFF = 24423;
    private static final int VOLATILE_NIGHTMARE_STAFF = 24424;
    private static final int ELDRITCH_NIGHTMARE_STAFF = 24425;

    private static void combine(Player player, Item itemOne, Item itemTwo, int result) {
        itemOne.remove();
        itemTwo.remove();
        player.getInventory().add(result, 1);
    }

    private static void dismantle(Player player, Item item, int resultOne, int resultTwo) {
        if(player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough free space to do this.");
            return;
        }
        item.remove();
        player.getInventory().add(resultOne, 1);
        player.getInventory().add(resultTwo, 1);
    }

    static {
        /**
         * Combine
         */
        ItemItemAction.register(HARMONISED_ORB, NIGHTMARE_STAFF, (player, primary, secondary) -> combine(player, primary, secondary, HARMONISED_NIGHTMARE_STAFF));
        ItemItemAction.register(VOLATILE_ORB, NIGHTMARE_STAFF, (player, primary, secondary) -> combine(player, primary, secondary, VOLATILE_NIGHTMARE_STAFF));
        ItemItemAction.register(ELDRITCH_ORB, NIGHTMARE_STAFF, (player, primary, secondary) -> combine(player, primary, secondary, ELDRITCH_NIGHTMARE_STAFF));

        /**
         * Dismantle
         */
        ItemAction.registerInventory(HARMONISED_NIGHTMARE_STAFF, "dismantle", (player, item) -> dismantle(player, item, HARMONISED_ORB, NIGHTMARE_STAFF));
        ItemAction.registerInventory(VOLATILE_NIGHTMARE_STAFF, "dismantle", (player, item) -> dismantle(player, item, VOLATILE_ORB, NIGHTMARE_STAFF));
        ItemAction.registerInventory(ELDRITCH_NIGHTMARE_STAFF, "dismantle", (player, item) -> dismantle(player, item, ELDRITCH_ORB, NIGHTMARE_STAFF));
    }

}