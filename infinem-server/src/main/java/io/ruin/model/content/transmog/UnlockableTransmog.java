package io.ruin.model.content.transmog;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaTaskTier;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.item.Items;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/5/2024
 */
@Getter
@AllArgsConstructor
public enum UnlockableTransmog {
    // Monkey backpacks
    MONKEY(TransmogSlot.CAPE, 19556, "Search the crate north of Tree Gnome Stronghold."),
    KARAMJA_MONKEY(TransmogSlot.CAPE, 24862, "Complete laps of the Ape Atoll Agility course."),
    ZOMBIE_MONKEY(TransmogSlot.CAPE, 24863, "Complete laps of the Ape Atoll Agility course."),
    MANIACAL_MONKEY(TransmogSlot.CAPE, 24864, "Complete laps of the Ape Atoll Agility course."),
    SKELETON_MONKEY(TransmogSlot.CAPE, 24865, "Complete laps of the Ape Atoll Agility course."),
    KRUK_JR(TransmogSlot.CAPE, 24866, "Complete laps of the Ape Atoll Agility course."),
    PRINCELY_MONKEY(TransmogSlot.CAPE, 24867, "Complete laps of the Ape Atoll Agility course."),
    // Coffins
    BRONZE_COFFIN(TransmogSlot.CAPE, 25459, "Open Shades of Mort'ton chests."),
    STEEL_COFFIN(TransmogSlot.CAPE, 25461, "Open Shades of Mort'ton chests."),
    BLACK_COFFIN(TransmogSlot.CAPE, 25463, "Open Shades of Mort'ton chests."),
    SILVER_COFFIN(TransmogSlot.CAPE, 25465, "Open Shades of Mort'ton chests."),
    GOLD_COFFIN(TransmogSlot.CAPE, 25467, "Open Shades of Mort'ton chests."),
    // Diary rewards, KEEP ORDINALS
    ARDOUGNE_CLOAK_1(TransmogSlot.CAPE, 13121, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.EASY) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.EASY),
    ARDOUGNE_CLOAK_2(TransmogSlot.CAPE, 13122, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.MEDIUM),
    ARDOUGNE_CLOAK_3(TransmogSlot.CAPE, 13123, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.HARD) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.HARD),
    ARDOUGNE_CLOAK_4(TransmogSlot.CAPE, 13124, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.ELITE) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.ELITE),
    DESERT_AMULET_1(TransmogSlot.AMULET, 13133, "Get " + TaskArea.DESERT.getPointThreshold(AreaTaskTier.EASY) + " task points from Desert tasks.", TaskArea.DESERT, AreaTaskTier.EASY),
    DESERT_AMULET_2(TransmogSlot.AMULET, 13134, "Get " + TaskArea.DESERT.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Desert tasks.", TaskArea.DESERT, AreaTaskTier.MEDIUM),
    DESERT_AMULET_3(TransmogSlot.AMULET, 13135, "Get " + TaskArea.DESERT.getPointThreshold(AreaTaskTier.HARD) + " task points from Desert tasks.", TaskArea.DESERT, AreaTaskTier.HARD),
    DESERT_AMULET_4(TransmogSlot.AMULET, 13136, "Get " + TaskArea.DESERT.getPointThreshold(AreaTaskTier.ELITE) + " task points from Desert tasks.", TaskArea.DESERT, AreaTaskTier.ELITE),
    FALADOR_SHIELD_1(TransmogSlot.SHIELD, 13117, "Get " + TaskArea.ASGARNIA.getPointThreshold(AreaTaskTier.EASY) + " task points from Asgarnia tasks.", TaskArea.ASGARNIA, AreaTaskTier.EASY),
    FALADOR_SHIELD_2(TransmogSlot.SHIELD, 13118, "Get " + TaskArea.ASGARNIA.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Asgarnia tasks.", TaskArea.ASGARNIA, AreaTaskTier.MEDIUM),
    FALADOR_SHIELD_3(TransmogSlot.SHIELD, 13119, "Get " + TaskArea.ASGARNIA.getPointThreshold(AreaTaskTier.HARD) + " task points from Asgarnia tasks.", TaskArea.ASGARNIA, AreaTaskTier.HARD),
    FALADOR_SHIELD_4(TransmogSlot.SHIELD, 13120, "Get " + TaskArea.ASGARNIA.getPointThreshold(AreaTaskTier.ELITE) + " task points from Asgarnia tasks.", TaskArea.ASGARNIA, AreaTaskTier.ELITE),
    FREMENNIK_SEA_BOOTS_1(TransmogSlot.FEET, 13129, "Get " + TaskArea.FREMENNIK.getPointThreshold(AreaTaskTier.EASY) + " task points from Fremennik tasks.", TaskArea.FREMENNIK, AreaTaskTier.EASY),
    FREMENNIK_SEA_BOOTS_2(TransmogSlot.FEET, 13130, "Get " + TaskArea.FREMENNIK.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Fremennik tasks.", TaskArea.FREMENNIK, AreaTaskTier.MEDIUM),
    FREMENNIK_SEA_BOOTS_3(TransmogSlot.FEET, 13131, "Get " + TaskArea.FREMENNIK.getPointThreshold(AreaTaskTier.HARD) + " task points from Fremennik tasks.", TaskArea.FREMENNIK, AreaTaskTier.HARD),
    FREMENNIK_SEA_BOOTS_4(TransmogSlot.FEET, 13132, "Get " + TaskArea.FREMENNIK.getPointThreshold(AreaTaskTier.ELITE) + " task points from Fremennik tasks.", TaskArea.FREMENNIK, AreaTaskTier.ELITE),
    KANDARIN_HEADGEAR_1(TransmogSlot.HAT, 13137, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.EASY) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.EASY),
    KANDARIN_HEADGEAR_2(TransmogSlot.HAT, 13138, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.MEDIUM),
    KANDARIN_HEADGEAR_3(TransmogSlot.HAT, 13139, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.HARD) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.HARD),
    KANDARIN_HEADGEAR_4(TransmogSlot.HAT, 13140, "Get " + TaskArea.KANDARIN.getPointThreshold(AreaTaskTier.ELITE) + " task points from Kandarin tasks.", TaskArea.KANDARIN, AreaTaskTier.ELITE),
    KARAMJA_GLOVES_1(TransmogSlot.HANDS, 11136, "Get " + TaskArea.KARAMJA.getPointThreshold(AreaTaskTier.EASY) + " task points from Karamja tasks.", TaskArea.KARAMJA, AreaTaskTier.EASY),
    KARAMJA_GLOVES_2(TransmogSlot.HANDS, 11138, "Get " + TaskArea.KARAMJA.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Karamja tasks.", TaskArea.KARAMJA, AreaTaskTier.MEDIUM),
    KARAMJA_GLOVES_3(TransmogSlot.HANDS, 11140, "Get " + TaskArea.KARAMJA.getPointThreshold(AreaTaskTier.HARD) + " task points from Karamja tasks.", TaskArea.KARAMJA, AreaTaskTier.HARD),
    KARAMJA_GLOVES_4(TransmogSlot.HANDS, 13103, "Get " + TaskArea.KARAMJA.getPointThreshold(AreaTaskTier.ELITE) + " task points from Karamja tasks.", TaskArea.KARAMJA, AreaTaskTier.ELITE),
    MORYTANIA_LEGS_1(TransmogSlot.LEGS, 13112, "Get " + TaskArea.MORYTANIA.getPointThreshold(AreaTaskTier.EASY) + " task points from Morytania tasks.", TaskArea.MORYTANIA, AreaTaskTier.EASY),
    MORYTANIA_LEGS_2(TransmogSlot.LEGS, 13113, "Get " + TaskArea.MORYTANIA.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Morytania tasks.", TaskArea.MORYTANIA, AreaTaskTier.MEDIUM),
    MORYTANIA_LEGS_3(TransmogSlot.LEGS, 13114, "Get " + TaskArea.MORYTANIA.getPointThreshold(AreaTaskTier.HARD) + " task points from Morytania tasks.", TaskArea.MORYTANIA, AreaTaskTier.HARD),
    MORYTANIA_LEGS_4(TransmogSlot.LEGS, 13115, "Get " + TaskArea.MORYTANIA.getPointThreshold(AreaTaskTier.ELITE) + " task points from Morytania tasks.", TaskArea.MORYTANIA, AreaTaskTier.ELITE),
    VARROCK_ARMOUR_1(TransmogSlot.CHEST, 13104, "Get " + TaskArea.MISTHALIN.getPointThreshold(AreaTaskTier.EASY) + " task points from Misthalin tasks.", TaskArea.MISTHALIN, AreaTaskTier.EASY),
    VARROCK_ARMOUR_2(TransmogSlot.CHEST, 13105, "Get " + TaskArea.MISTHALIN.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Misthalin tasks.", TaskArea.MISTHALIN, AreaTaskTier.MEDIUM),
    VARROCK_ARMOUR_3(TransmogSlot.CHEST, 13106, "Get " + TaskArea.MISTHALIN.getPointThreshold(AreaTaskTier.HARD) + " task points from Misthalin tasks.", TaskArea.MISTHALIN, AreaTaskTier.HARD),
    VARROCK_ARMOUR_4(TransmogSlot.CHEST, 13107, "Get " + TaskArea.MISTHALIN.getPointThreshold(AreaTaskTier.ELITE) + " task points from Misthalin tasks.", TaskArea.MISTHALIN, AreaTaskTier.ELITE),
    WILDERNESS_SWORD_1(TransmogSlot.WEAPON, 13108, "Get " + TaskArea.WILDERNESS.getPointThreshold(AreaTaskTier.EASY) + " task points from Wilderness tasks.", TaskArea.WILDERNESS, AreaTaskTier.EASY),
    WILDERNESS_SWORD_2(TransmogSlot.WEAPON, 13109, "Get " + TaskArea.WILDERNESS.getPointThreshold(AreaTaskTier.MEDIUM) + " task points from Wilderness tasks.", TaskArea.WILDERNESS, AreaTaskTier.MEDIUM),
    WILDERNESS_SWORD_3(TransmogSlot.WEAPON, 13110, "Get " + TaskArea.WILDERNESS.getPointThreshold(AreaTaskTier.HARD) + " task points from Wilderness tasks.", TaskArea.WILDERNESS, AreaTaskTier.HARD),
    WILDERNESS_SWORD_4(TransmogSlot.WEAPON, 13111, "Get " + TaskArea.WILDERNESS.getPointThreshold(AreaTaskTier.ELITE) + " task points from Wilderness tasks.", TaskArea.WILDERNESS, AreaTaskTier.ELITE),
    // Misc
    FIRE_CAPE(TransmogSlot.CAPE, Items.FIRE_CAPE, "Complete the Fight Caves."),
    INFERNAL_CAPE(TransmogSlot.CAPE, Items.INFERNAL_CAPE, "Complete The Inferno.")
    ;

    private final TransmogSlot slot;
    private final int itemId;
    private final String unlockRequirement;
    private final TaskArea taskArea;
    private final AreaTaskTier taskAreaTier;

    UnlockableTransmog(TransmogSlot slot, int itemId, String unlockRequirement) {
        this.slot = slot;
        this.itemId = itemId;
        this.unlockRequirement = unlockRequirement;
        this.taskArea = null;
        this.taskAreaTier = null;
    }

    public void unlock(Player player, boolean showNotification) {
        player.getTransmogCollection().addToCollection(itemId, showNotification);
    }

    public static final List<Integer> UNLOCKABLE_TRANSMOG_IDS = new ArrayList<>();
    public static final Map<TransmogSlot, List<UnlockableTransmog>> TRANSMOGS_BY_SLOT = new HashMap<>();
    public static final Map<Integer, UnlockableTransmog> TRANSMOGS_BY_ID = new HashMap<>();
    public static final Map<TaskArea, List<UnlockableTransmog>> REGION_UNLOCKABLES = new HashMap<>();

    static {
        for (UnlockableTransmog t : values()) {
            TRANSMOGS_BY_ID.put(t.getItemId(), t);
            UNLOCKABLE_TRANSMOG_IDS.add(t.getItemId());
            if (TRANSMOGS_BY_SLOT.containsKey(t.slot)) {
                TRANSMOGS_BY_SLOT.get(t.slot).add(t);
            } else {
                List<UnlockableTransmog> list = new ArrayList<>();
                list.add(t);
                TRANSMOGS_BY_SLOT.put(t.slot, list);
            }
            if (t.taskArea != null && t.taskAreaTier != null) {
                REGION_UNLOCKABLES.computeIfAbsent(t.taskArea, area -> new ArrayList<>()).add(t);
            }
        }
        for (StatType type : StatType.values()) {
            UNLOCKABLE_TRANSMOG_IDS.add(type.regularCapeId);
            UNLOCKABLE_TRANSMOG_IDS.add(type.trimmedCapeId);
            UNLOCKABLE_TRANSMOG_IDS.add(type.masterCapeId);
            UNLOCKABLE_TRANSMOG_IDS.add(type.hoodId);
        }
        LoginListener.register(player -> {
            if (player.lastLogin <= 1 /* TODO Timestamp when deploying */) {
                for (UnlockableTransmog t : values()) {
                    if (player.getCollectionLog().hasCollected(t.getItemId())) {
                        player.getTransmogCollection().addToCollection(t.getItemId(), false);
                    }
                    if (t.taskArea != null && t.taskAreaTier != null) {
                        if (t.taskArea.hasTierUnlocked(player, t.taskAreaTier)) {
                            player.getTransmogCollection().addToCollection(t.getItemId(), false);
                        }
                    }
                }
                for (StatType type : StatType.values()) {
                    if (player.getStats().get(type).fixedLevel >= 99) {
                        for (int itemId : Arrays.asList(type.regularCapeId, type.trimmedCapeId, type.hoodId)) {
                            player.getTransmogCollection().addToCollection(itemId, false);
                        }
                    }
                    if (player.getStats().get(type).experience >= Stat.xpForLevel(120)) {
                        player.getTransmogCollection().addToCollection(type.masterCapeId, false);
                    }
                }
                if (player.getStats().isMaxed()) {
                    player.getTransmogCollection().addToCollection(13342, false);
                    player.getTransmogCollection().addToCollection(13281, false);
                }
            }
        });
    }
}
