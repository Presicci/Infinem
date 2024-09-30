package io.ruin.model.skills.fletching;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.Event;

public enum LogCutting {
    //Normal log
    ARROW_SHAFT(1511, 1, 5.0, new Item(52, 15), "15 arrow shafts", ""),
    JAVELIN_SHAFT(1511, 3, 5.0, new Item(19584, 15), "15 javelin shafts", "fletch Javelin shafts"),
    SHORTBOW(1511, 5, 5.0, new Item(50, 1), "a shortbow", "make a Shortbow"),
    LONGBOW(1511, 10, 10.0, new Item(48, 1), "a longbow", "make a Longbow"),
    WOODEN_STOCK(1511, 9, 6.0, new Item(9440, 1), "a crossbow stock", "make a Wooden stock"),

    //Oak log
    OAK_SHAFT(1521, 15, 10.0, new Item(52, 30), "30 arrow shafts", "make shafts from oak logs"),
    OAK_SHORTBOW(1521, 20, 16.5, new Item(54, 1), "a shorbow", "make an Oak Shortbow"),
    OAK_LONGBOW(1521, 25, 16.5, new Item(56, 1), "a longbow", "make an Oak Longbow"),
    OAK_STOCK(1521, 24, 16.0, new Item(9442, 1), "a crossbow stock", "make an Oak Stock"),
    OAK_SHIELD(1521, 27, 50, new Item(22251, 1), "a shield", "make a Oak shield"),

    //Willow log
    WILLOW_SHAFT(1519, 30, 15.0, new Item(52, 45), "45 arrow shafts", "make shafts from willow logs"),
    WILLOW_SHORTBOW(1519, 35, 33.3, new Item(60, 1), "a shortbow", "make a Willow Shortbow"),
    WILLOW_LONGBOW(1519, 40, 41.5, new Item(58, 1), "a longbow", "make a Willow Longbow"),
    WILLOW_STOCK(1519, 39, 22.0, new Item(9444, 1), "a crossbow stock", "make a Willow Stock"),
    WILLOW_SHIELD(1519, 42, 83, new Item(22254, 1), "a shield", "make a Willow shield"),

    //Maple log
    MAPLE_SHAFT(1517, 45, 20.0, new Item(52, 60), "60 arrow shafts", "make shafts from maple logs"),
    MAPLE_SHORTBOW(1517, 50, 50.0, new Item(64, 1), "a shortbow", "make a Maple Shortbow"),
    MAPLE_LONGBOW(1517, 55, 55.0, new Item(62, 1), "a longbow", "make a Maple Longbow"),
    MAPLE_STOCK(1517, 54, 32.0, new Item(9448, 1), "a crossbow stock", "make a Maple Stock"),
    MAPLE_SHIELD(1517, 57, 116.5, new Item(22257, 1), "a shield", "make a Maple shield"),

    //Yew log
    YEW_SHAFT(1515, 60, 25.0, new Item(52, 75), "75 arrow shafts", "make shafts from yew logs"),
    YEW_SHORTBOW(1515, 65, 67.5, new Item(68, 1), "a shortbow", "make a Yew Shortbow"),
    YEW_LONGBOW(1515, 70, 75.0, new Item(66, 1), "a longbow", "make a Yew Longbow"),
    YEW_STOCK(1515, 69, 32.0, new Item(9452, 1), "a crossbow stock", "make a Yew Stock"),
    YEW_SHIELD(1515, 72, 150, new Item(22260, 1), "a shield", "make a Yew shield"),

    //Magic log
    MAGIC_SHAFT(1513, 75, 30.0, new Item(52, 90), "90 arrow shafts", "make shafts from magic logs"),
    MAGIC_SHORTBOW(1513, 80, 83.3, new Item(72, 1), "a shortbow", "make a Magic Shortbow"),
    MAGIC_LONGBOW(1513, 85, 91.5, new Item(70, 1), "a longbow", "make a Magic Longbow"),
    MAGIC_STOCK(1513, 78, 70.0, new Item(21952, 1), "a crossbow stock", "make a Magic Stock"),
    MAGIC_SHIELD(1513, 87, 183, new Item(22263, 1), "a shield", "make a Magic shield"),

    //Redwood log
    REDWOOD_SHAFT(19669, 90, 35.0, new Item(52, 105), "105 arrow shafts", "make shafts from redwood logs"),
    REDWOOD_SHIELD(19669, 92, 216.0, new Item(22266, 1), "a shield", "make a redwood shield"),

    BATTLESTAFF(22935, 40, 80, new Item(1391, 1), "a battlestaff", "make a battlestaff"),
    ;


    public final int logID, levelReq;
    public final double exp;
    public final Item item;
    public final String name, descriptionName;

    LogCutting(int logID, int levelReq, double exp, Item item, String name, String descriptionName) {
        this.logID = logID;
        this.levelReq = levelReq;
        this.exp = exp;
        this.item = item;
        this.name = name;
        this.descriptionName = descriptionName;
    }

