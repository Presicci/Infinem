package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.MapRegion;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/21/2022
 */
public class HotColdClue extends Clue {

    public Position position;

    private HotColdClue(ClueType type, Position position) {
        super(type);
        this.position = position;
    }

    private static final String BEGINNER_MESSAGE = "Buried beneath the ground, who knows where it's found. Lucky for you, A man called Reldo may have a clue.";
    private static final String MASTER_MESSAGE = "Buried beneath the ground, who knows where it's found. Lucky for you, A man called Jorral may have a clue.";


    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, type == ClueType.MASTER ? MASTER_MESSAGE : BEGINNER_MESSAGE);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    private static void feelBeginner(Player player, Item item) {
        String message = "The device is ";
        if (player.beginnerClue != null && player.beginnerClue.id >= 0 && player.getInventory().contains(ClueType.BEGINNER.clueId) &&
                Clue.CLUES[player.beginnerClue.id] instanceof HotColdClue) {
            HotColdClue clue = (HotColdClue) Clue.CLUES[player.beginnerClue.id];
            int distance = player.getPosition().distance(clue.position);
            int lastDistance = player.attributeOr(AttributeKey.HOT_AND_COLD, 0);
            Temperature temperature = null;
            for (Temperature temp : Temperature.values()) {
                if (distance > temp.minDistance && distance < temp.maxDistance) {
                    message += temp.message;
                    player.putAttribute(AttributeKey.HOT_AND_COLD, distance);
                    temperature = temp;
                    break;
                }
            }
            if (temperature == Temperature.INCREDIBLY_HOT) {
                player.sendMessage(message + " and burns to the touch. This must be the spot.");
            } else if (lastDistance <= 0) {
                player.sendMessage(message + ".");
            } else {
                if (distance < lastDistance) {
                    message += ", and warmer than last time.";
                } else if (distance > lastDistance) {
                    message += ", but colder than last time.";
                } else {
                    message += ", and the same temperature as last time.";
                }
                player.sendMessage(message);
            }
        } else {
            player.dialogue(new ItemDialogue().one(item.getId(), "The device is inactive."));
        }
    }

    private static void feelMaster(Player player, Item item) {
        String message = "The device is ";
        if (player.masterClue != null && player.masterClue.id >= 0 && player.getInventory().contains(ClueType.MASTER.clueId) &&
                Clue.CLUES[player.masterClue.id] instanceof HotColdClue) {
            HotColdClue clue = (HotColdClue) Clue.CLUES[player.masterClue.id];
            int distance = player.getPosition().distance(clue.position);
            int lastDistance = player.attributeOr(AttributeKey.HOT_AND_COLD, 0);
            Temperature temperature = null;
            for (Temperature temp : Temperature.values()) {
                if (distance > temp.minDistance && distance < temp.maxDistance) {
                    message += temp.message;
                    player.putAttribute(AttributeKey.HOT_AND_COLD, distance);
                    temperature = temp;
                    break;
                }
            }
            if (temperature == Temperature.INCREDIBLY_HOT) {
                player.sendMessage(message + " and burns to the touch. This must be the spot.");
            } else if (lastDistance <= 0) {
                player.sendMessage(message + ".");
            } else {
                if (distance < lastDistance) {
                    message += ", and warmer than last time.";
                } else if (distance > lastDistance) {
                    message += ", but colder than last time.";
                } else {
                    message += ", and the same temperature as last time.";
                }
                player.sendMessage(message);
            }
            player.hit(new Hit().fixedDamage(Random.get(3, 8)));
            player.sendFilteredMessage("The power of the strange device hurts you in the process.");
        } else {
            player.dialogue(new ItemDialogue().one(item.getId(), "The device is inactive."));
        }
    }

    /**
     * Register
     */
    private static void registerDig(Position position, ClueType type) {
        HotColdClue clue = new HotColdClue(type, position);
        Tile.get(position, true).digAction = clue::advance;
    }

    private static final int BEGINNER_DEVICE = 23183, MASTER_DEVICE = 19939;

    static {
        for (HotCold clue : HotCold.values()) {
            registerDig(clue.position, clue.beginner ? ClueType.BEGINNER : ClueType.MASTER);
        }
        ItemAction.registerInventory(BEGINNER_DEVICE, "feel", HotColdClue::feelBeginner);
        ItemAction.registerInventory(MASTER_DEVICE, "feel", HotColdClue::feelMaster);
        NPCAction.register(4243, "talk-to", HotColdClue::reldoDialogue);
        NPCAction.register(3490, "talk-to", HotColdClue::jorralDialogue);
    }

    private static void reldoDialogue(Player player, NPC npc) {
        if (player.beginnerClue != null && player.beginnerClue.id >= 0 && player.getInventory().contains(ClueType.BEGINNER.clueId) &&
                Clue.CLUES[player.beginnerClue.id] instanceof HotColdClue) {
            if (player.getInventory().hasId(BEGINNER_DEVICE) || player.getBank().hasId(BEGINNER_DEVICE)) {
                player.dialogue(
                        new PlayerDialogue("Hey Reldo, I have one of those clue scrolls again, do you have any advice?"),
                        new NPCDialogue(npc.getId(), "That strange device will guide you in the same way the key did. Feel it, but be careful as it is very powerful. Good luck.")
                );
            } else {
                player.dialogue(
                        new ItemDialogue().one(ClueType.BEGINNER.clueId, "You show Reldo your clue scroll."),
                        new NPCDialogue(npc.getId(), "Ah! Do you know about hot and cold clues?"),
                        new OptionsDialogue(
                                new Option("Yes, I do.", () -> {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "Well then use this strange device to feel where your destination is and dig."),
                                            new ActionDialogue(() -> {
                                                if (player.getInventory().hasFreeSlots(1)) {
                                                    player.getInventory().add(BEGINNER_DEVICE);
                                                } else {
                                                    player.dialogue(
                                                            new NPCDialogue(npc.getId(), "You should make some room for it before I can give you it.")
                                                    );
                                                }
                                            })
                                    );
                                }),
                                new Option("No, I don't.", () -> {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "Ah, you use a strange device to tell you how far away you are from the place you need to be. When you get to your destination you dig."),
                                            new NPCDialogue(npc.getId(), "Well then use this strange device to feel where your destination is and dig."),
                                            new ActionDialogue(() -> {
                                                if (player.getInventory().hasFreeSlots(1)) {
                                                    player.getInventory().add(BEGINNER_DEVICE);
                                                } else {
                                                    player.dialogue(
                                                            new NPCDialogue(npc.getId(), "You should make some room for it before I can give you it.")
                                                    );
                                                }
                                            })
                                    );
                                })
                        )
                );
            }
        } else {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Hello stranger."),
                    new PlayerDialogue("Hello.")
            );
        }
    }

    private static void jorralDialogue(Player player, NPC npc) {
        if (player.masterClue != null && player.masterClue.id >= 0 && player.getInventory().contains(ClueType.MASTER.clueId) &&
                Clue.CLUES[player.masterClue.id] instanceof HotColdClue) {
            if (player.getInventory().hasId(MASTER_DEVICE) || player.getBank().hasId(MASTER_DEVICE)) {
                player.dialogue(
                        new PlayerDialogue("Hey Jorral, I have one of those clue scrolls again, do you have any advice?"),
                        new NPCDialogue(npc.getId(), "That strange device will guide you in the same way the key did. Feel it, but be careful as it is very powerful. Good luck.")
                );
            } else {
                player.dialogue(
                        new ItemDialogue().one(ClueType.MASTER.clueId, "You show Jorral your clue scroll."),
                        new NPCDialogue(npc.getId(), "Ah! Do you know about hot and cold clues?"),
                        new OptionsDialogue(
                                new Option("Yes, I do.", () -> {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "Well then use this strange device to feel where your destination is and dig."),
                                            new ActionDialogue(() -> {
                                                if (player.getInventory().hasFreeSlots(1)) {
                                                    player.getInventory().add(MASTER_DEVICE);
                                                } else {
                                                    player.dialogue(
                                                            new NPCDialogue(npc.getId(), "You should make some room for it before I can give you it.")
                                                    );
                                                }
                                            })
                                    );
                                }),
                                new Option("No, I don't.", () -> {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "Ah, you use a strange device to tell you how far away you are from the place you need to be. When you get to your destination you dig."),
                                            new NPCDialogue(npc.getId(), "Well then use this strange device to feel where your destination is and dig."),
                                            new ActionDialogue(() -> {
                                                if (player.getInventory().hasFreeSlots(1)) {
                                                    player.getInventory().add(MASTER_DEVICE);
                                                } else {
                                                    player.dialogue(
                                                            new NPCDialogue(npc.getId(), "You should make some room for it before I can give you it.")
                                                    );
                                                }
                                            })
                                    );
                                })
                        )
                );
            }
        } else {
            player.dialogue(
                    new NPCDialogue(npc.getId(), "Hello stranger."),
                    new PlayerDialogue("Hello.")
            );
        }
    }

    @AllArgsConstructor
    private enum Temperature {
        ICE_COLD("ice cold", 500, 50000),
        VERY_COLD("very cold", 200, 499),
        COLD("cold", 150, 199),
        WARM("warm", 100, 149),
        HOT("hot", 70, 99),
        VERY_HOT("very hot", 30, 69),
        INCREDIBLY_HOT("incredibly hot", 4, 29),
        VISIBLY_SHAKING("visibly shaking", 0, 3);

        private final String message;
        private final int minDistance, maxDistance;
    }

    /**
     * Enum containing data for hot/cold steps.
     * basically copy/pasted from runelite plugin
     */
    private enum HotCold {
        LUMBRIDGE_COW_FIELD(true, new Position(3174, 3336, 0), MapRegion.MISTHALIN, "Cow field north of Lumbridge"),
        ICE_MOUNTAIN(true, new Position(3007, 3475, 0), MapRegion.MISTHALIN, "Atop Ice Mountain"),
        DRAYNOR_MANOR_MUSHROOMS(true, new Position(3096, 3379, 0), MapRegion.MISTHALIN, "Patch of mushrooms just northwest of Draynor Manor"),
        DRAYNOR_WHEAT_FIELD(true, new Position(3120, 3282, 0), MapRegion.MISTHALIN, "Inside the wheat field next to Draynor Village"),
        NORTHEAST_OF_AL_KHARID_MINE(true, new Position(3332, 3313, 0), MapRegion.MISTHALIN, "Northeast of Al Kharid Mine"),

        ASGARNIA_WARRIORS(new Position(2860, 3562, 0), MapRegion.ASGARNIA, "North of the Warriors' Guild in Burthorpe."),
        ASGARNIA_JATIX(new Position(2914, 3429, 0), MapRegion.ASGARNIA, "East of Jatix's Herblore Shop in Taverley."),
        ASGARNIA_BARB(new Position(3036, 3439, 0), MapRegion.ASGARNIA, "West of Barbarian Village."),
        ASGARNIA_MIAZRQA(new Position(2973, 3489, 0), MapRegion.ASGARNIA, "North of Miazrqa's tower, outside Goblin Village."),
        ASGARNIA_COW(new Position(3033, 3308, 0), MapRegion.ASGARNIA, "In the cow pen north of Sarah's Farming Shop."),
        ASGARNIA_PARTY_ROOM(new Position(3026, 3363, 0), MapRegion.ASGARNIA, "Outside the Falador Party Room."),
        ASGARNIA_CRAFT_GUILD(new Position(2917, 3295, 0), MapRegion.ASGARNIA, "Outside the Crafting Guild cow pen."),
        ASGARNIA_RIMMINGTON(new Position(2978, 3241, 0), MapRegion.ASGARNIA, "In the centre of the Rimmington mine."),
        ASGARNIA_MUDSKIPPER(new Position(2984, 3109, 0), MapRegion.ASGARNIA, "Mudskipper Point, on the starfish in the south-west corner."),
        ASGARNIA_TROLL(new Position(2910, 3616, 0), MapRegion.ASGARNIA, "The Troll arena, where the player fights Dad during the Troll Stronghold quest. Bring climbing boots if travelling from Burthorpe."),
        DESERT_GENIE(new Position(3364, 2910, 0), MapRegion.DESERT, "West of Nardah genie cave."),
        DESERT_ALKHARID_MINE(new Position(3282, 3270, 0), MapRegion.DESERT, "West of Al Kharid mine."),
        DESERT_MENAPHOS_GATE(new Position(3224, 2816, 0), MapRegion.DESERT, "North of Menaphos gate."),
        DESERT_BEDABIN_CAMP(new Position(3164, 3050, 0), MapRegion.DESERT, "Bedabin Camp, dig around the north tent."),
        DESERT_UZER(new Position(3431, 3106, 0), MapRegion.DESERT, "West of Uzer."),
        DESERT_POLLNIVNEACH(new Position(3287, 2975, 0), MapRegion.DESERT, "West of Pollnivneach."),
        DESERT_MTA(new Position(3350, 3293, 0), MapRegion.DESERT, "Next to Mage Training Arena."),
        DESERT_SHANTY(new Position(3294, 3106, 0), MapRegion.DESERT, "South-west of Shantay Pass."),
        FELDIP_HILLS_JIGGIG(new Position(2413, 3055, 0), MapRegion.FELDIP_HILLS, "West of Jiggig, east of the fairy ring bkp."),
        FELDIP_HILLS_SW(new Position(2582, 2895, 0), MapRegion.FELDIP_HILLS, "West of the southeasternmost lake in Feldip Hills."),
        FELDIP_HILLS_GNOME_GLITER(new Position(2553, 2972, 0), MapRegion.FELDIP_HILLS, "East of the gnome glider (Lemantolly Undri)."),
        FELDIP_HILLS_RANTZ(new Position(2611, 2946, 0), MapRegion.FELDIP_HILLS, "South of Rantz, six steps west of the empty glass bottles."),
        FELDIP_HILLS_SOUTH(new Position(2487, 3005, 0), MapRegion.FELDIP_HILLS, "South of Jiggig."),
        FELDIP_HILLS_RED_CHIN(new Position(2532, 2900, 0), MapRegion.FELDIP_HILLS, "Outside the red chinchompa hunting ground entrance, south of the Hunting expert's hut."),
        FELDIP_HILLS_SE(new Position(2567, 2916, 0), MapRegion.FELDIP_HILLS, "South-east of the ∩-shaped lake, near the icon."),
        FELDIP_HILLS_CW_BALLOON(new Position(2452, 3108, 0), MapRegion.FELDIP_HILLS, "Directly west of the Castle Wars balloon."),
        FREMENNIK_PROVINCE_MTN_CAMP(new Position(2804, 3672, 0), MapRegion.FREMENNIK_PROVINCE, "At the Mountain Camp."),
        FREMENNIK_PROVINCE_RELLEKKA_HUNTER(new Position(2724, 3783, 0), MapRegion.FREMENNIK_PROVINCE, "At the Rellekka Hunter area, near the icon."),
        FREMENNIK_PROVINCE_KELGADRIM_ENTRANCE(new Position(2715, 3689, 0), MapRegion.FREMENNIK_PROVINCE, "West of the Keldagrim entrance mine."),
        FREMENNIK_PROVINCE_SW(new Position(2605, 3648, 0), MapRegion.FREMENNIK_PROVINCE, "Outside the fence in the south-western corner of Rellekka."),
        FREMENNIK_PROVINCE_LIGHTHOUSE(new Position(2589, 3598, 0), MapRegion.FREMENNIK_PROVINCE, "South-east of the Lighthouse."),
        FREMENNIK_PROVINCE_ETCETERIA_CASTLE(new Position(2614, 3867, 0), MapRegion.FREMENNIK_PROVINCE, "Inside Etceteria's castle, in the southern staircase."),
        FREMENNIK_PROVINCE_MISC_COURTYARD(new Position(2529, 3867, 0), MapRegion.FREMENNIK_PROVINCE, "Outside Miscellania's courtyard."),
        FREMENNIK_PROVINCE_FREMMY_ISLES_MINE(new Position(2378, 3849, 0), MapRegion.FREMENNIK_PROVINCE, "Central Fremennik Isles mine."),
        FREMENNIK_PROVINCE_WEST_ISLES_MINE(new Position(2313, 3854, 0), MapRegion.FREMENNIK_PROVINCE, "West Fremennik Isles mine."),
        FREMENNIK_PROVINCE_WEST_JATIZSO_ENTRANCE(new Position(2391, 3813, 0), MapRegion.FREMENNIK_PROVINCE, "West of the Jatizso mine entrance."),
        FREMENNIK_PROVINCE_PIRATES_COVE(new Position(2210, 3814, 0), MapRegion.FREMENNIK_PROVINCE, "Pirates' Cove"),
        FREMENNIK_PROVINCE_ASTRAL_ALTER(new Position(2147, 3862, 0), MapRegion.FREMENNIK_PROVINCE, "Astral altar"),
        FREMENNIK_PROVINCE_LUNAR_VILLAGE(new Position(2087, 3915, 0), MapRegion.FREMENNIK_PROVINCE, "Lunar Isle, inside the village."),
        FREMENNIK_PROVINCE_LUNAR_NORTH(new Position(2106, 3949, 0), MapRegion.FREMENNIK_PROVINCE, "Lunar Isle, north of the village."),
        KANDARIN_SINCLAR_MANSION(new Position(2726, 3588, 0), MapRegion.KANDARIN, "North-west of the Sinclair Mansion, near the log balance shortcut."),
        KANDARIN_CATHERBY(new Position(2774, 3433, 0), MapRegion.KANDARIN, "Catherby, between the bank and the beehives, near small rock formation."),
        KANDARIN_GRAND_TREE(new Position(2444, 3503, 0), MapRegion.KANDARIN, "Grand Tree, just east of the terrorchick gnome enclosure."),
        KANDARIN_SEERS(new Position(2735, 3486, 0), MapRegion.KANDARIN, "Between the Seers' Village bank and Camelot."),
        KANDARIN_MCGRUBORS_WOOD(new Position(2653, 3485, 0), MapRegion.KANDARIN, "McGrubor's Wood"),
        KANDARIN_FISHING_BUILD(new Position(2586, 3372, 0), MapRegion.KANDARIN, "South of Fishing Guild"),
        KANDARIN_WITCHHAVEN(new Position(2708, 3304, 0), MapRegion.KANDARIN, "Outside Witchaven, west of Jeb, Holgart, and Caroline."),
        KANDARIN_NECRO_TOWER(new Position(2669, 3242, 0), MapRegion.KANDARIN, "Ground floor inside the Necromancer Tower. Easily accessed by using fairy ring code djp."),
        KANDARIN_FIGHT_ARENA(new Position(2587, 3134, 0), MapRegion.KANDARIN, "South of the Fight Arena, north-west of the Nightmare Zone."),
        KANDARIN_TREE_GNOME_VILLAGE(new Position(2526, 3160, 0), MapRegion.KANDARIN, "Tree Gnome Village, near the general store icon."),
        KANDARIN_GRAVE_OF_SCORPIUS(new Position(2464, 3228, 0), MapRegion.KANDARIN, "Grave of Scorpius"),
        KANDARIN_KHAZARD_BATTLEFIELD(new Position(2518, 3249, 0), MapRegion.KANDARIN, "Khazard Battlefield, in the small ruins south of tracker gnome 2."),
        KANDARIN_WEST_ARDY(new Position(2533, 3320, 0), MapRegion.KANDARIN, "West Ardougne, near the staircase outside the Civic Office."),
        KANDARIN_SW_TREE_GNOME_STRONGHOLD(new Position(2411, 3431, 0), MapRegion.KANDARIN, "South-west Tree Gnome Stronghold"),
        KANDARIN_OUTPOST(new Position(2458, 3364, 0), MapRegion.KANDARIN, "South of the Tree Gnome Stronghold, north-east of the Outpost."),
        KANDARIN_BAXTORIAN_FALLS(new Position(2534, 3479, 0), MapRegion.KANDARIN, "South-east of Almera's house on Baxtorian Falls."),
        KANDARIN_BA_AGILITY_COURSE(new Position(2536, 3546, 0), MapRegion.KANDARIN, "Inside the Barbarian Agility Course. Completion of Alfred Grimhand's Barcrawl is required."),
        KARAMJA_MUSA_POINT(new Position(2914, 3168, 0), MapRegion.KARAMJA, "Musa Point, banana plantation."),
        KARAMJA_BRIMHAVEN_FRUIT_TREE(new Position(2783, 3214, 0), MapRegion.KARAMJA, "Brimhaven, east of the fruit tree patch."),
        KARAMJA_WEST_BRIMHAVEN(new Position(2721, 3169, 0), MapRegion.KARAMJA, "West of Brimhaven."),
        KARAMJA_GLIDER(new Position(2966, 2975, 0), MapRegion.KARAMJA, "West of the gnome glider."),
        KARAMJA_KHARAZI_NE(new Position(2904, 2925, 0), MapRegion.KARAMJA, "North-eastern part of Kharazi Jungle."),
        KARAMJA_KHARAZI_SW(new Position(2783, 2898, 0), MapRegion.KARAMJA, "South-western part of Kharazi Jungle."),
        KARAMJA_CRASH_ISLAND(new Position(2910, 2737, 0), MapRegion.KARAMJA, "Northern part of Crash Island."),
        MISTHALIN_VARROCK_STONE_CIRCLE(new Position(3225, 3355, 0), MapRegion.MISTHALIN, "South of the stone circle near Varrock's entrance."),
        MISTHALIN_LUMBRIDGE(new Position(3238, 3169, 0), MapRegion.MISTHALIN, "Just north-west of the Lumbridge Fishing tutor."),
        MISTHALIN_LUMBRIDGE_2(new Position(3170, 3278, 0), MapRegion.MISTHALIN, "North of the pond between Lumbridge and Draynor Village."),
        MISTHALIN_GERTUDES(new Position(3158, 3421, 0), MapRegion.MISTHALIN, "North-east of Gertrude's house west of Varrock."),
        MISTHALIN_DRAYNOR_BANK(new Position(3096, 3235, 0), MapRegion.MISTHALIN, "South of Draynor Village bank."),
        MISTHALIN_LUMBER_YARD(new Position(3303, 3483, 0), MapRegion.MISTHALIN, "South of Lumber Yard, east of Assistant Serf."),
        MORYTANIA_BURGH_DE_ROTT(new Position(3545, 3253, 0), MapRegion.MORYTANIA, "In the north-east area of Burgh de Rott, by the reverse-L-shaped ruins."),
        MORYTANIA_PORT_PHASMATYS(new Position(3613, 3485, 0), MapRegion.MORYTANIA, "West of Port Phasmatys, south-east of fairy ring."),
        MORYTANIA_HOLLOWS(new Position(3500, 3423, 0), MapRegion.MORYTANIA, "Inside The Hollows, south of the bridge which was repaired in a quest."),
        MORYTANIA_SWAMP(new Position(3422, 3374, 0), MapRegion.MORYTANIA, "Inside the Mort Myre Swamp, north-west of the Nature Grotto."),
        MORYTANIA_HAUNTED_MINE(new Position(3441, 3259, 0), MapRegion.MORYTANIA, "At Haunted Mine quest start."),
        MORYTANIA_MAUSOLEUM(new Position(3499, 3539, 0), MapRegion.MORYTANIA, "South of the Mausoleum."),
        MORYTANIA_MOS_LES_HARMLESS(new Position(3744, 3041, 0), MapRegion.MORYTANIA, "Northern area of Mos Le'Harmless, between the lakes."),
        MORYTANIA_MOS_LES_HARMLESS_BAR(new Position(3670, 2974, 0), MapRegion.MORYTANIA, "Near Mos Le'Harmless southern bar."),
        MORYTANIA_DRAGONTOOTH_NORTH(new Position(3813, 3567, 0), MapRegion.MORYTANIA, "Northern part of Dragontooth Island."),
        MORYTANIA_DRAGONTOOTH_SOUTH(new Position(3803, 3532, 0), MapRegion.MORYTANIA, "Southern part of Dragontooth Island."),
        WESTERN_PROVINCE_EAGLES_PEAK(new Position(2297, 3530, 0), MapRegion.WESTERN_PROVINCE, "North-west of Eagles' Peak."),
        WESTERN_PROVINCE_PISCATORIS(new Position(2337, 3689, 0), MapRegion.WESTERN_PROVINCE, "Piscatoris Fishing Colony"),
        WESTERN_PROVINCE_PISCATORIS_HUNTER_AREA(new Position(2359, 3564, 0), MapRegion.WESTERN_PROVINCE, "Eastern part of Piscatoris Hunter area, south-west of the Falconry."),
        WESTERN_PROVINCE_ARANDAR(new Position(2366, 3318, 0), MapRegion.WESTERN_PROVINCE, "South-west of the crystal gate to Arandar."),
        WESTERN_PROVINCE_ELF_CAMP_EAST(new Position(2270, 3244, 0), MapRegion.WESTERN_PROVINCE, "East of Elf Camp."),
        WESTERN_PROVINCE_ELF_CAMP_NW(new Position(2174, 3280, 0), MapRegion.WESTERN_PROVINCE, "North-west of Elf Camp."),
        WESTERN_PROVINCE_LLETYA(new Position(2335, 3166, 0), MapRegion.WESTERN_PROVINCE, "In Lletya."),
        WESTERN_PROVINCE_TYRAS(new Position(2204, 3157, 0), MapRegion.WESTERN_PROVINCE, "Near Tyras Camp."),
        WESTERN_PROVINCE_ZULANDRA(new Position(2196, 3057, 0), MapRegion.WESTERN_PROVINCE, "The northern house at Zul-Andra."),
        WILDERNESS_5(new Position(3173, 3556, 0), MapRegion.WILDERNESS, "North of the Grand Exchange, level 5 Wilderness."),
        WILDERNESS_12(new Position(3038, 3612, 0), MapRegion.WILDERNESS, "South-east of the Dark Warriors' Fortress, level 12 Wilderness."),
        WILDERNESS_20(new Position(3225, 3676, 0), MapRegion.WILDERNESS, "East of the Corporeal Beast's lair, level 20 Wilderness."),
        WILDERNESS_27(new Position(3174, 3735, 0), MapRegion.WILDERNESS, "Inside the Ruins north of the Graveyard of Shadows, level 27 Wilderness."),
        WILDERNESS_28(new Position(3374, 3734, 0), MapRegion.WILDERNESS, "East of Venenatis' nest, level 28 Wilderness."),
        WILDERNESS_32(new Position(3311, 3773, 0), MapRegion.WILDERNESS, "North of Venenatis' nest, level 32 Wilderness."),
        WILDERNESS_35(new Position(3153, 3795, 0), MapRegion.WILDERNESS, "East of the Wilderness canoe exit, level 35 Wilderness."),
        WILDERNESS_37(new Position(2975, 3811, 0), MapRegion.WILDERNESS, "South-east of the Chaos Temple, level 37 Wilderness."),
        WILDERNESS_38(new Position(3293, 3813, 0), MapRegion.WILDERNESS, "South of Callisto, level 38 Wilderness."),
        WILDERNESS_49(new Position(3140, 3910, 0), MapRegion.WILDERNESS, "South-west of the Deserted Keep, level 49 Wilderness."),
        WILDERNESS_54(new Position(2983, 3946, 0), MapRegion.WILDERNESS, "West of the Wilderness Agility Course, level 54 Wilderness."),
        ZEAH_BLASTMINE_BANK(new Position(1507, 3856, 0), MapRegion.ZEAH, "Next to the bank in the Lovakengj blast mine."),
        ZEAH_BLASTMINE_NORTH(new Position(1488, 3881, 0), MapRegion.ZEAH, "Northern part of the Lovakengj blast mine."),
        ZEAH_LOVAKITE_FURNACE(new Position(1507, 3819, 0), MapRegion.ZEAH, "Next to the lovakite furnace in Lovakengj."),
        ZEAH_LOVAKENGJ_MINE(new Position(1477, 3779, 0), MapRegion.ZEAH, "Next to mithril rock in the Lovakengj mine."),
        ZEAH_SULPHR_MINE(new Position(1428, 3866, 0), MapRegion.ZEAH, "Western entrance in the Lovakengj sulphur mine."),
        ZEAH_SHAYZIEN_BANK(new Position(1517, 3603, 0), MapRegion.ZEAH, "South-east of the bank in Shayzien."),
        ZEAH_OVERPASS(new Position(1467, 3714, 0), MapRegion.ZEAH, "Overpass between Lovakengj and Shayzien."),
        ZEAH_LIZARDMAN(new Position(1493, 3694, 0), MapRegion.ZEAH, "Within Lizardman Canyon, east of the ladder. Requires 5% favour with Shayzien."),
        ZEAH_COMBAT_RING(new Position(1557, 3580, 0), MapRegion.ZEAH, "Shayzien, south-east of the Combat Ring."),
        ZEAH_SHAYZIEN_BANK_2(new Position(1494, 3622, 0), MapRegion.ZEAH, "North-west of the bank in Shayzien."),
        ZEAH_LIBRARY(new Position(1601, 3842, 0), MapRegion.ZEAH, "North-west of the Arceuus Library."),
        ZEAH_HOUSECHURCH(new Position(1682, 3792, 0), MapRegion.ZEAH, "By the entrance to the Arceuus church."),
        ZEAH_DARK_ALTAR(new Position(1699, 3879, 0), MapRegion.ZEAH, "West of the Dark Altar."),
        ZEAH_ARCEUUS_HOUSE(new Position(1708, 3701, 0), MapRegion.ZEAH, "By the southern entrance to Arceuus."),
        ZEAH_ESSENCE_MINE(new Position(1762, 3852, 0), MapRegion.ZEAH, "By the Arceuus essence mine."),
        ZEAH_ESSENCE_MINE_NE(new Position(1772, 3866, 0), MapRegion.ZEAH, "North-east of the Arceuus essence mine."),
        ZEAH_PISCARILUS_MINE(new Position(1768, 3705, 0), MapRegion.ZEAH, "South of the Piscarilius mine."),
        ZEAH_GOLDEN_FIELD_TAVERN(new Position(1718, 3647, 0), MapRegion.ZEAH, "South of The Golden Field tavern in the northern area of Hosidius."),
        ZEAH_MESS_HALL(new Position(1658, 3621, 0), MapRegion.ZEAH, "East of the Mess hall."),
        ZEAH_WATSONS_HOUSE(new Position(1653, 3573, 0), MapRegion.ZEAH, "East of Watson's house."),
        ZEAH_VANNAHS_FARM_STORE(new Position(1806, 3521, 0), MapRegion.ZEAH, "North of Vannah's Farm Store, between the chicken coop and willow trees."),
        ZEAH_FARMING_GUILD_W(new Position(1208, 3736, 0), MapRegion.ZEAH, "West of the Farming Guild."),
        ZEAH_DAIRY_COW(new Position(1320, 3718, 0), MapRegion.ZEAH, "North-east of the Kebos Lowlands, east of the dairy cow."),
        ZEAH_CRIMSON_SWIFTS(new Position(1186, 3583, 0), MapRegion.ZEAH, "South-west of the Kebos Swamp, below the crimson swifts.");

        private final boolean beginner;
        private final Position position;
        private final MapRegion mapRegion;
        private final String description;

        HotCold(Position position, MapRegion mapRegion, String description) {
            this.beginner = false;
            this.position = position;
            this.mapRegion = mapRegion;
            this.description = description;
        }

        HotCold(boolean beginner, Position position, MapRegion mapRegion, String description) {
            this.beginner = beginner;
            this.position = position;
            this.mapRegion = mapRegion;
            this.description = description;
        }
    }
}