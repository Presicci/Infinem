package io.ruin.model.content.tasksystem.relics.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/29/2024
 */
public class ProductionMaster {

    public static boolean roll(Player player) {
        return player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER) && Random.rollDie(4);
    }

    public static void extra(Player player, int amt, int itemId, StatType stat, double experience) {
        extra(player, amt, itemId, stat, experience, null);
    }

    public static void extra(Player player, int amt, int itemId, StatType stat, double experience, TaskCategory category) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) return;
        if (amt <= 0) return;
        player.getStats().addXp(stat, experience, true);
        player.getBank().add(itemId, amt);
        player.sendMessage("<col=09950f>You make an extra " + amt + " x " + ItemDefinition.get(itemId).name + ", which are put into your bank.");
        if (category != null) player.getTaskManager().doLookupByCategoryAndTrigger(category, ItemDefinition.get(itemId).name, amt);
    }
}
