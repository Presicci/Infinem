package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.shop.Currency;
import io.ruin.utility.Color;

import java.util.*;

public class Clue {

    private static int OFFSET;

    public static final Clue[] CLUES = new Clue[600];

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

    public Clue(ClueType type, StepType stepType) {
        this.id = OFFSET++;
        this.type = type;
        CLUES[id] = this;
        CLUES_BY_CATEGORY.get(stepType).get(type.ordinal()).add(this);
    }

    public void open(Player player) {
        /* override required */
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

}
