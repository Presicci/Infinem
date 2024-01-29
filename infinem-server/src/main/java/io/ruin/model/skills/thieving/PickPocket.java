package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.content.tasksystem.tasks.areas.rewards.KandarinReward;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.jewellery.DodgyNecklace;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.skillcapes.ThievingSkillCape;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.MapArea;
import io.ruin.model.skills.BotPrevention;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

public enum PickPocket {

    MAN(1, 8.0, 422, 5, 1, "man's", PlayerCounter.PICKPOCKETED_MAN,
            257211,
            22521,
            70.70,
            0.2424,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 3, 3, 1)  //Coins
            ), "hengel"),
    WOMAN(1, 8.0, 422, 5, 1, "woman's", PlayerCounter.PICKPOCKETED_MAN,
            257211,
            22521,
            70.70,
            0.2424,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 3, 3, 1)  //Coins
            ), "anja"),
    TOWER_OF_LIFE_NPCS(1, 8.0, 422, 5, 1, "man's", null,
            257211,
            -1,
            70.70,
            02.2424,
            new LootTable().addTable(1,
                    new LootItem(Items.TRIANGLE_SANDWICH, 1, 1)
            ),
            "'black-eye'", "'gummy'", "'no fingers'", "'the guns'"),
    FARMER(10, 14.5, 433, 5, 1, "farmer's", PlayerCounter.PICKPOCKETED_FARMER,
            257211,
            22522,
            60.55,
            0.365,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 9, 9, 24), //Coins
                    new LootItem(5318, 1, 1) //Potato seed
            )),
    HAM(15, 18.5, 433, 4, 1, "H.A.M member's", PlayerCounter.PICKPOCKETED_HAM_MEMBER,
            257211,
            22523,
            58.59,
            0.4232,
            new LootTable().addTable(1,
                    new LootItem(Items.BRONZE_ARROW, 1, 13, 10),
                    new LootItem(Items.BRONZE_AXE, 1, 10),
                    new LootItem(Items.BRONZE_DAGGER, 1, 10),
                    new LootItem(Items.BRONZE_PICKAXE, 1, 10),
                    new LootItem(Items.IRON_AXE, 1, 10),
                    new LootItem(Items.IRON_DAGGER, 1, 10),
                    new LootItem(Items.IRON_PICKAXE, 1, 10),
                    new LootItem(Items.LEATHER_BODY, 1, 10),
                    new LootItem(Items.STEEL_ARROW, 1, 13, 7),
                    new LootItem(Items.STEEL_AXE, 1, 7),
                    new LootItem(Items.STEEL_DAGGER, 1, 7),
                    new LootItem(Items.STEEL_PICKAXE, 1, 7),
                    new LootItem(Items.HAM_BOOTS, 1, 4),
                    new LootItem(Items.HAM_CLOAK, 1, 4),
                    new LootItem(Items.HAM_GLOVES, 1, 4),
                    new LootItem(Items.HAM_HOOD, 1, 4),
                    new LootItem(Items.HAM_LOGO, 1, 4),
                    new LootItem(Items.HAM_ROBE, 1, 4),
                    new LootItem(Items.HAM_SHIRT, 1, 4),
                    new LootItem(Items.COINS, 1, 21, 49),
                    new LootItem(Items.BUTTONS, 1, 13),
                    new LootItem(Items.DAMAGED_ARMOUR, 1, 13),
                    new LootItem(Items.RUSTY_SWORD, 1, 13),
                    new LootItem(Items.FEATHER, 1, 7, 10),
                    new LootItem(Items.LOGS, 1, 10),
                    new LootItem(Items.THREAD, 1, 10, 10),
                    new LootItem(Items.COWHIDE, 1, 10),
                    new LootItem(Items.KNIFE, 1, 7),
                    new LootItem(Items.NEEDLE, 1, 7),
                    new LootItem(Items.RAW_ANCHOVIES, 1, 7),
                    new LootItem(Items.RAW_CHICKEN, 1, 7),
                    new LootItem(Items.TINDERBOX, 1, 7),
                    new LootItem(Items.UNCUT_OPAL, 1, 7),
                    new LootItem(ClueType.EASY.boxId, 1, 7),
                    new LootItem(Items.COAL, 1, 7),
                    new LootItem(Items.IRON_ORE, 1, 7),
                    new LootItem(Items.UNCUT_JADE, 1, 7),
                    new LootItem(Items.GRIMY_GUAM_LEAF, 1, 4),
                    new LootItem(Items.GRIMY_MARRENTILL, 1, 2),
                    new LootItem(Items.GRIMY_TARROMIN, 1, 1)

            )),
    DIGSITE_WORKMAN(25, 10.4, 422, 4, 1, "digsite workman's", null,
            257211,
            -1,
            60.55,
            0.365,
            new LootTable().addTable(1,
                    new LootItem(Items.SPECIMEN_BRUSH, 1, 3),
                    new LootItem(Items.ANIMAL_SKULL, 1, 3),
                    new LootItem(Items.COINS, 10, 1),
                    new LootItem(Items.ROPE, 1, 1),
                    new LootItem(Items.BUCKET, 1, 1),
                    new LootItem(Items.LEATHER_GLOVES, 1, 1),
                    new LootItem(Items.SPADE, 1, 1)
            )),
    WARRIOR(25, 26.0, 386, 5, 2, "warrior's", PlayerCounter.PICKPOCKETED_WARRIOR,
            257211,
            22524,
            52.34,
            0.5649,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 18, 18, 1) //Coins
            )),
    ROGUE(32, 35.5, 422, 5, 2, "rogue's", PlayerCounter.PICKPOCKETED_ROGUE,
            257211,
            22525,
            49.22,
            0.67,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 25, 40, 108),
                    new LootItem(Items.AIR_RUNE, 8, 8),
                    new LootItem(Items.JUG_OF_WINE, 1, 6),
                    new LootItem(Items.LOCKPICK, 1, 5),
                    new LootItem(Items.IRON_DAGGER_P, 1, 1)
            )),
    // Only record with chances not sourced from osrs wiki data
    CAVE_GOBLIN(36, 40.0, 6001, 5, 1, "goblin's", PlayerCounter.PICKPOCKETED_CAVE_GOBLIN,
            257211,
            22526,
            55,
            0.6,
            new LootTable().addTable(1,
                    new LootItem(Items.BAT_SHISH, 1, 1),
                    new LootItem(Items.COATED_FROGS_LEGS, 1, 1),
                    new LootItem(Items.FINGERS, 1, 1),
                    new LootItem(Items.FROGBURGER, 1, 1),
                    new LootItem(Items.FROGSPAWN_GUMBO, 1, 1),
                    new LootItem(Items.GREEN_GLOOP_SOUP, 1, 1),
                    new LootItem(Items.COINS, 10, 50, 7),
                    new LootItem(Items.BULLSEYE_LANTERN, 1, 1),
                    new LootItem(Items.CAVE_GOBLIN_WIRE, 1, 1),
                    new LootItem(Items.IRON_ORE, 1, 4, 1),
                    new LootItem(Items.OIL_LANTERN, 1, 1),
                    new LootItem(Items.SWAMP_TAR, 1, 1),
                    new LootItem(Items.TINDERBOX, 1, 1),
                    new LootItem(Items.UNLIT_TORCH, 1, 1)
            )),
    MASTER_FARMER(38, 43.0, 386, 5, 3, "master farmer's", PlayerCounter.PICKPOCKETED_MASTER_FARMER,
            257211,
            -1,
            57.42,
            0.6019,
            new LootTable().addTable(1,
                    new LootItem(5318, 1, 4, 1785), //Potato seed
                    new LootItem(5319, 1, 3, 1338), //Onion seed
                    new LootItem(5324, 1, 3, 714), //Cabbage seed
                    new LootItem(5322, 1, 2, 666), //Tomato seed
                    new LootItem(5320, 1, 2, 227), //Sweetcorn seed
                    new LootItem(5323, 1, 121), //Strawberry seed
                    new LootItem(5321, 1, 53), //Watermelon seed
                    new LootItem(22879, 1, 38), //Snape grass seed
                    // Hops
                    new LootItem(Items.BARLEY_SEED, 1, 12, 555),
                    new LootItem(Items.HAMMERSTONE_SEED, 1, 9, 555),
                    new LootItem(Items.ASGARNIAN_SEED, 1, 6, 417),
                    new LootItem(Items.JUTE_SEED, 1, 9, 416),
                    new LootItem(Items.YANILLIAN_SEED, 1, 6, 277),
                    new LootItem(Items.KRANDORIAN_SEED, 1, 6, 131),
                    new LootItem(Items.WILDBLOOD_SEED, 1, 3, 70),
                    // Flowers
                    new LootItem(5096, 1, 476), //Marigold seed
                    new LootItem(5098, 1, 312), //Nasturtium seed
                    new LootItem(5097, 1, 200), //Rosemary seed
                    new LootItem(Items.WOAD_SEED, 1, 147),
                    new LootItem(5100, 1, 116), //Limpwurt seed
                    // Bush seeds
                    new LootItem(Items.REDBERRY_SEED, 1, 400),
                    new LootItem(Items.CADAVABERRY_SEED, 1, 277),
                    new LootItem(Items.DWELLBERRY_SEED, 1, 196),
                    new LootItem(Items.JANGERBERRY_SEED, 1, 77),
                    new LootItem(Items.WHITEBERRY_SEED, 1, 28),
                    new LootItem(Items.POISON_IVY_SEED, 1, 11),
                    // Herb seeds
                    new LootItem(Items.GUAM_SEED, 1, 153),
                    new LootItem(Items.MARRENTILL_SEED, 1, 105),
                    new LootItem(Items.TARROMIN_SEED, 1, 71),
                    new LootItem(Items.HARRALANDER_SEED, 1, 50),
                    new LootItem(5295, 1, 33), //Ranarr seed
                    new LootItem(5296, 1, 21), //Toadflax seed
                    new LootItem(5297, 1, 18), //Irit seed
                    new LootItem(5298, 1, 10), //Avantoe seed
                    new LootItem(5299, 1, 10), //Kwuarm seed
                    new LootItem(5300, 1, 5), //Snapdragon seed
                    new LootItem(5301, 1, 4), //Cadantine seed
                    new LootItem(5302, 1, 2), //Lantadyme seed
                    new LootItem(5303, 1, 2), //Dwarf weed seed
                    new LootItem(5304, 1, 1),  //Torstol seed
                    // Special
                    new LootItem(Items.MUSHROOM_SPORE, 1, 22),
                    new LootItem(Items.BELLADONNA_SEED, 1, 28),
                    new LootItem(Items.CACTUS_SEED, 1, 10),
                    new LootItem(21490, 1, 5),  // Seaweed spore
                    new LootItem(22873, 1, 5)   // Potato cactus seed
            )),
    GUARD(40, 46.8, 386, 5, 2, "guard's", PlayerCounter.PICKPOCKETED_GUARD,
            257211,
            22527,
            49.22,
            0.761,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 30, 30, 1) //Coins
            )),
    BANDIT(53, 79.5, 422, 5, 3, "bandit's", PlayerCounter.PICKPOCKETED_BANDIT,
            257211,
            22530,
            58.98,
            0.764,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 30, 30, 5), //Coins
                    new LootItem(175, 1, 1),  //Antipoison
                    new LootItem(1523, 1, 1)  //Lockpick
            )),
    KNIGHT(55, 84.3, 386, 5, 3, "knight's", PlayerCounter.PICKPOCKETED_KNIGHT,
            257211,
            22531,
            60.55,
            0.763,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 50, 50, 1) //Coins
            )),
    WATCHMAN(65, 137.5, 433, 5, 3, "watchman's", PlayerCounter.PICKPOCKETED_WATCHMAN,
            134625,
            22533,
            42.97,
            0.5859,
            new LootTable().addTable(1,
                    new LootItem(995, 60, 0),
                    new LootItem(Items.BREAD, 1, 0)
            )
    ),
    PALADIN(70, 151.75, 386, 5, 3, "paladin's", PlayerCounter.PICKPOCKETED_PALADIN,
            127056,
            22535,
            46.88,
            0.417,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 80, 80, 0), //Coins
                    new LootItem(562, 2, 0)   //Chaos runes
            )),
    GNOME(75, 198.5, 201, 5, 1, "gnome's", PlayerCounter.PICKPOCKETED_GNOME,
            108718,
            22536,
            35.94,
            0.472,
            new LootTable().addTable(1,
                    new LootItem(Items.KING_WORM, 1, 1, 24),
                    new LootItem(Items.COINS, 300, 300, 17),
                    new LootItem(Items.SWAMP_TOAD, 1, 1, 16),
                    new LootItem(Items.GOLD_ORE, 1, 1, 4),
                    new LootItem(Items.EARTH_RUNE, 1, 1, 2),
                    new LootItem(Items.FIRE_ORB, 1, 1, 1)
            )),
    HERO(80, 275.0, 386, 6, 4, "hero's", PlayerCounter.PICKPOCKETED_HERO,
            99175,
            22537,
            32.03,
            0.391,
            new LootTable().addTable(1,
                    new LootItem(Items.COINS, 200, 300, 105),
                    new LootItem(Items.DEATH_RUNE, 2, 8),
                    new LootItem(Items.JUG_OF_WINE, 1, 6),
                    new LootItem(Items.NATURE_RUNE, 3, 5),
                    new LootItem(Items.FIRE_ORB, 1, 2),
                    new LootItem(Items.GOLD_ORE, 1, 1),
                    new LootItem(Items.DIAMOND, 1, 1)
            )),
    VYRE(82, 306.9, -1, 6, 5, "vyre's", PlayerCounter.PICKPOCKETED_VYRE,
            99175,
            24703,
            41.8,
            0.505,
            new LootTable().addTable(1,
                    new LootItem(Items.COINS, 250, 315, 4129),
                    new LootItem(Items.DEATH_RUNE, 2, 303),
                    new LootItem(24774, 1, 227), // Blood pint
                    new LootItem(Items.UNCUT_RUBY, 1, 189),
                    new LootItem(Items.BLOOD_RUNE, 4, 76),
                    new LootItem(Items.DIAMOND, 1, 38),
                    new LootItem(24785, 1, 38), // Mystery meat
                    new LootItem(24777, 1, 1)   // Blood shard
            )),
    ELF(85, 353.0, 422, 6, 5, "elf's", PlayerCounter.PICKPOCKETED_ELF,
            99175,
            22538,
            33.59,
            0.4186,
            new LootTable().addTable(1,
                    new LootItem(Items.COINS, 280, 350, 105),
                    new LootItem(Items.DEATH_RUNE, 3, 8),
                    new LootItem(Items.JUG_OF_WINE, 1, 6),
                    new LootItem(Items.NATURE_RUNE, 4, 5),
                    new LootItem(Items.FIRE_ORB, 1, 2),
                    new LootItem(Items.GOLD_ORE, 1, 1),
                    new LootItem(Items.DIAMOND, 1, 1)
            )),
    ELF_PRIF(85, 353.0, 422, 6, 5, "elf's", PlayerCounter.PICKPOCKETED_ELF,
            99175,
            22538,
            33.59,
            0.4186,
            new LootTable().addTable(34,
                    new LootItem(Items.COINS, 280, 350, 102),
                    new LootItem(Items.DEATH_RUNE, 3, 8),
                    new LootItem(Items.JUG_OF_WINE, 1, 6),
                    new LootItem(Items.NATURE_RUNE, 4, 5),
                    new LootItem(Items.FIRE_ORB, 1, 2),
                    new LootItem(Items.GOLD_ORE, 1, 1),
                    new LootItem(Items.DIAMOND, 1, 1)
            ).addTable(1,
                    new LootItem(Items.CRYSTAL_SHARD, 1, 29),
                    new LootItem(Items.ENHANCED_CRYSTAL_TELEPORT_SEED, 1, 1)
            )),
    TZHAAR_HUR(90, 103.0, 2609, 6, 4, "tzhaar-hur's", PlayerCounter.PICKPOCKETED_TZHAAR_HUR,
            176743,
            -1,
            64.06,
            1.60667,
            new LootTable().addTable(1,
                    new LootItem(Items.CHISEL, 1, 300000),
                    new LootItem(Items.HAMMER, 1, 300000),
                    new LootItem(Items.JUG, 1, 300000),
                    new LootItem(Items.KNIFE, 1, 300000),
                    new LootItem(Items.POT, 1, 300000),
                    new LootItem(Items.TOKKUL, 1, 32, 1000000),
                    new LootItem(Items.UNCUT_SAPPHIRE, 1, 200000),
                    new LootItem(Items.UNCUT_EMERALD, 1, 200000),
                    new LootItem(Items.UNCUT_RUBY, 1, 200000),
                    new LootItem(Items.UNCUT_DIAMOND, 1, 100000),
                    new LootItem(Items.UNCUT_DRAGONSTONE, 1, 10),
                    new LootItem(Items.UNCUT_ONYX, 1, 1)
            ));

    public final int levelReq, stunAnimation, stunSeconds, stunDamage, petOdds, pouchId;
    private final String name, identifier;
    public final double exp, startingChance, chanceSlope;
    public final LootTable lootTable;
    public final PlayerCounter counter;
    private final String[] names;

    PickPocket(int levelReq, double exp, int stunAnimation, int stunSeconds, int stunDamage, String identifier, PlayerCounter counter, int petOdds, int pouchId, double startingChance, double chanceSlope, LootTable lootTable) {
        this(levelReq, exp, stunAnimation, stunSeconds, stunDamage, identifier, counter, petOdds, pouchId, startingChance, chanceSlope, lootTable, null);
    }

    PickPocket(int levelReq, double exp, int stunAnimation, int stunSeconds, int stunDamage, String identifier, PlayerCounter counter, int petOdds, int pouchId, double startingChance, double chanceSlope, LootTable lootTable, String... names) {
        this.levelReq = levelReq;
        this.exp = exp;
        this.stunAnimation = stunAnimation;
        this.stunSeconds = stunSeconds;
        this.stunDamage = stunDamage;
        this.name = identifier.replace("'s", "");
        this.identifier = identifier;
        this.counter = counter;
        this.petOdds = petOdds;
        this.pouchId = pouchId;
        this.startingChance = startingChance;
        this.chanceSlope = chanceSlope;
        this.lootTable = lootTable;
        this.names = names;
    }

    private static boolean checkAll(Player player, PickPocket pickpocket) {
        if (!player.getStats().check(StatType.Thieving, pickpocket.levelReq, "pickpocket the " + pickpocket.name + "."))
            return false;
        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more loot.");
            return false;
        }
        if (player.isStunned()) {
            player.sendMessage("You're stunned!");
            return false;
        }
        if (BotPrevention.isBlocked(player)) {
            player.sendMessage("You can't pickpocket an NPC while a guard is watching you.");
            return false;
        }
        if (pickpocket.pouchId > 1 && player.getInventory().getAmount(pickpocket.pouchId) >= (player.getRelicManager().hasRelicEnalbed(Relic.TRICKSTER) ? 28 * 3 : 28)) {
            player.sendMessage("You need to open your coin pouches before you pick any more pockets.");
            return false;
        }
        return true;
    }

    private static void pickpocket(Player player, NPC npc, PickPocket pickpocket) {
        if (!checkAll(player, pickpocket)) {
            return;
        }
        player.lock(LockType.FULL_REGULAR_DAMAGE);
        player.addEvent(event -> {
            if (successful(player, pickpocket)) {
                /*
                 Roll for 'additional loot'
                 Rolls are nested, so the 3x roll only happens after 2x succeeds, etc.
                 Each nested roll takes 10 more agility and thieving levels
                 */
                int agilityLevel = player.getStats().get(StatType.Agility).currentLevel;
                int thievingLevel = player.getStats().get(StatType.Thieving).currentLevel;
                int additionalRolls = 0;
                while (agilityLevel >= pickpocket.levelReq
                        && thievingLevel - 10 >= pickpocket.levelReq
                        && Random.rollDie(3)         // 33% chance to get more loot
                        && ++additionalRolls < 3) {         // Maximum of 3 additional rolls
                    // Because each additional roll requires +10 levels in each
                    agilityLevel -= 10;
                    thievingLevel -= 10;
                }
                // Rogue set effect
                boolean rogueEffectActive = false;
                if (Random.get() < getRogueEffectChance(player)) {
                    ++additionalRolls;
                    rogueEffectActive = true;
                }

                int pouchId = pickpocket.pouchId;
                player.animate(881);
                player.privateSound(2581);
                LootTable lootTable = pickpocket == ELF && MapArea.PRIFDDINAS.inArea(player) ? ELF_PRIF.lootTable : pickpocket.lootTable;
                if (additionalRolls > 0) {
                    boolean hasGottenPouch = false;
                    for (int index = additionalRolls + 1; index > 0; index--) {
                        List<Item> items = lootTable.rollItems(true);
                        for (Item item : items) {
                            //  Coin pouch handling
                            if (item.getId() == 995 && pouchId != -1 && !hasGottenPouch) {
                                player.getInventory().addOrDrop(pouchId, 1);
                                hasGottenPouch = true;  // Prevents getting multiple pouches per pickpocket
                            } else {
                                player.getInventory().addOrDrop(item);
                                player.getTaskManager().doSkillItemLookup(item);
                            }
                        }
                    }
                    if (rogueEffectActive && additionalRolls == 1) {
                        player.sendFilteredMessage("You pick the " + pickpocket.identifier + " pocket. Your outfit allows you to steal double loot.");
                    } else {
                        player.sendFilteredMessage("Your lightning-fast reactions allow you to steal " + (additionalRolls == 1 ? "double" : additionalRolls == 2 ? "triple" : additionalRolls == 3 ? "quadruple" : "quintuple") + " the loot.");
                    }
                } else {
                    player.sendFilteredMessage("You pick the " + pickpocket.identifier + " pocket.");
                    List<Item> items = lootTable.rollItems(true);
                    for (Item item : items) {
                        if (item.getId() == 995 && pouchId != -1) {
                            player.getInventory().add(pouchId, 1);
                        } else {
                            player.getInventory().add(item);
                            player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.PICKPOCKETLOOT, item.getDef().name, item.getAmount(), true);
                        }
                    }
                }
                if (Random.rollDie(pickpocket.petOdds - (player.getStats().get(StatType.Thieving).currentLevel * 25)))
                    Pet.ROCKY.unlock(player);
                player.getStats().addXp(StatType.Thieving, pickpocket.exp, true);
                player.getTaskManager().doLookupByCategory(TaskCategory.PICKPOCKET, npc.getDef().name.toLowerCase());
                if (pickpocket == HAM && !player.getTaskManager().hasCompletedTask(938) && hasHAMSet(player))
                    player.getTaskManager().doLookupByUUID(938);    // Pickpocket a H.A.M. Member as a H.A.M. Member
            } else {
                player.sendFilteredMessage("You fail to pick the " + pickpocket.identifier + " pocket.");
                npc.forceText("What do you think you're doing?");
                npc.faceTemp(player);
                npc.animate(pickpocket.stunAnimation);
                player.hit(new Hit().randDamage(pickpocket.stunDamage).postDamage((hit) -> {
                    if (!DodgyNecklace.test(player)) {
                        hit.player.stun(pickpocket.stunSeconds, true);
                    }
                }));
            }
            BotPrevention.attemptBlock(player);
            player.unlock();
        });
    }

    private static boolean successful(Player player, PickPocket pickpocket) {
        return Random.get(100) <= chance(player, pickpocket.levelReq, pickpocket);
    }

    private static final int GLOVES_OF_SILENCE = 10075;
    private static final int[] MAX_CAPES = {13280, 13329, 13331, 13333, 13335, 13337, 13342, 20760};

    /*
     * Chance caps at 94 w/o any bonuses
     */
    private static int chance(Player player, int levelReq, PickPocket pickpocket) {
        double chance = pickpocket.startingChance;
        int thievingLevel = player.getStats().get(StatType.Thieving).currentLevel;
        chance += (thievingLevel - levelReq) * pickpocket.chanceSlope;
        if (pickpocket == HAM && hasHAMSet(player)) {
            chance *= 1.15; // 15% boost for wearing ham set when pickpocketing ham
        }
        if ((KandarinReward.THIEVING_BOOST_1.hasReward(player) && MapArea.ARDOUGNE.inArea(player)) || KandarinReward.THIEVING_BOOST_2.hasReward(player)) {
            chance *= 1.1;
        } else if (player.getEquipment().hasId(GLOVES_OF_SILENCE))  // Only applies if ardy effect doesn't
            chance *= 1.05;
        if (player.getEquipment().hasAtLeastOneOf(MAX_CAPES) || ThievingSkillCape.wearsThievingCape(player)) {
            chance *= 1.1;
        }
        return (int) Math.floor(chance);
    }

    private static boolean hasHAMSet(Player player) {
        for (int id : Arrays.asList(Items.HAM_BOOTS, Items.HAM_HOOD, Items.HAM_ROBE, Items.HAM_SHIRT, Items.HAM_BOOTS, Items.HAM_LOGO)) {
            if (!player.getEquipment().hasId(id)) return false;
        }
        if (!player.getEquipment().hasAtLeastOneOf(MAX_CAPES) && !ThievingSkillCape.wearsThievingCape(player) && !player.getEquipment().hasId(Items.HAM_CLOAK))
            return false;
        return player.getEquipment().hasId(Items.HAM_GLOVES) || player.getEquipment().hasId(Items.GLOVES_OF_SILENCE);
    }

    private static float getRogueEffectChance(Player player) {
        float chance = 0;
        Equipment equipment = player.getEquipment();
        for (int id : Arrays.asList(Items.ROGUE_BOOTS, Items.ROGUE_GLOVES, Items.ROGUE_TOP, Items.ROGUE_TROUSERS, Items.ROGUE_MASK)) {
            if (equipment.hasId(id)) chance += .2;
        }
        return chance;
    }

    static {
        NPCDef.forEach(npcDef -> {
            for (PickPocket pickpocket : values()) {
                if (npcDef.name.equalsIgnoreCase(pickpocket.name().replace("_", " "))
                        || npcDef.name.toLowerCase().contains(pickpocket.name().toLowerCase())
                        || (pickpocket.names != null && (Arrays.stream(pickpocket.names).anyMatch(n -> npcDef.name.toLowerCase().contains(n.toLowerCase())
                            || npcDef.name.equalsIgnoreCase(n))))) {
                    int pickpocketOption = npcDef.getOption("pickpocket", "steal-from");
                    if (pickpocketOption == -1)
                        return;
                    NPCAction.register(npcDef.name, pickpocketOption, (player, npc) -> pickpocket(player, npc, pickpocket));
                }
            }
        });
        final int[] HAM_MEMBERS = {2540, 2541};
        for (int hamMember : HAM_MEMBERS) {
            NPCAction.register(hamMember, "pickpocket", (player, npc) -> pickpocket(player, npc, HAM));
        }
        NPCAction.register("tzhaar-hur", "pickpocket", (player, npc) -> pickpocket(player, npc, TZHAAR_HUR));
    }
}
