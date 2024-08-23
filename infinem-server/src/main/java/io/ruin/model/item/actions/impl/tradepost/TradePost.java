package io.ruin.model.item.actions.impl.tradepost;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.*;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.ItemSet;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author <a href="https://github.com/kLeptO">Augustinas R.</a>
 */
public class TradePost {

    private static final int MAX_MY_OFFERS = 10;
    private static final int MAX_VIEW_OFFERS = 50;
    private static final int MAX_HISTORY = 50;

    private Player player;
    private String searchText;
    private TradePostSort sort = TradePostSort.AGE_ASCENDING;

    @Expose
    private List<TradePostOffer> tradePostOffers = new ArrayList<>();
    private List<TradePostOffer> viewOffers = new ArrayList<>();
    @Expose
    private LinkedList<ExchangeHistory> exchangeHistory = new LinkedList<>();

    @Expose
    private long coffer;

    public void init(Player player) {
        this.player = player;
    }

    public void openViewOffers() {
        //if(player.getBankPin().requiresVerification(p -> openViewOffers()))
        //    return;
        player.openInterface(InterfaceType.MAIN_STRETCHED, Interface.TRADING_POST_VIEW);
        player.getPacketSender().sendAccessMask(1005, 19, 0, 100, AccessMasks.ClickOp1, AccessMasks.ClickOp10);
        player.closeInterface(InterfaceType.INVENTORY);
        changeInventoryAccess();
        resetSearch();
    }

    public void openMyOffers() {
        //if(player.getBankPin().requiresVerification(p -> openMyOffers()))
        //    return;
        player.openInterface(InterfaceType.MAIN_STRETCHED, Interface.TRADING_POST_MY_OFFERS);
        player.getPacketSender().sendAccessMask(1006, 5, 0, 100, AccessMasks.ClickOp1, AccessMasks.ClickOp10);
        changeInventoryAccess();
        updateMyOffers();
    }

    public void openHistory() {
        player.openInterface(InterfaceType.MAIN, 383);
        player.getPacketSender().sendClientScript(1644);
        Iterator<ExchangeHistory> iterator = exchangeHistory.descendingIterator();
        int index = 0;
        while (iterator.hasNext()) {
            ExchangeHistory record = iterator.next();
            player.getPacketSender().sendClientScript(1645, "iiiii", index++, record.getItemId(), record.getType() == ExchangeType.BUYING ? 0 : 1, record.getItemQuantity(), record.getPrice());
        }
        player.getPacketSender().sendClientScript(1646);
    }

    private void promptCreateOffer(int itemId) {
        if (tradePostOffers.size() >= MAX_MY_OFFERS) {
            player.sendMessage("You cannot create more sell offers.");
            return;
        }
        ItemDefinition itemDefinition = ItemDefinition.get(itemId);
        if (itemDefinition == null || !itemDefinition.tradeable || itemId == COINS_995) {
            player.sendMessage("You cannot trade this item.");
            return;
        }
        final int unnotedId = !itemDefinition.isNote() ? itemId : itemDefinition.fromNote().id;
        if (tradePostOffers.stream().anyMatch(offer -> offer.getItem().getId() == unnotedId)) {
            player.sendMessage("You are already selling this item.");
            return;
        }
        if (player.getInventory().getAmount(itemId) > 1) {
            player.integerInput("Enter item amount you would like to sell:", amount -> {
                if (!player.getInventory().contains(itemId, amount)) {
                    player.sendMessage("You do not have that many of this item!");
                    player.closeDialogue();
                    return;
                }
                promptPrice(itemId, unnotedId, amount);
            });
        } else {
            promptPrice(itemId, unnotedId, 1);
        }
    }

    private void promptPrice(int itemId, int unnotedId, int amount) {
        player.integerInput("Enter price per item:", price -> {
            player.getInventory().remove(itemId, amount);
            tradePostOffers.add(
                    new TradePostOffer(
                            player.getName(),
                            new Item(unnotedId, amount),
                            price,
                            System.currentTimeMillis()
                    )

            );
            updateMyOffers();
        });
    }

    private void promptAdjustOffer(int index) {
        if (index >= tradePostOffers.size()) {
            return;
        }

        player.integerInput("Enter price per item:", price -> {
            TradePostOffer offer = tradePostOffers.get(index);
            tradePostOffers.set(index,
                    new TradePostOffer(
                            player.getName(),
                            new Item(offer.getItem().getId(), offer.getItem().getAmount()),
                            price,
                            offer.getTimestamp()
                    )
            );
            updateMyOffers();
        });
    }

