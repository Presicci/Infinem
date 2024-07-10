package io.ruin.model.item.containers.collectionlog;

import com.google.gson.annotations.Expose;
import io.ruin.model.inter.AccessMasks;
import io.ruin.utility.Color;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class CollectionLog {

    private static final String CATEGORY_KEY = "CL_CAT";
    private static final String ENTRY_KEY = "CL_ENT";
    public static final int BLANK_ID = -1;
    public static int[] bossParams = {40697866, 40697867, 40697868, 40697869};
    private static final String SEARCH_LETTERS = "abcdefghijklmnopqrstuvwxyz \t";
    @Expose @Getter
    private Map<Integer, Integer> collected = new HashMap<>();

    private Player player;

    public void init(Player player) {
        this.player = player;
    }

    public int getCollected(int itemId) {
        return collected.getOrDefault(itemId,0);
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.COLLECTION_LOG);
        // Open last tab that was open
        CollectionLogCategory category = CollectionLogCategory.values()[player.getTemporaryAttributeIntOrZero(CATEGORY_KEY)];
        player.getPacketSender().sendClientScript(2389, "i", category.ordinal());
        selectEntry(player,player.getTemporaryAttributeIntOrZero(ENTRY_KEY), category);
        category.sendAccessMasks(player);

        for (int index = 41; index < 70; index++) {
            player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, index, -1, -1, AccessMasks.ClickOp1);
        }
    }

    public void sendTab(Player player, CollectionLogCategory category) {
        player.getPacketSender().sendClientScript(2389, "i", category.ordinal());
        selectEntry(player,0, category);
        category.sendAccessMasks(player);
        player.putTemporaryAttribute(CATEGORY_KEY, category.ordinal());
    }

    public void collect(int id) {
        collect(id, 1);
    }

    /**
     * Collects an item into the collection log.
     *
     * @param id     The item id.
     * @param amount The amount.
     */
    public void collect(int id, int amount) {
        collect(new Item(id, amount));
    }

    /**
     * Collects the items into the collection log.
     *
     * @param items The items.
     */
    public boolean collect(Item...items) {
        boolean added = false;
        for (Item item : items) {
            if (collect(item))
                added = true;
         }
        return added;
    }

    /**
     * Collects an item into the collection log if it isn't already collected.
     *
     * @param item The item being collected.
     */
    public boolean collectIfNotCollected(Item item) {
        if (collected.containsKey(item.getId())) return false;
        return collect(item);
    }

    /**
     * Collects an item into the collection log.
     *
     * @param item The item being collected.
     */
    private boolean collect(Item item) {
        if (!item.getDef().collectable) {
            return false;
        }
        if (item.getAmount() <= 0) {
            return false;
        }
        int amount = collected.getOrDefault(item.getId(),0);
        collected.put(item.getId(), amount + item.getAmount());
        if (amount == 0) {
            if ((Config.COLLECTION_LOG_SETTINGS.get(player) & 2) == 2) {
                player.getPacketSender().sendPopupNotification(0xff981f, "Collection log", "New item:<br><br>" + Color.WHITE.wrap(item.getDef().name));
            }
            if ((Config.COLLECTION_LOG_SETTINGS.get(player) & 1) == 1) {
                player.sendMessage("New item added to your collection log: " + Color.RED.wrap(item.getDef().name));
            }
        }
        return true;
    }

    public void clearCollectedItems() {
        collected.clear();
    }

    public static void handleClose(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
    }

    private void selectEntry(Player player, int slot, CollectionLogCategory category) {
        category.sendKillCount(player, slot);
        category.sendItems(player, slot);
        player.getPacketSender().sendClientScript(2730, "iiiiii", category.getParams()[0], category.getParams()[1], category.getParams()[2], category.getParams()[3], category.getCategoryStruct(), slot);
        player.putTemporaryAttribute(ENTRY_KEY, slot);
    }

    private static OptionsDialogue get(Player player, NPC npc) {
        return new OptionsDialogue("Would you like a Collection log?",
                new Option("Yes", () -> {
                    player.dialogue(
                            new PlayerDialogue("Yes, that sounds helpful!"),
                            new NPCDialogue(npc,"There! Now you will be able to see the true beauty of everything you collect on your adventures.").onDialogueOpened(() -> player.getInventory().add(ItemID.COLLECTION_LOG)),
                            new PlayerDialogue("Thanks!")
                    );
                }),
                new Option("No thanks.", () -> {
                    player.dialogue(
                            new PlayerDialogue("No thanks. I think I'll pass."),
                            new NPCDialogue(npc, "I should've guessed you wouldn't understand the true beauty of a good collection!")
                    );
                })
        );
    }

    static {
        NPCAction.register(8491, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "It's beautiful, isn't it?"),
                    new PlayerDialogue("What is?"),
                    new NPCDialogue(npc,"Everything! The wonders right here in this museum, collected from all corners of the land."),
                    new PlayerDialogue("I guess it is, you're right."),
                    new NPCDialogue(npc,"Matter of fact, I consider myself quite the collector. I keep a record of just about everything that I find!"),
                    get(player, npc));
        });

        NPCAction.register(8491,"get log", (player, npc) -> player.dialogue(get(player,npc)));

        ItemAction.registerInventory(ItemID.COLLECTION_LOG, 1, (player, item) -> {
            player.getCollectionLog().open(player);
        });

        InterfaceHandler.register(Interface.COLLECTION_LOG, h -> {
            h.actions[4] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogCategory.BOSS);
            h.actions[5] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogCategory.RAIDS);
            h.actions[6] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogCategory.CLUES);
            h.actions[7] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogCategory.MINIGAMES);
            h.actions[8] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogCategory.OTHER);

            h.actions[11] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogCategory.BOSS);
            };

            h.actions[15] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogCategory.RAIDS);
            };

            h.actions[31] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogCategory.CLUES);
            };

            h.actions[26] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogCategory.MINIGAMES);
            };

            h.actions[33] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogCategory.OTHER);
            };

            //h.actions[20] = (SimpleAction) p -> handleCombatAchievementsButton(p);

            h.actions[79] = (SimpleAction) CollectionLog::handleClose;

            for (int index = 41; index < 69; index++) {
                //h.actions[index] = (SimpleAction) player -> player.getPacketSender().sendClientScript(4100, "iiii", 4151, 5, 1, 476);
            }

            h.closedAction = (p, i) -> {
                p.getPacketSender().sendClientScript(101, "i", 11);
            };
        });
    }

    /* adds up all the clue count from beginner -> master */
    public static int addSumMultipleClues(Player player) {
        int sum1 = PlayerCounter.BEGINNER_CLUES_COMPLETED.get(player);
        int sum2 = PlayerCounter.EASY_CLUES_COMPLETED.get(player);
        int sum3 = PlayerCounter.MEDIUM_CLUES_COMPLETED.get(player);
        int sum4 = PlayerCounter.HARD_CLUES_COMPLETED.get(player);
        int sum5 = PlayerCounter.ELITE_CLUES_COMPLETED.get(player);
        int sum6 = PlayerCounter.MASTER_CLUES_COMPLETED.get(player);
        return sum1+sum2+sum3+sum4+sum5+sum6;
    }
}