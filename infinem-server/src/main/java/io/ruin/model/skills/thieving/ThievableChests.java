package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
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

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class ThievableChests {

    public enum Chest {
        COINS_10(11737, 13, 7.8, 7, false,
                new LootTable().addTable(1,
                        new LootItem(995, 10, 0)
                ),
                new Position[]{new Position(2671, 3299, 1)}),
        COINS_101(11735, 13, 7.8, 7, false,
                new LootTable().addTable(1,
                        new LootItem(995, 10, 0)
                ),
                new Position[]{new Position(2612, 3314, 1), new Position(2630, 3655), new Position(2673, 3307)}),

        NATURE(11736, 28, 25.0, 13, false,
                new LootTable().addTable(1,
                        new LootItem(995, 10, 0),
                        new LootItem(561, 2, 0)
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
                        new LootItem(995, 50, 0)
                ),
                new Position[]{new Position(3188, 3962), new Position(3189, 3962), new Position(3193, 3962), new Position(3044, 3951)}),
        COINS_501(11736, 43, 125.0, 50, false,
                new LootTable().addTable(1,
                        new LootItem(995, 50, 0)
                ),
                new Position[]{new Position(3042, 3949)}),
        COINS_502(11737, 43, 125.0, 50, false,
                new LootTable().addTable(1,
                        new LootItem(995, 50, 0)
                ),
                new Position[]{new Position(3040, 3949)}),

        ARROWTIP(11742, 47, 150.0, 60, false,
                new LootTable().addTable(1,
                        new LootItem(41, 5, 10, 0),
                        new LootItem(995, 20, 0)
                ),
                new Position[]{new Position(2650, 3659), new Position(2639, 3424)}),

        DORGESH_KAAN(22697, 52, 200.0, 100, 22699, false,
                new LootTable().addTable(1,
                        new LootItem(995, 1, 250, 10),
                        new LootItem(4548, 1, 10),
                        new LootItem(4537, 1, 10),
                        new LootItem(10981, 1, 10),
                        new LootItem(5013, 1, 10),
                        new LootItem(24362, 1, 1)
                ),
                new Position[]{}),

        BLOOD(11738, 59, 250.0, 100, false,
                new LootTable().addTable(1,
                        new LootItem(995, 500, 0),
                        new LootItem(565, 2, 3, 0)
                ),
                new Position[]{new Position(2586, 9737), new Position(2586, 9734)}),

        STONE_CHEST(34429, 64, 280, 0, 34430, true,
                new LootTable().addTable(15, LootTable.CommonTables.UNCOMMON_SEED.items)
                        .addTable(91661,  // Other
                                new LootItem(995, 20, 260, 33299), // Coins
                                new LootItem(13391, 1, 28300), // Lizardman fang
                                new LootItem(13383, 1, 22000), // Xerician fabric
                                new LootItem(1623, 1, 4000),  // Uncut sapphire
                                new LootItem(1619, 1, 2670),   // Uncut ruby
                                new LootItem(24363, 1, 1000),   // Clue scroll (medium)
                                new LootItem(13392, 1, 333)   // Xeric's talisman
                        ).addTable(3335,  // Bolt tip table
                                new LootItem(45, 4, 12, 541), // Opal
                                new LootItem(46, 4, 12, 541), // Pearl
                                new LootItem(9192, 4, 12, 541),   // Diamond
                                new LootItem(9191, 4, 12, 360),   // Ruby
                                new LootItem(9193, 4, 12, 360),   // Dragonstone
                                new LootItem(9190, 4, 12, 270),   // Emerald
                                new LootItem(9187, 4, 12, 180),   // Jade
                                new LootItem(9188, 4, 12, 180),   // Topaz
                                new LootItem(9189, 4, 12, 180),   // Sapphire
                                new LootItem(9194, 4, 12, 180)   // Onyx
                        ).addTable(5003,  // Seeds
                                LootTable.CommonTables.UNCOMMON_SEED.items
                        ),
                new Position[]{new Position(1302, 10087), new Position(1300, 10085), new Position(1300, 10089)}),

        ARDOUGNE_CASTLE(11739, 72, 500.0, 125, false,
                new LootTable().addTable(1,
                        new LootItem(995, 1000, 0),
                        new LootItem(383, 1, 2, 0),
                        new LootItem(449, 1, 2, 0),
                        new LootItem(1623, 1, 2, 0)
                ),
                new Position[]{new Position(2588, 3302, 1), new Position(2588, 3291, 1)}),

        DORG_RICH(22681, 78, 650.0, 200, 22683, false,
                new LootTable().addTable(1,
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
                        new LootItem(2351, 1, 1),
                        new LootItem(Items.CAVE_GOBLIN_WIRE, 1, 1),
                        new LootItem(10973, 1, 1),
                        new LootItem(10980, 1, 1)
                ),
                new Position[]{}),

        ROGUES_CASTLE(26757, 84, 100, 10, false,
                new LootTable().addTable(1,
                        new LootItem(Items.MIND_RUNE, 25, 70),
                        new LootItem(Items.RAW_TUNA_NOTE, 15, 35),
                        new LootItem(Items.COAL_NOTE, 13, 25),
                        new LootItem(Items.COINS, 1250, 2250, 20),
                        new LootItem(Items.ASHES_NOTE, 25, 10),
                        new LootItem(Items.DIAMOND_NOTE, 3, 10),
                        new LootItem(Items.UNCUT_EMERALD_NOTE, 5, 8),
                        new LootItem(Items.DIAMOND_NOTE, 2, 8),
                        new LootItem(Items.FIRE_RUNE, 30, 8),
                        new LootItem(Items.TINDERBOX_NOTE, 3, 8),
                        new LootItem(Items.UNCUT_SAPPHIRE_NOTE, 6, 4),
                        new LootItem(Items.CHAOS_RUNE, 40, 4),
                        new LootItem(Items.DEATH_RUNE, 30, 4),
                        new LootItem(Items.PIKE_NOTE, 20, 4),
                        new LootItem(Items.IRON_ORE_NOTE, 10, 4),
                        new LootItem(Items.SHARK_NOTE, 10, 4),
                        new LootItem(Items.DRAGONSTONE_NOTE, 2, 4),
                        new LootItem(24364, 1, 2)   // Hard clue
                ),
                new Position[]{new Position(3297, 3940), new Position(3287, 3946), new Position(3283, 3946)}
        );

        public final int objectId;
        public final int level;
        public final double xp;
        public final int respawnTime;
        public final boolean failable;
        public final LootTable lootTable;
        public final Position[] positions;

        public final int replacementId;

        Chest(int objectId, int level, double xp, int respawnTime, int replacementId, boolean failable, LootTable lootTable, Position[] positions) {
            this.objectId = objectId;
            this.level = level;
            this.xp = xp;
            this.respawnTime = respawnTime;
            this.replacementId = replacementId;
            this.failable = failable;
            this.lootTable = lootTable;
            this.positions = positions;
        }

        Chest(int objectId, int level, double xp, int respawnTime, boolean failable, LootTable lootTable, Position[] positions) {
            this(objectId, level, xp, respawnTime, 26758, failable, lootTable, positions);
        }
    }

    private static final LootTable ZANIK_CHEST = new LootTable().addTable(1,
            new LootItem(24362, 1, 1),
            new LootItem(Items.ROPE, 1, 1),
            new LootItem(Items.MINING_HELMET, 1, 1),
            new LootItem(Items.FROGLEATHER_BODY, 1, 1),
            new LootItem(Items.FROGLEATHER_CHAPS, 1, 1),
            new LootItem(Items.OIL_LANTERN, 1, 1),
            new LootItem(Items.BULLSEYE_LANTERN, 1, 1),
            new LootItem(Items.NEWCOMER_MAP, 1, 1),
            new LootItem(Items.BONE_BOLTS, 1, 89, 1),
            new LootItem(Items.DORGESHUUN_CROSSBOW, 1, 1),
            new LootItem(Items.BONE_DAGGER, 1, 1),
            new LootItem(Items.HAMMER, 1, 1),
            new LootItem(Items.BIG_BONES, 1, 1),
            new LootItem(Items.HAM_HOOD, 1, 1),
            new LootItem(Items.HAM_LOGO, 1, 1),
            new LootItem(Items.HAM_SHIRT, 1, 1),
            new LootItem(Items.HAM_ROBE, 1, 1),
            new LootItem(Items.HAM_GLOVES, 1, 1),
            new LootItem(Items.HAM_CLOAK, 1, 1),
            new LootItem(Items.HAM_BOOTS, 1, 1),
            new LootItem(Items.SPADE, 1, 1),
            new LootItem(Items.BUCKET, 1, 1),
            new LootItem(Items.CAVE_GOBLIN_WIRE, 1, 1)
    );
    private static final LootTable OLDAK_CHEST = new LootTable().addTable(1,
            new LootItem(Items.AIR_RUNE, 10, 19, 1),
            new LootItem(Items.MIND_RUNE, 10, 19, 1),
            new LootItem(Items.WATER_RUNE, 10, 19, 1),
            new LootItem(Items.EARTH_RUNE, 10, 19, 1),
            new LootItem(Items.FIRE_RUNE, 10, 19, 1),
            new LootItem(Items.BODY_RUNE, 10, 19, 1),
            new LootItem(Items.COSMIC_RUNE, 5, 9, 1),
            new LootItem(Items.CHAOS_RUNE, 5, 9, 1),
            new LootItem(Items.LAW_RUNE, 5, 9, 1),
            new LootItem(Items.NATURE_RUNE, 2, 4, 1),
            new LootItem(Items.DEATH_RUNE, 2, 4, 1),
            new LootItem(Items.DORGESHKAAN_SPHERE, 1, 1),
            new LootItem(Items.DORGESHKAAN_SPHERE, 1, 1),
            new LootItem(Items.UNPOWERED_ORB, 1, 1),
            new LootItem(Items.CAVE_GOBLIN_WIRE, 1, 1),
            new LootItem(Items.EMPTY_LIGHT_ORB, 1, 1),
            new LootItem(Items.LIGHT_ORB, 1, 1),
            new LootItem(Items.AIR_TALISMAN, 1, 1),
            new LootItem(Items.WATER_TALISMAN, 1, 1),
            new LootItem(Items.EARTH_TALISMAN, 1, 1),
            new LootItem(Items.FIRE_TALISMAN, 1, 1)
    );

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
            e.delay(2);
            player.sendMessage("You trigger a trap!");
            player.hit(new Hit().randDamage(2, 3));
            player.unlock();
        });
    }

    private static boolean isSuccessful(Player player, Chest chest) {
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

    private static void disarm(Player player, Chest chest, GameObject object) {
        if (!player.getStats().check(StatType.Thieving, chest.level)) {
            player.sendMessage("You need a thieving level of " + chest.level + " to disarm this trap.");
            return;
        }
        player.startEvent(e -> {
            player.lock(LockType.FULL_REGULAR_DAMAGE);
            player.sendMessage("You begin to open the chest...");
            player.animate(537);
            e.delay(2);
            if (isSuccessful(player, chest)) {
                player.animate(536);
                player.sendMessage("You successfully disarm the trap.");
                e.delay(1);
                Item item = chest.lootTable.rollItem();
                if (chest == Chest.DORGESH_KAAN) {
                    if (object.getPosition().equals(2706, 5364)) {
                        item = OLDAK_CHEST.rollItem();
                    } else if (object.getPosition().equals(2746, 5350) || object.getPosition().equals(2743, 5350)) {
                        item = ZANIK_CHEST.rollItem();
                    }
                }
                player.getInventory().addOrDrop(item);
                player.getStats().addXp(StatType.Thieving, chest.xp, true);
                player.sendMessage("You steal some loot from the chest.");
                lookupTask(player, chest, item);
                replaceChest(object, chest.replacementId, chest.respawnTime);
                if (chest == Chest.ROGUES_CASTLE && Random.rollDie(50)) {
                    rougesAttack(player);
                }
            } else {
                player.sendMessage("You trigger a trap!");
                e.delay(1);
                player.hit(new Hit().randDamage(2, 3));
                // Currently don't teleport the player out, consider if this is needed in future
            }
            player.unlock();
        });
    }

    private static void lookupTask(Player player, Chest chest, Item item) {
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.THIEVECHEST, item.getDef().name, item.getAmount(), true);
        if (chest == Chest.ISLE_OF_SOULS)
            player.getTaskManager().doLookupByUUID(912, 1); // Steal from the Isle of Souls Dungeon Chest
        if (chest == Chest.ROGUES_CASTLE)
            player.getTaskManager().doLookupByUUID(893); // Steal From the Chest in the Rogues' Castle
    }

    static {
        for (Chest chest : Chest.values()) {
            if (chest == Chest.DORGESH_KAAN || chest == Chest.DORG_RICH) {    // So many spawns, just blanket cover all of them
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
