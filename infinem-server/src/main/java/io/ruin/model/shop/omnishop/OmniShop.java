package io.ruin.model.shop.omnishop;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.db.DBRowDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/13/2024
 */
public enum OmniShop {
    FORESTRY_SHOP(
            3284,
            new OmniShopItem(   // Forestry kit
                    0, -1, 1, 3285,
                    "This equipable kit bag holds many items commonly used or found while woodcutting. There are two modifications available for it;<br>A log basket and sturdy harness will allow it to hold logs.<br>The clothes pouch allows it to store your lumberjack outfit."),
            new OmniShopItem(   // Secateurs blade
                    1, -1, 20, 3286,
                    "The secateurs blade is an ingredient of the secateurs attachment, which allows you to collect more leaves when chopping trees. You will also need:<br><br>An iron bar<br><br>35 Smithing<br>35 Woodcutting<br><br>Makes 50"),
            new OmniShopItem(   // Ritual mulch
                    2, -1, 150, 3287,
                    "This component is used to make nature offerings which give extra logs while woodcutting. You will also need:<br><br>A high tier herb<br><br>50 Farming<br>68 Woodcutting<br><br>Makes 40"),
            new OmniShopItem(   // Log brace
                    6, -1, 3000, 3290,
                    "The log brace is used to make the sturdy harness. Which allows the forestry kit and log basket to be combined into one tool. You will also need:<br><br>45 Nails<br>2 Rope<br>3 Adamantite bars<br><br>75 Smithing<br>75 Woodcutting"),
            new OmniShopItem(   // Clothes pouch blueprint
                    7, -1, 10000, 3291,
                    "This blueprint is used as a pattern for creating your very own clothes pouch.<br><br>The clothes pouch, when used with a forestry kit, will allow the storage of the lumberjack outfit. You will also need:<br><br>Thread<br>Leather<br><br>50 Crafting<br>50 Woodcutting"),
            new OmniShopItem(   // Cape pouch
                    8, -1, 2500, 3442,
                    "This pouch will allow you to store your woodcutting cape in your forestry kit.<br><br>The forestry kit will inherit the cape's increased chance at bird's nests when worn.<br><br>Requires a woodcutting level of 99 to use."),
            new OmniShopItem(   // Log basket
                    9, -1, 5000, 3292,
                    "The log basket allows you to carry more logs. When opened logs will automatically be deposited into it. The log basket can be combined with the forestry kit to make a single equipable item when used with the sturdy harness."),
            new OmniShopItem(   // Felling axe handle
                    10, -1, 10000, 3433,
                    "A large handle for turning an axe into a felling axe."),
            new OmniShopItem(   // Twitcher's gloves
                    11, -1, 5000, 3434,
                    "These gloves allow the wearer to nimbly dislodge birds' nests, giving them a greater chance of receiving their desired type of nest."),
            new OmniShopItem(   // Funky shaped log
                    12, -1, 15000, 3293,
                    "This log has grown into a pretty funky shape and has been infused with Anima. Feeding it to a beaver will unlock the beaver's ability to change colour when fed other types of logs. The type of log determines the colour that the beaver will adopt."),
            new OmniShopItem(   // Sawmill voucher (x10)
                    13, -1, 150, 3435,
                    "This voucher can be redeemed for an extra plank per log cut at the sawmill.<br><br>Must be presented in person."),
            new OmniShopItem(   // Lumberjack boots
                    14, -1, 1000, 3294,
                    "These boots enhance your learning speed in the woodcutting skill, increasing woodcutting XP by 0.2% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Lumberjack hat
                    15, -1, 1200, 3295,
                    "This hat enhances your learning speed in the woodcutting skill, increasing woodcutting XP by 0.4% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Lumberjack legs
                    16, -1, 1300, 3296,
                    "These legs enhance your learning speed in the woodcutting skill, increasing woodcutting XP by 0.2% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Lumberjack top
                    17, -1, 1500, 3297,
                    "This top enhances your learning speed in the woodcutting skill, increasing woodcutting XP by 0.4% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Forestry boots
                    18, -1, 1, 3298,
                    "These boots enhance your learning speed in the woodcutting skill, increasing woodcutting XP by 0.2% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Forestry hat
                    19, -1, 1, 3299,
                    "This hat enhances your learning speed in the woodcutting skill, increasing woodcutting XP by 0.4% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Forestry legs
                    20, -1, 1, 3300,
                    "These legs enhance your learning speed in the woodcutting skill, increasing woodcutting XP by 0.2% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped."),
            new OmniShopItem(   // Forestry top
                    21, -1, 1, 3301,
                    "This top enhances your learning speed in the woodcutting skill, increasing woodcutting XP by 0.4% while worn. An extra 0.5% boost is given if all four parts of the lumberjack or forestry outfit are equipped.")
    ),
    WORM_TONGUES_WARES(
            3764,
            new OmniShopItem(0, -1, 100, 3772, "A pack containing 100 crystals of amylase."),
            new OmniShopItem(1, -1, 40, 3773, "A teleport to the Colossal Wyrm Remains in the Avium Savannah."),
            new OmniShopItem(2, -1, 650, 3774, "Gives your graceful kit a Varlamore-themed makeover."),
            new OmniShopItem(3, -1, 900, 3775, "A brittle looking acorn. Perhaps a squirrel would be interested in this?")
    );

