package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StashUnits;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.npc.actions.clues.Uri;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.TabEmote;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.map.Bounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/20/2021
 */
public class EmoteClue extends Clue {

    String clue;
    public List<Integer> equipment;
    List<Integer> emptyEquipmentSlots;
    List<TabEmote> emotes;

    public EmoteClue(String clue, ClueType type, List<Integer> equipment, List<Integer> emptyEquipmentSlots, List<TabEmote> emotes) {
        super(type);
        this.clue = clue;
        this.equipment = equipment;
        this.emptyEquipmentSlots = emptyEquipmentSlots;
        this.emotes = emotes;
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    public void spawnUri(Player player, TabEmote emote) {
        if (emote != this.emotes.get(0) || !equipmentCheck(player) || player.hasAttribute(AttributeKey.URI_SPAWNED)) {
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

    //private int[][] alternatives {

    //}

    public static Item itemAlternatives(Player player, int itemId) {
        Item item = player.getInventory().findItemIgnoringAttributes(itemId, false);
        if (item != null) {
            return item;
        }
        item = player.getEquipment().findItemIgnoringAttributes(itemId, false);
        return item;
    }

    public boolean hasPerformedSecondEmote(Player player) {
        TabEmote emote = player.attributeOr(AttributeKey.LAST_EMOTE, null);
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
        for (int item : equipment) {
            if (!player.getEquipment().contains(item)) {
                return false;
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
                Arrays.asList(Items.GREEN_ROBE_TOP, Items.HAM_ROBE, Items.IRON_WARHAMMER), ClueType.EASY, Config.STASH_UNITS[31], 28970)
        ;

        public final String clueText;
        public final List<TabEmote> emotes;
        public final Bounds bounds;
        public final List<Integer> equipment;
        public final List<Integer> emptySlots;
        public final ClueType type;
        public final Config stashUnitConfig;
        public final int objectId;

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
