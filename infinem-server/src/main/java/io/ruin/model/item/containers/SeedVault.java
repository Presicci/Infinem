package io.ruin.model.item.containers;

import io.ruin.cache.InventoryDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.*;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Optional;


/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/2/2021
 */
public class SeedVault extends ItemContainer {

    private static final int[] seeds = {
            // Allotment
            5318,   // Potato
            5319,   // Onion
            5324,   // Cabbage
            5322,   // Tomato
            5320,   // Sweetcorn
            5323,   // Strawberry
            5321,   // Watermelon
            22879,  // Snape grass

            // Flower
            5096,   // Marigold
            5097,   // Rosemary
            5098,   // Nasturtium
            5099,   // Woad
            5100,   // Limpwurt
            22887,  // White lily

            // Herb
            5291,   // Guam
            5292,   // Marrentill
            5293,   // Tarromin
            5294,   // Harralander
            5295,   // Ranarr
            5296,   // Toadflax
            5297,   // Irit
            5298,   // Avantoe
            5299,   // Kwuarm
            5300,   // Snapdragon
            5301,   // Cadantine seed
            5302,   // Lantadyme
            5303,   // Dwarf weed
            5304,   // Torstol

            // Hops
            5305,   // Barley
            5307,   // Hammerstone
            5308,   // Asgarnian
            5306,   // Jute
            5309,   // Yanillian
            5310,   // Krandorian
            5311,   // Wildblood

            // Bush
            5101,   // Redberry
            5102,   // Cadavaberry
            5103,   // Dwellberry
            5104,   // Jangerberry
            5105,   // Whiteberry
            5106,   // Poison ivy

            // Tree
            5312,   // Acorn
            5313,   // Willow
            21486,  // Teak
            5314,   // Maple
            21488,  // Mahogany
            5315,   // Yew
            5316,   // Magic
            22871,  // Redwood

            // Fruit tree
            5283,   // Apple
            5284,   // Banana
            5285,   // Orange
            5286,   // Curry
            5287,   // Pineapple
            5288,   // Papaya
            5289,   // Palm
            22877,  // Dragonfruit

            // Exotic
            21490,  // Seaweed
            13657,  // Grape
            5282,   // Mushroom
            5280,   // Cactus
            5281,   // Belladonna
            22873,  // Potato cactus
            22875,  // Hespori
            5290,   // Calquat
            5317,   // Spirit
            22869,  // Celastrus
            23661,  // Crystal

            // Anima
            22881,  // Attas
            22883,  // Iasor
            22885,  // Kronos

            // Sapling
            5370,   // Oak
            5496,   // Apple
            5371,   // Willow
            5497,   // Banana
            21477,  // Teak
            5498,   // Orange
            5499,   // Curry
            5372,   // Maple
            5500,   // Pineapple
            21480,  // Mahogany
            5501,   // Papaya
            5373,   // Yew
            5502,   // Palm
            5503,   // Calquat
            5374,   // Magic
            22866,  // Dragonfruit
            5375,   // Spirit
            22856,  // Celastrus
            22859,  // Redwood
            23659   // Crysal
    };

    public void sendVault() {
        if (player.getGameMode().isUltimateIronman()) {
            player.sendMessage("Ultimate ironmen can not use the seed vault.");
            return;
        }
        if(player.getBankPin().requiresVerification(p -> sendVault()))
            return;
        player.openInterface(InterfaceType.MAIN, Interface.SEED_VAULT);
        player.openInterface(InterfaceType.INVENTORY, Interface.SEED_VAULT_INVENTORY);
        player.getPacketSender().sendItems(Interface.SEED_VAULT, 15, 626, this.items);
        player.getPacketSender().sendAccessMask(Interface.SEED_VAULT, 15, 0, 90,
                AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5,
                AccessMasks.ClickOp6, AccessMasks.ClickOp7, AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10,
                AccessMasks.DragDepth2, AccessMasks.DragTargetable);
        player.getPacketSender().sendAccessMask(Interface.SEED_VAULT_INVENTORY, 1, 0, 27,
                AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5,
                AccessMasks.ClickOp6, AccessMasks.ClickOp7, AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10,
                AccessMasks.DragDepth1, AccessMasks.DragTargetable);
    }

    public void deposit(Item item, int amount) {
        boolean found = false;
        if (Arrays.stream(seeds).anyMatch(i -> i == item.getId())) {
            if (item.move(item.getId(), amount, this) == 0) {
                player.sendMessage("Not enough space in your seed vault.");
            }
            found = true;
        } else if (Arrays.stream(seeds).anyMatch(i -> i == item.getDef().notedId)) {
            if (item.move(item.getId(), amount, this) == 0) {
                player.sendMessage("Not enough space in your seed vault.");
            }
            int amt = this.findItem(item.getId()).getAmount();
            this.findItem(item.getId()).remove();
            this.add(new Item(item.getDef().notedId, amt));
            found = true;
        }
        if (!found) {
            player.sendMessage("You can only put seeds in the seed vault.");
        }
    }

    public void depositAll() {
        for (Item item : player.getInventory().getItems()) {
            if (item == null) {
                continue;
            }
            if (Arrays.stream(seeds).anyMatch(i -> i == item.getId())) {
                if (item.move(item.getId(), item.getAmount(), this) == 0) {
                    player.sendMessage("Not enough space in your seed vault for <col=ffffff>" + item.getDef().name.toLowerCase() + "</col> .");
                }
            } else if (Arrays.stream(seeds).anyMatch(i -> i == item.getDef().notedId)) {
                if (item.move(item.getId(), item.getAmount(), this) == 0) {
                    player.sendMessage("Not enough space in your seed vault for <col=ffffff>" + item.getDef().name.toLowerCase() + "</col> .");
                }
                int amt = this.findItem(item.getId()).getAmount();
                this.findItem(item.getId()).remove();
                this.add(new Item(item.getDef().notedId, amt));
            }
        }
    }

