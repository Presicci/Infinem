package io.ruin.model.skills.fletching;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.FEATHER;

public enum Dart {

    BRONZE_DART(806, 819, 1, 1.8, 8482),
    IRON_DART(807, 820, 22, 3.8, 8483),
    STEEL_DART(808, 821, 37, 7.5, 8484),
    MITHRIL_DART(809, 822, 52, 11.2, 8485),
    ADAMANT_DART(810, 823, 67, 15.0, 8486),
    RUNE_DART(811, 824, 81, 18.8, 8487),
    AMETHYST_DART(25849, 25853, 89, 21.0, 8487),    // Temp animation
    DRAGON_DART(11230, 11232, 95, 25.0, 8488),

    BRONZE_BOLT(877, 9375, 9, 0.5, 8463),
    IRON_BOLT(9140, 9377, 39, 1.5, 8464),
    SILVER_BOLT(9145, 9382, 43, 2.5, 8467),
    STEEL_BOLT(9141, 9378, 46, 3.5, 8467),
    BROAD_BOLT(11875, 11876, 55, 3, 8464),
    MITHRIL_BOLT(9142, 9379, 54, 5.0, 8468),
    ADAMANT_BOLT(9143, 9380, 61, 7.0, 8469),
    RUNE_BOLT(9144, 9381, 69, 10.0, 8470),
    DRAGON_BOLTS(21905, 21930, 84, 12.0, 8471),
    ;

    public final int lvlReq;

    public final double xp;

    public final int unfinishedId, finishedId;

    public final String pluralName;

    public final int emote;

    Dart(int dartId, int tipId, int lvlReq, double xp, int emote) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.unfinishedId = tipId;
        this.finishedId = dartId;
        this.pluralName = ItemDefinition.get(dartId).name.toLowerCase() + (ItemDefinition.get(dartId).name.endsWith("s") ? "" : "s");
        this.emote = emote;
    }

    private void make(Player player, Item tipItem, Item featherItem) {
        if (!player.getStats().check(StatType.Fletching, lvlReq, finishedId, "make " + pluralName))
            return;
        if (this == BROAD_BOLT && Config.BROADER_FLETCHING.get(player) == 0) {
            player.sendMessage("You haven't unlocked the ability to fletch broad bolts yet.");
            return;
        }
        RandomEvent.attemptTrigger(player);
        int supplyCount = Math.min(tipItem.getAmount(), featherItem.getAmount());
        int maxPossible = player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER) ? 100 : 10;
        int toMake = Math.min(supplyCount, maxPossible);
        tipItem.remove(toMake);
        featherItem.remove(toMake);
        player.animate(emote);
        player.getInventory().add(finishedId, toMake);
        player.getStats().addXp(StatType.Fletching, xp * toMake, true);
        player.sendFilteredMessage("You make " + toMake + " " + pluralName + ".");
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.FLETCH_AMMO, ItemDefinition.get(finishedId).name, toMake);
        if (ProductionMaster.roll(player))
            ProductionMaster.extra(player, toMake, finishedId, StatType.Fletching, xp * toMake, TaskCategory.FLETCH_AMMO);
    }

    static {
        for (Dart dart : values())
            ItemItemAction.register(dart.unfinishedId, FEATHER, dart::make);
    }

}