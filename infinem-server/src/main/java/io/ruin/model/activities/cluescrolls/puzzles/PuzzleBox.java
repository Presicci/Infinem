package io.ruin.model.activities.cluescrolls.puzzles;

import io.ruin.api.utils.Random;
import io.ruin.cache.EnumMap;
import io.ruin.model.activities.cluescrolls.ClueSave;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.utility.Utils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.val;
import org.jetbrains.annotations.NotNull;

/**
 * Manages the treasure trails puzzle boxes throughout the game.
 *
 * @author Kris | 29. march 2018 : 23:40.02
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public final class PuzzleBox {

    /**
     * The player managing the puzzle box.
     */
    private final Player player;

    /**
     * The puzzle that we're currently managing.
     */
    private PuzzleBoxType puzzle;

    /**
     * The container of the current puzzle pieces.
     */
    private ItemContainer currentPuzzle;

    /**
     * Whether the puzzle is already complete or not.
     */
    private boolean complete;

    /**
     * Constructs the puzzle box for the player.
     *
     * @param player the player whose box this is.
     */
    public PuzzleBox(final Player player) {
        this.player = player;
    }

    /**
     * Opens the puzzle interface for the player. If the puzzle is null, it will
     * generate a puzzle for the player.
     *
     * @param id the id of the puzzle box that we're managing.
     */
    public void openPuzzle(final int id) {
        if (currentPuzzle == null || puzzle != null && puzzle.getPuzzleBox() != id) {
            generatePuzzle(id);
        }
        assert puzzle != null : "Puzzle is not yet set";
        val puzzleBox = player.getPuzzleBox();
        assert puzzleBox.containsPuzzle() : "A puzzle has not yet been constructed.";
        player.openInterface(InterfaceType.MAIN, 306);
        player.getPacketSender().sendAccessMask(306, 4, 0, 25, 2);
        puzzleBox.fullRefresh();
    }

    public void reset() {
        currentPuzzle = null;
    }

    /**
     * Performs a full container and varp refresh on the current puzzle.
     */
    public void fullRefresh() {
        assert puzzle != null : "Puzzle is not set yet.";
        currentPuzzle.sendAll = true;
        currentPuzzle.sendUpdates();
        Config.PUZZLE_INDEX.set(player, EnumMap.get(1864).valuesAsKeysInts().get(puzzle.getEnumId()));
    }

    /**
     * Checks whether a puzzle has currently been generated.
     *
     * @return whether a puzzle has been generated.
     */
    public boolean containsPuzzle() {
        return puzzle != null;
    }

    /**
     * Gets the current int enum for the puzzle box, resulting an enum of slot:itemId for all the puzzle pieces.
     *
     * @return the enum containing all the puzzle pieces in the correct order.
     */
    private EnumMap getEnum() {
        return EnumMap.get(puzzle.getEnumId());
    }

    /**
     * Gets the current empty slot based on the only available slot in the container.
     *
     * @return the current empty slot of the puzzle.
     */
    private int emptySlot() {
        val freeSlots = currentPuzzle.getFreeSlots();
        assert freeSlots == 1;
        return currentPuzzle.freeSlot();
    }

    /**
     * Checks whether the player can move that puzzle tile.
     *
     * @param slotId the slot on which the player clicked.
     * @return whether the player can move that puzzle tile or not.
     */
    private boolean canMove(final int slotId) {
        val emptySlot = emptySlot();
        return (slotId + 1) == emptySlot || (slotId - 1) == emptySlot || (slotId + 5) == emptySlot || (slotId - 5) == emptySlot;
    }

    /**
     * Shifts the puzzle tile upon clicking if possible.
     *
     * @param slot the slot that was clicked.
     */
    public void shiftPuzzle(final int slot) {
        if (complete || !canMove(slot)) {
            return;
        }
        val movedTile = currentPuzzle.get(slot);
        currentPuzzle.set(emptySlot(), movedTile);
        currentPuzzle.set(slot, null);
        currentPuzzle.sendUpdates();
    }

    /**
     * Checks whether the puzzle has been completed or not; if it has been and
     * the state isn't set to complete, it will update the puzzle box in
     * player's inventory - if there still is one - and inform the player that
     * they've solved the puzzle.
     */
    public void checkCompletion() {
        if (complete) {
            return;
        }
        val enumList = getEnum();
        for (int i = 0; i < 24; i++) {
            val enumValue = enumList.ints().get(i);
            val currentPiece = currentPuzzle.get(i);
            if (currentPiece == null || (currentPiece.getId() != enumValue)) {
                return;
            }
        }
        val item = findPuzzleItem();
        if (item == null) {
            return;
        }
        AttributeExtensions.setCharges(item, 1);
        complete = true;
        player.sendMessage("Congratulations! You've solved the puzzle!");
    }

    /**
     * Finds the puzzle box item in the player's inventory that matches this puzzle.
     *
     * @return the puzzle box item.
     */
    private Item findPuzzleItem() {
        val puzzleBox = puzzle.getPuzzleBox();
        return player.getInventory().findItem(puzzleBox);
    }

    /**
     * Generates the puzzle and fills it with the pieces in a random order.
     *
     * @param id the puzzle box id to fill the puzzle with.
     */
    private void generatePuzzle(final int id) {
        puzzle = PuzzleBoxType.getMap().get(id);
        currentPuzzle = new ItemContainer();
        currentPuzzle.init(player, 25, -1, 0, 140, false);
        currentPuzzle.sendAll = true;
        complete = false;
        currentPuzzle.clear();
        val firstPieceItemId = getEnum().ints().get(0);
        for (int i = 0; i < 24; i++) {
            currentPuzzle.set(i, new Item(firstPieceItemId + i));
        }
        shuffle();
    }

    /**
     * Shuffles the puzzle in a backwards direction to ensure the puzzle remains completeable and is not just a few steps away from being completed.
     */
    private void shuffle() {
        val availableMoves = new IntArrayList(4);
        int emptySlot = emptySlot();
        int lastSlot = -1;
        for (int i = 0; i < 40; i++) {
            val x = emptySlot % 5;
            val y = emptySlot / 5;
            if (x > 0) {
                availableMoves.add(emptySlot - 1);
            }
            if (x < 4) {
                availableMoves.add(emptySlot + 1);
            }
            if (y > 0) {
                availableMoves.add(emptySlot - 5);
            }
            if (y < 4) {
                availableMoves.add(emptySlot + 5);
            }
            availableMoves.rem(lastSlot);
            lastSlot = emptySlot;
            assert !availableMoves.isEmpty() : "No available moves remaining";
            assert ensureBoundaries(availableMoves) : "One or more of the available moves are out of boundaries: " + availableMoves;
            emptySlot = availableMoves.getInt(Random.get(availableMoves.size() - 1));
            assert canMove(emptySlot) : "Cannot move on the requested position of " + emptySlot + " with the puzzle of " + currentPuzzle.getItems();
            shiftPuzzle(emptySlot);
            assert emptySlot == emptySlot() : "Current empty slot does not match the container's empty slot.";
            availableMoves.clear();
        }
    }

    /**
     * Ensures that the slots provided in the list are within the boundaries of the puzzle palette.
     *
     * @param list the lsit of slots.
     * @return whether or not the slots are within boundaries of the puzzle palette.
     */
    private boolean ensureBoundaries(@NotNull final IntList list) {
        return Utils.findMatching(list, element -> element < 0 || element > 24) == null;
    }


    static {
        for (PuzzleBoxType puz : PuzzleBoxType.values()) {
            ItemAction.registerInventory(puz.getPuzzleBox(), "open", ((p, item) -> {
                if (AttributeExtensions.getCharges(item) == 1) {
                    p.sendMessage("You've already completed this puzzle.");
                    return;
                }
                p.getPuzzleBox().openPuzzle(item.getId());
            }));
            ItemAction.registerInventory(puz.getPuzzleBox(), "check steps", ((p, item) -> {
                ClueSave save = puz.getLevel().getSave(p);
                if(save == null)
                    p.sendMessage("You haven't started this clue yet.");
                else if(save.remaining == 1)
                    p.sendMessage("There is 1 step remaining in this clue.");
                else
                    p.sendMessage("There are " + save.remaining + " steps remaining in this clue.");
            }));
        }
        InterfaceHandler.register(306, h -> {
            h.actions[4] = (SlotAction)  (player, slot) -> player.getPuzzleBox().shiftPuzzle(slot);
            h.closedAction = ((player, integer) -> player.getPuzzleBox().checkCompletion());
        });
    }

}