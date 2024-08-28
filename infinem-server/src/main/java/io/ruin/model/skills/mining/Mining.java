package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.Geode;
import io.ruin.model.item.actions.impl.chargable.CelestialRing;
import io.ruin.model.item.actions.impl.chargable.CrystalEquipment;
import io.ruin.model.item.actions.impl.chargable.InfernalTools;
import io.ruin.model.item.actions.impl.storage.CoalBag;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.skillcapes.MiningSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.MapArea;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class Mining {

    /* LootTable for the gems obtained at a 1/256 rate from mining */
    private static final LootTable GEM_TABLE = new LootTable().addTable("gems", 1,
            new LootItem(Items.UNCUT_SAPPHIRE, 1, 8),
            new LootItem(Items.UNCUT_EMERALD, 1, 5),
            new LootItem(Items.UNCUT_RUBY, 1, 3),
            new LootItem(Items.UNCUT_DIAMOND, 1, 1)
    );

    public static final LootTable GEM_ROCK_TABLE = new LootTable().addTable(1,
            new LootItem(Items.UNCUT_OPAL, 1, 1500),
            new LootItem(Items.UNCUT_JADE, 1, 750),
            new LootItem(Items.UNCUT_RED_TOPAZ, 1, 375),
            new LootItem(Items.UNCUT_SAPPHIRE, 1, 225),
            new LootItem(Items.UNCUT_EMERALD, 1, 125),
            new LootItem(Items.UNCUT_RUBY, 1, 125),
            new LootItem(Items.UNCUT_DIAMOND, 1, 100)
    );

    private static void mine(Rock rockData, Player player, GameObject rock, int emptyId, PlayerCounter counter, NPC npc) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        Stat stat = player.getStats().get(StatType.Mining);
        if (stat.currentLevel < rockData.levelReq) {
            player.sendMessage("You need a Mining level of " + rockData.levelReq + " to mine this rock.");
            player.privateSound(2277);
            return;
        }
        if (rockData == Rock.AMETHYST && MapArea.EXCLUSIVE_AMETHYST_MINE.inArea(player) && !AreaReward.ALTERNATE_AMETHYST_MINE.checkReward(player, "mine these rocks."))
            return;
        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more " + rockData.rockName + ".");
            return;
        }
        final int miningAnimation = rockData == Rock.AMETHYST ? pickaxe.crystalAnimationID : pickaxe.regularAnimationID;
        player.startEvent(event -> {
            int attempts = 1;
            player.sendFilteredMessage("You swing your pick at the rock.");
            player.animate(miningAnimation);
            while (attempts < 300) {
                if ((rock != null && rock.id == emptyId)
                        || (npc != null && npc.isRemoved())) {
                    player.resetAnimation();
                    return;
                }
                if (!player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getInventory().isFull()) {
                    player.resetAnimation();
                    player.privateSound(2277);
                    player.sendMessage("Your inventory is too full to hold any more " + rockData.rockName + ".");
                    return;
                }

                boolean rockyOutcrop = false;
                boolean gemRock = false;
                int itemId = 0;
                int random = 0;
                /* If the rock is granite or sandstone, grab a random size */
                if (rockData == Rock.GRANITE || rockData == Rock.SANDSTONE) {
                    if (rockData.multiOre != null) {
                        random = Random.get(rockData.multiOre.length - 1);
                        itemId = rockData.multiOre[random];
                    }
                    rockyOutcrop = true;
                }

                /* If the rock is a gem rock, grab a random size */
                if (rockData == Rock.GEM_ROCK) {
                    itemId = GEM_ROCK_TABLE.rollItem().getId();
                    gemRock = true;
                }

                Item gem = null;
                boolean multiple = false;
                if (canAttempt(attempts, pickaxe) && Random.get(100) <= chance(player, getEffectiveLevel(player), rockData)) {
                    int amount = (isSalt(rockData)) ? getSaltAmount(player) : 1;
                    if (rockData != Rock.GEM_ROCK && Random.rollDie(wearingGlory(player) ? 86 : 256)) {  // 1/256 chance to replace ore with a gem
                        gem = GEM_TABLE.rollItem();

                        player.collectResource(gem);
                        if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                            if (player.getBank().hasRoomFor(gem)) {
                                player.getBank().add(gem.getId(), gem.getAmount());
                                player.sendFilteredMessage("Your Relic banks the " + gem.getDef().name + " you would have gained, giving you a total of " + player.getBank().getAmount(gem.getId()) + ".");
                            } else {
                                player.getInventory().addOrDrop(gem.getId(), gem.getAmount());
                            }
                        } else {
                            player.getInventory().add(gem);
                        }
                    } else {
                        int id = rockyOutcrop || gemRock ? itemId : rockData.ore;
                        if (Random.rollPercent(getExtraOreChance(player, rockData))) {
                            amount *= 2;
                            player.sendFilteredMessage("You manage to mine an additional ore.");
                            multiple = true;
                        }
                        if (rockData.ordinal() <= Rock.ADAMANT.ordinal())
                            CelestialRing.removeChargeIfEquipped(player);
                        player.collectResource(new Item(id, amount));
                        if (rockData == Rock.GRANITE)
                            player.getTaskManager().doLookupByUUID(656, 1, true);    // Mine 30 Chunks of Granite
                        else
                            player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.MINE, ItemDefinition.get(id).name);
                        if (pickaxe == Pickaxe.INFERNAL && Random.rollDie(3, 1) && InfernalTools.INFERNAL_PICKAXE.hasCharge(player) && infernalPickProc(player, rockData.ore)) {
                            player.graphics(580, 155, 0);
                            InfernalTools.INFERNAL_PICKAXE.removeCharge(player);
                            player.sendMessage("Your infernal pickaxe incinerates the " + ItemDefinition.get(id).name + ".");
                        } else if (rockData == Rock.COAL && CoalBag.hasOpenBag(player) && CoalBag.hasSpaceInBag(player)) {
                            player.incrementNumericAttribute("BAGGED_COAL", 1);
                        } else if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasRoomFor(id)) {
                            if (player.getBank().hasRoomFor(id)) {
                                player.getBank().add(id, amount);
                                player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(id).name + " you would have gained, giving you a total of " + player.getBank().getAmount(id) + ".");
                            } else {
                                player.getInventory().addOrDrop(id, amount);
                            }
                        } else {
                            player.getInventory().add(id, amount);
                        }
                    }

                    if (Random.rollDie(rockData.petOdds - (player.getStats().get(StatType.Mining).currentLevel * 25)))
                        Pet.ROCK_GOLEM.unlock(player);

                    /* Rolling for a Geode clue scroll */
                    if (Random.rollDie(ActivitySpotlight.isActive(ActivitySpotlight.DOUBLE_GEODE_CHANCE) ? 125 : 250, 1)) {
                        player.getInventory().addOrDrop(Geode.getRandomGeode(), 1);
                        PlayerCounter.MINED_GEODE.increment(player, 1);
                        player.getTaskManager().doLookupByUUID(102, 1);  // Obtain a Clue Geode While Mining
                    }

                    /* Rolling for mined minerals */
                    if (minedMineral(player, rockData))
                        player.getInventory().addOrDrop(21341, 1);

                    counter.increment(player, amount);
                    if (rockData == Rock.GEM_ROCK) {
                        player.getStats().addXp(StatType.Mining, rockData.experience, true);
                    } else if (gem != null) {   // No xp is earned and the ore is not depleted, just go next
                        if (!player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                            player.sendFilteredMessage("You find an " + gem.getDef().name + ".");
                        }
                        player.getTaskManager().doLookupByUUID(24, 1);  // Obtain a Gem While Mining
                        continue;
                    } else {
                        player.getStats().addXp(StatType.Mining, (rockyOutcrop ? rockData.multiExp[random] : rockData.experience * (multiple ? 2 : 1)) * amount, true);
                    }
                    if (!player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                        player.sendFilteredMessage("You manage to mine " + (rockData == Rock.GEM_ROCK ? "a " : "some ") +
                                (rockData == Rock.GEM_ROCK ? ItemDefinition.get(itemId).name.toLowerCase() : rockData.rockName) + ".");
                    }
                    player.getTaskManager().doSkillItemLookup(itemId, amount);  // Fix this looking up id 0 all the time
                    if (pickaxe == Pickaxe.STEEL)
                        player.getTaskManager().doLookupByUUID(23, 1);  // Mine some Ore With a Steel Pickaxe
                    if (pickaxe == Pickaxe.RUNE)
                        player.getTaskManager().doLookupByUUID(101, 1);  // Mine some Ore With a Rune Pickaxe
                    if (pickaxe == Pickaxe.CRYSTAL) {
                        CrystalEquipment.PICKAXE.removeCharge(player);
                    }
                    if (rockData.depleteTime > 0 && rock != null) {
                        // Mining gloves now have a chance to add 5 health to the rock being mined
                        if (Random.get() < miningGloves(player, rockData)) {
                            TimedRock.pingRockWithMiningGloves(rock, rockData, emptyId);
                        } else {
                            TimedRock.pingRock(rock, rockData, emptyId);
                        }
                    } else if (Random.get() < rockData.depleteChance) {
                        player.resetAnimation();
                        World.startEvent(worldEvent -> {
                            if (rock != null) {
                                rock.setId(emptyId);
                                worldEvent.delay(rockData.respawnTime);
                                if (rock.id == emptyId) {
                                    rock.setId(rock.originalId);
                                }
                            } else if (npc != null) {
                                npc.remove();
                            }
                        });
                        return;
                    }
                } else if (rockData.depleteTime > 0 && rock != null) {
                    TimedRock.pingIfActive(rock);
                }

                if (attempts++ % 4 == 0)
                    player.animate(miningAnimation);

                event.delay(1);
            }
        });
    }

    public static int getEffectiveLevel(Player player) {
        int level = player.getStats().get(StatType.Mining).currentLevel;
        if (player.getPosition().inBounds(MiningGuild.MINERAL_AREA))
            level += 7;
        level += getMiningBoost(player);
        return level;
    }

    private static final int MINING_GLOVES = 21343;
    private static final int SUPERIOR_MINING_GLOVES = 21345;
    private static final int EXPERT_MINING_GLOVES = 21392;

    /**
     * Chance to add 5 health to the rock being mined.
     */
    private static double miningGloves(Player player, Rock rockData) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        if (gloves == null)
            return 0;
        if (gloves.getId() == MINING_GLOVES || gloves.getId() == SUPERIOR_MINING_GLOVES || gloves.getId() == EXPERT_MINING_GLOVES) {
            if (rockData == Rock.COPPER)
                return 0.25;
            if (rockData == Rock.TIN)
                return 0.25;
            if (rockData == Rock.IRON)
                return 0.2;
            if (rockData == Rock.SILVER)
                return 0.2;
            if (rockData == Rock.COAL)
                return 0.2;
            if (rockData == Rock.GOLD)
                return 0.17;
        }
        if (gloves.getId() == SUPERIOR_MINING_GLOVES || gloves.getId() == EXPERT_MINING_GLOVES) {
            if (rockData == Rock.MITHRIL)
                return 0.14;
            if (rockData == Rock.ADAMANT)
                return 0.1;
        }
        if (gloves.getId() == EXPERT_MINING_GLOVES) {
            if (rockData == Rock.RUNE)
                return 0.075;
            if (rockData == Rock.AMETHYST)
                return 0.05;
        }

        return 0;
    }

    private static int getMiningBoost(Player player) {
        int boost = 0;
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if (ring != null && (ring.getId() == 25539 || ring.getId() == 25541 || ring.getId() == 25543 || ring.getId() == 25545))
            boost += 4;
        return boost;
    }

    private static int getExtraOreChance(Player player, Rock rockData) {
        int chance = 0;
        Item chest = player.getEquipment().get(Equipment.SLOT_CHEST);
        int chestId = chest != null ? chest.getId() : 0;
        if (rockData.ordinal() <= Rock.GOLD.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_1)
                chance += 10;
        }
        if (rockData.ordinal() <= Rock.MITHRIL.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_2)
                chance += 10;
        }
        if (rockData.ordinal() <= Rock.ADAMANT.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_3)
                chance += 10;
            if (MiningSkillCape.wearsMiningCape(player))
                chance += 5;
            if (CelestialRing.wearingChargedRing(player)) {
                chance += 10;
            }
        }
        if (rockData.ordinal() <= Rock.RUNE.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_4)
                chance += 10;
        }
        return chance;
    }

    private static boolean minedMineral(Player player, Rock rockData) {
        if (!player.miningGuildMinerals)
            return false;
        if (!player.getPosition().inBounds(MiningGuild.MINERAL_AREA))
            return false;
        if (rockData == Rock.IRON)
            return Random.rollDie(100, 1);
        if (rockData == Rock.COAL)
            return Random.rollDie(60, 1);
        if (rockData == Rock.MITHRIL)
            return Random.rollDie(40, 1);
        if (rockData == Rock.ADAMANT)
            return Random.rollDie(30, 1);
        if (rockData == Rock.RUNE || rockData == Rock.AMETHYST)
            return Random.rollDie(20, 1);
        return false;
    }

    private static void prospect(Rock rockData, Player player, Boolean crystals) {
        player.startEvent(event -> {
            player.lock();
            player.sendMessage(crystals ? "You inspect the crystals..." : "You examine the rock for ores...");
            event.delay(4);
            player.getStats().addXp(StatType.Mining, 1, false);
            player.sendMessage(crystals ? "These crystals are made up of amethyst." : "This rock contains " + (rockData == Rock.GEM_ROCK ? "gems" : rockData.rockName) + ".");
            player.unlock();
        });
    }

    private static boolean isSalt(Rock rockdata) {
        return rockdata == Rock.TE_SALT || rockdata == Rock.URT_SALT || rockdata == Rock.EFH_SALT;
    }

    private static int getSaltAmount(Player player) {
        int level = player.getStats().get(StatType.Mining).currentLevel;
        if (level < 81)
            return Random.get(2, 4);
        else if (level < 90)
            return Random.get(3, 5);
        else if (level < 99)
            return Random.get(4, 6);
        else
            return Random.get(5, 7);
    }

    private static Boolean infernalPickProc(Player player, int ore) {
        switch (ore) {
            case Items.COPPER_ORE:
            case Items.TIN_ORE:
                player.getStats().addXp(StatType.Smithing, 3.6, true);
                return true;
            case Items.IRON_ORE:
                player.getStats().addXp(StatType.Smithing, 6.25, true);
                return true;
            case Items.SILVER_ORE:
                player.getStats().addXp(StatType.Smithing, 7.8, true);
                return true;
            case Items.SANDSTONE_1KG:
            case Items.SANDSTONE_2KG:
            case Items.SANDSTONE_5KG:
            case Items.SANDSTONE_10KG:
            case Items.GRANITE_500G:
            case Items.GRANITE_2KG:
            case Items.GRANITE_5KG:
                player.getStats().addXp(StatType.Smithing, 8.0, true);
                return true;
            case Items.GOLD_ORE:
                player.getStats().addXp(StatType.Smithing, 9.0, true);
                return true;
            case Items.MITHRIL_ORE:
                player.getStats().addXp(StatType.Smithing, 12.0, true);
                return true;
            case Items.ADAMANTITE_ORE:
                player.getStats().addXp(StatType.Smithing, 18.75, true);
                return true;
            case Items.RUNITE_ORE:
                player.getStats().addXp(StatType.Smithing, 25, true);
                return true;
            default:
                return false;
        }
    }

    public static boolean canAttempt(int cycle, Pickaxe pickaxe) {
        int pickaxeTicks = pickaxe.ticks;
        if (cycle % pickaxeTicks == 0) return true;
        if (pickaxe == Pickaxe.CRYSTAL) return Random.rollDie(4) && cycle % pickaxeTicks - 1 == 0;
        return pickaxe.ordinal() >= 7 && Random.rollDie(6) && cycle % pickaxeTicks - 1 == 0;
    }

    private static boolean wearingGlory(Player player) {
        return player.getEquipment().hasAtLeastOneOf(
                Items.AMULET_OF_ETERNAL_GLORY,
                Items.AMULET_OF_GLORY_1, Items.AMULET_OF_GLORY_2, Items.AMULET_OF_GLORY_3, Items.AMULET_OF_GLORY_4, Items.AMULET_OF_GLORY_5, Items.AMULET_OF_GLORY_6,
                Items.AMULET_OF_GLORY_T1, Items.AMULET_OF_GLORY_T2, Items.AMULET_OF_GLORY_T3, Items.AMULET_OF_GLORY_T4, Items.AMULET_OF_GLORY_T5, Items.AMULET_OF_GLORY_T6);
    }

    public static double chance(Player player, int level, Rock type) {
        int levelDifference = level - type.levelReq;
        double baseChance = type.baseChance;
        double levelBonus = type.levelSlope * levelDifference;
        double chance = baseChance + levelBonus;
        if (type == Rock.GEM_ROCK && wearingGlory(player)) {
            chance *= 2;
        }
        return chance;
    }

    static {
        Object[][] oreData = {
                //rock, baseId, emptyId
                {Rock.BLURITE, 11378, 11390, PlayerCounter.MINED_COPPER},
                {Rock.COPPER, 11161, 11390, PlayerCounter.MINED_COPPER},
                {Rock.COPPER, 10943, 11391, PlayerCounter.MINED_COPPER},
                {Rock.TIN, 11360, 11390, PlayerCounter.MINED_TIN},
                {Rock.TIN, 11361, 11391, PlayerCounter.MINED_TIN},
                {Rock.CLAY, 11362, 11390, PlayerCounter.MINED_CLAY},
                {Rock.CLAY, 11363, 11391, PlayerCounter.MINED_CLAY},
                {Rock.IRON, 11364, 11390, PlayerCounter.MINED_IRON},
                {Rock.IRON, 11365, 11391, PlayerCounter.MINED_IRON},
                {Rock.COAL, 11366, 11391, PlayerCounter.MINED_COAL},
                {Rock.COAL, 11367, 11390, PlayerCounter.MINED_COAL},
                {Rock.SILVER, 11368, 11390, PlayerCounter.MINED_SILVER},
                {Rock.SILVER, 11369, 11391, PlayerCounter.MINED_SILVER},
                {Rock.GOLD, 11370, 11390, PlayerCounter.MINED_GOLD},
                {Rock.GOLD, 11371, 11391, PlayerCounter.MINED_GOLD},
                {Rock.MITHRIL, 11372, 11390, PlayerCounter.MINED_MITHRIL},
                {Rock.MITHRIL, 11373, 11391, PlayerCounter.MINED_MITHRIL},
                {Rock.ADAMANT, 11374, 11390, PlayerCounter.MINED_ADAMANT},
                {Rock.ADAMANT, 11375, 11391, PlayerCounter.MINED_ADAMANT},
                {Rock.RUNE, 11376, 11390, PlayerCounter.MINED_RUNITE},
                {Rock.RUNE, 11377, 11391, PlayerCounter.MINED_RUNITE},
                {Rock.LOVAKITE, 28596, 11390, PlayerCounter.MINED_LOVAKITE},
                {Rock.LOVAKITE, 28597, 11391, PlayerCounter.MINED_LOVAKITE},
                {Rock.SANDSTONE, 11386, 11391, PlayerCounter.MINED_SANDSTONE},
                {Rock.GRANITE, 11387, 11390, PlayerCounter.MINED_GRANITE},
                {Rock.GEM_ROCK, 11380, 11390, PlayerCounter.MINED_GEM_ROCK},
                {Rock.GEM_ROCK, 11381, 11391, PlayerCounter.MINED_GEM_ROCK},
                {Rock.TE_SALT, 33256, 33253, PlayerCounter.MINE_SALT},
                {Rock.EFH_SALT, 33255, 33253, PlayerCounter.MINE_SALT},
                {Rock.URT_SALT, 33254, 33253, PlayerCounter.MINE_SALT},
                {Rock.BASALT, 33257, 33253, PlayerCounter.MINE_SALT},
                {Rock.LIMESTONE, 11382, 11383, PlayerCounter.MINED_LIMESTONE},
                {Rock.LIMESTONE, 11383, 11384, PlayerCounter.MINED_LIMESTONE},
                {Rock.LIMESTONE, 11384, 11385, PlayerCounter.MINED_LIMESTONE}
        };
        for (Object[] d : oreData) {
            Rock rock = (Rock) d[0];
            int baseId = (Integer) d[1];
            int emptyId = (Integer) d[2];
            PlayerCounter counter = (PlayerCounter) d[3];
            ObjectAction.register(baseId, "mine", (player, obj) -> mine(rock, player, obj, emptyId, counter, null));
            ObjectAction.register(baseId, "prospect", (player, obj) -> prospect(rock, player, false));
        }
        int[] emptyOreIds = {11390, 11391, 11385};
        for (int id : emptyOreIds) {
            ObjectAction.register(id, "mine", (player, obj) -> player.sendMessage("There is no ore currently available in this rock."));
            ObjectAction.register(id, "prospect", (player, obj) -> player.sendMessage("There is no ore currently available in this rock."));
        }

        /**
         * Crystals
         */
        Object[][] crystals = {
                {Rock.AMETHYST, 11389, 11393, PlayerCounter.MINED_AMETHYST},
                {Rock.AMETHYST, 11388, 11393, PlayerCounter.MINED_AMETHYST}
        };
        for (Object[] c : crystals) {
            Rock rock = (Rock) c[0];
            int baseId = (Integer) c[1];
            int emptyId = (Integer) c[2];
            PlayerCounter counter = (PlayerCounter) c[3];
            ObjectAction.register(baseId, "mine", (player, obj) -> mine(rock, player, obj, emptyId, counter, null));
            ObjectAction.register(baseId, "prospect", (player, obj) -> prospect(rock, player, true));
        }
        int[] emptyCrystals = {11393};
        for (int id : emptyCrystals) {
            ObjectAction.register(id, "mine", (player, obj) -> player.sendMessage("There is no ore currently available in this wall."));
            ObjectAction.register(id, "prospect", (player, obj) -> player.sendMessage("There is no ore currently available in this wall."));
        }

        /**
         * Gem rocks
         */
        Object[][] gemRock = {
                {Rock.GEM_ROCK, 7463, 11390, PlayerCounter.MINED_GEM_ROCK},
                {Rock.GEM_ROCK, 7464, 11391, PlayerCounter.MINED_GEM_ROCK}
        };
        for (Object[] g : gemRock) {
            Rock rock = (Rock) g[0];
            int baseId = (Integer) g[1];
            int emptyId = (Integer) g[2];
            PlayerCounter counter = (PlayerCounter) g[3];
            ObjectAction.register(baseId, "mine", (player, obj) -> mine(rock, player, obj, emptyId, counter, null));
            ObjectAction.register(baseId, "prospect", (player, obj) -> prospect(rock, player, false));
        }

        /**
         * Runite golem
         */
        NPCAction.register(6601, "mine", (player, npc) -> mine(Rock.RUNE, player, null, -1, PlayerCounter.MINED_RUNITE, npc));
        NPCAction.register(6601, "prospect", (player, npc) -> prospect(Rock.RUNE, player, false));
    }

}