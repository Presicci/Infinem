package io.ruin.model.skills.woodcutting;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.content.tasksystem.areas.diaryitems.KandarinHeadgear;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.BirdNest;
import io.ruin.model.item.actions.impl.chargable.CrystalEquipment;
import io.ruin.model.item.actions.impl.chargable.InfernalTools;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.skillcapes.WoodcuttingSkillCape;
import io.ruin.model.map.MapArea;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.skills.firemaking.Burning;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.EventConsumer;

import java.util.function.Supplier;

public class Woodcutting {

    public static final LootTable SULLIUSCEP_LOOT = new LootTable().addTable(1,
            new LootItem(21562, 1, 1440),  // Small unid fossil
            new LootItem(21564, 1, 45),  // Medium unid fossil
            new LootItem(21566, 1, 40),  // Large unid fossil
            new LootItem(21568, 1, 10),  // Rare unid fossil
            new LootItem(21555, 5, 16, 4000),  // Numulite
            new LootItem(6004, 1, 1, 650),  // Mushroom
            new LootItem(2970, 1, 1, 650),  // Mort myre fungus
            new LootItem(21626, 1, 1, 70)   // Sulliuscep cap
    );

    private static final Config SULLIUSCEP_VARPBIT = Config.varpbit(5808, true);

    private static void chop(Tree treeData, Player player, GameObject tree, int treeStump) {
        chop(treeData, player, tree, () -> tree.id == treeStump, worldEvent -> {
            tree.setId(treeStump);
            player.publicSound(2734, 1, 0);
            worldEvent.delay(treeData.respawnTime);
            tree.setId(tree.originalId);
        });
    }

