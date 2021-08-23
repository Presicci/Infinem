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
    List<Integer> emptyEquipment;
    List<TabEmote> emotes;

    public EmoteClue(String clue, ClueType type, List<Integer> equipment, List<Integer> emptyEquipment, List<TabEmote> emotes) {
        super(type);
        this.clue = clue;
        this.equipment = equipment;
        this.emptyEquipment = emptyEquipment;
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
            while(player.isOnline()) {
                if (player.getPosition().distance(npc.getPosition()) > 20) {
                    npc.remove();
                }
                e.delay(5);
            }
            npc.remove();
        });
    }

    //private int[][] alternatives {

    //}

    public static Item itemAlternatives(Player player, int itemId, boolean fromEquipment) {
        if (fromEquipment) {
            Equipment equipment = player.getEquipment();
            return equipment.findItemIgnoringAttributes(itemId, false);
        } else {
            Inventory inventory = player.getInventory();
            return inventory.findItemIgnoringAttributes(itemId, false);
        }
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
        if (emptyEquipment.isEmpty()) {
            return true;
        }
        for (int item : emptyEquipment) {
            if (player.getEquipment().contains(item)) {
                return false;
            }
        }
        return true;
    }

    public enum EmoteClueData {
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
        FLYNNS_MACE_SHOP("Spin at Flynn's Mace Shop.",
                Collections.singletonList(TabEmote.SPIN), new Bounds(2948, 3385, 2952, 3388, 0),
                Collections.emptyList(), ClueType.BEGINNER)
        ;

        public final String clueText;
        public final List<TabEmote> emotes;
        public final Bounds bounds;
        public final List<Integer> equipment;
        public final ClueType type;
        public final Config stashUnitConfig;
        public final int objectId;

        EmoteClueData(String clue, List<TabEmote> emotes, Bounds bounds, List<Integer> equipment, ClueType type, Config stashUnitConfig, int objectId) {
            this.clueText = clue;
            this.emotes = emotes;
            this.bounds = bounds;
            this.equipment = equipment;
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
            this.equipment = equipment;
            this.type = type;
            this.stashUnitConfig = null;
            this.objectId = 0;
            EmoteClue emoteClue = new EmoteClue(clue, type, equipment, Collections.emptyList(), emotes);
            bounds.forEachPos(pos -> pos.getTile().emoteAction = emoteClue::spawnUri);
        }
    }
}
