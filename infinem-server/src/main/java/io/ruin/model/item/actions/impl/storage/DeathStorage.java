package io.ruin.model.item.actions.impl.storage;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.itemskeptondeath.IKOD;
import io.ruin.utility.Color;
import io.ruin.model.combat.Killer;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class DeathStorage extends ItemContainer {


    static {
        // death storage chest -- maybe find something that fits the area better?
        ObjectAction.register(31675, 1, (p, obj) -> {
            if (p.getDeathStorage().isEmpty()) {
                p.sendMessage("The chest is currently empty. Should you die, you may retrieve your lost items from it.");
            } else {
                p.getDeathStorage().open();
                p.getPacketSender().resetHintIcon(false);
            }
        });

    }

    static {
        InterfaceHandler.register(Interface.DEATH_STORAGE, h -> {
            h.actions[3] = (DefaultAction) (p, option, slot, itemId) -> {
                if (option == 2)
                    p.getDeathStorage().take(slot);
                else if (option == 9)
                    p.getDeathStorage().value(slot);
                else
                    p.getDeathStorage().examine(slot);
            };
            h.actions[6] = (SimpleAction) p -> {
                if (p.getDeathStorage().isUnlocked())
                    p.getDeathStorage().takeAll();
                else
                    p.getDeathStorage().unlock();
            };
            h.actions[8] = (SimpleAction) p -> p.getDeathStorage().discardAll();
            h.closedAction = (p, integer) -> {
                if (p.getDeathStorage().isUnlocked() && !p.getDeathStorage().isEmpty()) {
                    p.sendMessage(Color.RED.wrap("WARNING:") + " Should you die again, all items currently in death storage will be gone forever!");
                }
            };
        });
    }

    @Expose private boolean unlocked = false;

    private void discardAll() {
        player.dialogue(new MessageDialogue("Are you sure you want to discard all items?<br><br>They will be lost forever!"),
                new OptionsDialogue("Discard all items?",
                        new Option("Yes", () -> {
                            reset();
                            player.closeInterface(InterfaceType.MAIN);
                            player.sendMessage("The storage has been cleared.");
                        }),
                        new Option("No", this::open)
                )
        );
    }

    private void take(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        if (!unlocked && item.getId() != BLOOD_MONEY && item.getId() != COINS_995) {
            player.sendMessage("You must first unlock your items. Click the padlock icon to pay the fee.");
            return;
        }
        if (item.move(item.getId(), item.getAmount(), player.getInventory()) == 0)
            player.sendMessage("Not enough space in your inventory.");
        sendUpdates();
    }

    private void value(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        player.sendMessage(item.getDef().name + ": " + NumberUtils.formatNumber(item.getDef().value * item.getAmount()) + " coins.");
    }

    private void examine(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        item.examine(player);
    }

    private void unlock() {
        if (unlocked)
            return;
        Item cost = getUnlockCost();
        if (cost.getAmount() == 0) {
            unlocked = true;
            update();
            return;
        }
        if ((player.getInventory().findItem(995) == null ? 0 : (long) player.getInventory().findItem(995).getAmount())
                + (player.getBank().findItem(995) == null ? 0 : (long) player.getBank().findItem(995).getAmount())
                + (this.findItem(995) == null ? 0 : (long) this.findItem(995).getAmount()) < cost.getAmount()) {
            player.sendMessage("You do not have the required funds to pay the unlock fee.");
        } else {
            if (this.contains(995)) {
                int amt = this.findItem(995).getAmount();
                if (amt > cost.getAmount()) {
                    this.remove(cost.getId(), cost.getAmount(), true, null);
                    sendUpdates();
                    finalizeUnlock();
                    return;
                } else {
                    this.remove(995, amt);
                    cost.incrementAmount(-amt);
                    sendUpdates();
                }
            }
            Inventory inventory = player.getInventory();
            if (inventory.contains(995)) {
                int amt = inventory.findItem(995).getAmount();
                if (amt > cost.getAmount()) {
                    inventory.remove(cost.getId(), cost.getAmount(), true, null);
                    finalizeUnlock();
                    return;
                } else {
                    inventory.remove(995, amt);
                    cost.incrementAmount(-amt);
                }
            }
            Bank bank = player.getBank();
            if (bank.contains(995)) {
                int amt = bank.findItem(995).getAmount();
                if (amt > cost.getAmount()) {
                    bank.remove(cost.getId(), cost.getAmount(), true, null);
                    finalizeUnlock();
                    return;
                } else {    // OOPS IF WE HIT THIS
                    System.out.println("Not good");
                    bank.remove(995, amt);
                    cost.incrementAmount(-amt);
                }
            }
        }
    }

    private void finalizeUnlock() {
        unlocked = true;
        update();
        player.sendMessage("You may now collect your items.");
    }

    private void takeAll() {
        for (Item item : getItems()) {
            if (item != null) {
                int itemAmt = item.getAmount();
                if (item.move(item.getId(), itemAmt, player.getInventory()) != itemAmt) {
                    break;
                }
            }
        }
        sendUpdates();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void open() {
        send(player);
        player.openInterface(InterfaceType.MAIN, Interface.DEATH_STORAGE);
        update();
    }

    private void update() {
        Config.DEATH_STORAGE_TYPE.set(player, unlocked ? 4 : 3);
        new Unlock(602, 3).children(0, 50).unlockMultiple(player, 1,8,9);
        Item cost = getUnlockCost();
        if (!unlocked && cost != null) {
            player.addEvent(event -> {
                event.delay(1); // this is required because the interface runs a script that changes the string on the child we want to modify,
                //the delay shouldn't be noticeable because the string is only displayed when the player hovers over the button
                //but this could be avoided by modifying the interface/script
                player.getPacketSender().sendString(602, 11, "Fee to unlock:<br><col=ffffff>" + NumberUtils.formatNumber(cost.getAmount()) + " x " + cost.getDef().name);
            });
        }
    }

    private Item getUnlockCost() {
        long cost = 0;
        if (player.getStats().totalLevel < 250) {
            cost = 0;
        } else {
            cost = (int) (getContainerWorth() * 0.05f);
        }
        if (cost < 25000) {
            cost = 0;
        }
        if (cost > 10000000) {
            cost = 10000000;
        }
        cost = (long) (cost * getDonatorCostMultiplier(player));
        return new Item(COINS_995, (int) cost);
    }

    public static float getDonatorCostMultiplier(Player player) {
        return player.isOnyx() ? 0.7f : player.isDragonStone() ? 0.75f : player.isDiamond() ? 0.8f : player.isRuby() ? 0.85f : player.isEmerald() ? 0.9f : player.isSapphire() ? 0.95f : 1f;
    }

    public void reset() {
        clear();
        unlocked = getUnlockCost() == null;
    }

    public void death(Killer killer) {
        reset();
        IKOD.forLostItem(player, killer, this::add);
        if (!isEmpty()) {
            switch (player.getRespawnPoint()) {
                case EDGEVILLE:
                    player.getPacketSender().sendHintIcon(3093, 3487);
                    break;
                case FALADOR:
                    player.getPacketSender().sendHintIcon(2943, 3371);
                    break;
                case CAMELOT:
                    player.getPacketSender().sendHintIcon(2760, 3481);
                    break;
                case ARDOUGNE:
                    player.getPacketSender().sendHintIcon(2651, 3287);
                    break;
                case PRIFDDINAS:
                    player.getPacketSender().sendHintIcon(3261, 6068);
                    break;
                case FEROX_ENCLAVE:
                    player.getPacketSender().sendHintIcon(3137, 3636);
                    break;
                case KOUREND_CASTLE:
                    player.getPacketSender().sendHintIcon(1633, 3673);
                    break;
                default:
                    player.getPacketSender().sendHintIcon(3216, 3221);
                    break;
            }
        }
    }
}
