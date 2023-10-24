package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.jewellery.DodgyNecklace;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.skillcapes.ThievingSkillCape;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.BotPrevention;
import io.ruin.model.stat.StatType;

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
            )),
    WOMAN(1, 8.0, 422, 5, 1, "woman's", PlayerCounter.PICKPOCKETED_MAN,
            257211,
            22521,
            70.70,
            0.2424,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 3, 3, 1)  //Coins
            )),
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
                    new LootItem(882, 16, 60), //Coins
                    new LootItem(1351, 1, 1), //Coins
                    new LootItem(1265, 1, 1), //Coins
                    new LootItem(1349, 1, 1), //Coins
                    new LootItem(1267, 1, 1), //Coins
                    new LootItem(886, 20, 1), //Coins
                    new LootItem(1353, 1, 1), //Coins
                    new LootItem(1207, 1, 1), //Coins
                    new LootItem(1129, 1, 1), //Coins
                    new LootItem(4302, 1, 1), //Coins
                    new LootItem(4298, 1, 1), //Coins
                    new LootItem(4300, 1, 1), //Coins
                    new LootItem(4304, 1, 1), //Coins
                    new LootItem(4306, 1, 1), //Coins
                    new LootItem(4308, 1, 1), //Coins
                    new LootItem(4310, 1, 1), //Coins
                    new LootItem(COINS_995, 1, 21, 1), //Coins
                    new LootItem(319, 1, 1), //Coins
                    new LootItem(2138, 1, 1), //Coins
                    new LootItem(453, 1, 1), //Coins
                    new LootItem(440, 1, 1), //Coins
                    new LootItem(1739, 1, 1), //Coins
                    new LootItem(314, 5, 1), //Coins
                    new LootItem(1734, 6, 1), //Coins
                    new LootItem(1733, 1, 1), //Coins
                    new LootItem(1511, 1, 1), //Coins
                    new LootItem(686, 1, 1), //Coins
                    new LootItem(697, 1, 1), //Coins
                    new LootItem(1625, 1, 1), //Coins
                    new LootItem(1627, 1, 1), //Coins
                    new LootItem(199, 5, 1), //Coins
                    new LootItem(201, 6, 1), //Coins
                    new LootItem(203, 1, 1) //Coins
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
                    new LootItem(COINS_995, 25, 40, 10), //Coins
                    new LootItem(556, 8, 5),  //Air runes
                    new LootItem(1933, 1, 4), //Jug of wine
                    new LootItem(1219, 1, 3), //Iron dagger(p)
                    new LootItem(1523, 1, 1)  //Lockpick
            )),
    //  TODO stunAnimation
    // Only record with chances not sourced from osrs wiki data
    CAVE_GOBLIN(36, 40.0, -1, 5, 1, "goblin's", PlayerCounter.PICKPOCKETED_CAVE_GOBLIN,
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
                    new LootItem(5318, 1, 4, 8), //Potato seed
                    new LootItem(5319, 1, 3, 5), //Onion seed
                    new LootItem(5324, 1, 3, 5), //Cabbage seed
                    new LootItem(5322, 1, 2, 5), //Tomato seed
                    new LootItem(5320, 1, 2, 5), //Sweetcorn seed
                    new LootItem(5096, 1, 5), //Marigold seed
                    new LootItem(5097, 1, 5), //Rosemary seed
                    new LootItem(5098, 1, 5), //Nasturtium seed
                    new LootItem(5291, 1, 5), //Guam seed
                    new LootItem(5292, 1, 5), //Marrentill seed
                    new LootItem(5293, 1, 5), //Tarromin seed
                    new LootItem(5294, 1, 5), //Harralander seed
                    new LootItem(5323, 1, 3), //Strawberry seed
                    new LootItem(5321, 1, 3), //Watermelon seed
                    new LootItem(5100, 1, 3), //Limpwurt seed
                    new LootItem(5295, 1, 2), //Ranarr seed
                    new LootItem(5296, 1, 2), //Toadflax seed
                    new LootItem(5297, 1, 2), //Irit seed
                    new LootItem(5298, 1, 1), //Avantoe seed
                    new LootItem(5299, 1, 1), //Kwuarm seed
                    new LootItem(5300, 1, 1), //Snapdragon seed
                    new LootItem(5301, 1, 1), //Cadantine seed
                    new LootItem(5302, 1, 1), //Lantadyme seed
                    new LootItem(5303, 1, 1), //Dwarf weed seed
                    new LootItem(5304, 1, 1)  //Torstol seed
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
                    new LootItem(COINS_995, 30, 30, 8), //Coins
                    new LootItem(175, 1, 3),  //Antipoison
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
                    new LootItem(COINS_995, 300, 300, 16), //Coins
                    new LootItem(5321, 3, 8),   //Watermelon seed
                    new LootItem(5100, 1, 8),   //Limpwurt seed
                    new LootItem(5295, 1, 7),   //Ranarr seed
                    new LootItem(5296, 1, 7),   //Toadflax seed
                    new LootItem(5297, 1, 7),   //Irit seed
                    new LootItem(5298, 1, 7),   //Avantoe seed
                    new LootItem(5299, 1, 7),   //Kwuarm seed
                    new LootItem(5300, 1, 7),   //Snapdragon seed
                    new LootItem(5301, 1, 7),   //Cadantine seed
                    new LootItem(5302, 1, 6),   //Lantadyme seed
                    new LootItem(5303, 1, 5),   //Dwarf weed seed
                    new LootItem(5304, 1, 4),   //Torstol seed
                    new LootItem(5312, 1, 4),   //Acorn
                    new LootItem(5313, 1, 3),   //Willow seed
                    new LootItem(5314, 1, 4),   //Maple seed
                    new LootItem(5315, 1, 1),   //Yew seed
                    new LootItem(5283, 1, 9),   //Apple tree seed
                    new LootItem(5284, 1, 8),   //Banana tree seed
                    new LootItem(5285, 1, 7),   //Orange tree seed
                    new LootItem(5286, 1, 6),   //Curry tree seed
                    new LootItem(5287, 1, 3),   //Pineapple seed
                    new LootItem(5288, 1, 2)    //Papaya tree seed
            )),
    HERO(80, 275.0, 386, 6, 4, "hero's", PlayerCounter.PICKPOCKETED_HERO,
            99175,
            22537,
            32.03,
            0.391,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 200, 300, 16),  //Coins
                    new LootItem(565, 1, 5),  //Blood rune
                    new LootItem(560, 2, 5),  //Death runes
                    new LootItem(1933, 1, 2), //Jug of wine
                    new LootItem(569, 1, 2),  //Fire orb
                    new LootItem(444, 1, 2),  //Gold ore
                    new LootItem(1617, 1, 1)  //Uncut diamond
            )),
    VYRE(82, 306.9, -1, 6, 5, "vyre's", PlayerCounter.PICKPOCKETED_VYRE,
            99175,
            24703,
            41.8,
            0.505,
            new LootTable().addTable(1,
                    new LootItem(995, 250, 315, 4129),
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
                    new LootItem(Items.DEATH_RUNE, 2, 8),
                    new LootItem(Items.JUG_OF_WINE, 1, 6),
                    new LootItem(Items.NATURE_RUNE, 3, 5),
                    new LootItem(Items.FIRE_ORB, 1, 2),
                    new LootItem(Items.GOLD_ORE, 1, 1),
                    new LootItem(Items.DIAMOND, 1, 1)
            )),
    TZHAAR_HUR(90, 103.0, 2609, 6, 4, "tzhaar-hur's", PlayerCounter.PICKPOCKETED_TZHAAR_HUR,
            176743,
            -1,
            64.06,
            1.60667,
            new LootTable().addTable(1,
                    new LootItem(Items.CHISEL, 1, 3),
                    new LootItem(Items.HAMMER, 1, 3),
                    new LootItem(Items.JUG, 1, 3),
                    new LootItem(Items.KNIFE, 1, 3),
                    new LootItem(Items.POT, 1, 3),
                    new LootItem(Items.TOKKUL, 1, 16, 10),
                    new LootItem(Items.UNCUT_SAPPHIRE, 1, 2),
                    new LootItem(Items.UNCUT_EMERALD, 1, 2),
                    new LootItem(Items.UNCUT_RUBY, 1, 2),
                    new LootItem(Items.UNCUT_DIAMOND, 1, 1)
            ));

    public final int levelReq, stunAnimation, stunSeconds, stunDamage, petOdds, pouchId;
    private final String name, identifier;
    public final double exp, startingChance, chanceSlope;
    public final LootTable lootTable;
    public final PlayerCounter counter;

    PickPocket(int levelReq, double exp, int stunAnimation, int stunSeconds, int stunDamage, String identifier, PlayerCounter counter, int petOdds, int pouchId, double startingChance, double chanceSlope, LootTable lootTable) {
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
        if (pickpocket.pouchId > 1 && player.getInventory().getAmount(pickpocket.pouchId) >= 28) {
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
                int pouchId = pickpocket.pouchId;
                player.animate(881);
                player.privateSound(2581);
                if (additionalRolls > 0) {
                    boolean hasGottenPouch = false;
                    for (int index = additionalRolls + 1; index > 0; index--) {
                        List<Item> items = pickpocket.lootTable.rollItems(true);
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
                    player.sendFilteredMessage("Your lightning-fast reactions allow you to steal " + (additionalRolls == 1 ? "double" : additionalRolls == 2 ? "triple" : "quadruple") + " the loot.");
                } else {
                    player.sendFilteredMessage("You pick the " + pickpocket.identifier + " pocket.");
                    List<Item> items = pickpocket.lootTable.rollItems(true);
                    for (Item item : items) {
                        if (item.getId() == 995 && pouchId != -1) {
                            player.getInventory().add(pouchId, 1);
                        } else {
                            player.getInventory().add(item);
                            player.getTaskManager().doSkillItemLookup(item);
                            player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.PICKPOCKETLOOT, item.getDef().name, item.getAmount(), true);
                        }
                    }
                }
                if (Random.rollDie(pickpocket.petOdds - (player.getStats().get(StatType.Thieving).currentLevel * 25)))
                    Pet.ROCKY.unlock(player);
                player.getStats().addXp(StatType.Thieving, pickpocket.exp, true);
                player.getTaskManager().doLookupByCategory(TaskCategory.PICKPOCKET, npc.getDef().name.toLowerCase());
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
        if (player.getEquipment().hasId(GLOVES_OF_SILENCE))
            chance *= 1.05;
        if (player.getEquipment().hasAtLeastOneOf(MAX_CAPES) || ThievingSkillCape.wearsThievingCape(player)) {
            chance *= 1.1;
        }
        //TODO Ardy buff? if (player.getEquipment().hasAtLeastOneOf(MAX_CAPES) || ThievingSkillCape.wearsThievingCape(player)) {
        //    chance *= 1.1;
        //}
        return (int) Math.floor(chance);
    }

    static {
        NPCDef.forEach(npcDef -> {
            for (PickPocket pickpocket : values()) {
                if (npcDef.name.equalsIgnoreCase(pickpocket.name().replace("_", " ")) ||
                        npcDef.name.toLowerCase().contains(pickpocket.name().toLowerCase())) {
                    int pickpocketOption = npcDef.getOption("pickpocket");
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
