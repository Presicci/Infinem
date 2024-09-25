package io.ruin.model.activities.shadesofmortton;

import io.ruin.model.World;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/21/2024
 */
@Getter
@AllArgsConstructor
public enum FuneralPyre {
    LOAR(Items.LOAR_REMAINS, 33, new LootTable().addTable(1,
            new LootItem(Items.BRONZE_KEY_RED, 1, 33),
            new LootItem(Items.BRONZE_KEY_BROWN, 1, 28),
            new LootItem(995, 200, 300, 21),
            new LootItem(Items.BRONZE_KEY_CRIMSON, 1, 18)
    )),
    PHRIN(Items.PHRIN_REMAINS, 45, new LootTable().addTable(1,
            new LootItem(Items.STEEL_KEY_RED, 1, 27),
            new LootItem(Items.STEEL_KEY_BROWN, 1, 23),
            new LootItem(995, 400, 500, 21),
            new LootItem(Items.STEEL_KEY_CRIMSON, 1, 16),
            new LootItem(Items.BRONZE_KEY_BLACK, 1, 8),
            new LootItem(Items.BRONZE_KEY_PURPLE, 1, 5)
    )),
    RIYL(Items.RIYL_REMAINS, 61, new LootTable().addTable(1,
            new LootItem(Items.BLACK_KEY_RED, 1, 27),
            new LootItem(Items.BLACK_KEY_BROWN, 1, 23),
            new LootItem(995, 600, 700, 21),
            new LootItem(Items.BLACK_KEY_CRIMSON, 1, 16),
            new LootItem(Items.STEEL_KEY_BLACK, 1, 8),
            new LootItem(Items.STEEL_KEY_PURPLE, 1, 5)
    )),
    ASYN(Items.ASYN_REMAINS, 80, new LootTable().addTable(1,
            new LootItem(Items.SILVER_KEY_RED, 1, 27),
            new LootItem(Items.SILVER_KEY_BROWN, 1, 23),
            new LootItem(995, 800, 900, 21),
            new LootItem(Items.BLACK_KEY_CRIMSON, 1, 16),
            new LootItem(Items.BLACK_KEY_BLACK, 1, 8),
            new LootItem(Items.BLACK_KEY_PURPLE, 1, 5)
    )),
    FIYR(Items.FIYR_REMAINS, 100, new LootTable().addTable(1,
            new LootItem(Items.SILVER_KEY_CRIMSON, 1, 35),
            new LootItem(995, 2000, 4000, 21),
            new LootItem(25424, 1, 16),
            new LootItem(Items.SILVER_KEY_BROWN, 1, 16),
            new LootItem(Items.SILVER_KEY_BLACK, 1, 8),
            new LootItem(Items.SILVER_KEY_PURPLE, 1, 4)
    )),
    URIUM(25419, 180.5, new LootTable().addTable(1,
            new LootItem(25428, 1, 35),
            new LootItem(995, 2000, 4000, 21),
            new LootItem(25424, 1, 16),
            new LootItem(25426, 1, 16),
            new LootItem(25430, 1, 8),
            new LootItem(25432, 1, 4)
    ));

    private final int remains;
    private final double prayerExperience;
    private final LootTable lootTable;

    private void spawnLoot(Player player, GameObject object) {
        Position pos = object.getPosition();
        switch (object.direction) {
            default:
                pos = pos.relative(0, 3);
                break;
            case 1:
                pos = pos.relative(3, 0);
                break;
            case 2:
                pos = pos.relative(0, -1);
                break;
            case 3:
                pos = pos.relative(-1, 0);
                break;
        }
        Item item = lootTable.rollItem();
        World.sendGraphics(86, 150, 0, pos);
        new GroundItem(item).position(pos).owner(player).addToCL().spawn();
    }

    private static void light(Player player, GameObject object) {
        Player owner = (Player) object.attributes.getOrDefault("OWNER", null);
        if (owner != null && owner != player) {
            player.sendMessage("This isn't your pyre.");
            return;
        }
        if (!player.getInventory().hasId(Tool.TINDER_BOX)) {
            player.sendMessage("You need a tinderbox to light the pyre.");
            return;
        }
        FuneralPyre pyre = (FuneralPyre) object.attributes.getOrDefault("PYRE_CORPSE", LOAR);
        PyreLog log = (PyreLog) object.attributes.getOrDefault("PYRE_LOG", PyreLog.REGULAR);
        player.lock();
        player.startEvent(e -> {
            player.animate(733);
            e.delay(3);
            player.getStats().addXp(StatType.Firemaking, log.getCremationExperience() * (AreaReward.SHADE_FIREMAKING_EXP.hasReward(player) ? 1.5 : 1), true);
            player.getStats().addXp(StatType.Prayer, pyre.prayerExperience * (AreaReward.SHADE_PRAYER_EXP.hasReward(player) ? 1.5 : 1), true);
            PlayerCounter.PYRES_LIT.increment(player, 1);
            pyre.spawnLoot(player, object);
            resetPyre(object);
            player.getTaskManager().doLookupByCategory(TaskCategory.PYRE_LOG, 1, true);
            player.resetAnimation();
            player.unlock();
        });
    }

    private static void addCorpse(Player player, Item item, GameObject object, FuneralPyre pyre) {
        Player owner = (Player) object.attributes.getOrDefault("OWNER", null);
        if (owner != null && owner != player) {
            player.sendMessage("This isn't your pyre.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(832);
            e.delay(1);
            item.remove();
            player.sendFilteredMessage("You add the remains to the pyre.");
            object.setId(4101);
            object.attributes.put("PYRE_CORPSE", pyre);
            World.startEvent(we -> {
                int ticks = 0;
                while (ticks < 31) {
                    we.delay(1);
                    if (object.id != 4101) break;
                    if (ticks++ == 30) {
                        resetPyre(object);
                    }
                }
            });
            player.unlock();
        });
    }

    private static void addLog(Player player, Item item, GameObject object, PyreLog log) {
        player.lock();
        player.startEvent(e -> {
            player.animate(832);
            e.delay(1);
            item.remove();
            player.sendFilteredMessage("You add the logs to the pyre.");
            object.setId(4094);
            object.attributes.put("PYRE_LOG", log);
            object.attributes.put("OWNER", player);
            World.startEvent(we -> {
                int ticks = 0;
                while (ticks < 31) {
                    we.delay(1);
                    if (object.id != 4094) break;
                    if (ticks++ == 30) resetPyre(object);
                }
            });
            player.unlock();
        });
    }

    private static void resetPyre(GameObject object) {
        object.setId(4093);
        object.attributes.remove("PYRE_LOG");
        object.attributes.remove("PYRE_CORPSE");
        object.attributes.remove("OWNER");
    }

    public static final Set<Integer> REMAINS = new LinkedHashSet<>();

    static {
        for (PyreLog log : PyreLog.values()) {
            ItemObjectAction.register(log.getPyreLogId(), 4093, (player, item, obj) -> addLog(player, item, obj, log));
        }
        for (FuneralPyre pyre : values()) {
            ItemObjectAction.register(pyre.remains, 4094, (player, item, obj) -> addCorpse(player, item, obj, pyre));
            REMAINS.add(pyre.remains);
        }
        ObjectAction.register(4101, "light", FuneralPyre::light);
    }
}
