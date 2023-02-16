package io.ruin.model.activities.cluescrolls.puzzles;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import it.unimi.dsi.fastutil.ints.*;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/16/2023
 */
public final class LightBox {

    /**
     * Constructs the light box object per player on login.
     *
     * @param player the player who to consruct the box for.
     */
    public LightBox(final Player player) {
        this.player = player;
    }

    /**
     * The player who's managing this box.
     */
    private final Player player;

    /**
     * A bitset containing 25 bits; one bit for each lightbulb.
     */
    private final BitSet bits = new BitSet(25);

    /**
     * A map of <buttonIndex, LightButtonObject> containing information about
     * each button. Initialized under the capacity of eight as there are only
     * eight buttons.
     */
    private final Int2ObjectMap<LightButton> lightButtons = new Int2ObjectOpenHashMap<>(8);

    /**
     * Whether the light box has already been solved or not.
     */
    private boolean complete;

    /**
     * Counters to determine how many buttons are toggled, and how many aren't.
     * Used to ensure that the puzzle has at least two buttons toggled/two
     * buttons not toggled.
     */
    private int unlitButtonCount, litButtonCount;

    /**
     * Constructs the light box itself, associates all of the bits to each
     * button and ensures that the puzzle itself becomes solvable. Ensures that
     * all the bulbs have at least one button associated to them. Additionally,
     * for the purpose of having a strategic way of solving the puzzle, each
     * button has one light bulb associated to it that can only be toggled by
     * this said button.
     */
    private void construct() {
        val availableBits = new IntArrayList(25);
        /* Populating the available bits with values from 0 to 24(inclusive) */
        for (int i = 0; i < 25; i++) {
            availableBits.add(i);
        }

        /* A list of bits that can only be toggled by one specific button. */
        val uniqueBits = new IntOpenHashSet(8);

        /*
         * A map of <bitIndex, amount> showing how many times each bit is used. Constructing the map with a capacity of 17, as eight of the bulbs are
         * unique to specific buttons.
         */
        val usedBits = new Int2IntOpenHashMap(17);

        /* Filling the list of unique bits with random values from the available bits, removing them from the available bits list. */
        for (int i = 0; i < 8; i++) {
            uniqueBits.add(availableBits.removeInt(Random.get(availableBits.size() - 1)));
        }
        /*
         * Variables for defining the number of bulbs to attach to the button,
         * the random value generated and the amount of bits it's been associated to.
         */
        int amount, value, num;

        /*
         * Populating the light buttons map with a random - odd amount of bits
         * associated to it; each button will have one unique light button that
         * can only be switched on or off using it
         */
        for (int i = 0; i < 8; i++) {
            /* Generating a random number(between 3 and 10) of bits that will be associated to this button */
            amount = Random.get(3, 10);
            int tryCount = 100;
            while (amount > 0) {
                if (--tryCount <= 0) {
                    break;
                }
                /* Generating a random value from 0 to 24(inclusive) */
                value = Random.get(24);
                /* If the specific value is a unique value and already  associated to a button, we continue and generate a new value. */
                if (uniqueBits.contains(value)) {
                    continue;
                }

                /* Obtain the number of times the specific bit has already been used, or 0 if the bit hasn't been touched yet. */
                num = usedBits.getOrDefault(value, 0);

                /* Limiting each lightbulb to have a maximum of 4 buttons attached to it. */
                if (num >= 4 || !addBit(i, value)) {
                    continue;
                }
                amount--;
                usedBits.put(value, num + 1);
            }
        }

        /* Loop over all of the bits one more time to ensure each light bulb is associated to at least one button. */
        for (int i = 0; i < 25; i++) {
            /* Get the number of times the specific bit has been associated to a button, or 0 if the bit is untouched. */
            num = usedBits.getOrDefault(i, 0);

            /* If the number of times this bit has been associated to a button is above 0, we skip the bit. */
            if (num > 0) {
                continue;
            }

            /* If the bit is listed as a unique bit, we skip it. */
            if (uniqueBits.contains(num)) {
                continue;
            }

            /* Add the bit to two random buttons, as each bulb needs to be associated to an odd amount of buttons, except for the uniques. */
            addBit(Random.get(7), i);
        }

        /*
         * An iterator for the used bits map; we need to iterate over all of the keys to ensure that all of the values are in fact in even quantities;
         * if the value isn't an even number, we're going to associate the specific bit to a random button out of the eight.
         */
        associate(usedBits);
    }

    /**
     * Associates the un-even buttons to random buttons on the interface.
     *
     * @param usedBits the used bits map.
     */
    private void associate(@NotNull final Int2IntOpenHashMap usedBits) {
        val it = usedBits.int2IntEntrySet().fastIterator();

        /* The entry object from the map. */
        Int2IntMap.Entry entry;

        /* The key and value representative ints for the map. */
        int k, v, value;
        while (it.hasNext()) {
            entry = it.next();
            v = entry.getIntValue();

            /* If the value is an odd amount, we continue on to the next bit in the map. */
            if ((v & 0x1) == 1) {
                continue;
            }
            k = entry.getIntKey();

            /* Generate a random button index to associate with the bit. */
            value = Random.get(7);
            addBit(value, k);
        }
    }

