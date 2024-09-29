package io.ruin.model.skills.cooking;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public class JugOfWine {

    private static final int GRAPES = 1987;
    private static final int JUG_OF_WINE = 1993;
    private static final int JUG_OF_WATER = 1937;

    private static void makeWine(Player player, Item primary, Item secondary) {
        player.startEvent(event -> {
            if (player.getStats().get(StatType.Cooking).fixedLevel < 35) {
                player.sendMessage("You do not have the required level to make wine.");
                return;
            }
            int amount = Math.min(primary.count(), secondary.count());
            int prodCount = 0;
            boolean prodMaster = player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER);
            while(amount --> 0) {
                player.getInventory().remove(primary.getId(), 1);
                player.getInventory().remove(secondary.getId(), 1);
                player.getInventory().add(JUG_OF_WINE, 1);
                player.getStats().addXp(StatType.Cooking, 200.0, true);
                PlayerCounter.JUGS_OF_WINE_MADE.increment(player, 1);
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.COOKITEM, ItemDefinition.get(JUG_OF_WINE).name);
                if (ProductionMaster.roll(player))
                    prodCount++;
                if (!prodMaster) {
                    player.sendMessage("You squeeze the grapes into the jug. The wine begins to ferment.");
                    event.delay(2);
                }
            }
            ProductionMaster.extra(player, prodCount, JUG_OF_WINE, StatType.Cooking, 200 * prodCount, TaskCategory.COOKITEM);
        });
    }

    static {
        ItemItemAction.register(JUG_OF_WATER, GRAPES, JugOfWine::makeWine);
    }
}
