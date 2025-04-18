package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Inventory;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/28/2021
 */
public enum CoinPouch {
    MAN(22521, 3),
    FARMER(22522, 9),
    HAM(22523, 1, 21),
    WARRIOR(22524, 18),
    ROGUE(22525, 25, 40),
    CAVE_GOBLIN(22526, 10, 50),
    GUARD(22527, 30),
    WEALTHY_CITIZEN(28822, 85),
    DESERT_BANDIT(22530, 30),
    KNIGHT(22531, 50),
    WATCHMAN(22533, 60),
    PALADIN(22535, 80),
    GNOME(22536, 300),
    HERO(22537, 200, 300),
    VYRE(24703, 250, 315),
    ELF(22538, 280, 350);

    private final int pouchId, minCoins, maxCoins;

    CoinPouch(int pouchId, int minCoins, int maxCoins) {
        this.pouchId = pouchId;
        this.minCoins = minCoins;
        this.maxCoins = maxCoins;
    }

    CoinPouch(int pouchId, int coins) {
        this(pouchId, coins, coins);
    }

    static {
        for (CoinPouch pouch : values()) {
            ItemAction.registerInventory(pouch.pouchId, "open", (player, item) -> {
                Inventory inventory = player.getInventory();
                if (inventory.hasRoomFor(995, pouch.maxCoins) || (inventory.getAmount(pouch.pouchId) == 1 && !inventory.contains(995))) {
                    inventory.remove(pouch.pouchId, 1);
                    if (pouch.minCoins != pouch.maxCoins) {
                        inventory.add(995, Random.get(pouch.minCoins, pouch.maxCoins));
                    } else {
                        inventory.add(995, pouch.minCoins);
                    }
                } else {
                    player.sendMessage("You don't have enough inventory space to open the coin pouch.");
                }
            });
            ItemAction.registerInventory(pouch.pouchId, "open-all", (player, item) -> {
                Inventory inventory = player.getInventory();
                int amountOfPouches = inventory.getAmount(pouch.pouchId);
                if (inventory.hasRoomFor(995, pouch.maxCoins * amountOfPouches) || (amountOfPouches == 1 && !inventory.contains(995))) {
                    int amountAdded = 0;
                    if (pouch.minCoins != pouch.maxCoins) {
                        for (int index = 0; index < amountOfPouches; index++) {
                            if (inventory.contains(pouch.pouchId)) {
                                inventory.remove(pouch.pouchId, 1);
                                int amt = Random.get(pouch.minCoins, pouch.maxCoins);
                                inventory.add(995, amt);
                                amountAdded += amt;
                            } else {
                                break;
                            }
                        }
                    } else {
                        inventory.remove(pouch.pouchId, amountOfPouches);
                        amountAdded = pouch.minCoins * amountOfPouches;
                        inventory.add(995, amountAdded);
                    }
                    player.sendMessage("You open " + (amountOfPouches > 1 ? amountOfPouches + " pouches" : "a coin pouch") + ""
                            + (amountOfPouches > 1 ? ", finding a total of " : " and find ") + amountAdded + " coins!");
                    if (amountOfPouches >= 28) {
                        player.getTaskManager().doLookupByUUID(11, 1);
                    }
                    if (amountAdded >= 800) {
                        player.getTaskManager().doLookupByUUID(89, 1);
                    }
                } else {
                    player.sendMessage("You don't have enough inventory space to open the coin pouches.");
                }
            });
        }
    }
}
