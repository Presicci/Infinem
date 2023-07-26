package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class ThievableChests {

    public enum Chests {
        COINS_10(11737, 13, 7.8, 7, false,
                new LootTable().addTable(1,
                        new LootItem(995, 3000, 3250, 0)
                ),
                new Position[]{new Position(2671, 3299, 1)}),
        COINS_101(11735, 13, 7.8, 7, false,
                new LootTable().addTable(1,
                        new LootItem(995, 3000, 3250, 0)
                ),
                new Position[]{new Position(2612, 3314, 1), new Position(2630, 3655), new Position(2673, 3307)}),

        NATURE(11736, 28, 25.0, 8, false,
                new LootTable().addTable(1,
                        new LootItem(995, 1000, 1250, 0),
                        new LootItem(561, 3, 8, 0)
                ),
                new Position[]{new Position(2671, 3301, 1), new Position(2614, 3314, 1)}),

        ISLE_OF_SOULS(40739, 28, 150.0, 0, 40740, true,
                new LootTable().addTable(1,
                        new LootItem(Items.COINS, 90, 190, 11),
                        new LootItem(Items.FEATHER, 30, 80, 11),
                        new LootItem(Items.GRIMY_GUAM_LEAF, 1, 2, 11),
                        new LootItem(Items.NATURE_RUNE, 1, 2, 11),
                        new LootItem(Items.DEATH_RUNE, 1, 2, 11),
                        new LootItem(Items.MIND_RUNE, 1, 10, 11),
                        new LootItem(Items.GRIMY_LANTADYME, 1, 10),
                        new LootItem(Items.GRIMY_RANARR_WEED, 1, 9),
                        new LootItem(Items.UNCUT_SAPPHIRE, 1, 2, 8),
                        new LootItem(Items.UNCUT_EMERALD, 1, 2, 8),
                        new LootItem(Items.CHOCOLATE_BAR, 1, 7),
                        new LootItem(Items.BIRD_SNARE, 1, 2, 7),
                        new LootItem(Items.BOX_TRAP, 1, 2, 7),
                        new LootItem(Items.UNCUT_RUBY, 1, 2, 7),
                        new LootItem(Items.MITHRIL_PICKAXE, 1, 7),
                        new LootItem(Items.MITHRIL_AXE, 1, 7),
                        new LootItem(Items.UNCUT_DIAMOND, 1, 2, 4),
                        new LootItem(Items.ADAMANT_SCIMITAR, 1, 4),
                        new LootItem(24363, 1, 2),  // Medium clue
                        new LootItem(25244, 1, 1)   // Dark key
                ),
                new Position[]{ new Position(2139, 9299) }),

        COINS_50(11735, 43, 125.0, 50, false,
                new LootTable().addTable(1,
                        new LootItem(995, 5000, 6500, 0)
                ),
                new Position[]{new Position(3188, 3962), new Position(3189, 3962), new Position(3193, 3962), new Position(3044, 3951)}),
        COINS_501(11736, 43, 125.0, 50, false,
                new LootTable().addTable(1,
                        new LootItem(995, 5000, 6500, 0)
                ),
                new Position[]{new Position(3042, 3949)}),
        COINS_502(11737, 43, 125.0, 50, false,
                new LootTable().addTable(1,
                        new LootItem(995, 5000, 6500, 0)
                ),
                new Position[]{new Position(3040, 3949)}),

        ARROWTIP(11742, 47, 150.0, 210, false,
                new LootTable().addTable(1,
                        new LootItem(41, 25, 30, 0),
                        new LootItem(995, 1000, 1250, 0)
                ),
                new Position[]{new Position(2650, 3659), new Position(2639, 3424)}),

        DORGESH_KAAN(22697, 52, 200.0, 210, false,
                new LootTable().addTable(1,
                        new LootItem(995, 1500, 1750, 1),
                        new LootItem(4548, 1, 1),
                        new LootItem(4537, 1, 1),
                        new LootItem(10981, 1, 1),
                        new LootItem(5013, 1, 1),
                        new LootItem(10192, 1, 1)
                ),
                new Position[]{}),

        BLOOD(11738, 59, 250.0, 135, false,
                new LootTable().addTable(1,
                        new LootItem(995, 1750, 2000, 0),
                        new LootItem(565, 15, 25, 0)
                ),
                new Position[]{new Position(2586, 9737), new Position(2586, 9734)}),

        STONE_CHEST(34429, 64, 280, 0, 34430, true,
                new LootTable().addTable(15, LootTable.CommonTables.UNCOMMON_SEED.items)
                        .addTable(270,
                                new LootItem(995, 20, 260, 99), // Coins
                                new LootItem(13391, 1, 90), // Lizardman fang
                                new LootItem(13383, 1, 60), // Xerician fabric
                                new LootItem(1623, 1, 12),  // Uncut sapphire
                                new LootItem(1619, 1, 9),   // Uncut ruby
                                new LootItem(2801, 1, 3),   // Clue scroll (medium)
                                new LootItem(13392, 1, 1)   // Xeric's talisman
                        ).addTable(15, // Bolt tip table
                        new LootItem(45, 4, 12, 4), // Opal
                        new LootItem(46, 4, 12, 4), // Pearl
                        new LootItem(9192, 4, 12, 4),   // Diamond
                        new LootItem(9191, 4, 12, 3),   // Ruby
                        new LootItem(9193, 4, 12, 3),   // Dragonstone
                        new LootItem(9190, 4, 12, 2),   // Emerald
                        new LootItem(9187, 4, 12, 1),   // Jade
                        new LootItem(9188, 4, 12, 1),   // Topaz
                        new LootItem(9189, 4, 12, 1),   // Sapphire
                        new LootItem(9194, 4, 12, 1)   // Onyx
                ),
                new Position[]{new Position(1302, 10087), new Position(1300, 10085), new Position(1300, 10089)}),

        PALADIN(11739, 72, 500.0, 400, false,
                new LootTable().addTable(1,
                        new LootItem(995, 2500, 3500, 0),
                        new LootItem(384, 1, 2, 0),
                        new LootItem(450, 1, 2, 0),
                        new LootItem(1623, 1, 2, 0)
                ),
                new Position[]{new Position(2588, 3302, 1), new Position(2588, 3291, 1)}),

        DORG_RICH(22681, 78, 650.0, 300, false,
                new LootTable().addTable(1,
                        new LootItem(995, 500, 2500, 1),
                        new LootItem(1623, 1, 1),
                        new LootItem(1621, 1, 1),
                        new LootItem(1619, 1, 1),
                        new LootItem(1617, 1, 1),
                        new LootItem(1625, 1, 1),
                        new LootItem(1627, 1, 1),
                        new LootItem(1629, 1, 1),
                        new LootItem(4548, 1, 1),
                        new LootItem(5013, 1, 1),
                        new LootItem(10954, 1, 1),
                        new LootItem(10956, 1, 1),
                        new LootItem(10958, 1, 1),
                        new LootItem(2351, 1, 1),
                        new LootItem(10973, 1, 1),
                        new LootItem(10980, 1, 1)
                ),
                new Position[]{}),

        ROGUES_CASTLE(26757, 84, 100, 10, false,
                new LootTable().addTable(1,
                        new LootItem(1622, 7, 12, 5),          // Uncut emerald
                        new LootItem(1624, 9, 15, 5),          // Uncut sapphire
                        new LootItem(360, 25, 35, 5),          // Raw tuna
                        new LootItem(995, 10000, 15000, 5),    // Coins
                        new LootItem(593, 35, 45, 5),          // Ashes
                        new LootItem(454, 20, 30, 5),          // Coal
                        new LootItem(558, 30, 50, 5),          // Mind rune
                        new LootItem(1602, 4, 6, 3),           // Diamond
                        new LootItem(562, 35, 50, 3),          // Chaos rune
                        new LootItem(560, 25, 35, 3),          // Death rune
                        new LootItem(554, 25, 40, 3),          // Fire rune
                        new LootItem(352, 10, 15, 3),          // Pike
                        new LootItem(591, 4, 5, 3),            // Tinderbox
                        new LootItem(441, 15, 25, 3),          // Iron ore
                        new LootItem(386, 15, 20, 1),           // Shark
                        new LootItem(1616, 2, 2, 1),            // Dragonstone
                        new LootItem(2722, 1, 1, 1)             // Clue scroll (hard)
                ),
                new Position[]{new Position(3297, 3940), new Position(3287, 3946), new Position(3283, 3946)});   // TODO add one to home

        public final int objectId;
        public final int level;
        public final double xp;
        public final int respawnTime;
        public final boolean failable;
        public final LootTable lootTable;
        public final Position[] positions;

        public int replacementId;

        Chests(int objectId, int level, double xp, int respawnTime, int replacementId, boolean failable, LootTable lootTable, Position[] positions) {
            this.objectId = objectId;
            this.level = level;
            this.xp = xp;
            this.respawnTime = respawnTime;
            this.replacementId = replacementId;
            this.failable = failable;
            this.lootTable = lootTable;
            this.positions = positions;
        }

        Chests(int objectId, int level, double xp, int respawnTime, boolean failable, LootTable lootTable, Position[] positions) {
            this(objectId, level, xp, respawnTime, 26758, failable, lootTable, positions);
        }
    }

    private static void replaceChest(GameObject chest, int replacementId, int respawnTime) {
        World.startEvent(event -> {
            chest.setId(replacementId);
            event.delay(respawnTime);
            chest.setId(chest.originalId);
        });
    }

    private static void rougesAttack(Player player) {
        for (NPC npc : player.localNpcs()) {
            if (npc.getId() != 6603)
                continue;
            if (npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                npc.face(player);
                npc.getCombat().setTarget(player);
                npc.forceText("Someone's stealing from us, get them!");
            }
        }
    }

    private static void open(Player player) {
        player.startEvent(e -> {
            player.lock(LockType.FULL_REGULAR_DAMAGE);
            player.sendMessage("You begin to open the chest...");
            player.animate(537);
            e.delay(5);
            player.sendMessage("You trigger a trap!");
            e.delay(1);
            player.hit(new Hit().randDamage(2, 3));
            player.unlock();
        });
    }

    private static boolean isSuccessful(Player player, Chests chest) {
        if (!chest.failable)
            return true;
        int level = player.getStats().get(StatType.Thieving).currentLevel;
        int reqLevel = chest.level;
        double chance = 0.3;
        int levelDiff = level - reqLevel;
        double levels = 99 - reqLevel;
        double step = 1/levels;
        chance *= 1 + (levelDiff * step);
        if (player.getInventory().contains(Items.LOCKPICK))
            chance += 0.1;
        if (player.debug)
            player.sendFilteredMessage("Chance: " + chance);
        return Random.get() <= Math.min(0.80, chance);
    }

    private static void disarm(Player player, Chests chest, GameObject object) {
        if (!player.getStats().check(StatType.Thieving, chest.level)) {
            player.sendMessage("You need a thieving level of " + chest.level + "to disarm this trap.");
            return;
        }
        if (!isSuccessful(player, chest)) {
            open(player);
            return;
        }
        player.startEvent(e -> {
            player.lock(LockType.FULL_REGULAR_DAMAGE);
            player.sendMessage("You begin to open the chest...");
            player.animate(537);
            e.delay(5);
            if (Random.rollDie(100, 85)) {
                player.animate(536);
                player.sendMessage("You successfully disarm the trap.");
                e.delay(1);
                Item item = chest.lootTable.rollItem();
                player.getInventory().addOrDrop(item);
                player.getStats().addXp(StatType.Thieving, chest.xp, true);
                player.sendMessage("You steal some loot from the chest.");
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.THIEVECHEST, item.getDef().name, item.getAmount(), true);
                if (chest == Chests.ROGUES_CASTLE && Random.rollDie(50)) {
                    rougesAttack(player);
                }
                if (chest != Chests.DORG_RICH && chest != Chests.DORGESH_KAAN) {
                    replaceChest(object, chest.replacementId, chest.respawnTime);
                }
            } else {
                player.sendMessage("You trigger a trap!");
                e.delay(1);
                player.hit(new Hit().randDamage(2, 3));
                if (Random.rollDie(100, 15)) {
                    player.poison(2);
                }
            }
            player.unlock();
        });
    }

    static {
        for (Chests chest : Chests.values()) {
            if (chest == Chests.DORGESH_KAAN || chest == Chests.DORG_RICH) {    // So many spawns, just blanket cover all of them
                ObjectAction.register(chest.objectId, "open", (player, object) -> open(player));
                ObjectAction.register(chest.objectId, "pick-lock", (player, object) -> disarm(player, chest, object));
                continue;
            }
            for (Position pos : chest.positions) {
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "open", (player, object) -> open(player));
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "search for traps", (player, object) -> disarm(player, chest, object));
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "picklock", (player, object) -> disarm(player, chest, object));
            }
        }
    }
}
