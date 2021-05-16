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

        NARDAH(12402, new Bounds(3419, 2916, 3421, 2918, 0), 5, 21361, 21374),
        DIGSITE(12403, new Bounds(3324, 3411, 3327, 3414, 0), 9, 21362, 21375),
        FELDIP_HILLS(12404, new Bounds(2540, 2924, 2542, 2926, 0), 13, 21363, 21376),
        LUNAR_ISLE(12405, new Bounds(2098, 3913, 2101, 3915, 0), 17, 21364,21377),
        MORT_TON(12406, new Bounds(3487, 3287, 3489, 3289, 0), 21, 21365, 21378),
        PEST_CONTROL(12407, new Bounds(2657, 2658, 2659, 2660, 0), 25, 21366, 21379),
        PISCATORIS(12408, new Bounds(2338, 3648, 2341, 3651, 0), 29, 21367, 21380),
        TAI_BWO_WANNAI(12409, new Bounds(2787, 3064, 2791, 3067, 0), 33, 21368, 21381),
        ELF_CAMP(12410, new Bounds(2202, 3352, 2206, 3354, 0), 37, 21369, 21382),
        MOS_LE_HARMLESS(12411, new Bounds(3684, 2968, 3687, 2971, 0), 41, 21370, 21383),
        LUMBER_YARD(12642, new Bounds(3306, 3488, 3309, 3490, 0), 45, 21371, 21384),
        ZAL_ANDRA(12938, new Bounds(2194, 3055, 2197, 3057, 0), 49, 21372, 21385),
        KEY_MASTER(13249, new Bounds(1312, 1249, 1315, 1251, 0), 53, 21373, 21386),
        REVENANT_SCROLL(21802, new Bounds(3128, 3830, 3131, 3835, 0), 57, 21789, 21790),
        WATSON(23387, new Bounds(1644, 3575, 1646, 3580, 0), 61, 7674, 0);

        public final int id, componentId, emptyId, defaultId;

        private Config config;

        public final Bounds bounds;

        TeleportScroll(int id, Bounds bounds, int componentId, int emptyId, int defaultId) {
            this.id = id;
            this.bounds = bounds;
            this.componentId = componentId;
            this.emptyId = emptyId;
            this.defaultId = defaultId;
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

    private Player player;  // TODO Get varpbits from osrs
    @Expose @Setter private TeleportScroll defaultScroll = null;
    @Expose private HashMap<TeleportScroll, Integer> storedScrolls;

    private static final int SCROLLBOOK = 21389;
    private static final int INTERFACE = 597;

    private static final int MAX_SCROLL_AMT = 1000;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MasterScrollBook() {
        this.storedScrolls = new HashMap<TeleportScroll, Integer>();
        for (TeleportScroll scroll : TeleportScroll.values()) {
            this.storedScrolls.put(scroll, 0);
        }
    }

    private void sendInterface() {
        for (TeleportScroll scroll : TeleportScroll.values()) {
            int scrollAmt = storedScrolls.get(scroll);
            if (scrollAmt > 0) {
                player.getPacketSender().sendString(INTERFACE, scroll.componentId + 2, "<col=ffff00>" + scrollAmt);
                if (defaultScroll != null && scroll == defaultScroll) {
                    player.getPacketSender().sendItem(INTERFACE, scroll.componentId + 1, scroll.defaultId, 1);
                } else {
                    player.getPacketSender().sendItem(INTERFACE, scroll.componentId + 1, scroll.id, 1);
                }
                player.sendMessage("sent");
            }
        }
        player.openInterface(InterfaceType.MAIN, INTERFACE);
    }

    private boolean teleport(TeleportScroll scroll) {
        if (storedScrolls.get(scroll) <= 0) {
            player.sendMessage("You do not have any of that scroll left.");
            return false;
        }
        scroll.bookTeleport(player);
        storedScrolls.put(scroll, storedScrolls.get(scroll) - 1);
        return true;
    }

    private void deposit(Item primary, Item secondary) {
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
        int storedAmt = storedScrolls.get(scroll);

        if (storedAmt >= MAX_SCROLL_AMT) {
            player.sendMessage("Your scroll storage for that scroll is full!");
            return;
        }
        if ((storedAmt + intAmt) > MAX_SCROLL_AMT) {
            intAmt = MAX_SCROLL_AMT - storedAmt;
        }
        player.getInventory().remove(secondary.getId(), intAmt);
        storedScrolls.put(scroll, storedAmt + intAmt);
    }

    private void withdraw(TeleportScroll scroll) {
        if (storedScrolls.get(scroll) <= 0) {
            player.sendMessage("You do not have any of this scroll stored.");
            return;
        }
        if (player.getInventory().isFull() && !player.getInventory().contains(scroll.id)) {
            player.sendMessage("Your inventory is full.");
            return;
        }
        int storedAmt = storedScrolls.get(scroll);
        int withdrawAmt = storedAmt;
        long invAmt = player.getInventory().getAmount(scroll.id);
        if (invAmt + storedAmt > Integer.MAX_VALUE) {
            withdrawAmt = (int) (Integer.MAX_VALUE - invAmt);
        }
        player.getInventory().add(scroll.id, withdrawAmt);
        storedScrolls.put(scroll, storedAmt - withdrawAmt);
    }

    static {
        ItemAction.registerInventory(SCROLLBOOK, "open", (player, item) -> {
            player.getMasterScrollBook().sendInterface();
        });
        for (TeleportScroll scroll : TeleportScroll.values()) {
            ItemItemAction.register(SCROLLBOOK, scroll.id, (player, primary, secondary) -> {
                player.getMasterScrollBook().deposit(primary, secondary);
            });
        }
        InterfaceHandler.register(INTERFACE, h -> {
            for (TeleportScroll scroll : TeleportScroll.values()) {
                h.actions[scroll.componentId] = (OptionAction) (player, option) -> {
                    if (option == 1) {  // Activate
                        player.getMasterScrollBook().teleport(scroll);
                    } else if (option == 2) {   // Set default
                        player.getMasterScrollBook().setDefaultScroll(scroll);
                    } else {    // Withdraw
                        player.getMasterScrollBook().withdraw(scroll);
                    }
                };
            }
        });
    }
}
