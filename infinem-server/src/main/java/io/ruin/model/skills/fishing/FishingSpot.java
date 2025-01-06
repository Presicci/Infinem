package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.content.tasksystem.areas.diaryitems.RadasBlessing;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentModifier;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentModifierEffects;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentType;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.chargable.CrystalEquipment;
import io.ruin.model.item.actions.impl.chargable.InfernalTools;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.loot.RareDropTable;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.MapArea;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.cooking.Food;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class FishingSpot {

    private final FishingTool defaultTool;

    private FishingCatch[] regularCatches, barehandCatches;

    private LootTable catchTable;

    private FishingSpot(FishingTool tool) {
        this.defaultTool = tool;
    }

    private FishingSpot catchTable(LootTable table) {
        this.catchTable = table;
        return this;
    }

    private FishingSpot regularCatches(FishingCatch... regularCatches) {
        this.regularCatches = regularCatches;
        return this;
    }

    private FishingSpot barehandCatches(FishingCatch... barehandCatches) {
        this.barehandCatches = barehandCatches;
        return this;
    }

    private FishingCatch randomCatch(Player player, int level, boolean barehand, FishingTool tool) {
        FishingCatch[] catches = barehand ? barehandCatches : regularCatches;
        return randomCatch(player, level, tool, catches);
    }

    private FishingCatch randomCatch(Player player, int level, FishingTool tool, FishingCatch[] catches) {
        for (int i = catches.length - 1; i >= 0; i--) {
            FishingCatch c = catches[i];
            if (c == FishingCatch.LEAPING_SALMON && (player.getStats().get(StatType.Strength).currentLevel < 30 || player.getStats().get(StatType.Agility).currentLevel < 30)) {
                continue;
            } else if (c == FishingCatch.LEAPING_STURGEON && (player.getStats().get(StatType.Strength).currentLevel < 45 || player.getStats().get(StatType.Agility).currentLevel < 45)) {
                continue;
            }
            if (!rollCatch(player, level, c, tool)) continue;
            return c;
        }
        return null;
    }

    private boolean rollCatch(Player player, int level, FishingCatch c, FishingTool tool) {
        double chance = c.baseChance;
        int levelDifference = level - c.levelReq;
        double roll = Random.get();
        if (levelDifference < 0) {
            /* not high enough level */
            return false;
        }
        if (chance >= 1.0) {
            /* always catch this bad boy */
            return true;
        }
        if (tool == FishingTool.DRAGON_HARPOON || tool == FishingTool.INFERNAL_HARPOON)
            chance *= 1.20;
        if (tool == FishingTool.CRYSTAL_HARPOON) {
            if (CrystalEquipment.HARPOON.hasCharge(player))
                chance *= 1.35;
            else    // No charge, default to dragon bonus
                chance *= 1.20;
        }
        chance += (double) levelDifference * 0.003;
        return !(roll > Math.min(chance, 0.90));
    }

    private int getLevelWithBoost(Player player, Stat fishing) {
        int level = fishing.currentLevel;
        if (MapArea.FISHING_GUILD.inArea(player))
            level += 7;
        return level;
    }

    private static final List<Integer> BARBARIAN_BAITS = Arrays.asList(Items.FISH_OFFCUTS, Items.FISHING_BAIT, Items.FEATHER, Items.ROE, Items.CAVIAR);

    private void fish(Player player, NPC npc) {
        boolean skipBait = player.getRelicFragmentManager().hasModifier(FragmentType.Fishing, FragmentModifier.IGNORE_BAIT);
        boolean barehand;
        FishingTool tool = FishingTool.getAlternative(player, defaultTool);
        Stat fishing = player.getStats().get(StatType.Fishing);
        if (tool == FishingTool.DRAGON_HARPOON && fishing.currentLevel < 61) {
            player.sendMessage("You need a Fishing level of at least 61 to fish with a dragon harpoon.");
            return;
        }
        if (tool == FishingTool.INFERNAL_HARPOON && fishing.currentLevel < 75) {
            player.sendMessage("You need a Fishing level of at least 75 to fish with a infernal harpoon.");
            return;
        }
        if (tool == FishingTool.CRYSTAL_HARPOON && fishing.currentLevel < 71) {
            player.sendMessage("You need a Fishing level of at least 71 to fish with a crystal harpoon.");
            return;
        }
        if (tool == FishingTool.BARB_TAIL_HARPOON && player.getStats().get(StatType.Hunter).currentLevel < 33) {
            player.sendMessage("You need a Hunter level of at least 33 to fish with a barb-tail harpoon.");
            return;
        }
        RandomEvent.attemptTrigger(player, 5, 1.3D);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (player.getInventory().contains(tool.id, 1, false, true) || (weapon != null && weapon.getId() == tool.id)) {
            FishingCatch lowestCatch = regularCatches[0];

            if (fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to fish at this spot.");
                return;
            }
            if (defaultTool == FishingTool.BARBARIAN_ROD && (player.getStats().get(StatType.Strength).currentLevel < 15 || player.getStats().get(StatType.Agility).currentLevel < 15)) {
                player.sendMessage("You need a Strength and Agility level of 15 to fish at this spot.");
                return;
            }

            barehand = false;
        } else {
            if (barehandCatches == null) {
                player.sendMessage("You need a " + tool.primaryName + " to fish at this spot.");
                return;
            }

            FishingCatch lowestCatch = barehandCatches[0];

            if (fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if (player.getStats().get(StatType.Agility).currentLevel < lowestCatch.agilityReq) {
                player.sendMessage("You need an Agility level of at least " + lowestCatch.agilityReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if (player.getStats().get(StatType.Strength).currentLevel < lowestCatch.strengthReq) {
                player.sendMessage("You need a Strength level of at least " + lowestCatch.strengthReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            barehand = true;
        }

        Item secondary = null;
        if (barehand || tool.secondaryId == -1) {
            secondary = null;
        } else if (defaultTool == FishingTool.BARBARIAN_ROD) {
            Item bait = null;
            for (int id : BARBARIAN_BAITS) {
                bait = player.getInventory().findItem(id);
                if (bait != null) {
                    secondary = bait;
                    break;
                }
            }
            if (secondary == null && !skipBait) {
                player.sendMessage("You need at least one bait to fish at this spot.");
                return;
            }
        } else if ((secondary = player.getInventory().findItem(tool.secondaryId)) == null && !skipBait) {
            player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
            return;
        }
        boolean endlessHarvest = player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST);
        if (!endlessHarvest && player.getInventory().isFull() && npc.getId() != KARAMBWANJI) {
            player.sendMessage("Your inventory is too full to hold any fish.");
            return;
        }

        if (npc.getId() == INFERNO_EEL) {
            if (!player.getEquipment().hasId(1580)) {
                player.sendMessage("You need to be wearing ice gloves to catch infernal eel.");
                return;
            }
        }

        Item finalSecondary = skipBait ? null : secondary;

        /*
         * Start event
         */
        player.animate(barehand ? 6703 : tool.startAnimationId);
        player.startEvent(event -> {
            int animTicks = 2;
            boolean firstBarehandAnim = true;
            int lastLevel = fishing.currentLevel;
            int level = getLevelWithBoost(player, fishing);
            Item bait = finalSecondary;
            while (true) {
                if (animTicks > 0) { //we do this so we can check if the npc has moved every tick
                    int diffX = Math.abs(player.getAbsX() - npc.getAbsX());
                    int diffY = Math.abs(player.getAbsY() - npc.getAbsY());
                    if (diffX + diffY > 1) {
                        player.resetAnimation();
                        return;
                    }

                    event.delay(1);
                    animTicks--;
                    continue;
                }
                if (lastLevel != fishing.currentLevel)
                    level = getLevelWithBoost(player, fishing);
                FishingCatch c = randomCatch(player, level, barehand, tool);
                if (c != null) {
                    if (npc.getId() == MINNOWS && (npc.minnowsFish || (npc.minnowsFish = Random.rollDie(100)))) {
                        npc.graphics(1387);
                        player.getInventory().remove(c.id, 26);
                        player.sendFilteredMessage("A flying fish jumps up and eats some of your minnows!");
                    } else {
                        if (bait != null)
                            bait.incrementAmount(-1);
                        if (npc.getId() == MINNOWS)
                            player.sendFilteredMessage("You catch some minnows!");
                        if (npc.getId() == INFERNO_EEL)
                            player.sendFilteredMessage("You catch an infernal eel. It hardens as you handle it with your ice gloves.");
                        Food cookedFish = null;

                        // ID transforms
                        int id = c.id;
                        if (player.getRelicFragmentManager().rollChanceModifier(FragmentType.Fishing, FragmentModifier.COOK_FISH) && Food.FISH_BY_RAW_ID.containsKey(c.id)) {
                            cookedFish = Food.FISH_BY_RAW_ID.get(c.id);
                            id = cookedFish.cookedID;
                        }

                        // Amount handling
                        int amount = npc.getId() == MINNOWS ? Random.get(10, 26)
                                : id == FishingCatch.KARAMBWANJI.id ? ((fishing.currentLevel / 5) + 1)
                                : 1;
                        if (RadasBlessing.extraFish(player)) amount += 1;
                        if (endlessHarvest) amount *= 2;

                        // Catch tables - big net fishing etc.
                        if (catchTable != null) {
                            Item item = catchTable.rollItem();
                            if (item != null && item.getId() != -1) {
                                id = item.getId();
                                amount = item.getAmount();
                            }
                        }

                        // Catch handling
                        player.collectResource(new Item(id, amount));
                        if (tool == FishingTool.CRYSTAL_HARPOON && CrystalEquipment.HARPOON.hasCharge(player))
                            CrystalEquipment.HARPOON.removeCharge(player);
                        if (tool == FishingTool.INFERNAL_HARPOON && InfernalTools.INFERNAL_HARPOON.hasCharge(player) && Random.rollDie(3, 1) && Food.COOKING_EXPERIENCE.containsKey(id)) {
                            player.graphics(580, 155, 0);
                            InfernalTools.INFERNAL_HARPOON.removeCharge(player);
                            player.getStats().addXp(StatType.Fishing, c.xp, true);
                            player.getStats().addXp(StatType.Cooking, Food.COOKING_EXPERIENCE.get(id) / 2, true);
                            player.sendMessage("Your infernal harpoon incinerates the " + c.name() + ".");
                        } else if (endlessHarvest || player.getRelicFragmentManager().rollChanceModifier(FragmentType.Fishing, FragmentModifier.BANK_RESOURCES)) {
                            if (player.getBank().hasRoomFor(id)) {
                                player.getBank().add(id, amount);
                                if (endlessHarvest) player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(id).name + " you would have gained, giving you a total of " + player.getBank().getAmount(id) + ".");
                                else player.sendFilteredMessage("You bank the " + ItemDefinition.get(id).name + " you would have gained, giving you a total of " + player.getBank().getAmount(id) + ".");
                            } else {
                                player.getInventory().addOrDrop(id, amount);
                            }
                            player.getStats().addXp(StatType.Fishing, c.xp * 2, true);
                            if (cookedFish != null) player.getStats().addXp(StatType.Cooking, cookedFish.experience * 2, true);
                        } else {
                            player.getInventory().addOrDrop(id, amount);
                            player.getStats().addXp(StatType.Fishing, c.xp, true);
                            if (cookedFish != null) player.getStats().addXp(StatType.Cooking, cookedFish.experience, true);
                        }

                        // Counter handling
                        if (npc.getId() != MINNOWS) PlayerCounter.TOTAL_FISH.increment(player, amount);

                        // Task handling
                        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.FISHCATCH, ItemDefinition.get(id).name, amount, true);

                        // Roll clue bottles
                        FishingClueBottle.roll(player, c, barehand);

                        // Award barbarian fishing experience
                        if (c.barbarianXp > 0) {
                            player.getStats().addXp(StatType.Agility, c.barbarianXp, true);
                            player.getStats().addXp(StatType.Strength, c.barbarianXp, true);
                            if (barehand) {
                                if (c == FishingCatch.BARBARIAN_TUNA)
                                    player.animate(firstBarehandAnim ? 6710 : 6711);
                                else if (c == FishingCatch.BARBARIAN_SWORDFISH)
                                    player.animate(firstBarehandAnim ? 6707 : 6708);
                                else if (c == FishingCatch.BARBARIAN_SHARK)
                                    player.animate(firstBarehandAnim ? 6705 : 6706);
                                firstBarehandAnim = !firstBarehandAnim;
                                animTicks = 8;
                            }
                        }
                    }

                    // Roll experience lamps if player has fragment with mod
                    if (player.getRelicFragmentManager().rollChanceModifier(FragmentType.Fishing, FragmentModifier.EXPERIENCE_LAMP))
                        FragmentModifierEffects.rewardExperienceLamp(player);

                    // Roll RDT if player has fragment with mod
                    RareDropTable.rollSkillingRareDropTable(player, FragmentType.Fishing, c.levelReq);

                    // Roll pet
                    if (Random.rollDie(c.petOdds - (player.getStats().get(StatType.Fishing).currentLevel * 25)))
                        Pet.HERON.unlock(player);

                    // If inventory is full, reset action
                    if (!endlessHarvest && player.getInventory().isFull() && npc.getId() != KARAMBWANJI) {
                        player.sendMessage("Your inventory is too full to hold any more fish.");
                        player.resetAnimation();
                        return;
                    }

                    // Check if still have required bait
                    if (bait != null) {
                        if (defaultTool == FishingTool.BARBARIAN_ROD) {
                            Item findBait = null;
                            for (int id : BARBARIAN_BAITS) {
                                findBait = player.getInventory().findItem(id);
                                if (findBait != null) {
                                    bait = findBait;
                                    break;
                                }
                            }
                            if (findBait == null) {
                                player.sendMessage("You need at least one bait to fish at this spot.");
                                return;
                            }
                        } else {
                            Item requiredSecondary = player.getInventory().findItem(bait.getId());
                            if (requiredSecondary == null) {
                                player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
                                return;
                            }
                        }
                    }
                }

                if (animTicks == 0) {
                    player.animate(barehand ? 6704 : tool.loopAnimationId);
                    animTicks = 5 - (int) player.getRelicFragmentManager().getModifierValue(StatType.Fishing, FragmentModifier.TICK_FASTER);
                }
            }
        });
    }

    private void register(int npcId, String option) {
        NPCAction.register(npcId, option, this::fish);
    }

    /**
     * 14036 - Lure/bait
     * 14037 - Big net/harpoon
     * 14038 - Small net/bait
     * 14039 - Cage/harpoon
     * 14040 - Small net/bait
     * 14041 - Small net/bait
     */

    public static final int NET_BAIT = 1518;            //shrimps,anchovies / sardine,herring
    public static final int NEW_NET_BAIT = 14038;            //shrimps,anchovies / sardine,herring
    public static final int LURE_BAIT = 1508;           //trout,salmon / pike
    public static final int NEW_LURE_BAIT = 14036;           //trout,salmon / pike
    public static final int CAGE_HARPOON = 1519;        //lobster / tuna,swordfish
    public static final int NEW_CAGE_HARPOON = 14039;        //lobster / tuna,swordfish
    public static final int BIG_NET_HARPOON = 1520;     //shark / tuna,swordfish
    public static final int NEW_BIG_NET_HARPOON = 14037;     //shark / tuna,swordfish
    public static final int SMALL_NET_HARPOON = 4316;   //monkfish / swordfish
    public static final int USE_ROD = 1542;             //leaping
    public static final int CAGE = 1535;                //dark crab
    public static final int BAIT = 6825;                //angler
    private static final int MINNOWS = 7731;            //minnows
    public static final int INFERNO_EEL = 7676;        //infernal eel
    public static final int KARAMBWAN_SPOT = 4712;
    public static final int KARAMBWANJI = 4710;
    public static final int MOLTEN_EEL = 15018;
    public static final int LAVA_EEL = 4928;
    public static final int SLIMY_EEL = 2653;
    public static final int SACRED_EEL = 6488;
    public static final int SWAMP_NET_BAIT = 1497;
    public static final int HEMENSTER = 4079;
    public static final int CIVITAS_ILLA_FORTIS = 13329;

    static {
        new FishingSpot(FishingTool.KARAMBWAN_VESSEL)
                .regularCatches(FishingCatch.KARAMBWAN)
                .register(KARAMBWAN_SPOT, "fish");
        /*
         * Net / Bait
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT, "bait");
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NEW_NET_BAIT, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NEW_NET_BAIT, "bait");
        /*
         * Lure / Bait
         */
        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT, "bait");
        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(NEW_LURE_BAIT, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(NEW_LURE_BAIT, "bait");
        /*
         * Cage / Harpoon
         */
        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON, "harpoon");
        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(NEW_CAGE_HARPOON, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(NEW_CAGE_HARPOON, "harpoon");
        /*
         * Net (big) / Harpoon
         */
        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .catchTable(FishingTables.BIG_NET)
                .register(BIG_NET_HARPOON, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON, "harpoon");
        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .catchTable(FishingTables.BIG_NET)
                .register(NEW_BIG_NET_HARPOON, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(NEW_BIG_NET_HARPOON, "harpoon");
        /*
         * Net (small) / Harpoon
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON, "harpoon");
        /*
         * Swamp Net (small) / Bait
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.FROG_SPAWN, FishingCatch.SWAMP_WEED)
                .register(SWAMP_NET_BAIT, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SLIMY_EEL, FishingCatch.CAVE_EEL)
                .register(SWAMP_NET_BAIT, "bait");
        /*
         * Use-rod (Leaping)
         */
        new FishingSpot(FishingTool.BARBARIAN_ROD)
                .regularCatches(FishingCatch.LEAPING_TROUT, FishingCatch.LEAPING_SALMON, FishingCatch.LEAPING_STURGEON)
                .register(USE_ROD, "use-rod");
        /*
         * Cage (Dark crab)
         */
        new FishingSpot(FishingTool.DARK_CRAB_POT)
                .regularCatches(FishingCatch.DARK_CRAB)
                .register(CAGE, "cage");

        /*
         * Hemenster - Giant carp
         */
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.GIANT_CARP)
                .register(HEMENSTER, "bait");

        /*
         * Bait (Angler)
         */
        new FishingSpot(FishingTool.ANGLER_ROD)
                .regularCatches(FishingCatch.ANGLERFISH)
                .register(BAIT, "bait");
        /*
         * Minnows
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MINNOWS)
                .register(MINNOWS, "small net");
        NPC minnow1 = new NPC(MINNOWS).spawn(2611, 3443, 0);
        NPC minnow2 = new NPC(MINNOWS).spawn(2610, 3444, 0);
        NPC minnow3 = new NPC(MINNOWS).spawn(2618, 3443, 0);
        NPC minnow4 = new NPC(MINNOWS).spawn(2619, 3444, 0);
        World.startEvent(e -> {
            while (true) {
                e.delay(20);
                moveMinnow(minnow1, minnow3);
                e.delay(4);
                moveMinnow(minnow2, minnow4);
            }
        });
        /*
         * Infernal eel
         */
        new FishingSpot(FishingTool.OILY_FISHING_ROD)
                .regularCatches(FishingCatch.INFERNAL_EEL)
                .register(INFERNO_EEL, "bait");
        /*
         * Slimy eel
         */
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SLIMY_EEL)
                .register(SLIMY_EEL, "bait");
        /*
         * Sacred eel
         */
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SACRED_EEL)
                .register(SACRED_EEL, "bait");

        /*
         * Lava eel
         */
        new FishingSpot(FishingTool.OILY_FISHING_ROD)
                .regularCatches(FishingCatch.LAVA_EEL)
                .register(LAVA_EEL, "bait");

        /*
         * Karambwanji
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.KARAMBWANJI)
                .register(KARAMBWANJI, "net");
        /*
         * Civitas illa fortis
         */
        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.CIVITAS_ILLA_FORTIS)
                .catchTable(FishingTables.CIVITAS_ILLA_FORTIS)
                .register(CIVITAS_ILLA_FORTIS, "cast");
    }

    private static void moveMinnow(NPC... minnows) {
        for (NPC minnow : minnows) {
            int x = minnow.getAbsX();
            int y = minnow.getAbsY();

            if (y == 3443) {
                if (x == 2609 || x == 2617)
                    minnow.step(0, 1, StepType.FORCE_WALK);
                else
                    minnow.step(-1, 0, StepType.FORCE_WALK);
            } else {
                if (x == 2612 || x == 2620)
                    minnow.step(0, -1, StepType.FORCE_WALK);
                else
                    minnow.step(1, 0, StepType.FORCE_WALK);
            }

            minnow.minnowsFish = false;
        }
    }
}