    /**
     * This method was modified so farmed trees could share the same chopping code as normal trees in the world. Only differences should be in the parameters specified below
     *
     * @param treeDeadCheck  Checks if the tree was removed during the time in between the actions of the given player. Only relevant if the tree can lose logs through means other than the given player chopping it
     * @param treeDeadAction What to do when the given player chops the tree's last logs. Should include changing the object into a stump and handle the respawning.
     */
    public static void chop(Tree treeData, Player player, GameObject tree, Supplier<Boolean> treeDeadCheck, EventConsumer treeDeadAction) {
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }
        Stat stat = player.getStats().get(StatType.Woodcutting);
        if (stat.currentLevel < treeData.levelReq) {
            player.sendMessage("You need a Woodcutting level of " + treeData.levelReq + " to chop down this tree.");
            player.privateSound(2277);
            return;
        }
        if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasFreeSlots(1) && player.getInventory().isFull()) {
            player.sendMessage("Your inventory and bank are too full to hold any more logs.");
            player.privateSound(2277);
            return;
        } else if (player.getInventory().isFull()) {
            player.sendMessage("Your inventory is too full to hold any more logs.");
            player.privateSound(2277);
            return;
        }
        player.startEvent(event -> {
            int attempts = 0;
            player.animate(hatchet.animationId);
            player.sendFilteredMessage("You swing your axe at the tree.");
            event.delay(1);
            while (true) {
                int effectiveLevel = getEffectiveLevel(player, treeData, tree);
                if (player.debug) {
                    double chance = chance(effectiveLevel, treeData, hatchet);
                    double logsPerTick = chance / 2;
                    double logsPerHour = 100 * 60 * logsPerTick;
                    double xpPerTick = logsPerTick * treeData.experience * StatType.Woodcutting.defaultXpMultiplier;
                    double xpPerHour = 100 * 60 * xpPerTick;
                    player.sendMessage("baseChance=" + treeData.baseChance + ", chance=" + NumberUtils.formatTwoPlaces(chance) + ", xp/tick=" + NumberUtils.formatNumber((long) xpPerTick) + "");
                    player.sendMessage("logsPerHour=" + NumberUtils.formatTwoPlaces(logsPerHour) + ", xpPerHour=" + NumberUtils.formatNumber((long) xpPerHour));
                }
                if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasFreeSlots(1) && player.getInventory().isFull()) {
                    player.sendMessage("Your inventory and bank are too full to hold any more logs.");
                    player.privateSound(2277);
                    player.resetAnimation();
                    return;
                } else if (player.getInventory().isFull()) {
                    player.sendMessage("Your inventory is too full to hold any more logs.");
                    player.privateSound(2277);
                    player.resetAnimation();
                    return;
                }
                if (hatchet == Hatchet.CRYSTAL) {
                    if (!CrystalEquipment.AXE.hasCharge(player)) return;
                }
                if (hatchet == Hatchet.INFERNAL) {
                    if (!InfernalTools.INFERNAL_AXE.hasCharge(player)) return;
                }
                if (treeDeadCheck.get()) {
                    player.resetAnimation();
                    return;
                }
                if (attempts % 4 == 0 && successfullyCutTree(effectiveLevel, treeData, hatchet)) {
                    int amount = 1;
                    Burning burning = Burning.get(treeData.log);
                    if (hatchet == Hatchet.INFERNAL && (Random.rollDie(3, 1)) && InfernalTools.INFERNAL_AXE.hasCharge(player) && burning != null) {
                        player.sendFilteredMessage("The infernal axe incinerates some logs.");
                        player.graphics(580, 50, 0);
                        player.getStats().addXp(StatType.Firemaking, burning.exp / 2, true);
                        InfernalTools.INFERNAL_AXE.removeCharge(player);
                    } else {
                        if (treeData == Tree.SULLIUSCEP) {
                            Item loot = SULLIUSCEP_LOOT.rollItem();
                            if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasRoomFor(treeData.log)) {
                                amount *= 2;
                                player.getBank().add(loot.getId(), loot.getAmount());
                                player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(treeData.log).name + " you would have gained, giving you a total of " + player.getBank().getAmount(loot.getId()) + ".");
                            } else {
                                player.sendFilteredMessage("You get " + loot.getDef().descriptiveName + ".");
                                player.getInventory().add(loot);
                            }
                            if (loot.getId() == 21626)
                                player.getTaskManager().doLookupByUUID(352);    // Chop a Sulliuscep Cap
                        } else if (treeData != Tree.CRYSTAL) {
                            if (treeData == Tree.REGULAR && KandarinHeadgear.hasEquipped(player)) amount += 1;
                            if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasRoomFor(treeData.log)) {
                                amount *= 2;
                                player.getBank().add(treeData.log, amount);
                                player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(treeData.log).name + " you would have gained, giving you a total of " + player.getBank().getAmount(treeData.log) + ".");
                            } else {
                                player.sendFilteredMessage("You get some " + treeData.treeName + ".");
                                player.getInventory().add(treeData.log, amount);
                            }
                            player.getTaskManager().doLookupByUUID(16, amount);  // Chop Some Logs
                            if (hatchet == Hatchet.STEEL)
                                player.getTaskManager().doLookupByUUID(17, 1);  // Chop Some Logs With a Steel Axe
                            if (hatchet == Hatchet.RUNE)
                                player.getTaskManager().doLookupByUUID(95, 1);  // Chop Some Logs With a Rune Axe
                        }
                        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.CHOPLOG, ItemDefinition.get(treeData.log).name);
                        player.collectResource(new Item(treeData.log, 1));
                    }
                    rollBirdNest(player, treeData);
                    rollClueNest(player, treeData);
                    rollPet(player, treeData);
                    treeData.counter.increment(player, amount);
                    double xp = treeData.experience;
                    player.getStats().addXp(StatType.Woodcutting, xp * amount, true);
                    if (hatchet == Hatchet.CRYSTAL) {
                        CrystalEquipment.AXE.removeCharge(player);
                    }
                    if (treeData.single
                            || (treeData.fellChance > 0 && Random.get() < treeData.fellChance)
                            || (tree == null && treeDeadAction != null && treeData.fellTime > 0 && Random.rollDie(Math.min(8, treeData.fellTime / 10)))  // Should catch farming trees
                    ) {
                        player.resetAnimation();
                        if (treeData == Tree.SULLIUSCEP) {
                            int newValue = SULLIUSCEP_VARPBIT.increment(player, 1);
                            if (newValue >= 5)
                                SULLIUSCEP_VARPBIT.set(player, 0);
                            return;
                        }
                        World.startEvent(treeDeadAction);
                        return;
                    } else if (tree != null && treeData.fellTime > 0) {
                        ForestryTree.pingTree(player, tree, treeData, treeDeadAction);
                    }
                } else if (tree != null) {
                    ForestryTree.pingIfActive(player, tree);
                }
                if (attempts++ % 4 == 0)
                    player.animate(hatchet.animationId);
                event.delay(1);
            }
        });
    }

    private static void rollPet(Player player, Tree tree) {
        if (Random.rollDie(tree.petOdds - (player.getStats().get(StatType.Woodcutting).currentLevel * 25)))
            Pet.BEAVER.unlock(player);
    }

    protected static void rollBirdNest(Player player, Tree tree) {
        if (tree == Tree.REDWOOD || tree == Tree.SULLIUSCEP) return;
        int chance = 256;
        if (WoodcuttingSkillCape.wearsWoodcuttingCape(player))
            chance -= chance / 10;
        if (ActivitySpotlight.isActive(ActivitySpotlight.DOUBLE_BIRD_NEST_CHANCE))
            chance /= 2;
        if (Random.rollDie(chance)) {
            new GroundItem(BirdNest.getRandomNest(), 1)
                    .owner(player).position(RouteFinder.findWalkable(player.getPosition()))
                    .spawn();
            player.sendFilteredMessage("A bird's nest falls out of the tree.");
            player.getTaskManager().doLookupByUUID(18, 1);  // Obtain a Bird Nest
            PlayerCounter.ACQUIRED_BIRDS_NESTS.increment(player, 1);
        }
    }

    private static void rollClueNest(Player player, Tree tree) {
         int numerator = tree.petOdds;
         if (numerator <= 0) return;
         int denominator = 100 + player.getStats().get(StatType.Woodcutting).currentLevel;
         boolean ringOfWealthEffect = RingOfWealth.wearingRingOfWealthImbued(player) && player.wildernessLevel > 0;
         for (ClueType clue : ClueType.values()) {
             if (clue == ClueType.MASTER) continue;
             int chance = (numerator / denominator) * (clue.ordinal() + 1);
             if (ActivitySpotlight.isActive(ActivitySpotlight.DOUBLE_CLUE_NEST_CHANCE))
                 chance /= 2;
             if (ringOfWealthEffect)
                 chance /= 2;
             if (Random.rollDie(chance)) {
                 player.getInventory().addOrDrop(ClueNest.values()[clue.ordinal()].itemID, 1);
             }
         }
    }

    protected static int getEffectiveLevel(Player player, Tree treeData, GameObject tree) {
        int base = player.getStats().get(StatType.Woodcutting).currentLevel;
        if (WoodcuttingGuild.hasBoost(player)) {
            base += 7;
        } else if (!treeData.single && tree != null) {
            int treePlayers = ForestryTree.getTreePlayers(tree);
            if (treePlayers > 1) base += Math.min(10, treePlayers - 1);
        }
        return base;
    }

    protected static boolean successfullyCutTree(int level, Tree type, Hatchet hatchet) {
        return Random.get(100) <= chance(level, type, hatchet);
    }

    private static double chance(int woodcuttingLevel, Tree tree, Hatchet hatchet) {
        int levelDifference = woodcuttingLevel - tree.levelReq;
        double chance = tree.baseChance + (levelDifference * tree.levelSlope);
        double axeMultiplier = hatchet.axeMultiplier;
        return (Math.min(100, chance * axeMultiplier));
    }

    static {
        ObjectAction.register(4818, "chop-down", (player, obj) -> chop(Tree.REGULAR, player, obj, 4819));   // Ape atoll
        ObjectAction.register(4820, "chop-down", (player, obj) -> chop(Tree.REGULAR, player, obj, 4821));   // Ape atoll
        ObjectAction.register(1278, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(1276, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(2091, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(2092, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1355));
        ObjectAction.register(1286, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1351));
        ObjectAction.register(1282, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1347));
        ObjectAction.register(1383, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1358));
        ObjectAction.register(1289, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1353));
        ObjectAction.register(1290, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1354));   // Dead tree
        ObjectAction.register(36672, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 36673)); // Gwenith tree
        ObjectAction.register(36674, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 36675)); // Gwenith tree
        ObjectAction.register(42393, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 42394)); // Tree in shayzien
        ObjectAction.register(2023, "chop", (player, obj) -> chop(Tree.ACHEY, player, obj, 3371));
        ObjectAction.register(10820, "chop down", (player, obj) -> chop(Tree.OAK, player, obj, 1356));
        ObjectAction.register(10819, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10833, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10831, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10829, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(15062, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 9037));
        ObjectAction.register(9036, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 9037));
        ObjectAction.register(40758, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 40759));
        ObjectAction.register(27499, "chop down", (player, obj) -> chop(Tree.JUNIPER, player, obj, 27500));
        ObjectAction.register(10832, "chop down", (player, obj) -> chop(Tree.MAPLE, player, obj, 9712));
        ObjectAction.register(36681, "chop down", (player, obj) -> chop(Tree.MAPLE, player, obj, 36682)); // Gwenith tree
        ObjectAction.register(10822, "chop down", (player, obj) -> chop(Tree.YEW, player, obj, 9714));
        ObjectAction.register(1754, "chop down", (player, obj) -> chop(Tree.YEW, player, obj, 9714));
        ObjectAction.register(10823, "chop down", (player, obj) -> chop(Tree.YEW, player, obj, 9714));
        ObjectAction.register(10834, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(1761, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(1762, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(10835, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(29668, "cut", (player, obj) -> chop(Tree.REDWOOD, player, obj, 29669));
        ObjectAction.register(29670, "cut", (player, obj) -> chop(Tree.REDWOOD, player, obj, 29671));
        ObjectAction.register(29763, "chop", (player, obj) -> chop(Tree.SAPLING, player, obj, 29764));
        ObjectAction.register(9034, "chop down", (player, obj) -> chop(Tree.MAHOGANY, player, obj, 9035));
        ObjectAction.register(10821, "chop down", (player, obj) -> chop(Tree.HOLLOW_TREE, player, obj, 2310));
        ObjectAction.register(10830, "chop down", (player, obj) -> chop(Tree.HOLLOW_TREE, player, obj, 4061));
        ObjectAction.register(1292, "chop down", (player, obj) -> chop(Tree.DRAMEN_TREE, player, obj, -1));
        ObjectAction.register(3037, "cut down", (player, obj) -> chop(Tree.ARCTIC_PINE, player, obj, 1349));
        ObjectAction.register(31420, 1, (player, obj) -> chop(Tree.SULLIUSCEP, player, obj, -1));
    }

}
