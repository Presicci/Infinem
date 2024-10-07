package io.ruin.model.activities.combat.raids.xeric.chamber.skilling;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.skillcapes.FarmingSkillCape;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.concurrent.atomic.AtomicInteger;

import static io.ruin.model.skills.Tool.SEED_DIBBER;

public enum Farming {

    GOLPAR(27, 4, 10, 20906, 20904, 29998, 30010),
    BUCHU(39, 6, 15, 20909, 20907, 29999, 30011),
    NOXIFER(55, 12, 30, 20903, 20901, 29997, 30009);

    public final int levelReq, seedId, harvestId, objectIdStart, objectIdEnd;
    private final double plantXp, harvestXp;

    Farming(int levelReq, double plantXp, double harvestXp, int seedId, int harvestId, int objectIdStart, int objectIdEnd) {
        this.levelReq = levelReq;
        this.plantXp = plantXp;
        this.harvestXp = harvestXp;
        this.seedId = seedId;
        this.harvestId = harvestId;
        this.objectIdStart = objectIdStart;
        this.objectIdEnd = objectIdEnd;
    }

    /**
     * Herb patches
     */
    static {
        ObjectAction.register(29765, "inspect", (player, obj) -> {
            player.sendFilteredMessage("The herb patch appears to be empty.");
        });
        for (Farming patch : values()) {
            ItemObjectAction.register(patch.seedId, 29765, (player, item, obj) -> plantSeed(player, item, obj, patch));
            ObjectAction.register(patch.objectIdEnd, "pick", (player, obj) -> pickHerbPatch(player, obj, patch));
            for (int i = 0; i < 5; i++)
                ObjectAction.register(patch.objectIdStart + (3 * i), "inspect", (player, obj) -> inspectHerbPatch(player, obj, patch));
            ObjectAction.register(patch.objectIdEnd, "clear", Farming::clearHerbPatch);
        }
    }

    private static void plantSeed(Player player, Item item, GameObject object, Farming patch) {
        if (!player.getStats().check(StatType.Farming, patch.levelReq, SEED_DIBBER, patch.seedId, "plant a " + item.getDef().name))
            return;
        if (!player.getInventory().contains(Tool.SEED_DIBBER, 1)) {
            player.sendMessage("You need a seed dibber to plant seeds.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(2291);
            event.delay(2);
            player.getInventory().remove(patch.seedId, 1);
            player.getStats().addXp(StatType.Farming, patch.plantXp, true);
            player.sendFilteredMessage("You plant the " + item.getDef().name + " in the herb patch.");
            growHerbPatch(object, patch.objectIdStart);
            player.unlock();
        });
    }

    private static void growHerbPatch(GameObject object, int patchId) {
        object.putTemporaryAttribute("PRODUCE", 3);
        World.startEvent(event -> {
            for (int i = 0; i < 5; i++) {
                object.setId(patchId + (3 * i));
                event.delay(13);
            }
        });
    }

    private static void pickHerbPatch(Player player, GameObject object, Farming patch) {
        player.startEvent(event -> {
            if (!player.getInventory().contains(952, 1)) {
                player.sendMessage("You'll need a spade to harvest your crops.");
                return;
            }
            if (player.getInventory().getFreeSlots() == 0) {
                player.sendMessage("Not enough space in your inventory.");
                return;
            }
            player.sendFilteredMessage("You begin to harvest the herb patch.");
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2282, 2);
                event.delay(2);
                player.collectResource(patch.harvestId, 1);
                player.getInventory().add(patch.harvestId, 1);
                player.getStats().addXp(StatType.Farming, patch.harvestXp, true);
                removeProduce(player, object);
                if (!hasProduce(object)) {
                    object.setId(29765);
                    player.sendMessage("The herb patch is now empty.");
                    return;
                }
                event.delay(1);
            }
        });
    }

    private static void removeProduce(Player player, GameObject object) {
        int lvl = player.getStats().get(StatType.Farming).currentLevel;
        double perLevel = (80.0 - 45.0) / 99;
        if (Random.get(100) < 30.0 + (lvl * perLevel)) {
            object.incrementTemporaryNumericAttribute("PRODUCE", -1);
        }
    }

    private static boolean hasProduce(GameObject object) {
        return object.getTemporaryAttributeIntOrZero("PRODUCE") > 0;
    }

    private static void inspectHerbPatch(Player player, GameObject object, Farming patch) {
        if (object.id == patch.objectIdEnd)
            player.sendFilteredMessage("This herb patch is ready to be picked.");
        else
            player.sendFilteredMessage("There's a " + ItemDefinition.get(patch.seedId).name + " growing here.");
    }

    private static void clearHerbPatch(Player player, GameObject object) {
        player.startEvent(event -> {
            player.lock();
            player.animate(830);
            event.delay(1);
            object.setId(29765);
            player.sendMessage("You clear the farming patch..");
            if (player.raidsParty.getMembers().size() != 0)
                player.raidsParty.getMembers().forEach(m -> m.sendFilteredMessage(player.getName() + " has cleared the farming patch."));
            event.delay(1);
            player.unlock();
        });
    }


    /**
     * Weeds
     */
    public static final LootTable SEEDS = new LootTable().addTable(1,
            new LootItem(20903, 1, 3, 1),   //Noxifer seed
            new LootItem(20906, 1, 3, 1),  //Golpar seed
            new LootItem(20909, 1, 3, 1)   //Buchu seed
    );

    static {
        ObjectAction.register(29773, "rake", (player, obj) -> {
            if (!player.getInventory().hasId(Tool.RAKE)) {
                player.sendFilteredMessage("You need a rake to do this.");
                return;
            }

            player.animate(2273);
            player.startEvent(event -> {
                while (true) {
                    player.animate(2273);
                    player.privateSound(2442);
                    event.delay(4);
                    if (Random.get() < 0.8) {
                        Item randomSeed = SEEDS.rollItem();
                        player.getInventory().add(randomSeed);
                        player.sendFilteredMessage("You find " + (randomSeed.getAmount() > 1 ? "several " : "a ") +
                                randomSeed.getDef().name + (randomSeed.getAmount() > 1 ? "s!" : "!"));
                    }
                }
            });
        });
    }
}
