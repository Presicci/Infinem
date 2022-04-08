package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Tuple;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public enum SlayerUnlock {
    GARGOYLE_SMASHER(Config.GARGOYLE_SMASHER, 120, 0),
    SLUG_SALTER(Config.SLUG_SALTER, 80, 1),
    REPTILE_FREEZER(Config.REPTILE_FREEZER, 90, 2),
    SHROOM_SPRAYER(Config.SHROOM_SPRAYER, 110, 3),
    BROADER_FLETCHING(Config.BROADER_FLETCHING, 300, 7),
    MALEVOLENT_MASQUERADE(Config.SLAYER_UNLOCKED_HELM, 400, 5),
    RING_BLING(Config.RING_BLING, 300, 6),
    SEEING_RED(Config.SEEING_RED, 50, 34),
    I_HOPE_YOU_MITH_ME(Config.I_HOPE_YOU_MITH_ME, 80, 16),
    WATCH_THE_BIRDIE(Config.WATCH_THE_BIRDIE, 80, 17),
    HOT_STUFF(Config.HOT_STUFF, 100, 18),
    REPTILE_GOT_RIPPED(Config.REPTILE_GOT_RIPPED, 75, 30),
    LIKE_A_BOSS(Config.LIKE_A_BOSS, 200, 19),
    BIGGER_AND_BADDER(Config.BIGGER_AND_BADDER, 150, 35),
    KING_BLACK_BONNET(Config.KING_BLACK_BONNET, 1000, 31),
    KALPHITE_KHAT(Config.KALPHITE_KHAT, 1000, 32),
    UNHOLY_HELMET(Config.UNHOLY_HELMET, 1000, 33),
    DARK_MANTLE(Config.DARK_MANTLE, 1000, 38),
    UNDEAD_HEAD(Config.UNDEAD_HEAD, 1000, 42),
    USE_MORE_HEAD(Config.USE_MORE_HEAD, 1000, 45),
    DULY_NOTED(Config.UNLOCK_DULY_NOTED, 200, 37),
    STOP_THE_WYVERN(Config.STOP_THE_WYVERN, 500, 43),
    DOUBLE_TROUBLE(Config.DOUBLE_TROUBLE, 500, 44),
    BASILOCKED(Config.BASILOCKED, 80, 47),

    NEED_MORE_DARKNESS(Config.NEED_MORE_DARKNESS, 100, 4, true),
    ANKOU_VERY_MUCH(Config.ANKOU_VERY_MUCH, 100, 8, true),
    SUQANOTHER_ONE(Config.SUQ_ANOTHER_ONE, 100, 9, true),
    FIRE_AND_DARKNESS(Config.FIRE_AND_DARKNESS, 50, 10, true),
    PEDAL_TO_THE_METALS(Config.PEDAL_TO_THE_METALS, 100, 11, true),
    I_REALLY_MITH_YOU(Config.I_REALLY_MITH_YOU, 120, 23, true),
    ADA_MIND_SOME_MORE(Config.ADA_MIND_SOME_MORE, 100, 40, true),
    RUUUUUNE(Config.RUUUUUNE, 100, 41, true),
    SPIRITUAL_FERVOUR(Config.SPIRITUAL_FERVOUR, 100, 12, true),
    BIRDS_OF_A_FEATHER(Config.BIRDS_OF_A_FEATHER, 100, 22, true),
    GREATER_CHALLENGER(Config.GREATER_CHALLENGE, 100, 15, true),
    ITS_DARK_IN_HERE(Config.ITS_DARK_IN_HERE, 100, 14, true),
    BLEED_ME_DRY(Config.BLEED_ME_DRY, 75, 20, true),
    SMELL_YA_LATER(Config.SMELL_YA_LATER, 100, 21, true),
    HORRORFIC(Config.HORRORIFIC, 100, 24, true),
    TO_DUST_YOU_SHALL_RETURN(Config.TO_DUST_YOU_SHALL_RETURN, 100, 25, true),
    WYVERNOTHER_ONE(Config.WYVER_NOTHER_ONE, 100, 26, true),
    GET_SMASHED(Config.GET_SMASHED, 100, 27, true),
    NECHS_PLEASE(Config.NECHS_PLEASE, 100, 28, true),
    AUGMENT_MY_ABBIES(Config.AUGMENT_MY_ABBIES, 100, 13, true),
    KRACK_ON(Config.KRACK_ON, 100, 29, true),
    GET_SCABARIGHT_ON_IT(Config.GET_SCABARIGHT_ON_IT, 50, 36, true),
    WYVERNOTHER_TWO(Config.WYVER_NOTHER_TWO, 100, 39, true),
    BASILONGER(Config.BASILONGER, 100, 46, true);

    final Config config;
    final int price;
    final int slot;
    final boolean extension;


    SlayerUnlock(Config config, int price, int slot, boolean extension) {
        this.config = config;
        this.price = price;
        this.extension = extension;
        this.slot = slot;
    }

    SlayerUnlock(Config config, int price, int slot) {
        this(config, price, slot, false);
    }

    void toggle(Player player) {
        if (config.get(player) == 1) {
            config.set(player, 0);
        } else if (Config.SLAYER_POINTS.get(player) < price) {
            player.sendMessage("You don't have enough slayer points to make that purchase.");
        } else {
            config.set(player, 1);
            Config.SLAYER_POINTS.set(player, Config.SLAYER_POINTS.get(player) - price);
            player.sendMessage((extension ? "Extension" : "Unlock") + " purchased.");
            if (this == SlayerUnlock.BIGGER_AND_BADDER)
                player.getTaskManager().doLookupByUUID(106, 1); // Unlock Bigger and Badder
            player.sendMessage("" + toString());
        }
    }





    private static final Map<Integer, SlayerUnlock> UNLOCKS = new HashMap<>();

    static {
        for (SlayerUnlock slayerUnlock : values()) {
            UNLOCKS.put(slayerUnlock.slot, slayerUnlock);
        }
        InterfaceHandler.register(426, handler ->  {
            handler.actions[8] = (SlotAction) SlayerUnlock::handleUnlock;
            handler.actions[23] = (DefaultAction) SlayerUnlock::buyItem;
        });
    }

    /**
     * Handles unlocking upgrades from the shop.
     * @param player The player unlocking something.
     * @param slot The slot of the unlock.
     */
    private static void handleUnlock(Player player, int slot) {
        if (slot == 35) {
            player.sendMessage("Superior slayer monsters are not yet available.");
        } else if (slot == 56) {
            extendAll(player);
        } else if (slot == 48) {
            cancelTask(player);
        } else if (slot == 49) {
            blockTask(player);
        } else if (slot >= 50 && slot <= 55) {
            unblockTask(player, slot - 50);
        }
        SlayerUnlock unlock = UNLOCKS.get(slot);
        if (unlock != null) {
            unlock.toggle(player);
        }
    }

    /**
     * Handles the extend all tasks button.
     * @param player The player extending all tasks.
     */
    private static void extendAll(Player player) {
        int cost = UNLOCKS.values().stream()
                .filter(unlock -> unlock.extension && unlock.config.get(player) == 0)
                .mapToInt(unlock -> unlock.price)
                .sum();
        int pts = Config.SLAYER_POINTS.get(player);
        if (pts < cost) {
            player.sendMessage("You do not have enough points to make that purchase.");
            return;
        }
        Config.SLAYER_POINTS.set(player, pts - cost);
        UNLOCKS.values().stream()
                .filter(unlock -> unlock.extension && unlock.config.get(player) == 0)
                .forEach(unlock -> unlock.config.set(player, 1));
        player.sendMessage("Purchase complete, all tasks extended.");
    }

    private static void unblockTask(Player player, int slot) {
        Config.BLOCKED_TASKS[slot].set(player, 0);
    }

    /**
     * Handles the cancelling of a task.
     * @param player The player cancelling the task.
     */
    private static void cancelTask(Player player) {
        SlayerCreature task = SlayerCreature.lookup(Config.SLAYER_TASK_1.get(player));
        if (task == null) {
            player.sendMessage("You don't have a slayer task to block.");
            return;
        }
        if (Config.SLAYER_POINTS.get(player) < 30) {
            player.sendMessage("You don't have enough slayer points to cancel your task.");
            return;
        }
        Config.SLAYER_POINTS.set(player, Config.SLAYER_POINTS.get(player) - 30);
        Config.SLAYER_TASK_AMOUNT.set(player, 0);
        Config.SLAYER_TASK_1.set(player, 0);
        player.slayerTaskRemaining = 0;
        player.sendMessage("You have successfully cancelled your task.");
    }

    /**
     * Handles the blocking of a task.
     * @param player The player blocking the task.
     */
    private static void blockTask(Player player) {
        SlayerCreature task = SlayerCreature.lookup(Config.SLAYER_TASK_1.get(player));
        if (task == null) {
            player.sendMessage("You don't have a slayer task to block.");
            return;
        }
        if (Config.SLAYER_POINTS.get(player) < 100) {
            player.sendMessage("You need 100 points to block your task.");
            return;
        }
        if (task == SlayerCreature.BOSSES) {
            return;
        }
        // Grab our current task ID
        final int taskId = Config.SLAYER_TASK_1.get(player);

        // Check if the player has already blocked this task
        if (isBlocked(player, taskId)) {
            player.sendMessage("You are already blocking " + SlayerCreature.taskName(player, taskId) + ".");
            return;
        }
        // Check for an available slot and block
        for (Config i : Config.BLOCKED_TASKS) {
            if (i.get(player) == 0) {
                i.set(player, taskId);

                Config.SLAYER_TASK_AMOUNT.set(player, 0);
                player.slayerTaskRemaining = 0;
                Config.SLAYER_TASK_1.set(player, 0);

                int pts = Config.SLAYER_POINTS.get(player) - 100;

                Config.SLAYER_POINTS.set(player, pts);
                player.sendMessage("You have successfully blocked your task.");
                return;
            }
        }
    }

    /**
     * Opens the reward shop.
     * @param player The player opening the shop.
     */
    public static void openRewards(Player player) {
        //Slayer.sendTaskInfo(player);
        //Slayer.sendRewardInfo(player);
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 426);
        player.getPacketSender().sendAccessMask(426, 8, 0, 56, 2);
        player.getPacketSender().sendAccessMask(426, 23, 0, 5, 1052);
    }

    /**
     * Checks if a task is blocked by the player.
     * @param player The player being checked.
     * @param taskId The task being checked.
     * @return Returns true if the given task is already blocked
     */
    static boolean isBlocked(Player player, int taskId) {
        for (Config i : Config.BLOCKED_TASKS) {
            if (i.get(player) == taskId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the buying of an item from the reward shop.
     * @param player The player buying the item.
     * @param option The option of the interface call.
     * @param slot The slot of the interface call.
     * @param itemId The itemId of the item.
     */
    private static void buyItem(Player player, int option, int slot, int itemId) {
        if (slot < 0 || slot >= ShopItem.values().length)
            return;
        int itemID, itemPrice, itemAmount;
        ShopItem item = ShopItem.values()[slot];
        itemID = item.id;
        itemPrice = item.price;
        itemAmount = item.buyAmount;

        if (option == 10) {
            player.sendMessage(ItemDef.get(itemID).examine);
            return;
        }
        int pts = Config.SLAYER_POINTS.get(player);
        if (pts < itemPrice) {
            player.sendMessage("You don't have enough slayer points to buy that.");
            return;
        }
        int amount = ((option == 2) ? 1 : ((option == 3) ? 5 : 10));
        if (ItemDef.get(itemID).stackable && !player.getInventory().hasRoomFor(itemID)) {
            player.sendMessage("Not enough space in your inventory.");
        } else if (!ItemDef.get(itemID).stackable && player.getInventory().isFull()) {
            player.sendMessage("Not enough space in your inventory.");
        }
        amount = Math.min(amount, pts / itemPrice);
        player.getInventory().add(itemID, amount * itemAmount);
        Config.SLAYER_POINTS.set(player, pts - (amount * itemPrice));
    }

    /**
     * Opens the slayer gold shop.
     * @param player The player opening the shop.
     * @param npc The slayer master.
     */
    public static void openShop(Player player, NPC npc) {
        npc.getDef().shops.get(0).open(player);
    }

    /**
     * Tuple mapping slayer creature UIDs with configs for that
     * creature's task extension unlock.
     */
    public static final List<Tuple<Integer, Config>> multipliable = Arrays.asList(
            new Tuple<>(66, Config.NEED_MORE_DARKNESS), new Tuple<>(79, Config.ANKOU_VERY_MUCH), new Tuple<>(83, Config.SUQ_ANOTHER_ONE),
            new Tuple<>(27, Config.FIRE_AND_DARKNESS), new Tuple<>(58, Config.PEDAL_TO_THE_METALS), new Tuple<>(59, Config.PEDAL_TO_THE_METALS),
            new Tuple<>(60, Config.PEDAL_TO_THE_METALS), new Tuple<>(89, Config.SPIRITUAL_FERVOUR), new Tuple<>(91, Config.SPIRITUAL_FERVOUR),
            new Tuple<>(42, Config.AUGMENT_MY_ABBIES), new Tuple<>(30, Config.ITS_DARK_IN_HERE), new Tuple<>(29, Config.GREATER_CHALLENGE),
            new Tuple<>(48, Config.BLEED_ME_DRY), new Tuple<>(41, Config.SMELL_YA_LATER), new Tuple<>(94, Config.BIRDS_OF_A_FEATHER),
            new Tuple<>(93, Config.I_REALLY_MITH_YOU), new Tuple<>(80, Config.HORRORIFIC), new Tuple<>(72, Config.WYVER_NOTHER_ONE),
            new Tuple<>(46, Config.GET_SMASHED), new Tuple<>(52, Config.NECHS_PLEASE), new Tuple<>(92, Config.KRACK_ON)
    );

    private enum ShopItem {
        SLAYER_RING(11866, 75, 1),      // Slayer ring
        BROAD_BOLTS(11875, 35, 250),    // Broad bolts
        BROAD_ARROWS(4160, 35, 250),    // Broad arrows
        HERB_SACK(13226, 750, 1),       // Herb sack
        RUNE_POUCH(12791, 1250, 1);      // Rune pouch

        public final int id, price, buyAmount;

        ShopItem(int id, int price, int buyAmount) {
            this.id = id;
            this.price = price;
            this.buyAmount = buyAmount;
        }
    }
}
