package io.ruin.model.item.actions.impl.teleport;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/3/2024
 */
public class BookOfTheDead {

    private static final String KEY = "MEMOIR_CHARGES";
    private static final int MEMOIRS = 21760;
    private static final int BOOK_OF_THE_DEAD = 25818;
    private static final List<Item> CHARGE_ITEMS = Arrays.asList(Rune.LAW.toItem(1), Rune.BODY.toItem(1), Rune.MIND.toItem(1), Rune.SOUL.toItem(1));


    private static void teleport(Player player, Item item, Destination destination) {
        int currentCharges = player.getAttributeIntOrZero(KEY);
        if (currentCharges <= 0) {
            player.sendMessage("Your " + item.getDef().name + " doesn't have any charges remaining.");
            return;
        }
        int newCharged = player.incrementNumericAttribute(KEY, -1);
        player.sendMessage("Your " + item.getDef().name + " now has " + newCharged + " charges remaining.");
        ModernTeleport.teleport(player, destination.teleportBounds);
    }

    private static void reminisce(Player player, Item item) {
        int currentCharges = player.getAttributeIntOrZero(KEY);
        if (currentCharges <= 0) {
            player.sendMessage("Your " + item.getDef().name + " doesn't have any charges remaining.");
            return;
        }
        List<Option> options = new ArrayList<>();
        for (Destination destination : Destination.values()) {
            options.add(new Option(destination.optionName, () -> teleport(player, item, destination)));
        }
        player.dialogue(new OptionsDialogue(options));
    }

    private static void check(Player player, Item item) {
        int charges = player.getAttributeIntOrZero(KEY);
        player.sendMessage("Your " + item.getDef().name + " has " + charges + " charges remaining.");
    }

    private static void charge(Player player, Item item, int amt) {
        int charges = player.getAttributeIntOrZero(KEY);
        int maxCharges = item.getId() == BOOK_OF_THE_DEAD ? 250 : 100;
        if (charges >= maxCharges) {
            player.sendMessage("Your " + item.getDef().name + " can't hold any more charges.");
            return;
        }
        int inventoryCharges = CHARGE_ITEMS.stream().mapToInt(i -> player.getInventory().getAmount(i.getId()) / i.getAmount()).min().getAsInt();
        if (inventoryCharges == 0) {
            player.sendMessage("The " + item.getDef().name + " requires 1 law, body, mind, and soul rune for each charge.");
            return;
        }
        int chargesToAdd = Math.min(amt, Math.min(inventoryCharges, maxCharges - charges));
        CHARGE_ITEMS.forEach(i -> player.getInventory().remove(i.getId(), i.getAmount() * chargesToAdd));
        player.getStats().addXp(StatType.Magic, 10, true);
        player.incrementNumericAttribute(KEY, chargesToAdd);
        check(player, item);
    }

    static {
        for (int itemId : Arrays.asList(MEMOIRS, BOOK_OF_THE_DEAD)) {
            ItemAction.registerInventory(itemId, "reminisce", BookOfTheDead::reminisce);
            ItemAction.registerInventory(itemId, "check", BookOfTheDead::check);
            ItemAction.registerEquipment(itemId, "check", BookOfTheDead::check);
            ItemObjectAction.register(itemId, 31714, (player, item, obj) -> player.integerInput("How many charges would you like to add?", i -> charge(player, item, i)));
            for (Destination destination : Destination.values()) {
                ItemAction.registerEquipment(itemId, destination.optionName, (player, item) -> teleport(player, item, destination));
            }
            for (Item rune : CHARGE_ITEMS) {
                ItemItemAction.register(itemId, rune.getId(), (player, book, secondary) -> {
                    player.integerInput("How many charges would you like to add?", i -> charge(player, book, i));
                });
            }
        }
        ObjectAction.register(31714, "inspect", (player, object) -> player.dialogue(new MessageDialogue("'Obey the Laws of your Body, Mind and Soul' - King Kharedst IV")));
    }

    @AllArgsConstructor
    private enum Destination {
        LUNCH_BY_THE_LANCALLIUMS(new Bounds(1713, 3610, 1714, 3613, 0), "Lunch by the lancalliums"),
        THE_FISHERS_FLUTE(new Bounds(1802, 3746, 1804, 3749, 0), "The fisher's flute"),
        HISTORY_AND_HEARSAY(new Bounds(1476, 3575, 1480, 3576, 0), "History and hearsay"),
        JEWELLERY_OF_JUBILATION(new Bounds(1542, 3759, 1545, 3762, 0), "Jewellery of jubilation"),
        A_DARK_DISPOSITION(new Bounds(1678, 3744, 1680, 3747, 0), "A dark disposition");

        private final Bounds teleportBounds;
        private final String optionName;
    }
}
