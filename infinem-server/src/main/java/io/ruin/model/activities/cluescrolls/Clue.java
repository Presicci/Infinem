package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.cluescrolls.puzzles.PuzzleBox;
import io.ruin.model.activities.cluescrolls.puzzles.PuzzleBoxType;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.shop.Currency;
import io.ruin.utility.Color;

import java.util.*;

public class Clue {

    private static int OFFSET;

    public static final List<Integer> SCROLL_BOXES = Arrays.asList(24361, 24362, 24363, 24364, 24365);

    public static final Clue[] CLUES = new Clue[700];

    // Clue storage, separated by ClueType
    public static final Map<StepType, Map<Integer, List<Clue>>> CLUES_BY_CATEGORY = new HashMap<StepType, Map<Integer, List<Clue>>>() {{
        for (StepType t : StepType.values()) {
            put(t, new HashMap<Integer, List<Clue>>() {{
                put(0, new ArrayList<>());
                put(1, new ArrayList<>());
                put(2, new ArrayList<>());
                put(3, new ArrayList<>());
                put(4, new ArrayList<>());
                put(5, new ArrayList<>());
            }});
        }
    }};

    /**
     * Separator
     */

    public final int id;

    public final ClueType type;

    private final boolean requiresPuzzleBox;

    public Clue(ClueType type, StepType stepType) {
        this(type, stepType, false);
    }

    public Clue(ClueType type, StepType stepType, boolean requiresPuzzleBox) {
        this.id = OFFSET++;
        this.type = type;
        this.requiresPuzzleBox = requiresPuzzleBox;
        CLUES[id] = this;
        CLUES_BY_CATEGORY.get(stepType).get(type.ordinal()).add(this);
    }

    public void open(Player player) {
        /* override required */
    }

    public boolean canAdvance(Player player) {
        for(Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            ItemDefinition def = item.getDef();
            if (def.clueType == null)
                continue;
            ClueSave save = def.clueType.getSave(player);
            if (save == null || save.id != this.id)
                continue;
            return true;
        }
        return false;
    }

    public boolean advance(Player player) {
        for(Item item : player.getInventory().getItems()) {
            if(item == null)
                continue;
            ItemDefinition def = item.getDef();
            if(def.clueType == null)
                continue;
            ClueSave save = def.clueType.getSave(player);
            if(save == null || save.id != this.id)
                continue;
            save.id = -1;
            if(--save.remaining > 0) {
                player.dialogue(new ItemDialogue().one(def.clueType.clueId, "Good job, you are now one step closer to completing your clue scroll."));
            } else {
                item.setId(def.clueType.casketId);
                String message = "Great job, you have completed your clue scroll!";
                int completedClues = 0;
                int pointAmount = 1;
                if (def.clueType == ClueType.BEGINNER) {
                    completedClues = PlayerCounter.BEGINNER_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " beginner clue scrolls!";
                } else if (def.clueType == ClueType.EASY) {
                    completedClues = PlayerCounter.EASY_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " easy clue scrolls!";
                    pointAmount = 2;
                } else if (def.clueType == ClueType.MEDIUM) {
                    completedClues = PlayerCounter.MEDIUM_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " medium clue scrolls!";
                    pointAmount = 3;
                } else if (def.clueType == ClueType.HARD) {
                    completedClues = PlayerCounter.HARD_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " hard clue scrolls!";
                    pointAmount = 4;
                } else if (def.clueType == ClueType.ELITE) {
                    completedClues = PlayerCounter.ELITE_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " elite clue scrolls!";
                    pointAmount = 6;
                }  else if (def.clueType == ClueType.MASTER) {
                    completedClues = PlayerCounter.MASTER_CLUES_COMPLETED.increment(player, 1);
                    message += " You have now completed a total of " + NumberUtils.formatNumber(completedClues) + " master clue scrolls!";
                    pointAmount = 10;
                }
                player.dialogue(new ItemDialogue().one(def.clueType.casketId, message));
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.COMPLETECLUE, def.clueType.toString().toLowerCase());
                player.sendMessage(message);
                pointAmount *= (completedClues % 100 == 0 ? 4 : completedClues % 10 == 0 ? 2 : 1);
                int newAmt = Currency.TREASURE_TRAIL_POINTS.getCurrencyHandler().addCurrency(player, pointAmount);
                player.sendMessage(Color.GREEN.wrap("You earn " + pointAmount + " treasure trail points. You now have " + newAmt + " points."));
            }
            return true;
        }
        return false;
    }

    public boolean requiresPuzzleBox() {
        return requiresPuzzleBox;
    }

    private Item getPuzzleBox(Player player) {
        for (PuzzleBoxType boxType : PuzzleBoxType.values()) {
            if (boxType.getLevel() != type) continue;
            Item item = player.getInventory().findItemIgnoringAttributes(boxType.getPuzzleBox(), false);
            if (item != null) return item;
        }
        return null;
    }

    public boolean hasPuzzleBox(Player player) {
        for (PuzzleBoxType boxType : PuzzleBoxType.values()) {
            if (boxType.getLevel() != type) continue;
            if (player.findItem(boxType.getPuzzleBox(), true) != null) return true;
        }
        return false;
    }

    public void puzzleBoxDialogue(Player player, NPC npc) {
        if (!requiresPuzzleBox()) return;
        if (hasPuzzleBox(player)) {
            Item item = getPuzzleBox(player);
            if (item == null) {
                player.dialogue(new NPCDialogue(npc, "You must complete the puzzle I gave you."));
                return;
            }
            if (PuzzleBox.isCompleted(item)) {
                player.dialogue(
                        new NPCDialogue(npc, "Good work!"),
                        new ActionDialogue(() -> {
                            PuzzleBox.clearPuzzleBoxes(player, type);
                            this.advance(player);
                        })
                );
            } else {
                player.dialogue(new NPCDialogue(npc, "Sorry but you haven't solved the puzzle yet."));
            }
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Here, solve this for me."),
                    new ActionDialogue(() -> {
                        if (!player.getInventory().hasFreeSlots(1)) {
                            player.dialogue(new NPCDialogue(npc, "Make some room so I can give you this puzzle box."));
                            return;
                        }
                        PuzzleBoxType puzzleBoxType = PuzzleBoxType.random(type);
                        int puzzleBox = puzzleBoxType.getPuzzleBox();
                        player.getInventory().add(puzzleBox);
                        player.dialogue(new ItemDialogue().one(puzzleBox, npc.getDef().name + " has given you a puzzle box!"));
                    })
            );
        }
    }
}
