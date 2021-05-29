package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
public class ThievableChests {

    public static class Loot {
        public Loot(int item, int amt) {
            this(item, amt, amt);
        }

        public Loot(int item) {
            this(item, 1, 1);
        }

        public Loot(int item, int min, int max) {
            this.item = item;
            this.min = min;
            this.max = max;
        }

        public int item;
        public int min, max;
    }

    @AllArgsConstructor
    public enum Chests {
        COINS_10(11737, 13, 7.8, 7,
                new Loot[] { new Loot(995, 3000, 3250) },
                new Position[] { new Position(2671, 3299, 1) }),
        COINS_101(11735, 13, 7.8, 7,
                new Loot[] { new Loot(995, 3000, 3250) },
                new Position[] { new Position(2612, 3314, 1), new Position(2630, 3655) }),

        NATURE(11736, 28, 25.0, 8,
                new Loot[] { new Loot(995, 1000, 1250), new Loot(561, 3, 8) },
                new Position[] { new Position(2671, 3301, 1), new Position(2614, 3314, 1) }),

        COINS_50(11735, 43, 125.0, 50,
                new Loot[] { new Loot(995, 5000, 6500) },
                new Position[] { new Position(3188, 3962), new Position(3189, 3962), new Position(3193, 3962), new Position(3044, 3951) }),
        COINS_501(11736, 43, 125.0, 50,
                new Loot[] { new Loot(995, 5000, 6500) },
                new Position[] { new Position(3042, 3949) }),
        COINS_502(11737, 43, 125.0, 50,
                new Loot[] { new Loot(995, 5000, 6500) },
                new Position[] { new Position(3040, 3949) }),

        ARROWTIP(11742, 47, 150.0, 210,
                new Loot[] { new Loot(41, 25, 30), new Loot(995, 1000, 1250) },
                new Position[] { new Position(2650, 3659), new Position(2639, 3424) }),

        DORGESH_KAAN(22697, 52, 200.0, 210,
                new Loot[] { new Loot(995, 1500, 1750), new Loot(4548), new Loot(4537), new Loot(10981), new Loot(5013), new Loot(10192) },
                new Position[] {  }),

        BLOOD(11738, 59, 250.0, 135,
                new Loot[] { new Loot(995, 1750, 2000), new Loot(565, 15, 25) },
                new Position[] { new Position(2586, 9737), new Position(2586, 9734) }),

        STONE_CHEST(34429, 64, 280, 0,
                new Loot[] {},  // TODO Loot
                new Position[] { new Position(1302, 10087), new Position(1300, 10085), new Position(1300, 10089) }),

        PALADIN(11739, 72, 500.0, 400,
                new Loot[] { new Loot(995, 2500, 3500), new Loot(384, 3, 6), new Loot(450, 3, 6), new Loot(1623, 1, 2) },
                new Position[] { new Position(2588, 3302, 1), new Position(2588, 3291, 1) }),

        DORG_RICH(22681, 78, 650.0, 300,
                new Loot[] { new Loot(995, 500, 2500), new Loot(1623), new Loot(1621), new Loot(1619), new Loot(1617),
                new Loot(1625), new Loot(1627), new Loot(1629), new Loot(4548), new Loot(5013), new Loot(10954), new Loot(10956), new Loot(10958),
                new Loot(2351), new Loot(10973), new Loot(10980) },
                new Position[] {  }),

        ROGUES_CASTLE(26757, 84, 100, 10,
                new Loot[] {
                        new Loot(1622, 7, 12),          // Uncut emerald
                        new Loot(1624, 9, 15),          // Uncut sapphire
                        new Loot(360, 25, 35),          // Raw tuna
                        new Loot(995, 10000, 15000),    // Coins
                        new Loot(593, 35, 45),          // Ashes
                        new Loot(454, 20, 30),          // Coal
                        new Loot(558, 30, 50),          // Mind rune
                        new Loot(1602, 4, 6),           // Diamond
                        new Loot(562, 35, 50),          // Chaos rune
                        new Loot(560, 25, 35),          // Death rune
                        new Loot(554, 25, 40),          // Fire rune
                        new Loot(352, 10, 15),          // Pike
                        new Loot(591, 4, 5),            // Tinderbox
                        new Loot(441, 15, 25)          // Iron ore
                },
                new Position[] { new Position(3297, 3940), new Position(3287, 3946), new Position(3283, 3946) })
        ;   // TODO add one to home

        public int objectId;
        public int level;
        public double xp;
        public int respawnTime;
        public Loot[] loot;
        public Position[] positions;
    }

    private static void replaceChest(GameObject chest) {
        World.startEvent(event -> {
            chest.setId(26758);
            event.delay(6);
            chest.setId(chest.originalId);
        });
    }

    private static void rougesAttack(Player player) {
        for(NPC npc : player.localNpcs()) {
            if(npc.getId() != 6603)
                continue;
            if(npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
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

    private static void disarm(Player player, Chests chest, GameObject object) {
        if (!player.getStats().check(StatType.Thieving, chest.level)) {
            player.sendMessage("You need a thieving level of " + chest.level + "to disarm this trap.");
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
                Loot loot = chest.loot[Random.get(chest.loot.length - 1)];
                int lootAmount = Random.get(loot.min, loot.max);
                player.getInventory().addOrDrop(loot.item, lootAmount);
                player.getStats().addXp(StatType.Thieving, chest.xp, true);
                player.sendMessage("You steal " + (lootAmount > 1 ? "some" : "a") + " " + ItemDef.get(loot.item).name + ".");
                if (chest == Chests.ROGUES_CASTLE && Random.rollDie(50)) {
                    rougesAttack(player);
                }
                if (chest != Chests.DORG_RICH && chest != Chests.DORGESH_KAAN && chest != Chests.STONE_CHEST) {
                    replaceChest(object);
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
                ObjectAction.register(chest.objectId, "open", (player, object) -> {
                    open(player);
                });
                ObjectAction.register(chest.objectId, "pick-lock", (player, object) -> {
                    disarm(player, chest, object);
                });
                continue;
            }
            for (Position pos : chest.positions) {
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "open", (player, object) -> {
                    open(player);
                });
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "search for traps", (player, object) -> {
                    disarm(player, chest, object);
                });
                ObjectAction.register(chest.objectId, pos.getX(), pos.getY(), pos.getZ(), "picklock", (player, object) -> {
                    disarm(player, chest, object);
                });
            }
        }
    }
}