    private final int rowId;
    private final Map<Integer, OmniShopItem> items;

    OmniShop(int rowId, OmniShopItem... items) {
        this.rowId = rowId;
        this.items = new HashMap<>();
        for (OmniShopItem item : items) {
            this.items.put(item.getSlot(), item);
        }
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.INVENTORY, 806);
        player.openResizeableInterface(InterfaceType.MAIN, 819);
        player.getPacketSender().sendClientScript(917, "ii", -1, -3);
        player.getPacketSender().sendClientScript(7140, "ii", rowId, -1);
        player.getPacketSender().sendClientScript(7246, "i", rowId);
        // Stock
        player.getPacketSender().sendAccessMask(819, 33, 0, 288, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp6, AccessMasks.ClickOp10);
        // ?
        player.getPacketSender().sendAccessMask(819, 36, 0, 8, AccessMasks.ClickOp1);
        // Inventory
        player.getPacketSender().sendAccessMask(806, 0, 0, 28, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp10, AccessMasks.DragDepth1, AccessMasks.DragTargetable);
        // ?
        player.getPacketSender().sendAccessMask(806, 1, 0, 0, AccessMasks.ClickOp1);
        player.putTemporaryAttribute(SHOP_KEY, this);
    }

    private static final String SHOP_KEY = "OMNI_SHOP";
    private static final String ITEM_KEY = "OMNI_ITEM";

    private static void clickItem(Player player, int option, int slot) {
        OmniShop shop = player.getTemporaryAttributeOrDefault(SHOP_KEY, null);
        if (shop == null) return;
        int index = slot / 12;
        OmniShopItem item = shop.items.get(index);
        if (item == null) return;
        int currentItem = player.getTemporaryAttributeOrDefault(ITEM_KEY, -1);
        if (currentItem == index) return;
        int itemId = (int) DBRowDefinition.get(item.getRowId()).getColumnValue(0);
        if (option == 1) {
            player.getPacketSender().sendClientScript(7146, "iiiiiiisi",
                    shop.rowId,
                    -1, // ?
                    item.getSlot(),
                    item.getStock(),
                    0,  // ?
                    item.getCost(),
                    item.getRowId(),
                    item.getDescription(),
                    1   // Reset scroll, 0 for off
            );
            player.putTemporaryAttribute(ITEM_KEY, index);
        } else if (option == 10) {
            ItemDefinition def = ItemDefinition.get(itemId);
            if (def != null) def.examine(player);
        }
    }

    private static void clickInventoryItem(Player player, int option, int slot, int itemId) {
        // slot, shopdbrow, amt, 0?, value, -1?, examine
        OmniShop shop = player.getTemporaryAttributeOrDefault(SHOP_KEY, null);
        if (shop == null) return;
        player.getPacketSender().sendClientScript(7253, "iiiiiis", slot, shop.rowId, player.getInventory().getAmount(itemId), 0, -1, -1, ItemDefinition.get(itemId).examine);
    }

    static {
        InterfaceHandler.register(819, h -> {
            // Changing between views removes selected item
            h.actions[7] = (SimpleAction) player -> player.removeTemporaryAttribute(ITEM_KEY);
            h.actions[33] = (DefaultAction) (player, option, slot, itemId) -> clickItem(player, option, slot);
            h.closedAction = (player, i) -> {
                player.removeTemporaryAttribute(SHOP_KEY);
                player.removeTemporaryAttribute(ITEM_KEY);
            };
        });
        InterfaceHandler.register(806, h -> {
            h.actions[0] = (DefaultAction) OmniShop::clickInventoryItem;
        });
    }
}