    /**
     * Adds the requested bit to the requested button, if it doesn't already
     * contain it.
     *
     * @param buttonIndex the index of the button to associate the bit with.
     * @param bit         the index of the bit to associate with the button.
     * @return false if the button already contains the bit,
     * true if the bit was added to the button successfully
     */
    private boolean addBit(final int buttonIndex, final int bit) {
        LightButton button = lightButtons.get(buttonIndex);
        if (button == null) {
            boolean lit = Random.get(1) == 0;
            /* To ensure we have at least 2 buttons lit and unlit simultaneously, so the puzzle doesn't automatically solve itself. */
            if (!lit) {
                if (unlitButtonCount > 6) {
                    lit = true;
                } else {
                    unlitButtonCount++;
                }
            } else {
                if (litButtonCount > 6) {
                    lit = false;
                } else {
                    litButtonCount++;
                }
            }
            button = new LightButton(lit, new IntArrayList(4));
            lightButtons.put(buttonIndex, button);
        }

        /* If this specific bit has already been associated to the button, we return false meaning the action was cancelled. */
        if (button.associatedBits.contains(bit)) {
            return false;
        }

        /* If the button has been toggled, we flip all of the bits that are associated to it. */
        if (button.toggled) {
            bits.flip(bit);
        }
        return button.associatedBits.add(bit);
    }

    private void reset() {
        complete = false;
        bits.clear();
        lightButtons.clear();
        construct();
    }

    /**
     * Opens the light box interface, makes the buttons clickable
     *
     * @param reset whether to reset the box statistics or not.
     */
    public void open(final boolean reset) {
        if (lightButtons.isEmpty() || reset) {
            reset();
        }
        refresh();
        player.openInterface(InterfaceType.MAIN, 322);
        player.getPacketSender().sendAccessMask(322, 8, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 9, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 10, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 11, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 12, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 13, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 14, -1, 0, 2);
        player.getPacketSender().sendAccessMask(322, 15, -1, 0, 2);
    }

    /**
     * Refreshes the toggled bits on the interface; if none of the bits are
     * toggled(as they're all toggled by default at value 0), we set the puzzle
     * state finished and inform the player, as well as lock the box.
     */
    private void refresh() {
        val longBit = bits.toLongArray();
        Config.LIGHT_BOX_INDEX.set(player, longBit.length == 0 ? 0 : (int) longBit[0]);
        if (longBit.length == 0) {
            val item = findLightboxItem();
            if (item == null) {
                return;
            }
            AttributeExtensions.setCharges(item, 2);
            player.sendMessage("As the last light turns on, you hear the latch release.");
            complete = true;
        }
    }

    /**
     * Finds the light box item in the player's inventory that matches this light box.
     *
     * @return the light box item.
     */
    private Item findLightboxItem() {
        val puzzleBox = Items.LIGHT_BOX;
        return player.getInventory().findItemIgnoringAttributes(puzzleBox, false);
    }

    /**
     * Handles pressing a button
     *
     * @param slot the button that was clicked.
     */
    public void press(final int slot) {
        if (complete) {
            player.sendMessage("You've already completed the light box puzzle.");
            return;
        }
        val button = lightButtons.get(slot);
        if (button == null) {
            return;
        }
        button.toggled = !button.toggled;
        for (int i = button.associatedBits.size() - 1; i >= 0; i--) {
            bits.flip(button.associatedBits.getInt(i));
        }
        refresh();
    }

    static {
        ItemAction.registerInventory(Items.LIGHT_BOX, "open", ((p, item) -> {
            if (AttributeExtensions.getCharges(item) == 2) {
                p.sendMessage("You've already solved this light box.");
                return;
            }
            p.getLightBox().open(AttributeExtensions.getCharges(item) == 0);
            if (AttributeExtensions.getCharges(item) == 0) {
                AttributeExtensions.setCharges(item, 1);
            }
        }));
        InterfaceHandler.register(322, h -> {
            h.actions[8] = (SlotAction)  (player, slot) -> player.getLightBox().press(0);
            h.actions[9] = (SlotAction)  (player, slot) -> player.getLightBox().press(1);
            h.actions[10] = (SlotAction)  (player, slot) -> player.getLightBox().press(2);
            h.actions[11] = (SlotAction)  (player, slot) -> player.getLightBox().press(3);
            h.actions[12] = (SlotAction)  (player, slot) -> player.getLightBox().press(4);
            h.actions[13] = (SlotAction)  (player, slot) -> player.getLightBox().press(5);
            h.actions[14] = (SlotAction)  (player, slot) -> player.getLightBox().press(6);
            h.actions[15] = (SlotAction)  (player, slot) -> player.getLightBox().press(7);
        });
    }

    /**
     * A class holding all the attributes of one button on the interface.
     *
     * @author Kris | 30. march 2018 : 21:35.54
     * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
     */
    private static class LightButton {

        /**
         * Whether this button has been toggled or not.
         */
        private boolean toggled;

        /**
         * A list of bits that have been associated to this button.
         */
        private final IntList associatedBits;

        /**
         * Constructs a light button with the said state and the said list of
         * bits associated to it.
         *
         * @param toggled        whether the button should be enabled or not.
         * @param associatedBits a list of bits associated to this button.
         */
        public LightButton(final boolean toggled, final IntList associatedBits) {
            this.toggled = toggled;
            this.associatedBits = associatedBits;
        }
    }
}