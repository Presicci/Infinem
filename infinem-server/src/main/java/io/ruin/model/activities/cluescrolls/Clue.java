package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;

public class Clue {

    private static int OFFSET;

    public static final Clue[] CLUES = new Clue[600];

    /**
     * Separator
     */

    public final int id;

    public final ClueType type;

    public Clue(ClueType type) {
        this.id = OFFSET++;
        this.type = type;
        CLUES[id] = this;
    }

    public void open(Player player) {
        /* override required */
    }

    public boolean advance(Player player) {
        for(Item item : player.getInventory().getItems()) {
            if(item == null)
                continue;
            ItemDef def = item.getDef();
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
                if(def.clueType == ClueType.BEGINNER)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.BEGINNER_CLUES_COMPLETED.increment(player, 1)) + " beginner clue scrolls!";
                else if(def.clueType == ClueType.EASY)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.EASY_CLUES_COMPLETED.increment(player, 1)) + " easy clue scrolls!";
                else if(def.clueType == ClueType.MEDIUM)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.MEDIUM_CLUES_COMPLETED.increment(player, 1)) + " medium clue scrolls!";
                else if(def.clueType == ClueType.HARD)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.HARD_CLUES_COMPLETED.increment(player, 1)) + " hard clue scrolls!";
                else if(def.clueType == ClueType.ELITE)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.ELITE_CLUES_COMPLETED.increment(player, 1)) + " elite clue scrolls!";
                else if(def.clueType == ClueType.MASTER)
                    message += " You have now completed a total of " + NumberUtils.formatNumber(PlayerCounter.MASTER_CLUES_COMPLETED.increment(player, 1)) + " master clue scrolls!";
                player.dialogue(new ItemDialogue().one(def.clueType.casketId, message));
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.COMPLETECLUE, def.clueType.toString().toLowerCase());
                player.sendMessage(message);
            }
            return true;
        }
        return false;
    }

}