    public void withdraw(Item item, int amount) {
        if (item.move(item.getId(), amount, player.getInventory()) == 0) {
            player.sendMessage("Not enough inventory space.");
        }
    }

    public boolean favorite(int slot) {
        // Remove from favorites
        if (Config.SEED_VAULT_FAVORITE_1.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_1.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_2.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_2.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_3.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_3.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_4.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_4.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_5.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_5.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_6.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_6.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_7.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_7.set(player, 255);
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_8.get(player) == slot) {
            Config.SEED_VAULT_FAVORITE_8.set(player, 255);
            return true;
        }
        // Add to favorites
        if (Config.SEED_VAULT_FAVORITE_1.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_1.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_2.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_2.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_3.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_3.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_4.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_4.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_5.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_5.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_6.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_6.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_7.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_7.set(player, slot);
        } else if (Config.SEED_VAULT_FAVORITE_8.get(player) == 255) {
            Config.SEED_VAULT_FAVORITE_8.set(player, slot);
        } else {
            player.sendMessage("Your favorite slots are full.  Unfavorite something to make space.");
            return false;
        }
        return true;
    }

    private boolean isFavorite(int slot) {
        if (Config.SEED_VAULT_FAVORITE_1.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_2.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_3.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_4.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_5.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_6.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_7.get(player) == slot) {
            return true;
        } else if (Config.SEED_VAULT_FAVORITE_8.get(player) == slot) {
            return true;
        }
        return false;
    }

    static {
        InterfaceHandler.register(Interface.SEED_VAULT, h -> {
            h.actions[19] = (DefaultAction) (p, option, slot, itemId) -> Config.SEED_VAULT_QUANTITY.set(p, 1);
            h.actions[20] = (DefaultAction) (p, option, slot, itemId) -> Config.SEED_VAULT_QUANTITY.set(p, 5);
            h.actions[21] = (DefaultAction) (p, option, slot, itemId) -> Config.SEED_VAULT_QUANTITY.set(p, 10);
            h.actions[22] = (DefaultAction) (p, option, slot, itemId) -> p.integerInput("Enter amount:", amt -> Config.SEED_VAULT_QUANTITY.set(p, amt));
            h.actions[23] = (DefaultAction) (p, option, slot, itemId) -> Config.SEED_VAULT_QUANTITY.set(p, Integer.MAX_VALUE);

            h.actions[15] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    Item item = player.getSeedVault().get(slot, itemId);
                    SeedVault vault = player.getSeedVault();
                    if (item == null)
                        return;
                    if (option == 1)
                        vault.withdraw(item, Config.SEED_VAULT_QUANTITY.get(player));
                    else if (option == 2)
                        vault.withdraw(item, 1);
                    else if (option == 3)
                        vault.withdraw(item, 5);
                    else if (option == 4)
                        vault.withdraw(item, 10);
                    else if (option == 5)
                        player.integerInput("Enter amount:", amt -> vault.withdraw(item, amt));
                    else if (option == 6)
                        vault.withdraw(item, Integer.MAX_VALUE);
                    else if (option == 7)
                        vault.favorite(slot);
                    else
                        item.examine(player);
                }

                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    Item fromItem = player.getSeedVault().getSafe(fromSlot);
                    Item toItem = player.getSeedVault().getSafe(toSlot);
                    SeedVault vault = player.getSeedVault();
                    // Favorite slot
                    if (toSlot == InventoryDef.getSize(626)) {
                        vault.favorite(fromSlot);
                        vault.send(player);
                        return;
                    }
                    if (fromItem == null || toItem == null) return;
                    if (!isSameCategory(fromItem, toItem) && !(vault.isFavorite(fromSlot) && vault.isFavorite(toSlot))) {
                        player.sendMessage("You can only swap seeds and saplings within their category.");
                        return;
                    }
                    vault.set(fromSlot, toItem);
                    vault.set(toSlot, fromItem);
                    vault.send(player);
                }
            };

            h.actions[25] = (DefaultAction) (p, option, slot, itemId) -> p.getSeedVault().depositAll();
        });

        InterfaceHandler.register(Interface.SEED_VAULT_INVENTORY, h -> {
            h.actions[1] = (DefaultAction) (player, option, slot, itemId) -> {
                Item item = player.getInventory().get(slot, itemId);
                SeedVault vault = player.getSeedVault();
                if (item == null)
                    return;
                if (option == 1)
                    vault.deposit(item, Config.SEED_VAULT_QUANTITY.get(player));
                else if (option == 2)
                    vault.deposit(item, 1);
                else if (option == 3)
                    vault.deposit(item, 5);
                else if (option == 4)
                    vault.deposit(item, 10);
                else if (option == 5)
                    player.integerInput("Enter amount:", amt -> vault.deposit(item, amt));
                else if (option == 6)
                    vault.deposit(item, Integer.MAX_VALUE);
                else
                    item.examine(player);
            };
        });

        ObjectAction.register(26206, "open", (p, o) -> p.getSeedVault().sendVault());
    }

    private static boolean isSameCategory(Item from, Item to) {
        return getCategory(from) == getCategory(to);
    }

    private static int getCategory(Item item) {
        String catString = getCategoryString(item).orElse("NAN");
        if (!NumberUtils.isDigits(catString)) return -1;
        return Integer.parseInt(catString);
    }

    private static Optional<String> getCategoryString(Item item) {
        val attributes = item.getDef().attributes;
        if (attributes == null) return Optional.empty();
        val category = attributes.get(709);
        if (category == null) return Optional.empty();
        return Optional.of(category.toString());
    }
}