    private static void cut(Player player, LogCutting log, int amount) {
        if (!player.getStats().check(StatType.Fletching, log.levelReq, log.descriptionName))
            return;
        if (log.descriptionName.contains("shield") && !player.getInventory().contains(log.logID, 2)) {
            player.sendMessage("You need 2 logs to make a shield.");
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(event -> {
            int made = 0;
            int prodCount = 0;
            while (made++ < amount) {
                Item knife = player.getInventory().findItem(Tool.KNIFE);
                if (knife == null) {
                    break;
                }
                Item logToCut = player.getInventory().findItem(log.logID);
                if (logToCut == null) {
                    break;
                }
                if (log.descriptionName.contains("shield") && !player.getInventory().contains(log.logID, 2)) {
                    break;
                }
                int logAmt = log.descriptionName.contains("shield") ? 2 : 1;
                logToCut.remove(logAmt);
                player.getInventory().add(log.item);
                player.getStats().addXp(StatType.Fletching, log.exp, true);
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.FLETCHLOG, log.item.getDef().name);
                player.getTaskManager().doLookupByCategory(TaskCategory.FLETCHED_LOGS, logAmt, true);
                player.animate(1248);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    player.sendFilteredMessage("You carefully cut the wood into " + log.name + ".");
                    event.delay(2);
                }
                if (ProductionMaster.roll(player))
                    prodCount++;
            }
            ProductionMaster.extra(player, prodCount * log.item.getAmount(), log.item.getId(), StatType.Fletching, log.exp * prodCount);
        });
    }

    private static final int NORMAL_LOG = 1511;
    private static final int OAK_LOG = 1521;
    private static final int WILLOW_LOG = 1519;
    private static final int MAPLE_LOG = 1517;
    private static final int YEW_LOG = 1515;
    private static final int MAGIC_LOG = 1513;
    private static final int CELASTRUS_BARK = 22935;

    static {
        /*
         * Normal log
         */
        ItemItemAction.register(Tool.KNIFE, NORMAL_LOG, (player, knife, normalLog) -> SkillDialogue.make(player,
                new SkillItem(ARROW_SHAFT.item.getId()).addAction((p, amount, event) -> cut(p, ARROW_SHAFT, amount)),
                new SkillItem(JAVELIN_SHAFT.item.getId()).addAction((p, amount, event) -> cut(p, JAVELIN_SHAFT, amount)),
                new SkillItem(SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, SHORTBOW, amount)),
                new SkillItem(LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, LONGBOW, amount)),
                new SkillItem(WOODEN_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, WOODEN_STOCK, amount))));

        /*
         * Oak log
         */
        ItemItemAction.register(Tool.KNIFE, OAK_LOG, (player, knife, oakLog) -> SkillDialogue.make(player,
                item(OAK_SHAFT),
                new SkillItem(OAK_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, OAK_SHORTBOW, amount)),
                new SkillItem(OAK_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, OAK_LONGBOW, amount)),
                new SkillItem(OAK_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, OAK_STOCK, amount)),
                item(OAK_SHIELD)));

        /*
         * Willow log
         */
        ItemItemAction.register(Tool.KNIFE, WILLOW_LOG, (player, knife, willowLog) -> SkillDialogue.make(player,
                item(WILLOW_SHAFT),
                new SkillItem(WILLOW_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_SHORTBOW, amount)),
                new SkillItem(WILLOW_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_LONGBOW, amount)),
                new SkillItem(WILLOW_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, WILLOW_STOCK, amount)),
                item(WILLOW_SHIELD)));

        /*
         * Maple log
         */
        ItemItemAction.register(Tool.KNIFE, MAPLE_LOG, (player, knife, mapleLog) -> SkillDialogue.make(player,
                item(MAPLE_SHAFT),
                new SkillItem(MAPLE_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_SHORTBOW, amount)),
                new SkillItem(MAPLE_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_LONGBOW, amount)),
                new SkillItem(MAPLE_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, MAPLE_STOCK, amount)),
                item(MAPLE_SHIELD)));

        /*
         * Yew log
         */
        ItemItemAction.register(Tool.KNIFE, YEW_LOG, (player, knife, yewLog) -> SkillDialogue.make(player,
                item(YEW_SHAFT),
                new SkillItem(YEW_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, YEW_SHORTBOW, amount)),
                new SkillItem(YEW_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, YEW_LONGBOW, amount)),
                new SkillItem(YEW_STOCK.item.getId()).addAction((p, amount, event) -> cut(p, YEW_STOCK, amount)),
                item(YEW_SHIELD)));

        /*
         * Magic log
         */
        ItemItemAction.register(Tool.KNIFE, MAGIC_LOG, (player, knife, magicLog) -> SkillDialogue.make(player,
                item(MAGIC_SHAFT),
                new SkillItem(MAGIC_SHORTBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAGIC_SHORTBOW, amount)),
                new SkillItem(MAGIC_LONGBOW.item.getId()).addAction((p, amount, event) -> cut(p, MAGIC_LONGBOW, amount)),
                item(MAGIC_STOCK),
                item(MAGIC_SHIELD)));

        /*
         * Redwood log
         */
        ItemItemAction.register(Tool.KNIFE, 19669, (player, knife, magicLog) -> SkillDialogue.make(player,
                item(REDWOOD_SHAFT),
                item(REDWOOD_SHIELD)));

        /*
         * Celastrus bark
         */
        ItemItemAction.register(Tool.KNIFE, CELASTRUS_BARK, (player, knife, bark) ->
                SkillDialogue.make(player, item(BATTLESTAFF)));

        ItemAction.registerInventory(CELASTRUS_BARK, "fletch", (player, id) -> {
            if (!player.getInventory().hasId(Tool.KNIFE)) {
                player.sendMessage("You need a knife to fletch the bark.");
                return;
            }
            SkillDialogue.make(player, item(BATTLESTAFF));
        });

    }

    private static SkillItem item(LogCutting lc) {
        return new SkillItem(lc.item.getId()).addAction(action(lc));
    }

    private static SkillItem.SkillItemConsumer<Player, Integer, Event> action(LogCutting log) {
        return (player, amount, event) -> cut(player, log, amount);
    }
}