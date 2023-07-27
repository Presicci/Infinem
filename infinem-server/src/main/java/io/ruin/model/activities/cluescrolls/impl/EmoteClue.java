package io.ruin.model.activities.cluescrolls.impl;

import com.google.common.collect.ImmutableMap;
import io.ruin.cache.ItemDef;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StashUnits;
import io.ruin.model.entity.shared.AttributeKey;
import io.ruin.model.entity.npc.actions.clues.Uri;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.TabEmote;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/20/2021
 */
public class EmoteClue extends Clue {

    String clue;
    public List<Integer> equipment;
    List<Integer> emptyEquipmentSlots;
    List<TabEmote> emotes;
    int setSize;

    public EmoteClue(String clue, ClueType type, List<Integer> equipment, List<Integer> emptyEquipmentSlots, List<TabEmote> emotes) {
        super(type);
        this.clue = clue;
        this.equipment = equipment;
        this.emptyEquipmentSlots = emptyEquipmentSlots;
        this.emotes = emotes;
    }
    public EmoteClue(String clue, ClueType type, List<Integer> equipment, List<Integer> emptyEquipmentSlots, List<TabEmote> emotes, int setSize) {
        super(type);
        this.clue = clue;
        this.equipment = equipment;
        this.emptyEquipmentSlots = emptyEquipmentSlots;
        this.emotes = emotes;
        this.setSize = setSize;
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    public void spawnUri(Player player, TabEmote emote) {
        if (emote != this.emotes.get(0) || !equipmentCheck(player) || player.hasTemporaryAttribute(AttributeKey.URI_SPAWNED)) {
            return;
        }
        Uri npc = new Uri(player, this);
        npc.addEvent(e -> {
            int loops = 0;
            while(player.isOnline()) {
                if (player.getPosition().distance(npc.getPosition()) > 10 || loops > 15) {
                    npc.remove();
                }
                ++loops;
                e.delay(10);
            }
            npc.remove();
        });
    }

    public static final Map<String, String> STRING_REPLACEMENTS = ImmutableMap.<String, String>builder()
            .put("Team-1 cape", "Team cape")
            .put("Ring of dueling(1)", "Ring of dueling")
            .put("Saradomin stole", "vestment stole")
            .put("Rune shield (h1)", "rune heraldic shield")
            .put("Rune helm (h1)", "rune heraldic helm")
            .put("Red headband", "headband")
            .put("Saradomin crozier", "crozier")
            .put("Amulet of glory(1)", "Amulet of glory")
            .put("Holy book", "completed god book")
            .put("Saradomin mitre", "mitre")
            .put("Castle wars bracelet(1)", "Castle wars bracelet")
            .put("Bob's red shirt", "bob shirt")
            .put("New crystal bow", "Crystal bow")
            .build();

    private static final Map<Integer, List<Integer>> ITEM_ALTERNATIVES = ImmutableMap.<Integer, List<Integer>>builder()
            .put(Items.TEAM1_CAPE, IntStream.range(Items.TEAM1_CAPE, Items.TEAM50_CAPE).boxed().collect(Collectors.toList()))
            .put(Items.RING_OF_DUELING_1, IntStream.range(Items.RING_OF_DUELING_8, Items.RING_OF_DUELING_1).boxed().collect(Collectors.toList()))
            .put(Items.SARADOMIN_STOLE, Arrays.asList(Items.SARADOMIN_STOLE, Items.GUTHIX_STOLE, Items.ZAMORAK_STOLE, Items.ANCIENT_STOLE, Items.ARMADYL_STOLE, Items.BANDOS_STOLE))
            .put(Items.RUNE_SHIELD_H1, Arrays.asList(Items.RUNE_SHIELD_H1, Items.RUNE_SHIELD_H2, Items.RUNE_SHIELD_H3, Items.RUNE_SHIELD_H4, Items.RUNE_SHIELD_H5))
            .put(Items.RUNE_HELM_H1, Arrays.asList(Items.RUNE_HELM_H1, Items.RUNE_HELM_H2, Items.RUNE_HELM_H3, Items.RUNE_HELM_H4, Items.RUNE_HELM_H5))
            .put(Items.RED_HEADBAND, Arrays.asList(Items.RED_HEADBAND, Items.BLACK_HEADBAND, Items.BROWN_HEADBAND, Items.WHITE_HEADBAND, Items.BLUE_HEADBAND, Items.GOLD_HEADBAND, Items.PINK_HEADBAND, Items.GREEN_HEADBAND))
            .put(Items.SARADOMIN_CROZIER, Arrays.asList(Items.SARADOMIN_CROZIER, Items.GUTHIX_CROZIER, Items.ZAMORAK_CROZIER, Items.ANCIENT_CROZIER, Items.ARMADYL_CROZIER, Items.BANDOS_CROZIER))
            .put(Items.AMULET_OF_GLORY_1, Arrays.asList(Items.AMULET_OF_GLORY, Items.AMULET_OF_GLORY_1, Items.AMULET_OF_GLORY_2, Items.AMULET_OF_GLORY_3, Items.AMULET_OF_GLORY_4, Items.AMULET_OF_ETERNAL_GLORY))
            .put(Items.HOLY_BOOK, Arrays.asList(Items.HOLY_BOOK, Items.UNHOLY_BOOK, Items.BOOK_OF_BALANCE, Items.BOOK_OF_WAR, Items.BOOK_OF_LAW, Items.BOOK_OF_DARKNESS))
            .put(Items.SARADOMIN_MITRE, Arrays.asList(Items.SARADOMIN_MITRE, Items.GUTHIX_MITRE, Items.ZAMORAK_MITRE, Items.ANCIENT_MITRE, Items.ARMADYL_MITRE, Items.BANDOS_MITRE))
            .put(Items.COMBAT_BRACELET, Arrays.asList(Items.COMBAT_BRACELET, Items.COMBAT_BRACELET_1, Items.COMBAT_BRACELET_2, Items.COMBAT_BRACELET_3, Items.COMBAT_BRACELET_4, Items.COMBAT_BRACELET_5, Items.COMBAT_BRACELET_6))
            .put(Items.PIRATE_BANDANA, Arrays.asList(Items.PIRATE_BANDANA, Items.PIRATE_BANDANA_2, Items.PIRATE_BANDANA_3, Items.PIRATE_BANDANA_4, Items.PIRATE_BANDANA_5))
            .put(Items.CASTLE_WARS_BRACELET_1, Arrays.asList(Items.CASTLE_WARS_BRACELET_1, Items.CASTLE_WARS_BRACELET_2, Items.CASTLE_WARS_BRACELET_3))
            .put(Items.BOBS_RED_SHIRT, Arrays.asList(Items.BOBS_RED_SHIRT, Items.BOBS_BLUE_SHIRT, Items.BOBS_GREEN_SHIRT, Items.BOBS_BLACK_SHIRT, Items.BOBS_PURPLE_SHIRT))
            .put(Items.LAVA_BATTLESTAFF, Arrays.asList(Items.LAVA_BATTLESTAFF, 21198))
            .put(22370, Arrays.asList(22370, 22368))
            .put(Items.DRAGON_DEFENDER, Arrays.asList(Items.DRAGON_DEFENDER, 22322, Items.DRAGON_DEFENDER_T))
            .put(Items.SLAYER_HELMET, Arrays.asList(Items.SLAYER_HELMET, Items.SLAYER_HELMET_I, Items.BLACK_SLAYER_HELMET, Items.BLACK_SLAYER_HELMET_I, Items.GREEN_SLAYER_HELMET, Items.GREEN_SLAYER_HELMET_I, Items.RED_SLAYER_HELMET, Items.RED_SLAYER_HELMET_I,
                    Items.PURPLE_SLAYER_HELMET, 21266, // Purple
                    21888, 21890, // Turquoise
                    23073, 23075, // Hydra
                    24370, 24444, // Twisted
                    25898, 25900, // Tztok
                    25904, 25906, // Vampyric
                    25910, 25912)) // Tzkal
            .put(23983, Arrays.asList(23983, 23985, // Crystal bows
                    25862, 25865, 25867, 25869, 25884, 25886, 25888, 25890, 25892, 25894, 25896))   // Bow of Faerdhinen
            .put(Items.IBANS_STAFF, Arrays.asList(Items.IBANS_STAFF, Items.IBANS_STAFF_U))
            .put(Items.RING_OF_WEALTH, Arrays.asList(Items.RING_OF_WEALTH, Items.RING_OF_WEALTH_1, Items.RING_OF_WEALTH_2, Items.RING_OF_WEALTH_3, Items.RING_OF_WEALTH_4, Items.RING_OF_WEALTH_5,
                    Items.RING_OF_WEALTH_I, Items.RING_OF_WEALTH_I1, Items.RING_OF_WEALTH_I2, Items.RING_OF_WEALTH_I3, Items.RING_OF_WEALTH_I4, Items.RING_OF_WEALTH_I5))
            .put(Items.BANDOS_GODSWORD, Arrays.asList(Items.BANDOS_GODSWORD, Items.BANDOS_GODSWORD_OR))
            .put(Items.ABYSSAL_WHIP, Arrays.asList(Items.ABYSSAL_WHIP, Items.FROZEN_ABYSSAL_WHIP, Items.VOLCANIC_ABYSSAL_WHIP, 26482))
            .put(Items.ZAMORAK_GODSWORD, Arrays.asList(Items.ZAMORAK_GODSWORD, Items.ZAMORAK_GODSWORD_OR))
            .put(Items.AMULET_OF_THE_DAMNED, Arrays.asList(Items.AMULET_OF_THE_DAMNED, Items.AMULET_OF_THE_DAMNED_FULL))
            .put(Items.DRAGON_PICKAXE, Arrays.asList(Items.DRAGON_PICKAXE, Items.DRAGON_PICKAXE_OR, Items.CRYSTAL_PICKAXE, 23682))
            .put(Items.DRAGON_AXE, Arrays.asList(Items.DRAGON_AXE, Items.CRYSTAL_AXE, 23675))
            .put(Items.DRAGON_PLATESKIRT, Arrays.asList(Items.DRAGON_PLATESKIRT, Items.DRAGON_PLATESKIRT_G))
            .put(Items.DRAGON_CHAINBODY, Arrays.asList(Items.DRAGON_CHAINBODY, Items.DRAGON_CHAINBODY_G))
            .put(Items.DRAGON_SQ_SHIELD, Arrays.asList(Items.DRAGON_SQ_SHIELD, Items.DRAGON_SQ_SHIELD_G))
            .put(Items.RED_BOATER, Arrays.asList(Items.RED_BOATER, Items.ORANGE_BOATER, Items.GREEN_BOATER, Items.BLUE_BOATER, Items.BLACK_BOATER, Items.PINK_BOATER, Items.PURPLE_BOATER, Items.WHITE_BOATER))
            .put(Items.PHARAOHS_SCEPTRE, Arrays.asList(Items.PHARAOHS_SCEPTRE, Items.PHARAOHS_SCEPTRE_1, Items.PHARAOHS_SCEPTRE_2, Items.PHARAOHS_SCEPTRE_3, Items.PHARAOHS_SCEPTRE_4, Items.PHARAOHS_SCEPTRE_5, Items.PHARAOHS_SCEPTRE_6, Items.PHARAOHS_SCEPTRE_7, Items.PHARAOHS_SCEPTRE_8))
            .build();

    public static Item getAlternative(Player player, int itemId, boolean ignoreIventory) {
        Item item = player.getInventory().findItemIgnoringAttributes(itemId, false);
        if (item != null && !ignoreIventory) {
            return item;
        }
        item = player.getEquipment().findItemIgnoringAttributes(itemId, false);
        if (item != null) {
            return item;
        }
        List<Integer> alternatives = ITEM_ALTERNATIVES.get(itemId);
        if (alternatives == null) {
            return null;
        }
        item = player.getInventory().findFirst(alternatives.stream().mapToInt(Integer::intValue).toArray());
        if (item != null)
            return item;
        Item equipped = player.getEquipment().get(ItemDef.get(itemId).equipSlot);
        if (equipped != null && alternatives.contains(equipped.getId())) {
            return equipped;
        } else {
            return null;
        }
    }

    public boolean hasPerformedSecondEmote(Player player) {
        TabEmote emote = player.getTemporaryAttributeOrDefault(AttributeKey.LAST_EMOTE, null);
        return emotes.size() == 1 || (emote != null && emote == emotes.get(1));
    }

    /**
     * Does both the isWearingEquipment and isNotWearingEquipment checks
     * @param player The player completing the clue step.
     * @return True if the player passes both checks.
     */
    public boolean equipmentCheck(Player player) {
        if (!isWearingEquipment(player)) {
            return false;
        }
        return isNotWearingEquipment(player);
    }

    /**
     * Checks if the player has the required items equipped.
     * @param player The player completing the clue step.
     * @return True if the player is wearing all the required pieces.
     */
    private boolean isWearingEquipment(Player player) {
        if (equipment.isEmpty()) {
            return true;
        }
        if (setSize > 0) {
            setLoop: for (int index = 0; index < equipment.size() / setSize; index++) {
                int leftIndex = index * setSize;
                List<Integer> set = equipment.subList(leftIndex, leftIndex + setSize);
                int count = 0;
                for (int item : set) {
                    if (!player.getEquipment().contains(item) && getAlternative(player, item, true) == null) {
                        continue setLoop;
                    }
                    count++;
                }
                if (count == setSize) {
                    return true;
                }
            }
            return false;
        } else {
            for (int item : equipment) {
                if (!player.getEquipment().contains(item)) {
                    return getAlternative(player, item, true) != null;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the player has the required slots empty.
     * @param player The player completing the clue step.
     * @return True if not wearing anything in the required slots.
     */
    private boolean isNotWearingEquipment(Player player) {
        if (emptyEquipmentSlots.isEmpty()) {
            return true;
        }
        for (int slotId : emptyEquipmentSlots) {
            if (player.getEquipment().get(slotId) != null) {
                return false;
            }
        }
        return true;
    }

    static final List<Integer> allSlots = Arrays.asList(
            Equipment.SLOT_HAT, Equipment.SLOT_AMMO, Equipment.SLOT_AMULET, Equipment.SLOT_CAPE, Equipment.SLOT_CHEST, Equipment.SLOT_FEET,
            Equipment.SLOT_HANDS, Equipment.SLOT_LEGS, Equipment.SLOT_RING, Equipment.SLOT_SHIELD, Equipment.SLOT_WEAPON
    );

    public enum EmoteClueData {
        /*
         * Beginner
         */
        GYPSY_ARIS("Blow a raspberry at Gypsy Aris in her tent. Equip a gold ring and a gold necklace.",
                Collections.singletonList(TabEmote.RASPBERRY), new Bounds(3201, 3422, 3205, 3426, 0),
                Arrays.asList(Items.GOLD_RING, Items.GOLD_NECKLACE), ClueType.BEGINNER, Config.STASH_UNITS[0], 34736),
        BRUGSEN_BURSEN("Bow to Brugsen Bursen at the Grand Exchange.",
                Collections.singletonList(TabEmote.BOW), new Bounds(3161, 3474, 3168, 3479, 0),
                Collections.emptyList(), ClueType.BEGINNER),
        IFFIE_NITTER("Cheer at Iffie Nitter. Equip a chef hat and a red cape.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(3204, 3414, 3209, 3418, 0),
                Arrays.asList(Items.CHEFS_HAT, Items.RED_CAPE), ClueType.BEGINNER, Config.STASH_UNITS[2], 34737),
        BOBS_AXES("Clap at Bob's Brilliant Axes. Equip a bronze axe and leather boots.",
                Collections.singletonList(TabEmote.CLAP), new Bounds(3228, 3201, 3233, 3205, 0),
                Arrays.asList(Items.BRONZE_AXE, Items.LEATHER_BOOTS), ClueType.BEGINNER, Config.STASH_UNITS[1], 34738),
        AL_KHARID_MINE("Panic at Al Kharid mine.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(3292, 3271, 3306, 3317, 0),
                Collections.emptyList(), ClueType.BEGINNER),
        FLYNNS_MACE_SHOP("Spin at Flynn's Mace Shop.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(2948, 3385, 2952, 3388, 0),
                Collections.emptyList(), ClueType.BEGINNER),

        /*
         * Easy
         */
        MONKEY_CAGE("Blow a raspberry at the monkey cage in Ardougne Zoo. Equip a studded leather body, bronze platelegs and a normal staff with no orb.",
                Collections.singletonList(TabEmote.RASPBERRY), new Bounds(2597, 3272, 2612, 3285, 0),
                Arrays.asList(Items.STUDDED_BODY, Items.BRONZE_PLATELEGS, Items.STAFF), ClueType.EASY, Config.STASH_UNITS[3], 28975),
        KEEP_LE_FAYE("Blow raspberries outside the entrance to Keep Le Faye. Equip a coif, an iron platebody and leather gloves.",
                Collections.singletonList(TabEmote.BLOW_KISS), new Bounds(2753, 3397, 2762, 3403, 0),
                Arrays.asList(Items.COIF, Items.IRON_PLATEBODY, Items.LEATHER_GLOVES), ClueType.EASY, Config.STASH_UNITS[4], 28978),
        DUEL_ARENA_OFFICE("Bow in the ticket office of the Duel Arena. Equip an iron chain body, leather chaps and coif.",
                Collections.singletonList(TabEmote.BOW), new Bounds(3311, 3240, 3316, 3244, 0),
                Arrays.asList(Items.IRON_CHAINBODY, Items.LEATHER_CHAPS, Items.COIF), ClueType.EASY, Config.STASH_UNITS[5], 28982),
        LEGENDS_GUILD_ENTRANCE("Bow outside the entrance to the Legends' Guild. Equip iron platelegs, an emerald amulet and an oak longbow.",
                Collections.singletonList(TabEmote.BOW), new Bounds(2725, 3347, 2731, 3349, 0),
                Arrays.asList(Items.IRON_PLATELEGS, Items.EMERALD_AMULET, Items.OAK_LONGBOW), ClueType.EASY, Config.STASH_UNITS[6], 28962),
        DRUIDS_CIRCLE("Cheer at the Druids' Circle. Equip a blue wizard hat, a bronze two-handed sword and HAM boots.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(2920, 3477, 2932, 3488, 0),
                Arrays.asList(Items.BLUE_WIZARD_HAT, Items.BRONZE_2H_SWORD, Items.HAM_BOOTS), ClueType.EASY, Config.STASH_UNITS[7], 28973),
        GAMES_ROOM("Cheer at the games room. Have nothing equipped at all when you do.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(2194, 4946, 2221, 4973, 0),
                Collections.emptyList(), allSlots, ClueType.EASY),
        PORT_SARIM("Cheer for the monks at Port Sarim. Equip a coif, steel plateskirt and a sapphire necklace.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(3043, 3234, 3052, 3237, 0),
                Arrays.asList(Items.COIF, Items.STEEL_PLATESKIRT, Items.SAPPHIRE_NECKLACE), ClueType.EASY, Config.STASH_UNITS[8], 28964),
        EXAM_CENTRE("Clap in the main exam room in the Exam Centre. Equip a white apron, green gnome boots and leather gloves.",
                Collections.singletonList(TabEmote.CLAP), new Bounds(3357, 3332, 3367, 3348, 0),
                Arrays.asList(Items.WHITE_APRON, Items.GREEN_BOOTS, Items.LEATHER_GLOVES), ClueType.EASY, Config.STASH_UNITS[9], 28980),
        WIZARDS_TOWER_CAUSEWAY("Clap on the causeway to the Wizards' Tower. Equip an iron medium helmet, emerald ring and a white apron.",
                Collections.singletonList(TabEmote.CLAP), new Bounds(3112, 3173, 3115, 3207, 0),
                Arrays.asList(Items.WHITE_APRON, Items.IRON_MED_HELM, Items.EMERALD_RING), ClueType.EASY, Config.STASH_UNITS[10], 28959),
        EAST_ARDOUGNE_MILL("Clap on the top level of the mill, north of East Ardougne. Equip a blue gnome robe top, HAM robe bottom and an unenchanted tiara.",
                Collections.singletonList(TabEmote.CLAP), new Bounds(2629, 3383, 2635, 3389, 2),
                Arrays.asList(Items.BLUE_ROBE_TOP, Items.HAM_ROBE, Items.TIARA), ClueType.EASY, Config.STASH_UNITS[11], 28971),
        FISHING_GUILD("Dance a jig by the entrance to the Fishing Guild. Equip an emerald ring, a sapphire amulet, and a bronze chain body.",
                Collections.singletonList(TabEmote.JIG), new Bounds(2605, 3388, 2616, 3393, 0),
                Arrays.asList(Items.EMERALD_RING, Items.SAPPHIRE_AMULET, Items.BRONZE_CHAINBODY), ClueType.EASY, Config.STASH_UNITS[12], 28977),
        DRAYNOR_CROSSROAD("Dance at the crossroads north of Draynor. Equip an iron chain body, a sapphire ring and a longbow.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(3108, 3293, 3111, 3296, 0),
                Arrays.asList(Items.IRON_CHAINBODY, Items.SAPPHIRE_RING, Items.LONGBOW), ClueType.EASY, Config.STASH_UNITS[13], 28968),
        GRAND_EXCHANGE_ENTRANCE("Dance at the entrance to the Grand Exchange. Equip a pink skirt, pink robe top and a body tiara.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(3162, 3464, 3168, 3469, 0),
                Arrays.asList(Items.PINK_SKIRT, Items.PINK_ROBE_TOP, Items.BODY_TIARA), ClueType.EASY, Config.STASH_UNITS[14], 28985),
        PARTY_ROOM("Dance in the Party Room. Equip a steel full helmet, steel platebody and an iron plateskirt.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(3037, 3372, 3053, 3384, 0),
                Arrays.asList(Items.STEEL_FULL_HELM, Items.STEEL_PLATEBODY, Items.IRON_PLATESKIRT), ClueType.EASY, Config.STASH_UNITS[15], 28972),
        LUMBRIDGE_SWAMP_SHACK("Dance in the shack in Lumbridge Swamp. Equip a bronze dagger, iron full helmet and a gold ring.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(3202, 3167, 3205, 3170, 0),
                Arrays.asList(Items.BRONZE_DAGGER, Items.IRON_FULL_HELM, Items.GOLD_RING), ClueType.EASY, Config.STASH_UNITS[16], 28958),
        RUNE_STORE("Do a jig in Varrock's rune store. Equip an air tiara and a staff of water.",
                Collections.singletonList(TabEmote.JIG), new Bounds(3252, 3399, 3255, 3403, 0),
                Arrays.asList(Items.AIR_TIARA, Items.STAFF_OF_WATER), ClueType.EASY, Config.STASH_UNITS[17], 28986),
        AL_KHARID_MINE_2("Headbang in the mine north of Al Kharid. Equip a desert shirt, leather gloves and leather boots.",
                Collections.singletonList(TabEmote.HEAD_BANG), new Bounds(3292, 3271, 3306, 3317, 0),
                Arrays.asList(Items.DESERT_SHIRT, Items.LEATHER_GLOVES, Items.LEATHER_BOOTS), ClueType.EASY, Config.STASH_UNITS[18], 28965),
        BEEHIVES("Jump for joy at the beehives. Equip a desert shirt, green gnome robe bottoms and a steel axe.",
                Collections.singletonList(TabEmote.JUMP_FOR_JOY), new Bounds(2752, 3437, 2765, 3449, 0),
                Arrays.asList(Items.DESERT_SHIRT, Items.GREEN_ROBE_BOTTOMS, Items.STEEL_AXE), ClueType.EASY, Config.STASH_UNITS[19], 28974),
        CROSSROAD_SINCLAIR_MANSION("Laugh at the crossroads south of the Sinclair mansion. Equip a cowl, a blue wizard robe top and an iron scimitar.",
                Collections.singletonList(TabEmote.LAUGH), new Bounds(2736, 3530, 2744, 3539, 0),
                Arrays.asList(Items.LEATHER_COWL, Items.IRON_SCIMITAR, Items.BLUE_WIZARD_ROBE), ClueType.EASY, Config.STASH_UNITS[20], 28979),
        LIMESTONE_MINE("Panic in the Limestone Mine. Equip bronze platelegs, a steel pickaxe and a steel medium helmet.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(3367, 3495, 3376, 3502, 0),
                Arrays.asList(Items.BRONZE_PLATELEGS, Items.STEEL_PICKAXE, Items.STEEL_MED_HELM), ClueType.EASY, Config.STASH_UNITS[21], 28961),
        FISHING_TRAWLER("Panic on the pier where you catch the Fishing trawler. Have nothing equipped at all when you do.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(2675, 3163, 2677, 3174, 0),
                Collections.emptyList(), allSlots, ClueType.EASY),
        RIMMINGTON_MINE("Shrug in the mine near Rimmington. Equip a gold necklace, a gold ring and a bronze spear.",
                Collections.singletonList(TabEmote.SHRUG), new Bounds(2967, 3229, 2987, 3250, 0),
                Arrays.asList(Items.GOLD_NECKLACE, Items.GOLD_RING, Items.BRONZE_SPEAR), ClueType.EASY, Config.STASH_UNITS[22], 28969),
        RIMMINGTON_CROSSROADS("Spin at the crossroads north of Rimmington. Equip a green gnome hat, cream gnome top and leather chaps.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(2976, 3273, 2987, 3280, 0),
                Arrays.asList(Items.GREEN_HAT, Items.CREAM_ROBE_TOP, Items.LEATHER_CHAPS), ClueType.EASY, Config.STASH_UNITS[23], 28976),
        DRAYNOR_MANOR("Spin in Draynor Manor by the fountain. Equip an iron platebody, studded leather chaps and a bronze full helmet.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(3085, 3332, 3090, 3337, 0),
                Arrays.asList(Items.IRON_PLATEBODY, Items.STUDDED_CHAPS, Items.BRONZE_FULL_HELM), ClueType.EASY, Config.STASH_UNITS[24], 28966),
        VARROCK_CASTLE_COURTYARD("Spin in the Varrock Castle courtyard. Equip a black axe, a coif and a ruby ring.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(3204, 3459, 3224, 3464, 0),
                Arrays.asList(Items.BLACK_AXE, Items.COIF, Items.RUBY_RING), ClueType.EASY, Config.STASH_UNITS[25], 28983),
        WHEAT_FIELD_LUMBRIDGE("Think in middle of the wheat field by the Lumbridge mill. Equip a blue gnome robetop, a turquoise gnome robe bottom and an oak shortbow.",
                Collections.singletonList(TabEmote.THINK), new Bounds(3154, 3295, 3162, 3303, 0),
                Arrays.asList(Items.BLUE_ROBE_TOP, Items.TURQUOISE_ROBE_BOTTOMS, Items.OAK_SHORTBOW), ClueType.EASY, Config.STASH_UNITS[26], 28967),
        SOUTH_FENCE_LUMBER_YARD("Wave along the south fence of the Lumber Yard. Equip a hard leather body, leather chaps and a bronze axe.",
                Collections.singletonList(TabEmote.WAVE), new Bounds(3305, 3489, 3311, 3492, 0),
                Arrays.asList(Items.HARDLEATHER_BODY, Items.LEATHER_CHAPS, Items.BRONZE_AXE), ClueType.EASY, Config.STASH_UNITS[27], 28981),
        FALADOR_GEM_STORE("Wave in the Falador gem store. Equip a Mithril pickaxe, Black platebody and an Iron Kiteshield.",
                Collections.singletonList(TabEmote.WAVE), new Bounds(2944, 3332, 2946, 3337, 0),
                Arrays.asList(Items.MITHRIL_PICKAXE, Items.BLACK_PLATEBODY, Items.IRON_KITESHIELD), ClueType.EASY, Config.STASH_UNITS[28], 28984),
        MUDSKIPPER_POINT("Wave on Mudskipper Point. Equip a black cape, leather chaps and a steel mace.",
                Collections.singletonList(TabEmote.WAVE), new Bounds(2984, 3107, 2995, 3115, 0),
                Arrays.asList(Items.BLACK_CAPE, Items.LEATHER_CHAPS, Items.STEEL_MACE), ClueType.EASY, Config.STASH_UNITS[29], 28963),
        DRAYNOR_MARKETPLACE("Yawn in Draynor Marketplace. Equip studded leather chaps, an iron kiteshield and a steel longsword.",
                Collections.singletonList(TabEmote.YAWN), new Bounds(3074, 3245, 3086, 3255, 0),
                Arrays.asList(Items.STUDDED_CHAPS, Items.IRON_KITESHIELD, Items.STEEL_LONGSWORD), ClueType.EASY, Config.STASH_UNITS[30], 28960),
        VARROCK_LIBRARY("Yawn in the Varrock library. Equip a green gnome robe top, HAM robe bottom and an iron warhammer.",
                Collections.singletonList(TabEmote.YAWN), new Bounds(3207, 3490, 3214, 3497, 0),
                Arrays.asList(Items.GREEN_ROBE_TOP, Items.HAM_ROBE, Items.IRON_WARHAMMER), ClueType.EASY, Config.STASH_UNITS[31], 28970),

        /*
         * Medium
         */
        TAI_BWO_WANNAI("Beckon in Tai Bwo Wannai. Clap before you talk to me. Equip green dragonhide chaps, a ring of dueling and a mithril medium helmet.",
                Collections.singletonList(TabEmote.BECKON), new Bounds(2774, 3061, 2815, 3076, 0),
                Arrays.asList(Items.GREEN_DHIDE_VAMB, Items.RING_OF_DUELING_1, Items.MITHRIL_MED_HELM), ClueType.MEDIUM, Config.STASH_UNITS[32],28990),
        SHAYZIEN_COMBAT_RING("Beckon in the combat ring of Shayzien. Show your anger before you talk to me. Equip an adamant platebody, adamant full helm and adamant platelegs.",
                Collections.singletonList(TabEmote.BECKON), new Bounds(1536, 3585, 1552, 3602, 0),
                Arrays.asList(Items.ADAMANT_PLATELEGS, Items.ADAMANT_PLATEBODY, Items.ADAMANT_FULL_HELM), ClueType.MEDIUM, Config.STASH_UNITS[33], 29006),
        DIGSITE("Beckon in the Digsite, near the eastern winch. Bow before you talk to me. Equip a green gnome hat, snakeskin boots and an iron pickaxe.",
                Collections.singletonList(TabEmote.BECKON), new Bounds(3367, 3423, 3372, 3430, 0),
                Arrays.asList(Items.IRON_PICKAXE, Items.SNAKESKIN_BOOTS, Items.GREEN_HAT), ClueType.MEDIUM, Config.STASH_UNITS[34], 28997),
        BARBARIAN_AGILITY("Cheer in the Barbarian Agility Arena. Headbang before you talk to me. Equip a steel platebody, maple shortbow and a Wilderness cape.",
                Arrays.asList(TabEmote.CHEER, TabEmote.HEAD_BANG), new Bounds(2529, 3542, 2553, 3559, 0),
                Arrays.asList(Items.STEEL_PLATEBODY, Items.MAPLE_SHORTBOW, Items.TEAM1_CAPE), ClueType.MEDIUM, Config.STASH_UNITS[35], 28992),
        EDGEVILLE_GENERAL("Cheer in the Edgeville general store. Dance before you talk to me. Equip a brown apron, leather boots and leather gloves.",   // TODO have to reimport edgeville to get this to work
                Arrays.asList(TabEmote.CHEER, TabEmote.DANCE), new Bounds(1, 1, 1, 1, 0),
                Arrays.asList(Items.BROWN_APRON, Items.LEATHER_BOOTS, Items.LEATHER_GLOVES), ClueType.MEDIUM, Config.STASH_UNITS[36], 1),
        OGRE_PEN("Cheer in the Ogre Pen in the Training Camp. Show you are angry before you talk to me. Equip a green dragonhide body and chaps and a steel square shield.",
                Arrays.asList(TabEmote.CHEER, TabEmote.ANGRY), new Bounds(2523, 3373, 2533, 3377, 0),
                Arrays.asList(Items.GREEN_DHIDE_BODY, Items.GREEN_DHIDE_CHAPS, Items.STEEL_SQ_SHIELD), ClueType.MEDIUM, Config.STASH_UNITS[37], 28996),
        COURT_HOUSE("Clap in Seers court house. Spin before you talk to me. Equip an adamant halberd, blue mystic robe bottom and a diamond ring.",
                Arrays.asList(TabEmote.CLAP, TabEmote.SPIN), new Bounds(2732, 3467, 2739, 3471, 0),
                Arrays.asList(Items.ADAMANT_HALBERD, Items.MYSTIC_ROBE_BOTTOM, Items.DIAMOND_RING), ClueType.MEDIUM, Config.STASH_UNITS[38], 29002),
        MOUNT_KARUULM("Clap your hands north of Mount Karuulm Spin before you talk to me. Equip an adamant warhammer, a ring of life and a pair of mithril boots.",
                Arrays.asList(TabEmote.CLAP, TabEmote.SPIN), new Bounds(1298, 3836, 1317, 3842, 0),
                Arrays.asList(Items.ADAMANT_WARHAMMER, Items.MITHRIL_BOOTS, Items.RING_OF_LIFE), ClueType.MEDIUM, Config.STASH_UNITS[39], 34647),
        CATHERBY_RANGING_SHOP("Cry in the Catherby Ranging shop. Bow before you talk to me. Equip blue gnome boots, a hard leather body and an unblessed silver sickle.",
                Arrays.asList(TabEmote.CRY, TabEmote.BOW), new Bounds(2821, 3441, 2825, 3445, 0),
                Arrays.asList(Items.BLUE_BOOTS, Items.HARDLEATHER_BODY, Items.SILVER_SICKLE), ClueType.MEDIUM, Config.STASH_UNITS[40], 28998),
        DRAYNO_JAIL("Cry in the Draynor Village jail. Jump for joy before you talk to me. Equip an adamant sword, a sapphire amulet and an adamant plateskirt.",
                Arrays.asList(TabEmote.CRY, TabEmote.JUMP_FOR_JOY), new Bounds(3121, 3240, 3130, 3246, 0),
                Arrays.asList(Items.ADAMANT_LONGSWORD, Items.SAPPHIRE_AMULET, Items.ADAMANT_PLATESKIRT), ClueType.MEDIUM, Config.STASH_UNITS[41], 29008),
        CATHERBY_BEACH("Cry on the shore of Catherby beach. Laugh before you talk to me, equip an adamant sq shield, a bone dagger and mithril platebody.",
                Arrays.asList(TabEmote.CRY, TabEmote.LAUGH), new Bounds(2831, 3423, 2860, 3437, 0),
                Arrays.asList(Items.ADAMANT_SQ_SHIELD, Items.BONE_DAGGER, Items.MITHRIL_PLATEBODY), ClueType.MEDIUM, Config.STASH_UNITS[42], 29003),
        GNOME_AGILITY("Cry on top of the western tree in the Gnome Agility Arena. Indicate 'no' before you talk to me. Equip a steel kiteshield, ring of forging and green dragonhide chaps.",
                Arrays.asList(TabEmote.CRY, TabEmote.NO), new Bounds(2472, 3418, 2477, 3423, 2),
                Arrays.asList(Items.STEEL_KITESHIELD, Items.RING_OF_FORGING, Items.GREEN_DHIDE_CHAPS), ClueType.MEDIUM, Config.STASH_UNITS[43], 28993),
        SHANTAY_PASS("Dance a jig under Shantay's Awning. Bow before you talk to me. Equip a pointed blue snail helmet, an air staff and a bronze square shield.",
                Arrays.asList(TabEmote.JIG, TabEmote.BOW), new Bounds(3301, 3121, 3306, 3125, 0),
                Arrays.asList(Items.BRUISE_BLUE_SNELM_2, Items.STAFF_OF_AIR, Items.BRONZE_SQ_SHIELD), ClueType.MEDIUM, Config.STASH_UNITS[44], 28999),
        CANIFIS("Dance in the centre of Canifis. Bow before you talk to me. Equip a green gnome robe top, mithril plate legs and an iron two-handed sword.",
                Arrays.asList(TabEmote.DANCE, TabEmote.BOW), new Bounds(3485, 3479, 3501, 3498, 0),
                Arrays.asList(Items.MITHRIL_PLATELEGS, Items.IRON_2H_SWORD, Items.GREEN_ROBE_TOP), ClueType.MEDIUM, Config.STASH_UNITS[45], 28987),
        LUMBRIDGE_SWAMP_CAVE("Dance in the dark caves beneath Lumbridge Swamp. Blow a kiss before you talk to me. Equip an air staff, Bronze full helm and an amulet of power.",
                Arrays.asList(TabEmote.CRY, TabEmote.JUMP_FOR_JOY), new Bounds(3165, 9567, 3175, 9574, 0),
                Arrays.asList(Items.STAFF_OF_AIR, Items.BRONZE_FULL_HELM, Items.AMULET_OF_POWER), ClueType.MEDIUM, Config.STASH_UNITS[46], 29000),
        TZHAAR_SWORD_SHOP("Jump for joy in the TzHaar sword shop. Shrug before you talk to me. Equip a Steel longsword, Blue D'hide body and blue mystic gloves.",
                Arrays.asList(TabEmote.JUMP_FOR_JOY, TabEmote.SHRUG), new Bounds(2475, 5144, 2480, 5148, 0),
                Arrays.asList(Items.STEEL_LONGSWORD, Items.BLUE_DHIDE_BODY, Items.MYSTIC_GLOVES), ClueType.MEDIUM, Config.STASH_UNITS[47], 29004),
        YANILLE_BANK("Jump for joy in Yanille bank. Dance a jig before you talk to me. Equip a brown apron, adamantite medium helmet and snakeskin chaps.",
                Arrays.asList(TabEmote.JUMP_FOR_JOY, TabEmote.JIG), new Bounds(2609, 3088, 2613, 3097, 0),
                Arrays.asList(Items.BROWN_APRON, Items.ADAMANT_MED_HELM, Items.SNAKESKIN_CHAPS), ClueType.MEDIUM, Config.STASH_UNITS[48], 28994),
        MAUSOLEUM("Panic by the mausoleum in Morytania. Wave before you speak to me. Equip a mithril plate skirt, a maple longbow and no boots.",
                Arrays.asList(TabEmote.PANIC, TabEmote.WAVE), new Bounds(3488, 3565, 3514, 3583, 0),
                Arrays.asList(Items.MITHRIL_PLATESKIRT, Items.MAPLE_LONGBOW), Collections.singletonList(Equipment.SLOT_FEET),
                ClueType.MEDIUM, Config.STASH_UNITS[49], 28988),
        CATHERBY_BANK("Shrug in Catherby bank. Yawn before you talk to me. Equip a maple longbow, green d'hide chaps and an iron med helm.",
                Arrays.asList(TabEmote.SHRUG, TabEmote.YAWN), new Bounds(2806, 3438, 2812, 3441, 0),
                Arrays.asList(Items.MAPLE_LONGBOW, Items.GREEN_DHIDE_CHAPS, Items.IRON_MED_HELM), ClueType.MEDIUM, Config.STASH_UNITS[50], 29001),
        BARB_VILLAGE_BRIDGE("Spin on the bridge by the Barbarian Village. Salute before you talk to me. Equip purple gloves, a steel kiteshield and a mithril full helmet.",
                Arrays.asList(TabEmote.SPIN, TabEmote.SALUTE), new Bounds(3103, 3420, 3107, 3421, 0),
                Arrays.asList(Items.PURPLE_GLOVES, Items.STEEL_KITESHIELD, Items.MITHRIL_FULL_HELM), ClueType.MEDIUM, Config.STASH_UNITS[51], 28989),
        OBSERVATORY("Think in the centre of the Observatory. Spin before you talk to me. Equip a mithril chain body, green dragonhide chaps and a ruby amulet.",
                Arrays.asList(TabEmote.THINK, TabEmote.SPIN), new Bounds(2435, 3156, 2447, 3167, 0),
                Arrays.asList(Items.MITHRIL_CHAINBODY, Items.GREEN_DHIDE_CHAPS, Items.RUBY_AMULET), ClueType.MEDIUM, Config.STASH_UNITS[52], 28995),
        CASTLE_WARS("Yawn in the Castle Wars lobby. Shrug before you talk to me. Equip a ruby amulet, a mithril scimitar and a Wilderness cape.",
                Arrays.asList(TabEmote.YAWN, TabEmote.SHRUG), new Bounds(2435, 3081, 2446, 3098, 0),
                Arrays.asList(Items.RUBY_AMULET, Items.MITHRIL_SCIMITAR, Items.TEAM1_CAPE), ClueType.MEDIUM, Config.STASH_UNITS[53], 28991),
        ARCEUUS_LIBRARY("Yawn in the centre of Arceuus library. Nod your head before you talk to me. Equip blue dragonhide vambraces, adamant boots and an adamant dagger.",
                Arrays.asList(TabEmote.YAWN, TabEmote.YES), new Bounds(1621, 3795, 1644, 3819, 0),
                Arrays.asList(Items.BLUE_DHIDE_VAMB, Items.ADAMANT_BOOTS, Items.ADAMANT_DAGGER), ClueType.MEDIUM, Config.STASH_UNITS[54], 29007),

        /*
         * Hard
         */
        KHARAZI_JUNGLE("Beckon on the east coast of the Kharazi Jungle. Beware of double agents! Equip any vestment stole and a heraldic rune shield.",
                Collections.singletonList(TabEmote.BECKON), new Bounds(2953, 2932, 2955, 2934, 0),
                Arrays.asList(Items.SARADOMIN_STOLE, Items.RUNE_SHIELD_H1), ClueType.HARD, Config.STASH_UNITS[55], 29019),
        SHILO_VILLAGE_BANK("Blow a kiss between the tables in Shilo Village bank. Beware of double agents! Equip a blue mystic hat, bone spear and rune platebody.",
                Collections.singletonList(TabEmote.BLOW_KISS), new Bounds(2851, 2952, 2853, 2954, 0),
                Arrays.asList(Items.MYSTIC_HAT, Items.BONE_SPEAR, Items.RUNE_PLATEBODY), ClueType.HARD, Config.STASH_UNITS[56], 29017),
        FISHING_GUILD_BANK("Blow a raspberry in the Fishing Guild bank. Beware of double agents! Equip an elemental shield, blue dragonhide chaps and a rune warhammer.",
                Collections.singletonList(TabEmote.RASPBERRY), new Bounds(2586, 3418, 2589, 3422, 0),
                Arrays.asList(Items.ELEMENTAL_SHIELD, Items.BLUE_DHIDE_CHAPS, Items.RUNE_WARHAMMER), ClueType.HARD, Config.STASH_UNITS[57], 29010),
        LIGHTHOUSE("Bow at the top of the lighthouse. Beware of double agents! Equip a blue dragonhide body, blue dragonhide vambraces and no jewelry.",
                Collections.singletonList(TabEmote.BOW), new Bounds(2504, 3635, 2514, 3646, 2),
                Arrays.asList(Items.BLUE_DHIDE_BODY, Items.BLUE_DHIDE_VAMB), Collections.singletonList(Equipment.SLOT_AMULET),
                ClueType.HARD, Config.STASH_UNITS[58], 29011),
        AGILITY_PYRAMID("Cheer at the top of the agility pyramid. Beware of double agents! Equip a blue mystic robe top, and any rune heraldic shield.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(3042, 4695, 3047, 4700, 0),
                Arrays.asList(Items.MYSTIC_ROBE_TOP, Items.RUNE_SHIELD_H1), ClueType.HARD, Config.STASH_UNITS[59], 29022),
        SOPHANEM("Dance at the cat-doored pyramid in Sophanem. Beware of double agents! Equip a ring of life, an uncharged amulet of glory and an adamant two-handed sword.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(3291, 2781, 3296, 2783, 0),
                Arrays.asList(Items.RING_OF_LIFE, Items.AMULET_OF_GLORY, Items.ADAMANT_2H_SWORD), ClueType.HARD, Config.STASH_UNITS[60], 29012),
        EXAM_CENTRE_HEADBANG("Headbang at the exam centre. Beware of double agents! Equip a mystic fire staff, a diamond bracelet and rune boots.",
                Collections.singletonList(TabEmote.HEAD_BANG), new Bounds(3357, 3332, 3367, 3348, 0),
                Arrays.asList(Items.MYSTIC_FIRE_STAFF, Items.DIAMOND_BRACELET, Items.RUNE_BOOTS), ClueType.HARD, Config.STASH_UNITS[61], 29018),
        JIGGIG("Jig at Jiggig. Beware of double agents! Equip a Rune spear, rune platelegs and any rune heraldic helm.",
                Collections.singletonList(TabEmote.JIG), new Bounds(2460, 3040, 2491, 3053, 0),
                Arrays.asList(Items.RUNE_SPEAR, Items.RUNE_PLATELEGS, Items.RUNE_HELM_H1), ClueType.HARD, Config.STASH_UNITS[62], 29021),
        JOKULS_TENT("Laugh in Jokul's tent in the Mountain Camp. Beware of double agents! Equip a rune full helmet, blue dragonhide chaps and a fire battlestaff.",
                Collections.singletonList(TabEmote.LAUGH), new Bounds(2811, 3680, 2813, 3681, 0),
                Arrays.asList(Items.RUNE_FULL_HELM, Items.BLUE_DHIDE_CHAPS, Items.FIRE_BATTLESTAFF), ClueType.HARD, Config.STASH_UNITS[63], 29015),
        WHITE_WOLF_MOUNTAIN("Panic by the pilot on White Wolf Mountain. Beware of double agents! Equip mithril platelegs, a ring of life and a rune axe.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(2845, 3497, 2848, 3499, 0),
                Arrays.asList(Items.MITHRIL_PLATELEGS, Items.RING_OF_LIFE, Items.RUNE_AXE), ClueType.HARD, Config.STASH_UNITS[64], 29016),
        HAUNTED_WOODS("Panic in the heart of the Haunted Woods. Beware of double agents! Have no items equipped when you do.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(3607, 3487, 3617, 3497, 0),
                Collections.emptyList(), allSlots, ClueType.HARD, null, -1),
        WILDERNESS_VOLCANO_BRIDGE("Panic on the Wilderness volcano bridge. Beware of double agents! Equip any headband and crozier.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(3362, 3935, 3372, 3936, 0),
                Arrays.asList(Items.RED_HEADBAND, Items.SARADOMIN_CROZIER), ClueType.HARD, Config.STASH_UNITS[65], 29020),
        BANANA_PLANTATION("Salute in the banana plantation. Beware of double agents! Equip a diamond ring, amulet of power, and nothing on your chest and legs.",
                Collections.singletonList(TabEmote.SALUTE), new Bounds(2910, 3163, 2920, 3172, 0),
                Arrays.asList(Items.DIAMOND_RING, Items.AMULET_OF_POWER), Arrays.asList(Equipment.SLOT_LEGS, Equipment.SLOT_CHEST),
                ClueType.HARD, Config.STASH_UNITS[66], 29014),
        MESS_HALL("Salute in the centre of the mess hall. Beware of double agents! Equip a rune halberd rune platebody, and an amulet of strength.",
                Collections.singletonList(TabEmote.SALUTE), new Bounds(1639, 3618, 1646, 3635, 0),
                Arrays.asList(Items.RUNE_HALBERD, Items.RUNE_PLATEBODY, Items.AMULET_OF_STRENGTH), ClueType.HARD, Config.STASH_UNITS[67], 29023),
        CHAOS_TEMPLE("Shrug in the Zamorak temple found in the Eastern Wilderness. Beware of double agents! Equip rune platelegs, an iron platebody and blue dragonhide vambraces.",
                Collections.singletonList(TabEmote.SHRUG), new Bounds(3235, 3604, 3244, 3613, 0),
                Arrays.asList(Items.RUNE_PLATEBODY, Items.IRON_PLATEBODY, Items.BLACK_DHIDE_VAMB), ClueType.HARD, Config.STASH_UNITS[68], 29009),
        ROGUES_GENERAL_STORE("Yawn in the rogues' general store. Beware of double agents! Equip an adamant square shield, blue dragon vambraces and a rune pickaxe.",
                Collections.singletonList(TabEmote.YAWN), new Bounds(3024, 3699, 3027, 3704, 0),
                Arrays.asList(Items.ADAMANT_SQ_SHIELD, Items.BLUE_DHIDE_VAMB, Items.RUNE_PICKAXE), ClueType.HARD, Config.STASH_UNITS[69], 29013),

        /*
         * Elite
         */
        LAVA_MAZE("Blow a kiss in the heart of the lava maze. Equip black dragonhide chaps, a spotted cape and a rolling pin.",
                Collections.singletonList(TabEmote.BLOW_KISS), new Bounds(3063, 3852, 3071, 3863, 0),
                Arrays.asList(Items.BLACK_DHIDE_CHAPS, Items.SPOTTED_CAPE, Items.ROLLING_PIN), ClueType.ELITE, Config.STASH_UNITS[70], 29026),
        LEGENDS_GUILD("Bow on the ground floor of the Legend's guild. Equip Legend's cape, a dragon battleaxe and an amulet of glory.",
                Collections.singletonList(TabEmote.BOW), new Bounds(2723, 3374, 2735, 3381, 0),
                Arrays.asList(Items.CAPE_OF_LEGENDS, Items.DRAGON_BATTLEAXE, Items.AMULET_OF_GLORY_1), ClueType.ELITE, Config.STASH_UNITS[71], 29037),
        EDGEVILLE_MONASTERY("Bow upstairs in the Edgeville Monastery. Equip a completed prayer book.",
                Collections.singletonList(TabEmote.BOW), new Bounds(3055, 3482, 3059, 3485, 1),
                Collections.singletonList(Items.HOLY_BOOK), ClueType.ELITE, Config.STASH_UNITS[72], 29029),
        //SHADOW_DUNGEON("Cheer in the Shadow dungeon. Equip a rune crossbow, climbing boots and any mitre.",
        //        Collections.singletonList(TabEmote.CHEER), new Bounds(, 0),
        //        Arrays.asList(Items.RUNE_CROSSBOW, Items.CLIMBING_BOOTS, Items.SARADOMIN_MITRE), ClueType.ELITE, Config.STASH_UNITS[73], 0),
        FISHING_PLATFORM("Dance on the Fishing Platform. Equip barrows gloves, an amulet of glory and a dragon med helm.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(2779, 3273, 2787, 3279, 0),
                Arrays.asList(Items.AMULET_OF_GLORY_1, Items.BARROWS_GLOVES, Items.DRAGON_MED_HELM), ClueType.ELITE, Config.STASH_UNITS[74], 29030),
        SLAYER_TOWER("Headbang at the top of Slayer Tower. Equip a seercull, a combat bracelet and helm of Neitiznot.",
                Collections.singletonList(TabEmote.HEAD_BANG), new Bounds(3416, 3534, 3425, 3544, 2),
                Arrays.asList(Items.SEERCULL, Items.COMBAT_BRACELET, Items.HELM_OF_NEITIZNOT), ClueType.ELITE, Config.STASH_UNITS[75], 29031),
        FIGHT_ARENA_PUB("Headbang in the Fight Arena pub. Equip a pirate bandana, a dragonstone necklace and and a magic longbow.",
                Collections.singletonList(TabEmote.HEAD_BANG), new Bounds(2563, 3139, 2570, 3150, 0),
                Arrays.asList(Items.PIRATE_BANDANA, Items.DRAGON_NECKLACE, Items.MAGIC_LONGBOW), ClueType.ELITE, Config.STASH_UNITS[76], 29039),
        NEITIZNOT_RUNE("Jump for joy at the Neitiznot rune rock. Equip Rune boots, a proselyte hauberk and a dragonstone ring.",
                Collections.singletonList(TabEmote.JUMP_FOR_JOY), new Bounds(2371, 3846, 2378, 3852, 0),
                Arrays.asList(Items.RUNE_BOOTS, Items.PROSELYTE_HAUBERK, Items.DRAGONSTONE_RING), ClueType.ELITE, Config.STASH_UNITS[77], 29025),
        ANCIENT_CAVERN("Jump for joy in the Ancient Cavern. Equip a granite shield, splitbark body and any rune heraldic helm.",
                Collections.singletonList(TabEmote.JUMP_FOR_JOY), new Bounds(1762, 5364, 1768, 5367, 1),
                Arrays.asList(Items.GRANITE_SHIELD, Items.SPLITBARK_BODY, Items.RUNE_HELM_H1), ClueType.ELITE, Config.STASH_UNITS[78], 29034),
        FOUNTAIN_OF_HEROES("Laugh by the fountain of heroes. Equip splitbark legs, dragon boots and a Rune longsword.",
                Collections.singletonList(TabEmote.LAUGH), new Bounds(2913, 9891, 2922, 9896, 0),
                Arrays.asList(Items.SPLITBARK_LEGS, Items.DRAGON_BOOTS, Items.RUNE_LONGSWORD), ClueType.ELITE, Config.STASH_UNITS[79], 29033),
        ARDY_GEM_STALL("Laugh in front of the gem store in Ardougne market. Equip a Castlewars bracelet, a dragonstone amulet and a ring of forging.",
                Collections.singletonList(TabEmote.LAUGH), new Bounds(2664, 3301, 2666, 3304, 0),
                Arrays.asList(Items.CASTLE_WARS_BRACELET_1, Items.DRAGONSTONE_AMULET, Items.RING_OF_FORGING), ClueType.ELITE, Config.STASH_UNITS[80], 29038),
        TROLLWEISS("Panic at the area flowers meet snow. Equip Blue D'hide vambraces, a dragon spear and a rune plateskirt.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(2775, 3780, 2782, 3785, 0),
                Arrays.asList(Items.BLUE_DHIDE_VAMB, Items.DRAGON_SPEAR, Items.RUNE_PLATESKIRT), ClueType.ELITE, Config.STASH_UNITS[81], 29035),
        CHARCOAL_BURNERS("Salute by the Charcoal Burners. Equip a Farmer's strawhat, Shayzien platebody (5) and Pyromancer robes.",
                Collections.singletonList(TabEmote.SALUTE), new Bounds(1710, 3465, 1724, 3468, 0),
                Arrays.asList(Items.FARMERS_STRAWHAT, Items.SHAYZIEN_PLATEBODY_5, Items.PYROMANCER_ROBE), ClueType.ELITE, Config.STASH_UNITS[82], 41758),
        WARRIOR_GUILD_BANK("Salute in the Warriors' guild bank. Equip only a black salamander.",
                Collections.singletonList(TabEmote.SALUTE), new Bounds(2843, 3537, 2848, 3545, 0),
                Collections.singletonList(Items.BLACK_SALAMANDER),
                Arrays.asList(Equipment.SLOT_HAT, Equipment.SLOT_AMMO, Equipment.SLOT_AMULET, Equipment.SLOT_CAPE, Equipment.SLOT_CHEST, Equipment.SLOT_FEET,
                        Equipment.SLOT_HANDS, Equipment.SLOT_LEGS, Equipment.SLOT_RING, Equipment.SLOT_SHIELD),
                ClueType.ELITE, Config.STASH_UNITS[83], 29028),
        SHAYZIEN_WAR_TENT("Shrug in the Shayzien war tent. Equip a blue mystic robe bottom, a rune kiteshield and any bob shirt.",
                Collections.singletonList(TabEmote.SHRUG), new Bounds(1481, 3632, 1488, 3639, 0),
                Arrays.asList(Items.MYSTIC_ROBE_BOTTOM, Items.RUNE_KITESHIELD, Items.BOBS_RED_SHIRT), ClueType.ELITE, Config.STASH_UNITS[84], 29036),
        WEST_ARDY_CHURCH("Spin in West Ardougne Church. Equip a dragon spear and red dragonhide chaps.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(2527, 3285, 2532, 3295, 0),
                Arrays.asList(Items.DRAGON_SPEAR, Items.RED_DHIDE_VAMB), ClueType.ELITE, Config.STASH_UNITS[85], 29024),
        TROLLHEIM("Yawn at the top of Trollheim. Equip a lava battlestaff, black dragonhide vambraces and a mind shield.",
                Collections.singletonList(TabEmote.YAWN), new Bounds(2886, 3673, 2895, 3681, 0),
                Arrays.asList(Items.LAVA_BATTLESTAFF, Items.BLACK_DHIDE_VAMB, Items.MIND_SHIELD), ClueType.ELITE, Config.STASH_UNITS[86], 29032),

        /*
         * Master
         */
        CRYSTALLINE_MAPLE_TREES("Beckon by a collection of crystalline maple trees. Beware of double agents! Equip Bryophyta's staff and a nature tiara.",
                Collections.singletonList(TabEmote.BECKON), new Bounds(2208, 3424, 2214, 3429, 0),
                Arrays.asList(22370, Items.NATURE_TIARA), ClueType.MASTER, Config.STASH_UNITS[87], 34953),
        KRIL_TSUTSAROTH("Blow a kiss outside K'ril Tsutsaroth's chamber. Beware of double agents! Equip a Zamorak full helm and the shadow sword.",
                Collections.singletonList(TabEmote.BLOW_KISS), new Bounds(2924, 5333, 2926, 5336, 2),
                Arrays.asList(Items.ZAMORAK_FULL_HELM, Items.SHADOW_SWORD), ClueType.MASTER, Config.STASH_UNITS[88], 29054),
        WARRIORS_GUILD_BANK("Blow a raspberry in the bank of the Warriors' Guild. Beware of double agents! Equip a dragon battleaxe, a slayer helm of any kind and a dragon defender or avernic defender.",
                Collections.singletonList(TabEmote.RASPBERRY), new Bounds(2843, 3537, 2848, 3545, 0),
                Arrays.asList(Items.DRAGON_BATTLEAXE, Items.DRAGON_DEFENDER, Items.SLAYER_HELMET), ClueType.MASTER, Config.STASH_UNITS[89], 29047),
        IORWERTH_CAMP("Bow in the Iorwerth Camp. Beware of double agents! Equip a charged crystal bow.",
                Collections.singletonList(TabEmote.BOW), new Bounds(2192, 3246, 2211, 3259, 0),
                Collections.singletonList(23983), // Crystal bow
                ClueType.MASTER, Config.STASH_UNITS[90], 29050),
        ENTRANA_CHURCH("Cheer in the Entrana church. Beware of double agents! Equip a full set of black dragonhide armour.",
                Collections.singletonList(TabEmote.CHEER), new Bounds(2849, 3342, 2853, 3355, 0),
                Arrays.asList(Items.BLACK_DHIDE_BODY, Items.BLACK_DHIDE_CHAPS, Items.BLACK_DHIDE_VAMB), ClueType.MASTER, Config.STASH_UNITS[91], 29048),
        MAGIC_AXE_HUT("Clap in the magic axe hut. Beware of double agents! Equip only some flared trousers.",
                Collections.singletonList(TabEmote.CLAP), new Bounds(3187, 3958, 3194, 3962, 0),
                Collections.singletonList(Items.FLARED_TROUSERS), Arrays.asList(Equipment.SLOT_HAT, Equipment.SLOT_AMMO, Equipment.SLOT_AMULET, Equipment.SLOT_CAPE, Equipment.SLOT_CHEST, Equipment.SLOT_FEET,
                Equipment.SLOT_HANDS, Equipment.SLOT_RING, Equipment.SLOT_SHIELD, Equipment.SLOT_WEAPON),
                ClueType.MASTER, Config.STASH_UNITS[92], 29056),
        TZHAAR_GEM_STORE("Cry in the TzHaar gem store. Beware of double agents! Equip a fire cape and TokTz-Xil-Ul.",
                Collections.singletonList(TabEmote.CRY), new Bounds(2463, 5147, 2465, 5150, 0),
                Arrays.asList(Items.FIRE_CAPE, Items.TOKTZ_XIL_UL), ClueType.MASTER, Config.STASH_UNITS[93], 29049),
        IBANS_TEMPLE("Dance in Iban's temple. Beware of double agents! Equip Iban's staff, a black mystic top and a black mystic bottom.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(2003, 4705, 2015, 4718, 1),
                Arrays.asList(Items.IBANS_STAFF, Items.MYSTIC_ROBE_TOP_DARK, Items.MYSTIC_ROBE_BOTTOM_DARK), ClueType.MASTER, Config.STASH_UNITS[94], 29043),
        KING_BLACK_DRAGON_LAIR("Dance in the King Black Dragon's lair. Beware of double agents! Equip a black dragonhide body, black dragonhide vambraces and a black dragon mask.",
                Collections.singletonList(TabEmote.DANCE), new Bounds(2252, 4678, 2290, 4713, 0),
                Arrays.asList(Items.BLACK_DHIDE_BODY, Items.BLACK_DHIDE_VAMB, Items.BLACK_DRAGON_MASK), ClueType.MASTER, Config.STASH_UNITS[95], 29053),
        BARROWS("Do a jig at the barrows chest. Beware of double agents! Equip any full barrows set.",
                Collections.singletonList(TabEmote.JIG), new Bounds(3549, 9692, 3554, 9697, 0),
                Arrays.asList(
                        Items.AHRIMS_HOOD, Items.AHRIMS_ROBETOP, Items.AHRIMS_ROBESKIRT, Items.AHRIMS_STAFF,
                        Items.DHAROKS_HELM, Items.DHAROKS_PLATEBODY, Items.DHAROKS_PLATELEGS, Items.DHAROKS_GREATAXE,
                        Items.GUTHANS_HELM, Items.GUTHANS_PLATEBODY, Items.GUTHANS_CHAINSKIRT, Items.GUTHANS_WARSPEAR,
                        Items.KARILS_COIF, Items.KARILS_LEATHERTOP, Items.KARILS_LEATHERSKIRT, Items.KARILS_CROSSBOW,
                        Items.TORAGS_HELM, Items.TORAGS_PLATEBODY, Items.TORAGS_PLATELEGS, Items.TORAGS_HAMMERS,
                        Items.VERACS_HELM, Items.VERACS_BRASSARD, Items.VERACS_PLATESKIRT, Items.VERACS_FLAIL
                ),
                ClueType.ELITE, Config.STASH_UNITS[96], 29042, 4),
        DEATH_ALTAR("Flap at the Death Altar. Beware of double agents! Equip a death tiara, a legend's cape and any ring of wealth.",
                Collections.singletonList(TabEmote.FLAP), new Bounds(2200, 4831, 2211, 4841, 0),
                Arrays.asList(Items.DEATH_TIARA, Items.CAPE_OF_LEGENDS, Items.RING_OF_WEALTH), ClueType.MASTER, Config.STASH_UNITS[97], 29058),
        GOBLIN_VILLAGE("Goblin Salute in the Goblin Village. Beware of double agents! Equip a bandos godsword, a bandos cloak and a bandos platebody.",
                Collections.singletonList(TabEmote.GOBLIN_SALUTE), new Bounds(2949, 3498, 2964, 3512, 0),
                Arrays.asList(Items.BANDOS_PLATEBODY, Items.BANDOS_CLOAK, Items.BANDOS_GODSWORD), ClueType.MASTER, Config.STASH_UNITS[98], 29051),
        ZUL_ANDRA("Jump for joy in the centre of Zul-Andra. Beware of double agents! Equip a dragon 2h sword, bandos boots and an obsidian cape.",
                Collections.singletonList(TabEmote.JUMP_FOR_JOY), new Bounds(2195, 3052, 2205, 3059, 0),
                Arrays.asList(Items.DRAGON_2H_SWORD, Items.BANDOS_BOOTS, Items.OBSIDIAN_CAPE), ClueType.MASTER, Config.STASH_UNITS[99], 29041),
        LAVA_DRAGON_ISLE("Panic by the big egg where no one dare goes and the ground is burnt. Beware of double agents! Equip a dragon med helm, a TokTz-Ket-Xil, a brine sabre, rune platebody and an uncharged amulet of glory.",
                Collections.singletonList(TabEmote.PANIC), new Bounds(3220, 3826, 3230, 3835, 0),
                Arrays.asList(Items.DRAGON_MED_HELM, Items.TOKTZ_KET_XIL, Items.BRINE_SABRE, Items.RUNE_PLATEBODY, Items.AMULET_OF_GLORY), ClueType.MASTER, Config.STASH_UNITS[100], 29040),
        WISE_OLD_MAN("Show your anger at the Wise old man. Beware of double agents! Equip an abyssal whip, a legend's cape and some spined chaps.",
                Collections.singletonList(TabEmote.ANGRY), new Bounds(3087, 3251, 3094, 3255, 0),
                Arrays.asList(Items.ABYSSAL_WHIP, Items.CAPE_OF_LEGENDS, Items.SPINED_CHAPS), ClueType.MASTER, Config.STASH_UNITS[101], 29059),
        ELLAMARIAS_GARDEN("Show your anger towards the Statue of Saradomin in Ellamaria's garden. Beware of double agents! Equip a zamorak godsword.",
                Collections.singletonList(TabEmote.ANGRY), new Bounds(3228, 3476, 3233, 3481, 0),
                Collections.singletonList(Items.ZAMORAK_GODSWORD), ClueType.MASTER, Config.STASH_UNITS[102], 29055),
        KOUREND_CATACOMBS("Slap your head in the centre of the Kourend catacombs. Beware of double agents! Equip the arclight and the amulet of the damned.",
                Collections.singletonList(TabEmote.SLAP_HEAD), new Bounds(1660, 10044, 1667, 10051, 0),
                Arrays.asList(Items.ARCLIGHT, Items.AMULET_OF_THE_DAMNED), ClueType.MASTER, Config.STASH_UNITS[103], 29052),
        SOUL_ALTAR("Spin in front of the Soul Altar. Beware of double agents! Equip a dragon pickaxe, helm of neitiznot and a pair of rune boots.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(1810, 3850, 1819, 3859, 0),
                Arrays.asList(Items.DRAGON_PICKAXE, Items.HELM_OF_NEITIZNOT, Items.RUNE_BOOTS), ClueType.MASTER, Config.STASH_UNITS[104], 29046),
        ENCHANTED_VALLEY("Stamp in the Enchanted valley west of the waterfall. Beware of double agents! Equip a dragon axe.",
                Collections.singletonList(TabEmote.STOMP), new Bounds(3025, 4517, 3035, 4523, 0),
                Collections.singletonList(Items.DRAGON_AXE), ClueType.MASTER, Config.STASH_UNITS[105], 29060),
        WATCHTOWER("Swing a bullroarer at the top of the watchtower. Beware of double agents! Equip a dragon plateskirt, climbing boots and a dragon chainbody.",
                Collections.singletonList(TabEmote.BULL_ROARER), new Bounds(2927, 4711, 2934, 4718, 0),
                Arrays.asList(Items.DRAGON_PLATESKIRT, Items.CLIMBING_BOOTS, Items.DRAGON_CHAINBODY), ClueType.MASTER, Config.STASH_UNITS[106], 29057),
        CASTLE_DRAKAN("Wave on the northern wall of Castle Drakan. Beware of double agents! Wear a dragon sq shield, splitbark body and any boater.",
                Collections.singletonList(TabEmote.WAVE), new Bounds(3558, 3379, 3572, 3385, 0),
                Arrays.asList(Items.DRAGON_SQ_SHIELD, Items.SPLITBARK_BODY, Items.RED_BOATER), ClueType.MASTER, Config.STASH_UNITS[107], 29044),
        PYRAMID_PLUNDER("Yawn in the 7th room of Pyramid Plunder. Beware of double agents! Equip a pharaoh sceptre and a full set of menaphite robes.",
                Collections.singletonList(TabEmote.YAWN), new Bounds(1941, 4421, 1954, 4431, 0),
                Arrays.asList(Items.PHARAOHS_SCEPTRE, Items.MENAPHITE_PURPLE_HAT, Items.MENAPHITE_PURPLE_TOP, Items.MENAPHITE_PURPLE_ROBE, Items.PHARAOHS_SCEPTRE, Items.MENAPHITE_RED_HAT, Items.MENAPHITE_RED_TOP, Items.MENAPHITE_RED_ROBE),
                ClueType.MASTER, Config.STASH_UNITS[108], 29045, 4);

        public final String clueText;
        public final List<TabEmote> emotes;
        public final Bounds bounds;
        public final List<Integer> equipment;
        public final List<Integer> emptySlots;
        public final ClueType type;
        public final Config stashUnitConfig;
        public final int objectId;
        public int setSize;

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, ClueType type, Config stashUnitConfig, int objectId) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.equipment = equipment;
            this.emptySlots = null;
            this.type = type;
            this.stashUnitConfig = stashUnitConfig;
            this.objectId = objectId;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
            StashUnits.registerStashUnit(this, stashUnitConfig, objectId);
        }

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, ClueType type, Config stashUnitConfig, int objectId, int setSize) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.equipment = equipment;
            this.emptySlots = null;
            this.type = type;
            this.stashUnitConfig = stashUnitConfig;
            this.objectId = objectId;
            this.setSize = setSize;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes, setSize);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
            StashUnits.registerStashUnit(this, stashUnitConfig, objectId);
        }

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, List<Integer> emptySlots, ClueType type, Config stashUnitConfig, int objectId) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.equipment = equipment;
            this.emptySlots = emptySlots;
            this.type = type;
            this.stashUnitConfig = stashUnitConfig;
            this.objectId = objectId;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
            StashUnits.registerStashUnit(this, stashUnitConfig, objectId);
        }

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, ClueType type) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.emptySlots = null;
            this.equipment = equipment;
            this.type = type;
            this.stashUnitConfig = null;
            this.objectId = 0;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
        }

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, List<Integer> emptySlots, ClueType type) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.equipment = equipment;
            this.emptySlots = emptySlots;
            this.type = type;
            this.stashUnitConfig = null;
            this.objectId = 0;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
        }
    }
}
