package io.ruin.model.item.actions.impl;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Bounds;
import lombok.Setter;

import java.util.HashMap;

public class MasterScrollBook {

    public enum TeleportScroll {

        NARDAH(12402, new Bounds(3419, 2916, 3421, 2918, 0), 5, Config.NARDAH_SCROLLS),
        DIGSITE(12403, new Bounds(3324, 3411, 3327, 3414, 0), 9, Config.DIGSITE_SCROLLS),
        FELDIP_HILLS(12404, new Bounds(2540, 2924, 2542, 2926, 0), 13, Config.FELDIP_SCROLLS),
        LUNAR_ISLE(12405, new Bounds(2098, 3913, 2101, 3915, 0), 17, Config.LUNAR_SCROLLS),
        MORT_TON(12406, new Bounds(3487, 3287, 3489, 3289, 0), 21, Config.MORTTON_SCROLLS),
        PEST_CONTROL(12407, new Bounds(2657, 2658, 2659, 2660, 0), 25, Config.PEST_CONTROL_SCROLLS),
        PISCATORIS(12408, new Bounds(2338, 3648, 2341, 3651, 0), 29, Config.PISCATORIS_SCROLLS),
        TAI_BWO_WANNAI(12409, new Bounds(2787, 3064, 2791, 3067, 0), 33, Config.TAI_BWO_WANNAI_SCROLLS),
        ELF_CAMP(12410, new Bounds(2202, 3352, 2206, 3354, 0), 37, Config.IORWERTH_SCROLLS),
        MOS_LE_HARMLESS(12411, new Bounds(3684, 2968, 3687, 2971, 0), 41, Config.MOS_LEHARMLESS_SCROLLS),
        LUMBER_YARD(12642, new Bounds(3306, 3488, 3309, 3490, 0), 45, Config.LUMBERYARD_SCROLLS),
        ZAL_ANDRA(12938, new Bounds(2194, 3055, 2197, 3057, 0), 49, Config.ZUL_ANDRA_SCROLLS),
        KEY_MASTER(13249, new Bounds(1312, 1249, 1315, 1251, 0), 53, Config.KEY_MASTER_SCROLLS),
        REVENANT_SCROLL(21802, new Bounds(3128, 3830, 3131, 3835, 0), 57, Config.REV_SCROLLS),
        WATSON(23387, new Bounds(1644, 3576, 1646, 3580, 0), 61, Config.WATSON_SCROLLS);

        public final int id, componentId;

        private Config config;

        public final Bounds bounds;

        TeleportScroll(int id, Bounds bounds, int componentId, Config config) {
            this.id = id;
            this.bounds = bounds;
            this.componentId = componentId;
            this.config = config;
        }

        private void teleport(Player player, Item scroll) {
            player.getMovement().startTeleport(event -> {
                player.animate(3864);
                player.graphics(1039);
                player.privateSound(200, 0, 10);
                scroll.remove(1);
                event.delay(2);
                player.getMovement().teleport(bounds);
            });
        }

        private void bookTeleport(Player player) {
            player.getMovement().startTeleport(event -> {
                player.animate(3864);
                player.graphics(1039);
                player.privateSound(200, 0, 10);
                event.delay(2);
                player.getMovement().teleport(bounds);
            });
        }

        static {
            for(TeleportScroll scroll : values())
                ItemAction.registerInventory(scroll.id, "teleport", scroll::teleport);
        }
    }

    private static final int SCROLLBOOK = 21389;
    private static final int INTERFACE = 597;

    private static final int MAX_SCROLL_AMT = 1000;

    private static void sendInterface(Player player) {
        player.openInterface(InterfaceType.MAIN, INTERFACE);
    }

    private static boolean teleport(Player player, TeleportScroll scroll) {
        int scrolls = scroll.config.get(player);
        if (scrolls <= 0) {
            player.sendMessage("You do not have any of that scroll left.");
            return false;
        }
        scroll.bookTeleport(player);
        scroll.config.set(player, scrolls - 1);
        return true;
    }

    private static void deposit(Player player, Item primary, Item secondary) {
        TeleportScroll scroll = null;
        for (TeleportScroll s : TeleportScroll.values()) {
            if (s.id == secondary.getId()) {
                scroll = s;
                break;
            }
        }
        if (scroll == null) {
            return;
        }
        int intAmt = player.getInventory().getAmount(scroll.id);
        int storedAmt = scroll.config.get(player);
        int maxAmt = scroll == TeleportScroll.WATSON ? 250 : MAX_SCROLL_AMT;
        if (storedAmt >= maxAmt) {
            player.sendMessage("Your scroll storage for that scroll is full!");
            return;
        }
        if ((storedAmt + intAmt) > maxAmt) {
            intAmt = maxAmt - storedAmt;
        }
        player.getInventory().remove(secondary.getId(), intAmt);
        scroll.config.set(player, storedAmt + intAmt);
    }

    private static void withdraw(Player player, TeleportScroll scroll) {
        int scrolls = scroll.config.get(player);
        if (scrolls <= 0) {
            player.sendMessage("You do not have any of this scroll stored.");
            return;
        }
        if (player.getInventory().isFull() && !player.getInventory().contains(scroll.id)) {
            player.sendMessage("Your inventory is full.");
            return;
        }
        int storedAmt = scrolls;
        int withdrawAmt = storedAmt;
        long invAmt = player.getInventory().getAmount(scroll.id);
        if (invAmt + storedAmt > Integer.MAX_VALUE) {
            withdrawAmt = (int) (Integer.MAX_VALUE - invAmt);
        }
        player.getInventory().add(scroll.id, withdrawAmt);
        scroll.config.set(player, storedAmt - withdrawAmt);
    }

    static {
        ItemAction.registerInventory(SCROLLBOOK, "open", (player, item) -> {
            MasterScrollBook.sendInterface(player);
        });
        ItemAction.registerInventory(SCROLLBOOK, "teleport", (player, item) -> {
            int scrollIndex = Config.DEFAULT_SCROLL.get(player) - 1;
            if (scrollIndex < 0) {
                player.sendMessage("You do not have a default scroll set.");
                return;
            }
            MasterScrollBook.teleport(player, TeleportScroll.values()[scrollIndex]);
        });
        ItemAction.registerInventory(SCROLLBOOK, "remove default", (player, item) -> {
            Config.DEFAULT_SCROLL.set(player, 0);
        });
        for (TeleportScroll scroll : TeleportScroll.values()) {
            ItemItemAction.register(SCROLLBOOK, scroll.id, MasterScrollBook::deposit);
        }
        InterfaceHandler.register(INTERFACE, h -> {
            for (TeleportScroll scroll : TeleportScroll.values()) {
                h.actions[scroll.componentId] = (OptionAction) (player, option) -> {
                    if (option == 1) {  // Activate
                        MasterScrollBook.teleport(player, scroll);
                    } else if (option == 2) {   // Set default
                        Config.DEFAULT_SCROLL.set(player, scroll.ordinal() + 1);
                    } else {    // Withdraw
                        MasterScrollBook.withdraw(player, scroll);
                    }
                };
            }
        });
    }
}
