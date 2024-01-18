package io.ruin.model.entity.npc.actions.zanaris.puropuro;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.hunter.Impling;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2024
 */
public class ElnockExchange {

    @AllArgsConstructor
    private enum ExchangeItem {
        IMP_REPELLENT(0, new Item(Items.IMP_REPELLENT), new Item[] { new Item(Items.BABY_IMPLING_JAR, 3), new Item(Items.YOUNG_IMPLING_JAR, 2), new Item(Items.GOURMET_IMPLING_JAR) }),
        MAGIC_BUTTERFLY_NET(3, new Item(Items.MAGIC_BUTTERFLY_NET), new Item[] { new Item(Items.GOURMET_IMPLING_JAR, 3), new Item(Items.EARTH_IMPLING_JAR, 2), new Item(Items.ESSENCE_IMPLING_JAR) }),
        JAR_GENERATOR(6, new Item(Items.JAR_GENERATOR), new Item[] { new Item(Items.ESSENCE_IMPLING_JAR, 3), new Item(Items.ECLECTIC_IMPLING_JAR, 2), new Item(Items.NATURE_IMPLING_JAR) }),
        IMPLING_JAR(9, new Item(Items.IMPLING_JAR, 3), null);

        private final int slot;
        private final Item product;
        private final Item[] payment;

        private static ExchangeItem get(int slot) {
            for (ExchangeItem item : values()) {
                if (item.slot == slot) return item;
            }
            return null;
        }
    }

    protected static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.ELNOCK_EXCHANGE);
        player.getPacketSender().sendAccessMask(Interface.ELNOCK_EXCHANGE, 4, 0, 9, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(Interface.ELNOCK_EXCHANGE, 7, 0, 9, AccessMasks.ClickOp1);
    }

    private static void selectOption(Player player, int slot) {
        player.putTemporaryAttribute("ELNOCK", slot);
    }

    private static void purchase(Player player) {
        if (!player.hasTemporaryAttribute("ELNOCK")) {
            player.sendMessage("Select something to purchase first!");
            return;
        }
        int slot = player.getTemporaryAttributeIntOrZero("ELNOCK");
        ExchangeItem exchangeItem = ExchangeItem.get(slot);
        if (exchangeItem == ExchangeItem.IMP_REPELLENT) {
            player.dialogue(false,
                    new MessageDialogue("Are you sure you want to buy IMP REPELLENT? It is almost entirely useless. If you want it, I wont stop you. This is your last warning. Imp repellent sucks."),
                    new OptionsDialogue("Are you out of your damn mind?",
                            new Option("Absolutely", () -> purchase(player, exchangeItem)),
                            new Option("Thanks for the warning, not interested"))
            );
        } else {
            purchase(player, exchangeItem);
        }
    }

    private static void purchase(Player player, ExchangeItem exchangeItem) {
        if (exchangeItem == null) return;
        if (exchangeItem.payment == null) {
            if (!player.getInventory().hasFreeSlots(2)) {
                player.dialogue(false, new MessageDialogue("You need 2 free inventory spaces to purchase impling jars."));
                return;
            }
            for (Impling impling : Impling.values()) {
                if (player.getInventory().contains(impling.jarId)) {
                    player.getInventory().remove(impling.jarId, 1);
                    player.getInventory().add(exchangeItem.product);
                    return;
                }
            }
            player.dialogue(false, new MessageDialogue("You need any filled impling jar to exchange for impling jars."));
            return;
        }
        for (Item item : exchangeItem.payment) {
            if (!player.getInventory().contains(item)) {
                player.dialogue(false, new MessageDialogue("You do not have the required implings to purchase that item."));
                return;
            }
        }
        for (Item item : exchangeItem.payment) {
            player.getInventory().remove(item);
        }
        player.getInventory().add(exchangeItem.product);
    }

    static {
        InterfaceHandler.register(Interface.ELNOCK_EXCHANGE, h -> {
            h.actions[4] = (SlotAction) ElnockExchange::selectOption;
            h.actions[7] = (SimpleAction) ElnockExchange::purchase;
        });
    }
}
