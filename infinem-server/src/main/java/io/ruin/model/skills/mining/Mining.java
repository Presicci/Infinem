package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.Geode;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.skillcapes.MiningSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
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

        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more " + rockData.rockName + ".");
            return;
        }

        player.startEvent(event -> {
            int attempts = 0;
            while (true) {
                if ((rock != null && rock.id == emptyId)
                        || (npc != null && npc.isRemoved())) {
                    player.resetAnimation();
                    return;
                }

                if (player.getInventory().isFull()) {
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
                    if (rockData.multiOre != null)
                        itemId = rockData.multiOre[Random.get(rockData.multiOre.length - 1)];
                    gemRock = true;
                }

                Item gem = null;
                Boolean multiple = false;
                final int miningAnimation = rockData == Rock.AMETHYST ? pickaxe.crystalAnimationID : pickaxe.regularAnimationID;
                if (attempts == 0) {
                    player.sendFilteredMessage("You swing your pick at the rock.");
                    player.animate(miningAnimation);
                    attempts++;
                } else if (attempts % 2 == 0 && Random.get(100) <= chance(getEffectiveLevel(player), rockData, pickaxe)) {
                    if (pickaxe == Pickaxe.INFERNAL && Random.rollDie(3, 1)) {//TODO: change back to bar smelting when charge consuming is added
                        player.graphics(580, 155, 0);
                        addBar(player, rockData.ore);
                        //TODO: Get the proper message and take away charge
                    } else if (rockData != Rock.GEM_ROCK && Random.rollDie(256)) {  // 1/256 chance to replace ore with a gem)
                        gem = GEM_TABLE.rollItem();

                        player.collectResource(gem);
                        player.getInventory().add(gem);
                    } else {
                        int id = rockyOutcrop || gemRock ? itemId : rockData.ore;

                        player.collectResource(new Item(id, 1));
                        player.getInventory().add(id, 1);

                        if (MiningSkillCape.wearsMiningCape(player)
                                && rockData.ordinal() <= Rock.ADAMANT.ordinal()
                                && Random.rollPercent(5)) {
                            player.getInventory().add(id, 1);
                            player.sendFilteredMessage("You manage to mine an additional ore.");
                            multiple = true;
                        }
                    }

                    if (Random.rollDie(rockData.petOdds - (player.getStats().get(StatType.Mining).currentLevel * 25)))
                        Pet.ROCK_GOLEM.unlock(player);

                    /* Rolling for a Geode clue scroll */
                    if (Random.rollDie(250, 1)) {
                        player.getInventory().addOrDrop(Geode.getRandomGeode(), 1);
                        PlayerCounter.MINED_GEODE.increment(player, 1);
                        player.getTaskManager().doLookupByUUID(102, 1);  // Obtain a Clue Geode While Mining
                    }

                    /* Rolling for mined minerals */
                    if (minedMineral(player, rockData))
                        player.getInventory().addOrDrop(21341, 1);

                    counter.increment(player, 1);
                    if (rockData == Rock.GEM_ROCK) {
                        player.getStats().addXp(StatType.Mining, rockData.experience * xpBonus(player, false), true);
                    } else if (gem != null) {   // No xp is earned and the ore is not depleted, just go next
                        player.sendFilteredMessage("You find an " + gem.getDef().name + ".");
                        player.getTaskManager().doLookupByUUID(24, 1);  // Obtain a Gem While Mining
                        continue;
                    } else {
                        player.getStats().addXp(StatType.Mining, rockyOutcrop ? rockData.multiExp[random] : rockData.experience * xpBonus(player, multiple), true);
                    }
                    player.sendFilteredMessage("You manage to mine " + (rockData == Rock.GEM_ROCK ? "a " : "some ") +
                            (rockData == Rock.GEM_ROCK ? ItemDef.get(itemId).name.toLowerCase() : rockData.rockName) + ".");
                    player.getTaskManager().doSkillItemLookup(itemId);
                    if (pickaxe == Pickaxe.STEEL)
                        player.getTaskManager().doLookupByUUID(23, 1);  // Mine some Ore With a Steel Pickaxe
                    if (pickaxe == Pickaxe.RUNE)
                        player.getTaskManager().doLookupByUUID(101, 1);  // Mine some Ore With a Rune Pickaxe

                    /* Rolling for rock depletion */
                    double depleteChance = rockData.depleteChance * (1 - miningGloves(player, rockData));
                    if (Random.get() < depleteChance) {
                        player.resetAnimation();
                        World.startEvent(worldEvent -> {
                            if (rock != null) {
                                rock.setId(emptyId);
                                worldEvent.delay(rockData.respawnTime);
                                rock.setId(rock.originalId);
                            } else if (npc != null) {
                                npc.remove();
                            }
                        });
                        return;
                    }
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
        return level;
    }

    private static final int MINING_GLOVES = 21343;
    private static final int SUPERIOR_MINING_GLOVES = 21345;
    private static final int EXPERT_MINING_GLOVES = 21392;

    private static double miningGloves(Player player, Rock rockData) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        if (gloves == null)
            return 0;

        if (gloves.getId() == MINING_GLOVES || gloves.getId() == EXPERT_MINING_GLOVES) {
            if (rockData == Rock.SILVER)
                return 0.33;
            if (rockData == Rock.COAL)
                return 0.5;
            if (rockData == Rock.GOLD)
                return 0.33;
        }

        if (gloves.getId() == SUPERIOR_MINING_GLOVES || gloves.getId() == EXPERT_MINING_GLOVES) {
            if (rockData == Rock.MITHRIL)
                return 0.33;
            if (rockData == Rock.ADAMANT)
                return 0.25;
            if (rockData == Rock.RUNE)
                return 0.15;
        }

        return 0;
    }

    private static boolean minedMineral(Player player, Rock rockData) {
        if (!player.miningGuildMinerals)
            return false;
        if (!player.getPosition().inBounds(MiningGuild.MINERAL_AREA))
            return false;

        if (rockData == Rock.IRON)
            return Random.rollDie(150, 1);
        if (rockData == Rock.COAL)
            return Random.rollDie(90, 1);
        if (rockData == Rock.MITHRIL)
            return Random.rollDie(60, 1);
        if (rockData == Rock.ADAMANT)
            return Random.rollDie(45, 1);
        if (rockData == Rock.RUNE || rockData == Rock.AMETHYST)
            return Random.rollDie(30, 1);

        return false;
    }

    private static void prospect(Rock rockData, Player player, Boolean crystals) {
        player.startEvent(event -> {
            player.lock();
            player.sendMessage(crystals ? "You inspect the crystals..." : "You examine the rock for ores...");
            event.delay(4);
            player.sendMessage(crystals ? "These crystals are made up of amethyst." : "This rock contains " + (rockData == Rock.GEM_ROCK ? "gems" : rockData.rockName) + ".");
            player.unlock();
        });
    }

    public static double xpBonus(Player player, Boolean multiple) {
        double multiplier = 1;
        multiplier *= prospectorBonus(player);
        if (multiple)
            multiplier *= 2;
        return multiplier;
    }

    public static double prospectorBonus(Player player) {
        double bonus = 1.0;
        Item helmet = player.getEquipment().get(Equipment.SLOT_HAT);
        Item jacket = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item legs = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (helmet != null && helmet.getId() == 12013)
            bonus += 0.4;
        if (jacket != null && jacket.getId() == 12014)
            bonus += 0.8;
        if (legs != null && legs.getId() == 12015)
            bonus += 0.6;
        if (boots != null && boots.getId() == 12016)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

    private static Boolean addBar(Player player, int ore) { // TODO: consider changing back to actually smelting bars when charge consuming is added
        switch (ore) {
            case 436:
            case 438:
//                player.getInventory().add(2349, 1);
                player.getStats().addXp(StatType.Smithing, 2.5, true);
                return true;
            case 440:
//                player.getInventory().add(2351, 1);
                player.getStats().addXp(StatType.Smithing, 5.0, true);
                return true;
            case 442:
//                player.getInventory().add(2355, 1);
                player.getStats().addXp(StatType.Smithing, 5.5, true);
                return true;
            case 444:
//                player.getInventory().add(2357, 1);
                player.getStats().addXp(StatType.Smithing, 9.0, true);
                return true;
            case 447:
//                player.getInventory().add(2359, 1);
                player.getStats().addXp(StatType.Smithing, 12.0, true);
                return true;
            case 449:
//                player.getInventory().add(2361, 1);
                player.getStats().addXp(StatType.Smithing, 15.0, true);
                return true;
            case 451:
//                player.getInventory().add(2362, 1);
                player.getStats().addXp(StatType.Smithing, 20.0, true);
                return true;
            default:
                return false;
        }
    }

    public static double chance(int level, Rock type, Pickaxe pickaxe) {
        double points = ((level - type.levelReq) + 1 + (double) pickaxe.points);
        double denominator = (double) type.difficulty;
        return (Math.min(0.95, points / denominator) * 100);
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
                {Rock.SANDSTONE, 11387, 11390, PlayerCounter.MINED_SANDSTONE},
                {Rock.GRANITE, 11386, 11391, PlayerCounter.MINED_GRANITE},
                {Rock.GEM_ROCK, 11380, 11390, PlayerCounter.MINED_GEM_ROCK},
                {Rock.GEM_ROCK, 11381, 11391, PlayerCounter.MINED_GEM_ROCK},
                {Rock.CORRUPTED, 50006, 50007, PlayerCounter.MINED_CORRUPT},
        };
        for (Object[] d : oreData) {
            Rock rock = (Rock) d[0];
            int baseId = (Integer) d[1];
            int emptyId = (Integer) d[2];
            PlayerCounter counter = (PlayerCounter) d[3];
            ObjectAction.register(baseId, "mine", (player, obj) -> mine(rock, player, obj, emptyId, counter, null));
            ObjectAction.register(baseId, "prospect", (player, obj) -> prospect(rock, player, false));
        }
        int[] emptyOreIds = {11390, 11391};
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