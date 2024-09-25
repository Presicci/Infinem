package io.ruin.model.activities.shadesofmortton;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.jewellery.FlamtaerBracelet;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2024
 */
public class TempleRepairing {

    protected static final String SANCTITY_KEY = "SOM_SANCTITY";
    protected static final String RESOURCES_KEY = "SOM_RESOURCES";
    private static final int FLUSH_WALL = 4068;
    private static final int CORNER_WALL = 4079;

    private static int repairState = 0;

    protected static void addSanctity(Player player, int amount) {
        if (player.getAttributeIntOrZero(SANCTITY_KEY) >= 100) return;
        player.incrementNumericAttribute(SANCTITY_KEY, amount);
        player.getPacketSender().sendVarp(345, player.getAttributeIntOrZero(SANCTITY_KEY));
    }

    private static boolean hasInventoryResources(Player player) {
        if (!FlamtaerBag.hasInBag(player, Items.TIMBER_BEAM, 1) && !player.getInventory().hasId(Items.TIMBER_BEAM) && !player.getInventory().hasId(Items.PLANK)) {
            return false;
        }
        if (!FlamtaerBag.hasInBag(player, Items.LIMESTONE_BRICK, 1) && !player.getInventory().hasId(Items.LIMESTONE_BRICK)) {
            return false;
        }
        return FlamtaerBag.hasInBag(player, Items.SWAMP_PASTE, 5) || player.getInventory().getAmount(Items.SWAMP_PASTE) >= 5;
    }

    private static void removeInventoryResources(Player player) {
        if (player.getAttributeIntOrZero(RESOURCES_KEY) >= 100) return;
        if (!FlamtaerBag.removeFromBag(player, Items.TIMBER_BEAM, 1) && player.getInventory().remove(Items.TIMBER_BEAM, 1) <= 0) {
            player.getInventory().remove(Items.PLANK, 1);
        }
        if (!FlamtaerBag.removeFromBag(player, Items.LIMESTONE_BRICK, 1)) {
            player.getInventory().remove(Items.LIMESTONE_BRICK, 1);
        }
        if (!FlamtaerBag.removeFromBag(player, Items.SWAMP_PASTE, 5)) {
            player.getInventory().remove(Items.SWAMP_PASTE, 5);
        }
        player.incrementNumericAttribute(RESOURCES_KEY, 5);
    }

    private static void buildIfPossible(GameObject object, int maxId) {
        if (object.id < maxId) object.setId(object.id + 1);
    }

    private static boolean isSuccessfulRepair(int effectiveLevel) {
        int roll = Math.max(2, 12 - (effectiveLevel / 10));
        return Random.rollDie(roll);
    }

    private static void repair(Player player, GameObject gameObject, int maxId) {
        if (!player.getStats().check(StatType.Crafting, 20, "build the temple")) {
            return;
        }
        if (!Tool.HAMMER.hasTool(player) && !player.getInventory().hasId(Items.FLAMTAER_HAMMER)) {
            player.sendMessage("You need a hammer or Flamtaer hammer to build the temple.");
            return;
        }
        if (player.getAttributeIntOrZero(RESOURCES_KEY) <= 0 && !hasInventoryResources(player)) {
            player.sendMessage("You need timber, a limestone brick, and swamp paste to repair the temple.");
            return;
        }

        player.startEvent(event -> {
            int attempts = 0;
            player.animate(3683);
            do {
                event.delay(4);
                player.animate(3683);
                event.delay(1);
                if (player.getAttributeIntOrZero(RESOURCES_KEY) <= 0 && !hasInventoryResources(player)) {
                    player.sendMessage("You are out of resources.");
                    return;
                }
                if (hasInventoryResources(player)) {
                    removeInventoryResources(player);
                }
                int effectiveCraftingLevel = player.getStats().get(StatType.Crafting).currentLevel + (player.getInventory().hasId(Items.FLAMTAER_HAMMER) ? 40 : 0);
                if (isSuccessfulRepair(effectiveCraftingLevel)) {
                    player.incrementNumericAttribute(RESOURCES_KEY, -1);
                    addSanctity(player, 5);
                    player.getStats().addXp(StatType.Crafting, Random.get(25) + 25, true);
                    buildIfPossible(gameObject, maxId);
                    RandomEvent.attemptTrigger(player, 25);
                    if (player.getAttributeIntOrZero(RESOURCES_KEY) > 0 && FlamtaerBracelet.test(player)) {
                        player.incrementNumericAttribute(RESOURCES_KEY, -1);
                        addSanctity(player, 5);
                        player.getStats().addXp(StatType.Crafting, Random.get(25) + 25, true);
                        buildIfPossible(gameObject, maxId);
                    }
                }
                player.getPacketSender().sendVarp(344, player.getAttributeIntOrZero(RESOURCES_KEY));
                player.getPacketSender().sendVarp(345, player.getAttributeIntOrZero(SANCTITY_KEY));
            } while (++attempts < 20);
        });
    }

    private static int calculateRepairState(List<GameObject> wallObjects) {
        int repairAmt = 0;
        for (GameObject obj : wallObjects) {
            if (obj.id >= CORNER_WALL) {
                repairAmt += obj.id - CORNER_WALL;
            } else {
                repairAmt += obj.id - FLUSH_WALL;
            }
        }
        return (int) (repairAmt / 1.5);
    }

    private static void updateRepairState(List<GameObject> wallObjects) {
        repairState = calculateRepairState(wallObjects);
        Region.get(13875).players.forEach(player -> {
            if (!BOUNDS.inBounds(player)) return;
            player.getPacketSender().sendVarp(343, repairState);
        });
    }

