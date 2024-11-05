package io.ruin.model.item.containers.bank;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.DonatorBenefits;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.*;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.handlers.TabInventory;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.storage.GemBag;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.map.MapArea;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

import static io.ruin.model.inter.AccessMasks.*;

@Slf4j
public class Bank extends ItemContainerG<BankItem> {

    private static final Config FILLER_AMT = Config.varpbit(6281, false);

    public static final int BLANK_ID = -1, FILLER_ID = 20594;

    private boolean asNote;

    private boolean sortSkip, sortRequired;

    public void open() {
        if (player.getGameMode().isUltimateIronman()) {
            player.sendMessage("Ultimate ironmen cannot access the bank.");
            return;
        }
        if (MapArea.CRAFTING_GUILD.inArea(player) && !AreaReward.CRAFTING_GUILD_BANK.checkReward(player, "access this bank."))
            return;
        if (MapArea.COOKING_GUILD.inArea(player) && !AreaReward.COOKS_GUILD_BANK.checkReward(player, "access this bank."))
            return;
        if (player.getBankPin().requiresVerification(p -> open()))
            return;
        //player.getPacketSender().sendClientScript(917, "ii", -1, -2147483648);
        player.openResizeableInterface(InterfaceType.MAIN, Interface.BANK);
        player.openResizeableInterface(InterfaceType.INVENTORY, Interface.BANK_INVENTORY);
        // Inventory
        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 3, 0, 27, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp7, AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.DragDepth1, AccessMasks.DragTargetable);
        // Equipment inventory
        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 4, 0, 27, AccessMasks.ClickOp1, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.DragDepth1, AccessMasks.DragTargetable);
        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 11, 0, 27, AccessMasks.ClickOp1, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.DragDepth1, AccessMasks.DragTargetable);
        // Bank container
        player.getPacketSender().sendAccessMask(Interface.BANK, 13, 0, 1219, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp7, AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.DragDepth2, AccessMasks.DragTargetable);
        // Empty slots at end of tabs
        player.getPacketSender().sendAccessMask(Interface.BANK, 13, 1238, 1247, AccessMasks.DragTargetable);
        // IDK
        player.getPacketSender().sendAccessMask(Interface.BANK, 13, 1229, 1237, AccessMasks.ClickOp1);
        // Tabs
        player.getPacketSender().sendAccessMask(Interface.BANK, 11, 10, 10, AccessMasks.ClickOp1, AccessMasks.ClickOp7, AccessMasks.DragTargetable);
        player.getPacketSender().sendAccessMask(Interface.BANK, 11, 11, 19, AccessMasks.ClickOp1, AccessMasks.ClickOp6, AccessMasks.ClickOp7, AccessMasks.DragDepth1, AccessMasks.DragTargetable);
        // Incinerator
        player.getPacketSender().sendAccessMask(Interface.BANK, 50, 0, 1219, ClickOp1);

        player.getPacketSender().sendAccessMask(Interface.BANK, 47, 1, 816, 2);
        player.getPacketSender().sendAccessMask(Interface.BANK, 50, 0, 3, AccessMasks.ClickOp1);
        // Update bank current/max storage display
        player.getPacketSender().sendString(Interface.BANK, 5, "" + getItems().length);
        player.getPacketSender().sendString(Interface.BANK, 9, Integer.toString(DonatorBenefits.getBenefits(player).getBankSize()));

        BankEquipment.sendStats(player);

        sendAll = true;
    }

    public void openDepositBox() {
        if(player.getGameMode().isUltimateIronman()) {
            player.sendMessage("Ultimate ironmen cannot access the bank.");
            return;
        }
        if (MapArea.CRAFTING_GUILD.inArea(player) && !AreaReward.CRAFTING_GUILD_BANK.checkReward(player, "access this deposit box."))
            return;
        player.getPacketSender().sendAccessMask(161, 47, -1, -1, 0);
        player.getPacketSender().sendAccessMask(161, 48, -1, -1, 0);
        player.getPacketSender().sendClientScript(915, "i", 3);
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.DEPOSIT_BOX);
        player.openInterface(InterfaceType.INVENTORY, 78);
        player.getPacketSender().sendAccessMask(192, 2, 0, 27, 1180734);
    }

    @Override
    public int add(int id, int amount, Map<String, String> attributes) {
        if (player.getGameMode().isUltimateIronman()) {
            return 0;
        }
        ItemDefinition def = ItemDefinition.get(id);
        if(def == null || def.isPlaceholder())
            return -1;
        int tab = Config.BANK_TAB.get(player);
        int freeSlot = -1;
        BankItem blankItem = null;
        int hash = AttributeExtensions.hashAttributes(attributes);
        for(int slot = 0; slot < items.length; slot++) {
            BankItem item = items[slot];
            if (item == null) {
                if(freeSlot == -1)
                    freeSlot = slot;
                continue;
            }
            if (item.getId() == BLANK_ID) {
                if(item.tab == tab && blankItem == null)
                    blankItem = item;
                continue;
            }
            if (hash == 0) {
                if (item.getId() == id && !item.hasAttributes()) {
                    item.incrementAmount(amount);
                    return amount;
                }
                if (item.getId() == def.placeholderMainId) {
                    item.setId(id);
                    item.setAmount(amount);
                    return amount;
                }
            }
        }
        if(blankItem != null) {
            blankItem.setId(id);
            blankItem.setAmount(amount);
            blankItem.putAttributes(attributes);
            return amount;
        }
        if(freeSlot != -1) {
            set(freeSlot, new BankItem(id, amount, tab, attributes));
            if(tab != 0) sort();
            return amount;
        }
        return 0;
    }

    @Override
    protected BankItem newItem(int id, int amount, Map<String, String> attributes) {
        return new BankItem(id, amount, 0, attributes);
    }

    @Override
    protected BankItem[] newArray(int size) {
        return new BankItem[size];
    }

    /**
     * Depositing
     */

    public int deposit(Item item, int amount, boolean message) {
        ItemDefinition def = item.getDef();
        int moved = item.move(def.isNote() ? def.notedId : def.id, amount, this);
        if (moved == 0 && message)
            player.sendMessage("You don't have enough space in your bank account.");
        return moved;
    }

    public void deposit(ItemContainer container, boolean message) {
        if(container.isEmpty()) {
            if(message)
                player.sendMessage("You have nothing to deposit.");
            return;
        }
        sortAfter(() -> {
            boolean deposited = false;
            for(Item item : container.getItems()) {
                if(item != null && deposit(item, item.getAmount(), false) > 0)
                    deposited = true;
            }
            if(!deposited && message)
                player.sendMessage("Your bank cannot hold your items.");
        });
    }

    /**
     * Withdrawing
     */

    private void withdraw(BankItem item, int amount) {
        if(item.getId() == BLANK_ID)
            return;
        if(item.getId() == FILLER_ID) {
            if(amount == -1)
                setBlank(item);
            else
                clearFillers();
            return;
        }
        ItemDefinition def = item.getDef();
        if(def.isPlaceholder()) {
            setBlank(item);
            return;
        }
        boolean placeholder = Config.BANK_ALWAYS_PLACEHOLDERS.get(player) == 1;
        if(amount == -1) {
            amount = item.getAmount();
            placeholder = true;
        }
        int itemId = item.getId();
        if(asNote) {
            if(def.notedId != -1)
                itemId = def.notedId;
            else
                player.sendMessage("This item cannot be withdrawn as a note.");
        }
        int moved = item.getDef().bankWithdrawListener(player, item, amount);
        if (moved > 0) item.remove(moved);
        moved += item.move(itemId, amount - moved, player.getInventory());
        if(moved <= 0) {
            player.sendMessage("You don't have enough inventory space.");
            return;
        }
        if((moved < amount) && amount != Integer.MAX_VALUE) {
            /* kinda pointless but rs does it so hey */
            player.sendMessage("You don't have enough inventory space to withdraw that many.");
        }
        if(item.getAmount() > 0) {
            /* some of this item still remains in your bank */
            return;
        }
        if(placeholder && allowPlaceHolder(def)) {
            item.setId(def.placeholderMainId);
            item.setAmount(1);
            set(item.getSlot(), item);
            return;
        }
        setBlank(item);
    }

    /**
     * Blank items
     */

    private void setBlank(BankItem item) {
        boolean removed = item.getContainer() == null;
        BankItem prev = getSafe(item.getSlot() - 1);
        if(prev == null || prev.tab != item.tab) {
            /**
             * First item in tab
             */
            if(!removed)
                set(item.getSlot(), null);
            for(int slot = item.getSlot() + 1; slot < items.length; slot++) {
                BankItem next = items[slot];
                if(next == null || next.tab != item.tab || next.getId() != BLANK_ID)
                    break;
                set(slot, null);
            }
            sort();
            return;
        }
        BankItem next = getSafe(item.getSlot() + 1);
        if(next == null || next.tab != item.tab) {
            /**
             * Last item in tab
             */
            if(!removed)
                set(item.getSlot(), null);
            for(int slot = item.getSlot() - 1; slot >= 0; slot--) {
                prev = items[slot];
                if(prev == null || prev.tab != item.tab || prev.getId() != BLANK_ID)
                    break;
                set(slot, null);
            }
            sort();
            return;
        }
        item.clearAttributes();
        item.toBlank();
        if(removed)
            set(item.getSlot(), item);
    }

    public void removeBlankItems() {
        int removed = 0;
        for(BankItem item : items) {
            if(item == null)
                break;
            if(item.getId() == BLANK_ID) {
                set(item.getSlot(), null);
                removed++;
            }
        }
        if(removed > 0) sort();
    }

    /**
     * Placeholders
     */

    private boolean allowPlaceHolder(ItemDefinition def) {
        if(!def.hasPlaceholder()) {
            player.sendMessage("This item cannot leave a placeholder.");
            return false;
        }
        return findItem(def.placeholderMainId) == null;
    }

    public boolean toPlaceholder(BankItem item) {
        ItemDefinition def = item.getDef();
        if(allowPlaceHolder(def)) {
            item.setId(def.placeholderMainId);
            item.setAmount(1);
            return true;
        }
        return false;
    }

    private void releasePlaceholders() {
        sortAfter(() -> {
            int released = 0;
            for(BankItem item : items) {
                if(item == null || item.getId() == BLANK_ID || item.getDef() == null || !item.getDef().isPlaceholder())
                    continue;
                setBlank(item);
                released++;
            }
            if(released == 0)
                player.sendMessage("You don't have any placeholders to release.");
            else if(released == 1)
                player.sendMessage("1 placeholder has been released.");
            else
                player.sendMessage(released + " placeholders have been released.");
        });
    }

    /**
     * Fillers
     */

    private void fillBank() {
        int fillValue = FILLER_AMT.get(player);
        int fillAmt = fillValue == 1 ? 1 : fillValue == 2 ? 10 : fillValue == 3 ? 50 : fillValue == 4 ? player.getTemporaryAttributeIntOrZero("FILLER_AMT") : 800;
        sortAfter(() -> {
            int filled = 0;
            boolean hadFiller = false;
            int tab = Config.BANK_TAB.get(player);
            for(int slot = items.length - 1; slot >= 0; slot--) {
                if (filled >= fillAmt) break;
                BankItem item = items[slot];
                if(item == null) {
                    filled++;
                    item = new BankItem(FILLER_ID, 1, tab, null);
                    item.sortSlot = 65535;
                    set(slot, item);
                    sort();
                } else if(item.getId() == FILLER_ID) {
                    hadFiller = true;
                } else if(item.getId() == BLANK_ID) {
                    filled++;
                    item.setId(FILLER_ID);
                    item.setAmount(1);
                    if(item.tab != tab || !hadFiller) {
                        item.tab = tab;
                        item.sortSlot = 65535;
                        sort();
                    }
                }
            }
            if(filled == 0)
                player.sendMessage("Your bank is already full.");
            else if(filled == 1)
                player.sendMessage("You fill up your bank with a bank filler.");
            else
                player.sendMessage("You fill up your bank with " + filled + " bank fillers.");
        });
    }

    private void clearFillers() {
        sortAfter(() -> {
            for(BankItem item : items) {
                if(item == null || item.getId() != FILLER_ID)
                    continue;
                setBlank(item);
            }
        });
    }

    /**
     * Incinerate
     */

    private void incinerate(int slot, int itemId) {
        BankItem item = get(slot - 1, itemId);
        if(item == null) {
            return;
        }
        setBlank(item);
    }

    /**
     * Rearranging
     */

    private void rearrange(int fromSlot, int toSlot) {
        if(fromSlot == toSlot)
            return;
        BankItem fromItem = getSafe(fromSlot);
        if(fromItem == null)
            return;
        BankItem toItem = getSafe(toSlot);
        if(toItem == null)
            return;
        if(Config.BANK_INSERT_MODE.get(player) == 1) {
            if(fromItem.getSlot() > toItem.getSlot() || toItem.tab == 0 && fromItem.tab != 0) {
                /* insert left */
                fromItem.sortSlot = toItem.getSlot();
                toItem.sortSlot = toItem.getSlot() + 1;
            } else {
                /* insert right */
                fromItem.sortSlot = toItem.getSlot();
                toItem.sortSlot = toItem.getSlot() - 1;
            }
            fromItem.tab = toItem.tab;
            sort();
        } else {
            int fromTab = fromItem.tab;
            fromItem.tab = toItem.tab;
            toItem.tab = fromTab;
            set(fromSlot, toItem);
            set(toSlot, fromItem);
        }
    }

    /**
     * Tabs
     */

    private void setTab(int newTab) {
        Config.BANK_TAB.set(player, newTab);
        player.getPacketSender().sendClientScript(101, "i", 11);
    }

    private void selectTab(int option, int slot) {
        int tab = slot - 10;
        if(tab < 0 || tab > 9)
            return;
        Config.BANK_TAB_DISPLAY.set(player, slot);
        if(option == 1)
            setTab(tab);
        else if(option == 6) //todo fix this action button and add collapse placeholders
            collapseTab(tab);
    }

    private void swapTabs(int fromSlot, int toSlot) {
        if(fromSlot == toSlot)
            return;
        int fromTab = fromSlot - 10;
        if(fromTab < 0 || fromTab > 9)
            return;
        int toTab = toSlot - 10;
        if(toTab < 0 || toTab > 9)
            return;
        for(BankItem item : items) {
            if(item == null)
                break;
            if(item.tab == fromTab)
                item.tab = toTab;
            else if(item.tab == toTab)
                item.tab = fromTab;
        }
        sort();
    }

    private void changeTab(int fromSlot, int fromItemId, int toSlot) {
        BankItem item = get(fromSlot, fromItemId);
        if(item == null)
            return;
        int newTab = toSlot - 10;
        if(newTab < 0 || newTab > 9)
            return;
        int oldTab = item.tab;
        if(oldTab == newTab)
            return;
        for(int slot = getStartSlot(newTab); slot < items.length; slot++) {
            BankItem bankItem = items[slot];
            if(bankItem == null || bankItem.tab != newTab) {
                item.tab = newTab;
                item.sortSlot = Short.MAX_VALUE;
                sort();
                return;
            }
            if(bankItem.getId() == BLANK_ID) {
                set(item.getSlot(), null);
                bankItem.setId(item.getId());
                bankItem.setAmount(item.getAmount());
                sort();
                return;
            }
        }
    }

    private void collapseTab(int tab) {
        if(tab < 1 || tab > 9)
            return;
        for(int slot = getStartSlot(tab); slot < items.length; slot++) {
            BankItem bankItem = items[slot];
            if(bankItem == null)
                break;
            if(bankItem.tab == 0) {
                if(bankItem.getId() == BLANK_ID)
                    set(slot, null);
            } else if(bankItem.tab == tab) {
                if(bankItem.getId() == BLANK_ID) {
                    set(slot, null);
                } else {
                    bankItem.tab = 0;
                    bankItem.sortSlot = Short.MAX_VALUE + slot;
                }
            }
        }
        setTab(0);
        sort();
    }

    private int getStartSlot(int tab) {
        int tabStartSlot = 0;
        for(int t = 1; t < tab; t++) {
            int size = Config.BANK_TAB_SIZES[t - 1].get(player);
            tabStartSlot += size;
        }
        return tabStartSlot;
    }

    /**
     * Sort
     */

    public void sort() {
        if(sortSkip)
            sortRequired = true;
        else
            doSort();
    }

    private void sortAfter(Runnable runnable) {
        sortSkip = true;
        runnable.run();
        sortSkip = false;
        if(sortRequired) {
            sortRequired = false;
            doSort();
        }
    }

    private void doSort() {
        ArrayList<BankItem> list = new ArrayList<>();
        for(int slot = 0; slot < items.length; slot++) {
            BankItem item = items[slot];
            if(item != null) {
                set(slot, null);
                list.add(item);
            }
        }
        list.sort((item1, item2) -> {
            int tab1 = item1.tab == 0 ? 10 : item1.tab;
            int tab2 = item2.tab == 0 ? 10 : item2.tab;
            if(tab1 == tab2) {
                int slot1 = item1.sortSlot == -1 ? item1.getSlot() : item1.sortSlot;
                int slot2 = item2.sortSlot == -1 ? item2.getSlot() : item2.sortSlot;
                return Integer.compare(slot1, slot2);
            }
            return Integer.compare(tab1, tab2);
        });
        int slot = 0;
        int[] tabSizes = new int[9];
        int lastTab = -1, tabOffset = 0;
        for(BankItem item : list) {
            if(lastTab != item.tab) {
                lastTab = item.tab;
                tabOffset++;
            }
            if(item.tab != 0) {
                item.tab = tabOffset;
                tabSizes[item.tab - 1]++;
            }
            item.sortSlot = -1;
            set(slot++, item);
        }
        for(int i = 0; i < tabSizes.length; i++)
            Config.BANK_TAB_SIZES[i].set(player, tabSizes[i]);
        sendAll = true;
    }

    /**
     * Interface handlers
     */

    static {
        /**
         * Main
         */
        InterfaceHandler.register(Interface.BANK, h -> {
            h.actions[11] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    player.getBank().selectTab(option, slot);
                }
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    if(toChildId == 11)
                        player.getBank().swapTabs(fromSlot, toSlot);
                }
            };
            h.actions[13] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    BankItem item = player.getBank().get(slot, itemId);
                    if(item == null)
                        return;
                    if(option == 1) {
                        switch(Config.BANK_DEFAULT_QUANTITY.get(player)) {
                            case 1:
                                player.getBank().withdraw(item, 5);
                                break;
                            case 2:
                                player.getBank().withdraw(item, 10);
                                break;
                            case 3:
                                if (Config.BANK_LAST_X.get(player) > 0) {
                                    player.getBank().withdraw(item, Config.BANK_LAST_X.get(player));
                                } else {
                                    player.integerInput("Enter amount:", amount -> {
                                        player.getBank().withdraw(item, amount);
                                        Config.BANK_LAST_X.set(player, amount);
                                    });
                                }
                                break;
                            case 4:
                                player.getBank().withdraw(item, Integer.MAX_VALUE);
                                break;
                            default:
                                player.getBank().withdraw(item, 1);
                                break;
                        }
                        return;
                    }
                    if(option == 2) {
                        player.getBank().withdraw(item, 1);
                        return;
                    }
                    if(option == 3) {
                        player.getBank().withdraw(item, 5);
                        return;
                    }
                    if(option == 4) {
                        player.getBank().withdraw(item, 10);
                        return;
                    }
                    if(option == 5) {
                        player.getBank().withdraw(item, Config.BANK_LAST_X.get(player));
                        return;
                    }
                    if(option == 6) {
                        player.integerInput("Enter amount:", amount -> {
                            player.getBank().withdraw(item, amount);
                            Config.BANK_LAST_X.set(player, amount);
                        });
                        return;
                    }
                    if(option == 7) {
                        player.getBank().withdraw(item, item.getAmount());
                        return;
                    }
                    if(option == 8) {
                        player.getBank().withdraw(item, item.getAmount() - 1);
                        return;
                    }
                    if(option == 9) {
                        player.getBank().withdraw(item, -1);
                        return;
                    }
                    item.examine(player);
                }
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    if(toChildId == 11) {
                        player.getBank().changeTab(fromSlot, fromItemId, toSlot);
                        return;
                    }
                    if(toChildId == 13) {
                        if(toSlot >= 1200)
                            player.getBank().changeTab(fromSlot, fromItemId, toSlot - 1200);
                        else
                            player.getBank().rearrange(fromSlot, toSlot);
                        return;
                    }
                }
            };
            h.actions[19] = (SimpleAction) p -> Config.BANK_INSERT_MODE.set(p, 0);
            h.actions[21] = (SimpleAction) p -> Config.BANK_INSERT_MODE.set(p, 1);
            h.actions[24] = (SimpleAction) p -> p.getBank().asNote = false;
            h.actions[26] = (SimpleAction) p -> p.getBank().asNote = true;
            h.actions[30] = (SimpleAction) p -> Config.BANK_DEFAULT_QUANTITY.set(p, 0);
            h.actions[32] = (SimpleAction) p -> Config.BANK_DEFAULT_QUANTITY.set(p, 1);
            h.actions[34] = (SimpleAction) p -> Config.BANK_DEFAULT_QUANTITY.set(p, 2);
            h.actions[36] = (OptionAction) (p, option) -> {
                switch (option) {
                    case 1:
                        Config.BANK_DEFAULT_QUANTITY.set(p, 3);
                        if (Config.BANK_LAST_X.get(p) <= 0) {
                            p.integerInput("Enter amount:", amount -> Config.BANK_LAST_X.set(p, amount));
                        }
                        break;
                    case 2:
                        p.integerInput("Enter amount:", amount -> Config.BANK_LAST_X.set(p, amount));
                        break;
                }
            };
            h.actions[38] = (SimpleAction) p -> Config.BANK_DEFAULT_QUANTITY.set(p, 4);
            h.actions[40] = (SimpleAction) Config.BANK_ALWAYS_PLACEHOLDERS::toggle;
            h.actions[44] = (SimpleAction) p -> p.getBank().deposit(p.getInventory(), true);
            h.actions[46] = (SimpleAction) p -> p.getBank().deposit(p.getEquipment(), true);
            h.actions[50] = (DefaultAction) (p, option, slot, itemId) -> p.getBank().incinerate(slot, itemId);
            h.actions[56] = (SimpleAction) Config.BANK_INCINERATOR::toggle;
            h.actions[57] = (SimpleAction) Config.BANK_TUTORIAL_BUTTON::toggle;
            h.actions[60] = (SimpleAction) p -> Config.BANK_INVENTORY_OPTIONS.set(p, 1);
            h.actions[61] = (SimpleAction) Config.BANK_DEPOSIT_INVENTORY::toggle;
            h.actions[62] = (SimpleAction) Config.BANK_DEPOSIT_EQUIPMENT::toggle;
            //h.actions[63] = (SimpleAction) Config.DEPOSIT_TO_POTION_STORE::toggle;
            h.actions[64] = (SimpleAction) p -> p.getBank().releasePlaceholders();
            h.actions[66] = (SimpleAction) p -> FILLER_AMT.set(p, 1);
            h.actions[68] = (SimpleAction) p -> FILLER_AMT.set(p, 2);
            h.actions[70] = (SimpleAction) p -> FILLER_AMT.set(p, 3);
            h.actions[72] = (SimpleAction) p -> p.integerInput("Enter amount:", amount -> {
                FILLER_AMT.set(p, 4);
                p.putTemporaryAttribute("FILLER_AMT", amount);
            });
            h.actions[74] = (SimpleAction) p -> FILLER_AMT.set(p, 0);
            h.actions[76] = (SimpleAction) p -> p.getBank().fillBank();

            h.closedAction = (p, i) -> {
                p.getBank().removeBlankItems();
                p.getPacketSender().sendClientScript(101, "i", 11);
                p.setInterfaceUnderlay(0, -1);
            };
        });
        /**
         * Inventory
         */
        InterfaceHandler.register(Interface.BANK_INVENTORY, h -> {
            h.actions[3] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    Item item = player.getInventory().get(slot, itemId);
                    if(item == null)
                        return;
                    if(option == 1 && item.getId() == LootingBag.CLOSED_LOOTING_BAG)
                        return;
                    if(option == 2) {
                        switch(Config.BANK_DEFAULT_QUANTITY.get(player)) {
                           case 1:
                               player.getBank().deposit(item, 5, true);
                               break;
                           case 2:
                               player.getBank().deposit(item, 10, true);
                               break;
                           case 3:
                               if (Config.BANK_LAST_X.get(player) > 0) {
                                   player.getBank().deposit(item, Config.BANK_LAST_X.get(player), true);
                               } else {
                                   player.integerInput("Enter amount:", amount -> {
                                       player.getBank().deposit(item, amount, true);
                                       Config.BANK_LAST_X.set(player, amount);
                                   });
                               }
                               break;
                           case 4:
                               player.getBank().deposit(item, Integer.MAX_VALUE, true);
                               break;
                           default:
                               player.getBank().deposit(item, 1, true);
                               break;
                       }
                       return;
                    }
                    if(option == 3) {
                        player.getBank().deposit(item, 1, true);
                        return;
                    }
                    if(option == 4) {
                        player.getBank().deposit(item, 5, true);
                        return;
                    }
                    if(option == 5) {
                        player.getBank().deposit(item, 10, true);
                        return;
                    }
                    if(option == 6) {
                        player.getBank().deposit(item, Config.BANK_LAST_X.get(player), true);
                        return;
                    }
                    if(option == 7) {
                        player.integerInput("Enter amount:", amount -> {
                            player.getBank().deposit(item, amount, true);
                            Config.BANK_LAST_X.set(player, amount);
                        });
                        return;
                    }
                    if(option == 8) {
                        player.getBank().deposit(item, Integer.MAX_VALUE, true);
                        return;
                    }
                    if(option == 9) {
                        if (item.getId() == RunePouch.RUNE_POUCH)
                            player.getRunePouch().empty(true);
                        if (item.getId() == Items.GEM_BAG)
                            GemBag.emptyBag(player, true);
                        return;
                    }
                    item.examine(player);
                }
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    TabInventory.drag(player, fromSlot, toSlot);
                }
            };
            /**
             * Looting bag
             */
            h.actions[6] = (SimpleAction) p -> {
                boolean messaged = false;
                for (Item item : p.getLootingBag().getItems()) {
                    if (item != null) {
                        messaged = item.getAmount() != p.getBank().deposit(item, item.getAmount(), !messaged);
                    }
                }
            };
            h.actions[11] = (DefaultAction) (player, option, slot, itemId) -> {
                Item item = player.getLootingBag().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1) {
                    player.getBank().deposit(item, 1, true);
                    return;
                }
                if(option == 2) {
                    player.getBank().deposit(item, 5, true);
                    return;
                }
                if(option == 3) {
                    player.getBank().deposit(item, Integer.MAX_VALUE, true);
                    return;
                }
                if(option == 4) {
                    player.integerInput("Enter amount:", amount -> player.getBank().deposit(item, amount, true));
                    return;
                }
                item.examine(player);
            };
        });
        /**
         * Deposit box
         */
        InterfaceHandler.register(Interface.DEPOSIT_BOX, h -> {

            h.actions[2] = (DefaultAction) (player, option, slot, itemId) -> {
                Item item = player.getInventory().get(slot, itemId);
                if (item == null)
                    return;
                if(option == 1) {
                    switch(Config.DEPOSIT_BOX_DEFAULT_QUANTITY.get(player)) {
                        case 1:
                            player.getBank().deposit(item, 5, true);
                            break;
                        case 2:
                            player.getBank().deposit(item, 10, true);
                            break;
                        case 3:
                            if (Config.DEPOSIT_BOX_LAST_X.get(player) > 0) {
                                player.getBank().deposit(item, Config.DEPOSIT_BOX_LAST_X.get(player), true);
                            } else {
                                player.integerInput("Enter amount:", amount -> {
                                    player.getBank().deposit(item, amount, true);
                                    Config.DEPOSIT_BOX_LAST_X.set(player, amount);
                                });
                            }
                            break;
                        case 4:
                            player.getBank().deposit(item, Integer.MAX_VALUE, true);
                            break;
                        default:
                            player.getBank().deposit(item, 1, true);
                            break;
                    }
                    return;
                }
                if(option == 3) {
                    player.getBank().deposit(item, 5, true);
                    return;
                }
                if(option == 4) {
                    player.getBank().deposit(item, 10, true);
                    return;
                }
                if(option == 5) {
                    player.getBank().deposit(item, Integer.MAX_VALUE, true);
                    return;
                }
                item.examine(player);
            };

            h.actions[4] = (SimpleAction) p -> p.getBank().deposit(p.getInventory(), true);
            h.actions[6] = (SimpleAction) p -> p.getBank().deposit(p.getEquipment(), true);
            h.actions[8] = (SimpleAction) p -> p.getBank().deposit(p.getLootingBag(), true);
            h.actions[11] = (SimpleAction) p -> Config.DEPOSIT_BOX_DEFAULT_QUANTITY.set(p, 0);
            h.actions[13] = (SimpleAction) p -> Config.DEPOSIT_BOX_DEFAULT_QUANTITY.set(p, 1);
            h.actions[15] = (SimpleAction) p -> Config.DEPOSIT_BOX_DEFAULT_QUANTITY.set(p, 2);
            h.actions[17] = (OptionAction) (p, option) -> {
                switch (option) {
                    case 1:
                        Config.DEPOSIT_BOX_DEFAULT_QUANTITY.set(p, 3);
                        if (Config.DEPOSIT_BOX_LAST_X.get(p) <= 0) {
                            p.integerInput("Enter amount:", amount -> Config.DEPOSIT_BOX_LAST_X.set(p, amount));
                        }
                        break;
                    case 2:
                        p.integerInput("Enter amount:", amount -> Config.DEPOSIT_BOX_LAST_X.set(p, amount));
                        break;
                }
            };
            h.actions[19] = (SimpleAction) p -> Config.DEPOSIT_BOX_DEFAULT_QUANTITY.set(p, 4);
            h.closedAction = (p, i) -> {
                p.getPacketSender().sendClientScript(915, "i", 3);
                p.getPacketSender().sendClientScript(917, "ii", -1, -1);
            };
        });
    }
}