    private void resetOffer(int index) {
        if (index >= tradePostOffers.size()) {
            return;
        }

        TradePostOffer offer = tradePostOffers.remove(index);
        if (player.getInventory().hasRoomFor(offer.getItem())) {
            player.getInventory().add(offer.getItem().getId(), offer.getItem().getAmount());
        } else {
            player.getBank().add(offer.getItem().getId(), offer.getItem().getAmount());
            player.sendMessage(offer.getItem().getAmount() + "x " + offer.getItem().getDef().name + " has been send to your bank!");
        }
        updateMyOffers();
    }

    private void changeInventoryAccess() {
        player.openInterface(InterfaceType.INVENTORY, Interface.TRADING_POST_INVENTORY);
        player.getPacketSender().sendClientScript(149, "IviiiIsssss", Interface.TRADING_POST_INVENTORY << 16, 93, 4, 7, 0, -1,
                "Select", "", "", "", "");
        player.getPacketSender().sendAccessMask(Interface.TRADING_POST_INVENTORY, 0, 0, 27, 1086);
    }

    private void updateMyOffers() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < MAX_MY_OFFERS; index++) {
            if (index >= tradePostOffers.size()) {
                break;
            }
            TradePostOffer offer = tradePostOffers.get(index);
            if (offer == null) break;
            String price = "<col=ffffff>" + (formatPrice(offer.getPricePerItem()) + " ea");
            String totalPrice = "<col=999999>" + formatPrice((long) offer.getPricePerItem() *
                    offer.getItem().getAmount());
            sb.append(offer.getItem().getDef().name);
            sb.append("|");
            sb.append(price);
            sb.append("|");
            sb.append(totalPrice);
            if (index < tradePostOffers.size() - 1) {
                sb.append("|");
            }
            //updateMyOffer(index, index >= tradePostOffers.size() ? null : tradePostOffers.get(index));
        }
        if (tradePostOffers.size() < MAX_MY_OFFERS) {
            if (!tradePostOffers.isEmpty())
                sb.append("|");
            sb.append("Open slot");
            int emptyAmt = MAX_MY_OFFERS - tradePostOffers.size();
            if (emptyAmt > 1)
                sb.append(" x").append(emptyAmt);
            sb.append("|<col=ffffff>-|");
        }
        player.getPacketSender().sendClientScript(10075, "s", sb.toString());
        int slot = 2;
        for (TradePostOffer offer : tradePostOffers) {
            player.getPacketSender().sendClientScript(9006, "iiii", 1006 << 16 | 5, slot, offer.getItem().getId(), offer.getItem().getAmount());
            slot += 12;
        }
    }

    private List<TradePostOffer> findViewOffers() {
        return World.getPlayerStream()
                .flatMap(player -> player.getTradePost().tradePostOffers.stream())
                .filter(offer -> {
                    if (offer.getUsername().equalsIgnoreCase(player.getName())) return false;
                    if (searchText == null) {
                        return true;
                    }

                    return offer.getItem().getDef().name.toLowerCase().contains(searchText);
                }).sorted((offerA, offerB) -> {
                    switch (sort) {
                        case QUANT_DESCENDING:
                            return offerB.getItem().getAmount() - offerA.getItem().getAmount();
                        case QUANT_ASCENDING:
                            return offerA.getItem().getAmount() - offerB.getItem().getAmount();
                        case PRICE_DESCENDING:
                            return offerB.getPricePerItem() - offerA.getPricePerItem();
                        case PRICE_ASCENDING:
                            return offerA.getPricePerItem() - offerB.getPricePerItem();
                        case AGE_DESCENDING:
                            return (int) (offerA.getTimestamp() - offerB.getTimestamp());
                        case AGE_ASCENDING:
                        default:
                            return (int) (offerB.getTimestamp() - offerA.getTimestamp());
                    }
                }).limit(MAX_VIEW_OFFERS)
                .collect(Collectors.toList());
    }

    private void sortByQuantity() {
        if (sort == TradePostSort.QUANT_DESCENDING) {
            sort = TradePostSort.QUANT_ASCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 21, 1050);
        } else {
            sort = TradePostSort.QUANT_DESCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 21, 1051);
        }
        updateViewOffers();
        player.getPacketSender().setHidden(1005, 21, false);
        player.getPacketSender().setHidden(1005, 7, true);
        player.getPacketSender().setHidden(1005, 8, true);
    }

    private void sortByPrice() {
        if (sort == TradePostSort.PRICE_DESCENDING) {
            sort = TradePostSort.PRICE_ASCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 7, 1050);
        } else {
            sort = TradePostSort.PRICE_DESCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 7, 1051);
        }
        updateViewOffers();
        player.getPacketSender().setHidden(1005, 21, true);
        player.getPacketSender().setHidden(1005, 7, false);
        player.getPacketSender().setHidden(1005, 8, true);
    }

    private void sortByAge() {
        if (sort == TradePostSort.AGE_DESCENDING) {
            sort = TradePostSort.AGE_ASCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 8, 1050);
        } else {
            sort = TradePostSort.AGE_DESCENDING;
            player.getPacketSender().sendClientScript(44, "ii", 1005 << 16 | 8, 1051);
        }
        updateViewOffers();
        player.getPacketSender().setHidden(1005, 21, true);
        player.getPacketSender().setHidden(1005, 7, true);
        player.getPacketSender().setHidden(1005, 8, false);
    }

    private void promptSearch(int option) {
        switch (option) {
            case 1:
            default:
                player.dialogue(false,
                        new OptionsDialogue("How would you like to search?",
                                new Option("Item name search", () -> {
                                    player.stringInput("Enter item name to search for:", searchText -> {
                                        this.searchText = searchText;
                                        updateSearch();
                                    });
                                }),
                                new Option("Item search", () -> {
                                    player.itemSearch("Check listings for:", false, item -> {
                                        this.searchText = ItemDefinition.get(item).name.toLowerCase();
                                        updateSearch();
                                    });
                                })
                        )
                );
                break;
            case 2:
                player.closeDialogue();
                player.stringInput("Enter item name to search for:", searchText -> {
                    this.searchText = searchText;
                    updateSearch();
                });
                break;
            case 3:
                player.closeDialogue();
                player.itemSearch("Check listings for:", false, item -> {
                    this.searchText = ItemDefinition.get(item).name.toLowerCase();
                    updateSearch();
                });
                break;
        }
    }

    private void resetSearch() {
        this.searchText = null;
        player.closeDialogue();
        updateSearch();
    }

    private void updateSearch() {
        updateViewOffers();

        String search = searchText == null ? "-" : searchText;
        if (search.length() > 20) {
            search = search.substring(0, 18) + "...";
        }

        player.getPacketSender().sendString(Interface.TRADING_POST_VIEW, 16, search);
    }

    private void buy(int index) {
        if (index >= viewOffers.size()) {
            return;
        }
        player.integerInput("Amount to Buy:", amount -> {
            TradePostOffer offer = viewOffers.get(index);
            String sellerName = offer.getUsername();
            Player seller = World.getPlayer(sellerName);

            if (Objects.isNull(seller) || !seller.isOnline()) {
                player.sendMessage("That player is not online!");
                return;
            }

            if (seller == player) {
                player.sendMessage("You cannot buy items from yourself!");
                return;
            }

            if (amount < 1) {
                player.sendMessage("You can't buy 0 of an item idiot.");
                return;
            }

            if (amount > offer.getItem().getAmount()) {
                amount = offer.getItem().getAmount();
            }

            long price = (long) offer.getPricePerItem() * amount;

            if (price > Integer.MAX_VALUE) {
                player.sendMessage("You can't buy this many, do a lower amount");
                return;
            }

            if (!player.getInventory().contains(COINS_995, (int) price)) {
                player.sendMessage("You do not have enough to purchase this.");
                return;
            }

            int finalAmount = amount;
            player.dialogue(
                    new MessageDialogue("Are you sure you want to purchase: " + finalAmount + "x " + offer.getItem().getDef().name + " for a price of: " + formatPrice(price) + "?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> {

                                if (!seller.isOnline()) {
                                    player.sendMessage("That player is not online!");
                                    return;
                                }

                                if (!player.getInventory().contains(COINS_995, (int) price)) {
                                    player.sendMessage("You do not have enough to purchase this amount.");
                                    player.closeDialogue();
                                    return;
                                }

                                Optional<TradePostOffer> sellersOffer = seller.getTradePost().tradePostOffers.stream().filter(toChange -> (toChange.getItem().getId() == offer.getItem().getId()) && (toChange.getItem().getAmount() >= finalAmount)).findAny();

                                if (!sellersOffer.isPresent()) {
                                    player.sendMessage("This item has been removed or sold already!");
                                    player.closeDialogue();
                                    return;
                                }

                                sellersOffer.ifPresent(tradePostOffer ->  {

                                    int amountLeft = tradePostOffer.getItem().getAmount() - finalAmount;

                                    if (amountLeft < 0) {
                                        player.sendMessage("GRAND EXCHANGE [ERROR: 1] Contact a Developer!");
                                        player.closeDialogue();
                                        return;
                                    }

                                    player.getInventory().remove(COINS_995, (int) price);
                                    player.getBank().add(offer.getItem().getId(), finalAmount);
                                    seller.getTradePost().coffer += price;
                                    boolean outOfStock = amountLeft == 0;

                                    if (outOfStock) {
                                        seller.getTradePost().tradePostOffers.remove(tradePostOffer);
                                    } else {
                                        TradePostOffer newOffer = new TradePostOffer(
                                                offer.getUsername(),
                                                new Item(offer.getItem().getId(), amountLeft),
                                                offer.getPricePerItem(),
                                                offer.getTimestamp()
                                        );
                                        seller.getTradePost().tradePostOffers.set(seller.getTradePost().tradePostOffers.indexOf(tradePostOffer), newOffer);
                                    }
                                    seller.getTradePost().updateTradeHistory(new ExchangeHistory(offer.getItem().getId(), finalAmount, (int) price, ExchangeType.SELLING));
                                    player.getTradePost().updateTradeHistory(new ExchangeHistory(offer.getItem().getId(), finalAmount, (int) price, ExchangeType.BUYING));
                                    PlayerCounter.GE_BUYS.increment(player, 1);
                                    PlayerCounter.GE_SALES.increment(seller, 1);
                                    if (price > 1000) {
                                        PlayerCounter.GE_SPENT.increment(player, (int) price/1000);
                                        PlayerCounter.GE_PROFIT.increment(seller, (int) price/1000);
                                    }
                                    if (outOfStock) {
                                        seller.sendMessage("<col=00c203>" + "Grand Exchange: Finished selling all of " + offer.getItem().getDef().name + ".</col>");
                                    } else {
                                        seller.sendMessage("<col=00c203>" + "Grand Exchange: " + finalAmount + "/" + offer.getItem().getAmount() + " of " + offer.getItem().getDef().name + " sold.</col>");
                                    }
                                    seller.sendMessage("Current Coffer: " + formatPrice(seller.getTradePost().coffer));
                                    player.sendMessage("<col=ff0000>" + "You have purchased " + finalAmount + "x " + offer.getItem().getDef().name + " for a price of " + formatPrice(price) + ".</col>");
                                    player.getTradePost().openViewOffers();
                                });
                            }),
                            new Option("No", player::closeDialogue)
                    )
            );
        });
    }

    public void updateTradeHistory(ExchangeHistory record) {
        exchangeHistory.add(record);
        if (exchangeHistory.size() > MAX_HISTORY) {
            exchangeHistory.poll();
        }
    }

    private void updateViewOffers() {
        viewOffers = findViewOffers();

        for (int index = 0; index < MAX_VIEW_OFFERS; index++) {
            hideViewOffer(index, true);
        }

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < viewOffers.size(); index++) {
            TradePostOffer offer = viewOffers.get(index);
            String price = formatPrice(offer.getPricePerItem()) + (offer.getPricePerItem() < 100_000 ? "gp" : "");
            String totalPrice = offer.getItem().getAmount() > 1 ? formatPrice((long) offer.getPricePerItem() *
                    offer.getItem().getAmount()) : "";
            sb.append(price);
            sb.append("|");
            sb.append(totalPrice);
            sb.append("|");
            sb.append(offer.getUsername());
            sb.append("|");
            sb.append(formatAge(offer.getTimestamp()));
            if (index < viewOffers.size() - 1) {
                sb.append("|");
            }
        }
        player.getPacketSender().sendClientScript(10072, "ss", "Buy", sb.toString());
        int slot = 1;
        for (TradePostOffer offer : viewOffers) {
            player.getPacketSender().sendClientScript(9006, "iiii", 1005 << 16 | 19, slot, offer.getItem().getId(), offer.getItem().getAmount());
            slot += 7;
        }
    }

    private void hideViewOffer(int index, boolean hidden) {
        player.getPacketSender().sendClientScript(69, "ii", hidden ? 1 : 0, Interface.TRADING_POST_VIEW << 16 | (44 + (9 * index)));
    }

    private static String formatPrice(long price) {
        if (price > 9_999_999) {
            return NumberUtils.formatNumber(price / 1000_000) + "M";
        } else if (price > 99_999) {
            return NumberUtils.formatNumber(price / 1000) + "K";
        }

        return NumberUtils.formatNumber(price);
    }

    private String formatAge(long timestamp) {
        long elapsed = System.currentTimeMillis() - timestamp;
        long days = TimeUnit.MILLISECONDS.toDays(elapsed);
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);

        return days > 0 ? days + "d"
                : hours > 0 ? hours + "h"
                : minutes + "min";
    }

    public static void openCoffer(Player player) {
        if (player.getGameMode().isIronMan()) {
            player.sendMessage("Your gamemode prevents you from accessing the Grand Exchange!");
            return;
        }
        long coffer = player.getTradePost().coffer;

        player.integerInput("How much would you like to withdraw from coffer: " + formatPrice(coffer), amount -> {

            if (amount < 1) {
                player.sendMessage("You can't with 0 of an item idiot.");
                return;
            }

            if (coffer < amount) {
                player.sendMessage("You do not have this much in your coffer!");
                return;
            }

            long inventoryAmount = player.getInventory().getAmount(COINS_995);
            if (inventoryAmount + amount >= Integer.MAX_VALUE) {
                player.sendMessage("Please bank the coins in your inventory before doing this!");
                return;
            }

            player.getInventory().add(new Item(COINS_995, amount));
            player.getTradePost().coffer -= amount;
        });
    }

    static {
        NPCAction.register(2148, "history", (player, npc) -> player.getTradePost().openHistory());
        InterfaceHandler.register(Interface.TRADING_POST_INVENTORY, handler -> {
            handler.actions[0] = new InterfaceAction() {
                public void handleClick(Player player, int option, int slot, int itemId) {
                    Item item = player.getInventory().get(slot);
                    if (item.hasAttributes()) {
                        player.sendMessage("You can't offer upgraded items on the Grand Exchange.");
                        return;
                    }
                    if (!player.isVisibleInterface(1006))
                        player.getTradePost().openMyOffers();
                    player.getTradePost().promptCreateOffer(itemId);
                }
            };
        });

        InterfaceHandler.register(Interface.TRADING_POST_MY_OFFERS, handler -> {
            handler.actions[2] = (SimpleAction) player -> player.getTradePost().openViewOffers();
            handler.actions[5] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 10 && itemId > -1) {
                    new Item(itemId, 1).examine(player);
                    return;
                }
                int index = slot / 12;
                if (slot % 12 == 6) {
                    player.getTradePost().promptAdjustOffer(index);
                }
                if (slot % 12 == 9) {
                    player.getTradePost().resetOffer(index);
                }
            };
        });

        InterfaceHandler.register(Interface.TRADING_POST_VIEW, handler -> {
            handler.actions[17] = (SimpleAction) player -> {
                player.getTradePost().openMyOffers();
            };
            handler.actions[22] = (SimpleAction) player -> {
                player.getTradePost().sortByQuantity();
            };
            handler.actions[23] = (SimpleAction) player -> {
                player.getTradePost().sortByPrice();
            };
            handler.actions[24] = (SimpleAction) player -> {
                player.getTradePost().sortByAge();
            };
            handler.actions[9] = (OptionAction) (player, option) -> {
                player.getTradePost().promptSearch(option);
            };
            handler.actions[12] = (SimpleAction) player -> {
                player.getTradePost().resetSearch();
            };
            handler.actions[19] = (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 10) {
                    new Item(itemId, 1).examine(player);
                    return;
                }
                player.getTradePost().buy(slot / 7);
            };
        });
        InterfaceHandler.register(383, handler -> {
            handler.actions[2] = (SimpleAction) player -> player.getTradePost().openViewOffers();
        });

        for(int trader : new int[]{2149, 2148}) {
            NPCAction.register(trader, "sets", (player, npc) -> ItemSet.open(player));
            NPCAction.register(trader, "exchange", ((player, npc) -> {
                if (player.getGameMode().isIronMan()) {
                    player.sendMessage("Your gamemode prevents you from accessing the Grand Exchange!");
                    return;
                }
                player.getTradePost().openViewOffers();
            }));
        }
        for (int objId : Arrays.asList(60000, 60001, 60002, 60003, 60004, 60005)) {
            ObjectAction.register(objId, "exchange", (player, obj) -> {
                if (player.getGameMode().isIronMan()) {
                    player.sendMessage("Your gamemode prevents you from accessing the Grand Exchange!");
                    return;
                }
                player.getTradePost().openViewOffers();
            });
            ObjectAction.register(objId, "item sets", (player, obj) -> ItemSet.open(player));
        }
    }
}