    private static final List<NPC> SPAWNED_SHADES = new ArrayList<>();
    private static final Bounds WEST_SPAWNS = new Bounds(3499, 3312, 3502, 3321, 0);
    private static final Bounds EAST_SPAWNS = new Bounds(3510, 3312, 3513, 3321, 0);

    private static void spawnShades() {
        if (Region.get(13875).players.isEmpty() && !SPAWNED_SHADES.isEmpty()) {
            NPC shade = Random.get(SPAWNED_SHADES);
            SPAWNED_SHADES.remove(shade);
            shade.hit(new Hit().fixedDamage(shade.getHp()).delay(0));
        } else if (SPAWNED_SHADES.size() < 10) {
            spawnShade();
        }
    }

    private static void spawnShade() {
        Bounds bounds = Random.rollDie(2) ? WEST_SPAWNS : EAST_SPAWNS;
        NPC shade = new NPC(1277).spawn(bounds.randomPosition());
        SPAWNED_SHADES.add(shade);
        shade.walkBounds = new Bounds(shade.getPosition(), 3);
        shade.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            shade.remove();
            SPAWNED_SHADES.remove(shade);
            if (killer != null && killer.player != null) {
                addSanctity(killer.player, 2);
            }
        };
    }

    private static final Bounds BOUNDS = new Bounds(3492, 3308, 3518, 3323, 0);

    static {
        List<GameObject> wallObjects = Arrays.asList(
                Tile.get(3505, 3314, 0).getObject(-1, -1, -1),
                Tile.get(3504, 3314, 0).getObject(-1, -1, -1),
                Tile.get(3504, 3315, 0).getObject(-1, -1, -1),
                Tile.get(3504, 3316, 0).getObject(-1, -1, -1),
                Tile.get(3504, 3317, 0).getObject(-1, -1, -1),
                Tile.get(3504, 3318, 0).getObject(-1, -1, -1),
                Tile.get(3505, 3318, 0).getObject(-1, -1, -1),
                Tile.get(3506, 3318, 0).getObject(-1, -1, -1),
                Tile.get(3507, 3318, 0).getObject(-1, -1, -1),
                Tile.get(3508, 3318, 0).getObject(-1, -1, -1),
                Tile.get(3508, 3317, 0).getObject(-1, -1, -1),
                Tile.get(3508, 3316, 0).getObject(-1, -1, -1),
                Tile.get(3508, 3315, 0).getObject(-1, -1, -1),
                Tile.get(3508, 3314, 0).getObject(-1, -1, -1),
                Tile.get(3507, 3314, 0).getObject(-1, -1, -1)
        );
        for (int index = FLUSH_WALL; index < FLUSH_WALL + 11; index++) {
            ObjectAction.register(index, "repair", (player, obj) -> repair(player, obj, FLUSH_WALL + 10));
        }
        for (int index = CORNER_WALL; index < CORNER_WALL + 11; index++) {
            ObjectAction.register(index, "repair", (player, obj) -> repair(player, obj, CORNER_WALL + 10));
        }
        // Repairing the altar
        ItemObjectAction.register(4092, (player, item, obj) -> {
            if (repairState < 25) {
                player.sendMessage("The temple needs a repair state of 25% before the altar can be repaired.");
                return;
            }
            if (!Tool.HAMMER.hasTool(player) && !player.getInventory().hasId(Items.FLAMTAER_HAMMER)) {
                player.sendMessage("You need a hammer or Flamtaer hammer to repair the altar.");
                return;
            }
            player.lock();
            player.startEvent(e -> {
                player.animate(3683);
                e.delay(1);
                player.sendMessage("You repair the altar.");
                player.getStats().addXp(StatType.Crafting, 10, true);
                player.unlock();
                if (obj.id == 4092) {
                    obj.setId(4091);
                    World.startEvent(event -> {
                        event.delay(500);
                        obj.setId(4092);
                    });
                }
            });
        });
        // Lighting the altar
        ObjectAction.register(4091, "light", (player, obj) -> {
            if (!player.getInventory().hasId(Tool.TINDER_BOX)) {
                player.sendMessage("You need a tinderbox to light the altar.");
                return;
            }
            player.lock();
            player.startEvent(e -> {
                player.sendMessage("You light the altar.");
                player.animate(832);
                e.delay(1);
                player.getStats().addXp(StatType.Firemaking, 100, true);
                player.unlock();
                if (obj.id == 4091) obj.setId(4090);
            });
        });
        MapListener.registerBounds(BOUNDS)
                .onEnter(player -> {
                    player.openInterface(InterfaceType.PRIMARY_OVERLAY, 171);
                    player.getPacketSender().sendVarp(344, player.getAttributeIntOrZero(RESOURCES_KEY));
                    player.getPacketSender().sendVarp(345, player.getAttributeIntOrZero(SANCTITY_KEY));
                })
                .onExit((player, logout) -> {
                    player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
                });


        World.startEvent(e -> {
            while (true) {
                e.delay(20);
                updateRepairState(wallObjects);
                spawnShades();
                e.delay(20);
                updateRepairState(wallObjects);
                spawnShades();
                e.delay(20);
                updateRepairState(wallObjects);
                spawnShades();
                e.delay(20);
                updateRepairState(wallObjects);
                spawnShades();
                e.delay(20);
                for (GameObject obj : wallObjects) {
                    if (!Random.rollDie(3)) continue;
                    if (obj.id >= CORNER_WALL) {
                        if (obj.id > CORNER_WALL) obj.setId(obj.id - 1);
                    } else {
                        if (obj.id > FLUSH_WALL) obj.setId(obj.id - 1);
                    }
                }
                updateRepairState(wallObjects);
                spawnShades();
            }
        });
    }
}
