package io.ruin.model.skills.smithing;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;

public class SmithItem {

    protected static final SmithItem NONE = null;

    public final int level;

    public final double xp;

    public final int makeId, makeAmount;

    public final int barReq;

    public SmithItem(int level, double xp, int makeId, int makeAmount, int barReq) {
        this.level = level;
        this.xp = xp;
        this.makeId = makeId;
        this.makeAmount = makeAmount;
        this.barReq = barReq;
    }

    public void make(Player player, int amount) {
        player.closeInterface(InterfaceType.MAIN);
        player.closeInterface(InterfaceType.CHATBOX);
        if (!SmithBar.hasHammer(player)) {
            player.dialogue(new MessageDialogue("You need a hammer to work the metal with."));
            return;
        }
        if (!player.getStats().check(StatType.Smithing, level, "make that"))
            return;
        RandomEvent.attemptTrigger(player);
        player.startEvent(e -> {
            int made = 0;
            int prodCount = 0;
            while (true) {
                int barId = ((SmithBar) player.getTemporaryAttribute("SMITH_BAR")).itemId;
                ArrayList<Item> bars = player.getInventory().findItems(barId, barReq);
                if (bars == null) {
                    if (made == 0)
                        player.sendMessage("You don't have enough bars to make that.");
                    else
                        player.sendMessage("You don't have enough bars to make anymore.");
                    break;
                }
                if (made % 2 == 0)
                    player.animate(SmithBar.getHammerAnim(player));
                for (Item bar : bars)
                    bar.remove();
                player.getInventory().add(makeId, makeAmount);
                player.getStats().addXp(StatType.Smithing, xp, true);
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SMITH_ITEM, ItemDefinition.get(makeId).name, makeAmount);
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SMITH_BAR, ItemDefinition.get(makeId).name, barReq);
                if (++made >= amount)
                    break;
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    e.delay(2);
                }
                if (ProductionMaster.roll(player))
                    prodCount++;
            }
            ProductionMaster.extra(player, prodCount * makeAmount, makeId, StatType.Fletching, xp * prodCount);
        });
    }

    @Override
    public String toString() {
        return "SmithItem{" +
                "makeId=" + makeId +
                ", makeAmount=" + makeAmount +
                '}';
    }
}
